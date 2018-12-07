const db = require("./db.js");

//////////////////// FUNCTION TO RETURN ALL NECESSARY DATA AT ONCE ////////////////////

module.exports.getData = function(param) {
  return new Promise((resolve, reject) => {
    // Query mysql for all directores, studios, genres, themes
    var autocompleteData = new Promise((res, rej) => {
      db.getConnection.then(mysql => {
        mysql.query(db.searchQuery, function (error, result) {
          if (error) reject("page_search.js: " + error);
          else res(result);
        })
      })
    });
    // Query mysql for data relating to particular anime input
    var aniData = new Promise((res, rej) => {
      db.getConnection.then(mysql=> {
        var titleArray = param.titles.split(",").map(item => item.trim());
        var query = db.buildSearchQuery(titleArray);
        //console.log(query);
        if (query == null)
          res("");
        else {
          mysql.query(query, function (error, result) {
            if (error) reject("page_search.js: " + error);
            else {
              //console.log(result);
              res(result);
            }
          })
        }
      })
    });
    // Gets non-repeating data from query response
    var formData = new Promise((res, rej) => {
      var dataArray = [];
      aniData.then(data => {
        dataArray.directors = getDirectors(data);
        dataArray.studios = getStudios(data);
        dataArray.genres = getGenres(data);
        dataArray.themes = getThemes(data);
        res(dataArray);
      })
    });
    // Create array of promises to wait on data
    var promises = [];
    promises.push(autocompleteData);
    promises.push(formData);
    // Resolves all daya
    Promise.all(promises).then(searchData => { 
      var data = searchData[0];
      var formData = searchData[1];
      var response = {};
      // Data to prefill form
      var titleArray = param.titles.split(",").map(item => item.trim())
      titleArray.remove('');
      response.formTitles = createStringFromArray(titleArray)
      response.formDirectors = createStringFromArray(formData.directors);
      response.formStudios = createStringFromArray(formData.studios);
      response.formGenres = createStringFromArray(formData.genres);
      response.formThemes = createStringFromArray(formData.themes);
      //Data for autocomplete functionality
      // Array of directors
      response.directors = [];
      for (var i = 0; i < data.length; i++) {
        if (data[i].director == null)
          break;
        response.directors.push(data[i].director);
      }
      // Array of studios
      response.studios = [];
      for (var i = 0; i < data.length; i++) {
        if (data[i].studio == null)
          break;
        response.studios.push(data[i].studio);
      }
      // Array of genres
      response.genres = [];
      for (var i = 0; i < data.length; i++) {
        if (data[i].genres == null)
          break;
        response.genres.push(data[i].genres);
      }
      // Array of themes
      response.themes = [];
      for (var i = 0; i < data.length; i++) {
        if (data[i].themes == null)
          break;
        response.themes.push(data[i].themes);
      }
      
      //send data back to client
      resolve(response);
    })
  })
}

//////////////////// HELPER FUNCTION TO RETURN NON-REPEATING DATA ////////////////////

function getDirectors(data) {
  var array = [];
  for (var i = 0; i < data.length; ++i) {
    if (array.indexOf(data[i].director) == -1)
      array.push(data[i].director);
  }
  return array;
}

function getStudios(data) {
  var array = [];
  for (var i = 0; i < data.length; ++i) {
    if (array.indexOf(data[i].studio) == -1)
      array.push(data[i].studio);
  }
  return array;
}

function getGenres(data) {
  var array = [];
  for (var i = 0; i < data.length; ++i) {
    var genres = data[i].genres.split(",");
    for (var j = 0; j < genres.length; ++j) {
      if (array.indexOf(genres[j]) == -1)
        array.push(genres[j]);
    }
  }
  return array;
}

function getThemes(data) {
  var array = [];
  for (var i = 0; i < data.length; ++i) {
    var themes = data[i].themes.split(",");
    for (var j = 0; j < themes.length; ++j) {
      if (array.indexOf(themes[j]) == -1)
        array.push(themes[j]);
    }
  }
  return array;
}

//////////////////// OTHER HELPER FUNCTIONS ////////////////////

// Helper function to one string from an array of strings
function createStringFromArray(array) {
  if (array.length == 0)
    return '';
  var string = array[0];
  for (var i = 1; i < array.length; ++i) {
    string = string.concat(', ' + array[i]);
  }
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