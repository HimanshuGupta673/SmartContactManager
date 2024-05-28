# Smart Contact Manager

## Overview

This repository contains the backend code for the Smart Contact Manager, a Spring Boot-based application that provides user authentication, user management, contact management, and various other functionalities to efficiently manage user contacts.

## API Endpoints

### Authentication Endpoints

#### Login
- **URL:** `/signin`
- **Method:** `GET`
- **Description:** Displays the login page.

#### Register
- **URL:** `/do_register`
- **Method:** `POST`
- **Description:** Registers a new user.

### User Endpoints

#### Dashboard
- **URL:** `/user/index`
- **Method:** `GET`
- **Description:** Displays the user dashboard.

#### Profile
- **URL:** `/user/profile`
- **Method:** `GET`
- **Description:** Displays the user's profile page.

#### Settings
- **URL:** `/user/settings`
- **Method:** `GET`
- **Description:** Displays the user's settings page.

#### Change Password
- **URL:** `/user/changePassword`
- **Method:** `POST`
- **Description:** Changes the user's password.

### Contact Endpoints

#### Add Contact Form
- **URL:** `/user/addContact`
- **Method:** `GET`
- **Description:** Displays the form to add a new contact.

#### Process Contact
- **URL:** `/user/processContact`
- **Method:** `POST`
- **Description:** Processes and saves a new contact.

#### Show Contacts
- **URL:** `/user/showContacts/{page}`
- **Method:** `GET`
- **Description:** Displays a paginated list of contacts.

#### Contact Detail
- **URL:** `/user/{cId}/contact`
- **Method:** `GET`
- **Description:** Displays the details of a specific contact.

#### Delete Contact
- **URL:** `/user/delete/{cId}`
- **Method:** `GET`
- **Description:** Deletes a specific contact.

#### Update Contact Form
- **URL:** `/user/updateContact/{cId}`
- **Method:** `POST`
- **Description:** Displays the form to update a contact.

#### Process Update
- **URL:** `/user/processUpdate`
- **Method:** `POST`
- **Description:** Processes and saves the updated contact information.

### Search Endpoints

#### Search Contacts
- **URL:** `/search/{query}`
- **Method:** `GET`
- **Description:** Searches contacts by name for the logged-in user.

### Password Management Endpoints

#### Forgot Password
- **URL:** `/forgot`
- **Method:** `GET`
- **Description:** Displays the form to enter the email for password reset.

#### Send OTP
- **URL:** `/sendOtp`
- **Method:** `POST`
- **Description:** Sends an OTP to the user's email for password reset.

#### Verify OTP
- **URL:** `/verifyOtp`
- **Method:** `POST`
- **Description:** Verifies the OTP sent to the user's email.

#### Change Password
- **URL:** `/changePawwword`
- **Method:** `POST`
- **Description:** Changes the user's password using the verified OTP.

## Technologies Used

- Spring Boot
- Spring Security
- Thymeleaf
- Hibernate
- MySQL
