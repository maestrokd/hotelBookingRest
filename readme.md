REST application Hotel booking
=============================

  With capabilities (each represented by separate endpoint):

1. View list of available rooms (room have a number, category, price, additional options like breakfast, cleaning with additional cost) for specified dates.
2. View rooms filtered by category.
3. Create user.
4. User can book the room for specified days.
5. User can view his booking.
6. User can get the total price of the booking (room for dates period + cost of additional options).
7. View all bookings for the hotel.

INSTALLATION
------------

Running the application should not require a standalone application server (application should run on embedded tomcat server) or installation of any software except Java and maven.

The project contains SQL-script (input.sql) for filling database tables with data necessary for test application.
Initialization and initial development(run input.sql) DB is carried out by the library Liquibase.
These data are presented in the initial database:

      rooms with numbers:   1 - 10
      categories of rooms:  A, B, C
      additional options:   cleaning, breakfast
      users' logins:        user1, user2, user3
      bookings:             few bookings of presented users for test     


QUICK START
-----------

You can see Api Documentation with List Operations and example parameters with the following URL:
http://localhost:8080/swagger-ui.html#/

There are:
1. GET "/api/rooms/free/{startBookingDate}/{endBookingDate}" where date format is like "20-12-2018"

2. GET "/api/rooms/findbycategory/{category}" where category is A or B or C

3. POST "/api/users/create"

    Example body:    
	{
	
		"login": "user1",
		"password": "pass1",
		"name": "Name1"
	}

4. POST "/api/bookings/create"

	Example body:
	{
	
		"userLogin": "user1",
		"roomNumber": "8",
		"startBookingDate": "19-03-2018",
		"endBookingDate": "20-03-2018",
		"additionalOptions": [
                    "cleaning",
                    "breakfast"
                ]
	}

5. GET "/api/users/{userlogin}/bookings"

6. POST "/api/bookings/totalprice"

	Example body:
	{
	
		"userLogin": "user1",
		"roomNumber": "1",
		"startBookingDate": "10-03-2018",
		"endBookingDate": "12-03-2018",
		"additionalOptions": [
				    "cleaning",
				    "breakfast"
			    ]
	}

7. GET "/api/bookings"
