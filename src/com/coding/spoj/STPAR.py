__author__ = 'sharma.varun'
"""
http://www.spoj.com/problems/STPAR/
AC
5
5 1 2 4 3
0
"""

import sys

sys.stdin = open('/tmp/spojtest.in', 'r')
while True:
    n = int(raw_input())
    if n == 0:
        break
    numbers = map(int, raw_input().split(" "))
    # print numbers
    current_number = 1
    stack = []
    i = 0
    while i < len(numbers) or stack:
        if stack and stack[-1] == current_number:
            stack.pop()
            current_number += 1
        elif stack and stack[-1] != current_number and i>=len(numbers):
            break
        elif numbers[i] == current_number:
            current_number += 1
            i += 1
        else:
            stack.append(numbers[i])
            i += 1
    # print current_number
    if current_number - 1 == len(numbers):
        print "yes"
    else:
        print "no"
