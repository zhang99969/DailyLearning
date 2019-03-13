# -*- coding: utf-8 -*-
"""
Created on Wed Mar 13 14:47:06 2019

@author: 最
"""

from selenium import webdriver

browser = webdriver.Chrome()
browser.get('http://localhost:8000')
assert 'Django' in browser.title
