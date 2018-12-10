const animeFile = require("./anime.js");
const pq = require("./PriorityQueue.js");

//function to get a priority queue of anime objects
function getPQ(req) {
  return new Promise((resolve, reject) => {
    //gets an array of anime objects
    var aniRecs = animeFile.getAniArray(req);
    aniRecs.then(data => {
      const aniQueue = new pq((a, b) => a.score > b.score);
      //pushes each anime object into a priority queue
      data.forEach(anime => {
        aniQueue.push(anime);
      });
      //attempts to resolve priority queue
      if (aniQueue.size() == 0)
        reject("Anime priority queue empty. File: page_rec.js");
      else
        resolve(aniQueue);
    })
  })
}

//function to return an object with 2 fields:
// 1. aniArray: array of sorted anime objects
// 2. params: request (parameter) body
module.exports.getData = function(req) {
  return new Promise((resolve, reject) => {
    //get promise for priority queue of anime objects
    var pqPromise = getPQ(req);
    pqPromise.then(pq => {
      var aniArray = [];
      //pushes the correct amount of anime objects into an array
      for (var i = 0; i < req.body.numRecs && pq.size() > 0; ++i) {
        aniArray.push(pq.pop());
      }
      var data = {};
      //add array of sorted anime objects to request body
      data.aniArray = aniArray;
      //add request body to return object
      data.params = req.body;
      //resolve the return object as a promise
      resolve(data);
    })
  });
}