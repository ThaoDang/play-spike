# play-spike

This is a spike to demonstrate how we use Java Playframework 2.5 with LDAP authentication and JPA with mySQL. It is just a simple login screen and then the user personal details upon login success. You can either browse the code to see how it is done or see the app in action by following the steps below.

## How this app works
1. If you access any link without having logged in, a filter (AuthorizationFilter.java) is put in place to intercept every http request and check that the user name is present in the session cookie. If it is not, you will be directed to a login page.

2. When you fill in the login form and submit, the credentials will be authenticated against the ldap database (ActiveDirectoryService.java). A session cookie is created of successful authentication (Play gives you integrity protection for `session`, more detail from https://www.playframework.com/documentation/2.5.x/ScalaSessionFlash)

3. Once successfully logged in, you are taken to the welcome page, which will show you the logged in user's personal detail (name, age) retrieved from mySql (PersonService.java, here we show how to retrieve the information using both Hibernate ORM and JPA)

## Pre-requisite
1. Install OpenLdap
  * Detailed installation instructions can be found at https://github.com/IntersectAustralia/acdata/wiki/Setting-up-OpenLDAP
  * Setup server for authentication https://wiki.gentoo.org/wiki/Centralized_authentication_using_OpenLDAP#OpenLDAP_Server_Configuration
  * Add a user


2. Install mySql
  * detailed instructions can be found at https://dev.mysql.com/downloads/installer/
  * Once mySql has been installed, create a schema with the following table:
    ```
    CREATE TABLE person (id MEDIUMINT not null auto_increment, 
                          name VARCHAR(40), 
                          age INT(10), 
                          username VARCHAR(20), 
                          PRIMARY KEY (id));
                          
    INSERT INTO person (name, age, username) VALUES('Jane Smith', 30, <the ldap username created>);
    ```
3. Install Play activator from https://www.playframework.com/download

4. Ensure you have JDK and JRE 1.8 on your machine

5. Clone this project

6. Modify application.conf to reflect the mySQL connection settings that you created in step 2

7. Modify ActiveDirectoryService.java to have the ldap settings that you created in step 3

## Run the app (using activator)
1. Start up activator: 
  * For linux/unix: `./<activator installation>/bin/activator ui`
  * For windows: `<activator installation>/bin/activator.bat ui`
  
2. Register the app:
  * Load the activator ui (usually at http://localhost:8888)
  * Select the folder icon in 'Open existing app'
  * Navigate to the play-spike folder that you just cloned
  * Select 'Choose'
  * The app should be loaded
  
3. Run the app:
  * Once the play-spike app has been loaded, select 'Run' on the menu and click on the 'Run' icon. You should see the server started up on localhost:9000 (by default)

## Run the app (using sbt command)
1. Ensure you download and install sbt http://www.scala-sbt.org/download.html. Ensure you can execute `sbt` command
2. `cd <directory where you cloned the code>/play-spike`
3. Type `sbt`. SBT will load up your project settings and compile everything, it may take a while to be ready on first start.
4. In the sbt shell type `run`. Your server should start up on localhost:9000

## Other Resources
1. Play tutorials: https://www.playframework.com/documentation/2.5.x/Tutorials
2. Ldaptive authentication: http://www.ldaptive.org/
