__author__ = 'sharma.varun'
# https://codepair.hackerrank.com/paper/jiqcszwrkovkdqoezzlwomdkydivsfjq?b=eyJpbnRlcnZpZXdfaWQiOjEzNTY3OSwicm9sZSI6ImludGVydmlld2VyIiwic2hvcnRfdXJsIjoiaHR0cDovL2hyLmdzLzU2ZTlhMCJ9
# This is the text editor interface.
# Anything you type or change here will be seen by the other person in real time.

import datetime

time_dict = {}


class Node(object):
    def __init__(self, key, time):
        self.key = key
        self.time = time

    def __eq__(self, other):
        return self.key == other.key and self.time == other.time

    def __hash__(self):
        return hash((self.key, self.time))

    def __repr__(self):
        return str(self.key) + "->" + str(self.time.strftime("%d-%m-%y %H:%M:%S"))


now = datetime.datetime.now()
a = Node(1, now)
b = Node(2, now + datetime.timedelta(days=1))
print a
print b

time_dict[a] = "yo"
time_dict[b] = "yay"

print time_dict

print time_dict[Node(1, now)]

import Queue as Q

time_q_dict = {}


class NodeValue(object):
    def __init__(self, value, time):
        self.value = value
        self.time = time

    def __cmp__(self, other):
        return cmp(other.time, self.time)

    def __repr__(self):
        return str(self.value) + "->" + str(self.time.strftime("%d-%m-%y %H:%M:%S"))


pq = Q.PriorityQueue()
now = datetime.datetime.now()
delta = datetime.timedelta(hours=1)
pq.put(NodeValue("yo", now))
pq.put(NodeValue("yola", now + delta))
pq.put(NodeValue("yaya", now - delta))
pq.put(NodeValue("heyy", now + delta * 2))

print pq.queue
while not pq.empty():
    print pq.queue[0]
    pq.get()



