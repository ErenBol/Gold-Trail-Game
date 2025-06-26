// Name: İbrahim Eren Bol
// Student ID: 2023400093
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Map {
    private int columns;
    private int rows;
    private Tile[][] tiles;
    private HashMap<String, Double> travelCosts; // Key: "x1,y1,x2,y2", Value: cost

    // Constructor
    public Map(String mapDataFilePath, String travelCostsFilePath) throws FileNotFoundException {
        travelCosts = new HashMap<>();
        loadMapData(mapDataFilePath);
        loadTravelCosts(travelCostsFilePath);
        createAdjacentTileConnections();
    }

    // Getters

    // Gets the tile at the specified position.
    public Tile getTile(int column, int row) {
        if (column >= 0 && column < columns && row >= 0 && row < rows) {
            return tiles[column][row];
        }
        return null;
    }

    // Returns number of columns
    public int getColumns() {
        return columns;
    }

    // Returns number of rows
    public int getRows() {
        return rows;
    }

    // Loads map data from the specified file.
    private void loadMapData(String mapDataFilePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(mapDataFilePath));

        // Read map dimensions
        String[] dimensions = scanner.nextLine().trim().split(" ");
        columns = Integer.parseInt(dimensions[0]);
        rows = Integer.parseInt(dimensions[1]);

        // Initialize tiles array
        tiles = new Tile[columns][rows];

        // Initialize all tiles as grass (type 0) by default
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                tiles[x][y] = new Tile(x, y, 0);
            }
        }

        // Read tile types
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] tileData = line.split(" ");
            int x = Integer.parseInt(tileData[0]);
            int y = Integer.parseInt(tileData[1]);
            int type = Integer.parseInt(tileData[2]);

            if (x >= 0 && x < columns && y >= 0 && y < rows) {
                tiles[x][y] = new Tile(x, y, type);
            }
        }

        scanner.close();
    }

    // Loads travel costs from the specified file.
    private void loadTravelCosts(String travelCostsFilePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(travelCostsFilePath));

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] costData = line.split(" ");
            int x1 = Integer.parseInt(costData[0]);
            int y1 = Integer.parseInt(costData[1]);
            int x2 = Integer.parseInt(costData[2]);
            int y2 = Integer.parseInt(costData[3]);
            double cost = Double.parseDouble(costData[4]);

            // Store costs in both directions
            travelCosts.put(x1 + "," + y1 + "," + x2 + "," + y2, cost);
            travelCosts.put(x2 + "," + y2 + "," + x1 + "," + y1, cost);
        }

        scanner.close();
    }

    // Creates connections between adjacent tiles on the map.
    private void createAdjacentTileConnections() {
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // Up, Right, Down, Left

        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                Tile currentTile = tiles[x][y];

                // Skip obstacle tiles
                if (currentTile.getType() == 2) continue;

                for (int[] dir : directions) {
                    int newX = x + dir[0];
                    int newY = y + dir[1];

                    // Check if the neighboring tile is within bounds
                    if (newX >= 0 && newX < columns && newY >= 0 && newY < rows) {
                        Tile neighborTile = tiles[newX][newY];

                        // Add connection if the neighbor is not an obstacle
                        if (neighborTile.getType() != 2) {
                            currentTile.addAdjacentTile(neighborTile);
                        }
                    }
                }
            }
        }
    }

    // Gets the travel cost between two adjacent tiles.
    public double getTravelCost(Tile from, Tile to) {
        // Check if tiles are obstacles
        if (from.getType() == 2 || to.getType() == 2) {
            return Double.POSITIVE_INFINITY;
        }

        // Generate the key for the travel cost
        String key = from.getColumn() + "," + from.getRow() + "," + to.getColumn() + "," + to.getRow();

        // Return the travel cost if it exists, otherwise infinity
        return travelCosts.getOrDefault(key, Double.POSITIVE_INFINITY);
    }


}