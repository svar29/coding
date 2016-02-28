__author__ = 'sharma.varun'
"""
http://www.spoj.com/problems/BUSYMAN/
TLE
3
3
3 9
2 8
6 9
4
1 7
5 8
7 8
1 8
6
7 9
0 10
4 5
8 9
4 10
5 7
"""
import sys

sys.stdin = open('/tmp/spojtest.in', 'r')


def solve(activities):
    sorted_activites = sorted(activities, key=lambda x: x[1])
    current_end_time = -1
    count = 0
    for activity in sorted_activites:
        if current_end_time <= activity[0]:
            count += 1
            current_end_time = activity[1]
    return count


t = int(raw_input())
for _ in range(t):
    N = int(raw_input())
    activities = []
    for i in range(N):
        m, n = map(int, raw_input().split())
        activities.append((m, n))
    count = solve(activities)
    print count
