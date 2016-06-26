__author__ = 'sharma.varun'


# http://www.dotnetperls.com/list-python
# count square, rectangle and others
def count_polygons():
    # Enter your code here. Read input from STDIN. Print output to STDOUT
    n = int(raw_input())
    square = 0
    rectangle = 0
    invalid = 0
    other = 0
    for letter in range(0, n):
        a, b, c, d = map(lambda x: int(x), raw_input().split(" "))
        if a <= 0 or b <= 0 or c <= 0 or d <= 0:
            invalid += 1
        else:
            if a == b and b == c and c == d:
                square += 1
            elif (a == b and c == d) or (a == c and b == d) or (a == d and b == c):
                rectangle += 1
            else:
                other += 1

    print str(square) + " " + str(rectangle) + " " + str(other + invalid)


# Enter your code here. Read input from STDIN. Print output to STDOUT
import sys


def polygons():
    sq, rect, invalid = 0, 0, 0
    for line in sys.stdin:
        try:
            a, b, c, d = [int(x) for x in line.strip().split(" ")]
            if a <= 0 or b <= 0 or c <= 0 or d <= 0:
                invalid += 1
            else:
                if a == b and b == c and c == d:
                    sq += 1
                elif (a == c and b == d):
                    rect += 1
                else:
                    invalid += 1
        except:
            pass
    print str(sq) + " " + str(rect) + " " + str(invalid)


polygons()


# given list of words, group it into anagrams with internal ordering within group be lexicographically sorted
def sorted_anagram():
    # Enter your code here. Read input from STDIN. Print output to STDOUT
    import sys

    anagram_dict = {}
    for line in sys.stdin:
        # print ''.join(filter(lambda x: x!= ' ' and x!='\n',sorted(line) ))
        sort_line = ''.join(filter(lambda x: x != ' ' and x != '\n', sorted(line)))
        if sort_line in anagram_dict:
            anagram_dict[sort_line].append(''.join(filter(lambda x: x != '\n', line)))
        else:
            anagram_dict[sort_line] = [''.join(filter(lambda x: x != '\n', line))]
    # print anagram_dict
    a = []
    for data in anagram_dict:
        a.append(",".join(sorted(anagram_dict[data])))
    for d in sorted(a):
        print d


# Enter your code here. Read input from STDIN. Print output to STDOUT
import sys


def anagrams():
    dict_anagram = {}
    for line in sys.stdin:
        sorted_line = ''.join([x for x in sorted(line) if x != " " and x != "\n"])
        if sorted_line in dict_anagram:
            dict_anagram[sorted_line].append(''.join([x for x in line if x != "\n"]))
        else:
            dict_anagram[sorted_line] = [''.join([x for x in line if x != "\n"])]
    ans = [",".join(sorted(dict_anagram[x])) for x in dict_anagram]
    for val in sorted(ans):
        print val


anagrams()


# some encoding
def encoding():
    # Enter your code here. Read input from STDIN. Print output to STDOUT
    x = map(lambda x: int(x), raw_input().split(" "))
    print x[0],
    for letter in range(1, len(x)):
        diff = x[letter] - x[letter - 1]
        if diff < -127 or diff > 127:
            print str(-128),
        print str(diff),


# Enter your code here. Read input from STDIN. Print output to STDOUT
def delta_encoding():
    input = [int(x) for x in raw_input().strip().split(" ")]
    if len(input) > 0:
        print input[0],
        for character in range(1, len(input)):
            difference = input[character] - input[character - 1]
            if difference < -127 or difference > 127:
                print -128,
            print difference,


delta_encoding()


# if pair with given sum exists
def pairs():
    # Enter your code here. Read input from STDIN. Print output to STDOUT
    N = int(raw_input())
    M = int(raw_input())
    data_dict = {}
    flag = False
    for letter in range(0, M):
        num = int(raw_input())
        if (N - num) in data_dict:
            flag = True
        data_dict[num] = True
    if flag:
        print 1
    else:
        print 0


