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
cd couch-bench/injector
wget -O src/test/resources/data/ids.csv http://192.168.10.3:8080/couch-bench/inject/10000
~~~

Launch on J2EE
------
This will launch Gatling with a 10 seconds ramp (hardcoded), a 5 minutes duration (hardcoded) and 10 users (parameter, default is 1). 
~~~bash
vagrant ssh injector
cd couch-bench/injector
mvn gatling:execute -Dgatling.simulationClass=CouchSimulation -Dusers=10
~~~

Launch on NodeJS
------
First, launch node on the server
~~~bash
vagrant ssh server
cd couch-bench/server
npm install
nodejs main.js
~~~
Then, launch the benchmark
~~~bash
vagrant ssh injector
cd couch-bench/injector
mvn gatling:execute -Dgatling.simulationClass=CouchSimulation -Dusers=10 -DisNode=true
~~~

Other useful commands
------
To retrieve all the ids you can call `wget -O ids.csv http://192.168.10.3:8080/couch-bench/all`
