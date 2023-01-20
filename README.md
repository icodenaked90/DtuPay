# 02267 Software Development of Webservices 
# Group 02 - DTU Pay

The DTU Pay app project consists of 4 microservices - Account Management Service, Token Service, Payment Service and Report Service.

# Installation
Before installing DTU Pay, ensure Java OpenJDK 11, Apache Maven and Docker is installed on the system.

In order to install DTU Pay, download/clone this GitHub repository from the main branch. Open a terminal and change the directory to the root of the downloaded repository and execute the
`build_and_run.sh`
script, which builds the jar files, then the Docker containers and runs them with docker-compose. 
It also runs all the service and end-to-end tests. 

After the build, the `end-to-end-tests` can be rerun again by executing the `test.sh` script inside the end-to-end-tests directory.

For further installation details see the Installation Guide.

# Contributors
    - Adin Jasarevic (s164432)
    - Emily Wibroe Warming (s223122)
    - Hildibjørg Didriksen (s164539)
    - Jonathan Emil Zørn (s194134)
    - Mila Valcheva (s223313)
    - Simon Philipsen (s163595)