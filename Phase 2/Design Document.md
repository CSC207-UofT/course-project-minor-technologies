<!-- Output copied to clipboard! -->

<!-----
NEW: Check the "Suppress top comment" option to remove this info from the output.

Conversion time: 0.627 seconds.


Using this Markdown file:

1. Paste this output into your source file.
2. See the notes and action items below regarding this conversion run.
3. Check the rendered output (headings, lists, code blocks, tables) for proper
   formatting and use a linkchecker before you publish this page.

Conversion notes:

* Docs to Markdown version 1.0β31
* Sun Dec 12 2021 16:21:46 GMT-0800 (PST)
* Source doc: Phase 2 Design Document
----->



## Project Specifications

**CSC207 Final Project**

_Submitted 05 December, 2021_

**Minor Technologies**

**Note: **Project specifications have not changed since phase 0. Design changes and differences have mostly (if not all) been with implementation.

**Authors: **Jay Wang, Philip Harker, Mudit Chandna, Raghav Banka, Alexander Tran, Domenica Vega

**Domain:** Job searching and recruitment tailored towards the user.

**Description: **An application** **to make searching for interesting, relevant jobs easier by tailoring available jobs to the user. The application will take into account the user’s skills and assets, but also their personality in an attempt to match them with the jobs that they will enjoy the most.

**Requirements:**



* Neatly organize job listings across platforms (indeed, linkedin, etc).
* Track Dates for job listing deadlines (if applicable)
* Somewhat automatic refreshing of job listings
* While program is running
* Probably have some function where user can set how often it refreshes
* Manual refresh option
* Something to identify and merge duplicate listings between platforms.
* Tracks listing status info - still up, removed, past deadline, etc.
* Ability for users to add a custom job listing (ie if they found a listing that is not on a platform the program will support)


## Design Document


### SOLID Principles

In the Score_Calculator module we make separate classes(handler_1, handler_2, and handler_3) to handle the score calculation for each attribute of the user, which follows the single responsibility principle. In future we will be extending the functionality for the calculation so to do that will be just creating new classes and not manipulating the abstract class which is in coherence with the open/closed principle.The code is also in agreement with the dependency inversion principle because all the handler classes depend on the handler abstract class which is then implemented by the handler main.

Note: in DataFormat, there is a method that could potentially be split into 2 separate methods to better adhere to the Single Responsibility Principle. Specifically LoadEntriesFromDirectory as while it doesn’t directly perform any reading and writing on it’s own, using FileIO to do so instead, FileIO potentially should have a method to return a list containing the data of each file in a directory.


### Clean Architecture

The program packages are currently separated into the different clean architecture layers. With exception to accessing Constants, layers do not directly interact with layers not adjacent to itself. For example, UserManagement controller object uses a UserDB usecase object to store instances of Users as opposed to having a collection of users as an instance variable.


### Design Patterns.

Creating Entry objects through deserialization in our program uses the Factory Design Principle. In UseCase.Factories contains Methods and objects that deserialize code. First, a map containing the entry data passed into ICreateEntry.create(), where it uses some portions of the data to determine which subclass should be created. Then, subclasses of ICreateEntry are used to create the final entry.

Any usecase objects that implements IEntryDatabase also has to implement the iterator design pattern. As these database objects will be a collection of Entry objects, it allows for iteration through the database. UserDB and JobListingDB both implement iterators.

For the Score_Calculator module, we implemented the chain of responsibility design pattern. There is a main handler class to send requests to the smaller handler that operates to calculate score on each attribute of the user. All the classes that do the calculation are extended from an abstract class. The processing of request goes on from one handler to the next till an end is reached.


### Design Diagrams

CRC Cards from phase 0 were not really used beyond being an idea of how we should implement our code, and therefore did not receive updates since phase 0.

IntelliJ Generated Class Diagrams


## Accessibility Report

As our frontend/UI had to be rushed for completion, it was primarily designed without consideration of the Principles of Universal Design.

Equitable Use



* Our frontend program is very simple. With exception to certain sensory disabilities (such as impaired vision), the use of the program should be equal to all users.
* That being said, as our program is a job listing search/manager, a possible feature in the future should we continue working on this project could be considerations for certain disabilities when searching for jobs for users that might have some impairment but are otherwise still able to work.
    * As in maybe filtering or sorting by jobs that could still be performed with specific disabilities

Flexibility in Use



* Something that could be implemented (and is fairly popular amongst many programs today) are custom themes, that is a user can either select preset colour pallets for the program or come up with their own.
* Implementing subwindows in a way that allows them to be moved around, similar to how the different panes in IDEs such as Intellij can be repositioned to the user’s preference.




Simple and Intuitive Use



* Implementation of a more robust error handling would reduce a user needing to solve issues with the program on their own, i.e. http call issues.
* A tooltip system could be implemented to provide hints or tips on usage of the program, especially for new users.

Perceptible Information



