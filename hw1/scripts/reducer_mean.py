#!/usr/bin/python3
"""reducer_mean.py"""

import sys

cur_mean = 0.0
cur_size = 0

for line in sys.stdin:
    mean, size = line.strip().split()
    mean = float(mean)
    size = int(size)
    cur_mean = (cur_mean * cur_size + mean * size) / (cur_size + size)
    cur_size += size

print(cur_mean)
