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
import sys

class MavenScrape(Scraper):

    PAGE_BASE = 'http://search.maven.org'

    def __init__(self):
        self.downloader = Downloader()
        self.browser = MavenBrowser()

    '''
        Set the configuration of the scraper
        By default the ID of the root listing is 47 (yes, wtf number uh?)
    '''
    def setConfig(self, rootID = '47', nodeIgnoreList = []):
        self.rootID = rootID
        self.nodeIgnoreList = nodeIgnoreList

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
        Perform the scraping on Maven Central Repository website from the given ID
    '''
    def runFrom(self, startID):
        self.dl_folder = self.DL_FOLDER_PATH_BASE + '/'

        # Create folder if is not existing
        try:
            os.makedirs(self.dl_folder)
        except OSError as exception:
            if exception.errno != errno.EEXIST:
                raise

        log.info('-- Starting scraping --')

        ''' 
        List all "root nodes" to find the start point
        '''
        started = False
        for item in self.getDirectoryListing(self.rootID):

            itemID = item.get('id', {})

            if not started:
                if itemID != startID:
                    continue
                else:
                    started = True

            # Scrap!
            self.listAllDirectory(itemID);

        log.info('-- Ending scraping --')

    '''
        List all sub-directories from the folder corresponding to the give ID
    '''
    def listAllDirectory(self, directoryID):
        if directoryID in self.nodeIgnoreList:
            return

        '''
        Actually, we store the current path, because sometime
        the current dependency is from an other artifact so her path
        couldn't be used to save the file.
        So we filled the variable with the first element of response array.
        '''
        currentPath = ''
        for item in self.getDirectoryListing(directoryID):

            itemType = item.get('type', {})
            itemID = item.get('id', {})
            itemName = item.get('name', {})
            itemPath = item.get('path', {})

            log.debug('Current Node :: ' + \
                      str(itemType) + ' : ' + \
                      str(itemID) + ' : ' + \
                      str(itemName) + ' : ' + \
                      str(itemPath))

            if itemID == directoryID:
                currentPath = itemPath
                continue

            # type = 0 is a directory
            # type = 1 is a file
            if itemType == 0:
                log.debug('List child node :: ID : ' + itemID + \
                          ', parent ID : ' + directoryID + \
                          ', path : ' + currentPath)
                self.listAllDirectory(itemID)

            # Downloading .pom
            elif re.match('.*\.pom$', itemName):
                log.info('Downloading :: ' + itemName)
                url = self.PAGE_BASE + '/remotecontent?filepath=' + itemPath

                # /!\ with save the file in the current path, not in the original item path /!\
                self.downloader.writeFile(url, self.dl_folder + currentPath + itemName)

                # Print a dot to display progressing
                sys.stdout.write('.')
                sys.stdout.flush()

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

