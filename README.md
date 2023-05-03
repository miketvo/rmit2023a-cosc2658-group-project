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
    ├── images/
    └── Analysis.ipynb
├── src/
    ├── main/java/vn/rmit/
        ├── developments/
        ├── SecretKey.java
        └── SecretKeyGuesser.java
    └── test/java/vn/rmit/
├── testData/
├── AssessmentDetails.md
├── README.md
├── LICENSE
├── pom.xml
└── requirements.txt
```
1. `notebooks/`: This folder contains all the images and data analysis from Jupyter Notebook based on different performance parameters 
  - `images/`: This folder contains all the results of data visualization.
  - Analysis.ipynb: The Jupyter Notebook for plotting and visualizing data.
2. `src/`: This folder contains two sub-folders: 
  - `main/java/vn/rmit/`: This folder contains the release guessing algorithm and the developments folder.
    - `developments/`: This folder contains two classes that support our testing phase.
    - SecretKey.java: The initial provided class from the project.
    - SecretKeyGuesser.java: The final key guessing algorithm.
  - `test/java/vn/rmit/`: This folder contains all the testing case throughout our devlopement
3. `README.md`: A text file containing useful reference information about this project, including how to run the algorithm
4. `LICENSE`: MIT
5. `pom.xml`: XML file used in Maven-based Java projects for building automation and dependency management tool

## Build

You'll need [Git](https://git-scm.com) to clone and Maven extension to run this project on your preferred IDE or Editor.

- On VSCode terminal
#### Before cloning our repository, you need to do some steps below:
```bash
# Open VS Code and go to the Extensions tab or simply press Ctrl + Shift + X.
# Search for "Extension Pack for Java" and click on "Install".
# After the extension is installed, VSCode will ask you to install JDK, choose the version you want and click on "Install".
# To ensure our code runs properly, our team highly recommends installing OpenJDK 8, specifically version 17.
```
```bash
# Clone this repository
$ git clone https://github.com/miketvo/rmit2023a-cosc2658-group-project.git

# Go into the repository
```
- On IntelliJ IDEA interface
```bash
# 1. Get from VCS (Version Control)
# 2. Paste https://github.com/miketvo/rmit2023a-cosc2658-group-project.git 
# into URL box
# 3. Clone
```
- Once the project is cloned, go to `src/main/java/vn/rmit/` folder and run the file `SecretKey.java`

## Video Demonstration

**TODO:** [Link](insert-link-here) or Embedded video here.
