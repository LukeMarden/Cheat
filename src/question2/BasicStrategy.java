package question2;
import java.util.Random;
public class BasicStrategy implements Strategy{
    /**
     *
     * a method to test the other methods
     */
    public static void main(String[] args) {
        BasicStrategy test = new BasicStrategy();
        Hand[] hands = {new Hand(), new Hand(), new Hand(), new Hand()};

        hands[0].add(new Card(Card.Suit.SPADES, Card.Rank.SEVEN));
        hands[0].add(new Card(Card.Suit.CLUBS, Card.Rank.SEVEN));

        hands[1].add(new Card(Card.Suit.SPADES, Card.Rank.EIGHT));
        hands[1].add(new Card(Card.Suit.CLUBS, Card.Rank.EIGHT));

        hands[2].add(new Card(Card.Suit.SPADES, Card.Rank.EIGHT));
        hands[2].add(new Card(Card.Suit.SPADES, Card.Rank.NINE));
        hands[2].add(new Card(Card.Suit.CLUBS, Card.Rank.NINE));

        hands[3].add(new Card(Card.Suit.SPADES, Card.Rank.NINE));
        hands[3].add(new Card(Card.Suit.SPADES, Card.Rank.NINE));

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
     * Cheats only when they need to
     * @param b   the bid this player has to follow (i.e the
     * bid prior to this players turn.
     * @param h	  The players current hand
     * @return whether they should cheat or not
     */
    @Override
    public boolean cheat(Bid b, Hand h){
        if(h.countRank(b.getRank()) > 0 || h.countRank(b.getRank().getNext()) > 0 ) {
            return false;
        }
        return true;
    }
    /**
     * gets rid of as many cards as possible when not cheating. when cheating get rid
     * of one card randomly selected
     * @param b   the bid the player has to follow.
     * @param h	  The players current hand
     * @param cheat true if the Strategy has decided to cheat (by call to cheat())
     *
     * @return the next bid
     */
    @Override
    public Bid chooseBid(Bid b, Hand h, boolean cheat){
        Card.Rank next = b.getRank().getNext();
        Card.Rank newBidRank;
        Hand toPlay = new Hand();
        if (!cheat) {
            if (h.countRank(b.getRank())>0) {
                for (Card card : h) {
                    if (card.getRank().equals(b.getRank())) {
                        toPlay.add(card);
                    }
                }
                newBidRank = b.getRank();
            }
            else {
                for (Card card : h) {
                    if (card.getRank().equals(next)) {
                        toPlay.add(card);
                    }
                }
                newBidRank = next;
            }

        }
        else {
            Random r1 = new Random();
            int ranNum = r1.nextInt(h.size());
            toPlay.add(h.get(ranNum));
            newBidRank = next;

        }
        h.removeCard(toPlay);
        return new Bid(toPlay, newBidRank);

    }
    /**
     * only calls cheat if they are sure of it based on their own hand and
     * the current bid.
     * @param h the players current hand
     * @param b the current bid
     * @return whether they think the latest player cheated (True/False)
     */
    @Override
    public boolean callCheat(Hand h, Bid b){
        if (h.countRank(b.getRank()) + b.getCount() > 4) {
            return true;
        }
        return false;
    }

}
