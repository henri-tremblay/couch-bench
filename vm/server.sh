#!/bin/sh

echo "=> Updating apt"
sudo add-apt-repository -y ppa:chris-lea/node.js
sudo apt-get update -y
sudo DEBIAN_FRONTEND=noninteractive apt-get -y -o Dpkg::Options::="--force-confdef" -o Dpkg::Options::="--force-confold" dist-upgrade

echo "=> Install java and git"
sudo apt-get install openjdk-7-jdk -y
sudo apt-get install git -y

echo "=> Install couchdb"
sudo apt-get install couchdb -y
sudo apt-get install curl -y
curl -X PUT http://localhost:5984/testdb

echo "=> Install Tomcat"
sudo apt-get install tomcat7 -y

echo "=> Install NodeJS"
sudo apt-get install -y python-software-properties python g++ make
sudo apt-get install nodejs -y

echo "=> Install Maven"
if [ ! -f apache-maven-3.1.1-bin.tar.gz ]; then
  wget http://mirror.its.dal.ca/apache/maven/maven-3/3.1.1/binaries/apache-maven-3.1.1-bin.tar.gz
  tar xvf apache-maven-3.1.1-bin.tar.gz  
  echo "export PATH=$PATH:$PWD/apache-maven-3.1.1/bin">>.bashrc
else
  echo "Skipped"
fi

export PATH=$PATH:$HOME/apache-maven-3.1.1/bin

echo "=> Install couchdb4j"
if [ ! -d couchdb4j ]; then
  git clone https://github.com/mbreese/couchdb4j.git
  cd couchdb4j
  mvn install -DskipTests
	cd ..
else
  echo "Skipped"	
fi

echo "=> Install the war"
if [ ! -d couch-bench ]; then
  git clone https://github.com/henri-tremblay/couch-bench.git
  cd couch-bench
  mvn install -pl server -am
  sudo cp server/target/couch-bench.war /var/lib/tomcat7/webapps
	cd ..
else
  echo "Skipped"
fi
