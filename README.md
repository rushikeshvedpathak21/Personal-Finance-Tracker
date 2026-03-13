 Personal Finance Tracker

A full-stack web application to track income, expenses, and budgets.

 Tech Stack

- Backend: Java 17, Spring Boot 3.2, Spring Security
- Database: MySQL 8
- Frontend: HTML, CSS, JavaScript
- Build: Maven

 Project Structure

```
personal-finance-tracker/
├── finance-tracker-backend/   # Spring Boot REST API
├── finance-tracker-frontend/  # HTML/CSS/JS frontend
└── database/
    └── schema.sql             # Database setup script
```

 Setup Instructions

 1. Database
sql
mysql -u root -p < database/schema.sql


Update `application.properties` with your MySQL credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/finance_tracker
spring.datasource.username=root
spring.datasource.password=your_password
```

 2. Backend
```bash
cd finance-tracker-backend
mvn spring-boot:run
```
API runs on `http://localhost:8080`

 3. Frontend
Open `finance-tracker-frontend/login.html` in your browser or serve with Live Server.

 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/users/register` | Register new user |
| POST | `/api/users/login` | Login |
| POST | `/api/expenses` | Add expense |
| GET | `/api/expenses` | Get all expenses |
| GET | `/api/expenses/search` | Search by category/keyword |
| POST | `/api/income` | Add income |
| GET | `/api/income` | Get all income |
| POST | `/api/budgets` | Set budget limit |
| GET | `/api/budgets` | Get budgets with spending |

 Features

- User registration and login with BCrypt password hashing
- Track expenses by category with search/filter
- Record income with source and frequency
- Set monthly budget limits per category
- Dashboard with income, expense, and balance summary
