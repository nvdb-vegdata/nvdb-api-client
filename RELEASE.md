# Release information
Run `release.sh` (usually through Jenkins job) after completing the preparations below. In Github: Open the new tag, «edit tag» add a release title «vX.Y.Z» and add the entries from `CHANGELOG.md`.

#Preparations
Check that all changes are added to `CHANGELOG.md`. The latest version specified will be injected into the project version for releases.
To make releases to the remote, you need the following properties in your `gradle.properties` file (typically located in `user_dir/.gradle/.gradle.properties`):

|property|description|
| :--- | :--- |
| nexusUsername | sonatype username for the repository |
| nexusPassword | sonatype password OR api key|
| signing.keyId | public key for GPG |
| signing.password | passphrase for GPG |
| signing.secretKeyRingFile | absolute file location for the keyring file |

Contact the system owner to get the required information.
