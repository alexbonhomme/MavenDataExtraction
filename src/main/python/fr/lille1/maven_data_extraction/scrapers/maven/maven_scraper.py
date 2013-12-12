'''
Created on 7 nov. 2013

@author: Alexandre Bonhomme
'''
from fr.lille1.maven_data_extraction.core.downloader import Downloader
from fr.lille1.maven_data_extraction.scrapers.maven.maven_browser import \
    MavenBrowser
from fr.lille1.maven_data_extraction.scrapers.scraper import Scraper
import errno
import logging as log
import os
import re

class MavenScrape(Scraper):

    PAGE_BASE = 'http://search.maven.org'

    def __init__(self):
        self.downloader = Downloader()
        self.browser = MavenBrowser()

    '''
        Set the configuration of the scraper
        By default the ID of the root listing is 47 (yes, wtf number uh?)
    '''
    def setConfig(self, rootID = 47):
        self.rootID = rootID

    '''
        Perform the scraping on Maven Central Repository website
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

        # The ID of the root to start the scraping
        self.listAllDirectory(self.rootID);

        log.info('-- Ending scraping --')

    '''
        List all sub-directories from the folder corresponding to the give ID
    '''
    def listAllDirectory(self, directoryID):

        # List link
        itemList = []
        for item in self.getDirectoryListing(directoryID):

            # type = 0 is a directory
            # type = 1 is a file
            itemType = item.get('type', {})
            itemID = item.get('id', {})
            itemName = item.get('name', {})
            itemPath = item.get('path', {})

            log.debug('Current Node :: ' + str(itemType) + ' : ' + \
                      str(itemID) + '(' + str(type(itemID)) + ')' + ' : ' + \
                      str(itemName) + ' : ' + str(itemPath))

            if itemType == 0 and itemID != directoryID:
                log.debug('List child node :: ' + itemID)
                self.listAllDirectory(itemID)

            elif itemType == 1 and re.match('.*\.pom$', itemName):
                # Downloading .pom
                log.info('Downloading :: ' + itemName)
                url = self.PAGE_BASE + '/remotecontent?filepath=' + itemPath
                self.downloader.writeFile(url, self.dl_folder + itemPath)

                # Print a dot to display progressing
                print('.')

        return itemList

    '''
     Get the listing of a directory from his id
    '''
    def getDirectoryListing(self, directoryID):
        # Get listing
        urlSafe = self.browser.buildURL(self.PAGE_BASE + '/solrsearch/select',
                                    [('q', 'id:"' + str(directoryID) + '" OR parentId:"' + str(directoryID) + '"'),
                                     ('rows', '100000'),
                                     ('core', 'filelisting'),
                                     ('wt', 'json')])
        log.debug(urlSafe)

        jsonResponse = self.browser.getJSONContent(urlSafe)
        listing = jsonResponse.get('response', {}).get('docs', {})

        return listing

