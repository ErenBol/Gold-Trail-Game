// Name: İbrahim Eren Bol
// Student ID: 2023400093

import java.util.ArrayList;

public class Tile {
    private int column;    // Column position of the tile
    private int row;       // Row position of the tile
    private int type;      // 0: Grass, 1: Sand, 2: Obstacle
    private ArrayList<Tile> adjacentTiles; // List of adjacent tiles

    // For pathfinding algorithm
    private double distanceFromStart; // Distance from the start tile
    private boolean visited;          // Flag to check if the tile has been visited
    private Tile previous;            // Previous tile in the path


    // Constructor
    public Tile(int column, int row, int type) {
        this.column = column;
        this.row = row;
        this.type = type;
        this.adjacentTiles = new ArrayList<>();

        // Initialize pathfinding properties
        this.distanceFromStart = Double.POSITIVE_INFINITY;
        this.visited = false;
        this.previous = null;
    }

    // Adds an adjacent tile to this tile's list of neighbors.
    public void addAdjacentTile(Tile tile) {
        if (!adjacentTiles.contains(tile)) {
            adjacentTiles.add(tile);
        }
    }

    // Returns the column position of the tile.
    public int getColumn() {
        return column;
    }

    // Returns the row position of the tile.
    public int getRow() {
        return row;
    }

    // Returns the type of the tile.
    public int getType() {
        return type;
    }

    // Returns the list of adjacent tiles.
    public ArrayList<Tile> getAdjacentTiles() {
        return adjacentTiles;
    }

    // Sets the distance from the start tile.
    public void setDistanceFromStart(double distance) {
        this.distanceFromStart = distance;
    }

    // Returns the distance from the start tile.
    public double getDistanceFromStart() {
        return distanceFromStart;
    }

    // Sets the tile if it is visited or not.
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    // Returns if the tile is visited or not.
    public boolean isVisited() {
        return visited;
    }

    // Sets the previous tile in the path.
    public void setPrevious(Tile previous) {
        this.previous = previous;
    }

    // Returns the previous tile in the path.
    public Tile getPrevious() {
        return previous;
    }

    // Resets the tile properties of the tile.
    public void reset() {
        this.distanceFromStart = Double.POSITIVE_INFINITY;
        this.visited = false;
        this.previous = null;
    }

    // Returns a string representation of the tile.
    @Override
    public String toString() {
        return "(" + column + ", " + row + ")";
    }

    // Compares this tile with another tile for equality based on their positions.
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Tile other = (Tile) obj;
        return column == other.column && row == other.row;
    }

}