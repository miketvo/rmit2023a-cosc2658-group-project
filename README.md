# COSC2658 Data Structures and Algorithms - Group Project

[Assessment Details](AssessmentDetails.md)

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
│   ├── main/java/vn/rmit/
│   │   ├── developments/
│   │   ├── SecretKey.java
│   │   └── SecretKeyGuesser.java
│   └── test
├── testData/
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
    - `main/java/vn/rmit/`: This folder contains the final guessing algorithm and the `development` Java package.
    - `developments/`: This Java package folder contains two classes that support our development and testing phase.
    - `SecretKey.java`: The initial provided class from the project.
    - `SecretKeyGuesser.java:` The final key guessing algorithm.
    - `test`: This folder contains all test cases (using JUnit) for various secret key.
3. `testData/`: This folder contains all test results from JUnit tests in .csv format.
4. `README.md`: A text file containing useful reference information about this project, including how to run the algorithm.
5. `LICENSE`: MIT
6. `pom.xml`: XML file used in Maven-based Java projects for building automation and dependency management tool
7. `requirements.txt`: Text file for `pip` installation of necessary packages for our data visualization and anlysis in `notebooks`


## Build

You'll need [Git](https://git-scm.com) to clone and Maven extension to run this project on your preferred IDE or Editor.
#### Before cloning our repository, you need to do some steps below:

- On VSCode terminal
```bash
# Open VS Code and go to the Extensions tab or simply press Ctrl + Shift + X.
# Search for "Extension Pack for Java" and click on "Install", 
# it includes Maven support and other important Java development features.
# After the extension is installed, VSCode will ask you to install JDK, 
# choose the version you want and click on "Install".
# To ensure our code runs properly, our team highly recommends 
# installing OpenJDK 8, specifically version 17.
```
For more information, refer to [Java Build on VSCode](https://code.visualstudio.com/docs/java/java-build)
```bash
# Clone this repository
$ git clone https://github.com/miketvo/rmit2023a-cosc2658-group-project.git

# Go into the repository
```
- On IntelliJ IDEA interface
```bash
# 1. Get from VCS (Version Control)
# 2. Paste https://github.com/miketvo/rmit2023a-cosc2658-group-project.git into URL box
# 3. Clone
```
- Once the project is cloned, go to `src/main/java/vn/rmit/` folder and run the file `SecretKey.java`


## Video Demonstration

**TODO:** [Link](insert-link-here) or Embedded video here.
