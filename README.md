# Tution billing system
This is a web-based application for managing the billing system for operations in tutions.

# Technologies 

- Java 8
- Spring boot
- MySQ
- Maven

# How to setup

1. Clone the project. `git clone https://github.com/dilum1995/tution-billing-system.git`

2. Open it on IDE

3. Install dependencies using `maven install`

4. Setup MySQL and change **application.properties** file accordingly.

5. Run the project

# API Endpoints 

| Endpoint                                                      | Http Method | Headers    | Params | Body                                                                                                                                                                                            | Response                                        | Description                                                 |
|---------------------------------------------------------------|-------------|------------|--------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------|-------------------------------------------------------------|
| /authenticate                                                 | POST        |            |        | `{      "username" :   "admin@abcinstitute.lk" ,      "password" :   "admin@abcinstitute123" }`                                                                                                 | Unique token                                    | User login                                                  |
| /institutes                                                   | GET         |            |        |                                                                                                                                                                                                 | List of institutes                              | To get institutes.                                          |
| /institutes                                                   | GET         |            | email  |                                                                                                                                                                                                 | Institute data                                  | To get specific institute data.                             |
| /institutes/{institute_id}/classes                            | GET         | Auth token |        |                                                                                                                                                                                                 | List of classes                                 | To get the list of classes registered under the institution |
| /institutes/{institute_id}/students/{student_id}/monthly-bill | GET         | Auth token |        |                                                                                                                                                                                                 | Monthly bill related data                       | To get monthly bill of each student.                        |
| /institutes                                                   | POST        | Auth token |        | `{      "email" :   "admin@sigma1.lk" ,      "password" :   "admin@sigma1123" ,      "address" :   "Colombo" ,      "contact" :   "0771234567" }`                                               | Registered institute data.                      | To register an institute.                                   |
| /institutes/{institute_id}/classes                            | POST        | Auth token |        | `{      "subject" :   "Biology" ,      "day" :   "Wednesday" ,      "time" :   "4.00PM - 6.00PM" ,      "lectureHall" :   "B" ,      "instructor" :   "Adam" ,      "monthlyFee" :   1000.00 }` | Registered class data.                          | To register a class.                                        |
| /classes/{class_id}/students                                  | POST        | Auth token |        | `{      "studentId" :   1 }`                                                                                                                                                                    | Student enrolled classes including the new one. | To register a student to a class.                           |
| /students                                                     | POST        | Auth token |        | `{      "name" :   "Jane" ,      "address" :   "Colombo" ,      "contact" :   "0771234567" ,      "gender" :   "f" }`                                                                           | Registered student data.                        | To register a student.                                      |
