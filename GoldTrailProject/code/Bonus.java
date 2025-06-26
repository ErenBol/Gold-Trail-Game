// Name: İbrahim Eren Bol
// Student ID: 2023400093

import java.io.IOException;
import java.util.ArrayList;

public class Bonus {
    public static void main(String[] args) {
        try {
            // Check command line arguments
            boolean draw = args.length > 3;
            String mapDataFile = draw? args[1] : args[0];
            String travelCostsFile = draw? args[2] : args[1];
            String objectivesFile = draw? args[3] : args[2];

            ArrayList<int[]> objectiveCoordinates = FileHandler.readObjectives(objectivesFile);

            if (objectiveCoordinates.isEmpty()) {
                System.out.println("No objectives found in the file.");
                return;
            }

            // Initialize map
            Map map = new Map(mapDataFile, travelCostsFile);

            // Initialize game visualizer if draw flag is set
            GameVisualizer visualizer = null;
            if (draw) {
                visualizer = new GameVisualizer(map);
                visualizer.drawMap(objectiveCoordinates, 1);
                StdDraw.show();
            }

            // Convert objective coordinates to tiles
            ArrayList<Tile> objectives = new ArrayList<>();
            for (int[] coord : objectiveCoordinates) {
                Tile tile = map.getTile(coord[0], coord[1]);
                if (tile != null) {
                    objectives.add(tile);
                }
            }

            // Initialize ShortestRoute
            ShortestRoute shortestRoute = new ShortestRoute(map, objectives);

            // Find the shortest route
            ArrayList<Integer> orderedObjectives = shortestRoute.findShortestRoute();

            // Write the result to the bonus output file
            shortestRoute.writeBonusOutput(orderedObjectives);

            if (draw) {
                // Get ordered paths
                ArrayList<ArrayList<Tile>> orderedPaths = shortestRoute.getOrderedPaths(orderedObjectives);

                // Initial display all paths and all objectives
                visualizer.drawMap(new ArrayList<>(), 0); // Draw map
                visualizer.drawPathsWithColors(orderedPaths); // Draw all paths

                // Draw all gold coins ensuring coins are drawn above the paths
                for (int i = 1; i < objectiveCoordinates.size(); i++) {
                    int[] objective = objectiveCoordinates.get(i);
                    visualizer.drawGoldCoin(objective[0], objective[1]);
                }

                // Draw knight at starting position
                Tile startingPosition = objectives.get(0);
                visualizer.drawKnight(startingPosition.getColumn(), startingPosition.getRow());
                StdDraw.show();

                // Pause to let user see the initial state
                StdDraw.pause(1000);

                // Animate knight movement - coins will disappear when collected
                visualizer.animateKnightMovementBonus(orderedPaths, objectiveCoordinates);
                StdDraw.pause(500);
                System.exit(0);
            }

        } catch (IOException e) {
            System.out.println("Error reading/writing files: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}