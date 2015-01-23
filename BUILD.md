# How to Build and Deploy this Project

## You will need:

- A working git installation
- Access to GitHub that allows you cloning of public repositories
- A working and somewhat current maven installation (tested with Maven 3.2.3)

## Developer build

### Get the repository from github

Checkout the master branch from GitHub to a first directory:

    git clone git@github.com:mbeiter/util.git

or

	git clone https://github.com/mbeiter/util.git

Checkout the master branch from GitHub to a second directory (optional, but handy to modify the GitHub pages without 
switching branches):

    git clone git@github.com:mbeiter/util.git util-site -b gh-pages

or

	git clone https://github.com/mbeiter/util.git util-site -b gh-pages

For first-time git users: configure a default identity for git (stored in ~/.gitconfig):

    git config --global user.name "Your Name"
    git config --global user.email your@email.com

Or configure a specific identity on a per-repository level (stored in <repo-root>/.git/config)

    git config user.name "Your Name"
    git config user.email your@email.com

### Make your changes

See [CONTRIBUTING.md](CONTRIBUTING.md) for pointers on how to contribute.

### Make sure that all new and updated files have the correct license header 

Add / update the license header to all files

    mvn license:update-file-header

To create the project license file

    mvn license:update-project-license

### Run a Fortify SCA scan

- Run `mvn clean package` (this will create the target folder and resolve + copy all dependencies and sources)
- Use the audit workbench, and create a new project in "advanced" mode:
    - Set the target SDK to 1.7
    - Exclude:
         - The build-tools project 
         - All unit test directories
         - All target directories
    - Include:
         - The `main` source directories
         - The `target/dependency-sources` source directories
    - Set as classpath directory
         - All `target/classes` directories
         - All `target/dependency` directories
    - Unset as classpath directory
         - All `target/test-classes` directories
    - Make sure that all of the standard rulepacks are enabled
    - Set filters:
        - Show all issues that may have security implications
        - Show all code quality issues
        - Application is a J2EE web application
        - Application is executed with elevated privileges 

### Run the Maven build

Check if everything builds without errors and warnings and is ready for a release build (the 'release' profile uses 
strict static code analysis settings - any pushed code must pass the tests of the 'release' profile)

    mvn clean install site -P release

Commit changes to git and push: See (Contribute.md) for hints on how to commit and what to include in commit messages.


## Release build

### Set a release version:

    mvn versions:set -DnewVersion=1.0
    mvn versions:commit

**Note** that the version setting will not affect the `build-tools` component, because it is not part of the main Maven 
project! It is intentionally kept separate to allow referencing it as a build properties root from within the project.

### Run a Fortify scan (see above)

### Build the release bits and site

    mvn clean install site -P release

If you get an error message about missing dependencies during site building (e.g. JavaDoc warnings), you forgot to also
do an `install` when building the site.

### Stage the site 

    mvn site:stage
    
- Check that the information is correct 
- Check that the site was built correctly and that intra-project links work

### Push the site to Github

- Copy the staging folder to the "gh-pages" branch in the correct version folder (e.g. `[...]/docs/1.0/]
- Commit and push, using "Release 1.0 documentation" as the commit message
- Update the project start page with a link to the new documentation, and make sure that the link works 
- Check if the site looks okay on GitHub pages

### Push the bits to Maven central:

    mvn deploy -P release
    
Or (for automated builds on a single-user machine):

    mvn deploy -P release -Dgpg.passphrase=keyPassphrase
        
Then go to the [Sonatype staging repo](https://oss.sonatype.org/) and promote the build.

### Commit release version change to git and tag

    mvn clean
    git status
    git add . -A
    git commit -m "Release 1.0 prep"
    git tag -a 1.0 -m "Release 1.0"

### Set a SNAPSHOT Version (increment of current release version number):

    mvn versions:set -DnewVersion=1.1-SNAPSHOT
    mvn versions:commit
    git status
    git add . -A
    git commit -m "1.1 SNAPSHOT prep"

### Push to GitHub

    git push