# Enter your code here. Read input from STDIN. Print output to STDOUT
def sum_exists():
    n = int(raw_input())
    m = int(raw_input())
    dict_sum = {}
    flag = False
    for num in range(0, m):
        number = int(raw_input())
        if n - number in dict_sum:
            print 1
            flag = True
            break
        else:
            dict_sum[number] = True
    if not flag:
        print 0


sum_exists()


# print all typos of given word
def typos():
    data = "london"
    replacement_map = {'l': ['k', 'o', 'p'], "o": ['p', 'l', 'k', 'i'], "n": ['j', 'm', 'h', 'b'],
                       "d": ['e', 'r', 'f', 'c', 'x', 's']}
    # missing letter
    for x in range(0, len(data) - 1):
        print data[:x] + data[x + 1:]
    print "........."

    # swap word
    for x in range(0, len(data) - 2):
        print data[:x] + data[x + 1] + data[x] + data[x + 2:]
    print "........."
    # replace by adjacent word
    for x in range(0, len(data)):
        if data[x] in replacement_map:
            replacements = replacement_map[data[x]]
            for letter in replacements:
                print data[:x] + letter + data[x + 1:]
    print "........."


# matrix with wall(w),door(d) and pathways(0), update all pathways by distance to neares door
def pathways():
    from collections import deque
    # matrix with wall(w),door(d) and pathways(0), update all pathways by distance to neares door
    n, m = map(lambda x: int(x), raw_input().split(" "))
    a = []
    for i in range(0, n):
        a.append(raw_input().split(" "))

    bfs_queue = []
    for i in range(0, n):
        for j in range(0, m):
            if a[i][j] == 'd':
                bfs_queue.append((i, j))
    bfs_queue = deque(bfs_queue)
    print "bfs_queue", bfs_queue

    def increment(from_pos, to_pos):
        print "a:", a
        i, j = to_pos
        p, q = from_pos
        # check for validity
        if 0 <= i < n and 0 <= j < m:
            # check if this node is already visited
            if a[i][j] != 'w' and a[i][j] != 'd' and a[i][j] == '0':
                # check if we are coming from door or pathway
                if a[p][q] == 'd':
                    a[i][j] = '1'
                else:
                    a[i][j] = str(int(a[p][q]) + 1)
                return i, j
        return None

    def do_bfs(i, j):
        return_tuple = increment((i, j), (i + 1, j))
        if return_tuple is not None:
            bfs_queue.append(return_tuple)
        return_tuple = increment((i, j), (i - 1, j))
        if return_tuple is not None:
            bfs_queue.append(return_tuple)
        return_tuple = increment((i, j), (i, j + 1))
        if return_tuple is not None:
            bfs_queue.append(return_tuple)
        return_tuple = increment((i, j), (i, j - 1))
        if return_tuple is not None:
            bfs_queue.append(return_tuple)
        print "bfs_queue_inside", bfs_queue

    while bfs_queue:
        i, j = bfs_queue.popleft()
        print "i,j", i, ",", j
        do_bfs(i, j)
    print a


# Write a subroutine which will merge integer ranges in the given array
# https://interview.bpad.booking.com/3950
# input: [[1, 2], [4, 7], [6, 9], [11, 15]]
# output: [[1, 2], [4, 9], [11, 15]]

input_arr = [(2, 10), (7, 15), (8, 10), (9, 18), (19, 23)]


def merge_ranges(input_arr):
    output_arr = [input_arr[0]]
    for i, data in enumerate(input_arr[1:]):
        print "input", data
        if data[0] <= output_arr[-1][1]:
            output_arr[-1] = (output_arr[-1][0], max(data[1], output_arr[-1][1]))
        else:
            output_arr.append(data)
    return output_arr


print merge_ranges(input_arr)


# Specification: Given 3 input lists, the subroutine should produce a list of the elements that are in at least 2 of the 3 input lists

# Example:
#     $a = [ 2, 5, 3, 8, 1 ]
#     $b = [ 1, 3, 6, 2, 7, 6 ]
#     $c = [ 5, 2, 13, 7, 3 ]

