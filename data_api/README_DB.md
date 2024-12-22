# Flask App Readme

## Installation and Setup

1. **Install Python and Flask**:  
   Ensure Python is installed on your system. You can install Flask by running:
   ```bash
   pip install flask
   ```

2. **Database Setup**:  
   Ensure a `db.json` file exists in the root directory of the project. The file should follow the structure below:
   ```json
   {
     "news": [...],
     "calendar": {...},
     "courses": [...],
     "students": [...],
     "student_grades": [...],
     "orientations": [...],
     "syllabus": [...],
     "cart": [],
     "orders": []
   }
   ```

3. **Run the Application**:  
   Start the server using the following command:
   ```bash
   python app.py
   ```
   The application will run on `localhost:5000` by default. If the port is unavailable, another port will be chosen, or you can specify one using the `PORT` environment variable.

---

## Endpoints

### General Endpoints
#### 1. **Retrieve News**
   - **URL**: `/news`
   - **Method**: `GET`
   - **Description**: Returns all news articles from the database.

#### 2. **Retrieve Calendar**
   - **URL**: `/calendar`
   - **Method**: `GET`
   - **Description**: Returns the calendar object from the database.

### Student Endpoints
#### 3. **Retrieve All Students**
   - **URL**: `/students`
   - **Method**: `GET`
   - **Description**: Returns a list of all students.

#### 4. **Retrieve a Student by Matricule**
   - **URL**: `/student/<matricule>`
   - **Method**: `GET`
   - **Description**: Fetches a student by their matricule (ID).  
   - **Response**:
     - **200**: Returns the student's details.
     - **404**: If no student matches the matricule.

#### 5. **Retrieve Grades for a Student**
   - **URL**: `/grades/<matricule>`
   - **Method**: `GET`
   - **Description**: Fetches grades for a student using their matricule.  
   - **Response**:
     - **200**: Returns the grades.
     - **404**: If no grades are found.

### Course Endpoints
#### 6. **Retrieve All Courses**
   - **URL**: `/courses`
   - **Method**: `GET`
   - **Description**: Returns a list of all courses.

#### 7. **Retrieve Subscribed Courses**
   - **URL**: `/get_subbed_courses/<student_id>`
   - **Method**: `GET`
   - **Description**: Fetches courses the student is enrolled in.

#### 8. **Retrieve Unsubscribed Courses**
   - **URL**: `/get_unsubed_courses/<student_id>`
   - **Method**: `GET`
   - **Description**: Fetches courses the student is not enrolled in.

#### 9. **Subscribe a Student to a Course**
   - **URL**: `/sub_student/<course_id>`
   - **Method**: `POST`
   - **Payload**:
     ```json
     {
       "student_id": "..."
     }
     ```
   - **Description**: Enrolls a student in a course.

#### 10. **Unsubscribe a Student from a Course**
   - **URL**: `/unsub_student/<course_id>`
   - **Method**: `POST`
   - **Payload**:
     ```json
     {
       "student_id": "..."
     }
     ```
   - **Description**: Removes a student from a course.

### Cart Endpoints
#### 11. **Retrieve Cart**
   - **URL**: `/cart`
   - **Method**: `GET`
   - **Description**: Fetches the current cart items.

#### 12. **Add to Cart**
   - **URL**: `/cart`
   - **Method**: `POST`
   - **Payload**:
     ```json
     {
       "id": 1,
       "quantity": 2
     }
     ```
   - **Description**: Adds an item to the cart or updates its quantity.

#### 13. **Update Cart Quantity**
   - **URL**: `/cart/<syllabus_id>`
   - **Method**: `PUT`
   - **Payload**:
     ```json
     {
       "quantity": 3
     }
     ```
   - **Description**: Updates the quantity of an item in the cart.

#### 14. **Remove from Cart**
   - **URL**: `/cart/<syllabus_id>`
   - **Method**: `DELETE`
   - **Description**: Removes an item from the cart.

#### 15. **Clear Cart**
   - **URL**: `/cart`
   - **Method**: `DELETE`
   - **Description**: Empties the cart.

### Order Endpoints
#### 16. **Add an Order**
   - **URL**: `/orders`
   - **Method**: `POST`
   - **Payload**:
     ```json
     {
       "student_id": "...",
       "items": [
         {"id": 1, "quantity": 2}
       ]
     }
     ```
   - **Description**: Places an order for the items in the cart. Assigns a unique order ID.

---

## Error Handling
- **400 Bad Request**: Returned if a required field is missing or invalid in the request payload.
- **404 Not Found**: Returned if a resource (e.g., student, course, or cart item) is not found.
- **Response Format**:
  ```json
  {
    "error": "Error message here"
  }
  ```

---

## Notes
- **Customization**: You can modify the database structure and routes as needed to fit your application's requirements.
- **Default Port**: The app runs on port `5000`. You can specify a custom port by setting the `PORT` environment variable.
- **Persistence**: Any changes made via the app (e.g., adding items to the cart) are saved to the `db.json` file. Ensure write permissions are enabled for this file.

---