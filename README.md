Clocker
=======

Clocker creates and manages a [Docker](http://docker.io/) cloud infrastructure. Clocker support 
single-click deployment and runtime management of multi-node applications that can run on
containers distributed across docker hosts. Application blueprints written for 
[Brooklyn](https://brooklyn.incubator.apache.org/) can thus be deployed to a Docker cloud 
infrastructure.

This repository contains the required Brooklyn entities, locations and examples.

## Getting started

To get started, you just have to download Clocker, deploy the _Docker Cloud_ blueprint to the 
cloud or machines of your choice, and then use Clocker to deploy your applications. This will 
automatically create the required Docker containers.

You can create a Docker based cloud infrastructure on your favourite cloud provider or on a 
private cloud using any of the jclouds supported APIs. Alternatively you can target one or 
more existing machines for running Docker.

If you are keen to peek under the covers, you can find the Docker cloud infrastructure blueprint at 
[docker-cloud.yaml](https://raw.githubusercontent.com/brooklyncentral/clocker/master/examples/src/main/assembly/files/blueprints/docker-cloud.yaml). 

### Using the latest Clocker release

You can build a *Docker Cloud Infrastructure* running these commands:
```Bash
% wget --no-check-certificate --quiet \
    -O brooklyn-clocker-examples-0.6.2-dist.tar.gz http://git.io/O--JKw
% tar zxf brooklyn-clocker-examples-0.6.2-dist.tar.gz
% cd brooklyn-clocker-examples-0.6.2
% ./clocker.sh launch --cloud --location location
```
Where _location_ can be e.g. _jclouds:softlayer:sjc01_, or a named location or a fixed IP e.g. _byon:(hosts="10.1.2.3,10.1.2.4")_.
Those simple steps will give you a running docker instance on your favourite cloud.

For anything other than a localhost or bring-your-own-nodes location, it is vital that you 
first configure a `~/.brooklyn/brooklyn.properties` file with cloud credentials and security 
details, and create an SSH key (defaulting to `~/.ssh/id_rsa`). A simple example 
`brooklyn.properties` file would be:

```
# Sets up a user with credentials admin:password for accessing the Brooklyn web-console.
# To genreate the hashed password, see `brooklyn generate-password --user admin`
brooklyn.webconsole.security.users=admin
brooklyn.webconsole.security.user.admin.salt=DOp5
brooklyn.webconsole.security.user.admin.sha256=ffc241eae74cd035fdab353229d53c20943d0c1b6a0a8972a4f24769d99a6826

# Credentials to use in your favourite cloud
brooklyn.location.jclouds.softlayer.identity=SL123456
brooklyn.location.jclouds.softlayer.credential=<private-key>

brooklyn.location.jclouds.aws-ec2.identity=AKA_YOUR_ACCESS_KEY_ID
brooklyn.location.jclouds.aws-ec2.credential=YourSecretKeyWhichIsABase64EncodedString
```

For more information on setting up locations, including supplying cloud provider credentials, see the [_Setting up Locations_ section of
Brooklyn Getting Started](https://brooklyn.incubator.apache.org/quickstart/#configuring-a-location), and the more detailed [locations guide](https://brooklyn.incubator.apache.org/v/0.7.0-M1/use/guide/locations/index.html).

The Brooklyn web-console, which will be deploying and managing your Docker Cloud, can be accessed at 
[http://localhost:8081](http://localhost:8081) - this URL will have been written to standard out during startup.

Once the `DockerCloud`  application has started, a new location named `my-docker-cloud` will be
available in the Locations drop-down list when adding new applications. Simply start a new application in this location
and it will use Docker containers instead of virtual machines.

For more information on deploying applications from the Brooklyn catalog, see
[Getting Started - Policies and Catalogs](https://brooklyn.incubator.apache.org/quickstart/policies-and-catalogs.html).
You can also paste a YAML blueprint into the _YAML_ tab of the _Add Application_ dialog, as follows:

```JS
location: my-docker-cloud
services:
- type: brooklyn.entity.webapp.jboss.JBoss7Server
  brooklyn.config:
    wars.root:
    - "https://s3-eu-west-1.amazonaws.com/brooklyn-clocker/hello-world.war"
```

### Building from source

Build and run the examples as follows:

```Bash
    % git clone https://github.com/brooklyncentral/clocker.git
    ...
    % cd clocker
    % mvn clean install
    ...
    % cd examples
    % mvn assembly:single
    ...
    % cd target
    % tar zxf brooklyn-clocker-examples-0.7.0-SNAPSHOT-dist.tar.gz
    % cd brooklyn-clocker-examples-0.7.0-SNAPSHOT
    % ./bin/clocker.sh location
    ...
```

## Getting involved

Clocker is [Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0) licensed, and builds on  the
[Apache Brooklyn](http://brooklyn.incubator.apache.org/) project. Please get involved and join the 
discussion on [Freenode](http://freenode.net/), IRC `#brooklyncentral` or the Apache Brooklyn 
community [mailing list](https://brooklyn.incubator.apache.org/community/). We also maintain a
[Trello](https://trello.com/b/lhS7ltyi/clocker) board with the current roadmap and active tasks.

### Documentation

Please visit the [Wiki](https://github.com/brooklyncentral/clocker/wiki) for more details.

----
Copyright 2014 by Cloudsoft Corporation Limited and Licensed with [CC-BY-SA 4.0i](http://creativecommons.org/licenses/by-sa/4.0/)
