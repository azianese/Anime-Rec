# allows for web-scrapping data
from urllib.request import urlopen as uReq
from bs4 import BeautifulSoup as soup

# Anime class for different animes
class Anime:
    def __init__(self, name, ranking, rating, votes, link):
        self.name = name
        self.ranking = ranking
        self.rating = rating
        self.votes = votes
        self.link = link

# List to store anime
animeList = []
# Clears the anime data file
open('AnimeData.txt','w').close()
# Opens the anime data file for append
animeFile = open('AnimeData.txt', 'a', encoding='utf-8')
animeFile.write('ANIME DATA TAKEN FROM ANIMENEWSNETWORK.COM')
animeFile.write('\n\n')

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
# Gets the html for each anime
containers = pageSoup.findAll('tr')
# print(len(containers))

# There are two irrelevant items at the beginning of containers
# and one irrelevant item at the end of containers.
# Iterates over the top 500 anime.

def getAnime():
  for index in range(2, 502):
      # gets anime statistics from HTML
      container = containers[index]
      ranking = container.td.text
      name = container.findAll('td', {'class', 't'})
      link = 'https://www.animenewsnetwork.com' + name[0].a['href']
      name = name[0].text
      statistics = container.findAll('td', {'class', 'r'})
      rating = statistics[0].text
      numVotes = statistics[1].text
      # prints out anime stats to console
      print('name: ' + name)
      print('link: ' + link)
      print('ranking: ' + ranking)
      print('rating: ' + rating)
      print('votes: ' + numVotes)
      # prints out anime stats to file
      currentAnime = Anime(name, ranking, rating, numVotes, link)
      animeFile.write('\nname: ' + name)
      animeFile.write('\nlink: ' + link)
      animeFile.write('\nranking: ' + ranking)
      animeFile.write('\nrating: ' + rating)
      animeFile.write('\nvotes: ' + numVotes)

      # Goes to the webpage for the current anime
      animeClient = uReq(link)
      animeHTML = animeClient.read()
      animeClient.close()
      pageSoup = soup(animeHTML, 'html.parser')

      # Genres of the current anime
      try:
          genreDiv = pageSoup.find(id='infotype-30')
          genres = genreDiv.findAll('span')
          genreList = []
          for genre in genres:
              genreList.append(genre.a.text)
          currentAnime.genres = genreList
      except:
          currentAnime.genres = 'unknown'

      # Themes of the current anime
      try:
          themes = pageSoup.find(id='infotype-31').findAll('span')
          themeList = []
          for theme in themes:
              themeList.append(theme.a.text)
          currentAnime.themes = themeList
      except:
          currentAnime.themes = 'unknown'

      # Premiere date of the current anime
      try:
          date = pageSoup.find(id='infotype-9').div.text
          currentAnime.premiereDate = date
      except:
          currentAnime.premiereDate = 'unknown'

      # Director of the current anime
      try:
          director = pageSoup.find('b', text='Director').parent.a.text
          currentAnime.director = director
      except:
          currentAnime.director = 'unknown'

      # Production Studio of the current anime
      try:
          productionStudio = pageSoup.find('b', text='Production').parent.a.text
          currentAnime.studio = productionStudio
      except:
          currentAnime.studio = 'unknown'

      # Prints the genres
      print('genres: ', end='')
      animeFile.write('\ngenres: ')
      for genre in currentAnime.genres:
          print(genre, end=', ')
          animeFile.write(genre + ', ')
      # Prints the themes
      print('\nthemes: ', end='')
      animeFile.write('\nthemes: ')
      for theme in currentAnime.themes:
          print(theme, end=', ')
          animeFile.write(theme + ', ')
      # Prints the Premiere Date
      print('\npremiereDate: ' + currentAnime.premiereDate)
      animeFile.write('\npremiere date: ' + currentAnime.premiereDate)
      # Prints the director
      print('director: ' + currentAnime.director)
      animeFile.write('\ndirector: ' + currentAnime.director)
      # Prints the studio
      print('production studio: ' + currentAnime.studio)
      animeFile.write('\nproduction studio: ' + currentAnime.studio)

      print()
      animeFile.write('\n')
    
getAnime()    

###
try:
  getAnime()
# except Exception as e: print(e)
except: traceback.print_exc()
###

#animeFile.write('\n')
#animeList.append(currentAnime)

# Closes the anime data file
animeFile.close()

# print(anime.name)
# print(animeURL)
# print('*** genres:')
# for genre in animeList[44].genres:
#     print(genre)
# print('*** themes:')
# for theme in animeList[44].themes:
#     print(theme)
# print('*** premiereDate:')
# print(animeList[44].premiereDate)
# print('*** director:')
# print(animeList[44].director)
# print('*** production studio:')
# print(animeList[44].studio)

# anime = animeList[44]
# animeURL = anime.link
# animeClient = uReq(animeURL)
# animeHTML = animeClient.read()
# animeClient.close()
# pageSoup = soup(animeHTML, 'html.parser')
#
# # Genres
# genreDiv = pageSoup.find(id='infotype-30')
# genres = genreDiv.findAll('span')
# genreList = []
# for genre in genres:
#     genreList.append(genre.a.text)
# animeList[44].genres = genreList
#
# # Themes
# themes = pageSoup.find(id='infotype-31').findAll('span')
# themeList = []
# for theme in themes:
#     themeList.append(theme.a.text)
# animeList[44].themes = themeList
#
# # Date
# date = pageSoup.find(id='infotype-9').div.text
# animeList[44].premiereDate = date
#
# # Director
# director = pageSoup.find('b',text='Director').parent.a.text
# animeList[44].director = director
#
# # Production Studio
# productionStudio = pageSoup.find('b',text='Production').parent.a.text
# animeList[44].studio = productionStudio