# DemoQA Automation Framework

Professional test automation framework for [DemoQA](https://demoqa.com/) website using Selenium WebDriver, TestNG and Allure Reporting.

## 🚀 Technologies & Tools

- **Language:** Java 21
- **Test Framework:** TestNG 7.11.0
- **Browser Automation:** Selenium WebDriver 4.34.0
- **Driver Management:** WebDriverManager 6.2.0
- **Reporting:** Allure TestNG 2.29.1
- **Logging:** SLF4J 2.0.17 + Log4j2 2.25.1
- **Build Tool:** Maven
- **AspectJ:** 1.9.24 (for Allure integration)

## 📋 Prerequisites

Before running the tests, ensure you have installed:
- Java 21 or higher
- Maven 3.6.0 or higher
- Chrome/Firefox browser (latest version)

## 🏗️ Project Structure
src/
├── main/java/com/demoqa/ - Page Object classes and utilities
├── test/java/com/demoqa/ - Test classes (functional, regression, smoke)
└── test/resources/
├── testng_suites/ - TestNG configuration files
│ ├── functional_tests.xml
│ ├── regression_tests.xml
│ └── smoke_tests.xml
└── config.properties - Framework configuration

text

## 🎯 Test Suites

The framework includes multiple test suites:
- **Smoke Tests** - Basic functionality verification
- **Functional Tests** - Detailed feature testing
- **Regression Tests** - Comprehensive system validation

## ⚡ Quick Start

### 1. Clone the repository
```bash
git clone https://github.com/KristinaSulyma/demoqa-automation.git
cd demoqa-automation
2. Run all test suites
bash
mvn clean test
3. Run specific test suite
bash
# Run only smoke tests
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng_suites/smoke_tests.xml

# Run only functional tests
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng_suites/functional_tests.xml

# Run only regression tests
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng_suites/regression_tests.xml
📊 Test Reports
Allure Reports
After test execution, generate Allure report:

bash
# Generate Allure report
allure serve target/allure-results

# Or generate report to directory
allure generate target/allure-results -o target/allure-report
Surefire Reports
HTML reports are available at: target/surefire-reports/index.html

⚙️ Configuration
Framework configuration is managed through:

src/test/resources/config.properties - Environment settings

TestNG XML files - Test suite configurations

pom.xml - Dependencies and build settings

🧪 Test Coverage
The framework covers following DemoQA sections:

Elements (Text Box, Buttons, Check Box, Radio Button)

Forms (Practice Form)

Alerts, Frame & Windows

Widgets (Slider, Progress Bar, Date Picker)

Interactions (Drag & Drop)

🔧 Build Configuration
Key Maven plugins configured:

maven-compiler-plugin - Java 21 compilation

maven-surefire-plugin - Test execution with Allure integration

AspectJ weaver - Allure attachment support

📝 Logging
The framework uses layered logging:

SLF4J as logging facade

Log4j2 as implementation

Console and file appenders configured

🤝 Contributing
Fork the project

Create your feature branch

Commit your changes

Push to the branch

Create a Pull Request