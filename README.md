# Description

This is an stock market application developed for our final project for TBC android bootcamp. It is meant to provide real time, accurate information regarding the most relevant, up-to-date and important changes in the stockmarket, as well as give the user 
the ability to sell, buy, save and retrieve their stocks with a mock balance, register mock creditcards, keep track of their transactions and bought stocks, as well as observe the latest updates in the stock market. It is designed with clean architecture in mind,
making use of dependancy injection to build a scalable, maintainble and easy to use application with simple and clean UI for the users to enjoy. It also includes an authentication, login and reset features making it possible for users to create multiple accounts and track
their progress. 

# Contepts used 

To build the application, we have used a variety of different technologies and concepts. these include : 

- Clean Architecture with MVI architectural pattern.
- Firebase authentication for user registeration and login
- Firebase FireStore for keeping track of relevant user data
- Firebase Realtime Database for larger files associated with the user
- Firebase Messaging for push notifications
- Retrofit and OkHttp logger for network communications and debugging
- SplashScreen api for an animated icon
- Coroutines for writing and executing asyncronous code
- ViewBinding for cleaner access of views
- Dagger Hilt for dependancy injection
- DataStore preferences for keeping track of the UserId, UserSession.
- Navgraph and Navargs for seemless transfer of information between fragments
- Paging for loading larger chunks of data
- RecyclerView and ViewPager for beautiful scrollable designs
- Kotlin Serialization
- Glide for image loading
- Room DataBase for local storage
- MPAndroidChart for drawing and displaying charts

# API used

//guga 

# Features 

## Authentication 

For authentication our application uses firebase for keeping track of the users currently authenticated, and uses datastore to keep track of the currently signed in user using their unique identifier provided by firebase. 

![image](https://github.com/kalkudin/Stock_Market_App/assets/117531275/e9d57080-8c79-4fc2-b514-f12f0d1c7aff)
![image](https://github.com/kalkudin/Stock_Market_App/assets/117531275/94216d6d-0a4a-499b-a33a-5058fdf555a7)
![image](https://github.com/kalkudin/Stock_Market_App/assets/117531275/601bf095-c792-4ee6-83e3-65968f52a26b)
![image](https://github.com/kalkudin/Stock_Market_App/assets/117531275/6d9c7df3-9034-4fe5-a19e-9e69807f84f6)

the application includes a home page, a login page, a registration page and a page for resetting the password in case the user forgets. 

## Stocks 

Once the user logs in they will be met with the home page. this page contains some basic information about their funds, their name, as well as the current hot topic when it comes to investing like best performing, worst performing and other stocks.

![image](https://github.com/kalkudin/Stock_Market_App/assets/117531275/e91e6bd6-abac-43bf-8235-4425a1823272)

The news 

![image](https://github.com/kalkudin/Stock_Market_App/assets/117531275/0ece34b2-c5e1-4afb-baae-07e695eec5b3)







