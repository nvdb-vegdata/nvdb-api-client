# Release

* Check that all changes are added to `CHANGELOG.md`
* If there are breaking changes bump version with `gradle markNextVersion -Prelease.version=1.X.0`
* Run `release.sh` (usually through Jenkins job)
* Log into Bintray and publish the artifacts.

