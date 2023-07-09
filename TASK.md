# Backend test for Just Eat Takeaway.com, Operations Finance

Takeaway Pay allows businesses to provide its employees a fixed daily allowance they can use on their food purchases.

In this task, we need you to design and implement an internal RESTful API to help customers transfer their Takeaway Pay allowance to the restaurant's account on their purchase.

## Task description

### Business requirements

* Keep it very simple and meet today's demands only.
* You don't need to implement any authentication.
* We need two main features, which should be implemented by REST endpoints. The application should be able to:
  * Take some money from the customer's Takeaway Pay allowance and put it in the restaurant's account. We don't need extra details like the customer's name, or restaurant's address.
    * The application should validate that the customer has enough funds in their Takeaway Pay allowance to complete the transaction and that the transfer amount does not exceed their daily limit of 10 Euros. You can assume the limit gets topped up by an external source every day, or you can always be creative and implement your own solution.
  * Display the amount in customer and restaurant's accounts

### Technical requirements

* You can use Java or Kotlin. You can use frameworks/libraries if you like. But don't forget the first business requirement.
* Data should be in-memory.
* Demonstrate with tests that your application works as expected.
* Please send it in a .zip file after you're finished. Final result should be a simple standalone executable application, should not require containers or servers or any additional configuration.

Also, please provide a simple API documentation and a README for how to run the application.