#     intersect_2of3( $a, $b, $c );
#     should return a list containing [ 1, 2, 3, 5, 7 ]

def intersect_2of3(input_list, k):
    final_dict = {}
    for a in input_list:
        visited = {}
        for x in a:
            if x not in visited:
                final_dict[x] = final_dict.get(x, 0) + 1
            visited[x] = True
    return filter(lambda x: final_dict[x] >= k, final_dict)


a = [2, 5, 3, 8, 1]
b = [1, 3, 6, 2, 7, 6]
c = [5, 2, 13, 7, 3]
k = 2
print intersect_2of3([a, b, c], k)


# Knight Tour Problem
def print_solution(solution):
    for x in solution:
        for y in x:
            print y,
        print
    print


def is_valid_move(move, solution):
    return 0 <= move[0] < len(solution) and 0 <= move[1] < len(solution[0]) and \
           solution[move[0]][move[1]] == -1


def solve_kt_problem(start, num_move, possible_moves, solution):
    # print "solution"
    # print_solution(solution)
    # print "num_move", num_move
    if num_move == len(solution) * len(solution[0]):
        return True
    for moves in possible_moves:
        move = (start[0] + moves[0], start[1] + moves[1])
        # print "move: ", move
        if is_valid_move(move, solution):
            # print "is a valid move: ", move, "from: ", start
            solution[move[0]][move[1]] = num_move
            if solve_kt_problem(move, num_move + 1, possible_moves, solution):
                return True
            else:
                solution[move[0]][move[1]] = -1
    return False


def kt_problem(m, n):
    solution = [[-1 for x in range(n)] for x in range(m)]
    start = (0, 0)
    solution[start[0]][start[1]] = 0
    possible_moves = [(1, 2), (1, -2), (-1, 2), (-1, -2), (2, 1), (2, -1), (-2, 1), (-2, -1)]
    # print_solution(solution)
    if solve_kt_problem(start, 1, possible_moves, solution):
        print_solution(solution)
    else:
        print_solution(solution)
        print "Solution doesn't exist"


kt_problem(5, 5)


def is_balanced_brackets(brackets):
    stack = []
    flag = True
    relation = {")": "(", "]": "[", "}": "{"}
    for bracket in brackets:
        # print bracket
        if bracket in relation.values():
            stack.append(bracket)
        elif bracket in relation.keys():
            if len(stack) == 0 or stack[-1] != relation[bracket]:
                flag = False
                break
            else:
                stack.pop()
        else:
            raise ValueError
    if flag and len(stack) == 0:
        print "balanced"
    else:
        print "in-balanced"


is_balanced_brackets("()[()(({}))]")
is_balanced_brackets("()[()(({)})]")
is_balanced_brackets("()[()(({())}))]")

# We have an access log of our website. Records are ordered by time, and each one contains a user id
# and the page they visited.
# If the user visited page B after page A, we assume it means that there is a link between A and B.
# Based on that assumption and given an access log, we want to build a sitemap of the website.

# INPUT:
# TIMESTAMP  USER PAGE
# 1458100000 1    'A'
# 1458100001 2    'A'
# 1458100001 3    'B'
# 1458100060 1    'B'
# 1458100061 2    'D'
# 1458100065 2    'C'
# 1458100120 1    'A'
# 1458100125 3    'D'

# OUTPUT:
# 'A' : ['B', 'D']
# 'B' : ['A', 'D']
# 'D' : ['C']

inp = [[1458100000, 1, 'A'], [1458100001, 2, 'A'], [1458100001, 3, 'B']]


def sitemap(inp):
    user_history = {}
    sitemap_dict = {}
    for log in inp:
        if log[1] in user_history:
            last_accessed_site = user_history[log[1]]
            current_site = log[2]

            if last_accessed_site in sitemap_dict:
                sitemap_dict[last_accessed_site].add(current_site)
            else:
                sitemap_dict[last_accessed_site] = {current_site}
        user_history[log[1]] = log[2]

    return sitemap_dict


if __name__ == "__main__":
    pass
