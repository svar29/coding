__author__ = 'sharma.varun'

#http://www.dotnetperls.com/list-python
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
def merge_ranges():
    input1 = [[4, 7], [6, 9], [7, 8], [7.5, 10]]  # n
    input1 = [[1, 2], [4, 7], [5, 8], [11, 15]]
    output = [input1[0]]  # [[4,7]]
    # len(input) will be 4 here
    i = 0
    print output
    end_i = len(input1)  # 3
    j = 0
    while i != end_i - 1:
        start, end = output[j]  # inner array 4,9 <------------
        start_next, end_next = input1[i + 1]  # 7,8 <------------
        # if i am merging

        if start_next < end:
            output[j] = [start, max(end, end_next)]  #
        else:
            output.append([start_next, end_next])  # output[[7,8]]
            j += 1
        i += 1
    print output

if __name__ == "__main__":
    merge_ranges()

