__author__ = 'sharma.varun'


#
# Your previous Python 3 content is preserved below:
#
# def say_hello():
#     print('Hello, World')
#
# for i in range(5):
#     say_hello()
#
#
# #
# # Your previous Plain Text content is preserved below:
# #
# # A Range is represented by a pair of integers [a, b], a <= b
# # and it represents the range of integers from a to b, both inclusive.
# # Example : [2, 6] represents the range of integers 2, 3, 4, 5, 6
# #
# # Write a function named `searchRanges` which takes takes two parameters
# # 1. `ranges` : An Array of non-overlapping, sorted Ranges
# # 2. `keyRange` : The Range to search for in `ranges`
# #
# # Return the first Range in `ranges` which
# # overlaps with the keyRange. If no
# # Range in `ranges` overlaps with `keyRange`, return null.
# #
# #
# # Example1:
# # Input :
# #                             key
# #                        |------------|
# # |-----|   |---------------| |--------|
# #   R1             R2           R3
# #
# # return: R2
# #
# #
# # Example 2:
# #
# #              key
# #             |---|
# # |-----|             |---------------| |--------|
# #   R1                         R2           R3
# #
# # return: null
# #
# #
# #
# # Example 3:
# #        key
# #    |--------------|
# #       |-----|   |---------------| |--------|
# #         R1             R2           R3
# #
# # return: R1
# #
# #
# #
# # Example 4:
# #                                             key
# #                                         |------------|
# # |-----|   |---------------| |--------|
# #   R1             R2           R3
# #
# # return: null
#

# interviewer code
def compare_ranges(r1, r2):
    return 0 if r1[0] <= r2[1] and r1[1] >= r2[0] else r1[0] - r2[0]


def search_ranges(ranges, key_range):
    l = len(ranges)
    if l == 0:
        return None
    else:
        cmp = compare_ranges(ranges[l / 2], key_range)
        print "cmp", cmp
        if cmp < 0:
            return search_ranges(ranges[l / 2 + 1:], key_range)
        elif cmp > 0:
            return search_ranges(ranges[:l / 2], key_range)
        else:
            if l > 1 and compare_ranges(ranges[l / 2 - 1], key_range) == 0:
                return search_ranges(ranges[:l / 2], key_range)
            else:
                return ranges[l / 2]


# My code
def search_max_index(ranges, key_range):
    for i in xrange(0, len(ranges)):
        if ranges[i][0] >= key_range[0]:
            return i
        if i == len(ranges) - 1:
            return len(ranges)


def search_max_index_binary(ranges, key_range):
    l = len(ranges)
    if (l == 0):
        return None
    else:
        if ranges[l / 2][0] >= key_range[0]:
            if ranges[l / 2 - 1][0] > key_range[0]:
                return search_max_index_binary(ranges[:l / 2], key_range)
            else:
                return l / 2
        else:
            return search_max_index_binary(ranges[l / 2 + 1:], key_range)


def find_range(ranges, key_range):
    idx = search_max_index_binary(ranges, key_range)
    if not idx:
        if compare_ranges(ranges[0], key_range) == 0:
            return ranges[0]
        if compare_ranges(ranges[-1], key_range) == 0:
            return ranges[-1]
        else:
            return None
    else:
        if compare_ranges(ranges[idx - 1], key_range) == 0:
            return ranges[idx - 1]
        elif compare_ranges(ranges[idx], key_range) == 0:
            return ranges[idx]
        else:
            return None


test1_key = (1, 1)
test2_key = (1, 4)
test3_key = (3, 4)
test4_key = (4, 6)
test5_key = (4, 8)
test6_key = (6, 6)
test7_key = (4, 20)
test8_key = (21, 22)
print find_range([(2, 5), (7, 10), (11, 15), (16, 20)], test1_key)
print find_range([(2, 5), (7, 10), (11, 15), (16, 20)], test2_key)
print find_range([(2, 5), (7, 10), (11, 15), (16, 20)], test3_key)
print find_range([(2, 5), (7, 10), (11, 15), (16, 20)], test4_key)
print find_range([(2, 5), (7, 10), (11, 15), (16, 20)], test5_key)
print find_range([(2, 5), (7, 10), (11, 15), (16, 20)], test6_key)
print find_range([(2, 5), (7, 10), (11, 15), (16, 20)], test7_key)
print find_range([(2, 5), (7, 10), (11, 15), (16, 20)], test8_key)

global_count = 0
found_global = None


def search_key(data_range, key):
    global global_count
    global found_global
    global_count = 0
    found_global = None
    # print "welcome"
    low = 0
    high = len(data_range)
    # print "low:high", low,high
    flag_range = binary_search(data_range, key, low, high)
    return flag_range


def binary_search(data_range, key, low, high):
    global global_count
    global found_global
    found = None
    if low >= high:
        if (found_global and not found):
            return found_global
        else:
            return None
    mid = (low + high) / 2
    # print "mid:", mid
    decision = intersect(data_range, key, mid)
    # print "decision",decision[1]
    # global_count+=1
    # if global_count>10:
    # return None
    if (decision[1] == "left"):
        # print "found_left"
        return binary_search(data_range, key, low, mid)
    elif (not found_global and decision[1] == "right"):
        # print "found_right"
        return binary_search(data_range, key, mid + 1, high)
    elif (decision[1] == "middle"):
        # print "found_middle"
        return data_range[mid]
    elif (decision[1] == "found"):
        # print "found_found"
        found_global = decision[0]
        return binary_search(data_range, key, low, mid)
    else:
        pass
    # print "found",found_global, found
    if (found_global and not found):
        return found_global
    else:
        return found


def intersect(data_range, key, mid):
    # time.sleep(1000)
    element = data_range[mid]
    # print "picked elem", element
    # 11,15
    if (key[0] > element[1]):
        # print "right", element
        return (element, "right")
    # 8,infinite--8,9
    elif (key[0] >= element[0] and key[0] <= element[1]):
        # print "middle",element
        return (element, "middle")
    # 6,8---6,infinite
    elif (key[0] <= element[0] and key[1] >= element[0]):
        # print "found",element
        return (element, "found")
    # 4,6
    elif (key[0] < element[0] and key[1] < element[0]):
        # print "left",element
        return (element, "left")
    else:
        # print "error",element
        return (element, "error")


test = [(2, 5), (7, 10), (11, 15), (16, 20)]
test1_key = (1, 1)
test2_key = (1, 4)
test3_key = (3, 4)
test4_key = (4, 6)
test5_key = (4, 8)
test6_key = (6, 6)
test7_key = (4, 20)
test8_key = (21, 22)
print "input ranges", test
print "found solution for", test1_key, search_key(test, test1_key)
print "found solution for", test2_key, search_key(test, test2_key)
print "found solution for", test3_key, search_key(test, test3_key)
print "found solution for", test4_key, search_key(test, test4_key)
print "found solution for", test5_key, search_key(test, test5_key)
print "found solution for", test6_key, search_key(test, test6_key)
print "found solution for", test7_key, search_key(test, test7_key)
print "found solution for", test8_key, search_key(test, test8_key)
