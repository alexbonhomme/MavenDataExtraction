'''
Last edit on 11 dec. 2013

@author: Alexandre Bonhomme
'''
from fr.lille1.maven_data_extraction.scrapers.maven.maven_scraper import MavenScrape
import logging as log
import os
import sys

class Main(object):


    def __init__(self):
        self.scraper = MavenScrape('en')


    '''
    Run scraping and fills the database
    '''
    def run(self):
        self.scraper.setConfig('man', 'jeans', 'Jean', 'Bottom')
        itemList = self.scraper.run();

        log.info('\n-- Scraping DONE ! --')

'''
Main
'''
if __name__ == '__main__':
    log.basicConfig(level = log.DEBUG)

    '''
    if len(sys.argv) < 2:
        print('Usage: ' + sys.argv[0] + ' section subsection')
    else:
        Main('en', sys.argv[1], sys.argv[2]).run()
    '''
    Main().run()

