# Portfolio Service | Java + SpringBoot + WebFlux + Redis + MySQL

This is Spring Boot service for managing portfolio. Built with multi-module setup so admin and public APIs are separated. This repo has been developed and used for my portfolio project (visit: https://alvayonara.com/).

## What's Inside?

**Backend:** Java 21, Spring Boot 3.3.5, WebFlux, Spring Security with JWT

**Database & Cache:** Railway MySQL, Upstash Redis

**File Storage:** Amazon S3

**Hosting:** Google Cloud Run

**API Docs:** Swagger UI available at `/swagger-ui.html`

**CI/CD:** GitHub Actions for auto deploy to Cloud Run

**Containerization:** Dockerfile for building Docker image

## Project Structure

Split into 4 modules:

```
portfolio-service/
├── portfolio-common/      # Common stuff (config, security, S3)
├── portfolio-admin/       # Admin panel (needs login)
├── portfolio-public/      # Public API (no login needed)
└── portfolio-app/         # Main app
```

**portfolio-common** - Shared code like security setup, S3 file upload service, error handling, and Redis for rate limiting.

**portfolio-admin** - Admin stuff only. Manage education, work experience, profile, projects, skills, and resume. Need admin login to access.

**portfolio-public** - Public pages. Show profile, projects, work history, skills. Also has contact form with rate limit protection.

**portfolio-app** - Main entry point. Has the app config files and database setup.

## How to Run

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
./mvnw clean install -DskipTests
./mvnw spring-boot:run -pl portfolio-app
```

Check Swagger docs at: `http://localhost:8080/swagger-ui.html`

## Main Features

Separated into modules for easier maintenance, fast and non blocking. All S3 upload logic in one place. Admin pages need JWT login. Public pages have rate limiting. This project is ready to deploy on Cloud Run.

## What's Next?

Planning to add more improvements:

- Unit tests for all services and controllers
- Run tests in GitHub Actions before deploying
- SonarQube for code quality check
- Integration tests for API endpoints

## License

MIT License - use this as reference for building your own portfolio service.

