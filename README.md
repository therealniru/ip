# Gojo - The Cursed Task Manager

> "Throughout heaven and earth, I alone am the honored one."

**Gojo** is a Java-based desktop application for managing tasks, optimized for use via a Command Line Interface (CLI) but with a Graphical User Interface (GUI). It is inspired by the strongest sorcerer, Gojo Satoru.

## Setting up in IntelliJ

> [!IMPORTANT]
> **Prerequisites:** JDK 17, IntelliJ IDEA (latest version).

1.  **Open IntelliJ** (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project first).
2.  **Open the project** into IntelliJ:
    1.  Click `Open`.
    2.  Select the project directory, and click `OK`.
    3.  If there are any further prompts, accept the defaults.
3.  **Configure the JDK**:
    1.  Go to `File` > `Project Structure` > `Project`.
    2.  Set the **SDK** to **Java 17** (e.g., Azul-17, Corretto-17).
    3.  Set the **Language Level** to `17 - Sealed types, always-strict floating-point semantics`.
4.  **Run the Application**:
    1.  Locate the `src/main/java/gojo/Launcher.java` file.
    2.  Right-click it and choose `Run Launcher.main()`.
    3.  The GUI should launch with the "Domain Expansion" theme.

> [!WARNING]
> Keep the `src/main/java` folder as the root folder for Java files. Do not rename or move these folders, as Gradle expects to find your source code there.

## Features

* **Manage Tasks**: Add Todo, Deadline, and Event tasks.
* **GUI**: A JavaFX interface with custom dialog boxes (Purple for Gojo, Grey for User).
* **Persistence**: Automatically saves your tasks to the hard disk.
* **Java 17**: Built using the latest LTS features.

## Launching via Gradle

If you prefer using the terminal:

```bash
./gradlew run
