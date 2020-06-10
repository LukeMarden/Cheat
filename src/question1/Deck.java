package question1;

import java.io.*;
import java.util.*;

/**
 * @author Luke Marden
 */
public class Deck extends ArrayList<Card> implements Iterable<Card>, Serializable {
    private int pointer = 0;
    private Card[] deck = new Card[52];
    static final long serialVersionUID = 100250071-10;
    public static void main(String args[]) throws IOException, ClassNotFoundException {
        Deck deck1 = new Deck();
        System.out.println("first item in deck = " + deck1.getDeck()[deck1.getPointer()].toString() + "\n");

        System.out.println("deck size = " + deck1.size() + "\n");

        deck1.shuffle();
        System.out.println("first item in deck after shuffling = " + deck1.getDeck()[deck1.getPointer()].toString() + "\n");

        deck1.newDeck();
        System.out.println("deck1 = newDeck(), first item = " + deck1.getDeck()[deck1.getPointer()].toString());
        ArrayList<Card> dealt = new ArrayList<Card>();
        dealt.add(deck1.deal());
        System.out.println("first item in deck after dealing a card = " + deck1.getDeck()[deck1.getPointer()].toString());
        System.out.println("Card that was dealt = " + dealt.get(0).toString() + "\n");

        System.out.println("\ndeck to be serialized(first item) = " + deck1.getDeck()[deck1.getPointer()].toString());
        deck1.serialize("deck1");
        deck1.shuffle();
        System.out.println("deck shuffled(first item) = " + deck1.getDeck()[deck1.getPointer()].toString());
        deck1 = deserialize("deck1");
        System.out.println("deck to be deserialized(first item) = " + deck1.getDeck()[deck1.getPointer()].toString());


        System.out.println("\n\n");
        deck1 = new Deck();
        dealt.clear();
        System.out.println("new deck in order. second number is index");
        Iterator<Card> orderedIterator = deck1.OrderedIterator();
        while(orderedIterator.hasNext()){
            Card c = orderedIterator.next();
//            dealt.add(c);
            System.out.println(c);
            for (int i = 0; i < deck1.getDeck().length; i++) {
                if (c.equals(deck1.getDeck()[i])) {
                    System.out.println(i);
                }
            }
        }
        System.out.println("\n\n");
        System.out.println("new deck in odd even order. second number is index");
        Iterator<Card> oddEvenIterator = deck1.OddEvenIterator();
        while(oddEvenIterator.hasNext()){
            Card c = oddEvenIterator.next();
//            dealt.add(c);
            System.out.println(c);
            for (int i = 0; i < deck1.getDeck().length; i++) {
                if (c.equals(deck1.getDeck()[i])) {
                    System.out.println(i);
                }
            }
        }


    }

    /**
     * a constructor for the card Deck
     */
    public Deck() {
//        for (Card.Suit suit: Card.Suit.values()) {
//            for (Card.Rank rank: Card.Rank.values()) {
//                this.add(new Card(suit, rank));
//            }
//        }
        int index = 0;
        for (int i =0; i<4; i++) {
            for (int j=0; j<13; j++) {
                deck[index] = new Card(Card.Suit.values()[i], Card.Rank.values()[j]);
                index++;
            }
        }
    }

    /**
     * find size of deck
     * @return the size
     */
    public int size() {
        int count = 0;
        for (int i =0; i<deck.length;i++) {
            if (deck[i] != null) {
                count++;
            }
        }
        return count;
    }

    /**
     * makes a new deck
     */
    public final void newDeck() {
        int index = 0;
        for (int i =0; i<4; i++) {
            for (int j=0; j<13; j++) {
                this.deck[index] = new Card(Card.Suit.values()[i], Card.Rank.values()[j]);
                index++;
            }
        }
        this.pointer = 0;

    }

    /**
     * iterator that returns them in order
     * @return an iterator
     */
    public Iterator<Card> OrderedIterator(){
        return new OrderedIterator();
    }

    /**
     * goes through the cards in order
     */
    private class OrderedIterator implements Iterator<Card> {
        int position = -1;

        @Override
        public boolean hasNext() {
            return position < size()-1;
        }

        @Override
        public Card next() {
//            Card next = Deck.this.get(position+1);
            Card next = deck[position+1];
            position++;
            return next;
        }
    }

    /**
     * iterator that reterns the cards in an odd index then even order
     * @return iterator
     */
    public Iterator<Card> OddEvenIterator(){return new OddEvenIterator(); }

    /**
     * goes through the cards in an odd position first. for example 1,3,5,2,4
     */
    private class OddEvenIterator implements Iterator<Card> {
        int position = -1 + pointer;

        @Override
        public boolean hasNext() {
            return position<size()-2;
        }
        @Override
        public Card next() {

//            Card card = Deck.this.get(position);
//            //maybe position-1
//            if (position % 2 == 1 && (position+1 == size())) {
//                position = 0;
//            }
//            else {
//                position=+2;
//            }
////            position++;
//            return card;
//            Card next = Deck.this.get(position+2);
            Card next = deck[position+2];

            if (position % 2 == 1 && (position == size()-3 || position+1 == size()-3)) {
                position = -2;

            }
            else {
                position += 2;
            }
            return next;
        }

    }

    /**
     * shuffles the deck
     */
    public void shuffle() {
        List<Card> deckToShuffle = Arrays.asList(this.deck);
        Collections.shuffle(deckToShuffle);
        deckToShuffle.toArray(this.deck);
    }

    /**
     * deals the cards in the deck
     * @return card to be dealt
     */
    public Card deal() {
        Card card = this.deck[pointer];
        this.deck[pointer] = null;
        pointer++;
        return card;
    }

    /**
     * stores a deck object in a file
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
    public static Deck deserialize(String filename) throws IOException, ClassNotFoundException{

        Deck deck;
        try (FileInputStream fis = new FileInputStream(filename + ".ser")) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            deck = (Deck) ois.readObject();
            ois.close();
        }
        return deck;
    }

    /**
     * gets the deck
     * @return an array of cards(deck)
     */
    public Card[] getDeck() {
        return deck;
    }

    /**
     * used in shuffle to find top of deck
     * @return pointer that keeps track of top of the deck
     */

    public int getPointer() {
        return pointer;
    }


}
