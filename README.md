# Signal Flow Graph 📊

Welcome to the Signal Flow Graph repository! This project is designed to facilitate the implementation and analysis of signal flow graphs, which are essential tools in systems theory and control engineering for representing the flow of signals within a system. This repository contains comprehensive documentation, examples, and tools to help you understand and work with signal flow graphs effectively.

## Table of Contents

-   [Introduction](#introduction) 🌐
-   [Features](#features) ✨
-   [Installation](#installation) 🛠️
-   [User_Guide](#user-guide) 📚
-   [Examples](#examples) 🌟
-   [Routh Criterion Program](#routh-criterion-program) 📈
-   [Contributors](#contributors) 👩‍💻👨‍💻

## Introduction

Signal flow graphs (SFG) are graphical representations used to visualize the flow of signals and the relationships between variables in a system of linear algebraic equations. These graphs are extensively used in control systems, signal processing, and communications. This repository provides a set of tools to create, analyze, and manipulate signal flow graphs, allowing for a deeper understanding of system dynamics and transfer functions. It contains a program for Signal flow graph representation of a given system given the total number of nodes and the numeric values of the branches gains. A web based program using VueJS for frontend and Java-Springboot for backend.

## Features

- **Graph Representation:** Tools to create and visualize signal flow graphs, helping users to see the structure and flow of their systems.
- **Mason's Gain Formula:** Implementations to calculate the overall transfer function of a system using Mason's Gain Formula, a fundamental technique in control theory.
- **Path and Loop Identification:** Methods to identify forward paths, individual loops, and non-touching loops within the graph, crucial for analyzing system behavior.
- **Transfer Function Calculation:** Functions to compute the transfer function of the system, providing insights into the input-output relationship.
- **Visualization:** Capabilities to generate and display graphical representations of signal flow graphs for better understanding and presentation.

## Installation

To use the tools and examples provided in this repository, you need to have Python installed on your system. You can install the necessary dependencies by cloning the repository and running the installation command.

1. Clone the repository to your local machine.
2. Navigate to the repository directory.
3. Install the required dependencies.

## User Guide

Once the dependencies are installed, you can start using the tools provided in this repository. The usage section includes descriptions of how to create a signal flow graph, add nodes and edges, and perform various analyses such as calculating the transfer function and identifying paths and loops.

### Creating a Signal Flow Graph

- Enter the total number of nodes in the problem at its field in the main bar.
- Set the nodes position by clicking the nodes button in the sidebar, after enabling it, you can click at any empty place in the board to put the node.
- After setting all nodes click the connect button to start adding branches between nodes.
- Enter the gain of each branch in its field in the sidebar then choose any two nodes to add this branch with the entered gain in it.
- Make sure that feedback branch gain must be negative, and the forward branch gain must be positive to get a right transfer function.
- To change the value of the branch gain, make another connect between the same two nodes with different value, if you need to remove the branch make the new value equal to 0.

### Calculating the Transfer Function

- After adding all nodes and branches press start to get your problem solution.
- The solution is displayed as following:
    - Transfer Function Gain.
    - Forward Paths nodes.
    - Individual Loops nodes.
    - Non touching loops nodes separated as a group by a line.
    - Deltas values.
- Now You can close the Output window and clear the graph, so you can add a new problem to solve.
One of the key features is the ability to calculate the transfer function of the system using Mason's Gain Formula. This involves identifying all forward paths, loops, and non-touching loops, and applying the formula to determine the overall transfer function from the input to the output.

## Examples

<img height="200" width="500" alt="1" src="https://github.com/MostafaElKaranshawy/Signal-Flow-Graph/assets/162327055/24299a23-9e50-4b7e-8a43-5dc94b21946d">


<img height="200" width="500" alt="2" src="https://github.com/MostafaElKaranshawy/Signal-Flow-Graph/assets/162327055/e6eb6203-41f6-470d-964b-10450be15bce">


# Routh Criterion Program

For a given characteristic equation, the program uses the Routh-Hurwitz Stability Criterion to determine if the system is stable. If the system is not stable, it lists the number and values of poles in the right-hand side (RHS) of the s-plane.

## Overview

This part file provides detailed information about the Routh Criterion program, which is part of a larger project that includes Signal Flow Graph representation. The Routh Criterion program determines the stability of a system based on its characteristic equation.

## Main Features

- **Degree Input**: The program accepts the degree (a) of the characteristic equation.
- **Coefficient Input**: The user inputs the coefficients for each variable in the equation from Sª to S⁰.
- **Stability Check**: Utilizes the Routh-Hurwitz Stability Criterion to check the system's stability by examining the sign changes in the first column of the Routh table.
- **Routh Table**: Generates and prints the Routh table.
- **Stability Report**: Informs the user if the system is stable or not.
- **Pole Identification**: If unstable, identifies the roots in the RHS of the s-plane causing instability.
- **Special Case Handling**:
    - **Zero Entry Replacement**: Replaces zero entries in the first column of the Routh table with a very small value (EPSILON = 10¯⁹).
    - **Zero Row Handling**: Replaces rows full of zeros with the derivative of the auxiliary equation to check for duplicate roots on the jw-axis.

## Examples

<img   height="370" width="320" alt="4" src="https://github.com/MostafaElKaranshawy/Signal-Flow-Graph/assets/162327055/54bedce6-9a8b-4232-9b50-df3fb7a750c0">
<img  height="370"width="320" alt="4" src="https://github.com/MostafaElKaranshawy/Signal-Flow-Graph/assets/162327055/c038b9cc-2791-4c37-8269-e5e2b082e2f6">
<img height="370"width="320"  alt="6" src="https://github.com/MostafaElKaranshawy/Signal-Flow-Graph/assets/162327055/4d59d2a2-00fb-4e56-9fd4-8ea5b3c549ea">


# Contributors

This project is a collaborative effort by the talented students of CSED26. The team members who contributed to the development and success of this project are:

- [Rowan Gamal](https://github.com/rowanxgamal)
- [Karene Antoine](https://github.com/Karene-Antoine)
- [Moustafa ElKaranshawy](https://github.com/MostafaElKaranshawy)
- [Amir Ragaie](https://github.com/Amir-Ragaie)
- [Youssef Mahmoud](https://github.com/Youssef-Mahmoud0)
- [Youmna Yasser](https://github.com/yomnay888)
