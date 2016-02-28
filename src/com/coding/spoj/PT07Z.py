__author__ = 'sharma.varun'
"""
http://www.spoj.com/problems/PT07Z/
AC

3
1 2
2 3
"""

import sys

sys.stdin = open('/tmp/spojtest.in', 'r')


class Node:
    def __init__(self, value):
        self.value = value
        self.neighbours = []

    def add_neighbour(self, neighbour):
        self.neighbours.append(neighbour)

    def __str__(self):
        return str(self.value)

    def __repr__(self):
        return str(self.value)


def find_pair_with_max_sum(array):
    array.sort(reverse=True)
    if len(array) > 1:
        return array[0] + array[1]
    else:
        return array[0]


visited = {}
max_path = -1


def find_longest_path(node):
    global max_path
    global visited
    visited[node.value] = True
    max_height = 0
    height_array = []
    for x in node.neighbours:
        if x.value not in visited:
            height = find_longest_path(x)
            # print "height:", height
            height_array.append(height)
            if height > max_height:
                max_height = height

    if height_array:
        # print height_array
        path_through_node = find_pair_with_max_sum(height_array)
        # print "path_through_node", path_through_node
        if path_through_node > max_path:
            max_path = path_through_node
            # print "max_path",max_path
    # print "max_height",max_height
    return max_height + 1


N = int(raw_input())
graph_node_map = {}
for _ in range(N - 1):
    u, v = map(int, raw_input().split(" "))
    if u not in graph_node_map:
        graph_node_map[u] = Node(u)
    if v not in graph_node_map:
        graph_node_map[v] = Node(v)
    graph_node_map[u].add_neighbour(graph_node_map[v])
    graph_node_map[v].add_neighbour(graph_node_map[u])
# print graph_node_map
# for x in graph_node_map:
#     print graph_node_map[x].neighbours
find_longest_path(graph_node_map.values()[0])
print max_path
