# OrangeHRM Test Automation Project

## Table of Contents
1. [Project Overview](#project-overview)
2. [Project Structure](#project-structure)
3. [Test Cases & Scenarios](#test-cases--scenarios)
4. [Setup & Installation](#setup--installation)
5. [Running the Tests](#running-the-tests)
6. [Dependencies & Libraries](#dependencies--libraries)
7. [Code Structure & Key Classes](#code-structure--key-classes)
8. [Test Data & Fixtures](#test-data--fixtures)
9. [Troubleshooting](#troubleshooting)
10. [Contributing Guidelines](#contributing-guidelines)

---

## Project Overview

### What is this project?

This project is an **automated testing suite** for [OrangeHRM](https://opensource-demo.orangehrmlive.com/), a web-based Human Resources (HR) management system. Instead of a human tester manually clicking through the website every time a new feature is released (logging in, adding employees, filling out timesheets, etc.), this project uses a robot-like program to do it automatically, exactly the same way every time, in seconds instead of many minutes.

Think of it like a self-driving test: you tell the computer "log in, add a new employee, save it, and check that everything worked," and the computer does exactly that inside a real browser window, then reports back "Pass" or "Fail."

### What does it actually test?

The suite currently checks five important areas of the OrangeHRM system:

1. **Logging in** — Can a user sign in successfully?
2. **Adding a new employee** — Can an admin create an employee record and give them login access with the correct permissions?
3. **Updating personal information** — Can an employee update their own address/contact details, and does the admin see the updated data afterward?
4. **Managing emergency contacts & dependents** — Can an employee add/remove emergency contacts and dependents (e.g., children, spouse), and does that data save correctly?
5. **Submitting and approving timesheets** — Can an employee fill in and submit a weekly timesheet, and can an admin then review and approve it?

Each of these mimics a real, everyday task that HR staff and employees would do in the actual application — so if a test fails, it likely means something in the real product broke.

### Key Technologies Used (in plain language)

| Technology | What it is | Why it's used here |
|---|---|---|
| **Java** | A widely used programming language | This is the language the entire test suite is written in |
| **Selenium WebDriver** | A tool that lets code "drive" a real web browser — clicking buttons, typing text, reading what's on screen | This is the core engine that actually opens Chrome/Firefox/Edge and interacts with OrangeHRM like a human would |
| **Cucumber** | A tool that lets you write test scenarios in plain English sentences (like "Given the user is on the login page") | This makes test scenarios readable by anyone, even people who don't code, since the scenarios read like a story |
| **TestNG** | A test-running framework for Java | This is what actually executes the tests, keeps track of pass/fail, and lets you run tests in groups |
| **Apache POI** | A library for reading/writing Microsoft Excel files | Used to pull test data (employee names, addresses, etc.) from an Excel spreadsheet instead of hardcoding it in the code |
| **Gson** | A library that reads JSON files (a simple structured text format) | Used to read configuration settings like the website URL and login credentials |
| **Log4j2** | A logging library | Records a detailed diary of everything the tests did, which is invaluable when a test fails and you need to figure out why |
| **WebDriverManager** | A helper library | Automatically downloads and configures the correct browser driver (e.g., ChromeDriver) so you don't have to do it by hand |
| **Allure** | A test reporting tool | Turns raw test results into a polished, visual report (with screenshots, timelines, and pass/fail charts) |
| **Maven** | A build and dependency management tool | Downloads all the above libraries automatically and knows how to compile and run the project with a single command |

**In short:** this is a Java + Selenium + Cucumber + TestNG project, built with Maven, that tests OrangeHRM's core HR workflows in a real browser and produces readable reports of what passed or failed.

---

## Project Structure

Here is the folder layout, with plain-language explanations of what lives where:

```
OrangeHRMProject/
├── pom.xml                              # The Maven "recipe" file — lists every library the project needs and how to build/run it
├── .gitignore                           # Tells Git (version control) which files/folders to ignore (e.g., build output, logs)
├── logs/                                 # Where log files (a running diary of test activity) are saved
├── allure-results/                       # Raw data used to generate the Allure visual test report
├── src/
│   ├── main/java/                       # "Production" code — reusable building blocks the tests rely on (not tests themselves)
│   │   ├── Pages/                       # One file per web page/screen in OrangeHRM (see "Page Object Model" below)
│   │   │   ├── BasePage.java            # Shared helper methods every page uses (click, type, wait, etc.)
│   │   │   ├── LoginPage.java           # Everything related to the Login screen
│   │   │   ├── DashboardPage.java       # The main menu/dashboard after logging in
│   │   │   ├── PIMPage.java             # The "PIM" (Personal Information Management) module landing page
│   │   │   ├── AddEmployeePage.java     # The "Add Employee" form
│   │   │   ├── EmployeeListPage.java    # The searchable list of all employees
│   │   │   ├── PersonalDetailsPage.java # The employee's personal details screen (after being created)
│   │   │   ├── MyInfoPage.java          # The "My Info" section: contact details, emergency contacts, dependents
│   │   │   ├── TimePage.java            # The Time/Timesheet module
│   │   │   ├── UserManagementPage.java  # The Admin > User Management search screen
│   │   │   └── EditUserPage.java        # The screen where an admin edits a user's role/status
│   │   ├── Models/
│   │   │   └── Employee.java            # A simple "container" object that holds all data about one test employee (name, address, etc.)
│   │   └── Utils/                       # Reusable helper tools used across the whole project
│   │       ├── DriverFactory.java       # Opens/closes the browser window
│   │       ├── ExcelManager.java        # Reads employee test data from the Excel file
│   │       ├── JsonFileManager.java     # Reads settings (URL, browser, admin credentials) from config.json
│   │       ├── EmployeeManager.java     # A shortcut that creates a brand-new employee end-to-end (used as a safety net — see below)
│   │       └── LoggerUtil.java          # Sets up the logging "diary" system
│   └── test/
│       ├── java/
│       │   ├── Runners/                 # One file per test suite — the "start button" for each set of scenarios
│       │   │   ├── LoginRunner.java
│       │   │   ├── AddEmployeeRunner.java
│       │   │   ├── UpdateInfoRunner.java
│       │   │   ├── EmergencyDependentRunner.java
│       │   │   └── TimesheetRunner.java
│       │   ├── StepDefs/                # The actual code behind each plain-English sentence in the feature files
│       │   │   ├── LoginStepDef.java
│       │   │   ├── AddEmployeeStepDef.java
│       │   │   ├── UpdatePersonalInfoStepDef.java
│       │   │   ├── EmergencyDependentStepDef.java
│       │   │   └── TimesheetStepDef.java
│       │   ├── Hook/
│       │   │   └── Hooks.java           # Code that runs automatically before/after every scenario (opens browser, takes screenshot on failure, closes browser)
│       │   └── Context/
│       │       └── TestContext.java     # A shared "notepad" that lets different steps in the same scenario pass data to each other (e.g., the employee just created)
│       └── resources/
│           ├── Features/                # Plain-English test scenarios, written in Gherkin (see below)
│           │   ├── login.feature
│           │   ├── addemployee.feature
│           │   ├── updateinfo.feature
│           │   ├── EmergencyDependent.feature
│           │   └── Timesheet.feature
│           ├── TestData/
│           │   └── Employees.xlsx       # Spreadsheet of sample employee data used to fill in test forms
│           ├── config/
│           │   └── config.json          # Settings: which browser to use, the website URL, and admin login credentials
│           └── log4j2.xml               # Configuration for how logs are formatted and where they're saved
```

### How test cases are organized and named

- Every real-world scenario (e.g., "add an employee") is described in a **`.feature` file** written in plain English, using the Given/When/Then style (explained in the next section).
- Each `.feature` file is paired with one **Runner** class (its "start button") and one **StepDef** ("step definition") class that contains the actual Java code executed for each sentence.
- Naming follows a simple, consistent pattern: the topic name is reused across all four related files. For example, the timesheet scenario uses `Timesheet.feature`, `TimesheetRunner.java`, and `TimesheetStepDef.java`.

---

## Test Cases & Scenarios

Test scenarios are written using **Gherkin**, a plain-English style that reads like: *Given* (the starting situation), *When* (an action happens), *Then* (what should be true afterward). You don't need to know any programming to read these — that's the whole point.

### 1. Login (`login.feature`) — Tag: `@LoginSuccessfully`

**Scenario: Successful Login**
- **Given** the user is on the login page
- **When** the user logs in with valid credentials
- **Then** the dashboard should be displayed

**In plain terms:** This confirms that a valid username and password successfully get someone into the system, landing them on the main dashboard. It's the simplest possible "does the front door open" check, and a good first test to run to confirm the whole system is working.

---

### 2. Adding a New Employee (`addemployee.feature`) — Tag: `@AddEmployee`

**Scenario: Admin creates a new employee**
- **Given** an admin is logged in
- **When** the admin navigates to the Add Employee page
- **And** enters the employee's details
- **And** saves the employee
- **And** assigns the employee a specific role (e.g., "Admin" or "ESS"/regular employee)
- **And** the admin logs out
- **And** the newly created employee logs in with their assigned credentials
- **Then** the employee should have the correct access rights for their assigned role

**In plain terms:** This is an end-to-end check of the full "hire someone" process, from an admin's point of view. It doesn't just check that the employee was saved — it actually logs in *as* the new employee afterward and checks that they can only see what someone in their role should see (for example, a regular employee should NOT see the Admin menu, but an Admin-role employee should).

---

### 3. Updating Personal Information (`updateinfo.feature`) — Tag: `@UpdatePersonalInfo`

**Scenario: Update Info**
- **Given** an employee is logged in
- **When** the employee updates their personal information (address, city, state, zip code, mobile number)
- **And** the employee logs out
- **And** an admin logs in
- **And** the admin opens that employee's profile
- **Then** the admin should see the updated personal information

**In plain terms:** This checks that when an employee updates their own contact details, those changes are actually saved to the system and are visible to HR/admin staff afterward — not just showing on the employee's own screen temporarily.

---

### 4. Emergency Contacts & Dependents (`EmergencyDependent.feature`) — Tag: `@AddEmergencyAndDependent`

**Scenario: Employee manages emergency contacts and dependents**
- **Given** an employee logs in
- **When** the employee deletes their existing emergency contact (if one exists)
- **And** adds a brand-new emergency contact
- **And** adds a dependent (e.g., a spouse or child)
- **Then** the emergency contact and dependent should be saved successfully
- **Given** an admin then logs into the system
- **When** the admin navigates to that employee's profile
- **Then** the admin reviews and confirms the updates match what was entered

**In plain terms:** This mirrors a common HR task — keeping "in case of emergency" information current. The test both makes the changes as the employee and then double-checks, as the admin, that the exact same name, relationship, and phone number/date of birth show up correctly.

---

### 5. Timesheet Submission & Approval (`Timesheet.feature`) — Tag: `@TimesheetSubmission`

**Scenario: Employee submits a weekly timesheet**
- **Given** an employee logs into the system
- **When** the employee opens "My Timesheet"
- **And** fills in and submits the timesheet (project, activity, and hours worked)
- **Then** the timesheet should show a status of "Submitted"
- **Given** an admin then logs into the Time module
- **When** the admin opens that employee's submitted timesheet
- **And** approves it
- **Then** the timesheet should show a status of "Approved"

**In plain terms:** This checks the full timesheet workflow used in almost every company: an employee logs their hours, submits them, and a manager/admin reviews and approves them. The test confirms the status label changes correctly at each stage ("Submitted" → "Approved"), which is exactly what a real manager would check.

### Summary Table

| Feature File | Tag | What It Proves |
|---|---|---|
| `login.feature` | `@LoginSuccessfully` | Users can log in successfully |
| `addemployee.feature` | `@AddEmployee` | Admins can hire employees and assign correct permission levels |
| `updateinfo.feature` | `@UpdatePersonalInfo` | Employees can update their info, and it's visible/correct to admins |
| `EmergencyDependent.feature` | `@AddEmergencyAndDependent` | Emergency contact & dependent data can be added, deleted, and verified |
| `Timesheet.feature` | `@TimesheetSubmission` | Timesheets can be submitted by employees and approved by admins |

---

## Setup & Installation

### Prerequisites

Before you start, you'll need the following installed on your computer. Don't worry if these terms are unfamiliar — a short explanation is included for each.

| Requirement | What it is | Minimum Version |
|---|---|---|
| **Java Development Kit (JDK)** | The engine that runs Java programs (like this test suite) | Java 21 |
| **Apache Maven** | A tool that automatically downloads all the code libraries this project needs, and knows how to build/run it | 3.8 or newer |
| **Google Chrome** (or Firefox / Edge) | A web browser — the tests will open and control this browser directly | Latest version |
| **Git** | A tool for downloading ("cloning") the project from a code repository | Any recent version |
| **An IDE (optional but recommended)** | A code editor with helpful features, such as IntelliJ IDEA or Eclipse | IntelliJ IDEA Community Edition (free) is a good choice |
| **Internet connection** | Required, because the tests run against a live demo website and Maven needs to download libraries the first time | — |

> **Note:** You do **not** need to manually download a "browser driver" file. A library called WebDriverManager handles this automatically the first time the tests run.

### Step-by-Step Installation

**1. Install Java 21**

Check if it's already installed by opening a terminal (Command Prompt on Windows, Terminal on Mac/Linux) and running:
```bash
java -version
```
If you see something like `openjdk version "21..."`, you're set. If not, download and install a JDK 21 build (for example, from [Adoptium](https://adoptium.net/)).

**2. Install Maven**

Check if it's installed:
```bash
mvn -version
```
If not, download it from the [official Maven site](https://maven.apache.org/download.cgi) and follow their installation instructions for your operating system.

**3. Clone the project**

Download a copy of the project to your computer using Git:
```bash
git clone <your-repository-url>
cd OrangeHRMProject
```
(If you received this project as a ZIP file instead of a Git repository, simply unzip it and open a terminal inside the extracted `OrangeHRMProject` folder.)

**4. Let Maven download all required libraries**

From inside the project folder, run:
```bash
mvn clean install -DskipTests
```
This reads the `pom.xml` file (the project's shopping list of libraries) and downloads everything needed — Selenium, Cucumber, TestNG, Apache POI, Gson, Log4j2, Allure, and WebDriverManager. The `-DskipTests` flag simply means "just set things up, don't run any tests yet."

**5. Review the configuration file**

Open `src/test/resources/config/config.json`. It looks like this:
```json
{
  "browser": "chrome",
  "url": "https://opensource-demo.orangehrmlive.com/",
  "adminUsername": "Admin",
  "adminPassword": "admin123"
}
```
- `browser` — which browser to run tests in. Accepted values: `chrome`, `firefox`, or `edge`.
- `url` — the website the tests will run against (a public OrangeHRM demo site).
- `adminUsername` / `adminPassword` — the admin login used throughout the tests.

You generally won't need to change this file unless you want to switch browsers or point the tests at a different OrangeHRM environment.

**6. Review the test data file**

Open `src/test/resources/TestData/Employees.xlsx` in Excel (or any spreadsheet program). This contains the sample employee information (name, address, emergency contact, etc.) that the tests use to fill in forms. You can view this to understand what data the tests will type into the website — see [Test Data & Fixtures](#test-data--fixtures) below for the full list of columns.

At this point, your setup is complete and you're ready to run tests.

---

## Running the Tests

All tests are run through Maven from the project's root folder (the one containing `pom.xml`).

### Run all tests

```bash
mvn test
```
This automatically finds and runs every file ending in `Runner` (there are 5 of them), which in turn runs every `.feature` scenario in the project. Chrome will pop up automatically, and you'll see the browser being controlled step by step.

### Run a single test suite

Maven lets you target one specific test class using the `-Dtest` flag. For example, to run only the Login test:
```bash
mvn test -Dtest=LoginRunner
```
To run only the "Add Employee" suite:
```bash
mvn test -Dtest=AddEmployeeRunner
```
Replace `LoginRunner` with any of: `AddEmployeeRunner`, `UpdateInfoRunner`, `EmergencyDependentRunner`, or `TimesheetRunner`.

### What "success" looks like

While running, you'll see:
- A Chrome window open and behave as if a person were clicking through the site.
- Console output describing each step (thanks to the `pretty` Cucumber plugin and the logging setup), such as:
  ```
  10:15:32 INFO  [main] LoginPage - Logging in with user: Admin
  10:15:34 INFO  [main] LoginPage - Login successful for user: Admin
  ```
- At the end, a summary showing how many scenarios/steps passed, failed, or were skipped, e.g.:
  ```
  Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
  BUILD SUCCESS
  ```

If everything says **BUILD SUCCESS** and **0 Failures / 0 Errors**, all tests passed.

### Viewing detailed reports

Two types of reports are generated automatically:

1. **Cucumber HTML report** — a simple, readable pass/fail report.
   Open this file in any browser after a run:
   ```
   target/cucumber-report.html
   ```

2. **Allure report** (richer, with charts, timelines, and failure screenshots). If you have the Allure command-line tool installed, generate and open the report with:
   ```bash
   allure serve allure-results
   ```
   (If you don't have Allure installed yet, see [Allure's official installation guide](https://allurereport.org/docs/install/) — it's a separate, free download.)

### What happens when a test fails

If a scenario fails, the code in `Hooks.java` automatically takes a **screenshot** of the browser at the moment of failure and attaches it to the Allure report, so you can visually see exactly what the screen looked like when things went wrong — no need to guess.

---

## Dependencies & Libraries

All dependencies are managed by Maven and defined in `pom.xml`. Here is the full list:

| Library | Version | Purpose |
|---|---|---|
| `selenium-java` | 4.39.0 | Core browser automation engine — clicks, types, reads page content |
| `io.cucumber:cucumber-java` | 7.33.0 | Lets test scenarios be written in plain-English Gherkin syntax |
| `io.cucumber:cucumber-core` | 7.33.0 | The underlying engine that parses and runs Gherkin scenarios |
| `io.cucumber:cucumber-testng` | 7.33.0 | Connects Cucumber scenarios to the TestNG test runner |
| `io.qameta.allure:allure-cucumber7-jvm` | 2.33.0 | Generates the rich, visual Allure test report |
| `io.github.bonigarcia:webdrivermanager` | 6.1.0 | Automatically downloads/matches the correct browser driver version — no manual setup needed |
| `org.apache.poi:poi` | 5.4.1 | Reads/writes Excel files (core engine) |
| `org.apache.poi:poi-ooxml` | 5.4.1 | Adds support specifically for modern `.xlsx` Excel files |
| `com.google.code.gson:gson` | 2.13.2 | Reads the JSON configuration file (browser, URL, credentials) |
| `org.apache.logging.log4j:log4j-core` | 2.25.4 | The main logging engine |
| `org.apache.logging.log4j:log4j-api` | 2.25.4 | The logging interface used throughout the code |
| `org.apache.logging.log4j:log4j-slf4j2-impl` | 2.25.4 | Bridges logging calls from other libraries into the Log4j2 system |

**Build tool plugin:**
- `maven-surefire-plugin` (3.5.3) — configured to automatically discover and run any class whose name ends in `Runner` (see `pom.xml`), which is how `mvn test` knows what to execute.

**Build settings:**
- Compiled for **Java 21** (`maven.compiler.source` / `maven.compiler.target`).

---

## Code Structure & Key Classes

### Design Pattern: Page Object Model (POM)

This project follows the **Page Object Model**, one of the most common and recommended patterns in test automation. Here's the plain-language version:

> Imagine every screen in the website (Login, Dashboard, Add Employee, My Info, etc.) as its own "remote control." Each remote control has buttons and knobs specific to that screen, and knows how to press its own buttons correctly. The test scenario itself doesn't need to know *how* a button is found on the page — it just says "log in" or "save the employee," and the relevant Page Object handles the details.

**Benefits of this approach:**
- If the website's design changes (e.g., a button's location or ID changes), you only need to update it in **one** Page Object file, not in every single test.
- Test scenarios (the StepDefs) stay clean and readable, focused on *what* should happen rather than *how* to click through the UI.

### Key Classes Explained

**`BasePage.java`** — The parent class every other Page Object inherits from. It provides shared, reusable actions:
- `click(locator)` — waits until an element is clickable, then clicks it (and waits for any loading spinner to disappear first)
- `type(locator, text)` — clears a text field and types new text into it
- `getText(locator)` / `getAttribute(locator, attr)` — reads text or attribute values off the page (used to verify data)
- `isDisplayed(locator)` / `isPresent(locator)` — checks whether something is visible or exists on the page
- `waitForLoaderToDisappear()` — waits for OrangeHRM's loading spinner to go away before continuing, preventing the test from clicking too early
- Several `waitFor...` helper methods that pause until specific conditions are true (a page has loaded, a value has appeared, etc.) — this prevents the classic automation problem of the test "racing ahead" of a slow-loading web page

**Individual Page classes** (`LoginPage`, `DashboardPage`, `PIMPage`, `AddEmployeePage`, `EmployeeListPage`, `PersonalDetailsPage`, `MyInfoPage`, `TimePage`, `UserManagementPage`, `EditUserPage`) each represent one screen and expose easy-to-read methods like `login()`, `navigateToPIM()`, `createEmployee()`, `updatePersonalDetails()`, `approveTimesheet()`, etc. Navigation methods typically **return the next Page Object** — for example, `dashboardPage.navigateToPIM()` returns a `PIMPage` object, representing the fact that clicking "PIM" takes you to the PIM screen. This chaining mirrors how a real person would move between screens.

**`Employee.java` (Model)** — A simple data container ("Java Bean") representing one test employee: name, login credentials, role, address, emergency contact, dependent, and timesheet-related fields. Think of it as a single, structured "form" that carries all the details about one fictional employee as they move through different test steps.

**`DriverFactory.java`** — Responsible for opening and closing the browser. It supports Chrome, Firefox, and Edge, decided by the `browser` setting in `config.json`. It ensures only **one** browser window is open at a time per test run and maximizes the window automatically.

**`ExcelManager.java`** — Reads employee data out of `Employees.xlsx`. Rather than hardcoding data in test code, this class looks up the column headers (e.g., "FirstName", "Street", "EmergencyContactName") and pulls the corresponding values for a given row number, then packages them into an `Employee` object.

**`JsonFileManager.java`** — Reads the `config.json` settings file once and makes any value (like `adminUsername` or `url`) available anywhere in the code with a simple `JsonFileManager.getValue("key")` call.

**`EmployeeManager.java`** — A convenience/safety-net utility. Some scenarios (like the Timesheet or Emergency Contact tests) expect a specific employee to already exist and be able to log in. If that employee's login fails (meaning they don't exist yet), this class automatically creates them first — logging in as admin, filling out the Add Employee form, assigning a role, and logging back out — before the actual test scenario continues.

**`LoggerUtil.java`** — A small helper that hands out a properly configured `Logger` object to any class that needs one, so that log messages are labeled with the class they came from (helpful when reading the log file later).

**`TestContext.java`** — A temporary, shared "notepad" for a single test scenario. Since Cucumber step definitions are ordinary Java objects that don't automatically share data with each other, this class provides a simple way to store the current `Employee` being tested so that later steps (like "Admin reviews and confirm updates") can retrieve exactly the same data that earlier steps (like "Employee adds a dependent") used to fill out the form.

**`Hooks.java`** — Code that runs automatically before and after **every single scenario**:
- **Before:** clears the shared "notepad" (`TestContext`), opens a fresh browser window, and navigates to the OrangeHRM URL.
- **After:** if the scenario failed, takes a screenshot and attaches it to the test report; either way, logs the final pass/fail result and closes the browser.

This ensures every scenario starts from a clean, predictable state and that browsers don't pile up unused in the background.

---

## Test Data & Fixtures

### Where test data lives

All sample employee data used to fill in forms is stored in one spreadsheet:
```
src/test/resources/TestData/Employees.xlsx
```
This keeps test data separate from test logic — if you want to test with different names or addresses, you edit the spreadsheet, not the Java code.

### What's inside the spreadsheet

The sheet is named **"Employees"**, and its first row contains column headers that the code looks up by name (not by position), so columns can be reordered safely as long as the header names stay the same. The columns used are:

| Column | Used For |
|---|---|
| `FirstName`, `LastName` | Employee's name |
| `Username`, `Password` | Login credentials created for the employee |
| `Role` | Assigned system role (e.g., Admin or ESS/employee) |
| `Nationality`, `MaritalStatus` | Personal details fields |
| `Street`, `City`, `State`, `ZipCode`, `Mobile` | Contact/address details |
| `EmergencyContactName`, `EmergencyRelationship`, `EmergencyContactNumber` | Emergency contact fields |
| `DependentName`, `DependentRelationship`, `DependentDOB` | Dependent (e.g., spouse/child) fields |
| `Project`, `Activity`, `MondayHours` | Timesheet fields |

Currently, tests read data from **row 1** (the first employee record) via `ExcelManager.getEmployee(1)`.

### Configuration ("fixture-like" settings)

Separately from employee data, environment settings live in:
```
src/test/resources/config/config.json
```
This includes the browser to use, the site URL, and the admin login — effectively the "fixed" settings every test scenario relies on regardless of which employee data is used.

### Setup and teardown

- **Setup (before each scenario):** Handled by `Hooks.java`'s `@Before` method — opens a fresh browser and loads the OrangeHRM login page.
- **Teardown (after each scenario):** Handled by `Hooks.java`'s `@After` method — captures a screenshot on failure, logs the outcome, and closes the browser so no leftover windows or sessions carry over into the next test.
- **Self-healing setup:** A few scenarios (Timesheet, Update Info, Emergency/Dependent) include a safety check — if the expected test employee doesn't exist yet (first run, or a previous run's data was reset), the test automatically creates that employee via `EmployeeManager` before continuing, rather than simply failing.

---

## Troubleshooting

| Problem | Likely Cause | How to Fix |
|---|---|---|
| `mvn: command not found` | Maven isn't installed or isn't on your system PATH | Reinstall Maven and make sure its `bin` folder is added to your PATH environment variable |
| `java: command not found` or wrong Java version | Java isn't installed, or an older version is active | Install JDK 21 and confirm with `java -version` |
| Browser doesn't open / test fails immediately with a driver error | Chrome isn't installed, or WebDriverManager couldn't reach the internet to download a matching driver | Make sure Chrome is installed and you have an internet connection on first run |
| Test fails with "element not found" / timeout errors | The website's design changed, the page loaded slower than usual, or your internet connection is slow | Re-run the test; if it keeps failing, check the Allure/Cucumber report screenshot to see what the page actually looked like at that moment, then compare the locator in the relevant `Pages/*.java` file against the current site |
| `Failed to load config.json` error | The `config.json` file is missing, misplaced, or has invalid JSON syntax (e.g., a missing comma) | Confirm the file still exists at `src/test/resources/config/config.json` and check it's valid JSON (you can paste it into an online JSON validator) |
| `Failed to read Employees.xlsx` error | The Excel file is missing, renamed, or a column header the code expects was deleted/renamed | Confirm the file exists at `src/test/resources/TestData/Employees.xlsx`, is named exactly that, and still contains a sheet called `Employees` with all expected column headers |
| Tests that depend on a specific employee existing keep failing | The demo environment resets periodically, removing previously created employees | This is expected sometimes on public demo sites; most scenarios in this project already detect this and recreate the employee automatically. If a scenario doesn't recover, simply re-run it |
| Timesheet test fails because a project/activity doesn't exist in the dropdown | The values in `Employees.xlsx` (`Project`, `Activity`) don't match options actually available in the OrangeHRM Time module | Update the spreadsheet values to match existing project/activity names in the target environment |
| All tests run in one browser only, even though you changed the browser | The value in `config.json` wasn't saved, or has a typo (must be lowercase `chrome`, `firefox`, or `edge`) | Double check spelling and save the file before re-running |
| Report doesn't show a screenshot for a failed test | Screenshots are only captured automatically on failure, and only if the browser was still open at the time of failure | Check the log file (`logs/automation.log`) for more detail on what happened right before the crash |

### General debugging tips
- Always check `logs/automation.log` first — it contains a timestamped, step-by-step account of what the test did, in plain sentences (e.g., "Logging in with user: Admin").
- The Allure report is the best place to see failure screenshots and a visual timeline of each step.
- If a test fails only occasionally (not every time), it's often a timing issue — the site took slightly longer to respond than expected. The `wait` logic in `BasePage.java` is designed to reduce this, but very slow connections can still occasionally trigger it.

---

## Contributing Guidelines

### Adding a new test scenario

1. **Write the scenario first, in plain English**, in a new (or existing) `.feature` file under `src/test/resources/Features/`. Use the Given/When/Then style, matching the tone of existing scenarios.
2. **Create a Runner class** (if this is a new feature area) under `src/test/java/Runners/`, following the exact pattern of the existing Runner files — just point `features` at your new `.feature` file and keep the same `glue` and `plugin` settings.
3. **Implement the step definitions** in a new or existing class under `src/test/java/StepDefs/`. Each Given/When/Then sentence in your feature file needs a matching annotated method (`@Given`, `@When`, `@Then`) with the exact same wording.
4. **Reuse existing Page Objects** wherever possible. Only add new methods to a Page Object class (or create a brand-new Page Object class) if the screen/action you need doesn't already exist.
5. If your scenario needs new test data fields, add a new column to `Employees.xlsx` and a matching getter/setter pair in `Employee.java`, then read it in `ExcelManager.java`.

### Naming conventions used in this project

- **Feature files:** lowercase or PascalCase, descriptive of the workflow (e.g., `login.feature`, `Timesheet.feature`).
- **Runner classes:** `<FeatureName>Runner.java` (e.g., `LoginRunner.java`).
- **Step definition classes:** `<FeatureName>StepDef.java` (e.g., `LoginStepDef.java`).
- **Page Object classes:** `<ScreenName>Page.java`, always ending in `Page` (e.g., `DashboardPage.java`).
- **Locators (element references) inside Page Objects:** named after what they represent, in camelCase, ending in a hint about the element type where useful (e.g., `saveButton`, `usernameField`, `dashboardHeader`).
- **Step methods:** written in camelCase, phrased as a readable sentence matching the Gherkin step (e.g., `adminNavigatesToAddEmployeePage()`), so the method name and the Gherkin text stay obviously connected.

### Best practices to follow

- **Keep step definitions thin.** Step definition methods should mostly call methods on Page Objects — they shouldn't contain raw Selenium code (like `driver.findElement(...)`) directly. That logic belongs inside the relevant Page Object class.
- **Always wait, don't guess.** Use the existing `wait...` helper methods in `BasePage` rather than adding fixed `Thread.sleep()` pauses, which make tests slower and less reliable.
- **Log meaningful actions.** Follow the existing pattern of calling `logger.info(...)` for major actions (e.g., "Creating employee", "Logging in with user"), which makes future troubleshooting much easier.
- **Don't hardcode test data or credentials in code.** Add new values to `Employees.xlsx` or `config.json` instead, keeping data and logic separate.
- **One scenario, one clear purpose.** Keep each `.feature` scenario focused on a single, describable business outcome, matching the existing five scenarios' style.
- **Test your changes locally** with `mvn test -Dtest=<YourRunner>` before considering the work done, and check the generated report to confirm the scenario behaves as expected, including on a failure path (e.g., temporarily break a locator to confirm the screenshot capture still works).

---

*This README was generated based on a full review of the project's source code, configuration, and test scenarios as of the current codebase state. If the project structure changes significantly, please update this document to keep it accurate.*
