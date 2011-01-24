## Installing the archetype locally

1. git clone git://github.com/sroysf/mmarchetype-model.git
2. cd mmarchetype-model
3. ./install-archetype.sh


## Project Structure Overview

This project is broken up into several maven modules:

**multimodule-parent** :
	
The parent project serves as the common parent to all three submodules, and contains project global settings.
	
**persistence** :
	
The persistence module encapsulates all functionality related to data persistence. This includes:

* JPA entities
* DAO's to provide a common data access layer.
* All configuration specific to communicating with the back-end persistence implementation.
		
**service** :

The service module provides an abstraction layer for common business logic, independent of specific types of clients.
This includes:
* Service interfaces and implementations
* Data Transfer Objects for serializing domain entities. 
	
**web** :

The web module encapsulates all the functionality needed to provide http based access to the service tier.


## Creating a new project from an archetype

In Spring STS...

1) File -> New -> Other -> Maven -> Maven Project

Open Advanced pulldown, and select [groupId].[artifactId] for the Name template

Click Next

2) Select "All Catalogs", check the box for "Include snapshot archetypes", and filter for com.vmforce

Select the archetype

Click Next.

3) Enter the desired group Id, artifact ID, and version number for your new project.

Click Finish

You should see 4 new projects in your workspace, with the naming convention matching the naming convention selected in step 1 above.


## Initial sanity check

1) Navigate to the main project directory

2) Execute : mvn -DskipTests clean package

You should get a successful build with no errors.

## Customizing your project

1) Set the name of the web application artifact this project will generate. Open web/pom.xml and look for the following XML snippet:

`<warName>exampleMultiModule</warName>`

Change the warName property to an application name of your choosing.

2) Setup your forceDatabase.properties file, in persistence/src/main/resources/

Instructions on setting up this file are included as comments within the file itself.

3) Build your application for the first time, with your customizations. Application artifact is in web/target

mvn -DskipTests clean install

4) Deploy your application to VMForce for the first time, in order to setup the oAuth authentication credentials.

See instructions [here](https://github.com/forcedotcom/vmforce/wiki) if you need help deploying an application.

5) Customize the oauth.properties file in web/src/main/resources

6) Now rebuild and redeploy your application, with the customized oauth credentials.


## Test drive

1) Setup and deploy the app as you normally would, either on VMForce or locally. Application artifact is in web/target

2) Example test, locally:

http://localhost:8080/login

## IMPORTANT NOTES

* If you add / remove entities, be sure to update the persistence.xml file with your updated class entries!
* Be careful with the properties files containing security credentials when checking into a version control system.
