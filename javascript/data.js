//variable to use express
var express = require('express');
//use express
var app = express();
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

//////////////////// HELPER ////////////////////

//Helper function to create an Anime object based on data input
function createAnime(data) {
  data.genres = data.genres.split(",");
  data.themes = data.themes.split(",");
  return new Anime(data);
}

//Query to get anime data
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

//////////////////// ANIME CLASS ////////////////////

//Anime object class
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
  //calculates and sets the calling anime object's score based on parameters
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