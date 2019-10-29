# Changelog for NVDB API LES V3 Client

## 1.1.5
* Roadsysref with only road system returns null

## 1.1.4
* Added Attribute.getValueAsString, giving a toString for all attributes

## 1.1.3
* Some strange Gradle behaviour made manifest contain `Implementation-Title: NVDB API Client - Release`. Pinning to `nvdb-api-client`.

## 1.1.2
* Added `ClientFactory.logout()` for clearing its auth tokens.

## 1.1.1
* Enum attribute values have their own classes. String-, Real-, IntegerEnumAttribute now
extends EnumAttribute. 

## 1.1.0
* Enum values have their own classes. String-, Double-, IntegerAttributeType now
is only the regular attributes.
* Removed deprecated constructors using `User-Agent` and the 
constructor not specifying client id.
* Added constructors for `X-Client-Session`-header. If a session identifier
is not specified a uuid is used. This uuid is stored in ~/.nvdb-api-read-v3/session

## 1.0.4
* Always parse responses as UTF-8. When running on windows parsing 
of properties with æøå failed before this.
* Include json in exception when parsing fails.

## 1.0.3
* Deprecate taking `User-Agent` as constructor parameter in `ClientFactory`.
Now `nvdb-api-client-$version` is used as `User-Agent`.

## 1.0.2
* First version of `RoadNetRouteClient`. The route part of the client may
change at any time.

## 1.0.0, 1.0.1
* First version of V3 Client
