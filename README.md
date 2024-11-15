# Blackjack Reactive REST API
This project is a **Spring Boot** application that allows Blackjack games creation and making moves.

It is fully reactive using Spring **WebFlux**.
It has two microservices, one that handles the games and uses **MongoDB**,
and another one that manages the player ranking and uses **MySQL** (via **R2DBC**).
There's testing implemented for the player microservice (using **JUnit** and Mockito).
Additionally, **Swagger** is used for automatic API documentation.

## Technologies used
- R2DBC (Reactive Relational Database Connectivity)
- MySQL (for managing players)
- MongoDB (for game persistence)
- Spring WebFlux (reactive programming)
- Swagger/OpenAPI (for API documentation)
- Java 21
            
## Installation and configuration

### Requirements
- Java 21
- Maven 4.0+
- MySQL and MongoDB installed

### Steps
#### 1. <u>Clone repo and install dependencies</u>
Clone this repo to your computer.  
This is a **maven** project, so run the following command to install the project dependencies: `mvn clean install`.  

#### 2. <u>Database configuration</u>
- #### MySQL 
Notice that the default port is 3306; you can manually change it in the application.properties file:  
`spring.r2dbc.url=r2dbc:mysql://localhost:<DESIRED_PORT>/blackjack`   

Configure MySQL credentials in the application.properties file:
```
spring.r2dbc.username=your_username
spring.r2dbc.password=your_password
```
Start the MySQL server at the specified port and create a database named `blackjack`.
- #### MongoDB
MongoDB should be running locally.
Collections will be automatically generated by MongoDB, so no manual setup is required.

#### 3. <u>Run the project</u>
To run the application, use the following command: `mvn spring-boot:run`

The API will be available at http://localhost:8080.
                                       
### API Documentation
Once the application is running, you can access the Swagger documentation at:  
http://localhost:8080/swagger-ui.html

## Endpoints
- ### Create a new game
**URL:** `/game/create`  
**Method:** `POST`  
**Description:** Creates a new game with one player.
**Request Body (JSON):**
```json
"Player1"

```
- ### Get game details
**URL:** `/game/{id}`  
**Method:** `GET`  
**Description:** Retrieves details of a game by its ID.
**Path Parameter:** `id` (string): The unique identifier of the game.

- ### Make a move
**URL:** `/game/{id}/play`  
**Method:** `POST`  
**Description:** Submits a move in the game for the player whose turn is active. Allowed moves: `"HIT"` and `"STAND"`.  
**Path Parameter:** `id` (string): The unique identifier of the game.  

**Request Body (JSON):**
```json
"HIT"
```
- ### Delete a game
**URL:** `/game/delete/{id}`  
**Method:** `DELETE`  
**Description:** Deletes a game by its ID.  
**Path Parameter:** `id` (string): The unique identifier of the game.
- 
- ### Get players ranking
**URL:** `/player/ranking`  
**Method:** `GET`  
**Description:** Retrieves the ranking of users, sorted by score.

**Response (200 OK):**
```json
[
  {
    "id": 3,
    "nickname": "Player3",
    "score": 10,
    "cardGamesWon": 1,
    "cardGamesLost": 0,
    "cardGamesDraw": 0
  },
  {
    "id": 5,
    "nickname": "Player1",
    "score": -10,
    "cardGamesWon": 0,
    "cardGamesLost": 1,
    "cardGamesDraw": 0
  },
  ...
]
```
- ### Change a player's name
**URL:** `/player/editnickname/{id}`  
**Method:** `PUT`  
**Description:** Changes the name of a user by their player ID.  
**Path Parameter:** `id` (Long): The unique identifier of the player.
**Request Body (JSON):**
```json
"new nickname"