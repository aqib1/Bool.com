docker build -t manacalacontainer .
### we can connect from host with port -p8081 and docker map/redirect
### this traffic to 8081 where our container is exposed
docker run -p8081:8081 manacalacontainer