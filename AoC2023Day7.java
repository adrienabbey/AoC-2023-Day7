/* Advent of Code, Day 7: Camel Cards
 * Adrien Abbey, Jan. 2024
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class AoC2023Day7 {
    /* Global Variables */
    public static String inputFileName = "example-input.txt";
    public static boolean testing = true;

    public static void main(String[] args) throws FileNotFoundException {
        // Load the input strings:
        ArrayList<String> inputStrings = loadInputStrings();

        // Create array lists to hold the hands and their bids:
        ArrayList<String> hands = new ArrayList<>();
        ArrayList<Integer> bids = new ArrayList<>();

        // Parse the input into usable data:
        for (String line : inputStrings) {
            // Split the string into usable parts:
            String[] splitString = line.split(" ");

            // Add each hand and bid into their relevant array list:
            hands.add(splitString[0]);
            bids.add(Integer.parseInt(splitString[1]));
        }

        // Classify each hand type:
        int[] handType = new int[hands.size()];
        for (int i = 0; i < hands.size(); i++) {
            handType[i] = classifyHand(hands.get(i));
        }

        // Test code:
        if (testing) {
            for (int i = 0; i < hands.size(); i++) {
                System.out.println("Hand: " + hands.get(i) + ", Type: " + handType[i]);
            }
        }

        // Rank the cards somehow...
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

    public static int classifyHand(String hand) {
        // Classifies the given hand string.
        // Note: I'm assuming only valid input is provided.

        // Hand types:
        // - Five of a kind: 7 (strongest)
        // - Four of a kind: 6
        // - Full house: 5
        // - Three of a kind: 4
        // - Two pair: 3
        // - One pair: 2
        // - High card: 1 (weakest)

        // Card chars and values:
        // A - 14
        // ...
        // 2 - 2

        // Create an array to track count of each card:
        int[] cardCount = new int[15]; // For readability

        // Categorize and count each card in the hand:
        for (int i = 0; i < 5; i++) {
            if (hand.charAt(i) == 'A') {
                cardCount[14]++;
            } else if (hand.charAt(i) == 'K') {
                cardCount[13]++;
            } else if (hand.charAt(i) == 'Q') {
                cardCount[12]++;
            } else if (hand.charAt(i) == 'J') {
                cardCount[11]++;
            } else if (hand.charAt(i) == 'T') {
                cardCount[10]++;
            } else {
                // Assume valid integers now.
                int cardNum = Character.getNumericValue(hand.charAt(i));
                cardCount[cardNum]++;
            }
        }

        // Classify the hand type.

        // If five of a kind:
        for (int count : cardCount) {
            if (count == 5) {
                return 7;
            }
        }

        // If four of a kind:
        for (int count : cardCount) {
            if (count == 4) {
                return 6;
            }
        }

        // If full house:
        for (int count : cardCount) {
            if (count == 3) {
                for (int secondCount : cardCount) {
                    if (secondCount == 2) {
                        return 5;
                    }
                }
            }
        }

        // If three of a kind:
        for (int count : cardCount) {
            if (count == 3) {
                return 4;
            }
        }

        // If two pair:
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (cardCount[i] == 2 && cardCount[j] == 2 && i != j) {
                    return 3;
                }
            }
        }

        // If one pair:
        for (int count : cardCount) {
            if (count == 2) {
                return 2;
            }
        }

        // Otherwise, this is high card:
        return 1;
    }
}