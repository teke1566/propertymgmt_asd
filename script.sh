./mvnw clean
./mvnw package

docker built -t propertyManagement .

docker-compose up -d