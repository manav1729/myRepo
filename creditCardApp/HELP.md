# Credit Card Processing

### A small application for a credit card provider.

It allow us to add new credit card accounts and view them as a list.
This is the RESTful API backend application.

#### Requirements
Two REST Endpoints are implemented
#### "Add" 
* Will create a new credit card for a given name, card number, and limit
* Card numbers is validated using Luhn 10 and returns error if not compatible. 
* New cards start with a £0 balance

#### "Get all" 
* Returns all cards in the system

#### Validation
* All input and output will be JSON
* Credit card numbers may vary in length, up to 19 characters
* Credit card numbers will always be numeric
