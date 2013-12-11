'''
Created on 24 oct. 2013

@author: Alexandre Bonhomme
'''

from bs4 import BeautifulSoup
from fr.lille1.maven_data_extraction.core.downloader import Downloader
from fr.lille1.maven_data_extraction.scrapers.browser import Browser
import urllib
import logging as log
import re
import json

class MavenBrowser(Browser):

    '''
    @param page: Just a string with html code
    '''
    def __init__(self):
        self.downloader = Downloader()

    '''
        Build an URL from an array of parameters
    '''
    def buildURL(self, url, parameters):
        params = urllib.parse.urlencode(parameters, safe = '')

        return url + '?' + params

    '''
        Return a python object wich represent the JSON string
    '''
    def getJSONContent(self, url, timeRetrying = None):
        try:
            page = self.downloader.getFile(url, timeRetrying)
        except:
            raise
        else:
            jsonObject = json.loads(page.decode("utf-8"))

        return jsonObject
