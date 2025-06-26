[cite_start]This GitHub repository contains a Java project for a grid-based shortest path finding game. [cite: 2] [cite_start]The game involves a knight collecting coins on a map composed of different tile types, each with varying travel costs. [cite: 2, 3, 4, 5, 6, 7, 8, 9, 10] [cite_start]The project utilizes Dijkstra's algorithm for shortest path calculations and, for a bonus section, the Held-Karp dynamic programming algorithm to solve the Traveling Salesman Problem for finding the optimal route to collect all coins. [cite: 31, 32, 35, 44, 45] [cite_start]The `StdDraw` library is used for visualizing the game, including the map, knight's movement, and paths. [cite: 3, 29]

**Project Structure:**

The project is organized into several classes:

* [cite_start]`Main`: Manages the overall game flow, including reading inputs and visualizing the game. [cite: 13, 14]
* [cite_start]`Bonus`: Implements the bonus part of the project, focusing on the Traveling Salesman Problem. [cite: 15, 16]
* [cite_start]`Tile`: Represents individual tiles on the map, storing information about their position, type (grass, sand, obstacle), neighbors, and pathfinding properties. [cite: 17, 18, 19]
* [cite_start]`FileHandler`: Handles all file operations, such as reading input files and writing output. [cite: 20, 21]
* [cite_start]`Map`: Manages the game map, loading data, storing tile information, and creating connections between adjacent tiles. [cite: 22, 23, 24]
* [cite_start]`PathFinder`: Implements Dijkstra's algorithm to find the shortest path between two tiles. [cite: 25, 26, 27]
* [cite_start]`GameVisualizer`: Manages the graphical visualization of the game using `StdDraw`. [cite: 28, 29]
* [cite_start]`ShortestRoute`: Solves the Traveling Salesman Problem (TSP) to find the shortest route visiting all objectives, utilizing Dijkstra's for precomputation and dynamic programming for optimization. [cite: 30, 31, 32, 33]

**Algorithms:**

* [cite_start]**Dijkstra's Algorithm**: Used to find the shortest path from a starting point to each objective, considering varying travel costs of different tile types (grass, sand). [cite: 35, 36, 37, 38, 39, 40, 41]
* [cite_start]**Held-Karp Dynamic Programming Algorithm**: Applied in the bonus section to find the exact shortest route that visits all objectives and returns to the start, addressing the Traveling Salesman Problem. [cite: 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54]

**Game Environment Elements:**

* [cite_start]**Knight Figure**: The player character. [cite: 4]
* [cite_start]**Coins**: Objectives for the knight to collect. [cite: 5]
* [cite_start]**Grass Tile**: Low-cost movement (1-5 units). [cite: 6, 10]
* [cite_start]**Sand Tile**: High-cost movement (8-10 units). [cite: 7, 10]
* [cite_start]**Impassable Tile**: Blocks movement. [cite: 8]
* [cite_start]**Trail Trace**: Visual representation of the knight's path. [cite: 9]
