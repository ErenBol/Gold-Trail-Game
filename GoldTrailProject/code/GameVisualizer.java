// Name: İbrahim Eren Bol
// Student ID: 2023400093

import java.awt.Color;
import java.util.ArrayList;

public class GameVisualizer {
    private Map map;                // The map to visualize
    private double tileSize;        // Size of each tile in pixels
    private double canvasWidth;     // Width of the canvas in pixels
    private double canvasHeight;    // Height of the canvas in pixels
    // Colors for different paths
    private static final Color[] PATH_COLORS = {
            Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.MAGENTA, Color.CYAN
    };
    private static final Color PATH_COLOR = new Color(255, 0, 0);  // Default path color
    private static final String grassTile = "misc/grassTile.jpeg";          // Grass tile image path
    private static final String sandTile = "misc/sandTile.png";             // Sand tile image path
    private static final String obstacleTile = "misc/impassableTile.jpeg";  // Obstacle tile image path
    private static final String coinImage = "misc/coin.png";                // Coin image path
    private static final String knightImage = "misc/knight.png";            // Knight image path

    // Fixed tile size for consistent visualization
    private static final double FIXED_TILE_SIZE = 40.0;

    // Constructor
    public GameVisualizer(Map map) {
        this.map = map;

        // Use fixed tile size and calculate canvas dimensions based on map size
        this.tileSize = FIXED_TILE_SIZE;

        // Calculate canvas dimensions based on map size
        this.canvasWidth = map.getColumns() * tileSize;
        this.canvasHeight = map.getRows() * tileSize;

        // Set up the drawing canvas
        StdDraw.setCanvasSize((int) canvasWidth, (int) canvasHeight);
        StdDraw.setXscale(0, canvasWidth);
        StdDraw.setYscale(0, canvasHeight);
        StdDraw.enableDoubleBuffering();
    }

    // Method to draw the map with all tiles and uncollected objectives
    public void drawMap(ArrayList<int[]> objectives, int objectiveStartingPosition) {
        StdDraw.clear();
        // Draw all tiles
        for (int x = 0; x < map.getColumns(); x++) {
            for (int y = 0; y < map.getRows(); y++) {
                Tile tile = map.getTile(x, y);
                if (tile == null) continue;

                double pixelX = x * tileSize;
                double pixelY = (map.getRows() - 1 - y) * tileSize; // Invert Y to match the coordinate system

                // Set path of tile image based on tile type
                String tileType;
                switch (tile.getType()) {
                    case 0:
                        tileType = grassTile;
                        break;
                    case 1:
                        tileType = sandTile;
                        break;
                    case 2:
                        tileType = obstacleTile;
                        break;
                    default:
                        tileType = grassTile;
                        break;
                }

                // Draw tile
                StdDraw.picture(pixelX + tileSize / 2, pixelY + tileSize / 2, tileType, tileSize, tileSize);
            }
        }

        // Draw remaining objectives
        for (int i = objectiveStartingPosition; i < objectives.size(); i++) {
            int[] objective = objectives.get(i);
            drawGoldCoin(objective[0], objective[1]);
        }
    }

    // Method to draw the knight at a specific position
    public void drawKnight(int column, int row) {
        double pixelX = column * tileSize + tileSize / 2;
        double pixelY = (map.getRows() - 1 - row) * tileSize + tileSize / 2;

        // Draw knight
        StdDraw.picture(pixelX, pixelY, knightImage, tileSize, tileSize);
    }

    // Methpd to draw the gold coin at a specific position
    public void drawGoldCoin(int column, int row) {
        double pixelX = column * tileSize + tileSize / 2;
        double pixelY = (map.getRows() - 1 - row) * tileSize + tileSize / 2;

        // Draw gold coin
        StdDraw.picture(pixelX, pixelY, coinImage, tileSize, tileSize);
    }

