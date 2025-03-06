📌 Data Personal App
A simple CRUD application for managing personal data using Java Spring Boot (back-end) and HTML, CSS, jQuery, Bootstrap (front-end).

🚀 How to Run the Application
1️⃣ Prerequisites
Make sure you have the following installed on your system:

-Java 17+
-Maven
-PostgreSQL

2️⃣ Database Setup
-Create a new database in PostgreSQL: 
      CREATE DATABASE personal-data;
-Update the application.properties file in the back-end project to match your PostgreSQL credentials:
      spring.datasource.url=jdbc:postgresql://localhost:5432/personal_data  
      spring.datasource.username=your_username  
      spring.datasource.password=your_password  
      spring.jpa.hibernate.ddl-auto=update  
      spring.jpa.show-sql=true
      server.port=8090

3️⃣ Running the Back-End (Spring Boot API)
-Navigate to the back-end folder
-Build & run the application : 
      mvn clean compile && mvn spring-boot:run
-Access the API in your browser/Postman :
      http://localhost:8090/api/data

4️⃣ Running the Front-End
-Navigate to the front-end folder
-Run with Live Server (Optional)
    If using Live Server extension in VS Code, simply click "Go Live".
    Alternatively, open index.html in a browser.


5️⃣ Available Features
✅ Add new data
✅ Edit data
✅ Delete data
✅ Detail data
✅ Search by NIK & Name

🔧 API Endpoints (Spring Boot)
Method	  Endpoint	          Description
GET	        /api/data	          Retrieve all data
GET           /api/data/find      Find data by NIK & Name
POST	        /api/data	          Add new data
PUT	        /api/data/{nik}	    Update data
DELETE	  /api/data/{nik}     Delete data
