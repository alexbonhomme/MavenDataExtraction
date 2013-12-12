'''
Last edit on 11 dec. 2013

@author: Alexandre Bonhomme
'''
from fr.lille1.maven_data_extraction.scrapers.maven.maven_scraper import \
    MavenScrape
import logging as log

class Main(object):

    def __init__(self):
        self.scraper = MavenScrape()

    '''
    Run scraping and .pom files
    by default : scraped all the artefacts (from the root of the repository)
    '''
    def run(self, directoryID = 47):

        self.scraper.setConfig(directoryID)

        print('-- Scraping START --')
        self.scraper.run();
        print('-- Scraping COMPLETE --')

'''
Main
'''
if __name__ == '__main__':
    log.basicConfig(filename = 'maven_data_extraction.log', level = log.DEBUG)

    # Start scraping on the 'com' package
    Main().run(3059406)

