//////////////////// VARIABLES ////////////////////

//use express
var express = require('express');
//variable to use express
var app = express();
//use the body-parser middleware to handle post data
var bodyParser = require('body-parser');

//////////////////// SETUP ////////////////////

//tells express to use ejs as the view/template engine
app.set('view engine', 'ejs');
//needed for body-parser(parse application/x-www-form-urlencoded)
app.use(bodyParser.urlencoded({ extended: false }));
//use express as middleware to serve static CSS pages
app.use('/CSS', express.static('CSS'));
app.use('/images', express.static('images'));

//////////////////// FUNCTIONALITY ////////////////////

//serve specified pages
app.get('/compare_rec', function (req, res) {
  res.render('compare_rec');
});