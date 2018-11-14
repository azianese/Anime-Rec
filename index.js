//////////////////// VARIABLES ////////////////////

//use express
var express = require('express');
//variable to use express
var app = express();
//use the body-parser middleware to handle post data
var bodyParser = require('body-parser');
//create application/x-www-form-urlencoded parser
var urlencodedParser = bodyParser.urlencoded({ extended: false });
//create a variable for the server
//var server = require('http').Server(app)//

//////////////////// SETUP ////////////////////

//tells express to use ejs as the view/template engine
app.set('view engine', 'ejs');
//use express as middleware to serve static pages
app.use('/CSS', express.static('CSS'));
app.use('/images', express.static('images'));

//////////////////// FUNCTIONALITY ////////////////////

//sets index as the default page
app.get('/', function (req, res) {
  res.render('index');
});

//serve specified pages
app.get('/:page', function (req, res) {
  res.render(req.params.page);
});

//gets data from the rec page
app.post('/rec', urlencodedParser, function (req, res) {
  console.log(req.body);
  res.render('rec', {data: req.body});
});

/*
//gets data from the custom_rec page
app.post('/custom_rec', urlencodedParser, function (req, res) {
  console.log(req.body);
  res.render('custom_rec', {data: req.body});
});

//gets data fromthe compare_search page
app.post('/compare_rec', urlencodedParser, function (req, res) {
  console.log(req.body);
  res.render('compare_rec', {data: req.body});
});
*/


//sets ports that the server will lisen to
const host = '0.0.0.0';
const port = process.env.PORT || 5000;
app.listen(port, host, function(){
  console.log("Express server listening on port %d in %s mode", this.address().port, app.settings.env);
});

//////////////////// OLD CODE ////////////////////
/*
//loads the http module into a variable
var http = require('http');
//loads the fs module to read and write files
var fs = require('fs');
//creates a server
var server = http.createServer(function(req, res) {
  //lets the client know the data is text/html
  res.writeHead(200, {'Content-Type': 'text/html'});
  //reads index.html file (potentially in multiple buffers)
  var readStream = fs.createReadStream(__dirname + '/index.html', 'utf8');
  //pipes the buffer data to the response
  readStream.pipe(res);
});

//sets the default page to the homepage
app.get('/', function(req, res) {
  res.sendFile(__dirname +  '/index.html');
});
//sets the page depending on the page specified
app.get('/:page', function(req, res) {
  res.sendFile(__dirname + '/' + req.params.page + '.html');
});

//server listens on port 3000
server.listen(3000);
console.log('now listening on port 3000');
*/