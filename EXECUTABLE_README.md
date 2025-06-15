# Cafe Management System - Executable Guide

**Authors:** Lorenz Soriano (18011129) & Phoebe Cruz (21143576)  
**Course:** COMP603 - Program Design and Construction

---

## 🚀 Running the Application Without IDE

### Prerequisites
- **Java 21 or later** must be installed on your system
- No IDE required - the application runs as a standalone executable

### Quick Start

#### Option 1: Use the Launcher Scripts (Recommended)

**For macOS/Linux:**
```bash
./run-cafe.sh
```

**For Windows:**
```cmd
run-cafe.bat
```

#### Option 2: Direct JAR Execution
```bash
java -jar target/CafeInventorySystem-1.0-SNAPSHOT-executable.jar
```

---

## 📁 File Structure

### Main Executable

- `target/CafeInventorySystem-1.0-SNAPSHOT-executable.jar` - Complete standalone application

### Launcher Scripts

- `run-cafe.sh` - Unix/Linux/macOS launcher with system checks
- `run-cafe.bat` - Windows launcher with system checks

### Supporting Files

- `cafeDB/` - Derby database files (auto-created)
- `receipts/` - Generated receipt files
- `src/main/resources/images/` - User profile photos
- `src/main/resources/icons/` - For icons specifically 

---

## 🔐 Login Credentials

The application comes with pre-configured accounts:

| Role              | Username | Password    | Access Level                        |
| ----------------- | -------- | ----------- | ----------------------------------- |
| **Administrator** | `admin`  | `admin123`  | Full system access, user management |
| **Staff**         | `enz`    | `whoyou123` | Menu operations, order processing   |

---

## 🛠️ Building from Source (If Needed)

If you need to rebuild the executable:

```bash
# Clean and build the project
mvn clean package

# The executable JAR will be created at:
# target/CafeInventorySystem-1.0-SNAPSHOT-executable.jar
```

---

## 🎯 Key Features

### User Management

- Complete CRUD operations for user accounts
- Photo upload functionality
- Form validation and placeholder text
- Role-based access control

### Menu System

- Dynamic menu loading from database
- Category-based organization
- Real-time inventory management

### Order Processing

- Shopping cart functionality
- Payment processing with multiple methods
- Automatic receipt generation

### Database

- Embedded Derby database (no setup required)
- Automatic table creation and initialization
- Proper connection management

---

## 🔧 Troubleshooting

### Common Issues

**"Java is not installed or not in PATH"**

- Install Java 21 or later from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)
- Ensure Java is added to your system PATH

**"Executable JAR file not found"**

- Run `mvn clean package` to build the application
- Ensure you're in the project root directory

**Database connection errors**

- The application automatically creates the Derby database
- Ensure you have write permissions in the project directory
- Database files are stored in the `cafeDB/` folder

**GUI not displaying properly**

- Ensure you're using Java 21 or later
- Try running with different look-and-feel settings

---

## 🚀 Distribution

### Sharing the Application

To share the application with others:

1. **Copy the entire project folder** (includes database and resources)
2. **Or share just the JAR file** (target/CafeInventorySystem-1.0-SNAPSHOT-executable.jar)
	   - Recipients will need to create their own database
	   - Application will auto-initialize on first run

### Platform Compatibility

The JAR file is platform-independent and will run on:

- ✅ Windows (7, 8, 10, 11)
- ✅ macOS (Intel and Apple Silicon)
- ✅ Linux (most distributions)
- ✅ Any system with Java 21+

---
## 📞 Support

For issues or questions:
- **Lorenz Soriano:** xvq7775@autuni.ac.nz
- **Phoebe Cruz:** tfs0091@autuni.ac.nz
- **GitHub:** https://github.com/Jlowkee19/comp603-pdc-project2

---

## 📝 License

This project is developed for academic purposes as part of COMP603 coursework at Auckland University of Technology

**Note:** This is a complete, standalone application that doesn't require any IDE or development environment to run. Simply ensure Java 21+ is installed and use the provided launcher scripts for the best experience.