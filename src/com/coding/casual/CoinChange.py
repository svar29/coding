__author__ = 'sharma.varun'

a = [1, 2, 3]
sum = 3
# no. of possible ways to reach sum by having denominations in a
def coin_change(sum, index, array):
    if index == len(a) or sum <= 0:
        if sum == 0:
            print array
            return 1
        else:
            return 0
    cur_sum = 0
    cur_sum += coin_change(sum - a[index], index, array + [a[index]])
    cur_sum += coin_change(sum, index + 1, array)
    return cur_sum

# if __name__=="__main__":
array = []
print coin_change(3, 0, array)
