'''
Created on 24 oct. 2013

@author: Alexandre Bonhomme
'''

from bs4 import BeautifulSoup
from fr.lille1.maven_data_extraction.core.downloader import Downloader
from fr.lille1.maven_data_extraction.scrapers.browser import Browser
import logging as log
import re

class MavenBrowser(Browser):

    '''
    @param page: Just a string with html code
    '''
    def __init__(self, page):
        self.downloader = Downloader()
        self.soup = BeautifulSoup(page)

    '''
    
    '''
    def goTo(self, url, timeRetrying = None):
        try:
            page = self.downloader.getFile(url, timeRetrying)
        except:
            raise
        else:
            self.soup = BeautifulSoup(page)

    '''
    Menu section parsing
    '''
    def getMenu(self, bSubmenu = False):
        if bSubmenu:
            menu = self.soup.find(id = 'mainNavigationMenu').find('ul', attrs = {'class': 'bSubmenu'})
        else:
            menu = self.soup.find(id = 'mainNavigationMenu')

        return menu

    def getMenuEntries(self, bSubmenu = False):
        menu = self.getMenu(bSubmenu)
        entries = menu.find_all('a')

        return entries

    def getMenuLinkFromName(self, name):
        menu = self.getMenu()
        link = menu.find('a', text = re.compile(r'\s+' + name, re.I)).get('href')

        return link

