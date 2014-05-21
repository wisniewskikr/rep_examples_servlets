DESCRIPTION
===========

This is example project built by using Java Servlets. It consists of two pages:
- input page		: here you can type your name;
- output page		: here your name is displayed after text "Hello World"

This project shows how to use Java Servlets together with:
- java script;
- css;
- validation.

Recommended deployment of project:
----------------------------------
Maven command:
mvn clean install -Ploc,deploy 

Recommended usage of project:
-----------------------------
Browser url:
http://localhost:8080/servlets-example-helloworld-tests

Recommended usage of unit tests:
---------------------------------------
Maven command:
mvn clean install -Punit 

Recommended usage of integration tests:
---------------------------------------
Maven command:
mvn clean install -Pintg 




PRECONDITIONS
=============

1. TOOLS
--------
This example project requires:
- Java (tested for version 1.7.0_10);
- Maven (tested for version 3.0.4);
- Tomcat (tested for version 7.0.34).

2. CONFIGURATION
----------------
All flexible configuration of project (server`s urls, logins, passwords etc.) can be changed in file:
<project_home>/project.properties





DEPLOYMENT
==========

You can deploy this application in two ways:
1. Copy war file
2. Use Maven plugin for deployment


Ad 1\ Copy war file
===================
You can do it using following steps:
- Open console;
- Go to project folder "servlets-example-helloworld-tests";
- Use Maven command for building project. Command: 
  
  mvn clean install
  
- Copy file "servlets-example-helloworld-tests.war" from <project_home>/target to <tomcat_home>/webapp


Ad 2\ Use Maven plugin for deployment
=====================================  
You can do it using following steps:
- Go to <tomcat_home>/conf/tomcat-users.xml and add user in manager role. For instance:

  <user username='admin' password='admin' roles='manager-gui,admin-gui,manager-script'/>
  
- Set server informations in file: <project_home>/project.properties. You have to set:
  loc.server.url, loc.server.username, loc.server.password;  
- Open console;
- Go to project folder "servlets-example-helloworld";
- Use Maven command for building and deployment project. Command:
 
  mvn clean install -Ploc,deploy  





UNIT TESTS
==========

To run unit tests you have to use maven command:

		mvn clean install -Punit





INTEGRATION TESTS
=================

This project has set of integration tests based on Selenium tool. To run integration tests you have to:

- Configure embedded Tomcat port (optional)
In file <project_home>/project.properties you can change embedded Tomcat port.
Update property "loc.test.intg.port". By default it is "8181".

- Run integration tests
To run integration tests you have to use maven command:
			mvn clean install -Pintg 
			
			
			
			
			
USAGE
=====

Type in browser:

http://localhost:8080/servlets-example-helloworld-tests