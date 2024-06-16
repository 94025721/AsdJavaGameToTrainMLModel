# Simple Java Game to Train Machine Learning Models

## Overview

This project is a simple Java game designed to test and train machine learning models. It includes various components such as player movement, enemy AI, collision detection, and level progression. The game environment was intended to be used with a Deep Q-Network (DQN) model to train an agent, but the DQN implementation is currently not working as expected.

Due to the persistent issues with the DQN model and performance problems in Java, I have decided to switch the project to Python (can be found [here](https://github.com/plusplus-hub/SimplePyGameToTrainMLModels)). The Python ecosystem offers more robust libraries and frameworks for machine learning and game development, which should facilitate smoother implementation and training.

## Current Status

### DQN Model Issues
The DQN model implementation in this project is not functioning correctly. I suspect the problem lies in outdated libraries and unexpected compatibility issues. Despite various troubleshooting attempts, the model fails to train the agent properly.

### Performance Challenges
The game does not run smoothly, which impacts both gameplay and the training process. To address this, I implemented a Quad Tree structure to optimize collision detection and enhance performance. However, this solution did not significantly improve the game's performance. The cause is probably that I am running Intellij with snap which somehow slows down the I/O processes. 

## Game Components

### Settings
The `Settings` class provides configuration parameters such as game width and height.

### Entities
The game includes various entities:
- **Player**: Controlled by the user or AI.
- **Enemy**: Moves according to predefined strategies and poses challenges to the player.
- **Coin**: Collectible items scattered across the levels.
- **Wall**: Obstacles that the player and enemies must navigate around.
- **Target Zone**: The goal area that the player must reach to finish the level.

### Collision Manager
The `CollisionManager` class handles collision detection and response for:
- Player movements
- Enemy movements
- Coin collection

### Game Manager
The `GameManager` class orchestrates the game's main loop, updating the game state and managing levels.

### Game Panel
The `GamePanel` class is responsible for rendering the game graphics and updating the display.

### Level Loader
The `LevelLoader` class loads level configurations from JSON files, including enemy positions, walls, coins, and target zones.

### Quad Tree
The `QuadTree` class is used to optimize collision detection by partitioning the game space into manageable sections.

### Game Environment
The `GameEnvironment` class encapsulates the game state and provides methods to reset, get the current state, and take actions. This is crucial for integrating with machine learning models.

### Dependencies
- Java Development Kit (JDK)
- [DeepLearning4J](https://deeplearning4j.konduit.ai/) for DQN implementation (currently not functioning)
