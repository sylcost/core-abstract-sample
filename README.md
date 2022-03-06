# Explanations
1. 3 entities : Shoe, Shop and Stock.
2. The Shoe DTO have been slightly modified :
   1. Add an id attribute, useful for patching the stock.
   2. Change the location of the Color enum.
3. Use of docker to spawn a postgres db : scripts are in ./sql folder
4. The PATCH for stock replace all the stock of the shop.

# How to

1. Install docker-compose https://docs.docker.com/compose/install/
2. Make sure the SQL files are readable by docker-compose : `chmod 664 sql/*`
3. Start postgres db `docker-compose up`
4. Start application 
```
mvn clean package && java -jar controller/target/controller-1.0.jar
```
5. Make a GET request 
   1. Shop : 
   ```
   curl --location --request GET 'http://localhost:8080/shop/search?name=DECATHLON NANTES' --header 'version: 1'
   ```
   2. Shoe : 
   ```
   curl --location --request GET 'http://localhost:8080/shoes/search?color=BLACK' --header 'version: 2'
   ```
   3. Stock : 
   ```
   curl --location --request GET 'http://localhost:8080/stock/shop/1' --header 'version: 1'
   ```
6. Make a PATCH request
   1. Stock (replace all the stock, throw Exception if quantity > 30) : 
   ```
   curl --location --request PATCH 'http://localhost:8080/stock/shop/1/full' \
      --header 'version: 1' \
      --header 'Content-Type: application/json' \
      --data-raw '{
      "stocks": [
        {
          "shoeId": 3,
          "quantity": 3
        },
        {
          "shoeId": 4,
          "quantity": 21
        },
        {
          "shoeId": 5,
          "quantity": 1
        },
        {
          "shoeId": 6,
          "quantity": 0
        }
      ]
      }'
   ```


# TODO / missing features
- Unit tests, especially for the service StockCoreImpl.
- Data validation on incoming request.
- Another PATCH endpoint with partial update.
