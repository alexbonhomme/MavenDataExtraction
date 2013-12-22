#! /usr/bin/env python3
# -*- coding: utf-8 -*-
'''
Last edit on 11 dec. 2013

@author: Alexandre Bonhomme
'''
from fr.lille1.maven_data_extraction.scrapers.maven.maven_scraper import \
    MavenScrape
import logging as log
import sys

NODE_IGNORE = ['3059406', # com
               '-509707146'] # org.apache

class Main(object):

    def __init__(self, directoryID = '47'):
        self.scraper = MavenScrape()
        self.scraper.setConfig(directoryID, NODE_IGNORE)

    '''
    Run scraping and .pom files
    by default : scraped all the artifacts (from the root of the repository 'central/')
    '''
    def run(self):
        print('-- Scraping START --')
        self.scraper.run();
        print('-- Scraping COMPLETE --')

'''
Main
'''
if __name__ == '__main__':
    log.basicConfig(filename = 'maven_data_extraction.log', level = log.DEBUG)

    if len(sys.argv) > 1:
        Main(sys.argv[1]).run()
    else:
        Main().run()

