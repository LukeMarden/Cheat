package question2;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * @author Luke Marden
 */

public class Hand extends ArrayList<Card> implements Iterable<Card>, Serializable{
    static final long serialVersionUID = 100250071+10;
    private int numSuit[];
    private int numRank[];
    private int total;
    private ArrayList<Card> orderAdded = new ArrayList<>(this);

    public Hand() {
        this.numSuit = new int[4];
        this.numRank = new int[13];
        this.total = 0;
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Hand Main:");
        Card c1 = new Card(Card.Suit.SPADES, Card.Rank.ACE);
        Card c2 = new Card(Card.Suit.SPADES, Card.Rank.TWO);
        Collection<Card> col = new ArrayList();
        col.add(c1);
        col.add(c2);
        Hand hand = new Hand();
        System.out.println("hand size of hand with 0 cards = " + hand.size());
        hand.add(col);
        System.out.println("hand size of hand after adding 2 cards via arraylist = " + hand.size());

        Hand toAdd = new Hand();
        toAdd.add(c1);
        toAdd.add(c2);
        hand = new Hand();
        System.out.println("\nhand size of hand with 0 cards = " + hand.size());
        hand.add(toAdd);
        System.out.println("hand size of hand with 2 cards after adding 2 cards via another hand = " + hand.size());

        hand.removeCard(c2);
        System.out.println("\nhand size after removing one card via a card = " + hand.size());

        hand = new Hand();
        hand.addCard(c2);
        hand.addCard(c2);
        Hand h2 = new Hand();
        h2.addCard(c2);
        h2.addCard(c2);
        System.out.println("\na hand with 2 cards = " + hand.size());
        System.out.println("a hand with the same 2 cards = " + h2.size());
        hand.removeCard(h2);
        System.out.println("a hand after using removecard(hand) = " + hand.size());

        hand = h2;
        System.out.println("\nhand size = " + hand.size());
        h2.removeCard(0);
        System.out.println("hand size after using removecard(position) where position = 0, = " + hand.size());

        hand = new Hand();
        hand.addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.ACE));
        hand.addCard(new Card(Card.Suit.HEARTS, Card.Rank.ACE));
        hand.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        System.out.println("\n" + hand.toString());
        System.out.println("Rank count = " + hand.countRank(Card.Rank.ACE));
        System.out.println("Hand Value = " + hand.handValue());
        hand = new Hand();
        for (int i=0;i<13;i++) {
            hand.addCard(new Card(Card.Suit.SPADES, Card.Rank.values()[i]));
        }
        System.out.println("\n" + hand.toString());
        if (hand.isFlush() == true) {
            System.out.println("Is a flush");
        }
        else {
            System.out.println("Isn't a flush");
        }
        if (hand.isStraight() == true) {
            System.out.println("Is a straight");
        }
        else {
            System.out.println("Isn't a straight");
        }
        hand.addCard(new Card(Card.Suit.HEARTS, Card.Rank.EIGHT));
        System.out.println("\n" + hand.toString());
        if (hand.isFlush() == true) {
            System.out.println("Is a flush");
        }
        else {
            System.out.println("Isn't a flush");
        }
        if (hand.isStraight() == true) {
            System.out.println("Is a straight");
        }
        else {
            System.out.println("Isn't a straight");
        }
        System.out.println("\nhand to serialize = " + hand.toString());
        hand.serialize("hand");
        Hand hand1 = deserialize("hand");
        System.out.println("hand deserialized = " + hand1.toString());


