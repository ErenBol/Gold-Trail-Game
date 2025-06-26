// Name: İbrahim Eren Bol
// Student ID: 2023400093

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class ShortestRoute {
    private Map map;
    private PathFinder pathFinder;
    private ArrayList<Tile> objectives;
    private double[][] distanceMatrix;
    private ArrayList<Tile>[][] pathMatrix;
    private final String BONUS_OUTPUT_FILE = "out/bonus.txt";

    // Constructor
    public ShortestRoute(Map map, ArrayList<Tile> objectives) {
        this.map = map;
        this.pathFinder = new PathFinder(map);
        this.objectives = objectives;
        this.distanceMatrix = new double[objectives.size()][objectives.size()];
        this.pathMatrix = new ArrayList[objectives.size()][objectives.size()];

        // Initialize distance matrix with infinity
        for (int i = 0; i < objectives.size(); i++) {
            Arrays.fill(distanceMatrix[i], Double.POSITIVE_INFINITY);
        }

        // Calculate distances and paths between all pairs of objectives
        precomputeDistancesAndPaths();
    }


    // Precomputes distances and paths between all pairs of objectives.
    private void precomputeDistancesAndPaths() {
        for (int i = 0; i < objectives.size(); i++) {
            for (int j = 0; j < objectives.size(); j++) {
                if (i == j) {
                    distanceMatrix[i][j] = 0;
                    continue;
                }

                Tile source = objectives.get(i);
                Tile destination = objectives.get(j);
                // Find the shortest path between source and destination
                ArrayList<Tile> path = pathFinder.findShortestPath(source, destination);
                if (path != null && path.size() > 1) {
                    double distance = calculatePathCost(path);
                    distanceMatrix[i][j] = distance;
                    pathMatrix[i][j] = path;
                }
            }
        }
    }

    // Calculates the total cost of a path.
    private double calculatePathCost(ArrayList<Tile> path) {
        double cost = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            cost += map.getTravelCost(path.get(i), path.get(i + 1));
        }
        return cost;
    }

    // Finds the shortest route that visits all objectives and returns to the starting point.
    // Uses dynamic programming to solve the Traveling Salesman Problem.
    public ArrayList<Integer> findShortestRoute() {
        int n = objectives.size();

        // Number of possible subsets
        int numSubsets = 1 << n;

        // dp[i][j] = minimum distance to visit subset i and end at vertex j
        double[][] dp = new double[numSubsets][n];

        // Initialize with infinity
        for (int i = 0; i < numSubsets; i++) {
            Arrays.fill(dp[i], Double.POSITIVE_INFINITY);
        }

        // Base case: start at vertex 0
        dp[1][0] = 0;

        // parent[i][j] = vertex visited before j in subset i
        int[][] parent = new int[numSubsets][n];

        // Fill dp table
        for (int mask = 1; mask < numSubsets; mask++) {
            for (int u = 0; u < n; u++) {
                // Skip if u is not in mask
                if ((mask & (1 << u)) == 0) continue;

                // Get the mask without u
                int prevMask = mask ^ (1 << u);

                // Skip if mask only contains u
                if (prevMask == 0) continue;

                for (int v = 0; v < n; v++) {
                    // Skip if v is not in prevMask
                    if ((prevMask & (1 << v)) == 0) continue;

                    // Check if going from v to u is better
                    if (dp[prevMask][v] + distanceMatrix[v][u] < dp[mask][u]) {
                        dp[mask][u] = dp[prevMask][v] + distanceMatrix[v][u];
                        parent[mask][u] = v;
                    }
                }
            }
        }

        // Find the optimal end vertex
        double minTourCost = Double.POSITIVE_INFINITY;
        int endVertex = -1;

        for (int u = 1; u < n; u++) {
            if (dp[numSubsets - 1][u] + distanceMatrix[u][0] < minTourCost) {
                minTourCost = dp[numSubsets - 1][u] + distanceMatrix[u][0];
                endVertex = u;
            }
        }

        // Reconstruct the path
        ArrayList<Integer> tour = new ArrayList<>();
        int mask = numSubsets - 1;
        int u = endVertex;

        while (u != 0) {
            tour.add(u);
            int v = parent[mask][u];
            mask = mask ^ (1 << u);
            u = v;
        }

        tour.add(0);  // Add starting point

        // Reverse to get correct order
        ArrayList<Integer> result = new ArrayList<>();
        result.add(0);  // Start at vertex 0
        for (int i = tour.size() - 2; i >= 0; i--) {
            result.add(tour.get(i));
        }
        result.add(0);  // Return to vertex 0

        return result;
    }

    // Writes the shortest route to the bonus output file.
    public void writeBonusOutput(ArrayList<Integer> orderedObjectives) throws IOException {
        // Create or clear the bonus output file
        FileWriter writer = new FileWriter(BONUS_OUTPUT_FILE);
        writer.close();

        double totalCost = 0;
        int totalSteps = 0;

        // Write each leg of the journey
        for (int i = 0; i < orderedObjectives.size() - 1; i++) {
            int sourceIndex = orderedObjectives.get(i);
            int destIndex = orderedObjectives.get(i + 1);

            Tile sourceTile = objectives.get(sourceIndex);
            Tile destTile = objectives.get(destIndex);

            // Get the path between these objectives
            ArrayList<Tile> path = pathMatrix[sourceIndex][destIndex];

            if (path == null || path.size() <= 1) {
                writer = new FileWriter(BONUS_OUTPUT_FILE, true);
                writer.write("Cannot reach objective " + destIndex + " from objective " + sourceIndex + "!\n");
                writer.close();
                continue;
            }

            // Write each step of this leg

            for (int j = 1; j < path.size(); j++) {
                Tile prevTile = path.get(j - 1);
                Tile currentTile = path.get(j);
                double stepCost = map.getTravelCost(prevTile, currentTile);
                totalCost += stepCost;
                totalSteps++;

                writer = new FileWriter(BONUS_OUTPUT_FILE, true);
                writer.write(String.format(Locale.US,"Step Count: %d, move to %s. Total Cost: %.2f.\n",
                        totalSteps, currentTile.toString(), totalCost));
                writer.close();
            }
            // Write the reached message
            writer = new FileWriter(BONUS_OUTPUT_FILE, true);
            if (i < orderedObjectives.size() - 2) {
                writer.write("Objective " + destIndex + " reached!\n");
            }
            writer.close();
        }

        // Write total summary
        writer = new FileWriter(BONUS_OUTPUT_FILE, true);
        writer.write(String.format(Locale.US,"Total Step: %d, Total Cost: %.2f", totalSteps, totalCost));
        writer.close();
    }

    // Gets the ordered sequence of paths for the shortest route.
    public ArrayList<ArrayList<Tile>> getOrderedPaths(ArrayList<Integer> orderedObjectives) {
        ArrayList<ArrayList<Tile>> orderedPaths = new ArrayList<>();

        for (int i = 0; i < orderedObjectives.size() - 1; i++) {
            int sourceIndex = orderedObjectives.get(i);
            int destIndex = orderedObjectives.get(i + 1);

            orderedPaths.add(pathMatrix[sourceIndex][destIndex]);
        }

        return orderedPaths;
    }
}