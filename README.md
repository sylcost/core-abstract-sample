# Explanations
1. 3 entities : Shoe, Shop and Stock.
   1. Shoe
      1. id
      2. name
      3. color
      4. size
   2. Shop
      1. id
      2. name
   3. Stock
      1. shop_id
      2. shoe_id
      3. quantity
2. The Shoe DTO have been slightly modified :
   1. Add an id attribute, useful for patching the stock.
   2. Change the location of the Color enum.
3. Use of docker to spawn a postgres db : init scripts are in ./sql folder
   1. DB can be accessed at jdbc:postgresql://localhost:5432/
   2. username : postgres
   3. password : postgres
4. 2 PATCH methods :
   1. Full : replace all the stock of the shop.
   2. Unitary : add or replace the stock for the given shoe.
5. Tests can be run with `mvn clean test`

# How to

1. Install docker-compose https://docs.docker.com/compose/install/
2. Make sure the SQL files are readable by docker-compose : `chmod 664 sql/*`
3. Start postgres db `docker-compose up`
4. Start application (java 17 might be needed)
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
   1. Full (replace all the stock, throw Exception if quantity > 30) : `/stock/shop/{shopId}`
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
   2. Unitary (add or replace the quantity of one shoe, throw exception if quantity > 30)
   ```
   curl --location --request PATCH 'http://localhost:8080/stock/shop/1/unitary' \
   --header 'version: 1' \
   --header 'Content-Type: application/json' \
   --data-raw '{
       "shoeId": 9,
       "quantity": 5
   }'
   ```


# TODO / missing features
- Test for unitary patch.
- Data validation on incoming request.
- In PATCH, throw Exception if shoe or shop does not exist
