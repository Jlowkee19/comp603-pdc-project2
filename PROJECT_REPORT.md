# Cafe Management System - Project Report

**Course:** COMP603 - Programming for Database Connectivity  
**Authors:** Lorenz Soriano (18011129) & Phoebe Cruz (21143576)  
**Date:** June 15, 2025

---

## Project Setup

### System Requirements
- **Java Version:** JDK 21+
- **Database:** Apache Derby (embedded)
- **GUI Framework:** Java Swing with FlatLaf Look & Feel
- **Build Tool:** Maven

### Login Credentials
The system comes with pre-configured user accounts:

| Username | Password | Role | Access Level |
|----------|----------|------|-------------|
| `admin` | `admin123` | Administrator | Full system access, user management |
| `enz` | `whoyou123` | Barista | Menu operations, order processing |

### Running the Application
1. Clone the repository
2. Navigate to project directory
3. Run: `java -cp target/classes com.cafe.CafeApp`
4. Use the credentials above to login

---

## GitHub Repository
**URL:** https://github.com/Jlowkee19/comp603-pdc-project2

The repository contains:
- Complete source code with proper package structure
- Database initialization scripts (`init_db.sql`)
- Maven configuration and dependencies
- Documentation and setup instructions
- Version history showing collaborative development

---

## Team Member Contributions

### Lorenz Soriano (18011129) - 50% Contribution
**Primary Responsibilities:**
- **Database Architecture:** Designed and implemented Derby database integration with connection management
- **Core Application Framework:** Developed main application controller, login system, and navigation structure
- **Menu System:** Implemented dynamic menu loading, category management, and order processing
- **Payment Integration:** Created payment dialog and receipt generation system
- **Code Integration:** Managed branch merging, conflict resolution, and version control

**Key Files Developed:**
- `CafeApp.java`, `CafeController.java`, `Database.java`
- `MenuItemDAO.java`, `MenuItemDAOImpl.java`, `PaymentDAO.java`
- `LoginFrame.java`, `PaymentDialog.java`
- Database schema and initialization scripts

### Phoebe Cruz (21143576) - 50% Contribution
**Primary Responsibilities:**
- **User Management System:** Designed and implemented comprehensive CRUD operations for user accounts
- **GUI Development:** Created user management dialogs with advanced form features
- **Photo Upload Functionality:** Implemented image upload, storage, and display system
- **Form Validation:** Developed placeholder text system and comprehensive input validation
- **User Experience:** Enhanced GUI components with modern styling and user-friendly features

**Key Files Developed:**
- `UserAccDialog.java`, `UserAccDialog_2.java` and corresponding form files
- `UserAccountDAO.java`, `UserAccountDAOImpl.java`, `RoleDAO.java`
- `UserAccount.java`, `Role.java` models
- Photo upload and form validation systems

### Collaborative Efforts
Both team members contributed equally to:
- **System Design:** Architectural decisions and pattern implementation
- **Testing & Debugging:** Comprehensive testing of all system components
- **Documentation:** Code comments, README files, and technical documentation
- **Integration:** Ensuring seamless integration between different system components

---

## Technical Highlights

### Key Features Implemented
- **User Management:** Complete CRUD operations with photo upload and form validation
- **Authentication System:** Secure login with role-based access control
- **Menu Management:** Dynamic menu display with category organization
- **Order Processing:** Cart functionality with payment processing and receipt generation
- **Database Integration:** Robust Derby database with proper connection management

### Code Quality Standards
- **Design Patterns:** DAO pattern for data access, Singleton for database connections
- **Error Handling:** Comprehensive exception handling and user feedback
- **Code Organization:** Clean package structure with separation of concerns
- **Version Control:** Proper Git workflow with meaningful commit messages

---

## Conclusion

This project demonstrates a professional-grade cafe management system with comprehensive functionality. Both team members contributed equally to the development process, bringing complementary skills in database management, GUI development, and system integration. The result is a robust, user-friendly application that meets all project requirements and showcases advanced programming concepts in database connectivity and GUI development.

**Final Note:** All code has been thoroughly tested and documented. The system is ready for production use and demonstrates professional software development practices.