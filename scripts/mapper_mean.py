#!/usr/bin/python3
"""mapper_mean.py"""

from csv import reader
import sys

size = 0
summa = 0.0

for i, line in enumerate(reader(sys.stdin)):
    try:
        summa += float(line[9])
        size += 1
    except (ValueError, IndexError) as err:
        continue
print(summa / size, size)
