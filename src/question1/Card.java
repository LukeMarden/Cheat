package question1;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/**
 * @author Luke Marden
 */
public class Card implements Comparable<Card>, Serializable {
    static final long serialVersionUID = 100250071;
    private final Suit suit;
    private final Rank rank;
    /**
     * enum for the suit of the card
     */
    enum Suit{
        CLUBS, SPADES, DIAMONDS, HEARTS;
    }
    /**
     * enum for the rank of the card
     */
    enum Rank {
        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8),
        NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10), ACE(11);
        private final int value;
        Rank(int x){
            this.value=x;
        }

        /**
         * gets the value of the enum
         * @return the numerical value of the enum
         */
        public int getValue() {
            return value;
        }

        /**
         * gets the previous rank
         * @return previous rank
         */
        public Rank getPrevious() {
            if (this.equals(Rank.TWO)) {
                return Rank.ACE;
            }
            else {
                return Rank.values()[this.ordinal()-1];
            }

        }

        /**
         * gets the next rank
         * @return the next rank
         */
        public Rank getNext() {
            if (this.equals(Card.Rank.ACE)) {
                return Card.Rank.TWO;
            }
            else {
                return Card.Rank.values()[this.ordinal()+1];
            }
        }

    }
    /**
     * card constructor
     * @param inputSuit
     * @param inputRank
     */
    public Card(Suit inputSuit, Rank inputRank) {
        this.suit = inputSuit;
        this.rank = inputRank;
    }
    /**
     * get the suit
     * @return returns the cards suit
     */
    public Suit getSuit() {
        return suit;
    }
    /**
     * get the rank
     * @return returns the cards rank
     */
    public Rank getRank() {
        return rank;
    }
    /**
     * toString method
     * @return returns a string version of the class
     */
    public String toString() {
        return (rank + " of " + suit);
    }
    /**
     * difference in ranks of two cards
     * @param card1 a card
     * @param card2 card to be compared to card1
     * @return calculates the difference in ranks
     */
    public static int difference(Card card1, Card card2) {
        return Math.abs(card1.rank.ordinal()-card2.rank.ordinal());
    }
    /**
     * difference in values of the cards
     * @param card1 a card
     * @param card2 card to be compared to card1
     * @return calculates the difference in values of the card
     */
    public static int differenceValue(Card card1, Card card2) {
        return Math.abs(card1.rank.getValue() - card2.rank.getValue());
    }
    /**
     * compareTo method
     * @param card card to compare with
     * @return returns -1 if the card is less, 0 if the same, +1 if the card is bigger
     */
    @Override
    public int compareTo(Card card) {
        int compare = this.rank.compareTo(card.rank);
        // If the rank is the same, compare the suit
        if(compare == 0){
            compare = this.suit.compareTo(card.suit);
        } // 0 if true
        return compare;
    }
    /**
     * a class to sort cards into ascending order
     */
    public static class CompareAscending implements Comparator<Card> {
        /**
         * sort cards into ascending order
         * @param c1 a card
         * @param c2 a card to compare c1 with
         * @return a number based on whether the card is smaller, bigger, or the same rank
         */
        @Override
        public int compare(Card c1, Card c2) {
            return c1.rank.ordinal() - c2.rank.ordinal();
        }
    }
    /**
     * a class to sort cards into an order depending on their suit
     */
    public static class CompareSuit implements Comparator<Card> {
        /**
         * sort the cards into an order based on suit
         * @param c1 a card
         * @param c2 a card to compare c1 with
         * @return a number based on whether the card is smaller, bigger, or the same rank
         */
        @Override
        public int compare(Card c1, Card c2) {
            int cmp = c1.suit.compareTo(c2.suit);
            if(cmp == 0){
                int cmpRank = c1.rank.compareTo(c2.rank);
                return cmpRank;
            }
            else{
                return cmp;
            }
        }
    }

    /**
     * Class containing a lambda to compare cards
     */
    public static class SelectTest {
        ArrayList<Card> cards;
        public SelectTest(ArrayList<Card> cards) {
            this.cards = cards;
        }

        /**
         * Tests a cards rank and value against the arraylist of cards stored inside the class.
         * @param card1 the card to test against the arraylist of cards
         */
        public void test(Card card1) {

            testBigger suitBigger = (c1, c2) -> (new CompareSuit().compare(c2, c1) > 1);
            testBigger valueBigger = (c1, c2) -> (new CompareAscending().compare(c1, c2) > -1);
            Iterator iterator = cards.iterator();
            while (iterator.hasNext()) {
                Card card2 = (Card)iterator.next();
                System.out.println(card1.toString());
                System.out.println(card2.toString());
                if (suitBigger.testBigger(card1,card2)&&valueBigger.testBigger(card1,card2)) {
                    System.out.println("Card 1 is biggest based on value and suit");
                }
                else if (suitBigger.testBigger(card2,card1)&&valueBigger.testBigger(card2,card1)) {
                    System.out.println("Card 2 is biggest based on value and rank");
                }
                else if (!suitBigger.testBigger(card1,card2)&&valueBigger.testBigger(card1,card2)) {
                    System.out.println("Card 1 has a bigger value");
                }
                else if (!suitBigger.testBigger(card2,card1)&&valueBigger.testBigger(card2,card1)) {
                    System.out.println("Card 2 has a bigger value");
                }
                else if (suitBigger.testBigger(card1,card2)&&!valueBigger.testBigger(card1,card2)) {
                    System.out.println("Card 1 has a higher suit");
                }
                else if (suitBigger.testBigger(card2,card1)&&!valueBigger.testBigger(card2,card1)){
                    System.out.println("Card 2 has a higher suit");
                }
                else {
                    System.out.println("They are the same card");
                }


            }
        }

        /**
         * Interface for the lambda
         */
        interface testBigger {
            boolean testBigger(Card c1, Card c2);
        }



    }
    /**
     *
     * main method to test the methods in card
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException{
        Card c1 = new Card(Suit.SPADES, Rank.ACE);
        System.out.println("c1 suit = " + c1.getSuit());
        System.out.println("c1 rank = " + c1.getRank());
        System.out.println("c1 toString = " + c1.toString());

        Card c2 = new Card(Suit.SPADES, Rank.JACK);
        System.out.println("\n\nc1 = " + c1);
        System.out.println("c2 = " + c2);
        System.out.println("difference = " + difference(c1,c2));
        System.out.println("difference value = " + differenceValue(c1,c2));

        ArrayList<Card> random = new ArrayList<Card>();
        for (int i = 12; i>=0;i--) {
            random.add(new Card(Suit.SPADES, Rank.values()[i]));
        }
        System.out.println("\n\nbefore ascending");
        System.out.println(random.get(0).toString());
        Collections.sort(random, new Card.CompareAscending());
        System.out.println("after ascending");
        System.out.println(random.get(0).toString());

        random = new ArrayList<Card>();
        for (int i = 3; i>=0;i--) {
            random.add(new Card(Suit.values()[i], Rank.ACE));
        }
        System.out.println("\n\nbefore comparesuit");
        System.out.println(random.get(0).toString());
        Collections.sort(random, new Card.CompareSuit());
        System.out.println("after comparesuit");
        System.out.println(random.get(0).toString());

        System.out.println("\n\ncard to be serialized = " + c1.toString());
        c1.serialize("c1");
        Card c3 = deserialize("c1");
        System.out.println("card after deserialization = " + c3.toString());

        System.out.println("\n\nCard 1 = " + c1.toString());
        System.out.println("next rank = " + new Card(Suit.SPADES, c1.getRank().getNext()).toString());
        System.out.println("previous rank = " + new Card(Suit.SPADES, c1.getRank().getPrevious()).toString());

        System.out.println("\n\n");
        ArrayList<Card> temp = new ArrayList();
        temp.add(new Card(Card.Suit.CLUBS, Card.Rank.FOUR));
        temp.add(new Card(Card.Suit.CLUBS, Card.Rank.EIGHT));
        c1 = new Card(Card.Suit.DIAMONDS, Card.Rank.SEVEN);
        SelectTest selectTest = new SelectTest(temp);
        selectTest.test(c1);



    }
    /**
     * stores a card object in a file
     * @param filename the filename to store the object
     * @throws IOException
     */
    public void serialize(String filename) throws IOException{
        try (FileOutputStream fos = new FileOutputStream(filename + ".ser")) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
        }
    }
    /**
     * retrieves an object from the specified file
     * @param filename the filename to look for
     * @return the object stored in the file
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Card deserialize(String filename) throws IOException,
            ClassNotFoundException{

        Card card;
        try (FileInputStream fis = new FileInputStream(filename + ".ser")) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            card = (Card) ois.readObject();
            ois.close();
        }
        return card;
    }





}
