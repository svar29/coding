__author__ = 'sharma.varun'
#!/bin/python

import sys
import os

# Complete the function below.
class Node:
    def __init__(self, val, left, right):
        self.val = val
        self.right = right
        self.left = left
    def __repr__(self):
        return str(self.val)+"("+str(self.left)+","+str(self.right)+")"

# def  maximum_XOR( a,  b):
#     maxXOR = 0
#     for i in xrange(a,b+1):
#         for j in xrange(i,b+1):
#             maxXOR = max(maxXOR,i^j)
#     return maxXOR



def find_num_bits(number):
    count = 0
    while number:
        count += 1
        number >>= 1
    return count

def put_in_trie(root, max_bits, num):
    #update this num into trie starting at root
    save_root = root
    for i in xrange(max_bits-1,-1,-1):
        bit = (num>>i)&1
        if bit == 0:
            if not root.left:
                root.left = Node(0, None, None)
            root = root.left
        if bit == 1:
            if not root.right:
                root.right = Node(1, None, None)
            root = root.right
    return save_root

def find_max_XOR(root,max_bits, num):
    max_XOR = 0
    cur_node = root
    # process this number in trie to find max
    for i in xrange(max_bits-1,-1,-1):
        bit = (num>>i)&1
        if bit == 0:
            if cur_node.right:
                max_XOR = max_XOR<<1 | 1
                cur_node = cur_node.right
            else:
                max_XOR = max_XOR<<1
                cur_node = cur_node.left
        elif bit == 1:
            if cur_node.left:
                max_XOR = max_XOR<<1 | 1
                cur_node = cur_node.left
            else:
                max_XOR = max_XOR <<1
                cur_node = cur_node.right
    return max_XOR

def maximum_XOR(a, b):
    maxXOR = 0
    max_bits = find_num_bits(b)
    root = Node(-1,None,None)
    for i in xrange(a,b+1):
        root = put_in_trie(root,max_bits, i)
    for i in xrange(a,b+1):
        maxXOR = max(maxXOR,find_max_XOR(root,max_bits, i))
    return maxXOR

f = open(os.environ['OUTPUT_PATH'], 'w')


_a = int(raw_input());


_b = int(raw_input());

res = maximum_XOR(_a, _b);
f.write(str(res) + "\n")

f.close()
