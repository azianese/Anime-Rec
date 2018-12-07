//create a mySQL connection
const mysql = require('mysql');
const pool = mysql.createPool({
  host: 'lyl3nln24eqcxxot.cbetxkdyhwsb.us-east-1.rds.amazonaws.com',
  user: 'ywbcssm5p26emy7n',
  password: 'e5pefjwpos1k1i17',
  port: '3306',
  database: 'nirzj0shn94smawo'
});

module.exports.getConnection = new Promise((resolve, reject) => {
  pool.getConnection(function(err, res) {
    if (err) {
      console.log("error establishing connection to MySQL database: ")
      reject(err);
    }
    else {
      //console.log("established connection to MySQL database");
      resolve(res);
    }
  })
});

//Query to get anime data
module.exports.dataQuery = 'SELECT anime.*, directors.director, studios.studio, '+
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
  'GROUP BY anime.title '+
  'ORDER BY anime.id';

//Query to get anime data for search page
module.exports.searchQuery = 'SELECT anime.id, anime.title, '+
  'directors.director, studios.studio, '+
  'genres.genre genres, themes.theme themes '+
  'FROM anime '+
  'LEFT JOIN directors '+
  'ON anime.id = directors.id '+
  'LEFT JOIN studios '+
  'ON anime.id = studios.id '+
  'LEFT JOIN genres '+
  'ON anime.id = genres.id '+
  'LEFT JOIN themes '+
  'ON anime.id = themes.id; '

//Query to get anime names
module.exports.nameQuery = 'SELECT title FROM anime'

//Query to get anime directors
module.exports.directorQuery = 'SELECT director FROM directors'

//Query to get anime studios
module.exports.studioQuery = 'SELECT studio FROM studios'

//Query to get anime genres
module.exports.genreQuery = 'SELECT genre FROM genres'

//Query to get anime themes
module.exports.themeQuery = 'SELECT theme FROM themes'

module.exports.buildSearchQuery = function(titles) {
  var string1 = 'SELECT anime.title, directors.director, studios.studio, ' +
      'GROUP_CONCAT(DISTINCT genres.genre) AS genres, ' +
      'GROUP_CONCAT(DISTINCT themes.theme) AS themes ' +
      'FROM anime ' +
      'INNER JOIN anime_directors ON anime.id = anime_directors.anime_id ' +
      'INNER JOIN directors ON directors.id = anime_directors.director_id ' +
      'INNER JOIN anime_studios ON anime.id = anime_studios.anime_id ' +
      'INNER JOIN studios ON studios.id = anime_studios.studio_id ' +
      'INNER JOIN anime_genres ON anime.id = anime_genres.anime_id ' +
      'INNER JOIN genres ON genres.id = anime_genres.genre_id ' +
      'INNER JOIN anime_themes ON anime.id = anime_themes.anime_id ' +
      'INNER JOIN themes ON themes.id = anime_themes.theme_id ';
  // Removes blank inputs
  titles.remove('');
  if (titles.length == 0)
    return null;
  var string2 = 'WHERE anime.title = \'' + titles[0] + '\' ';
  for (var i = 1; i < titles.length; ++i) {
    string2 = string2.concat('OR anime.title = \'' + titles[i] + '\' ');
  }
  var string3 = 'GROUP BY anime.title;';
  var string = string1.concat(string2).concat(string3);
  return string;
}

// Helper function to remove blank elements from an array input
Array.prototype.remove = function() {
    var what, a = arguments, L = a.length, ax;
    while (L && this.length) {
        what = a[--L];
        while ((ax = this.indexOf(what)) !== -1) {
            this.splice(ax, 1);
        }
    }
    return this;
};