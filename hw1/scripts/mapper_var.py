#!/usr/bin/python3
"""mapper_var.py"""

from csv import reader
import sys

size = 0
summa = 0.0
sum_sqr = 0.0

for i, line in enumerate(reader(sys.stdin)):
    try:
        summa += float(line[9])
        sum_sqr += float(line[9])**2
        size += 1
    except (ValueError, IndexError) as err:
        continue
mean = summa / size
var = sum_sqr / size - mean**2
print(summa/size, size, var)
