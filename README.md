# FIX Protocol Trading Simulator

The FIX Protocol Trading Simulator is a Java-based program that allows users to simulate electronic trading and experiment with trading algorithms. The program consists of three independent components that communicate over a network: a market component, a broker component, and a message router.

## Objective

The objective of this project is to create a lightning-fast, enterprise-grade trading simulator that allows users to experiment with trading algorithms and simulate electronic trading. The project requires the use of non-blocking sockets and the Java Executor framework for message handling. It will also be developed as a multi-module Maven build.

## Requirements

To successfully implement a winning solution, the program must meet the following key requirements:

- Use non-blocking sockets to ensure the program is lightning-fast and can handle large volumes of data.
- Use the Java Executor framework for efficient message handling and processing.
- Develop the program as a multi-module Maven build for easier maintenance and deployment.
- Implement a clean and easy-to-read design that can be easily modified to meet changing requirements.

## Usage

To use the program, first, make sure you have Java installed on your machine. Clone the repository and navigate to the project directory. Use Maven to compile the program by running the following command in your terminal:

mvn clean install

Once the program has been compiled, navigate to the target directory and run the following command to start the simulator:

java -jar trading-simulator.jar

## Contributions

This project is open for contributions. If you have any suggestions or improvements, please feel free to submit a pull request or open an issue.

## License

This project is licensed under the MIT License - see the LICENSE.md file for details.
