var http = require ('http'),
  url = require('url'),
  cradle = require('cradle'),
  c = new(cradle.Connection)(
          '127.0.0.1',5984,{cache: false, raw: false}),
  db = c.database('testdb'),
  port=8081;
 
process.on('uncaughtException', function (err) {
  console.log('Caught exception: ' + err);
});
 
http.createServer(function(req,res) {
  var id = url.parse(req.url).pathname.substring(1);
  console.log('id: ' + id);
  db.get(id,function(err, doc) {
    if (err) {
      console.log('Error '+err.message);
      res.writeHead(500,{'Content-Type': 'text/plain'});
      res.write('Error ' + err.message);
      res.end();
    } else {
      res.writeHead(200,{'Content-Type': 'application/json'});
      res.write(JSON.stringify(doc));
      res.end();
    }
  });
}).listen(port);