* Text-To-Speech could be implemented as a feature for people that are visually impaired.




Tolerance for Error



* Currently our frontend does not have intended error tolerance aside from if a user attempts to create an account with a login that is already taken and that a user confirms that a password they enter is the intended password to use.
* Just in account creation, regex checking could be implemented to ensure that illegal characters are not used in usernames, login, passwords, emails, etc.

Low Physical Effort



* Does not particularly apply to our project. However small design considerations in the UI could be made to ensure that repetitive action is reduced, and that a user would not need to strain their vision to see something clearly.

Size and Space for approach and use



* Similar to Low Physical Effort, it does not particularly apply to our project, and likewise small design considerations in the Ui can be made to ensure that elements of the program are clearly visible and not hidden or difficult to read/see.

We would market our project to students (Undergraduate or Postgraduate) or more generally people who are looking for jobs. The most important part of any job application is the resume, which is required in almost every job. We have made the user input his/her resume in the form of various categories. Our project then will match the jobs tailored to the resume inputted by the user. This project will be marketed to mainly job-seekers who want to find jobs in which they have the best chance of being selected.

Our program is less likely to be used by people who have retired or by people who are currently employed and content with their job, since neither are not actively looking for jobs. In these cases, they would not be interested in looking at job listings and how these match their skills, experience, grades, etc.


## Progress Report


### Jay Wang

DemoSource package

Running the TotalDemo class in the Demo package gives a demo of the below features.

TotalDemo was created as a relatively simple program loop with implementations of how to use the below classes. It does not have a proper UI that adhers to Clean Archictecture or SOLID principles. It is there for the purposes of demonstration and testing features.

DemoJobListingSource and DemoSourceJobListing are objects used to simulate obtaining job listings from an external source. TotalDemo generates a SearchQuery, passed to a DemoJobListingSource to get results in the form of a HashMap, which is then processed into a JobListing object.

Entry



* Added some entries that were necessary for score calculation

A Login/Token system



* A new controller: AuthTokenController
    * Stores AuthToken objects (which stores the token itself, and which user it is associated with).
    * Used for authentication purposes so that
        * Clients can “stay logged in”
        * Backend knows who it is communicating with, and to authenticate that the client it is communcating with is who it is.
        * Password (or the hash of the password) is not sent between backend and frontend. Only done to create a new token.

HTTP Requests



* Backend
    * HTTP Requests are how a frontend instance will communicate with backend.
    * Created HTTP Requests for User and Listing related operations. (see documentation)
    * Done primarily with the integration of Spring Framework.
* Frontend
    * Wrote rudimentary code to be able to send HTTP requests.

Testing



* Testing of the implemented HTTP Requests were primarily carried out manually through Intellij’s feature to send HTTP Requests.

Documentation:



