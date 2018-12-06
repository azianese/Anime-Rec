var db = require("./db.js");

//function to get an array of anime directors
module.exports.getDirectorArray = function() {
  return new Promise((resolve, reject) => {
    //promise for basic anime themes from database
    var dataPromise = new Promise((resolve, reject) => {
      db.getConnection.then(mysql => {
        mysql.query(db.directorQuery, function (err, result) {
          if (err) reject(err);
          else resolve(result);
        })
      })
    })
    //returns promise for array of only anime directors
    dataPromise.then(result => {
      var array = [];
      for (var i = 0; i < result.length; ++i) {
        array.push(result[i].director);
      }
      if (array.length == 0) {
        reject("anime directors could not be found");
      }
      else {
        resolve(array);
      }
    })
  })
}

//function to get an array of anime studios
module.exports.getStudioArray = function() {
  return new Promise((resolve, reject) => {
    //promise for basic anime themes from database
    var dataPromise = new Promise((resolve, reject) => {
      db.getConnection.then(mysql => {
        mysql.query(db.studioQuery, function (err, result) {
          if (err) reject(err);
          else resolve(result);
        })
      })
    })
    //returns promise for array of only anime studios
    dataPromise.then(result => {
      var array = [];
      for (var i = 0; i < result.length; ++i) {
        array.push(result[i].studio);
      }
      if (array.length == 0) {
        reject("anime studios could not be found");
      }
      else {
        resolve(array);
      }
    })
  })
}

//function to get an array of anime genres
module.exports.getGenreArray = function() {
  return new Promise((resolve, reject) => {
    //promise for basic anime genres from database
    var dataPromise = new Promise((resolve, reject) => {
      db.getConnection.then(mysql => {
        mysql.query(db.genreQuery, function (err, result) {
          if (err) reject(err);
          else resolve(result);
        })
      })
    })
    //returns promise for array of only anime genres
    dataPromise.then(result => {
      var array = [];
      for (var i = 0; i < result.length; ++i) {
        array.push(result[i].genre);
      }
      if (array.length == 0) {
        reject("anime genres could not be found");
      }
      else {
        resolve(array);
      }
    })
  })
}

//function to get an array of anime themes
module.exports.getThemeArray = function() {
  return new Promise((resolve, reject) => {
    //promise for basic anime themes from database
    var dataPromise = new Promise((resolve, reject) => {
      db.getConnection.then(mysql => {
        mysql.query(db.themeQuery, function (err, result) {
          if (err) reject(err);
          else resolve(result);
        })
      })
    })
    //returns promise for array of only anime themes
    dataPromise.then(result => {
      var array = [];
      for (var i = 0; i < result.length; ++i) {
        array.push(result[i].theme);
      }
      if (array.length == 0) {
        reject("anime themes could not be found");
      }
      else {
        resolve(array);
      }
    })
  })
}