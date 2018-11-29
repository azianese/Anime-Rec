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
  console.log('--------------------request body:');
  console.log(req.body);
  console.log('--------------------end body:');
  res.render('rec', {data: req.body});
  //create object to hold parameters
  var params = req.body;
  //split user input for genre and themes by comma. delete while spaces.
  var genres = params.genres.split(",").map(function(item) {return item.trim()});
  params.genres = genres.filter(function (genre) {return genre != ''});
  var themes = params.themes.split(",").map(function(item) {return item.trim()});
  params.themes = themes.filter(function (theme) {return theme != ''});

  //promise for basic anime data (name, link, rating, votes, date)
  var dataPromise = new Promise((resolve, reject) => {
    connection.query(dataQuery, function (err, result) {
      if (err) reject(err);
      else resolve(result);
    })
  });

  //array of anime promises
  var animePromises = [];
  dataPromise.then(result => {
    for (var i = 0; i < 2/*result.length*/; ++i) {
      //console.log(result[i]);
      var anime = createAnime(result[i]);
      console.log(anime);
    }
    //console.log(result[i]);
    /*
      animePromises.push(new Promise((resolve, reject) => {
        getData(result[i]).then
        (createAnime).then
        (data => setScore(params, data)).then
        (data => {
          //console.log(data);
          resolve(data);
        });
      }));
    }
    Promise.all(animePromises).then(object => {
      console.log(JSON.stringify(object));
    });
    */
  })
});

//////////////////// HELPER FUNCTIONS ////////////////////
/*
function getData(anime) {
  //console.log(anime);
  var name = anime.anime;
  var promises = [];
  promises.push(anime.anime);
  promises.push(anime.rating);
  promises.push(anime.votes);
  promises.push(anime.date);
  promises.push(anime.director);
  promises.push(anime.studio);
  promises.push(anime.genres);
  promises.push(anime.themes);
  return Promise.all(promises);
}
*/
function createAnime(data) {
  data.genres = data.genres.split(",");
  data.themes = data.themes.split(",");
  return new Anime(data);
  /*
  var params = [];
  params['name'] = data[0];
  params['rating'] = data[1];
  params['votes'] = data[2];
  params['date'] = data[3];
  params['director'] = data[4];
  params['studio'] = data[5];
  var genres = data[6].split(",");
  params['genres'] = genres;
  var themes = data[7].split(",");
  params['themes'] = themes;
  var newAnime = new Anime(params);
  console.log(newAnime.genres);
  */
  /*
  //console.log(data);
  return new Promise(function(resolve, reject) {
    var params = [];
    params['name'] = data[0];
    params['rating'] = data[1];
    params['votes'] = data[2];
    params['date'] = data[3];
    params['director'] = data[4];
    params['studio'] = data[5];
    params['genres'] = data[6];
    params['themes'] = data[7];
    resolve(new Anime(params));
  });
  */
}

function setScore(params, anime) {
  //console.log(anime);
  return new Promise(function(resolve, reject) {
    //anime.calcScore(params);
    resolve(anime);
  });
}

//////////////////// HELPER FUNCTIONS TO GET ANIME DATA ////////////////////
/*
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
*/

var dataQuery = 'SELECT anime.*, directors.director, studios.studio, '+
    'GROUP_CONCAT(DISTINCT genres.genre) AS genres, '+
    'GROUP_CONCAT(DISTINCT themes.theme) AS themes '+
    'FROM anime '+
    'INNER JOIN anime_directors ON anime.id = anime_directors.anime_id '+
    'INNER JOIN directors ON directors.id = anime_directors.director_id '+
    'INNER JOIN anime_studios ON anime.id = anime_studios.anime_id '+
    'INNER JOIN studios ON studios.id = anime_studios.studio_id '+
    'INNER JOIN anime_genres ON anime.id = anime_genres.anime_id '+
    'INNER JOIN genres ON genres.id = anime_genres.genre_id '+
    'INNER JOIN anime_themes ON anime.id = anime_themes.anime_id '+
    'INNER JOIN themes ON themes.id = anime_themes.theme_id '+
    'GROUP BY anime.anime '+
    'ORDER BY anime.id';

/*
var genreQuery = 'SELECT genres.genre FROM genres ' +
    'INNER JOIN anime_genres ON anime_genres.genre_id = genres.id ' +
    'INNER JOIN anime ON anime.id = anime_genres.anime_id ' +
    'WHERE anime.anime = ?';
var themeQuery = 'SELECT themes.theme FROM themes ' +
    'INNER JOIN anime_themes ON anime_themes.theme_id = themes.id ' +
    'INNER JOIN anime ON anime.id = anime_themes.anime_id ' +
    'WHERE anime.anime = ?';
*/
//////////////////// ANIME CLASS ////////////////////

//anime object class
class Anime {

  //class constructor
  constructor(params) {
    this.name = params.anime;
    this.rating = params.rating;
    this.votes = params.votes;
    this.date = params.date;
    this.director = params.director;
    this.studio = params.studio;
    this.genres = params.genres;
    this.themes = params.themes;
    this.score = 0;
  }

  //calculates an anime's score based on parameters
  calcScore(params) {
    //returns -1 if the anime does not fit requirments
    if (!this.isInRange(params.min_votes, params.max_votes, 
                        params.min_year, params.max_year)) {
      this.score = -1;
      return;
    }
    //starts with the base anime rating in the score calculation
    this.score += this.rating;
    //adds a score of 5 to anime with the right director/studio
    if (this.director.toLowerCase() == params.director.toLowerCase())
      this.score += 5;
    if (this.studio.toLowerCase() == params.studio.toLowerCase())
      this.score += 5;
    //sets weight divider for each genre/theme
    var len = params.genres.length + params.themes.length;
    //sets anime object genres to lowercase for comparison
    for (var i = 0; i < this.genres.length; ++i) 
      this.genres[i] = this.genres[i].toLowerCase();
    //calculate genres score
    for (var i = 0; i < params.genres.length; ++i) {
      if (this.genres.includes(params.genres[i].toLowerCase())) {
        this.score += (10/len);
      }
    }
    //sets anime object themes to lowercase for comparison
    for (var i = 0; i < this.themes.length; ++i)
      this.themes[i] = this.themes[i].toLowerCase();
    //calculate themes score
    for (var i = 0; i < params.themes.length; ++i) {
      if (this.themes.includes(params.themes[i].toLowerCase())) {
        this.score += (10/len);
      }
    }
  }

  //helper function to check if an anime fits between vote and year parameters
  isInRange(min_votes, max_votes, min_year, max_year) {
    var date = (new Date(this.date)).getFullYear();;
    if (this.votes < min_votes || this.votes > max_votes) 
      return false;
    //temporary solution: allow NaN dates to filter through
    if (isNaN(date)) 
      return true
    if (this.date < min_year || this.date < max_year)
      return false;
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