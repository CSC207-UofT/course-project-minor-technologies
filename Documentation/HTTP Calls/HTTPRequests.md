

# Workr HTTP API

Use these API Calls for the frontend program.


## General HTTP and Documentation Info


### Parameter Table:


<table>
  <tr>
   <td>Parameter
   </td>
   <td>Description
   </td>
   <td>ExpectedType
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>
   </td>
   <td>
   </td>
  </tr>
</table>



## Admin Commands


### GET /Admin/SerializeAllData

Serializes all data (Tokens, Listings, and User data).

Returns 1 when complete


### GET /Admin/GetAllTokens

Returns a list of all tokens currently in the database.


### Get /Admin/Shutdown

Serializes all data and then ends the program.


## User Related Calls


### GET /User/SignIn

Logs in a user with specified login and password


#### Parameters


<table>
  <tr>
   <td>login
   </td>
   <td>The login
   </td>
   <td>String
   </td>
  </tr>
  <tr>
   <td>password
   </td>
   <td>The password
   </td>
   <td>String
   </td>
  </tr>
</table>



#### Returns


##### String - Token

An authentication token. This token will be used for anything that has relation to a user, such as getting user data, creating custom listings, etc.

If the login or password does not match any users, the return will be null.


### GET /User/SignInByToken

Authenticates that a token provided is both valid and matches a user.

This should be called on program launch, when the app is launched and a user is signed in from a previous session.


#### Parameters


<table>
  <tr>
   <td>login
   </td>
   <td>The user’s login
   </td>
   <td>String
   </td>
  </tr>
  <tr>
   <td>token
   </td>
   <td>An AuthToken
   </td>
   <td>String
   </td>
  </tr>
</table>



#### Returns


##### Boolean

Boolean which represents whether the token and user pair is valid.


### POST /User/Create

Creates a new user.


#### Request Body

The request body needs to be in a Json format, or just a Map&lt;String, String>


<table>
  <tr>
   <td>Username
   </td>
   <td>Name of the user
   </td>
   <td>String
   </td>
  </tr>
  <tr>
   <td>email
   </td>
   <td>AuthToken
   </td>
   <td>String
   </td>
  </tr>
  <tr>
   <td>login
   </td>
   <td>A unique login
   </td>
   <td>String
   </td>
  </tr>
  <tr>
   <td>password
   </td>
   <td>A password
   </td>
   <td>String
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>
   </td>
   <td>
   </td>
  </tr>
</table>



#### Returns


##### Int

Response codes representing whether the user account has been created


<table>
  <tr>
   <td>1
   </td>
   <td>Account Successfully Created
   </td>
  </tr>
  <tr>
   <td>2
   </td>
   <td>User Name malformed
   </td>
  </tr>
  <tr>
   <td>3
   </td>
   <td>Login Malformed
   </td>
  </tr>
  <tr>
   <td>4
   </td>
   <td>Login Already Taken
   </td>
  </tr>
  <tr>
   <td>5
   </td>
   <td>Password malformed (if we want to implement password requirements)
   </td>
  </tr>
  <tr>
   <td>6
   </td>
   <td>Email malformed
   </td>
  </tr>
  <tr>
   <td>7
   </td>
   <td>Payload Data Malformed
   </td>
  </tr>
</table>



### POST /User/{login}/SetData

Sets a single data set for a User.

Note, user Email, Password, or Salt cannot be set in this method.


#### Path Variables


<table>
  <tr>
   <td>login
   </td>
   <td>A User’s login
   </td>
   <td>String
   </td>
  </tr>
</table>



#### Parameters


<table>
  <tr>
   <td>login
   </td>
   <td>A unique login
   </td>
   <td>String
   </td>
  </tr>
  <tr>
   <td>key
   </td>
   <td>The key of the Data to be added
   </td>
   <td>String
   </td>
  </tr>
  <tr>
   <td>data
   </td>
   <td>The data to be added
   </td>
   <td>Object
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>
   </td>
   <td>
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>
   </td>
   <td>
   </td>
  </tr>
</table>



#### Returns


##### Int

Response codes representing whether the data was successfully added.


<table>
  <tr>
   <td>0
   </td>
   <td>Invalid token
   </td>
  </tr>
  <tr>
   <td>1
   </td>
   <td>Data Successfully Set
   </td>
  </tr>
  <tr>
   <td>2
   </td>
   <td>Key Does Not Exist
   </td>
  </tr>
  <tr>
   <td>3
   </td>
   <td>Malformed Data
   </td>
  </tr>
</table>



### POST /User/{login}/SetDataLarge

Sets a variety of data for a User.

Note, user Email, Password, or Salt cannot be set in this method.


#### Path Variables


<table>
  <tr>
   <td>login
   </td>
   <td>A User’s login
   </td>
   <td>String
   </td>
  </tr>
</table>



#### Parameters


<table>
  <tr>
   <td>login
   </td>
   <td>A unique login
   </td>
   <td>String
   </td>
  </tr>
</table>



#### Request Body


<table>
  <tr>
   <td>Payload
   </td>
   <td>A Map containing the data to be set
   </td>
   <td>Map&lt;String, Object>
<p>
You should be able to use a JSONObject, or just a Map&lt;String, Object> as it essentially is a JSON
   </td>
  </tr>
</table>



#### Returns


##### Int

Response codes representing whether the data was successfully added.


