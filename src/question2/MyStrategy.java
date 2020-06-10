package question2;

import java.util.Random;

public class MyStrategy implements Strategy {
    private Hand inDiscard = new Hand();
    /**
     *
     * method to test the other methods
     */
    public static void main(String[] args) {
        MyStrategy test = new MyStrategy();
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
    }
    /**
     * never cheat if the hand has more than 1 of that rank. if it has 1 this has a small
     * probability of cheating. cheat, if they have to.
     * @param b   the bid this player has to follow (i.e the
     * bid prior to this players turn.
     * @param h	  The players current hand
     * @return whether the player should cheat or not
     */
    public boolean cheat(Bid b, Hand h){
        /*
        If the player has more than one of that rank they won't cheat. However if they don't there is
        a one in three chance they will cheat. If they can't go they have to cheat
         */
        Random r1 = new Random();
        int ranNum = r1.nextInt(3);
        if (h.countRank(b.getRank()) > 1 || h.countRank(b.getRank().getNext()) > 1 ) {
            return false;
        }
        else if(h.countRank(b.getRank()) > 0 || h.countRank(b.getRank().getNext()) > 0 ) {
            if (ranNum == 1) {
                return true;
            }
            return false;
        }
        return true;
    }
    /**
     * chooses bid based on number of cards left in the hand. if not cheating plays max cards possible
     * if cheating play amount of cards based on cards left in hand
     * @param b   the bid the player has to follow.
     * @param h	  The players current hand
     * @param cheat true if the Strategy has decided to cheat (by call to cheat())
     *
     * @return the next bid
     */
    public Bid chooseBid(Bid b, Hand h, boolean cheat){
        /*
        If they are not cheating they will play the max cards that they can. If cheating there is
        a 50:50 chance between playing the lowest card possible or highest cards possible. If cheating they will get
        get rid of an amount of cards based on how many cards are in the hand. The more cards that are in the hand
        the more cards it is likely to get rid of.
         */
        Card.Rank next = b.getRank().getNext();
        Card.Rank newBidRank;
        Hand toPlay = new Hand();
        Random r1 = new Random();
        if (!cheat) {
            if (h.countRank(b.getRank())>=h.countRank(next)) {
                for (Card card : h) {
                    if (card.getRank() == b.getRank()) {
                        toPlay.add(card);
                    }
                }
                newBidRank = b.getRank();
            }
            else {
                for (Card card : h) {
                    if (card.getRank() == next) {
                        toPlay.add(card);
                    }
                }
                newBidRank = next;
            }
        }
        else { //cheat
            int ranNum1 = r1.nextInt(4);
            int ranNum2 = r1.nextInt(2);
            int amountToAdd = 0;
            Hand temp = h;
            temp.sortDescending();
            if (ranNum2 == 1) {
                temp.sortAscending();
            }
            if (h.size() <= 4) {
                if (ranNum1 == 1) {
                    amountToAdd = 2;
                }
                else {
                    amountToAdd = 1;
                }
            }
            else if (5 <= h.size() && h.size() <= 7) {
                if (ranNum1 == 1) {
                    amountToAdd = 3;
                }
                else {
                    amountToAdd = 2;
                }
            }
            else {
                if (ranNum1 == 1) {
                    amountToAdd = 4;
                }
                else {
                    amountToAdd = 3;
                }
            }
            for (int i = 0; i<amountToAdd-1; i++) {
                toPlay.add(temp.get(i));
            }


            if (ranNum2 == 1) {
                newBidRank = b.getRank();
            }
            else {
                newBidRank = next;
            }

        }
        inDiscard.add(toPlay);
        h.removeCard(toPlay);
        return b = new Bid(toPlay, newBidRank);
    }
    /**
     * calls cheat based on the number of cards left in the hand
     * @param h the players hand
     * @param b the current bid
     * @return whether they think the latest player cheated (True/False)
     */
    public boolean callCheat(Hand h,Bid b){
        /*
        Keeps track of played cards. it will then call cheat based on this. Also the amount of cards left in the hand
        affects how likely they are to call cheat. Less cards equals less chance that they will call cheat.
         */
        Random r1 = new Random();
        int rankCount = h.countRank(b.getRank()) + this.inDiscard.countRank(b.getRank()) + b.getCount();
        if (rankCount > 4) {
            return true;
        }
        else if (h.size() <= 3){
            return false;
        }
        else if (4 <= h.size() && h.size() >= 8) {
            int ramNum = r1.nextInt(4);
            if (ramNum == 1 && b.getCount() == 4) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            int ranNum = r1.nextInt(2);
            if (ranNum == 1 && b.getCount() >= 3) {
                return true;
            }
            return false;
        }

    }
    /**
     * clears the indiscard variable
     */
    public void clearInDiscard() {
        inDiscard.clear();
    }
}
