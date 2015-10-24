# How to Build and Deploy this Project

## You will need:

- A working git installation
- Access to GitHub that allows you cloning of public repositories
- A working and somewhat current maven installation (tested with Maven 3.2.3)

**Note** that there is no "developer build".

## Release build

### Set a release version:

    mvn versions:set -DnewVersion=1.0
    mvn versions:commit

### Build the release bits and push them to Maven Central

There is no "Maven site" for this project. This is a "build container" project, and does not have Maven site metrics or
static code analysis results.

Run the Maven release build and push the bits to Maven Central:

    mvn clean deploy
    
Or (for automated builds on a single-user machine):

    mvn deploy -P release -Dgpg.passphrase=keyPassphrase
        
Then go to the [Sonatype staging repo](https://oss.sonatype.org/) and promote the build.

### Commit release version change to git and tag

    mvn clean
    git status
    git add . -A
    git commit -m "Build Tools Release 1.0 prep"
    git tag -a BT-1.0 -m "Build Tools Release 1.0"

### Push to GitHub

    git push
