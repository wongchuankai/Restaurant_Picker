# Restaurant Picking API
A Spring Boot backend service that allows predefined users to create sessions, collect restaurant suggestions, and randomly select a final restaurant.

## Features
- Create a restaurant voting session (predefined users only)
- Generate secure session token
- Add restaurants to an open session
- Lock session and randomly pick a restaurant (First Submitter only)
- Retrieve session and restaurant details

## Getting Started

### Prerequisites

- Java 17 installed
- Maven 3.6+ installed
- Docker (optional, if running via Docker)
- Git (optional, to clone repository)
- `predefinedUser.csv` in resources folder - A list of pre-defined users will be loaded upon program startup.
alice, bob, charlie, david are current pre-defined users.


### Build with Maven

Compile and package the application using Maven:

````
mvn clean install
````

### Run the application

Run locally
````
mvn spring-boot:run
````

or run the packaged JAR:
````
java -jar target/restaurant-picker-app-0.0.1-SNAPSHOT.jar
````

Application runs at:
````
http://localhost:8080
````
---
### Run with Docker

Build the Docker image:

````
docker build -t restaurant-picker-app .
````

Run the container:
```
docker run -p 8080:8080 restaurant-picker-app
```

## API Endpoints

### Create Session (Host Only)

Creates a new session. Only predefined users can create a session.

`POST /sessions`

Request body:
````
{
    "userId": "u101"
}
````

Response:

````
{
    "sessionToken": "NVXMEz",
    "hostUser": "u101",
    "status": "OPEN",
    "createdAt": "2026-01-28T16:36:01.370447400Z"
}
````

---
### Get Session by session Token

Retrieves session details by session token

`GET /sessions/{sessionToken}`

Response:
````
{
    "sessionToken": "NVXMEz",
    "hostUser": "u101",
    "status": "OPEN",
    "createdAt": "2026-01-28T16:37:55.650394Z"
}
````

### Join Session

User can join session

`POST /sessions/{sessionToken}/join`

Request body:
````
{
    "username": "guest 1"
}
````
---
### Submit Restaurant

Adds a restaurant to the specified session. Only allowed while the session is OPEN

`POST /sessions/{sessionToken}/restaurants`


Request body:
````
{
    "restaurantName": "Chicken rice",
    "latitude": 0.1,
    "longitude": 0.2,
    "submittedBy": "guest 1"
}
````

Response:
````
{
    "restaurantId": 1,
    "restaurantName": "Chicken rice",
    "latitude": 0.1,
    "longitude": 0.2,
    "submittedBy": "guest 1",
    "submittedAt": "2026-01-28T16:43:04.754043300Z"
}
````

---
### Get All Restaurants for a Session

Retrieves all restaurants submitted for a session.

`GET /sessions/{sessionToken}/restaurants`

Request body:
````
{
    "username": "guest 1"
}
````

Response:
````
[
    {
        "restaurantId": 1,
        "restaurantName": "Chicken rice",
        "latitude": 0.1,
        "longitude": 0.2,
        "submittedBy": "guest 1",
        "submittedAt": "2026-01-28T16:43:04.754043Z"
    }
]
````

---
### Select Random Restaurant and Lock Session

Randomly selects a restaurant from the session and locks the session. The user who initiated the session is able to end the session

`POST /sessions/{sessionToken}/end`

Request body:
````
{
    "userId": "u101"
}
````

Response:
````
{
    "pickedRestaurant": {
        "restaurantId": 1,
        "restaurantName": "Chicken rice",
        "latitude": 0.1,
        "longitude": 0.2,
        "submittedBy": "guest 1",
        "submittedAt": "2026-01-28T16:43:04.754043Z"
    }
}
````

---
### Users in session can see the picked restaurant 

users are able to view the picked restaurant after session has ended.

`GET  /sessions/{sessionToken}/result`

Request body:
````
{
    "username": "guest 1"
}
````

Response:
````
{
    "pickedRestaurant": {
        "restaurantId": 1,
        "restaurantName": "Chicken rice",
        "latitude": 0.1,
        "longitude": 0.2,
        "submittedBy": "guest 1",
        "submittedAt": "2026-01-28T16:43:04.754043Z"
    }
}
````



