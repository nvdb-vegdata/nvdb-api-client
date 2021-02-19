# Changelog for NVDB API LES V3 Client

## 1.16.0
* `Contract` and `Route` can refer to several objects

## 1.15.0
* `AreaClient.getStreets` changed to support pagination of `Street`s

## 1.14.0
* `RoadNetRouteRequest.geometry` changed to `String`, added `RoadNetRouteRequest.projection`
* Date params was not correctly handled in Route POST request
* `Quality` moved into `GeomtryAttributes`
* Handle exception on `ClientFactory.close()`

## 1.13.2
* Street introduced as an area equal to Contract and Route (But not yet enabled in API)

## 1.13.1
* Close Jersey Responses to avoid resource leak

## 1.13.0
* Renamed fields and getters in RoadNetRouteRequest:
  * `distanceThreshold` -> `distance` / `getDistance`
  * `circumferenceAroundPoints` -> `envelope` / `getEnvelope`
* Boolean field `connectionLinks` in RoadNetRouteRequest is now true by default.
* Added `length` / `getLength` and `status` / `getStatus` to `RouteOnRoadNet`.
* Send typeOfRoad properly if using `RoadNetRouteClient.postRouteOnRoadnet`.

## 1.12.1
* Added getter for apiValue on `Direction` enum

## 1.12.0
* `RoadNetClient` and `SegmentedRoadNetClient` used integer for linksequenceid, changed to long.
* Added `SegmentedRoadNetClient.getLinks(long linksequenceId, RoadNetRequest request)`

## 1.11.2
* Handle html response like «504 server did not respond in time».
* Do not trigger onDatakatalogUpdate callback if header is missing.

## 1.11.1
* /omrader/* does not have `?inkluder=polygon`.

## 1.11.0
* All `ClientFactory.create_*_Client` has been renamed to `get_*_Client`. These methods used to return a new instance each
time, now a previously created instance is returned if one exists. 
* Calling `close()` on a client returned from `ClientFactory.create_*_Client` removes it from the ClientFactory.
* `roadobject.segments` has a field `retning` with possible values `MED`/`MOT`. This is the same value as the `retning`
on the roadnet placement for the segment. 
* `ClientFactory.serviceLogin(username, password)` added for authenticating with service account.
* Fix that closing a client cause all other clients to fail with message about closed connection pool.
* Existing clients use updated login information. 
* Contract and Route now has `.municipalities` and `.counties`
* Reponses from `/beta/vegnett/rute` [has changed](https://github.com/nvdb-vegdata/nvdb-api-client/commit/2932d0b9c28eb1e182b028dbeaab87ed3c9765a3#diff-f457b5f7152c9026cd604bc51abf9eab)

## 1.10.1
* ListAttribute.getAttributeType() returned STRUCT
* Support `?inkluder_egenskaper`

## 1.10.0
* Clients no longer send header `X-Datakatalog-Versjon` by default.
  When NVDB API LES V3 has a different version of Datakatalogen than the version in this header 
  `HTTP 422: Nåværende datakatalogversjon i APIet er: {new version}` is returned.
* It is possible to define a callback for `RoadObjectClient` that is called when Datakatalog version changes.
  ```java
    clientFactory.createRoadObjectClient(
      DatakatalogPolicy.builder()
      .onDatakatalogUpdateCallback(() -> { /* do something */})    
      .build()
    )
  ```
* SegmentedRoadNetClient did not use national route parameter.

## 1.9.1
* `Intersection` and `SideArea` was missing field `trafficType`/`trafikantgruppe`.

## 1.9.0
* Use java.io.tmpdir instead of user.home for json caching 

## 1.8.4 (should have been 1.9.0)
* `SegmentedLink.superLinkId` replaced with `.superLinkExtent` 
* SegmentedRoadNetClient did not use contract parameter

## 1.8.3
* query parameters was not used when storing etag.

## 1.8.2
* Datakatalog client use etag information to cache responses.

## 1.8.1
* `Section.getSectionAndPartAsString()` returned SX*DS*Y, should be SX*D*Y
 
## 1.8.0
* `Link.getConnectionLink()` and `Link.getDetailed()` replaced by `Link.getLinkType`
 
## 1.7.1
* CRS 6173 replaced with 5973
 
## 1.7.0
* Only fetch the datakatalog version when creating `RoadObjectClient`, `RoadObjectClient.getDatakatalog()`.

## 1.6.1
* Use Datakatalog only in `RoadObjectClient`

## 1.6.0
* `Statistics.length` changed from `long` to `double`
* `ClientConfiguration` has been added to allow setting read and connect timeouts for Jersey client.

## 1.5.0
* RoadNetRequest.id, .superId changed from `List<Integer>` to `List<Long>`
* Added `SegmentedLink.topologyLevel`
* `Section.arm` is boolean
* `Node.id` changed from `Integer` to `Long`
* `Section.trafficType` changed from `String`to `RoadUserGroup`
* `Link.typeRoad` changed from `String` to `TypeOfRoad`

## 1.4.0
* RefLinkRequest.id (int) -> linksequenceId (long)
* RoadSysRefRequest.roadCategory,.phase changed to enums
* SegmentedLink.linkType, .detailLevel, .roadType changed to enums
* RoadPlacement.municipality was missing

## 1.3.0
* Fixed bug when parsing RoadSysRef of road segments in object.
* Fixed bug when formatting TimeAttribute.
* Section.direction, Intersection.direction, SideArea.direction changed from 
String to Direction.
* RoadSystem.roadCategory, RoadSystem.phase changed from String to enum.

## 1.2.2
* Fixed NPE when Roadsystem does not have number.
* Changes in roadnet route part of client.

## 1.2.1
* Changes in roadnet route part of client.  

## 1.2.0
* `/vegnett/elementer` has been removed
* `<? extends Attribute>` has been replaced with `<Attribute>` 

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
