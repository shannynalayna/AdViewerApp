# AdViewerApp

  My AdViewer application is a simple solution to viewing the ads pulled from the remote JSON URL. 
I utilized a feed structure to this application, as I believe that this is a useful and efficient way to browse through information. It is also a system that users are accustomed to, which will let users feel familiar with the application as soon as they begin using it. This traditional browsing format is displayed through a card-style list that encapsulates the information for the single ads. There is a general description of the ad as well as the price and location displayed for each ad. There is also a toggle checkbox that enables users to add the ad and associated information to a "favorites" list that can be viewed separately from the full list of ads. There is a button in the top corner of the app that enables users to view this list. 


__Features__

This is the first android application that I have developed and worked on by myself, therefore I am particularly proud of the fact that I have completed the task at hand! (And that it runs!) Overall, though, I do believe that the method of viewing the ad information is nicely formatted and easily understandable. I attempted to maintain proven principles of UI design that make using the application for first time users understandable. Utilizing these principles ensures that users are not put off from this application off of the pure reason that it is too difficult to use on their first time use.  


__Weak Points__

There are definitely a number of things that I certainly could have done better with this application. There were a number of features that I attempted to implement (as you may notice by the various branches) that took full days (through looking through information on how to implement and then actually attempting to integrate these features into the application) that I felt like I had to abandon because of the time constraint. 

I attempted to implement a database structure to maintaining the ad information (rather than the lists which are currently implemented) as this would have certainly made the application more efficient in terms of actually dealing with / getting / updating the data. The Android Room structure for handling databases would certainly be a more efficient means of data access, however I spent several days trying to implement this and decided that I wanted to focus on the basic functionality of the application using data structures that I know are less efficient, but I am more familiar with at this point. The actual means of accessing the database is familiar to me as I do have experience with SQL (though Room offers a certain level of abstraction over actual SQL) but I was unable to figure out how to structure the database with complex objects that I used to hold the information that was taken from the parsed JSON resource. This is definitely a fundamental piece of the app that I would work on first if I had more time.


__Further Modifications__

  The application could also certainly be more robust in terms of what it offers pertaining to the application. There was a lot of information in the JSON resource that could be held in the Ad_Block object, however I wanted to get the basic functionality down for this development test before I moved on to using the additional information on the ads. 
  This information, for example, could be used to filter the list of ads that are displayed to the user in the main activity of the application. After implementing a database structure to the application, accessing the objects based on variables (for example, ad-type, distance to the user's location, etc.) would be a lot more efficient then accessing these objects from the list structure that is currently implemented. 
  This additional information could have also been used to make each ad display (the cards format) clickable so that the user could access further information on the ads. The further information being a URL to the ad itself so that the user can interact with it further beyond simply seeing the information pertaining to the ad.

  In addition to this, I wish I would have spent some more time differentiating the "Favorites" ads view versus the main feed of the application. This would help users differentiate between the feeds, beyond the simple title change that is currently implemented. 


__Thanks for reading__

  Thank you for taking the time to look at this project. I certainly had a lot of fun developing this application. This entire process has taught me a lot about the intricacies of android development as well as shown me how much I enjoy this process. Overall, I know that this application may simply cover the basic funcationalities asked for, but I am proud that I have a finalized product that is currently usable! It was definitely a challenging test, but it has been a very enjoyable process.
  Thank you for the chance to show how much I am willing to dedicate to the projects that I am passionate to work on. At the end of this entire process, I am happy that I have gained a new pet project as well as the knowledge that this is something that I am very excited to begin working with. 
  
  _Shannyn Telander_
