# Employees

This is a project base on Employees for Cognizant challenge

## Getting Started

These instructions will give you a copy of the project up and running on
your local machine for development and testing purposes. See deployment
for notes on deploying the project on a live system.

### Prerequisites

Requirements for the software and other tools to build, test and push
- [Git](https://www.github.com)
- [JDK 16](https://www.oracle.com/java/technologies/javase/jdk16-archive-downloads.html)

### Installing

A step by step series of examples that tell you how to get a development
environment running

Download repository from Github

    git clone https://github.com/elanvelazquez/employees.git

now you can access to project on your local environment.

#About endpoints

## GET
`Get employees by last name` [/employees/{lastName}](#) <br/>
`Get employee by id` [/employees/find/{employeeId}](#) <br/>

## POST
`Create Employee` [/employees](#) <br/>

## DELETE
`Soft delete employee` [/employees/{employeeId}](#)

## Run the application without Docker
Open your terminal and navigate to the working directory we created and run the following command:

` $ ./mvnw spring-boot:run`

To test that the application is working properly, open a new browser and navigate to http://localhost:8080

## Built with Docker

-First create jar target file.

`$ mvn package -Dmaven.test.failure.ignore=true`

`-Dmaven.test.failure.ignore=true` This means the project will be built without compiling the tests.

-Build docker image.

`$ docker build -t evelazquez/employee `

-Check local images.

`docker images`

###Run image as a container
Run the following command in your terminal:

`$ docker run --publish 8080:8080 evelazquez/employee`


## Authors

- **Elan Velazquez** 
- *Provided README Template* -
  [PurpleBooth](https://github.com/PurpleBooth)
- *Steps for Docker* -
  [Docker](https://docs.docker.com/language/java/)


