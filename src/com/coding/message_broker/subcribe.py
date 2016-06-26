from json import dumps, loads
import socket
import sys, time

def subscribe(topic):
    print 'Listening for topic', topic
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.connect(('localhost', 1928))

    s.send(dumps({'type': 'subscribe', 'topic': topic}))
    while True:
        data = s.recv(1024)
        if not data:
            break
        message = loads(data)
        print 'Recevied message: "%s"' % (message['content']), time.time()
    s.close()

def main():
    if len(sys.argv) != 2:
        print "Usage: python subscribe.py <topic>"
        return
    subscribe(sys.argv[1])

if __name__ == "__main__":
    main()

