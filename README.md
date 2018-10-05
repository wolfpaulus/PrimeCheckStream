#PrimeCheckStream

PrimeCheckStream is a VERY simple AWS Lambda Function implemented in Java. 
It has two classes for representing the request and response objects. 
A third class that implements [RequestStreamHandler](https://github.com/aws/aws-lambda-java-libs/blob/master/aws-lambda-java-core/src/main/java/com/amazonaws/services/lambda/runtime/RequestStreamHandler.java), determines if a provided number is a prime number or not 
and responds with a message.

Implementing an RequestStreamHandler instead of an RequestHandler requires the Lambda function to perform object 
deserialization and serialization, which may seem like a disadvantage, but allows the use of a more modern library and 
more concise code.

The project also has some unit tests implemented and uses Log4J2 locally and also when deployed, logging into AWS CloudWatch.


##Interacting with AWS
For convenience, the PrimeCheck projects includes gradle tasks for 
* Creating a AWS LambdaBasicExecutionRole, which needs to be created even before the function can be deployed
* Deploying of the Lambda Function
* Invoking of the Lambda Function
* Create an S3 Bucket

This can be done from a commandline in Terminal or Inside the Gradle Tool-Window inside of IntelliJ IDEA
