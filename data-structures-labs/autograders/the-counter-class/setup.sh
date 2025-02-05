#!/usr/bin/env bash

# Adapted from: https://github.com/gradescope/autograder_samples/blob/master/java-mvn/setup.sh

# Note: if we use a JDK base image, we shouldn't need to install openjdk...
apt-get -y install maven bash

# The following lines are to make sure we can install packages from maven
# See https://bugs.launchpad.net/ubuntu/+source/ca-certificates-java/+bug/1396760
# and https://github.com/docker-library/openjdk/issues/19#issuecomment-70546872
apt-get install --reinstall ca-certificates-java
update-ca-certificates -f
