__author__ = 'sharma.varun'

#(Boggle – like question) In a 2D array (M x N, in the given ex. 3×3) of numbers, find the strictly increasing path from the specified origin cell (1,0) to the specified destination cell (0, 2). Array may contain duplicates, and solution should work with the dups.
def strictly_increasing(a, start, end, result, visited):
    # print "\nstart right now", start
    if 0 <= start[0] < len(a) and 0 <= start[1] < len(a[0]):
        # print "intial chk passed"
        if not result or a[start[0]][start[1]] > a[result[-1][0]][result[-1][1]]:
            if start == end:
                # print "reached end with result", result
                result = result + [start]
                print "result:", result
                return True
            else:
                # print "visited", visited
                if not visited[start[0]][start[1]]:
                    result = result + [start]
                    # print "result b4 going recursive", result
                    visited[start[0]][start[1]] = True
                    if (strictly_increasing(a, (start[0] + 1, start[1]), end, result, visited) or
                            strictly_increasing(a, (start[0], start[1] + 1), end, result, visited) or
                            strictly_increasing(a, (start[0] - 1, start[1]), end, result, visited) or
                            strictly_increasing(a, (start[0], start[1] - 1), end, result, visited) or
                            strictly_increasing(a, (start[0] + 1, start[1] + 1), end, result, visited) or
                            strictly_increasing(a, (start[0] + 1, start[1] - 1), end, result, visited) or
                            strictly_increasing(a, (start[0] - 1, start[1] - 1), end, result, visited) or
                            strictly_increasing(a, (start[0] - 1, start[1] + 1), end, result, visited)):
                        return True
    if result:
        visited[result[-1][0]][result[-1][1]] = False
    return False


a = [[1, 2, 3], [2, 1, 3], [0, 2, 4]]
start = (0, 1)
end = (2, 2)

visited = [[False for i in range(3)] for j in range(3)]
flag = strictly_increasing(a, start, end, [], visited)
if not flag:
    print "not possible"