    // Method to animate the knight's movement along a path for Main class
    public void animateKnightMovement(ArrayList<Tile> path, ArrayList<int[]> objectives, int currentObjectiveIndex, ArrayList<int[]> unreachableObjectives) {
        if (path == null || path.isEmpty()) return;

        // Animate knight movement
        for (int i = 0; i < path.size(); i++) {
            Tile tile = path.get(i);
            // Redraw the map to clear previous knight position
            drawMap(objectives, currentObjectiveIndex);

            // Redraw the path up to the current position
            drawPartialPath(path, i);

            // Draw unreachable objectives to ensure they are not erased after decided to be unreachable
            for (int[] unreachableObjective : unreachableObjectives) {
                drawGoldCoin(unreachableObjective[0], unreachableObjective[1]);
            }

            // Draw knight at new position
            drawKnight(tile.getColumn(), tile.getRow());
            StdDraw.show();
            // Pause for animation
            StdDraw.pause(100);
        }
    }

    // Helper method to draw the path up to the current position
    private void drawPartialPath(ArrayList<Tile> path, int currentIndex) {
        StdDraw.setPenColor(PATH_COLOR);
        for (int i = 0; i <= currentIndex; i++) {
            Tile tile = path.get(i);
            double pixelX = tile.getColumn() * tileSize + tileSize / 2;
            double pixelY = (map.getRows() - 1 - tile.getRow()) * tileSize + tileSize / 2;
            StdDraw.filledCircle(pixelX, pixelY, tileSize / 8);
        }
    }


    // Draw paths with different colors for each path (for bonus part)
    public void drawPathsWithColors(ArrayList<ArrayList<Tile>> orderedPaths) {
        for (int i = 0; i < orderedPaths.size(); i++) {
            ArrayList<Tile> path = orderedPaths.get(i);
            Color pathColor = PATH_COLORS[i % PATH_COLORS.length];
            drawPathWithColor(path, pathColor);
        }
    }

    // Draw a path with a specific color (helper method for drawPathsWithColors)
    private void drawPathWithColor(ArrayList<Tile> path, Color color) {
        if (path == null || path.isEmpty()) return;

        StdDraw.setPenColor(color);
        for (Tile tile : path) {
            double pixelX = tile.getColumn() * tileSize + tileSize / 2;
            double pixelY = (map.getRows() - 1 - tile.getRow()) * tileSize + tileSize / 2;
            StdDraw.filledCircle(pixelX, pixelY, tileSize / 8);
        }
    }

    // Method to animate the knight's movement along multiple paths (for bonus part)
    public void animateKnightMovementBonus(ArrayList<ArrayList<Tile>> orderedPaths, ArrayList<int[]> objectives) {
        // Keep track of which objectives have been collected
        boolean[] collectedObjectives = new boolean[objectives.size()];
        collectedObjectives[0] = true; // Starting position is not an objective

        for (ArrayList<Tile> path : orderedPaths) {
            if (path == null || path.isEmpty()) continue;

            // Animate knight movement along the path
            for (int i = 0; i < path.size(); i++) {
                Tile tile = path.get(i);
                int knightX = tile.getColumn();
                int knightY = tile.getRow();

                // Check if knight is on any objective position
                for (int j = 0; j < objectives.size(); j++) {
                    int[] objective = objectives.get(j);
                    // If knight is on an objective that hasn't been collected yet, collect it
                    if (!collectedObjectives[j] && objective[0] == knightX && objective[1] == knightY) {
                        collectedObjectives[j] = true;
                    }
                }

                // Redraw the map to clear previous knight position and paths
                drawMap(new ArrayList<>(), 0);
                drawPathsWithColors(orderedPaths);

                // Draw only uncollected objectives
                for (int j = 0; j < objectives.size(); j++) {
                    if (!collectedObjectives[j]) {
                        int[] objective = objectives.get(j);
                        drawGoldCoin(objective[0], objective[1]);
                    }
                }

                // Draw knight at new position
                drawKnight(knightX, knightY);
                StdDraw.show();
                // Pause for animation
                StdDraw.pause(100);
            }
        }
    }
}