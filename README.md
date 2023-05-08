# COSC2658 Data Structures and Algorithms - Group Project

For problem description, see [Assessment Details](AssessmentDetails.md).

---


## Contribution

| Student Name         | Student ID | Contribution Score |
|:---------------------|:-----------|:------------------:|
| Do Le Long An        | S-3963207  |         3          |
| Nguyen Nguyen Khuong | S-3924577  |         3          |
| Vo Tuong Minh        | S-3877562  |         3          |
| Tran Ly The Quang    | S-3878707  |         3          |


## Project Structure

```
.
├── notebooks/
│   ├── images/
│   └── Analysis.ipynb
├── src/
│   ├── main/java/vn/rmit/cosc2658
│   │   ├── development/
│   │   │   ├── InteractiveApp.java
│   │   │   ├── SecretKey.java
│   │   │   └── SecretKeyGuesser.java
│   │   ├── SecretKey.java
│   │   └── SecretKeyGuesser.java
│   └── test/java/vn/rmit/cosc2658/development
│       └── SecretKeyGuesserTest.java
├── test-data/
├── AssessmentDetails.md
├── README.md
├── LICENSE
├── pom.xml
└── requirements.txt
```

1. `notebooks/`: This folder contains all the images and data analysis with Jupyter Notebook based on different performance parameters
    - `images/`: This folder contains all the results of data visualization.
    - `Analysis.ipynb`: The Jupyter Notebook for plotting and visualizing data.
2. `src/`: This folder contains two sub-folders:
    - `main/java/vn/rmit/cosc2658`: This folder contains the final guessing algorithm and the `development` Java package.
    - `development/`: This Java package folder contains two classes that support our development and testing phase.
    - `test/`: This folder contains all test cases (using JUnit) for various test secret keys.
    - `SecretKey.java`: The initial provided class from the [Assessment Details](AssessmentDetails.md).
    - `SecretKeyGuesser.java:` The final key guessing algorithm implementation.
3. `test-data/`: This folder contains all test results from JUnit tests in .csv format.
4. `README.md`: A text file containing useful reference information about this project, including how to run the algorithm.
5. `LICENSE`: MIT
6. `pom.xml`: XML file used in Maven-based Java projects for building automation and dependency management tool
7. `requirements.txt`: Text file for `pip` installation of necessary packages for our data visualization and analysis in `notebooks`.


## Development Environment

### Global Requirements

| Requirement                        | Version |
|:-----------------------------------|:-------:|
| [Git](https://git-scm.com)         | latest  |
| [Maven](https://maven.apache.org/) |  4.0.0  |
| [OpenJDK](https://openjdk.org/)    | 17.0.2  |
| [Python](https://www.python.org/)  |  3.11   |

**Note:** For this project, we are using Python [virtual environment](https://docs.python.org/3/library/venv.html) to keep it light. However, feel free to use [Anaconda](https://www.anaconda.com/) if it is more convenient.

### VSCode Requirements

Extension requirements for building and running Java classes:

| Requirement                                                                                             | Version |
|:--------------------------------------------------------------------------------------------------------|:-------:|
| [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack) | latest  |
| [Test Runner for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-test)    | latest  |

Additional extensions for viewing data visualization in `notebooks`:

| Requirement                                                                       | Version |
|:----------------------------------------------------------------------------------|:-------:|
| [Python](https://marketplace.visualstudio.com/items?itemName=ms-python.python)    | latest  |
| [Jupyter](https://marketplace.visualstudio.com/items?itemName=ms-toolsai.jupyter) | latest  |

To properly setup Java and Python development environment for our project, refer to the following documentation:

- [Java in VSCode](https://code.visualstudio.com/docs/languages/java).
- [Java Testing in VSCode](https://code.visualstudio.com/docs/java/java-testing).
- [Data Science in VSCode](https://code.visualstudio.com/docs/datascience/overview).

To run tests, you need to install JUnit Maven dependency:

```bash
$ mvn install
```

If you are viewing and running Python scripts in `notebooks`, install the required Python packages:

```bash
$ pip install -r ./requirements.txt
```

### IntelliJ IDEA

| Requirement                                                                        | Version |
|:-----------------------------------------------------------------------------------|:-------:|
| [Python Plugin for IntelliJ IDEA](https://plugins.jetbrains.com/plugin/631-python) | latest  |

**Note:** The above requirement is needed only for viewing data visualization in `notebooks`. All Java functionalities and toolchain integrations are IDE's built-ins, so no other plugins are required.

To properly setup OpenJDK 17 to work with IntelliJ IDEA toolchain, refer to this documentation: [IntelliJ IDEA - SDKs](https://www.jetbrains.com/help/idea/sdk.html#change-module-sdk).

To run tests, you need to install JUnit:

```bash
$ mvn install
```

To enable and run Python functionality within IntelliJ IDEA, refer to the following documentation: [Python in IntelliJ IDEA](https://www.jetbrains.com/help/idea/python.html).

If you are viewing and running Python scripts in `notebooks`, install the required Python packages:

```bash
$ pip install -r ./requirements.txt
```

Refer to [IntelliJ IDEA - Getting Started](https://www.jetbrains.com/help/idea/getting-started.html) to learn about building, executing, debugging, and testing with IntelliJ IDEA and its Maven toolchain integration.


## Build and Execution

- A console [applet](src/main/java/vn/rmit/cosc2658/development/InteractiveApp.java) is available for you to manually play with guessing a random secret key. Edit line 7 to use any positive non-zero key length of choice.
- Our final solution can be run by calling `vn.rmit.cosc2658.SecretKey.main()`.
- All tests are in `vn.rmit.cosc2658.development.SecretKeyGuesserTest`.


## Video Demonstration

Video available on YouTube: [Link](https://youtu.be/414mOLgm3i4).
