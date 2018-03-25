1. Project And Technology:
 		 I chose TheatreSeatingProject and implemented it as a RestFul Web service using Spring Boot.
 		 
 	Assumption: 
 		1. Each request is stand alone. Means if a request is made with Seat Map and Parties, that is it for the theater. We can't do any modification/New reservation i.e like a request scope.  
 		2. This project is NOT a ticket Master. This is for a Single theater.
 		3. None of the data is persisted in a cache or DB to make the project Simple. Hence no transactions are maintained. All requests are request specific. 
2. Request Sample: 
   Hence assumed request to be 
   {
    "seatMap":[
    {"row":"5,6"},
    {"row":"7,8"}
    ],
    "seatReservation":[
    {"partyName":"Sam","SeatsRequired":5},
    {"partyName":"Tom","SeatsRequired":6}
    ]
    }

   In Seat Map Section, {"row":"5,6"} means Row 1 with Section 1(5 Seats) and Section 2(6 seats) and etc. Party is self explanatory.

3. Logic is such that , (Assume Row 1  Section 1 has 6 seats)
   a) If Number of seats requested by party is same as section(6 in this case) or number of seats required is less than half(less than 3) of the section seats,  then allocate..
   b) If (a) fails, and number of seats required is less than section and greater than half(say 4), then assign tentatively and move on to next section, if any section which has exact seats or less than half(which is condition (a) ) remove tentative and assign that section.. If none of the section satisfies (a), then assign the tentative section as final.
   
4. How to run:
    a) Need Java 8 and Apache Maven 3.3.9 to run this project.
    b) Once Java and and maven is installed set JAVA_HOME and MVN_HOME. Then run Maven build from command line using command "mvn clean install". 
    c) I have written 3 integration test cases, to test Rest Endpoint(without Mocking), in class "BarclaycardTheatreSeatingApplicationTests".
        Testcase1 : Input Same as in Problem Statement.
        TestCase2 : All Negative cases - Either Split or Can't Handle.
        TestCase3 : All Positive cases - Set Row and Section for all parties.
    d) Sample curl "curl -X POST -H "content-type:application/json" http://localhost:8081/api/v1/theatre/barclays/seating -d '{"seatMap":[{"row":"5,6"},{"row":"7,8"}],"seatReservation":[{"partyName":"Sam","seatsRequired":5},{"partyName":"John","seatsRequired”:6},{“partyName":"Bob","seatsRequired":6},{"partyName":"Mark","seatsRequired":7}]}'"
    e) Sample output: {
       "response": [
        {
            "partyName": "Smith",
            "seatsRequired": 2,
            "row": "Row 1",
            "section": "Section 1"
        },
        {
            "partyName": "Jones",
            "seatsRequired": 5,
            "row": "Row 2",
            "section": "Section 2"
        },
        {
            "partyName": "Davis",
            "seatsRequired": 6,
            "row": "Row 1",
            "section": "Section 2"
        },
        {
            "partyName": "Wilson",
            "seatsRequired": 100,
            "why can't allocate?": "Sorry, we can't handle your party."
        },
        {
            "partyName": "Johnson",
            "seatsRequired": 3,
            "row": "Row 2",
            "section": "Section 1"
        },
        {
            "partyName": "Williams",
            "seatsRequired": 4,
            "row": "Row 1",
            "section": "Section 1"
        },
        {
            "partyName": "Brown",
            "seatsRequired": 8,
            "row": "Row 4",
            "section": "Section 2"
        },
        {
            "partyName": "Miller",
            "seatsRequired": 12,
            "why can't allocate?": "Call to split party."
         }
        ]
       }
 5) I have included Java doc for class "SeatingServiceImpl" which has almost all of the logic.
 6) Row Name will be such that "ROW" + " Integer starting from 1". Say there are 2 rows , row name will be Row 1 , Row 2. Row corresponds to each request. Same for Section. Section corresponds to each Row.
 7)I didn't implement any Authorization as I feel Certificate is best way to Authorize and I don't want to complicate project.
 8) Log files are configured in path ProjectLocation/logs/barclaysTheatreSeating.log. You can tail it to see statement. Included TransactionId in each log so that we can grep and see logs for each request.      

******************Thanks******************
Hariprasath G
    