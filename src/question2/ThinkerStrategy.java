package question2;

import java.util.ArrayList;
import java.util.Random;
public class ThinkerStrategy implements Strategy {
    private Hand inDiscard = new Hand();
    /**
     *
     * method to test the other methods
     */
    public static void main(String[] args) {
        ThinkerStrategy test = new ThinkerStrategy();
        Hand[] hands = {new Hand(), new Hand(), new Hand(), new Hand(), new Hand(), new Hand(), new Hand()};


        hands[0].add(new Card(Card.Suit.SPADES, Card.Rank.SEVEN));
        hands[0].add(new Card(Card.Suit.CLUBS, Card.Rank.SEVEN));

        hands[1].add(new Card(Card.Suit.SPADES, Card.Rank.EIGHT));
        hands[1].add(new Card(Card.Suit.CLUBS, Card.Rank.EIGHT));

        hands[2].add(new Card(Card.Suit.SPADES, Card.Rank.EIGHT));
        hands[2].add(new Card(Card.Suit.SPADES, Card.Rank.NINE));
        hands[2].add(new Card(Card.Suit.CLUBS, Card.Rank.NINE));

        hands[3].add(new Card(Card.Suit.SPADES, Card.Rank.NINE));
        hands[3].add(new Card(Card.Suit.SPADES, Card.Rank.NINE));

        hands[4].add(new Card(Card.Suit.SPADES, Card.Rank.EIGHT));
        hands[4].add(new Card(Card.Suit.SPADES, Card.Rank.NINE));
        hands[4].add(new Card(Card.Suit.CLUBS, Card.Rank.NINE));

        hands[5].add(new Card(Card.Suit.SPADES, Card.Rank.EIGHT));
        hands[5].add(new Card(Card.Suit.SPADES, Card.Rank.NINE));
        hands[5].add(new Card(Card.Suit.CLUBS, Card.Rank.NINE));

        hands[6].add(new Card(Card.Suit.SPADES, Card.Rank.EIGHT));
        hands[6].add(new Card(Card.Suit.SPADES, Card.Rank.NINE));
        hands[6].add(new Card(Card.Suit.CLUBS, Card.Rank.NINE));

        Hand bidHand = new Hand();
        bidHand.add(new Card(Card.Suit.SPADES, Card.Rank.EIGHT));
        bidHand.add(new Card(Card.Suit.HEARTS, Card.Rank.EIGHT));
        Bid bid = new Bid(bidHand, Card.Rank.EIGHT);
        for (int i=0;i<hands.length;i++) {
            System.out.println("Hand " + (i+1) + " : " + hands[i].toString());
            System.out.println("Bid:     " + bid.toString());
            if (test.cheat(bid, hands[i])) {
                System.out.println("Cheat");
            }
            else {
                System.out.println("Don't Cheat");
            }
            Bid newBid = test.chooseBid(bid, hands[i], test.cheat(bid, hands[i]));
            System.out.println("New bid:  " + newBid);
            System.out.println("Hand " + (i+1) + " : " + hands[i].toString() + "\n");

        }

        bidHand.clear();
        for (int i =0;i<3;i++) {
            bidHand.add(new Card(Card.Suit.values()[i], Card.Rank.values()[i]));
        }
        bid = new Bid(bidHand, Card.Rank.EIGHT);
        Hand hand = new Hand();
        hand.add(new Card(Card.Suit.CLUBS, Card.Rank.EIGHT));
        hand.add(new Card(Card.Suit.SPADES, Card.Rank.EIGHT));
        System.out.println("\nHand: " + hand.toString());
        System.out.println("Bid:  " + bid.toString());
        if (test.callCheat(hand, bid)) {
            System.out.println("Call Cheat");
        }
        else{
            System.out.println("Don't Call Cheat");
        }
    }
    /**
     * decides whether to cheat. if the player doesn't have any valid cards it returns true.
     * if the player does have valid cards it will return false, and occassionally true.
     *
     * @param b   the bid this player has to follow (i.e the
     * bid prior to this players turn.
     * @param h	  The players current hand
     * @return whether the player should cheat or not
     *
     */
    public boolean cheat(Bid b, Hand h){
        Random r1 = new Random();
        int ranNum = r1.nextInt(7);
        if(h.countRank(b.getRank()) > 0 || h.countRank(b.getRank().getNext()) > 0 ) {
            if (ranNum == 0) {
                return true;
            }
            return false;
        }
        return true;
    }
    /**
     *
     * If cheating, the Thinker should be more likely to choose higher cards to discard than low cards.
     * If not cheating, it will  play all its cards but occasionally play a random number.
     * @param b   the bid the player has to follow.
     * @param h	  The players current hand
     * @param cheat true if the Strategy has decided to cheat (by call to cheat())
     *
     * @return the next bid
     */
    public Bid chooseBid(Bid b, Hand h, boolean cheat){
        Card.Rank next = b.getRank().getNext();
        Card.Rank newBidRank;
        Hand toPlay = new Hand();
        Random r1 = new Random();
//        int ranNum2 = r1.nextInt(4); //needs to check there are that many cards left
        if (!cheat) {
            int ranNum1 = r1.nextInt(7);
            if (h.countRank(b.getRank())>0) {

                if (ranNum1 == 1) {
                    int count = 0;
                    int ranNum2 = r1.nextInt(h.countRank(b.getRank()));
                    for (Card card : h) {
                        if (card.getRank() == b.getRank() && count<ranNum2) {
                            toPlay.add(card);
                            count++;
                        }
                    }
                }
                else {
                    for (Card card : h) {
                        if (card.getRank().equals(b.getRank())) {
                            toPlay.add(card);
//                        h.removeCard(card);
                        }
                    }
                }
                newBidRank = b.getRank();
            }
            else {
                if (ranNum1 == 1) {
                    int count = 0;
                    int ranNum2 = r1.nextInt(h.countRank(next));
                    for (Card card : h) {
                        if (card.getRank() == next && count<ranNum2) {
                            toPlay.add(card);
                            count++;
                        }
                    }
                }
                else {
                    for (Card card : h) {
                        if (card.getRank().equals(next)) {
                            toPlay.add(card);
//                        h.removeCard(card);
                        }
                    }
                }
                newBidRank = next;
            }
        }
        else { //cheat
            int ranNum1 = r1.nextInt(3);

            Hand temp = h;
            if (ranNum1 == 1) { //play low card
                //iterator
                temp.sortAscending();
            }
            else { //play high card
                temp.sortDescending();
            }
            int ranNum2 = r1.nextInt(temp.countRank(temp.get(0).getRank()));
            for (int i=0;i<=ranNum2;i++) {
                toPlay.add(temp.get(i));
            }
            newBidRank = next;

        }
        inDiscard.add(toPlay);
        h.removeCard(toPlay);
        return b = new Bid(toPlay, newBidRank);
    }
    /**
     * attempts to make an informed decision to call cheat on another player. it does
     * this using a seperate hand of discarded cards
     * @param h the players hand
     * @param b the current bid
     * @return whether they think the latest player cheated (True/False)
     */
    public boolean callCheat(Hand h,Bid b){
        Random r1 = new Random();
        int rankCount = h.countRank(b.getRank()) + this.inDiscard.countRank(b.getRank()) + b.getCount();
        if (rankCount <= 4) {
            switch(rankCount){
                case 1:
                    if (r1.nextInt(100) == 1) {
                        return true;
                    }
                    break;
                case 2:
                    if (r1.nextInt(50) == 1) {
                        return true;
                    }
                    break;
                case 3:
                    if (r1.nextInt(10) == 1) {
                        return true;
                    }
                    break;
                case 4:
                    if (r1.nextInt(5) == 1) {
                        return true;
                    }
                    break;
            }
            return false;
        }
        return true;
    }
    /**
     * clears the indiscard variable
     */
    public void clearInDiscard() {
        inDiscard.clear();
    }
}
