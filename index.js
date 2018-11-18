//////////////////// VARIABLES ////////////////////

//use express
var express = require('express');
//variable to use express
var app = express();
//use the body-parser middleware to handle post data
var bodyParser = require('body-parser');
//create application/x-www-form-urlencoded parser
var urlencodedParser = bodyParser.urlencoded({ extended: false });

//////////////////// SETUP ////////////////////

//tells express to use ejs as the view/template engine
app.set('view engine', 'ejs');
//use express as middleware to serve static pages
app.use('/CSS', express.static('CSS'));
app.use('/images', express.static('images'));
//create a mySQL connection
var mysql = require('mysql');
var connection = mysql.createConnection({
  host: 'lyl3nln24eqcxxot.cbetxkdyhwsb.us-east-1.rds.amazonaws.com',
  user: 'ywbcssm5p26emy7n',
  password: 'e5pefjwpos1k1i17',
  port: '3306',
  database: 'nirzj0shn94smawo'
});
connection.connect(function(err) {
  if (err) throw err;
  console.log('Connected to mysql database.');
});
//sets ports that the server will lisen to
const host = '0.0.0.0';
const port = process.env.PORT || 5000;
app.listen(port, host, function(){
  console.log("Express server listening on port %d in %s mode", this.address().port, app.settings.env);
});
//provide favicon ico
var favicon = require('serve-favicon');
app.use(favicon('favicon.ico'));

//////////////////// FUNCTIONALITY ////////////////////

//sets index as the default page
app.get('/', function (req, res) {
  res.render('index');
});
//serve specified pages
app.get('/:page', function (req, res) {
  res.render(req.params.page);
});
//submits data to the rec page
app.post('/rec', urlencodedParser, function (req, res) {
  console.log(req.body);
  res.render('rec', {data: req.body});
  //variables for data
  var numRecs = req.body.numRecs;
  var minYear = req.body.minYar;
  var maxYear = req.body.maxYear;
  var minVotes = req.body.minVotes;
  var maxVotes = req.body.maxVotes;
  var director = req.body.director;
  var studio = req.body.studio;
  //splits genres and themes into arrays
  var genres = req.body.genres.split(",").map(function(item) {return item.trim()});
  genres = genres.filter(function (genre) {return genre != ''});
  var themes = req.body.themes.split(",").map(function(item) {return item.trim()});
  themes = themes.filter(function (theme) {return theme != ''});

  //initial query string for the db
  var dataQuery = 'SELECT * FROM anime';
  var dataPromise = new Promise((resolve, reject) => {
    connection.query(dataQuery, function (err, result) {
      if (err) reject(err);
      else resolve(result);
    })
  });

  dataPromise.then((result) => {
    console.log(result);
    var animePRomises = [];
    for (var i = 0; i < result.length; ++i) {
      var currAnime = result[i];
      //console.log(currAnime.anime);
      var currPromise = getData(currAnime.anime);
      currPromise.then(function(data){
        var animeDirector = data[0][0].director;
        var animeStudio = data[1][0].studio;
        var animeGenres = [];
        for (var i = 0; i < data[2].length; ++i)
          animeGenres.push(data[2][i].genre);
        var animeThemes = []
        for (var i = 0; i < data[3].length; ++i)
          animeThemes.push(data[3][i].theme);
        var animeObject = new Anime(currAnime.anime, currAnime.rating, currAnime.votes, currAnime.date, animeDirector, animeStudio, animeGenres, animeThemes);
        console.log(animeObject);
      });
    }
  });
});//end rec page submission code

//////////////////// QUERY ANIME DATA ////////////////////

function getData(anime) {
  var promises = [];
  promises.push(getDirector(anime));
  promises.push(getStudio(anime));
  promises.push(getGenres(anime));
  promises.push(getThemes(anime));
  return Promise.all(promises);
}
function getDirector(anime){
  return new Promise((resolve, reject) => {
    connection.query(directorQuery, anime, function (err, result) {
      if (err) reject(err);
      else resolve(result);
    });
  });
}
function getStudio(anime) {
  return new Promise((resolve, reject) => {
    connection.query(studioQuery, anime, function (err, result) {
      if (err) reject(err);
      else resolve(result);
    });
  });
}
function getGenres(anime) {
  return new Promise((resolve, reject) => {
    connection.query(genreQuery, anime, function (err, result) {
      if (err) reject(err);
      else resolve(result);
    });
  })
}
function getThemes(anime) {
  return new Promise((resolve, reject) => {
    connection.query(themeQuery, anime, function (err, result) {
      if (err) reject(err);
      else resolve(result);
    });
  })
}
var directorQuery = 'SELECT directors.director FROM directors ' +
    'INNER JOIN anime_directors ON directors.id = anime_directors.director_id ' +
    'INNER JOIN anime ON anime.id = anime_directors.anime_id ' +
    'WHERE anime.anime = ?';
var studioQuery = 'SELECT studios.studio FROM studios ' +
    'INNER JOIN anime_studios ON studios.id = anime_studios.studio_id ' +
    'INNER JOIN anime ON anime.id = anime_studios.anime_id ' +
    'WHERE anime.anime = ?';
var genreQuery = 'SELECT genres.genre FROM genres ' +
    'INNER JOIN anime_genres ON anime_genres.genre_id = genres.id ' +
    'INNER JOIN anime ON anime.id = anime_genres.anime_id ' +
    'WHERE anime.anime = ?';
var themeQuery = 'SELECT themes.theme FROM themes ' +
    'INNER JOIN anime_themes ON anime_themes.theme_id = themes.id ' +
    'INNER JOIN anime ON anime.id = anime_themes.anime_id ' +
    'WHERE anime.anime = ?';

//////////////////// ANIME CLASS ////////////////////

class Anime {
  constructor(name, rating, votes, date, director, studio, genres, themes) {
    this.name = name;
    this.rating = rating;
    this.votes = votes;
    this.date = date;
    this.director = director;
    this.studio = studio;
    this.genres = genres;
    this.themes = themes;
  }

  determineScore(min_votes, max_votes, min_year, max_year, genres, themes, director, studio) {
    if (!isInRange(min_votes, max_votes, min_year, max_year)) {
      return -1;
    }
    var score = 0;

  }

  isInRange(min_votes, max_votes, min_year, max_year) {
    if (this.votes < min_votes || this.votes > max_votes) {
      return false;
    }
    if (this.date == null) {
      return true
    }
    if (this.date < min_year || this.date < max_year) {
      return false;
    }
    return true;
  }
}


//////////////////// TEST AREA ////////////////////




//////////////////// OLD CODE ////////////////////

//create a variable for the server
//var server = require('http').Server(app)//

//avoid favicon.ico requests
//app.get('/favicon.ico', (req, res) => res.status(204));

/*
//loads the http module into a variable
var http = require('http');
//loads the fs module to read and write files
var fs = require('fs');
//creates a server
var server = http.createServer(function(req, res) {
  //lets the client know the data is text/html
  res.writeHead(200, {'Content-Type': 'text/html'});
  //reads index.html file (potentially in multiple buffers)
  var readStream = fs.createReadStream(__dirname + '/index.html', 'utf8');
  //pipes the buffer data to the response
  readStream.pipe(res);
});
//sets the default page to the homepage
app.get('/', function(req, res) {
  res.sendFile(__dirname +  '/index.html');
});
//sets the page depending on the page specified
app.get('/:page', function(req, res) {
  res.sendFile(__dirname + '/' + req.params.page + '.html');
});
//server listens on port 3000
server.listen(3000);
console.log('now listening on port 3000');
*/