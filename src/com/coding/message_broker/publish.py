from json import dumps
import socket
import sys

def publish(topic, message):
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.connect(('localhost', 1928))

    # Send the data
    message = dumps({'topic': topic, 'content': message, 'type': 'publish'})
    print 'Sending : "%s"' % message
    s.sendall(message)
    s.close()

def main():
    if len(sys.argv) != 3:
        print "Usage: python publish.py <topic> <message>"
        return
    publish(sys.argv[1], sys.argv[2])

if __name__ == "__main__":
    main()

