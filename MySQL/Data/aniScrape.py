# allows for web-scrapping data
from urllib.request import urlopen
# from urllib2 import urlopen
from bs4 import BeautifulSoup as soup

# Anime class for different animes
class Anime:
    def __init__(self):
        self.data = []

# List to store anime
animeList = []
# Clears the anime data file
open('AnimeData.txt','w').close()
# Opens the anime data file for append
animeFile = open('AnimeData.txt', 'a', encoding='utf-8')
animeFile.write('ANIME DATA TAKEN FROM ANIMENEWSNETWORK.COM')
animeFile.write('\n\n')

# data URL
dataUrl = 'https://www.animenewsnetwork.com/encyclopedia/ratings-anime.php?top50=best_bayesian&n=500'
# opens the URL
dataClient = urlopen(dataUrl)
# takes the HTML from the URL
dataHTML = dataClient.read()
# closes the client
dataClient.close()
# parses the HTML
pageSoup = soup(dataHTML, 'html.parser')
# Gets the html for each anime
containers = pageSoup.findAll('tr')

# There are two irrelevant items at the beginning of containers
# and one irrelevant item at the end of containers.
# Iterates over the top 500 anime.

def getAnime():
  for index in range(2, 502):
    
      #################### GET ANIME DATA ####################
      
      # gets anime statistics from HTML
      container = containers[index]
      ranking = container.td.text
      name = container.findAll('td', {'class', 't'})
      link = 'https://www.animenewsnetwork.com' + name[0].a['href']
      name = name[0].text
      statistics = container.findAll('td', {'class', 'r'})
      rating = statistics[0].text
      numVotes = statistics[1].text
      # prints out basic anime stats to console
      print('name: ' + name)
      print('ranking: ' + ranking) 
      # create an anime class object to hold data
      currentAnime = Anime()

      # Goes to the webpage for the current anime
      success = False
      while success == False:
        try: 
          animeClient = urlopen(link, timeout=5)
          animeHTML = animeClient.read()
          success = True
        except: 
          pass
      animeClient.close()
      pageSoup = soup(animeHTML, 'html.parser')
      
      # Alternate title of the current anime
      try:
          altTitles = pageSoup.find(id='infotype-2').findAll("div");
          for title in altTitles:
              if "Japanese" in title.text:
                  currentAnime.altTitle = title.text;
                  break
      except:
          currentAnime.altTitle = 'unknown'

      try:
          currentAnime.altTitle = currentAnime.altTitle;
      except:
          currentAnime.altTitle = 'unknown'

      # Genres of the current anime
      try:
          genres = pageSoup.find(id='infotype-30').findAll('span')
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
          
      # Plot Summary of the current anime
      try:
          plot = pageSoup.find(id='infotype-12').find('span')
          currentAnime.plot = plot.text
      except:
          currentAnime.plot = 'unknown'
          
      # Image of the current anime
      try:
          imageLink = pageSoup.find(id='infotype-19').find('a')
          currentAnime.imageLink = 'https://www.animenewsnetwork.com' + imageLink.attrs['href']
      except:
          currentAnime.imageLink = 'unknown'

      if (currentAnime.imageLink == 'unknown'):
          try:
              imageLink = pageSoup.find(id='infotype-19').find('img')
              currentAnime.imageLink = 'https://www.animenewsnetwork.com' + imageLink.attrs['src'].split('animenewsnetwork.com')[1]
          except:
              currentAnime.imageLink = 'unknown'

      if  (currentAnime.imageLink == 'unknown'):
          try:
              imageLink = pageSoup.find(id='vid-art')
              currentAnime.imageLink = 'https://www.animenewsnetwork.com' + imageLink.attrs['src'].split('animenewsnetwork.com')[1]
          except:
              currentAnime.imageLink = 'unknown'

      # Video of the current anime
      #try:
      #    productionStudio = pageSoup.find('b', text='Production').parent.a.text
      #    currentAnime.studio = productionStudio
      #except:
      #    currentAnime.studio = 'unknown'
          
      #################### PRINTS ANIME DATA TO FILE ####################
        
      # prints out anime basic anime data to file
      animeFile.write('\nname: ' + name)
      animeFile.write('\nlink: ' + link)
      animeFile.write('\naltTitle: ' + currentAnime.altTitle)
      animeFile.write('\nranking: ' + ranking)
      animeFile.write('\nrating: ' + rating)
      animeFile.write('\nvotes: ' + numVotes)  
      animeFile.write('\npremiere date: ' + currentAnime.premiereDate)
      animeFile.write('\ndirector: ' + currentAnime.director)
      animeFile.write('\nproduction studio: ' + currentAnime.studio)
        
      # Prints the genres
      animeFile.write('\ngenres: ')
      for genre in currentAnime.genres:
          animeFile.write(genre + ',')
      # Prints the themes
      animeFile.write('\nthemes: ')
      for theme in currentAnime.themes:
          animeFile.write(theme + ',')

      # Prints the plot and link to image of the anime
      animeFile.write('\nplot: ' + currentAnime.plot)
      animeFile.write('\nimg: ' + currentAnime.imageLink)
      
      # Separate this anime from the next one with a blank line
      animeFile.write('\n')
      print()
    
getAnime()    
animeFile.close()