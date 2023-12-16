# CANalyzer

## Introduction

CANalyzer is a powerful desktop application designed for the analysis, visualization, and control of <a href="https://en.wikipedia.org/wiki/CAN_bus">Controller Area Network (CAN)</a> communication. The application facilitates seamless interaction with CAN devices, allowing users to monitor, send, and receive both data and remote frames effortlessly. CANalyzer is specifically tailored for users who connect their nodes to the CANalyzer node, providing a centralized hub for managing and controlling CAN communication.

## Features

- <strong>Real-time Visualization:</strong> Gain insights into CAN communication with real-time visualization of frames, enabling users to monitor network activity efficiently.

- <strong>Frame Control:</strong> Send and receive both data and remote frames, offering users the ability to control and manipulate CAN communication within the network.

- <strong>Node Connectivity:</strong> Seamlessly connect your CAN devices to CANalyzer, transforming it into a centralized control station for managing the connected nodes.

## Getting Started

### Prerequisites

Before using CANalyzer, ensure you have the following:

- <strong>NodeCAN Connectivity:</strong> Connect your CAN devices to the CANalyzer node for effective communication.

- <strong>CANalyzer Desktop App:</strong> Install the CANalyzer desktop application on your computer.

### Installation

- Ensure that you have Java Development Kit (JDK) 17 installed on your computer.
- Download and install JavaFX SDK 21 on your machine.
- Install Xampp, and import the database called "can" into Mysql.
  
<strong>Note:</strong> The database is located in package "helpers".

```
cd CANAnalyzer

java --module-path "C:\Program Files\Java\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.fxml,javafx.web -jar exported_javafx_app.jar
```

## Usage

Follow the steps below to effectively use CANalyzer and explore its features:

### Login

- Upon launching CANalyzer, you will be prompted to log in with your credentials.
- Enter your username and password to access the main features.
  <div align="center"> 
<img src="https://github.com/ranizouaoui/CANAnalyzer/blob/main/Pictures/Login.png" alt="" />
 </div>

### Home Page

- After successful login, you will be directed to the home page.
- The home page provides a brief introduction to CANalyzer and guides users on getting started.
- Explore the features and functionalities available to monitor, send, and receive CAN frames.
<div align="center"> 
<img src="https://github.com/ranizouaoui/CANAnalyzer/blob/main/Pictures/home.png" alt="" />
 </div>
 
### Sending and Receiving Frames

- CANalyzer enables users to send and receive both data and remote frames.
- Take control of your CAN communication and interact with connected devices.
<div align="center"> 
<img src="https://github.com/ranizouaoui/CANAnalyzer/blob/main/Pictures/Frames.png" alt="" />
 </div>

  ## Want more details?
 
 For more details or if you want to recommend me for other projects. Just contact me on my email: <strong> ranizouaouicontact@gmail.com </strong>
