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

//////////////////// GENERAL FUNCTIONALITY ////////////////////





var jsdom = require("jsdom");
const { JSDOM } = jsdom;
const { window } = new JSDOM();
const { document } = (new JSDOM('')).window;
global.document = document;
var $ = jQuery = require('jquery')(window);


/*
app.get('/aniNames', function (req, res) {
  var indexFile = require('./serverJS/page_index.js');
  var promise = indexFile.getTitleArray();
  promise.then(aniNames => {
    console.log(aniNames);
    res.send(aniNames);
  })
});*/



//sets index as the default page
app.get('/', function (req, res) {
  //res.render('index');
  var indexFile = require('./serverJS/page_index.js');
  var promise = indexFile.getTitleArray();
  promise.then(aniNames => {
    //console.log(aniNames);
    res.render('index', {data: aniNames});
    //res.render('index', {data: JSON.stringify(aniNames)});
    //res.render('index', {data: JSON.stringify(JSON.stringify(aniNames))});
  })
});

//serve specified pages
app.get('/:page', function (req, res) {
  res.render(req.params.page);
});

//////////////////// REC PAGE ////////////////////
app.post('/rec', urlencodedParser, function (req, res) {
  //res.render('rec', {data: req.body});
  var recFile = require("./serverJS/page_rec.js");
  var recPromise = recFile.getRecs(req);
  recPromise.then(aniRecs => {
    var aniArray = [];
    //console.log("size: " + aniRecs.size());
    console.log("req.numRecs: " + req.body.numRecs);
    for (var i = 0; i < req.body.numRecs; ++i) {
      //console.log("peek: " + aniRecs.peek());
      aniArray.push(aniRecs.pop());
      //console.log(aniArray[aniArray.length-1]);
    }
    //console.log("length: " + aniArray.length);
    res.render('rec', {data: aniArray});
  })
});