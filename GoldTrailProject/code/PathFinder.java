// Name: İbrahim Eren Bol
// Student ID: 2023400093

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class PathFinder {
    private Map map;

   // Constructor
    public PathFinder(Map map) {
        this.map = map;
    }


    //Finds the shortest path from the source tile to the destination tile.
    public ArrayList<Tile> findShortestPath(Tile source, Tile destination) {
        // If source or destination is an obstacle or null, no path exists
        if (source == null || destination == null || source.getType() == 2 || destination.getType() == 2) {
            return null;
        }

        // Reset all tiles for a fresh search
        resetTiles();

        // Set up the source tile
        source.setDistanceFromStart(0);

        // Set up the priority queue for Dijkstra's algorithm
        PriorityQueue<Tile> queue = new PriorityQueue<>(Comparator.comparingDouble(Tile::getDistanceFromStart));
        queue.add(source);

        // Dijkstra's algorithm
        while (!queue.isEmpty()) {
            Tile current = queue.poll();

            // Skip already visited tiles
            if (current.isVisited()) {
                continue;
            }

            // Mark as visited
            current.setVisited(true);

            // Check if we've reached the destination
            if (current.equals(destination)) {
                return reconstructPath(source, destination);
            }

            // Check all adjacent tiles
            for (Tile neighbor : current.getAdjacentTiles()) {
                // Skip visited tiles
                if (neighbor.isVisited()) {
                    continue;
                }

                // Calculate new distance
                double cost = map.getTravelCost(current, neighbor);
                if (cost == Double.POSITIVE_INFINITY) {
                    continue; // Skip if no direct connection
                }

                double newDistance = current.getDistanceFromStart() + cost;

                // Update distance if a shorter path is found
                if (newDistance < neighbor.getDistanceFromStart()) {
                    neighbor.setDistanceFromStart(newDistance);
                    neighbor.setPrevious(current);

                    // Add or update in queue
                    queue.remove(neighbor); // Remove if already in queue
                    queue.add(neighbor);    // Add with updated priority
                }
            }
        }

        // No path found
        return null;
    }

    // Method to reconstruct the path from source to destination.
    private ArrayList<Tile> reconstructPath(Tile source, Tile destination) {
        ArrayList<Tile> path = new ArrayList<>();
        Tile current = destination;

        // Add all tiles from destination to source in reverse order
        while (current != null && !current.equals(source)) {
            path.add(current);
            current = current.getPrevious();
        }

        // Add the source tile
        if (current != null) {
            path.add(current);
        }

        // Reverse to get path from source to destination
        Collections.reverse(path);

        return path;
    }


    //Resets all tiles in the map for a fresh path search.
    private void resetTiles() {
        for (int x = 0; x < map.getColumns(); x++) {
            for (int y = 0; y < map.getRows(); y++) {
                Tile tile = map.getTile(x, y);
                if (tile != null) {
                    tile.reset();
                }
            }
        }
    }
}