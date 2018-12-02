//////////////////// SETUP ////////////////////

//variable to use express
var express = require('express');
//use express
var app = express();
//sets ports that the server will lisen to
const host = '0.0.0.0';
const port = process.env.PORT || 5000;
app.listen(port, host, function(){
  console.log("Express server listening on port %d in %s mode", this.address().port, app.settings.env);
});
//tells express to use ejs as the view/template engine
app.set('view engine', 'ejs');
//use the body-parser middleware to handle post data
var bodyParser = require('body-parser');
//create application/x-www-form-urlencoded parser
var urlencodedParser = bodyParser.urlencoded({ extended: false });

//provide favicon ico
var favicon = require('serve-favicon');
app.use(favicon('./images/favicon.ico'));
//use express as middleware to serve static pages
app.use('/CSS', express.static('CSS'));
app.use('/images', express.static('images'));

//sets up the mysql database connection
//var db = require("./javascript/db.js");
var animeClass = require("./javascript/anime.js");

//////////////////// GENERAL FUNCTIONALITY ////////////////////

//sets index as the default page
app.get('/', function (req, res) {
  res.render('index');
});
//serve specified pages
app.get('/:page', function (req, res) {
  res.render(req.params.page);
});

//////////////////// REC PAGE ////////////////////
app.post('/rec', urlencodedParser, function (req, res) {
  //temporary line for testing
  res.render('rec', {data: req.body});
  var animeFile = require("./javascript/anime.js");
  var aniRecs = animeFile.getAniArray(req);
  aniRecs.then(data => {
    console.log(data[499]);
  })
});

//////////////////// TEST AREA ////////////////////


//////////////////// OLD CODE ////////////////////

