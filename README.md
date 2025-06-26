# Knight's Pathfinder Project

A Java implementation of a grid-based pathfinding system using Dijkstra's algorithm to find the optimal path for a knight collecting coins on various terrain types.

## Features

- **Pathfinding**: Uses Dijkstra's algorithm to find the shortest path between objectives
- **Terrain System**: Different tile types with varying movement costs (grass, sand, obstacles)
- **Visualization**: Interactive visualization using StdDraw library
- **Bonus Implementation**: Solves the Traveling Salesman Problem (TSP) using Held-Karp algorithm

## Classes Overview

1. **Main**: Manages the standard game flow
2. **Bonus**: Handles the TSP solution for visiting all objectives optimally
3. **Tile**: Represents individual map tiles with properties and neighbors
4. **Map**: Manages the game map and tile connections
5. **PathFinder**: Implements Dijkstra's algorithm for pathfinding
6. **GameVisualizer**: Handles all graphical output
7. **ShortestRoute**: Solves the TSP problem
8. **FileHandler**: Manages all file I/O operations

## How to Run

### Standard Mode
```
java Main [map_file] [costs_file] [objectives_file]
```

### With Visualization
```
java Main -d [map_file] [costs_file] [objectives_file]
```

### Bonus Mode
```
java Bonus [map_file] [costs_file] [objectives_file]
```

## Output
- Standard mode writes to `out/output.txt`
- Bonus mode writes to `out/bonus.txt`

## Screenshots
![Game Visualization](media/image9.png)
*Figure 1: 15x10 Game Screen*

![Bonus Visualization](media/image11.png)
*Figure 3: Bonus Part Game Screen*

## Algorithms
- **Main Pathfinding**: Dijkstra's Algorithm
- **Bonus TSP Solution**: 
  - Preprocessing with Dijkstra's
  - Held-Karp Dynamic Programming Algorithm

## Requirements
- Java 8+
- StdDraw library (included in project)
