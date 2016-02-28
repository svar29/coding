__author__ = 'sharma.varun'

#Nth fibonacci number
def power_recursive(a, n):
    if n > 1:
        ans = power_recursive(a, n / 2)
        if n % 2 == 0:
            ans = ans * ans
        else:
            ans = ans * ans * a
        return ans
    else:
        return a


#print "power_recursive", power_recursive(2, 3)


def matrix_multiply(a, b):
    if len(a[0]) == len(b):
        n = len(a)
        m = len(b[0])
        p = len(b)
        result = [[0 for i in range(m)] for j in range(n)]
        for i in range(0, n):
            for j in range(0, m):
                for k in range(0, p):
                    result[i][j] += a[i][k] * b[k][j]
        return result
    else:
        raise ValueError('Matrix not multipliable')


def power_matrix_recursive(a, n):
    if n > 1:
        ans = power_matrix_recursive(a, n / 2)
        if n % 2 == 0:
            ans = matrix_multiply(ans, ans)
        else:
            ans = matrix_multiply(matrix_multiply(ans, ans), a)
        return ans
    else:
        return a


def nth_fibonacci(n):
    matrix = [[1, 1], [1, 0]]
    if n == 0:
        return 0
    else:
        ans = power_matrix_recursive(matrix, n - 1)
        return ans[0][0]


if __name__ == "__main__":
    n = int(raw_input("Find nth fibonacci. Enter n: "))
    print "nth_fibonacci", str(nth_fibonacci(n))


def strictly_increasing(a,start,end,result,visited):
    if start[0]>0 and start[0]<len(a) and start[1]>0 and start[1]<len(a[0]):
        if start == end:
            result = result + [a[start[0]][start[1]]]
            return result, True
        else:
            print "visited", visited
            if(not visited[start[0]][start[1]] and (a[start[0]][start[1]] > result[-1]) or not result):
                result = result + [a[start[0]][start[1]]]
            visited[start[0]][start[1]]=True
            if (strictly_increasing(a,(start[0]+1,start[1]),end,result,visited)[1] or \
                        strictly_increasing(a,(start[0],start[1]+1),end,result,visited)[1] or \
                        strictly_increasing(a,(start[0]-1,start[1]),end,result,visited)[1] or \
                        strictly_increasing(a,(start[0],start[1]-1),end,result,visited)[1] or \
                        strictly_increasing(a,(start[0]+1,start[1]+1),end,result,visited)[1] or \
                        strictly_increasing(a,(start[0]+1,start[1]-1),end,result,visited)[1] or \
                        strictly_increasing(a,(start[0]-1,start[1]-1),end,result,visited)[1] or \
                        strictly_increasing(a,(start[0]-1,start[1]+1),end,result,visited)[1]):
                return result, True
        visited[start[0]][start[1]]=False
    return result, False
a=[[1,2,3],[3,4,5],[5,6,7]]
start=(0,1)
end=(0,2)

visited = [[False for i in range(3)] for j in range(3)]
result, flag = strictly_increasing(a,start,end,[a[start[0]],a[start[1]]],visited)
if flag:
    print result
else:
    print "not possible"