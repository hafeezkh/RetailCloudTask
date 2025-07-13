  <h1>Employee Management System</h1>

  <h2>Overview</h2>
  <p>
This Employee Management System is a RESTful API built using Java Spring Boot following the MVC (Model-View-Controller) pattern. 
The system allows managing employees and departments with CRUD operations and includes features such as pagination, reporting chains, and department expansions.
</p>
<p>
Data is stored in a PostgreSQL database, and all business logic resides in the Service layer to keep the controllers clean. The application uses JPA for database interactions and Jackson for JSON processing.
</p>
<p>
You can test the APIs easily using Postman by sending requests to the endpoints defined in the project.
</p>
  <ul>
    <li><strong>Controller:</strong> Handles HTTP requests and responses</li>
    <li><strong>Service:</strong> Contains business logic</li>
    <li><strong>Repository:</strong> Interacts with the database using JPA</li>
  </ul>

  <h2>Features</h2>
  <ul>
    <li>Create, update, delete employees and departments</li>
    <li>Assign employees to departments and move them between departments</li>
    <li>Paginated GET APIs for efficient data retrieval</li>
    <li>Prevents deletion of departments with assigned employees</li>
    <li>Option to expand department data with employee details</li>
  </ul>


  <h2>Database Schema (SQL)</h2>
  <pre><code>
CREATE TABLE departments (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  creation_date DATE,
  head_id BIGINT,
  CONSTRAINT fk_head FOREIGN KEY (head_id) REFERENCES employees(id)
);

CREATE TABLE employees (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  dob DATE,
  salary DECIMAL(10, 2),
  address VARCHAR(500),
  role VARCHAR(100),
  joining_date DATE,
  yearly_bonus_percentage FLOAT,
  reporting_manager_id BIGINT,
  department_id BIGINT,
  CONSTRAINT fk_manager FOREIGN KEY (reporting_manager_id) REFERENCES employees(id),
  CONSTRAINT fk_department FOREIGN KEY (department_id) REFERENCES departments(id)
);
  </code></pre>

  <h2>Sample JSON Schemas</h2>
  
  <h3>Create Employee Request</h3>
  <pre><code>{
  "name": "John Doe",
  "dob": "1990-05-20",
  "salary": 55000.00,
  "address": "123 Main St, City",
  "role": "Developer",
  "joiningDate": "2023-01-15",
  "yearlyBonusPercentage": 10.5,
  "reportingManagerId": 2,
  "departmentId": 1
}</code></pre>

  <h3>Employee Response</h3>
  <pre><code>{
  "id": 10,
  "name": "John Doe",
  "dob": "1990-05-20",
  "salary": 55000.00,
  "address": "123 Main St, City",
  "role": "Developer",
  "joiningDate": "2023-01-15",
  "yearlyBonusPercentage": 10.5,
  "reportingManager": {
    "id": 2,
    "name": "Jane Smith"
  },
  "department": {
    "id": 1,
    "name": "IT"
  }
}</code></pre>

  <h3>Create Department Request</h3>
  <pre><code>{
  "name": "IT",
  "creationDate": "2022-01-01",
  "departmentHeadId": 2
}</code></pre>

  <h2>API Endpoints</h2>
  <ul>
    <li><strong>Employee APIs</strong>
      <ul>
        <li><code>POST /api/employee</code> - Create employee(s)</li>
        <li><code>PUT /api/employee/{id}</code> - Update employee</li>
        <li><code>GET /api/employee/{id}</code> - Get employee by id</li>
        <li><code>GET /api/employee?lookup=true</code> - List employee names & IDs</li>
        <li><code>DELETE /api/employee/{id}</code> - Delete employee</li>
        <li><code>PUT /api/employee/{employeeId}/department/{departmentId}</code> - Change employee's department</li>
        <li><code>GET /api/employee?page=0</code> - Paginated list of employees</li>
      </ul>
    </li>
    <li><strong>Department APIs</strong>
      <ul>
        <li><code>POST /api/department</code> - Add department</li>
        <li><code>PUT /api/department/{id}</code> - Update department</li>
        <li><code>GET /api/department/{id}</code> - Get department by id</li>
        <li><code>GET /api/department?expand=employee</code> - Get department with employees</li>
        <li><code>GET /api/department?page=0</code> - Paginated list of departments</li>
        <li><code>DELETE /api/department/{id}</code> - Delete department (fails if employees exist)</li>
      </ul>
    </li>
  </ul>

</body>
</html>
