# Progress Report - 16 Oct 2021
## CSC207 Final Project - Minor Technologies

### Introduction
Our team, Minor Technologies, is developing a yet-unnamed job listing and recruiting application. Our goal is to create stable, user-friendly software that prioritizes job seekers’ goals and ideal work environments.

### Project Specification
Our specification was not written all at once, but was slowly added onto as the scope of our project grew and changed over the previous weeks. Our priority in writing the specification was not only determining what classes need to be programmed, but also who would be responsible for each element. In its final form, the specification lists the project authors, domain, a brief description of the project, a list of behavioural requirements (updating job listings, tracking dates, and other quality-of-life functions), and a list of core classes needed for the program, with their designated authors. It also lists miscellaneous tasks and those responsible for them. 

### CRC Model
The Class-Responsibility-Collaboration (CRC) cards give an at-a-glance overview of each class planned for the project. After writing each card (being sure that each card includes the class name, responsibilities, and related classes), we graphically arranged them in a logical pattern that is consistent with clean architecture. It should be noted that not every class described in the CRC model has been implemented in the skeleton code.

### Scenario Walkthrough
The scenario walkthrough gives an intuitive description of how the program currently operates. It starts by describing how the locally stored job listings are loaded, and briefly explains the command line interface. It goes through the process of user input, generating a com.minortechnologies.workr_frontend.controllers.com.minortechnologies.workr_frontend.controllers.search.Search.SearchQuery, and searching for relevant job listings, as well as how listings of interest are stored to the user’s listings of interest. The walkthrough makes specific references to several classes and methods, but also takes care to describe the process as abstractly as possible.

### Skeleton Program
Some of the most important classes currently implemented are:
* com.minortechnologies.workr_frontend.controllers.backgroundoperations.com.minortechnologies.workr_frontend.controllers.backgroundoperations (refreshes listings)
* com.minortechnologies.workr_frontend.entities.user.User (stores username and saved listings)
* com.minortechnologies.workr_frontend.controllers.localcache.LocalCache (loads listings)
* com.minortechnologies.workr_frontend.controllers.search.Search (searches for listings in local cache)
* com.minortechnologies.workr_frontend.main (command line interface)

There are also some included JSON files for test listing purposes. See the Scenario Walkthrough for a more detailed analysis on how these classes interact in the skeleton program. 

### Open Questions
* What is the best way to work with and narrow down huge amounts of data, i.e. many listings being scraped off of the internet?
* What data should be associated with each user account? 
* Should user security be a priority for us?
* What are the main considerations in user interface design?

### Team Member Contribution Summary
| Team Member | Completed Tasks                                                                                 | Planned Areas of Focus                 |
|-------------|-------------------------------------------------------------------------------------------------|----------------------------------------|
| Alex        | com.minortechnologies.workr_frontend.controllers.localcache.LocalCache class                                                                                | Algorithms for matching jobs to users  |
| Domenica    | Scenario Walkthrough, CRC Modelling                                                             | Algorithms for matching jobs to users  |
| Jay         | com.minortechnologies.workr_frontend.entities.listing.Listing class,  com.minortechnologies.workr_frontend.entities.user.User class,  FileIO class, com.minortechnologies.workr_frontend.controllers.backgroundoperations.com.minortechnologies.workr_frontend.controllers.backgroundoperations, Coordination and management    | Multi-threading in runtime             |
| Mudit       | Unit Testing,  CRC Modelling                                                                    | Sorting of listings for user browsing  |
| Philip      | com.minortechnologies.workr_frontend.main class,  Documentation                                                                      | Front end and user interface           |
| Raghav      | com.minortechnologies.workr_frontend.controllers.search.Search class, Debugging, CRC Modelling                                                          | Implementation of score finder classes |
