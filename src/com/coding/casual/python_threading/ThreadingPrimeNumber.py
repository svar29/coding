import threading
import time


class PrimeNumber(threading.Thread):
    def __init__(self, number):
        threading.Thread.__init__(self)
        self.number = number

    def run(self):
        print "sleeping for ", self.number
        time.sleep(self.number)
        print "woke up for ", self.number

    def add_me(self, num):
        self.number = self.number + num
        print "sleeping for more ", num
        time.sleep(num)


threads = {}
while True:
    inp = long(raw_input("number: "))
    if inp < 1:
        break

    if not inp in threads:
        thread = PrimeNumber(inp)
        threads[inp] = thread
        threads[inp].start()
    else:
        print "already found thread running for this"
        threads[inp].add_me(inp)
        threads[inp].start()

for x in threads:
    threads[x].join()
print "yolo"
