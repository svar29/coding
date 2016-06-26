#!/usr/bin/python
import time
import sys
from threading import Thread


class ThreadTest(Thread):
    def __init__(self, i):
        Thread.__init__(self)
        self.i = i

    def run(self):
        print "thread %d sleeps for 5 seconds" % self.i
        if self.i == 3:
            print self.getName()
            time.sleep(15)
        else:
            time.sleep(5)
        print "thread %d woke up" % self.i


def sleeper(i):
    print "thread %d sleeps for 5 seconds" % i
    time.sleep(5)
    print "thread %d woke up" % i


if __name__ == "__main__":
    for i in range(10):
        # t = Thread(target=sleeper, args=(i,))
        t = ThreadTest(i)
        t.getName()
        t.start()
        time.sleep(1)
        # if i == 3:
        #     t.join()
