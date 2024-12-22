# **School Management System**

## **Project Overview**
This project is a **School Management System**, a comprehensive solution designed to simplify administrative processes and enhance the educational experience for students and staff. The system is built with a centralized database, ensuring seamless communication and data sharing between the **backend (Flask API)** and various client platforms, including **Android**, **iOS**, and **Desktop**. 

The project is a collaborative effort by talented software architecture students, aiming to demonstrate cutting-edge design patterns and efficient cross-platform application development.

---

## **Core Features**
1. **Centralized Database**:  
   - All data is stored and managed in a centralized JSON database (`db.json`), facilitating easy retrieval and updates across all connected clients.
   - Data includes information about students, courses, grades, events, calendars, and more.

2. **Backend API**:  
   - A robust Flask backend handles all interactions with the database and provides RESTful APIs for seamless communication.
   - Endpoints include functionality to manage students, courses, grades, events, calendars, and shopping cart features for course materials.

3. **Cross-Platform Clients**:  
   - Clients can access the system from **Android**, **iOS**, or **Desktop** platforms, ensuring accessibility and ease of use.

4. **Dynamic Course Management**:  
   - Features like subscribing/unsubscribing students to courses and retrieving their enrolled/unsubscribed course lists.

5. **E-Commerce Features**:  
   - Shopping cart functionality for adding, removing, and updating quantities of course materials.
   - Ability to place orders for educational resources.

---

## **Dependencies**

### Backend (Flask)
The backend application relies on the following:
- **Python**: Flask framework and supporting libraries.
- **Database**: A JSON file (`db.json`) is used to store data centrally.

### Frontend (Clients)
The client applications rely on various libraries and dependencies. Below is a summary of the main dependencies used for the client-side development:

#### Versions
| Dependency                          | Version  |
|-------------------------------------|----------|
| **Android Gradle Plugin (AGP)**    | 8.5.2    |
| Android Compile SDK                | 34       |
| Android Min SDK                    | 24       |
| Android Target SDK                 | 34       |
| androidx-activity-compose          | 1.9.3    |
| androidx-appcompat                 | 1.7.0    |
| androidx-constraintlayout          | 2.1.4    |
| androidx-core-ktx                  | 1.13.1   |
| androidx-espresso-core             | 3.6.1    |
| androidx-lifecycle                 | 2.8.3    |
| androidx-material                  | 1.12.0   |
| androidx-test-junit                | 1.2.1    |
| compose-multiplatform              | 1.7.0    |
| junit                              | 4.13.2   |
| kotlin                             | 2.0.21   |
| kotlinx-coroutines                 | 1.9.0    |
| kotlinx-datetime                   | 0.4.0    |
| kotlinx-serialization-json         | 1.6.0    |
| ktor-serialization-kotlinx-json    | 2.3.4    |
| lifecycle-viewmodel-ktx            | 2.8.7    |
| ui-tooling-preview-android         | 1.7.5    |
| places                             | 4.1.0    |
| material3-android                  | 1.3.1    |
| material3-desktop                  | 1.3.1    |
| lifecycle-livedata-core-ktx        | 2.8.7    |
| foundation-android                 | 1.7.5    |

For a full list of libraries and their versions, refer to the **`[libraries]`** section in the provided project configuration.

---

## **Backend API Documentation**

### **Base URL**
- `http://localhost:3323`

### **Endpoints**
| Endpoint                          | Method | Description                                       |
|-----------------------------------|--------|---------------------------------------------------|
| `/students`                       | GET    | Retrieve all students.                           |
| `/student/<matricule>`            | GET    | Retrieve details of a student by matricule.      |
| `/grades/<matricule>`             | GET    | Retrieve grades for a student by matricule.      |
| `/courses`                        | GET    | Retrieve all courses.                            |
| `/get_subbed_courses/<student_id>`| GET    | Retrieve courses a student is subscribed to.     |
| `/get_unsubed_courses/<student_id>` | GET  | Retrieve courses a student is not subscribed to. |
| `/sub_student/<course_id>`        | POST   | Subscribe a student to a course.                |
| `/unsub_student/<course_id>`      | POST   | Unsubscribe a student from a course.            |
| `/cart`                           | GET    | Retrieve the shopping cart.                     |
| `/cart`                           | POST   | Add items to the cart.                          |
| `/cart/<syllabus_id>`             | PUT    | Update cart item quantity.                      |
| `/cart/<syllabus_id>`             | DELETE | Remove an item from the cart.                   |
| `/cart`                           | DELETE | Clear the shopping cart.                        |
| `/orders`                         | POST   | Place an order.                                 |

For full details about API error handling, data structure, and sample requests, refer to the [Backend README](./data_api/README_DB.md).

---

## **Database Structure**

The database (`db.json`) is structured as follows:
```json
{
  "students": [...],
  "grades": [...],
  "courses": [...],
  "events": [...],
  "calendar": {...},
  "cart": [...],
  "orders": [...]
}
```
Ensure this file is available and correctly formatted in the Flask backend directory.

---

## **How to Run the Application**

### Backend (Flask)
1. **Install Flask**: Run `pip install flask`.
2. **Start the Server**: Run the script using `python app.py`. The server will start on `localhost:3323`.

### Client (Android/iOS/Desktop)
#### **Android**
1. Open the project in **Android Studio**.
2. Sync Gradle to resolve dependencies.
3. Select a device or emulator and click **Run**.

#### **Desktop**
1. Configure and run the `ComposeApp:Run` setup in your IDE.

#### **iOS**
1. Open the project in **Xcode**.
2. Select a simulator or device and click **Run**.

---
