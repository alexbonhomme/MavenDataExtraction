'''
Created on 12 dec. 2013

@author: Alexandre Bonhomme
'''
import unittest
from fr.lille1.maven_data_extraction.scrapers.maven.maven_scraper import MavenScrape

class MavenScraperTestCase(unittest.TestCase):
    def setUp(self):
        self.scraper = MavenScrape()

    def testGetDirectoryListing(self):
        listing = self.scraper.getDirectoryListing(-1482203557)
        assert len(listing) == 9