* Wrote Documentation HTTP calls. They can be found [here ](https://docs.google.com/document/d/1PlpB4f4CSYEf37CMdXyTAccuCM-OjOrzASszVG0AMtk/edit?usp=sharing)or as “HTTPRequests.md” in the Documentation > HTTP Calls directory

Helped with debugging frontend code, primarily related to handling HTTP Calls.

Minor changes to existing code, primarily renaming packages, and minor refactoring to simplify some code.

Significant Pull Request



* [https://github.com/CSC207-UofT/course-project-minor-technologies/commit/4715d8a5d71c7eb18f2f402558ac23352f0222fc](https://github.com/CSC207-UofT/course-project-minor-technologies/commit/4715d8a5d71c7eb18f2f402558ac23352f0222fc)
    * Not a pull request, but that was only because the first time I tried, github basically wiped out all the changes I was trying to make with a pull request. Ended up manually copying and pasting files around.
    * This was integration of Spring Framework into our project, and splitting the project into 2 separate modules, a backend module, which stores all the information, processes it, and a frontend module, which is what a User would be using, along with it some minor refactoring of packages and code.


### Mudit Chandna

UI



* Reconstructed the entire UI class in the frontend. This involved a variety of things that I will discuss in the following bullet points
* loginScreen(). This is a private method in the UI that checks the login info of the user. The front end takes in the user’s input, creates the necessary parameters using those inputs, conducts a GET HTTP request and receives confirmation if the user has an account in the backend.
* signupScreen(). This is a private method in the UI that allows the user to create a new account using a username, full name and password. This involved using a POST HTTP request to send information to the back end and register a new account
* accountScreen(). This is a private method in the UI that displays the necessary functions the users has when logged in. They can edit their profile, search listings, look at their saved listings, and logout
* userScreen(). This is a private method in the UI that allows the user to edit their profile and add to their experiences and skill set. This will allow for better matching to listings and this has been integrated into the UI
* searchScreen(). This is a private method in the UI that allows the user to search for listings using 4 parameters. They can put key terms in, location, how old the listing is, and whether it is full time or part time.
* listingsScreen(). This is a private method in the UI that displays the user’s searched listings in a separate window. It also involves multiple HTTP requests to the back end and also attempts to integrate the score of the listing relative to the user
* Listing.java. This is a class that creates a displayable listing. It shows the title, location, listing date, pay and description of the listing. It can also show the score but, at the moment, this has been disabled.

Testing



* I used demo listings and demo users already present in the back end from previous testing

Significant Pull Request



* [https://github.com/CSC207-UofT/course-project-minor-technologies/pull/85](https://github.com/CSC207-UofT/course-project-minor-technologies/pull/85)
* This is the pull request that merged my branch into the main branch. In my branch, I worked on the UI as mentioned above, by adding all the required methods listed. And I integrated them with the backend using HTTP requests, designed by Jay.
* Jay also assisted with debugging


### Raghav Banka, Domenica Vega

ScoreCalculator



* Handler: This is the abstract class that has some basic functions common to all handlers like the scoreCalculator and setNext which links the handlers together. It also has the processRequest method that will calculate the score and send the control of the program to the next processing unit.
* HandlerExtWork: This is an abstract class that extends the Handler class. This abstract class has some common functions that are required in the score calculations of the handlers that extend it.
* HandlerExt: This abstract class also extends the Handler class which also has the common functions for the score calculations of the processing units extending it.
* HandlerSkills, HandlerAwards, and HandlerIncentive: These classes extend the HandlerExt. HandlerSkills calculates the score based on the similarity between the user’s skills set and the job description. Similarly, HandlerAwards and HandlerIncentive gives a score to the user based on the user’s awards and incentive.
* HandlerUnrelatedWork, HandlerRelatedWork, and HandlerLeadership: These classes extend the HandlerExtWork. This handler gives a score to the user based on the time period for which they worked and the number of responsibilities they took up.
* HandlerGrade: This class extends the Handler class and gives a score to the user based on how high their GPA is.

Testing



* We made a demo user and a demo job listing to test each of the handlers individually and then we called the handler main class on it to check if it works interactively together.

Significant Pull Request [Domenica Vega]



* [https://github.com/CSC207-UofT/course-project-minor-technologies/pull/31](https://github.com/CSC207-UofT/course-project-minor-technologies/pull/31)
    * I made the necessary changes to the first processing units (i.e. HandlerSkills, HandlerRelatedWork, HandlerUnrelatedWork) of ScoreCalculator as well as to HandlerExtWork class in order to ensure that ScoreCalculate() computes scores properly. Also, I created a user for unit tests, worked on the unit tests as well as debugging.

Significant Pull Request [Raghav Banka]



* You can find the link to the pull request [here](https://github.com/CSC207-UofT/course-project-minor-technologies/pull/24/commits/503629c80614d9a1d577c0a17c122a42bdd2cd40).
* This was the basic layout of the handler classes. I created the  handler abstract class and the first few processing units for the basic structure of the use case.


### Alexander Tran

Listings Processing



* ListingsProcessor
    * An abstract class that utilizes the template method design pattern. This class contains the method processList, which is intended to filter and sort a given List of JobListings as a user requires. Method filter is implemented within the class, as all processors should filter in the same way. On the other hand, the sort method is not implemented in this abstract class, but rather in its subclasses. This gives us access to various sorting algorithms as we require them, in the case that some perform better than others in different situations. Using the template method design pattern, the functionality of the class is left open for extension.
* DefaultProcessor
    * A child of ListingsProcessor which implements abstract method sort() using Java's Collection.sort()'s sorting algorithm, which sorts by descending order.
* QuickProcessor
    * A child of ListingsProcessor which implements abstract method sort() using the QuickSort sorting algorithm, and sorts by descending order.
* JobListingComparator
    * Implements Comparator&lt;JobListing> and overrides method compare() to compare JobListings based on a given criteria, such as by score, salary, or listing date. Used when sorting lists of listings in ListingsProcessor
    * Note. Due to internal issues it is currently not possible to sort by the scores of job listings for the user, as there is not a way to access the current user from the backend.
* CreateListingPredicate
    * Contains methods to create Predicates that will be used to filter listings in ListingsProcessor

Testing



* Tests were written for all processors, using various combinations of comparators and filters, and run on custom job listings to check if classes and methods worked as intended.

Significant Pull Requests

[https://github.com/CSC207-UofT/course-project-minor-technologies/pull/53](https://github.com/CSC207-UofT/course-project-minor-technologies/pull/53)

Includes:



* Refactoring in classes so that variables involved are more abstract.
* Addition of CreateListingPredicate,
* Addition of more tests to ensure that things are working as intended.
* Individual comparator classes being individual was unnecessary, and were consolidated into a single class to avoid unnecessary clutter in ListingsProcessing.

[https://github.com/CSC207-UofT/course-project-minor-technologies/pull/29](https://github.com/CSC207-UofT/course-project-minor-technologies/pull/29)

Includes the first versions providing the necessary bases for ListingsProcessing that will work with the program.
