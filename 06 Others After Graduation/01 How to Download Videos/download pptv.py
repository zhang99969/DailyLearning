# -*- coding: utf-8 -*-
"""
Created on Tue Jul 17 16:26:35 2018

@author: 
"""
	#http://153.3.49.202/0/0/0/xxxxx.mp4?fpp.ver=1.3.0.19&k=xxxxxx%26bppcataid%3D38&type=web.fpp&key=xxxxx

import time
import sys
import os
import urllib
import urllib.request
 
print("downloading with urllib")
num = 0


while True:
    file = str(num).zfill(4) + ".mp4"
    #url = url0 + file
    url0 ="http://153.3.49.202/"
    url1="/0/0/xxxxx.mp4?fpp.ver=1.3.0.19&k=xxxxxx%26bppcataid%3D38&type=web.fpp&key=xxxxx"
    url = url0 + str(num) + url1
    #print(url)    
    #print("downloading " + file)
    LocalPath = os.path.join('d:/pp/',file)
    #os.path.join将多个路径组合后返回
    
    request = urllib.request.Request(url)

    try:
        response = urllib.request.urlopen(request)
    except (urllib.error.HTTPError) as e:
        print('Connection error.'+str(e)+' num='+str(num)) #真实url是num-1
        continue
    else:
    
        #print(url+'成功成功成功')
        print('num='+str(num)+' 正在下载') #真实地址是num-1
        urllib.request.urlretrieve(url,LocalPath)   
        num=num+1
        time.sleep(0.1)
    #第一个参数url:需要下载的网络资源的URL地址
    #第二个参数LocalPath:文件下载到本地后的路径