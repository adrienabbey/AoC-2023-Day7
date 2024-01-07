/* Hand object class for Camel Cards
 * Adrien Abbey, Jan. 2024
 */

public class Hand implements Comparable<Hand> {
    /* Fields */
    public String handString;
    public int bid;
    public int handClass;

    /* Constructor */
    Hand(String handString, int handBid) {
        this.handString = handString;
        this.bid = handBid;
        this.handClass = classifyHand(handString);
    }

    /* Classes */
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
        // Note: these values are only for counting purposes!

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
        // NOTE: For Part Two, 'J' (cardCount[11]) is a wildcard.
        // If J=2 and one three of a kind, 5 of a kind.
        // If J>=3 and no pairs, 4-5 of a kind.

        // If five of a kind:
        for (int i = 0; i < 15; i++) {
            if (cardCount[i] == 5) {
                return 7;
            }
            if (AoC2023Day7.partTwo) {
                if (cardCount[i] + cardCount[11] >= 5 && i != 11) {
                    return 7;
                }
            }
        }

        // If four of a kind:
        for (int i = 0; i < 15; i++) {
            if (cardCount[i] == 4) {
                return 6;
            }
            if (AoC2023Day7.partTwo) {
                if (cardCount[i] + cardCount[11] >= 4 && i != 11) {
                    return 6;
                }
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
        if (AoC2023Day7.partTwo) {
            for (int i = 0; i < 15; i++) {
                // Possibilities:
                // If J=2 and one other pair, Four of a Kind
                // If J=1 and two pairs, Full House (2x+1J, 2y)
                for (int j = 0; j < 15; j++) {
                    if (i != 11 && j != 11 && i != j && cardCount[11] == 1 && cardCount[i] == 2 && cardCount[j] == 2) {
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
        if (AoC2023Day7.partTwo) {
            // If J=1 and one other pair, Three of a Kind
            // If J=2 and no other pair, Three of a Kind
            for (int i = 0; i < 15; i++) {
                if (i != 11 && cardCount[i] == 2 && cardCount[11] == 1) {
                    return 4;
                }
                if (cardCount[11] == 2) {
                    return 4;
                }
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
        // If J=1 and no other pair, One Pair
        if (AoC2023Day7.partTwo) {
            if (cardCount[11] > 0) {
                return 2;
            }
        }

        // Otherwise, this is high card:
        return 1;
    }

    @Override
    public int compareTo(Hand other) {
        // Compares the cards of two different hands. Assumes valid input.
        // Returns '0' if they are the same, '1' if hand1 is stronger, and '-1'
        // if hand2 is stronger.

        // Sort by class first, if possible:
        if (this.handClass > other.handClass) {
            return 1;
        } else if (this.handClass < other.handClass) {
            return -1;
        }

        // Card values:
        // A is strongest, 14 pts
        // ...
        // 2 is weakest, 2 pts

        // Convert each card into a numerical value:
        int[] thisHandInts = new int[this.handString.length()];
        int[] otherHandInts = new int[other.handString.length()];
        for (int i = 0; i < this.handString.length(); i++) {
            if (this.handString.charAt(i) == 'A') {
                thisHandInts[i] = 14;
            } else if (this.handString.charAt(i) == 'K') {
                thisHandInts[i] = 13;
            } else if (this.handString.charAt(i) == 'Q') {
                thisHandInts[i] = 12;
            } else if (this.handString.charAt(i) == 'J') {
                if (AoC2023Day7.partTwo) {
                    thisHandInts[i] = 1;
                } else {
                    thisHandInts[i] = 11;
                }
            } else if (this.handString.charAt(i) == 'T') {
                thisHandInts[i] = 10;
            } else {
                thisHandInts[i] = Character.getNumericValue(this.handString.charAt(i));
            }
        }
        for (int i = 0; i < other.handString.length(); i++) {
            if (other.handString.charAt(i) == 'A') {
                otherHandInts[i] = 14;
            } else if (other.handString.charAt(i) == 'K') {
                otherHandInts[i] = 13;
            } else if (other.handString.charAt(i) == 'Q') {
                otherHandInts[i] = 12;
            } else if (other.handString.charAt(i) == 'J') {
                if (AoC2023Day7.partTwo) {
                    otherHandInts[i] = 1;
                } else {
                    otherHandInts[i] = 11;
                }
            } else if (other.handString.charAt(i) == 'T') {
                otherHandInts[i] = 10;
            } else {
                otherHandInts[i] = Character.getNumericValue(other.handString.charAt(i));
            }
        }

        // Compare each hand's value:
        for (int i = 0; i < this.handString.length(); i++) {
            if (thisHandInts[i] > otherHandInts[i]) {
                return 1;
            }
            if (thisHandInts[i] < otherHandInts[i]) {
                return -1;
            }
        }

        // Otherwise, the hands are the same:
        return 0;
    }

    @Override
    public String toString() {
        // Converts the hand object to a string.
        return " Hand: " + this.handString + ", Bid: " + this.bid + ", Class: " + this.handClass;
    }
}
