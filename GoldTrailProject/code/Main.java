// Name: İbrahim Eren Bol
// Student ID: 2023400093
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    // Creating output file in out direction
    private static final String OUTPUT_FILE_PATH = "out/output.txt";

    static {
        try {
            File outputFile = new File(OUTPUT_FILE_PATH);
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();
                outputFile.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Output file could not be created: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        try {
            // Check command line arguments
            boolean draw = args.length > 3;
            String mapDataFile = draw? args[1] : args[0];
            String travelCostsFile = draw? args[2] : args[1];
            String objectivesFile = draw? args[3] : args[2];

            // Read objectives
            ArrayList<int[]> objectives = FileHandler.readObjectives(objectivesFile);

            // Initialize map
            Map map = new Map(mapDataFile, travelCostsFile);

            // Initialize pathFinder
            PathFinder pathFinder = new PathFinder(map);

            // Initialize game visualizer if draw flag is set
            GameVisualizer visualizer = null;
            if (draw) {
                visualizer = new GameVisualizer(map);
                visualizer.drawMap(objectives,1);
            }

            // Initialize output file
            FileHandler.initializeOutputFile(OUTPUT_FILE_PATH);

            // Get starting position (first line in objectives file)
            int[] startingPosition = objectives.get(0);
            Tile currentPosition = map.getTile(startingPosition[0], startingPosition[1]);

            // Write starting position to output file
            FileHandler.writeStartingPosition(OUTPUT_FILE_PATH, currentPosition.toString());

            // Draw knight at starting position
            if (draw) {
                visualizer.drawKnight(currentPosition.getColumn(), currentPosition.getRow());
                StdDraw.show();
            }

            // Process each objective (starting from second line)
            int totalSteps = 0;
            double totalCost = 0.0;

            ArrayList<int[]> unreachableObjectives = new ArrayList<>();
            for (int i = 1; i < objectives.size(); i++) {

                int[] objective = objectives.get(i);
                Tile targetPosition = map.getTile(objective[0], objective[1]);

                // Find the shortest path to objective
                ArrayList<Tile> path = pathFinder.findShortestPath(currentPosition, targetPosition);

                // Check if path exists
                if (path == null || path.size() <= 1) {
                    unreachableObjectives.add(objective);
                    // Write to output file that objective cannot be reached
                    FileHandler.removeLastLine(OUTPUT_FILE_PATH);
                    FileHandler.writeObjectiveUnreachable(OUTPUT_FILE_PATH, i);
                    FileHandler.writeStartingPosition(OUTPUT_FILE_PATH, currentPosition.toString());
                    continue;
                }

                // Move knight along path and write steps to output file
                double pathCost = 0.0;
                for (int j = 1; j < path.size(); j++) {
                    Tile prevTile = path.get(j - 1);
                    Tile currentTile = path.get(j);
                    double stepCost = map.getTravelCost(prevTile, currentTile);
                    pathCost += stepCost;
                    totalCost += stepCost;
                    totalSteps++;

                    // Write step to output file
                    FileHandler.writeStep(OUTPUT_FILE_PATH, j, currentTile.toString(), pathCost);
                }

                // Animate knight movement
                if (draw) {
                    visualizer.animateKnightMovement(path, objectives, i,unreachableObjectives);
                }

                // Write objective reached to output file
                FileHandler.writeObjectiveReached(OUTPUT_FILE_PATH, i);

                // Update current position
                currentPosition = targetPosition;

                // Write new starting position for next objective
                if(objective != objectives.getLast()){
                    FileHandler.writeStartingPosition(OUTPUT_FILE_PATH, currentPosition.toString());
                }

            }

            // Write total summary
            FileHandler.writeTotalSummary(OUTPUT_FILE_PATH, totalSteps, totalCost);

            if(draw){
                StdDraw.pause(500);
                System.exit(0);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}