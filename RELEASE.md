# Release

* Check that all changes are added to `CHANGELOG.md`
* If there are breaking changes bump version with `gradle markNextVersion -Prelease.version=1.X.0`
* Run `release.sh` (usually through Jenkins job)
* Log into Bintray and publish the artifacts.
* In Github: Open the new tag, «edit tag» add a release title «v1.X.Y» and add the entries from 
`CHANGELOG.md`. 
