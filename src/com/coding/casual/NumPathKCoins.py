__author__ = 'sharma.varun'

k = 12
arr = [[1, 2, 3], [4, 6, 5], [3, 2, 1]]

dp = [[[-1] * (k + 1) for _ in range(len(arr[0]))] for _ in range(len(arr))]


def num_path_k_coins(arr, i, j, k):
    print i, j, k
    if k < 0:
        return 0
    if i > len(arr) - 1 or j > len(arr[0]) - 1:
        return 0
    if i == len(arr) - 1 and j == len(arr[0]) - 1:
        if k == arr[i][j]:
            return 1
        else:
            return 0
    if dp[i][j][k] == -1:
        dp[i][j][k] = num_path_k_coins(arr, i + 1, j, k - arr[i][j]) + num_path_k_coins(arr, i, j + 1, k - arr[i][j])
    return dp[i][j][k]


print num_path_k_coins(arr, 0, 0, k)
