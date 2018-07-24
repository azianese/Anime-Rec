# creates an Anime class for different animes
class Anime:
    def __init__(self, name, ranking, rating, votes):
        self.name = name
        self.ranking = ranking
        self.rating = rating
        self.votes = votes
        self.link = link

animeList = []

# allows for web-scrapping data
from urllib.request import urlopen as uReq
from bs4 import BeautifulSoup as soup

# variable for the data's URL
dataUrl = 'https://www.animenewsnetwork.com/encyclopedia/ratings-anime.php?top50=best_bayesian&n=500'
# opens the URL
dataClient = uReq(dataUrl)
# takes the HTML from the URL
dataHTML = dataClient.read()
# closes the client
dataClient.close()
# parses the HTML
pageSoup = soup(dataHTML, 'html.parser')

# stores each anime into containers
containers = pageSoup.findAll('tr')
print(len(containers))

# There are two irrelevant items at the beginning of containers
# and one irrelevant item at the end of containers.
for index in range(2, 502):
    # gets anime statistics from HTML
    container = containers[index]
    ranking = container.td.text
    name = container.findAll('td',{'class','t'})
    link = 'https://www.animenewsnetwork.com' + name[0].a['href']
    name = name[0].text
    statistics = container.findAll('td',{'class','r'})
    rating = statistics[0].text
    numVotes = statistics[1].text
    # prints out anime statistics
    print('name: ' + name)
    print('ranking: ' + ranking)
    print('rating: ' + rating)
    print('votes: ' + numVotes)
    print(link)
    tempAnime = Anime(name, ranking, rating, numVotes)
    animeList.append(tempAnime)
    print('\n')

#for item in pageSoup.tBody :
# for tag in soup.find_all(class_='')

anime = animeList[44]
print(anime.name)
animeURL = anime.link
print(animeURL)
animeClient = uReq(animeURL)
animeHTML = animeClient.read()
animeClient.close()
pageSoup = soup(animeHTML, 'html.parser')

genreDiv = pageSoup.find(id='infotype-30')
print(genreDiv)