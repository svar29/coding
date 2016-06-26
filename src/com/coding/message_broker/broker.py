from socket import *
from threading import Thread
from json import dumps, loads
import os, pickle

HOST = 'localhost'
PORT = 1928
BUFSIZ = 1024
ADDR = (HOST, PORT)

class PersistenStore():
    def __init__(self, fname):
        self.messages = []
        self.fname = fname
        if os.path.exists(fname):
            with open(fname) as f:
                self.messages = pickle.load(f)

    def _persist(self):
        with open(self.fname, 'w') as f:
            pickle.dump(self.messages, f)

    def save_message(self, topic, message):
        self.messages.append((topic, message))
        self._persist()
        return len(self.messages) - 1

    def remove_message(self, idx):
        self.messages.pop(idx)
        self._persist()

    def get_messages(self, topic):
        ret = []
        newmsg = []
        for topic_, msg in self.messages:
            if topic_ == topic:
                ret.append((topic, msg))
            else:
                newmsg.append((topic, message))
        self.messages = newmsg
        self._persist()
        return ret

class Broker(object):
    def __init__(self):
        self.listeners = {}
        self.store = PersistenStore('store.db')

    def subscribe(self, topic, listener):
        print 'Adding listener for:', topic, listener
        if topic not in self.listeners:
            self.listeners[topic] = []
        self.listeners[topic].append(listener)
        messages = self.store.get_messages(topic)
        for topic, message in messages:
            print 'Dispatching', topic, message
            listener.publish(topic, message)

    def publish(self, topic, message):
        listeners = self.listeners.get(topic, [])
        id = self.store.save_message(topic, message)
        if listeners:
            for listener in listeners:
                listener.publish(topic, message)
        self.store.remove_message(id)

    def unsubscribe(self, which):
        for topic in self.listeners:
            if which in self.listeners[topic]:
                print 'Removing listener', which, 'from', topic
                self.listeners[topic].remove(which)

class PubSubClient(Thread):
    def __init__(self, socket, addr, broker):
        Thread.__init__(self)
        self.sock = socket
        self.addr = addr
        self.broker = broker
        self.keep_running = True

    def run(self):
        while True:
            data = self.sock.recv(BUFSIZ)
            if not data:
                break
            message = loads(data)
            self.handle_message(message)
        self.broker.unsubscribe(self)
        self.sock.close()

    def publish(self, topic, message):
        self.sock.send(dumps({'topic': topic, 'content': message}))

    def handle_message(self, message):
        if message['type'] == 'subscribe':
            self.broker.subscribe(message['topic'], self)
        elif message['type'] == 'publish':
            self.broker.publish(message['topic'], message['content'])

class Server(object):
    def __init__(self, addr):
        self.addr = addr
        self.serversock = socket(AF_INET, SOCK_STREAM)
        self.serversock.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)
        self.serversock.bind(addr)
        self.serversock.listen(2)
        self.broker = Broker()

    def serve(self):
        print 'Listenting on', self.addr
        while True:
            clientsock, addr = self.serversock.accept()
            clientsock.setsockopt(IPPROTO_TCP, TCP_NODELAY, True)
            print '...connected from:', addr
            client = PubSubClient(clientsock, addr, self.broker)
            client.start()

def main():
    server = Server(ADDR)
    server.serve()

if __name__=='__main__':
    main()
