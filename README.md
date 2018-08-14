# Market Trade App

### Quick note: To try out the app, download the jar file and run: java -jar trade.jar and then head over to your browser and go to localhost:4567/frontend. You can also post to /trade and get from /trade (the GET will fetch the list of trade messages).

The purpose of this project was to implement in a couple of hours the backend and frontend, in Java, for a Market Trade Processor.
The Market Trade Processor should be able to:

a) Consume a trade message sent by POST on a given endpoint;

b) Process those messages and send them to a given endpoint;

c) Render the list of the trade messages sent to the web services on the Frontend.

## Design Choices

Given the time constraints to implement this in Java, I have used the Spark Java microframework along with Velocity Templating Engine, to create a simple MVC application.

The Spark Java microframework allows for the fast development of RESTful web services, being very lightweight in terms of configuration and prep time.

The Velocity Templating Engine is an essential part of the separation of concerns for MVC. It is able to render the View (templates written in VTL) based on a Model (a map representing the data model) passed to it by the Controller (the logic in our Java application written with the help of the Spark framework).

The trade messages are stored as plain java objects on a regular file. No database was used as it was only necessary to save one table of information. This also helped reduce the time needed to spend in the development of the application.

As everything is embedded into the application (the Application Server, the templating engine and the "database" file) the deployment of the application is trivial.

## Architechture

There are 3 main packages on the app:
- lindacare.com.DAO;
- lindacare.com.Trading;
- lindacare.com.ws.

The DAO (data access object) is responsible for saving and loading the trade messages onto the "database". As this is abstracted into a separate class, eventually the interface could be maintained and the underlying implementation could use a database instead.

The Trading package has the Trade class that represents each single trade message. It also has a method to transform the Trade object instance into a JSON object.

The Main class is the controller of the application. It is responsible for creating the web services to collect the trade messages, handles the logic of the application and sends the model information to the view for rendering by the Velocity Templating Engine. As everything is embedded into the application, you just need to run this file as a normal Java application and everything will work accordingly.

## Possible improvements

- The security of the application is lacking. The inputs received at the endpoint should be sanitized to prevent exploitation (e.g. XSS and Stored XSS).

- The messages could be processed to extract information from it (e.g. analysing trends, volume of messages from one particular currency pair market, etc);

- Trade markets are often required to be viewed in real-time. Real-time data could be sent over Web Sockets and rendered at the frontend;

- The frontend could display the text info in a more appealling manner;

- The frontend could display several metrics, including historical graphs of currency pairs, trends, real-time visualization of trades, etc.
