var animeFile = require("./anime.js");
var pq = require("./PriorityQueue.js");

module.exports.getRecs = function(req) {
  return new Promise((resolve, reject) => {
    var aniRecs = animeFile.getAniArray(req);
    aniRecs.then(data => {
      const aniQueue = new pq((a, b) => a.score > b.score);
      data.forEach(anime => {
        aniQueue.push(anime);
      });
      if (aniQueue.size() == 0)
        reject("Anime priority queue empty. File: page_rec.js");
      else
        resolve(aniQueue);
    })
  })
}