# Chatbot Game v2

![Project Video](url)

>Client-Server java chatbot to play a game. The client side has a GUI, and communicates with the server to play this game.

---

## Table of Contents

- [Description](#description)
- [Project Development and Working](#project-development-and-working)
- [Improvements](#improvements)
- [Data Source](#data-source)
- [License](#license)
- [Author Info](#author-info)

---

## Description

This application is development on the [Chatbot v.1](https://github.com/taher-mulla/Chatbot-Game-v.1.git). I have improved this to be a client-server application, here the client machine is operated by the user who plays the game. This client will communicate with the server and will be given the questions, answers and score. I am using a database that has the movie name and character name in a .txt file. The questions and answers are taken from here. This project should be looked as a demonstration or example of how to write a client-server application, exchange data between them, etc. To look at or understanding individual parts of this project such as client-server application, threads, using a database, javafx, etc work, pls refer to my repository [here](https://github.com/taher-mulla/Java.git). You can see codes from all different java topics here. 

[Back To The Top](#chatbot-game-v2)

---

#### Technologies

- Thread Management
- Sockets
- JavaIO
- JavaFx
- JavaNIO
- Basic Java

[Back To The Top](#chatbot-game-v2)

---

## Project Development and Working

#### Data Base

  The database file is only used by the server, the file is a .txt file with involves 9,035 characters from 617 movies. 
  - character metadata included:
  	  - gender (for 3,774 characters)
  	  - position on movie credits (3,321 characters)

  [Data File---TO PUT]()

  - Data Pre-processing 
    
    The original delimiter used was ' +++$+++ '. This delimiter could not be used as the split function uses regular expressions, and could not accept this. The delimiter was changed to ';'

#### User Interface (Client) 

  The user interface is made using JavaFX. The top-level is a BorderPane, we use the top, center, and bottom of this view in the interface. 

  - Top

	  The top used and created in the controller, is the only part of the interface that has not been coded in the fxml file. It is used to keep the score and has a VBox in it for the user and bot scores. The TextFields are updated every time the score changes through the controller. 

  - Center 

	  The TextArea here is used for the conversation messages, it is appended here every time the bot or used sends a message.

  - Bottom

 	  Here a BorderPane is used with a TextField and  Button. 

#### Working

- Client Side
 
  The client has a GUI for better user interaction. The fx-contorller initialize() method displays the initial message and connects with the server. After this the server will determine whose turn it is and will accordingly send a question to the client or will ask the client to sent a question to the server. The send button in the GUI will send the typed string to the server, and the server will give the appropriate reply. 
  
  Every string sent from the server to the client has 4 parts. Using this string the server tells the client: who has sent the message, the message content, the server's score and the bot's score. This string is one example of how the client and server can exchange data and communicate. 
  
  Pls refer to the client src code [here](Client/src/sample/).

- Server Side

  The server does not need a GUI and hence has none. The server is always ON and waits for clients to connect and will be waiting and listening for connections even after all clients disconnect. Here the server keeps track of the turn and the score. The server also has the database, and is responsible for asking the questions to the client and checking the answers received from the client. The server has 2 methods to function, one that will find the movie from a given character name, and second will check if the given character and movie.Pls refer to the server src code [here](Server/src/).


[Back To The Top](#chatbot-game-v2)

---

## Improvements

The application UI can be improved by having a multiple possible answers from the server which can be randomly used. The client name should also be changeable to the users name. Lastly we should also be able to send a full sentence to the server. This can be done using NPL and finding the proper nouns fro the statement sent.

[Back To The Top](#chatbot-game-v2)

---

## Data Source

The data was taken from Kaggle and can be found [here.](https://www.kaggle.com/fungusamongus/chatbot-data)

[Back To The Top](#chatbot-game-v2)

---

## License

MIT License

Copyright (c) [2017] [Taher H Mulla]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

[Back To The Top](#Chatbot-game-v2)

---

## Author Info

 - [Linkdin](www.linkedin.com/in/taher-mulla-8b9546136) 

 - [GitHub](https://github.com/taher-mulla)

 - Email - taher.mulla@gmail.co

[Back To The Top](#chatbot-game-v2)

