# Campsite - Upgrade Coding Challenge

REST API program for booking an island's only campsite

Constraints:

* The campsite will be free for all.
* The campsite can be reserved for max 3 days.
* The campsite can be reserved minimum 1 day(s) ahead of arrival and up to 1 month in advance.
* Reservations can be cancelled anytime.
* For sake of simplicity assume the check-in & check-out time is 12:00 AM

System Requirements
* The users will need to find out when the campsite is available. So the system should expose an API to provide information of the availability of the campsite for a given date range with the default being 1 month.
* Provide an end point for reserving the campsite. The user will provide his/her email & full name at the time of reserving the campsitealong with intended arrival date and departure date. Return a unique booking identifier back to the caller if the reservation is successful.
* The unique booking identifier can be used to modify or cancel the reservation later on. Provide appropriate end point(s) to allow modification/cancellation of an existing reservation
* Due to the popularity of the island, there is a high likelihood of multiple users attempting to reserve the campsite for the same/overlapping date(s). Demonstrate with appropriate test cases that the system can gracefully handle concurrent requests to reserve the campsite.
* Provide appropriate error messages to the caller to indicate the error cases.
* In general, the system should be able to handle large volume of requests for getting the campsite availability.
* There are no restrictions on how reservations are stored as as long as system constraints are not violated.


# Implementation
This program uses Spring Boot to implement a REST API and JPA to handle the storage of data into a Postgresql database. 

The Constraints of requesting a booking are checked using the ReservationValidator class, wich is based off of the Decorator Design pattern. This class adds to itself a set of objects that implement the ReservationConstraint interface. Each constraint is checked, and if one fails the reason is saved to the reservation to be displayed to the user. 

The Postgres database has two tables: reservations and reserved_days which are related by a primary/forgeign key mapping. The SQL script to build the tables is in the sql directory of this project. The database design makes it simple to check availability by pulling all the days stored in the table and marking them unavailable for the user to book. The unique index on the days column of reserved_days will handle any requests to book an already reserved day, even concurrent ones without the need to lock any database records. 

REST call testing was done using Postman
