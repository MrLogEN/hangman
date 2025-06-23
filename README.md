# Hangman

Hangman is a simple game created for the purpose of Client-Server applications in Java course at Prague University
of Economics and Business.
## Prerequisites
### Java JDK 21 
#### Windows
You can download it from [Oracle's website](https://www.oracle.com/java/technologies/downloads/#jdk21-windows)
or from [OpenJDK's website](https://jdk.java.net/java-se-ri/21)

#### Ubuntu
You can download the Java JDK 21 from the official apt repositories or from [OpenJDK's website](https://jdk.java.net/java-se-ri/21)
```bash
sudo apt update && sudo apt install openjdk-21-jdk
```
#### Other Linux distros
As a primary method, use your distro's package manager. For rolling release distros the package might not be available
so you can try to find it in AUR (Arch User Repository) or use the links provided above.


The Oracle's version, you can find here [OpenJDK's website](https://www.oracle.com/java/technologies/downloads/#jdk21-linux)

### Maven
Installing Meven can be done by following instructions from Maven [official website](https://maven.apache.org/install.html)

### PMD
PMD is a static code analyzer that can be used to enforce good programming habits and convetions.

## Run
Clone this repository
```bash
# via HTTPS
git clone https://github.com/MrLogEN/hangman.git
# via SSH
git clone git@github.com:MrLogEN/hangman.git
```

Change directory to the cloned repository
```bash
cd hangman
```
From the root of the repository run the following command
```bash
mvn clean package
```
Now you can run each module individually from the project root.
```bash
# Run the server
java -jar server/target/server-version.jar
# Run the client
java -jar client/target/client-version.jar
```
> [!WARNING]\
> The server must be started before the client, otherwise the client has nothing to connect to.

## Development
We enforce certain styling rules using PMD and Checkstyle.
These rules are checked before commit using commit hooks.
For this reason there is a `setup` script, or rather two scripts, that sets up these hooks in your local repository.

### Windows
On Windows run the bat script from the project root.
```powershell
.\setup.bat
```
> [!NOTE]\
> For running git hooks on windows you need to use Git Bash which
> comes with git installation by default. Git hooks should work with IntelliJ IDEA out of the box, regardless of OS.
### Linux
On Linux run this script.
```bash
./setup.sh
```
### Git hooks
Git hooks run PMD and Checkstyle.
You can run these check by yourself from your terminal:

```bash
# PMD
# run for each module separately
mvn -f "module_name/pom.xml" pmd:check

#Checkstyle
# run from the project root
mvn checkstyle:check
```
These checks will prompt you to fix mistakes if any.

> [!TIP]\
> You might want to disable the check when commiting non-java files.
> You can do that by running
> `git commit --no-verify`
> or
> Editing the VC settings to not run git hooks in your IDE.

## Workflow
When you are working on a new feature, you should create a new issue on GitHub.

Provide some description as this makes it easier for others to understand what you are working on.
Describing what you are about to do can help you to understand the problem better.
![create_new_issue](https://github.com/user-attachments/assets/43ec5494-48d6-4fc3-99ef-ae7f7c3afa93)

When you are ready to start working on the issue, create a new branch from the `main` branch.
This can be achieved by creating a new branch on GitHub through your issue.

![create_branch_from_issue_button](https://github.com/user-attachments/assets/67c8c2bf-cdfb-4b75-bdb6-042e3e2bc7c0)
![create_branch_from_issue](https://github.com/user-attachments/assets/0ad04ef0-3301-41ce-857d-26192d7e0cc6)

As you can see creating the branch this way prefixes the branch with a number. This comes in handy when you try to checkout that branch.

Then you can fetch the newly created branch to your local repository.
```bash
git fetch
git checkout branch_name
```
> [!WARNING]\
> Make sure to fetch the changes first and checkout the branch.
> Otherwise, you might end up with the branch not existing (on your local clone) and making changes on the `main` branch.

When you are done with the feature, you can create a pull request on GitHub. Which can then be either reviewed by others
when you are not confident with your changes or merged by yourself when you are confident.

### Commit frequency
It is advised to commit often. This way you can easily revert changes if something goes wrong.
It is also easier to understand what you have done when you have smaller commits.

Commit message should be in the imperative mood. This means that the message should be a command.
For example, `Add new feature` or `Fix bug` NOT `Adding a feature` etc.

> Don't commit unrelated changes in one commit. This makes it harder to understand what you have done.

### Rebasing and Squashing
Sometimes when you commit often the history can become cluttered. In this case, you can squash commits.

Squashing can be done through interactive rebase.
```bash
git rebase -i HEAD~n # n is the number of commits you want to use for rebase
```
You can also specify commit hash instead of `HEAD~n` to rebase from a specific commit.

When you run this command, you will be presented with a list of commits in your default editor.

> [!TIP]\
> You can change the git's default editor by running `git config --global core.editor "editor_name"`

Let's say you have 5 commits and you want to squash them into one. This is how the list would look like.
```bash
pick b01c43f First commit
pick ec79f09 Second commit
pick 59d2be9 Third commit
pick 48faf16 Fourth commit
pick 5868bc3 Fifth commit

# Rebase 5fd6d92..5868bc3 onto 5fd6d92 (5 commands)
#
# Commands:
# p, pick <commit> = use commit
# r, reword <commit> = use commit, but edit the commit message
# e, edit <commit> = use commit, but stop for amending
# s, squash <commit> = use commit, but meld into previous commit
# f, fixup [-C | -c] <commit> = like "squash" but keep only the previous
#                    commit's log message, unless -C is used, in which case
#                    keep only this commit's message; -c is same as -C but
#                    opens the editor
# x, exec <command> = run command (the rest of the line) using shell
# b, break = stop here (continue rebase later with 'git rebase --continue')
# d, drop <commit> = remove commit
# l, label <label> = label current HEAD with a name
# t, reset <label> = reset HEAD to a label
# m, merge [-C <commit> | -c <commit>] <label> [# <oneline>]
# .       create a merge commit using the original merge commit's
# .       message (or the oneline, if no original merge commit was
# .       specified); use -c <commit> to reword the commit message
#
# These lines can be re-ordered; they are executed from top to bottom.
#
# If you remove a line here THAT COMMIT WILL BE LOST.
#
# However, if you remove everything, the rebase will be aborted.

```
As you can see there are 5 commits. You can see their hashes and commit messages. The commits are prefixed with `pick`.
That means that these commits will be picked up by the rebase. As you can see the interactive rebase provides you with 
a list of commands that you can use to manipulate the commits. 

In our case we want to use `squash` or `s` command to squash the commits. Let's say we want to squash the last commit 
it would look like this.
```bash
pick b01c43f First commit
pick ec79f09 Second commit
pick 59d2be9 Third commit
pick 48faf16 Fourth commit
squash 5868bc3 Fifth commit
```

When you are satisfied with the changes you can save and close the editor. The rebase will start and you will be
prompted to edit the commit message. If you are squashing multiple commits which are separated by pick commands,
you will be prompted to change the commit message for each commit that you are squashing into.

```bash
pick b01c43f First commit
squash ec79f09 Second commit
pick 59d2be9 Third commit
pick 48faf16 Fourth commit
squash 5868bc3 Fifth commit
```

More detailed tutorial for interactive rebase can be found on [Sitepoint](https://www.sitepoint.com/git-interactive-rebase-guide/)

It is a good practice to rebase your branch on the `main` branch before creating a pull request.
```bash
git fetch origin
git rebase origin/main
```
You should always fetch the changes from remote before rebasing. This way you can avoid conflicts.

After any form of rebase it is necessary to force push the changes to the remote repository.
```bash
git push -f
```
