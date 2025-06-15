# Cafe Management System - Video Demo Script
**Duration: Under 5 minutes**  
**Authors: Lorenz Soriano (18011129) & Phoebe Cruz (21143576)**

---

## **INTRODUCTION** (30 seconds)
**[Speaker 1 - Lorenz]**
"Hello, I'm Lorenz Soriano, student ID 18011129."

**[Speaker 2 - Phoebe]** 
"And I'm Phoebe Cruz, student ID 21143576."

**[Speaker 1]**
"Today we're demonstrating our Cafe Management System built in Java with Swing GUI and Derby database. Our system features comprehensive user management, menu operations, payment processing, and role-based access control."

---

## **SYSTEM ARCHITECTURE OVERVIEW** (45 seconds)
**[Speaker 2]**
"Let me explain our system architecture. We implemented the DAO pattern for database operations, with separate layers for:
- **Models**: UserAccount, Role, MenuItem, Payment classes
- **Data Access**: UserAccountDAO, RoleDAO, MenuItemDAO interfaces with implementations
- **Database**: Derby embedded database with connection management
- **GUI**: Swing components with custom dialogs for user management"

**[Show code briefly]**
"Our Database class manages connections using the singleton pattern, and we initialize tables from init_db.sql automatically."

---

## **APPLICATION DEMONSTRATION** (2 minutes 30 seconds)

### **Login System** (20 seconds)
**[Speaker 1]**
**[Launch application]**
"The application starts with a secure login system. Let me log in as admin with username 'admin' and password 'admin123'."
**[Demonstrate login]**

### **Main Dashboard** (30 seconds)
**[Speaker 2]**
"Once logged in, we see the main cafe interface with:
- Dynamic menu categories on the left
- Menu items displayed as clickable buttons
- Order management table on the right
- User-specific menu bar showing the logged-in user's name"

**[Navigate through menu categories]**
"Notice how menu items are dynamically loaded from the database and organized by categories like Coffee, Food, and Pastries."

### **User Management System** (1 minute 20 seconds)
**[Speaker 1]**
"Now let's demonstrate our comprehensive user management system. I'll go to Setup → User to open the User Management dialog."

**[Open UserAccDialog]**
"This dialog shows all users in a searchable table with real-time filtering. Let me demonstrate the CRUD operations:"

**[Demonstrate New User]**
"Clicking 'New' opens our user form with placeholder text in all fields. Notice how the placeholders disappear when we click in each field."

**[Show photo upload]**
"Users can upload profile photos by clicking on the photo area. The images are automatically saved to src/main/resources/images/ with unique filenames."

**[Demonstrate form validation]**
"The form includes comprehensive validation - if I try to submit without filling required fields, it shows appropriate error messages."

**[Show sequential ID generation]**
"When we create a new user, notice the ID is generated sequentially, not randomly - we fixed this using Derby's identity sequence management."

**[Demonstrate Update/Delete]**
"I can also update existing users or delete them. The table refreshes automatically after each operation."

### **Order Processing** (20 seconds)
**[Speaker 2]**
"Back to the main interface, let me demonstrate order processing. I'll add some items to an order and process payment."
**[Add items, show payment dialog]**
"Our payment system generates receipts and handles different payment methods."

---

## **CODE EXPLANATION** (1 minute 15 seconds)

### **Database Layer** (25 seconds)
**[Speaker 1]**
**[Show Database.java]**
"Our Database class uses the singleton pattern with automatic Derby initialization. The initializeDatabase method reads our SQL schema and creates tables if they don't exist."

**[Show UserAccountDAOImpl.java]**
"Each DAO method gets a fresh database connection to avoid 'No current connection' errors. Notice how we handle duplicate usernames and provide proper error handling."

### **User Management Implementation** (25 seconds)
**[Speaker 2]**
**[Show UserAccDialog_2.java]**
"Our user form implements placeholder text using focus listeners. The setupPlaceholder method adds gray placeholder text that disappears on focus and returns if the field is empty."

**[Show photo upload code]**
"Photo upload uses JFileChooser with file filtering, copies images to our resources directory with unique timestamps, and stores the data as BLOB in the database."

### **GUI Integration** (25 seconds)
**[Speaker 1]**
**[Show CafeMain.java integration]**
"The user management integrates seamlessly with our main application through the Setup menu. We use callback mechanisms to refresh data after operations and modal dialogs for better user experience."

---

## **CONCLUSION** (20 seconds)
**[Speaker 2]**
"Our system demonstrates professional software engineering practices including:
- Clean architecture with DAO pattern
- Comprehensive error handling and validation
- User-friendly GUI with modern features
- Robust database integration
- Role-based security"

**[Speaker 1]**
"The complete source code is available on GitHub with proper version control and documentation. Thank you for watching our demonstration."