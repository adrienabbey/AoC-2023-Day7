/* Advent of Code, Day 7: Camel Cards
 * Adrien Abbey, Jan. 2024
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class AoC2023Day7 {
    /* Global Variables */
    public static String inputFileName = "example-input.txt";
    public static boolean testing = false;

    public static void main(String[] args) throws FileNotFoundException {
        // Load the input strings:
        ArrayList<String> inputStrings = loadInputStrings();

        // Create array lists to hold the hands:
        ArrayList<Hand> handsList = new ArrayList<>();

        // Parse the input into usable data:
        for (String line : inputStrings) {
            // Split the string into usable parts:
            String[] splitString = line.split(" ");

            // Add a new hand to the list of hands:
            handsList.add(new Hand(splitString[0], Integer.parseInt(splitString[1])));
        }

        // Test code:
        if (testing) {
            System.out.println("Pre-sort:");
            for (Hand hand : handsList) {
                System.out.println(hand.toString());
            }
        }

        // Sort the hands:
        Collections.sort(handsList);

        // Test code:
        if (testing) {
            System.out.println("Post-sort:");
            for (Hand hand : handsList) {
                System.out.println(hand.toString());
            }
        }

        // Calculate and print the total winnings:
        long totalWinnings = 0;
        for (int i = 0; i < handsList.size(); i++) {
            // Each hand's winnings is its rank * bid:
            totalWinnings += handsList.get(i).bid * (i + 1);
        }

        // Print the total winnings:
        System.out.println("Total winnings is: " + totalWinnings);
    }

    public static ArrayList<String> loadInputStrings() throws FileNotFoundException {
        // Reads strings from the input file, loading them into an array list.

        // Open the input file:
        File inputFile = new File(inputFileName);
        Scanner inputScanner = new Scanner(inputFile);

        // Load strings from the input file into an array list:
        ArrayList<String> inputStrings = new ArrayList<>();
        while (inputScanner.hasNextLine()) {
            inputStrings.add(inputScanner.nextLine());
        }

        // Close the input scanner:
        inputScanner.close();

        // Return the array list of strings:
        return inputStrings;
    }
}