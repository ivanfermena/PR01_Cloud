#!/bin/sh

export BUCKET_NAME=practica-1.cloud.ivan-pedro
export RDS_ENDPOINT=events-db.c0kri8cmjwht.eu-west-1.rds.amazonaws.com

export REGION=eu-west-1
export RDS_DATABASE=events_db
export RDS_PASS=password
export RDS_USER=admin

java -jar -Dspring.profiles.active=production app.jar