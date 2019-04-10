# card-deck

Simple card-deck server.




### API

* POST /games  
create a new game

* GET /games  
list and display information about all the games

* GET /games/{id}  
returns information about the games: id, players, nbRemainingCards, nbDecks

* DELETE /games/{id}  
delete a game and its players

* PUT /games/{id}/addDeck  
adds one or more decks to the gamedeck.  The number is decks is specified through the nbDecks query parameters and default to one.
The added decks are not shuffled nor is the gamedeck.  That means the added decks are in consecutive suit and ranks. Best practice would be to shuffle the gamedeck after adding decks to it.

* GET /games/{id}/players  
returns list of player ids within the game

* GET /games/{id}/players/{player-id}  
fetches information about the player with id=player-id within game whose id is id.  Information consists of list of cards and the id.

* POST /games/{id}/players  
Adds a new player to the game

* DELETE /games/{id}/players/{player-id}  
Deletes a player from a game

* PUT /games/{id}/players/{player-id}/deal  
Deal cards to the specified player within the specified game. The number of cards to deal is specified by the query parameter "nbCards" and defaults to 1.

* PUT /games/{id}/shuffle
Shuffles the gamedeck of the specified game.

* GET /games/{id}/scores  
Return the current of all players in the game.

* GET /games/{id}/count_suits  
Counts the number of remaining cards for each suits in the gamedeck

* GET /games/{id}/count_cards
Counts the number of remaining occcurences of each card in the gamedeck


### Requirements
* Java 8 or more recent: i have built and run with both Java 8 and Java 11
* Mvn 3.6.0 (might work on older versions but not tested)


### Quick Start

[Github link](https://github.com/alaincaron/card-deck)

* git clone https://github.com/alaincaron/card-deck.git
* cd card-deck
* mvn install
* java -jar target/carddeck-service-0.1.0.jar

You can also import the project into Eclipse or Intellij and run from the IDE.  The main class is carddeck.Application

Once started, a web server listening on port 9000 is started.  The port number can be changed by editing the file application.yaml in the home directory of the project.


### Notes & Limitations

* This is a very basic server.  There is no persistence of data.  All game data is lost after a server shutdown.
* The code is organized in 3 main layers: Controller, Service, DAO
* Controller is concerned with REST API
* Service is concerned with the actual logic of the game
* DAO is concerned with actual data (query, saving).  This layer is really a simple in-memory implementation
* Using SpringBoot and Spring Injection
* The API endpoints are defined in the class carddeck.rest.GameController

