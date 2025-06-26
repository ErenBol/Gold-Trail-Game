// Name: İbrahim Eren Bol
// Student ID: 2023400093

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Locale;

public class FileHandler {

    //Reads objectives from the specified file and returns them as an ArrayList of int arrays.
    public static ArrayList<int[]> readObjectives(String objectivesFilePath) throws FileNotFoundException {
        ArrayList<int[]> objectives = new ArrayList<>();
        Scanner scanner = new Scanner(new File(objectivesFilePath));

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] coordinates = line.split(" ");
            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);

            objectives.add(new int[]{x, y});
        }

        scanner.close();
        return objectives;
    }

    // Creates or clears the output file.
    public static void initializeOutputFile(String outputFilePath) throws IOException {
        // Create directories if they don't exist
        File outputFile = new File(outputFilePath);
        outputFile.getParentFile().mkdirs();

        // Clear the file
        new PrintWriter(outputFile).close();
    }

    // Writes the starting position to the output file.
    public static void writeStartingPosition(String outputFilePath, String startingPosition) throws IOException {
        FileWriter writer = new FileWriter(outputFilePath, true);
        writer.write("Starting position: " + startingPosition + "\n");
        writer.close();
    }

    //Writes a step to the output file.
    public static void writeStep(String outputFilePath, int stepCount, String position, double totalCost) throws IOException {
        FileWriter writer = new FileWriter(outputFilePath, true);
        writer.write(String.format(Locale.US,"Step Count: %d, move to %s. Total Cost: %.2f.\n",
                stepCount, position, totalCost));
        writer.close();
    }

    // Writes an objective reached message to the output file.
    public static void writeObjectiveReached(String outputFilePath, int objectiveIndex) throws IOException {
        FileWriter writer = new FileWriter(outputFilePath, true);
        writer.write("Objective " + objectiveIndex + " reached!\n");
        writer.close();
    }

    // Writes an objective cannot be reached message to the output file.
    public static void writeObjectiveUnreachable(String outputFilePath, int objectiveIndex) throws IOException {
        FileWriter writer = new FileWriter(outputFilePath, true);
        writer.write("Objective " + objectiveIndex + " cannot be reached!\n");
        writer.close();
    }

    // Removes the last line from the output file.
    public static void removeLastLine(String filePath) throws IOException {
        File file = new File(filePath);
        ArrayList<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        }
        // Remove the last line
        if (!lines.isEmpty()) {
            lines.remove(lines.size() - 1);
        }

        // Write the remaining lines back to the file
        try (FileWriter writer = new FileWriter(file, false)) {
            for (String line : lines) {
                writer.write(line + System.lineSeparator());
            }
        }
    }

    // Writes the total summary to the output file.
    public static void writeTotalSummary(String outputFilePath, int totalSteps, double totalCost) throws IOException {
        FileWriter writer = new FileWriter(outputFilePath, true);
        writer.write(String.format(Locale.US, "Total Step: %d, Total Cost: %.2f", totalSteps, totalCost));
        writer.close();
    }
}