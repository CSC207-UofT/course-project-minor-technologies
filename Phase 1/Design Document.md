## Project Specifications

**CSC207 Final Project**

_Submitted 14 October, 2021_

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

Creating Entry objects through deserialization in our program uses the Factory Design Principle. In com.minortechnologies.workr.usecase.factories contains Methods and objects that deserialize code. First, a map containing the entry data passed into ICreateEntry.create(), where it uses some portions of the data to determine which subclass should be created. Then, subclasses of ICreateEntry are used to create the final entry.

Any usecase objects that implements IEntryDatabase also has to implement the iterator design pattern. As these database objects will be a collection of Entry objects, it allows for iteration through the database. UserDB and JobListingDB both implement iterators.

For the Score_Calculator module, we implemented the chain of responsibility design pattern. There is a main handler class to send requests to the smaller handler that operates to calculate score on each attribute of the user. All the classes that do the calculation are extended from an abstract class. The processing of request goes on from one handler to the next till an end in reached.


### Design Diagrams

CRC Cards from phase 0 were not really used beyond being an idea of how we should implement our code, and therefore did not receive updates since phase 0.

IntelliJ Generated Class Diagrams can be viewed as PDF with the "Class Diagram.pdf" file or in IntelliJ using the "Class Diagram.uml" file.

## Progress Report


### Jay Wang

DemoSource package

Running the TotalDemo class in the com.minortechnologies.workr.demo package gives a demo of the below features.

TotalDemo was created as a relatively simple program loop with implementations of how to use the below classes. It does not have a proper UI that adhers to Clean Archictecture or SOLID principles. It is there for the purposes of demonstration and testing features.

DemoJobListingSource and DemoSourceJobListing are objects used to simulate obtaining job listings from an external source. TotalDemo generates a SearchQuery, passed to a DemoJobListingSource to get results in the form of a HashMap, which is then processed into a JobListing object.

Entry



* Refactoring of the Entry class. Entry now contains a hashMap with methods to add, update and retrieve data. Primarily done to reduce the required maintenance for de/serialization.
    * This however means that data has to be Casted when retrieving, and that there is currently no checks to ensure data added are the correct types.
    * That being said, I am experimenting with type checking methods for adding and removing data.
    * I also believe that while now you have to cast the data, the extra effort required when coding is less than the effort saved from maintaining the de/serialization function.
* Subclasses have some unique methods, such as JobListing has methods to retrieve specific data already in the correct casted type.

Serialization



* User, JobListing, SearchQuery, and Experience can all be de/serialized
* LocalCache and UserManagement have methods that saves all entries.
* IEntrySerializer and IEntryDeserializer interfaces were created.
    * Currently there is a JSONSerializer that implements both interfaces. It serializes a map into a String in JSON Format and can deserialize a String in JSON Format into a HashMap
* SearchQueries and Experience are serialized as part of a User instance.
* Created EntryDataMapTypeCaster in com.minortechnologies.workr.usecase.factories.
    * This object exists to ensure essentially, that Entry.deserialize(Entry.serialize) works, in that output from Entry.serialize() can be directly passed into Entry.deserialize(). This was not the case before as serialized serialized list style collections (such as ArrayLists or HashSets) into an array, and therefore the deserialize method had to cast or convert to the correct object types.
    * Effectively, this Object was created to offload some de/serialization work from the Entries to a new class, which also adheres to the single responsibility design principle.



IEntryFactory



* IEntryFactory.create() takes a Hashmap and determines which subclass to create.
* There are 3 subclasses, ICreateJobListing, ICreateUser and CreateSearchQuery.
    * These subclasses do checks to make sure the data is not malformed, in this case, if there are any missing required keys.

UserManagement



* User accounts can be created. Using the CreateUser() factory, the create method takes 3 parameters, username, login and password.
* UserManagement prevents multiple accounts with the same login to ensure that login is unique.
* Passwords are hashed with a salt and are therefore not stored as plain text.
    * Hashing is done by the Security usecase object. It uses the BPKDF2 algorithm to hash passwords.
    * Salt is generated using Java's built-in SecureRandom generator.



Testing



* Tests were written for the Security usecase object, ICreateEntry, EntryDataTypeCaster, and UserCreation

Documentation:



* Wrote Documentation for Entry.Java and the Factories.


### Philip Harker

Raghav Banka, Domenica Vega, Mudit Chandna



* Score_Calculator
    * Contribution: Raghav and Domenica brainstormed how Score_Calculator would work (i.e. looked into possible design patterns). Raghav worked on the skeleton code for Score_Calculator, and Domenica made changes to the code so it works as desired. Mudit and Domenica worked on unit testing and debugging.
    * Goal: We want to design a process that allows us to score users and match them to various listings. Similar to how LinkedIn displays job listings to the user, we want to customize and personalize this to each user. We implemented the Score_Calculator package where we use the Chain of Responsibility design pattern to go through every qualification, requirement, and application requirement, including the entire description, and check for matches between a user’s skill set and the job listing. We also check work experience and unrelated work experiences and systematically add this to our score.
    * How it works: In this design pattern, we have a client which sends requests to the handlers which are individual processing unit to perform a single calculation. Classes(handler_1, handler_2, and handler_3) are all processing units that perform a part of the calculation. In this case, handler_1 calculates a score based on the user's technical skills by checking whether those skills appear on job listings’ qualifications, job requirements, application requirements and job description. After handler_1 adds its score to the handler_main’s score, handler_2 calculates a score based on the user’s work experience that is related to the job listing. For each of the user’s experiences. Once handler_2 adds its score to the handler_main’s score. handler_3 calculates a score based on the user’s work experience that is not related to the job listing.
    * The client that sends the request is the handler_main class.  Each processing unit follows a similar structure and summon methods so it extends the class handler. We start with the abstract class which contains the common method process_request. This method first updates the score that is being calculated by processing unit, then checks if the processing unit has a neighbour(another processing unit) it has to request to. If it does then sends the control to the next processing unit. Each handler class does its calculation in its specific way. We have another abstract class handler_ext_work which is being extended from handler. It adds an extra function which is being used by both handler_2 and handler_3. The handler_main initializes these processing units and sends the request to handler_1 which in turn sends it to other handlers till we have nothing left to calculate anymore.
    * Testing: Tests were written for all three handlers as well as handler_main. 