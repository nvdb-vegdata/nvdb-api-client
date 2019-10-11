# nvdb-api-client
Open source Java client library for use with the NVDB REST API v3.

Support or feedback: Issue on this repo or on [Gitter](https://gitter.im/nvdb-vegdata/api-les-v3)

# API URL
Base URL for the API is https://www.vegvesen.no/nvdb/api/v3

# Artifact
This artifact will be published to _jCenter_ upon releases: http://jcenter.bintray.com/
See 'set me up' on [bintray](https://bintray.com/beta/#/nvdb-vegdata/vegdata/nvdb-read-api-v3-client?tab=overview) for the latest version number.

## Gradle
```gradle
dependencies {
    compile "no.vegvesen.nvdb:nvdb-read-api-v3-client:$version"
}
repositories {
	maven {
		url  "https://dl.bintray.com/nvdb-vegdata/vegdata"
	}
}
```

## Maven
```xml
<dependencies>
    <dependency>
        <groupId>no.vegvesen.nvdb</groupId>
        <artifactId>nvdb-read-api-v3-client</artifactId>
        <version>$version</version>
    </dependency>
</dependencies>
<repositories>
    <repository>
        <snapshots>
            <enabled>false</enabled>
        </snapshots>
        <id>bintray-nvdb-vegdata-vegdata</id>
        <name>bintray</name>
        <url>https://dl.bintray.com/nvdb-vegdata/vegdata</url>
    </repository>
</repositories>
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
ClientFactory factory = new ClientFactory("https://www.vegvesen.no/nvdb/api/v3", "nvdb-read-api-v3-client");
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
