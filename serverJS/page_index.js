const db = require("./db.js");

module.exports.getTitleArray = function() {
  return new Promise((resolve, reject) => {
    //promise for basic anime titles from database
    var dataPromise = new Promise((resolve, reject) => {
      db.getConnection.then(mysql => {
        mysql.query(db.nameQuery, function (err, result) {
          if (err) reject(err);
          else resolve(result);
        })
      })
    })
    //returns promise for array of only anime titles
    dataPromise.then(result => {
      var array = [];
      for (var i = 0; i < result.length; ++i) {
        array.push(result[i].title);
      }
      if (array.length == 0) {
        reject("anime titles could not be found");
      }
      else {
        resolve(array);
      }
    })
  })
}