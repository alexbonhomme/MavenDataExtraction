'''
Created on 7 nov. 2013

@author: Alexandre Bonhomme
'''
from fr.lille1.maven_data_extraction.core.downloader import Downloader
from fr.lille1.maven_data_extraction.scrapers.scraper import Scraper
from fr.lille1.maven_data_extraction.scrapers.maven.maven_browser import MavenBrowser
import errno
import logging as log
import os
import time

class MavenScrape(Scraper):

    PAGE_BASE = 'http://search.maven.org/#browse'

    def __init__(self, lang):
        self.lang = lang
        self.downloader = Downloader()

    def setConfig(self, section, subsection, productType, bodyPart):
        self.section = section
        self.subsection = subsection
        self.type = productType
        self.bodies = bodyPart

    '''
        Perfom the scraping on Maven Central Repository website
    '''
    def run(self):
        self.dl_folder = self.DL_FOLDER_PATH_BASE + '/'

        # Create folder if is not existing
        try:
            os.makedirs(self.dl_folder)
        except OSError as exception:
            if exception.errno != errno.EEXIST:
                raise

        log.info('-- Starting scraping --')

        # Get base page
        home = self.downloader.getFile(self.PAGE_BASE + self.lang + '/')
        browser = MavenBrowser(home)

        # Start items parsing
        i = 0
        itemList = []
        for item in browser.getProductsList():
            log.debug('zzZZZZzzz')
            time.sleep(3) # let's do it cool

            # Downloading .pom file
            log.info('Downloading ' + imgFilename + '...')
            self.downloader.writeFile(imgUrl, self.dl_folder + imgFilename)

            # count the number of object
            i += 1

        log.info('-- Ending scraping --')
        log.info('-- ' + str(i) + ' projects (.pom) was scraped --')

        return itemList
