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

- Mock API for list of companies - https://run.mocky.io/v3/91776aaa-ca12-4c57-8330-28ef21085895
- Mock API for best stocks - https://run.mocky.io/v3/e37f2736-d4be-477e-b7bb-91e2e4a45899
- Mock API for worst stocks - https://run.mocky.io/v3/2080748b-0af0-4e88-a1b0-1ee8c01f1e9c
- Mock API for active stocks - https://run.mocky.io/v3/1f1e5417-2aaa-4fc6-9461-9d94db1d1dd4
- API for company details - https://financialmodelingprep.com/api//api/v3/profile/{symbol}
- API for stock news - https://financialmodelingprep.com/api//api/v3/historical-chart/{interval}/{symbol}
- API for charts - https://financialmodelingprep.com/api//api/v3/fmp/articles

For variuos company listings we created mock APIs using the https://designer.mocky.io
For details, news and charting information we used APIs provided by the https://site.financialmodelingprep.com/developer/docs


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

This is also the page where the user can either proceed to add additional funds. The addition of funds is purely mock, but still requires the user to enter a mock credit. They can do this on the page responsible for the funds : 

![image](https://github.com/kalkudin/Stock_Market_App/assets/117531275/8b58e39e-6abe-4781-8f63-6001223cb247) 

To successfully add funds the user is prompted to select a creditcard by entering in the initials of a new one, or selecting one which they have already registered 

![image](https://github.com/kalkudin/Stock_Market_App/assets/117531275/8006a431-261c-4b9a-b08f-ca68e146e473)
![image](https://github.com/kalkudin/Stock_Market_App/assets/117531275/79ccb8c2-d821-4e4f-bf9a-63944bd91d80)

after selecting a card, it is now possible to add funds by entering a designated amout. After entering, the amount will be displayed on the Home page. 
Also on the Home page, the user can choose to view the assorted stocks in more details by pressing either of the view more buttons for the respective categories. this will take the user to the appropriate page. 

![image](https://github.com/kalkudin/Stock_Market_App/assets/117531275/5343b8b2-8f85-4a71-97ae-5eaa24085826)

It is also possible to press any of the stock options in question to be taken to their details page 

![image](https://github.com/kalkudin/Stock_Market_App/assets/117531275/6925ad5e-9a76-4564-aa38-be8d9be993d7)
![image](https://github.com/kalkudin/Stock_Market_App/assets/117531275/a513c123-0ad8-4df4-a506-e6da74152689)

This page offers the performance of the stock over a selected time perioid, the user can add the stock to their watchlist, or buy and sell the stocks accordingly. The buying and selling happens with the mock funds which the user transfers to their account.

## Protfolio

On this page, the user can see all the stocks that they have bought. Pressing on any will of course take them to the respective stock page to perform additional operations. 

![image](https://github.com/kalkudin/Stock_Market_App/assets/117531275/747f3059-a819-43c0-a038-a0d5db87a568)

## The news 

![image](https://github.com/kalkudin/Stock_Market_App/assets/117531275/0ece34b2-c5e1-4afb-baae-07e695eec5b3)

This page is dedicated to presenting the latest and up to date news regarding the current financial market. 

##Profile 

![image](https://github.com/kalkudin/Stock_Market_App/assets/117531275/852653f9-0e60-4499-bddf-74992393f402)

![image](https://github.com/kalkudin/Stock_Market_App/assets/117531275/d614c07f-8b65-4269-9719-5c8c45cb8e05)


Here the user can set their profile picture, view their transaction history, view their watchlisted stocks which they have saved previously, and manage their credit cards. 

![image](https://github.com/kalkudin/Stock_Market_App/assets/117531275/acc9987e-e856-4ab6-aae3-f86050853b09)
![image](https://github.com/kalkudin/Stock_Market_App/assets/117531275/bd946bf6-44e0-414a-9326-dd297cff08d0)










