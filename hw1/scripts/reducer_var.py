#!/usr/bin/python3
"""reducer_var.py"""

import sys

cur_mean = 0
cur_size = 0
cur_var = 0

for line in sys.stdin:
    mean, size, var = line.strip().split()
    mean = float(mean)
    size = int(size)
    var = float(var)
    cur_var = (cur_var * cur_size + var * size) / (cur_size + size) + cur_size * size * (
                (mean - cur_mean) / (size + cur_size))
    cur_mean = (cur_mean * cur_size + mean * size) / (cur_size + size)
    cur_size += size

print(cur_var)
