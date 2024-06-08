# Shepherd Money User and Credit Card Management API

## Introduction
This project is a Spring Boot application that manages user creation/deletion and the addition of credit cards to user profiles. Users can have zero or more credit cards, and the application provides APIs to manage these relationships.

## Requirements
- JDK 17

## Setup
1. Install JDK 17 (e.g., `sudo apt install openjdk-17-jdk` for Debian Linux).
2. Clone the repository and navigate to the project directory.
3. Run the project using the following command:
   ```bash
   ./gradlew bootRun

## Project Summary
Write a Spring Boot program that manages user creation/deletion and adding credit cards to their profiles. Users may have zero or more credit cards associated with them. Also, create two APIs: one to get all credit cards for a user and another to find a user by their credit card number. There is an additional API that update the balance history of a credit card.

## Files to Change
Any files with content marked with **TODO** will contains hints on what to add. You are welcomed to add/modify any files to help to implement this project.

## Models
Following is a component overview of the models. You can find more hints in the source code files as well
- `User`: each user has their name, and some (or none) credit cards associated with them
- `CreditCard`: each credit card has issance bank, card number, who the card belongs to, and a list of balance history
- `BalanceHistory`: credit card balance for a specific date.

## Controllers
The controllers should contain enough comment for you to implement their basic functionalities. Note that apart from implementing the simple functionalities, we are looking for good coding conventions, good error handling, etc.

## Useful Tools
- **PostMan**: useful to send http requests to test your API
- **H2 Console**: when running your project, you can use `http://localhost:8080/h2-ui` to access the h2 console. This will allow to look at what's stored in the database.
  - The database name is `database`
  - The username is `sa`
  - The password is `password`
