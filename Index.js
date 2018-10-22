/*var express = require('express');
var app = express();
app.get('/', function(req, res) {
  res.send('Hello World!');
})

app.listen(3000, function() {
  console.log('Example app listening on port 3000!');
});*/

//loads the http module into a variable
var http = require('http');
//loads the fs module to read and write files
var fs = require('fs');
//creates a server
var server = http.createServer(function(req, res) {
  console.log('wtf');
  console.log('request was made at: ' + req.url);
  //lets the client know the data is text/html
  res.writeHead(200, {'Content-Type': 'text/html'});
  //reads index.html file (potentially in multiple buffers)
  var readStream = fs.createReadStream(__dirname + '/index.html', 'utf8');
  //pipes the buffer data to the response
  readStream.pipe(res);
});

//server listens on port 3000
server.listen(3000);

console.log('now listening on port 3000');
            