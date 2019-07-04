# -*- coding: utf-8 -*-
"""
Created on Mon Jul  1 13:49:29 2019

10.30 午饭10 水2 晚饭11.5
10.31 早饭5 水果 711买水12.5 ssr15
11.1 汉堡16.8? 饺子8
11.2 工资+14 饼3
11.3 电影张付33 可乐-33 爆米花33- 车1
2019
11.4 地铁4

"""
import re

line_num=0
notes=[[]for i in range(20)] #20行
with open("1.txt", "r") as f:
    for line in f.readlines():
        line = line.strip('\n')  #去掉列表中每一个元素的换行符
        notes[line_num]=line.split(" ")
        line_num=line_num+1
        print(line)

print(notes)

p1=re.compile('^[0-9]*$') #数字
p2=re.compile('^(-?\d+)(\.\d+)?$') #浮点数

# [\u4e00-\u9fa5] 中文
# ^[A-Za-z]+$ 英文

for i in notes:
    for j in i:
        if p1.match(j[-1]):  #每条最后一个是数字
            print(j)
        if p1.match(j):     #年份
            print(j)
            
