'''
Last edit on 11 dec. 2013

@author: Alexandre Bonhomme
'''
from fr.lille1.maven_data_extraction.scrapers.maven.maven_scraper import \
    MavenScrape
import logging as log
import os
import sys

class Main(object):

    def __init__(self):
        self.scraper = MavenScrape()

    '''
    Run scraping and .pom files
    '''
    def run(self):
        # We scraped all the artefacts (from the root of MCR)
        # self.scraper.setConfig(47)

        # Only the children of 'com' package
        self.scraper.setConfig(3059406)

        # Go!
        itemList = self.scraper.run();

        log.info('-- Scraping DONE ! --')

'''
Main
'''
if __name__ == '__main__':
    log.basicConfig(filename = 'maven_data_extraction.log', level = log.DEBUG)

    '''
    if len(sys.argv) < 2:
        print('Usage: ' + sys.argv[0] + ' section subsection')
    else:
        Main('en', sys.argv[1], sys.argv[2]).run()
    '''
    Main().run()

