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
import time

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

    def listAllDirectory(self, idDirectory):
        # Get listing
        urlSafe = self.browser.buildURL(self.PAGE_BASE + '/solrsearch/select',
                                    {'q': 'id:"' + str(idDirectory) + '" OR parentId:"' + str(idDirectory) + '"',
                                     'rows': '100000',
                                     'core': 'filelisting',
                                     'wt': 'json'})
        log.debug(urlSafe)
        jsonResponse = self.browser.getJSONContent(urlSafe)
        listing = jsonResponse.get('response', {}).get('docs', {})

        # List link
        itemList = []
        for item in listing:

            # type = 0 is a directory
            # type = 1 is a file
            type = item.get('type', {})

            if type == 0 and item.get('id', {}) != idDirectory:
                self.listAllDirectory(item.get('id', {}))

            name = item.get('name', {})
            path = item.get('path', {})
            if type == 1 and re.match('.*\.pom$', item.get('name', {})):
                # Downloading .pom
                log.info('Downloading ' + name)
                url = self.PAGE_BASE + '/remotecontent?filepath=' + path
                self.downloader.writeFile(url, self.dl_folder + path)

            '''DEBUG
            itemList.append({
                 'id': item.get('id', {}),
                 'name': item.get('name', {}),
                 'path': item.get('path', {})
            })

            log.info(itemList[-1]['id'] + ' : ' + itemList[-1]['name'] + ' : ' + itemList[-1]['path'])
            '''

        return itemList
