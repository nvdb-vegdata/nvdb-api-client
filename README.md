[![Build Status](https://travis-ci.org/nvdb-vegdata/nvdb-api-client.svg?branch=master)](https://travis-ci.org/nvdb-vegdata/nvdb-api-client)

# nvdb-api-client
Open source Java client library for use with the NVDB REST API v2

# WARNING
* The API version 2 is not yet released. This client is a work in progress.

# API URL
When it's released, the API will be available at https://www.vegvesen.no/nvdb/api/v2.

# Artifact
This artifact will be published to Maven Central and Bintray upon releases.

## Gradle
```gradle
compile "no.vegvesen.nvdb:nvdbapi-client:1.0.0"
```

## Maven
```xml
<dependency>
    <groupId>no.vegvesen.nvdb</groupId>
    <artifactId>nvdbapi-client</artifactId>
    <version>1.0.0</version>
</dependency>
```

# Getting started
Using the client is very simple. All it takes is a couple of lines of code.

## Authentication
This is a completely open API. It has no authentication nor any authorization. We strongly encourage using the **X-Client-Name** header because it helps us gather statistics which we use to improve the API.

## Example
To start using the library simply instantiate the factory. It takes three arguments:
1. Base URL for the API
2. Value for request header **User-Agent**
3. Value for request header **X-Client-Name**

```java
// First, create factory
ClientFactory factory = new ClientFactory("https://www.vegvesen.no/nvdb/api/v2", "nvdbapi-client", "ACME");
// Then, create your client. Typically, there's one per root endpoint
RoadObjectClient client = factory.createRoadObjectClient();

// Example single object download
RoadObject ro = client.getRoadObject(534, 1);

// Remember to close your factory when you're done using it
factory.close();
```
# How to build 
The repo contains the Gradle wrapper. The client is built running:
```bash
// Simple compilation 
./gradlew build

// Publish to your Maven local
./gradlew publishToMavenLocal
```
