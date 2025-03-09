My Security Tool

Overview

My Security Tool is a Java-based security-checking tool that analyzes Java source code for potential security vulnerabilities. It uses JavaParser to parse the Abstract Syntax Tree (AST) and detect security risks such as SQL injection, command injection, and hardcoded credentials.

Features

> Detects SQL Injection vulnerabilities.

> Identifies Command Injection risks.

> Flags Hardcoded Credentials in Java source files.

> Generates reports in JSON, XML, and HTML formats.

Prerequisites

> Java 8 or later must be installed.

> Maven must be installed and configured.

> Ensure you have an internet connection to download dependencies.

Project Structure

<img width="338" alt="image" src="https://github.com/user-attachments/assets/a18e988c-7ad0-4997-9371-76af9553766b" />

Installation and Setup

Clone the Repository

git clone git@github.com:isarmadfarooq/Java_Security_Tools.git    

Build the Project

mvn clean package

Running the Tool

To analyze Java files, use the following command:

java -cp target/my-security-tool-1.0-SNAPSHOT.jar com.mycompany.securitychecker.analyzer.CodeAnalyzer "D:/path/to/java/files" JSON

> Replace path/to/java/files with the actual directory containing Java files.

> Replace JSON with XML or HTML to change the report format.

Example Usage

Running the tool on sample Java files

java -cp target/my-security-tool-1.0-SNAPSHOT.jar com.mycompany.securitychecker.analyzer.CodeAnalyzer "D:/path/to/java/files" JSON

Sample JSON Output

<img width="309" alt="image" src="https://github.com/user-attachments/assets/5ea048f7-11c1-4199-a038-5ec73b742f6e" />

Troubleshooting

"Error: Could not find or load main class CodeAnalyzer"

 > Ensure you have compiled the project using mvn clean compile.

 > Check if CodeAnalyzer.class exists in target/classes.

"Failed to parse file"

 > Ensure the Java files are correctly formatted.

 > Check for syntax errors in your Java source files.

