couch-bench
===========

Reproduction of the [NodeJS vs J2EE benchmark]{http://java.dzone.com/articles/performance-comparison-between} used
as a demonstration in the following [article]{http://blog.octo.com/lart-du-benchmark/}.

The setup uses two machines, an injector and a server. I will provide a Vagrant file for them in the `vm` directory when I have time.

As of now, here is how to setup the machines to reproduce the benchmark.

Base
--------
We start with the same setup for both VMs. You can copy the vagrant directory to have two identical VMs.
~~~bash
# Create the vagrant box
mkdir injector
cd injector
vagrant init precise64 http://files.vagrantup.com/precise64.box
vagrant up
vagrant ssh
# Upgrade the VM
sudo apt-get update
sudo apt-get upgrade
# Install Java and Git
sudo apt-get install openjdk-7-jdk
sudo apt-get install git
# Install Maven
wget http://mirror.its.dal.ca/apache/maven/maven-3/3.1.1/binaries/apache-maven-3.1.1-bin.tar.gz
tar xvf apache-maven-3.1.1-bin.tar.gz
echo "export PATH=$PATH:$HOME/apache-maven-3.1.1/bin">>.bashrc
# Get the project
git clone https://github.com/henri-tremblay/couch-bench.git
cd couch-bench
mvn -N install
# Exit and copy
exit
vagrant suspend
cd ..
cp -R injector server
~~~

Server
------
~~~bash
# Connect to the server
cd server
vagrant up
vagrant ssh
# Install couchdb
sudo apt-get install couchdb
sudo apt-get install curl
curl -X PUT http://127.0.0.1:5984/testdb
# Install couchdb4j
git clone https://github.com/mbreese/couchdb4j.git
cd couchdb4j
mvn install
# Install Tomcat
sudo apt-get install tomcat7
# Install the war
cd ../couch-bench/server
mvn install
cp target/couch-bench.war /var/lib/tomcat7/webapps
# Fill the server with data (could take a while)
curl http://localhost:8080/couch-bench/inject
# Exit
exit
cd ..
~~~

Injector
--------
~~~bash
# Connect to the injector
cd injector
vagrant up
vagrant ssh
# Launch the injection
cd couch-bench/injector
mvn install
~~~

Run
---

