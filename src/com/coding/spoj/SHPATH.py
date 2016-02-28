__author__ = 'sharma.varun'
"""
http://www.spoj.com/problems/SHPATH/
TLE

1
4
gdansk
2
2 1
3 3
bydgoszcz
3
1 1
3 1
4 4
torun
3
1 3
2 1
4 1
warszawa
2
2 4
3 1
2
gdansk warszawa
bydgoszcz warszawa
"""
from collections import deque
import sys
sys.stdin = open('/tmp/spojtest.in', 'r')

class City(object):
    def __init__(self, name, index):
        self.index = index
        self.name = name
        self.neighbours = []

    def add_neighbour(self, node):
        self.neighbours.append(node)

    def __str__(self):
        return self.name + "=> " + str(self.neighbours)

    def __repr__(self):
        return self.name + "=> " + str(self.neighbours)

    def __hash__(self):
        return hash(self.index,self.name)

    def __eq__(self, other):
        return self.index == other.index

def solve(city_list, city_map, start_city, end_city):
    # start_node = filter(lambda x: x.name == start_city, city_list)[0]
    # end_node = filter(lambda x: x.name == end_city, city_list)[0]
    start_node = city_map[start_city]
    end_node = city_map[end_city]
    # print "start_node->"+ str(start_node)+ "end_node->"+ str(end_node)
    queue = deque()
    queue.append(start_node)
    dist = {start_node: 0}
    visited = {}
    while queue:
        # print "queue->", queue
        node = queue.popleft()
        visited[node] = True
        # print "visited", visited
        for x in node.neighbours:
            current_city = city_list[x[0]]
            # print "is_visited", current_city not in visited
            if current_city not in visited:
                # print "current_city", current_city
                if current_city in dist:
                    # pdb.set_trace()
                    dist[current_city] = min(dist[node] + x[1], dist[current_city])
                else:
                    dist[current_city] = dist[node] + x[1]
                queue.append(current_city)
        # print "dist", dist
    return dist[end_node]

# no of tests
s = int(raw_input())
for i in xrange(s):
    city_list = []
    city_map = {}
    # no. of cities
    n = int(raw_input())
    for j in xrange(n):
        # name of city
        NAME = raw_input()
        city = City(NAME, j)
        city_map[NAME]=city
        city_list.append(city)
        # no. of neighbours for above city
        p = int(raw_input())
        # print "p->", p
        for k in xrange(p):
            # index of city connected to NAME with weight as cost
            nr, cost = map(int, raw_input().split(" "))
            # nr-1 to make it 0 index
            city.add_neighbour((nr - 1, cost))
        # print "city->", city
    # print city_list
    r = int(raw_input())
    for l in xrange(r):
        NAME1, NAME2 = raw_input().split()
        distance = solve(city_list,city_map, NAME1, NAME2)
        print(distance)
    # raw_input()
sys.exit(0)
"""
from heapq import heappush as pqpush
from heapq import heappop as pqpop
import sys
sys.stdin = open('/tmp/spojtest.in', 'r')


def dijkstra(src, graph, maindest):
    route = [float('inf')]*len(graph)
    pqueue = [(0, src, src)]  # a trick here
    while pqueue:
        #print(route)
        weight, src, dest = pqpop(pqueue)
        if dest == maindest:
            return min(route[dest], weight)
        if route[dest] > weight:
            route[dest] = weight
            for ndest, ndweig in graph[dest]:
                pqpush(
                    pqueue,
                    (
                        route[dest] + ndweig,
                        dest,
                        ndest))



def main():
    for _ in range(int(input())):
        citydb = {}
        graph = []
        n = int(input())
        for city_id in range(n):
            city = input()
            citydb[city] = city_id
            graph.append([])
            for _ in range(int(input())):
                dest, weight = map(int, input().split())
                graph[-1].append((dest - 1, weight))
        for _ in range(int(input())):
            src, dest = input().split()
            print(dijkstra(citydb[src], graph, citydb[dest]))



main()

"""