//        hand1 = new Hand();
//        hand1.addCard(new Card(Card.Suit.SPADES, Card.Rank.EIGHT));
//        hand1.addCard(new Card(Card.Suit.SPADES, Card.Rank.FOUR));
//        hand1.addCard(new Card(Card.Suit.SPADES, Card.Rank.FIVE));
//        hand1.sortAscending();
//        hand1.remove(0);
//        System.out.println("hand1 = " + hand1);
//        Iterator<question2.Card> orderAddedIterator = ((Hand)(hand1.orderAdded)).OrderAddedIterator();
//        while(orderAddedIterator.hasNext()){
//            question2.Card c = orderAddedIterator.next();
//            System.out.println(c);
//        }

    }

    /**
     * allows you to add one card
     * @param card card to be added
     */
    public void addCard(Card card) {
        this.add(card);
        this.changeNumSuit(card.getSuit().ordinal(), +1);
        this.changeNumRank(card.getRank().ordinal(), +1);
    }
    /**
     * A method that allows multiple cards to be added to a hand at once
     *
     * @param cards the cards to be added
     *
     */
    public void add(Collection<Card> cards) {
        for (Card card : cards) {
            this.add(card);
            this.changeNumSuit(card.getSuit().ordinal(), +1);
            this.changeNumRank(card.getRank().ordinal(), +1);
        }
    }
    /**
     * A method that allows a seperate hand to be added to the hand
     *
     * @param hand the hand to be added
     *
     */
    public void add(Hand hand) {
        for (Card card : hand) {
            this.add(card);
            this.changeNumSuit(card.getSuit().ordinal(), +1);
            this.changeNumRank(card.getRank().ordinal(), +1);
        }
    }
    /**
     * A method that allows a single card to be removed
     *
     * @param card the card to be removed
     * @return true if the card is found and removed, false if it is'nt
     */
    public boolean removeCard(Card card) {
        if (this.contains(card)) {
            this.remove(card);
            this.changeNumSuit(card.getSuit().ordinal(), -1);
            this.changeNumRank(card.getRank().ordinal(), -1);
            return true;
        }
        return false;
    }
    /**
     * A method that allows a hand of cards to be removed from the hand
     *
     * @param hand the cards to be removed
     * @return true if all cards are removed, false if some are not or none at all
     */
    public boolean removeCard(Hand hand) {
        int count = 0;
        for (Card card : hand) {
            if (this.contains(card)) {
                this.remove(card);
                this.changeNumSuit(card.getSuit().ordinal(), -1);
                this.changeNumRank(card.getRank().ordinal(), -1);
                count++;
            }
        }
        if (count == hand.size()) {
            return true;
        }
        return false;

    }
    /**
     * A method that allows card to be removed from that index
     *
     * @param position the card to be removeds position
     * @return returns the removed card
     */
    public Card removeCard(int position){
//        if (this.size() > position) {
            Card card = this.get(position);
            this.remove(this.get(position));
            return card;
//        }

    }
    /**
     * A method to change the count of a suit
     *
     * @param index the index of the suit in the array
     * @param num amount to change it by
     *
     */
    public void changeNumSuit(int index, int num) {
        this.numSuit[index] =+ num;
    }
    /**
     * A method to change the count of a rank
     *
     * @param index the index of the rank in the array
     * @param num amount to change it by
     *
     */
    public void changeNumRank(int index, int num) {
        this.numRank[index] =+ num;
    }
    /**
     * A method to store an object
     *
     * @param filename the name to store the object under
     *
     */
    public void serialize(String filename) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filename + ".ser")) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
        }
    }
    /**
     * A method to load an object
     *
     * @param filename filename of the object to retrieve
     * @return returns the hand in the file
     */
    public static Hand deserialize(String filename) throws IOException,
            ClassNotFoundException{

        Hand hand;
        try (FileInputStream fis = new FileInputStream(filename + ".ser")) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            hand = (Hand) ois.readObject();
            ois.close();
        }
        return hand;
    }

    public Iterator<Card> OrderAddedIterator() {return new OrderAddedIterator();}
    private class OrderAddedIterator implements Iterator<Card>{
        int position = -1;

        @Override
        public boolean hasNext() {
            return position < orderAdded.size()-1;
        }

        @Override
        public Card next() {
//            Card next = Deck.this.get(position+1);
            Card next = orderAdded.get(position+1);
            position++;
            return next;
        }
    }

    /**
     * A method to sort the hand into ascending order
     *
     */
    public void sortAscending() {
        Collections.sort(this, new Card.CompareAscending());
    }
    /**
     * A method to sort the hand into descending order
     *
     */
    public void sortDescending() {

        Collections.sort(this, (Card c1, Card c2) -> c2.getRank().compareTo(c1.getRank()));
    }
    /**
     * A method to count the amount of cards of that rank in the hand
     *
     * @param rank the rank to count
     * @return the amount of cards of that rank in the hand
     */
    public int countRank(Card.Rank rank) {
        int total = 0;
        for (Card card : this) {
            if (card.getRank().equals(rank)) {
                total++;
            }
        }
        return total;
//        System.out.println(numRank[rank.ordinal()]);
//        return numRank[rank.ordinal()];
    }
    /**
     * A method to count the hand value
     * @return the hand value
     *
     */
    public int handValue() {
        int subTotal = 0;
        for (Card card : this) {
            subTotal += card.getRank().getValue();
        }
        total = subTotal;
        return subTotal;
    }
    /**
     * A method that takes the hand and converts it to a string for outputting
     * @return the hand in a form of a string
     *
     */
    @Override
    public String toString() {
        String output = "";
        for (Card card : this) {
            output += card.toString() + "   ";
        }
        return output;
    }
    //http://www.mathcs.emory.edu/~cheung/Courses/170/Syllabus/10/pokerCheck.html
    /**
     * A method to check if the hand is a flush
     * @return true if it is a flush
     *
     */
    public boolean isFlush() {
//        int totalCards = 0;
//        for (question1.Card card : this){
//            totalCards++;
//        }
//        if (this.size()<5) {
//            return false;
//        }
        Hand tempHand = this;
        Collections.sort(tempHand, new Card.CompareSuit());
        if (tempHand.get(0).getSuit() == tempHand.get(tempHand.size()-1).getSuit()) {
            return true;
        }
        return false;

    }
    /**
     * A method to check if the hand is a straight
     * @return true if it is a straight
     *
     */
    public boolean isStraight() {
        Hand tempHand = this;
        tempHand.sortAscending();
        int testRank = tempHand.get(0).getRank().ordinal()+1;
        for (int i=1;i<tempHand.size()-1;i++) {
            if (tempHand.get(i).getRank().ordinal() != testRank) {
                return false;
            }
            else {
                testRank++;
            }

        }
        return true;



    }
    public ArrayList<Card> getOrderAdded() {
        return this.orderAdded;
    }


}
