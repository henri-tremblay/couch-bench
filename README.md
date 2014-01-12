couch-bench
===========

Reproduction of the [NodeJS vs J2EE benchmark]{http://java.dzone.com/articles/performance-comparison-between} used
as a demonstration in the following [article]{http://blog.octo.com/lart-du-benchmark/}.

The setup uses two machines, an injector (using Gatling) and a server (hosting Tomcat, NodeJS and CouchDB).

Create the machines
We will use Vagrant and VirtualBox so you should have both installed on your machine. The default configuration uses 2 CPUs for the injector and 4 CPUs for the server.
cd couch-bench/vm

Prepare the database
------
From the injector, insert 10 000 entries of 4000 bytes each in CouchDB and keep the result
~~~bash
vagrant ssh injector
curl http://192.168.10.3:8080/couch-bench/inject/10000 > ids.csv
~~~

Launch on J2EE
------
~~~bash
vagrant ssh injector
TBD
~~~

Launch on NodeJS
------
TBD
