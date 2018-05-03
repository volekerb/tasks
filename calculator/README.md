#Simple calculator service

##Description
Application is a web service built on top of [Spring boot](https://projects.spring.io/spring-boot/).
Main goal of the app is to perform calculation for two provided numbers.
Supported operations: 
* ADD - addiction (+)
* SUB - subtraction (-)
* MUL - multiplication (*)
* DIV - division (/)

Web service has one endpoint : "/calculate" which accepts GET requests with parameters:
* number1 - first number
* number2 - second number
* operation - operation (Examples: "ADD", "SUB")

Example of usage:

"/calculate?number1=1&number2=2&operation=ADD"

Expected result:

"3"

## Run
For running app: `mvn spring-boot:run`. App will be available on [localhost:8080](http://localhost:8080)

## Test
For running tests: `mvn test`.

### Notes
I've got some ideas for the improvements but haven't implemented them due to lack of my time:

* performance tests - [Gatling](https://gatling.io/) could be used
* create several spring configuration profiles and use them for component and system(rest) tests so we can write 
logic in one test and perform testing on both levels. We can implement abstraction for that purpose which knows whether 
to use mvc(such tests are implemented) or system test(not implemented).
* provide contract with [Protocol Buffers](https://github.com/google/protobuf). 
It will allow easier interact with this service and in case of changes it's easier to track which tests will fail.
* think about more negative scenarios
* randomization logic could be used instead of hard-coded values
* think about more accurate messages for tests
* add logging
* add reporting with [Allure](http://allure.qatools.ru/)
* add Google code style pre-check into the project

 