<table>
  <tr>
   <td>0
   </td>
   <td>Invalid token
   </td>
  </tr>
  <tr>
   <td>1
   </td>
   <td>Data Successfully Set
   </td>
  </tr>
  <tr>
   <td>2
   </td>
   <td>No keys provided exists
   </td>
  </tr>
  <tr>
   <td>3
   </td>
   <td>Input Format Malformed
   </td>
  </tr>
  <tr>
   <td>4
   </td>
   <td>Some keys provided does not exist. Others added successfully
   </td>
  </tr>
  <tr>
   <td>5
   </td>
   <td>Some data Malformed. Others added successfully.
   </td>
  </tr>
  <tr>
   <td>6
   </td>
   <td>Some keys does not exist and some data malformed. Others successfully added.
   </td>
  </tr>
</table>



### POST /User/{login}/GetAllUserData

Gets all data for a User except for password and salt


#### Path Variables


<table>
  <tr>
   <td>login
   </td>
   <td>A User’s login
   </td>
   <td>String
   </td>
  </tr>
</table>



#### Parameters


<table>
  <tr>
   <td>token
   </td>
   <td>The Token associated with the user
   </td>
   <td>String
   </td>
  </tr>
</table>



#### Returns

Hashmap&lt;String, Object>


<table>
  <tr>
   <td>Data Returned
   </td>
   <td>User data successfully found
   </td>
  </tr>
  <tr>
   <td>null
   </td>
   <td>Token/login authentication failed.
   </td>
  </tr>
</table>



### POST /User/{login}/AddToWatched

Gets all data for a User except for password and salt


#### Path Variables


<table>
  <tr>
   <td>login
   </td>
   <td>A User’s login
   </td>
   <td>String
   </td>
  </tr>
</table>



#### Parameters


<table>
  <tr>
   <td>token
   </td>
   <td>The Token associated with the user
   </td>
   <td>String
   </td>
  </tr>
</table>



#### Request Body


<table>
  <tr>
   <td>Payload
   </td>
   <td>The listing data (can be obtained by using Entry.Serialize() method)
   </td>
   <td>Map&lt;String, Object>
<p>
You should be able to use a JSONObject, or just a Map&lt;String, Object> as it essentially is a JSON
   </td>
  </tr>
</table>



#### Returns

String


<table>
  <tr>
   <td>A large string with dashes inside
   </td>
   <td>Saved Listing’s UUID to be stored locally if needed
   </td>
  </tr>
  <tr>
   <td>null
   </td>
   <td>Token/login authentication failed.
   </td>
  </tr>
  <tr>
   <td>“malformed”
   </td>
   <td>Listing data was malformed.
   </td>
  </tr>
</table>



## Listing Related Calls


### GET /JobListing/Get/{uuid}

Gets all data for a User except for password and salt


#### Path Variables


<table>
  <tr>
   <td>uuid
   </td>
   <td>The UUID for the job listing
   </td>
   <td>String
   </td>
  </tr>
</table>



#### Returns

String


<table>
  <tr>
   <td>Map&lt;String, Object>
   </td>
   <td>The data for the Job Listing
   </td>
  </tr>
  <tr>
   <td>null
   </td>
   <td>No job listing found.
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>
   </td>
  </tr>
</table>



### GET /JobListing/GetMultiple/

Gets all data for a User except for password and salt


#### Request Body


<table>
  <tr>
   <td>uuids
   </td>
   <td>The UUIDs to be retrieved
   </td>
   <td>String[]
   </td>
  </tr>
</table>



#### Returns

String


<table>
  <tr>
   <td>ArrayList&lt;Map&lt;String, Object>> 
   </td>
   <td>A list containing the job listing data that were able to be retrieved
   </td>
  </tr>
  <tr>
   <td>null
   </td>
   <td>No job listing found.
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>
   </td>
  </tr>
</table>



### GET /JobListing/Search

Runs a search;


#### Parameters


<table>
  <tr>
   <td>dateTime
   </td>
   <td>The oldest listing to search for
   </td>
   <td>dateTime format (yyyy-mm-dd).
   </td>
  </tr>
  <tr>
   <td>location
   </td>
   <td>The location for listings
   </td>
   <td>String
   </td>
  </tr>
  <tr>
   <td>jobType
   </td>
   <td>The Job type
   </td>
   <td>String
<p>
PART_TIME, FULL_TIME
   </td>
  </tr>
  <tr>
   <td>searchTerms
   </td>
   <td>Searchterms to search for.
   </td>
   <td>String, separated by underscores.
   </td>
  </tr>
</table>



#### Returns

String


<table>
  <tr>
   <td>ArrayList&lt;Map&lt;String, Object>> 
   </td>
   <td>A list containing the job listing data that were able to be retrieved
   </td>
  </tr>
  <tr>
   <td>Empty ArrayList
   </td>
   <td>No job listing found.
   </td>
  </tr>
  <tr>
   <td>Null
   </td>
   <td>Some error occurred 
   </td>
  </tr>
</table>



### GET /JobListing/Score/{login}

Runs a search


#### Path Variables


<table>
  <tr>
   <td>login
   </td>
   <td>A User’s login
   </td>
   <td>String
   </td>
  </tr>
</table>



#### Parameters


<table>
  <tr>
   <td>token
   </td>
   <td>Token for the user to generate scores.
   </td>
   <td>String
   </td>
  </tr>
</table>



#### Request Body


<table>
  <tr>
   <td>payload
   </td>
   <td>Arraylist or Array of listing uuids to generate scores for
   </td>
   <td>String
   </td>
  </tr>
</table>



#### Returns

String


<table>
  <tr>
   <td>ArrayList&lt;Map&lt;String, Object>> 
   </td>
   <td>A list containing serializes Score entries.
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>
   </td>
  </tr>
</table>

