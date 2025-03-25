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
mvn package
```
Now you can run each module individually from the project root.
```bash
# Run the server
java -jar server/target/server-version.jar
# Run the client
java -jar client/target/client-version.jar
```
> ⚠️ Note that the server must be started before the client, otherwise the client has nothing to connect to.

## Development
We enforce certain styling rules using PMD and Checkstyle.
These rules are checked before commit using commit hooks.
For this reason there is a `setup` script, or rather two scripts, that sets up these hooks in your local repository.

### Windows
On Windows run the bat script from the project root.
```powershell
.\setup.bat
```
> Note that for running git hooks on windows you need to use Git Bash which
> comes with git installation by default. Git hooks should work with IntelliJ IDEA out of the box, regardless of OS.
### Linux
On Linux run this script.
```bash
./setup.sh
```
