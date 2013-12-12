'''
Created on 12 déc. 2013

@author: Alexandre Bonhomme
'''
from fr.lille1.maven_data_extraction.scrapers.maven.maven_browser import \
    MavenBrowser
import unittest


class Test(unittest.TestCase):


    def setUp(self):
        self.browser = MavenBrowser()

    def testBuildURL(self):
        url = 'http://search.maven.org/solrsearch/select'
        params = [('q', 'id:"-1764970778" OR parentId:"-1764970778"'),
                 ('rows', '100000'),
                 ('core', 'filelisting'),
                 ('wt', 'json')]

        safeUrl = self.browser.buildURL(url, params)

        assert safeUrl == 'http://search.maven.org/solrsearch/select?q=id%3A%22-1764970778%22+OR+parentId%3A%22-1764970778%22&rows=100000&core=filelisting&wt=json'
