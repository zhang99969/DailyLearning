# -*- coding: utf-8 -*-
"""
Created on Sun Jul 15 21:37:55 2018

@author: 
"""

    #http://cdn-host.media.yunxi.tv/record/2018-07-08/test_xxxxxxxx/1531029288_1.ts
    #http://cdn-host.media.yunxi.tv/record/2018-07-08/test_xxxxxxxx/1531048163_2173.ts

    
import os
import urllib
import urllib.request
 
print("downloading with urllib")
#url0 = "http://bcmi.sjtu.edu.cn/log/files/lecture_notes/ml_2014_spring_ieee/"
num = 29288
item =1
flag2=1
flag=1
cantdown=0
while item<48163:
#for item in range(1, 2173):
    file = str(item).zfill(4) + ".ts"
    #url = url0 + file
    url0 ="http://cdn-host.media.yunxi.tv/record/2018-07-08/test_xxxxxxxx/15310"
    url = url0 + str(num) +"_" + str(item) +".ts"
    num=num+1  
    #print("downloading " + file)
    LocalPath = os.path.join('D:/P2',file)
    #os.path.join将多个路径组合后返回
    request = urllib.request.Request(url)
    try:
        response = urllib.request.urlopen(request)
    except (urllib.error.HTTPError) as e:
        print('Connection error.'+str(e)+' num='+str(num)+' item='+str(item)) #真实url num-1
        cantdown=cantdown+1
        if cantdown>6:
            if flag==1:
                num=num-10
                cantdown=0
                flag=0
        continue
    else:
        #print(url+'成功成功成功')
        urllib.request.urlretrieve(url,LocalPath)   
        print('num='+str(num)+' item='+str(item)+' 成功') #真实地址 num-1
        num=num+7#一般加7，非常极少数需要+6
        item=item+1
        cantdown=0
        flag=1
        
    #第一个参数url:需要下载的网络资源的URL地址
    #第二个参数LocalPath:文件下载到本地后的路径