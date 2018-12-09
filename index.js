//////////////////// SETUP ////////////////////

//variable to use express
const express = require('express');
//use express
const app = express();
//sets ports that the server will lisen to
const host = '0.0.0.0';
const port = process.env.PORT || 5000;
app.listen(port, host, function(){
  console.log("Express server listening on port %d in %s mode", this.address().port, app.settings.env);
});
//tells express to use ejs as the view/template engine
app.set('view engine', 'ejs');
//use the body-parser middleware to handle post data
const bodyParser = require('body-parser');
//create application/x-www-form-urlencoded parser
const urlencodedParser = bodyParser.urlencoded({ extended: false });

//provide favicon ico
const favicon = require('serve-favicon');
app.use(favicon('./images/favicon.ico'));
//use express as middleware to serve static pages
app.use('/CSS', express.static('CSS'));
app.use('/images', express.static('images'));

//enable jQuery
const jsdom = require('./serverJS/jsdom.js');
const $ = jsdom.jQuery;

//////////////////// GENERAL FUNCTIONALITY ////////////////////

//////////////////// Index Page ////////////////////
//sets index as the default page
app.get('/', function (req, res) {
  var indexFile = require('./serverJS/page_index.js');
  var promise = indexFile.getTitleArray();
  promise.then(aniNames => {
    res.render('page1_index', {data: aniNames});
  })
});

//////////////////// Search Page ////////////////////
app.post('/search', urlencodedParser, function (req, res) {
  var searchFile = require('./serverJS/page_search.js');
  var searchPromise = searchFile.getData(req.body);
  searchPromise.then(pageData => {
    res.render('page2_search', {data: pageData});
  })
});

//////////////////// Rec Page ////////////////////
app.post('/rec', urlencodedParser, function (req, res) {
  //res.render('rec', {data: req.body});
  var recFile = require("./serverJS/page_rec.js");
  var recPromise = recFile.getRecs(req);
  recPromise.then(aniRecs => {
    var aniArray = [];
    for (var i = 0; i < req.body.numRecs; ++i) {
      aniArray.push(aniRecs.pop());
    }
    res.render('page3_rec', {data: aniArray});
  })
});

//////////////////// Other Pages ////////////////////

/*
//serve specified pages
app.get('/:page', function (req, res) {
  res.render(req.params.page);
});
*/