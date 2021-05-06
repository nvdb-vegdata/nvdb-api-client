# nvdb-api-client
Open source Java client library for use with the NVDB REST API v3.

Support or feedback: Issue on this repo or on [Gitter](https://gitter.im/nvdb-vegdata/api-les-v3)

# API URL
Base URL for the API is https://nvdbapiles-v3.atlas.vegvesen.no

# Artifact
This artifact will be published to [Maven Central](https://search.maven.org/artifact/no.vegvesen.nvdb/nvdb-read-api-v3-client/) upon releases.

## Gradle
```gradle
def nvdbVersion = "1.16.6"
def slfVersion = "1.7.30"

dependencies {
    compile "no.vegvesen.nvdb:nvdb-read-api-v3-client:$nvdbVersion"
    compile "javax.activation:activation:1.1"
    compile "org.slf4j:slf4j-api:$slfVersion"
    compile "org.slf4j:slf4j-simple:$slfVersion"
}

```

## Maven
```xml
<properties>
    <nvdb.version>1.16.6</nvdb.version>
    <slf.version>1.7.30</slf.version>
</properties>

<dependencies>
    <dependency>
        <groupId>no.vegvesen.nvdb</groupId>
        <artifactId>nvdb-read-api-v3-client</artifactId>
        <version>${nvdb.version}</version>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>${slf.version}</version>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-simple</artifactId>
        <version>${slf.version}</version>
    </dependency>
</dependencies>
```

# Getting started
Using the client is very simple. All it takes is a couple of lines of code.

## Authentication
This is a completely open API, but some featuretypes are restricted and need authentication and authorization.
We strongly encourage using the **X-Client-Name** header because it helps us gather statistics which we use to improve the API.

## Example
To start using the library simply instantiate the factory. It takes three arguments:
1. Base URL for the API
2. Value for request header **X-Client-Name**

```java
// First, create factory
ClientFactory factory = new ClientFactory("https://nvdbapiles-v3.atlas.vegvesen.no", "nvdb-read-api-v3-client");
// Then, create your client. Typically, there's one per root endpoint   
RoadObjectClient client = factory.getRoadObjectClient();

// Example single object download
RoadObject ro = client.getRoadObject(534, 1);

// Remember to close your factory when you're done using it
factory.close();
```

Please note that when the factory is closed all clients created by it will also be closed. So the following code will not work

```java
RoadObjectClient client;
try (ClientFactory factory = new ClientFactory("https://nvdbapiles-v3.atlas.vegvesen.no", "nvdb-read-api-v3-client")) {
    client = factory.createRoadObjectClient();
}

// Won't work because client is closed after the try-with-resources block.
RoadObject ro = client.getRoadObject(534, 1);
```

### Setting timeouts for Jersey client.
To set a connect and read timeout for the nvdb-api-client. An instance of `ClientConfiguration` can be added when creating the `ClientFactory`

 ```java
// Add a read timeout of 5000 millis and connect timeout of 1000 millis
ClientConfiguration clientConfig = 
    ClientConfigurationBuilder.builder()
       .withReadTimeout(5000)
       .withConnectTimeout(1000)
       .build();

// Create a factory with timeout settings.
ClientFactory factory = new ClientFactory("https://nvdbapiles-v3.atlas.vegvesen.no", "nvdb-read-api-v3-client", clientConfig);
```

## Simple setup
To use the client in your project, you should:
1. Add the gradle or maven code to your project build file (pom.xml for maven projects);
2. Fill in your preferred version at $version (maven versions are available here: https://search.maven.org/artifact/no.vegvesen.nvdb/nvdb-read-api-v3-client);
3. Add some of the example code and run it.

# How to build
The repo contains the Gradle wrapper. The client is built running:
```bash
// Simple compilation 
./gradlew build

// Publish to your Maven local
./gradlew publishToMavenLocal
```
