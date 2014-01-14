#!/bin/sh

echo "=> Updating apt"
sudo apt-get update -y

echo "=> Install Java and required tools"
sudo apt-get install openjdk-7-jdk -y
sudo apt-get install git -y
sudo apt-get install curl -y

echo "=> Install Maven"
if [ ! -f apache-maven-3.1.1-bin.tar.gz ]; then
  wget http://mirror.its.dal.ca/apache/maven/maven-3/3.1.1/binaries/apache-maven-3.1.1-bin.tar.gz
  tar xvf apache-maven-3.1.1-bin.tar.gz
	echo "export PATH=$PATH:$PWD/apache-maven-3.1.1/bin">>.bashrc
else
  echo "Skipped"	
fi

export PATH=$PATH:$HOME/apache-maven-3.1.1/bin

echo "=> Get the project"
if [ ! -d couch-bench ]; then
  git clone https://github.com/henri-tremblay/couch-bench.git
	cd couch-bench
  mvn install -am -pl injector
	cd ..
else
  echo "Skipped"
fi
