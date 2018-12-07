const db = require("./db.js");

module.exports.getAniArray = function(req) {
  return new Promise((resolve, reject) => {
    //create object to hold parameters
    //split user input for genre and themes by comma. delete white spaces.
    var params = req.body;
    var genres = params.genres.split(",").map(function(item) {return item.trim()});
    params.genres = genres.filter(function (genre) {return genre != ''});
    var themes = params.themes.split(",").map(function(item) {return item.trim()});
    params.themes = themes.filter(function (theme) {return theme != ''});

    //promise for basic anime data (name, link, rating, votes, date)
    var dataPromise = new Promise((resolve, reject) => {
      db.getConnection.then(mysql => {
        mysql.query(db.dataQuery, function (err, result) {
          if (err) reject(err);
          else resolve(result);
        })
      })
    })

    //work with data
    var animeArray = [];
    dataPromise.then(result => {
      for (var i = 0; i < result.length; ++i) {
        var anime = createAnime(result[i], params);
        anime.calcScore(params);
        animeArray.push(anime);
      }

      if (animeArray.length == 0) {
        reject("anime promise array could not be filled. File: anime.js");
      }
      else resolve(animeArray);
    })
  })
}

//Helper function to create an Anime object based on data input
function createAnime(data) {
  data.genres = data.genres.split(",");
  data.themes = data.themes.split(",");
  return new Anime(data);
}

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