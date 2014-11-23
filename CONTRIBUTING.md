# How to Contribute

## Reporting Issues / Submitting Feature Requests

- If you have found a bug, create an [issue](https://github.com/mbeiter/util/issues) on GitHub
- If you want to submit a feature request, send an email to Mike (<michael@beiter.org>)

## Workflow

- Check the [issue tracker](https://github.com/mbeiter/util/issues) to find out if the problem has already been 
  reported. If you find the problem on the tracker, add additional details.
- Create an [issue](https://github.com/mbeiter/util/issues) on GitHub
    - Clearly describe the issue, and include steps to reproduce it (really important in case of a bug) 
    - Do not forget to include the version number, debug output, etc. 
- Fork the repository on GitHub
- Create a feature branch in Git
    - Do not directly work on the master branch to avoid conflicts if you pull updates
    - Typically, you would create a branch from the master branch (e.g. to fix a bug or implement a new feature)
    - Use a descriptive name that explains the new branch's intent
- Make sure your changes meet the quality requirements:
    - Follow the build instructions, the build (project source and site) must build without errors or warnings 
    - Avoid bad coding practices like duplication, global state, excessive external dependencies, etc pp
    - Dependency versions must be specified in a DependencyManagement section 
    - The new code compiles cleanly with the SCA tools that are configured in the Maven build
    - The new code has >85% test code coverage, and in particular the critical paths are thoroughly tested
    - Review all of your changes before committing (e.g. `git diff` to review your changes, and `git diff --check` to 
      check for unnecessary whitespace)  
- Commit: Use descriptive commit messages 
    - The first line should be a descriptive sentence what the commit is doing, allowing a reviewer to fully understand 
      what the commit does by just reading this single line
    - Include reference to ticket number, prefixed with #, at the end of the first line
    - If the commit is non-trivial, the single line summary should be followed by a blank line and an enumerated list 
      with the details of the commit.
    - Add keywords to the commit, like `Review by @gituser` if you want to notify someone to review the changes    
- Open a [Pull Request](https://help.github.com/articles/using-pull-requests) on GitHub, and use the CLI to 
  [attach the pull request to an existing issue](http://opensoul.org/blog/archives/2012/11/09/convert-a-github-issue-into-a-pull-request/).
- Your code will be reviewed and the Pull Request will be merged into the master branch.
 
### Example for a Commit Message

    Added a new interface and factory for generic non-SQL database connections. Fixes #12345

      * Details how the interface works (review @mbeiter)
      * Details how the factory works


# Resources

## Project Specific

- [Mike's Blog](http://www.michael.beiter.org)
- [Project home](http://mbeiter.github.io/util/)
- [Build instructions](BUILD.md)
- [GitHub Issue Tracker](https://github.com/mbeiter/util/issues)

## GitHub Resources

- [GitHub documentation](https://help.github.com/)
- [GitHub contribution intro](https://guides.github.com/activities/contributing-to-open-source/)
- [GitHub issue tracker intro](https://guides.github.com/features/issues/)
- [GitHub markdown intro](https://guides.github.com/features/mastering-markdown/)
- [GitHub pull request documentation](https://help.github.com/send-pull-requests/)
