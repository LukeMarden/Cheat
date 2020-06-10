package question2;
import java.util.Scanner;

public class HumanStrategy implements Strategy{
    private Scanner scan = new Scanner(System.in);

    /**
     *
     * method to test the other methods
     */
    public static void main(String[] args) {
        HumanStrategy test = new HumanStrategy();
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
//        if (test.cheat(bid, hands[2])) {
//            System.out.println("Decided to cheat");
//        }
//        else {
//            System.out.println("Decided not to cheat");
//        }
        System.out.println("\nHand: " + hands[2].toString());
        System.out.println("Bid:  " + bid.toString());
        bid = test.chooseBid(bid, hands[2], test.cheat(bid, hands[2]));
        System.out.println("\nHand: " + hands[2].toString());
        System.out.println("Bid:  " + bid.toString());

        if (test.callCheat(hands[2], bid)) {
            System.out.println("Cheat called");
        }
        else {
            System.out.println("Cheat not called");
        }








    }
    /**
     * cheats based on the console input
     * @param b   the bid this player has to follow (i.e the
     * bid prior to this players turn.
     * @param h	  The players current hand
     * @return whether the player should cheat or not
     */
    public boolean cheat(Bid b, Hand h){
        Boolean cheat = false;
        int done = 0;
        System.out.println("Your hand is:" + h.toString());
        if (new BasicStrategy().cheat(b,h)) {
            System.out.println("You have no choice but to cheat");
            cheat = true;
        }
        else {
            System.out.println("Last bid was: " + b.toString());
            while (done == 0) {
                System.out.println("Would you like to cheat? (Y/N)");
                String answer = scan.nextLine().toLowerCase();
                if (answer.equals("y")) {
                    done = 1;
                    cheat = true;

                }
                else if (answer.equals("n")) {
                    done = 1;
                    cheat = false;
                }


            }

        }
        return cheat;
    }
    /**
     * chooses the bid based on the input from the console
     * @param b   the bid the player has to follow.
     * @param h	  The players current hand
     * @param cheat true if the Strategy has decided to cheat (by call to cheat())
     *
     * @return the next bid
     */
    public Bid chooseBid(Bid b, Hand h, boolean cheat){
        Hand toPlay = new Hand();
        Card.Rank newBidRank = b.getRank();
        if (!cheat) {
            int loop1 = 0;
            while (loop1 == 0) {
                System.out.println("You can play:");
                System.out.println("1. " + h.countRank(b.getRank()) + " x " + b.getRank());
                System.out.println("2. " + h.countRank(b.getRank().getNext()) + " x " + b.getRank().getNext());
                System.out.println("Please Enter A Number: (1/2)");
                int loop2 = 0;
                String select = scan.nextLine();
                if (select.equals("1") && (h.countRank(b.getRank())>0)) {
                    while (loop2 == 0) {
                        System.out.println("How Many Cards Would You Like To Get Rid Of?");
                        int num = scan.nextInt();
                        if (num <= h.countRank(b.getRank())) {
                            int count = 0;
                            for (Card card : h) {
                                if (card.getRank().equals(b.getRank()) && count<=num) {
                                    toPlay.add(card);
                                    count++;
                                }
                            }
                            newBidRank = b.getRank();
                            loop1 = 1;
                            loop2 = 1;
                        }
                        else{
                            System.out.println("Please enter a valid quantity");
                        }
                    }
                }
                else if (select.equals("2") && (h.countRank(b.getRank().getNext())>0)) {
                    while (loop2 == 0) {
                        System.out.println("How Many Cards Would You Like To Get Rid Of?");
                        int num = scan.nextInt();
                        if (num <= h.countRank(b.getRank().getNext())) {
                            int count = 0;
                            for (Card card : h) {
                                if (card.getRank().equals(b.getRank().getNext()) && count<num) {
                                    toPlay.add(card);
                                    count++;
                                }
                            }
                            newBidRank = b.getRank().getNext();
                            loop1 = 1;
                            loop2 = 1;
                        }
                        else{
                            System.out.println("Please enter a valid quantity");
                        }
                    }
                }
                else {
                    System.out.println("Please enter a valid amount");
                }
            }




        }
        else {
            int loop = 0;
            while (loop==0) {
                System.out.println("Your hand: " + h.toString());
                System.out.println("How many cards would you like to get rid of?");
                int num = scan.nextInt();
                if (num < 5 && num <= h.size()) {
                    System.out.println("What is the index of the card you would like to get rid of?");
                    int index = 0;
                    for (int i = 0; i<num; i++) {
                        int loop2 = 0;
                        while (loop2 == 0) {
                            System.out.println(h.toString());
                            index = scan.nextInt();
                            if (index < h.size()) {
                                toPlay.add(h.get(index));
                                loop2 = 1;
                            }
                            else{
                                System.out.println("Please enter valid number.");
                            }
                        }



                    }
                    String newRankOption;
                    System.out.println("Would you like to declare the rank as the same or incremented by 1? (0/1)");
                    int loop2 = 0;
                    while (loop2 == 0) {
                        int option = scan.nextInt();
                        if (option == 0) {
                            newBidRank = b.getRank();
                            loop = 1;
                            loop2 = 1;
                        }
                        else if (option == 1) {
                            newBidRank = b.getRank().getNext();
                            loop = 1;
                            loop2 = 1;
                        }
                        else {
                            System.out.println("Please enter a valid number");
                        }

                    }

                }
                else {
                    System.out.println("Please enter a valid quantity");
                }
            }

        }
        Hand bidHand = toPlay;
        h.removeCard(toPlay);
        return new Bid(bidHand, newBidRank);

    }
    /**
     * calls cheat based on the console input
     * @param h the players hand
     * @param b the current bid
     * @return whether they think the latest player cheated (True/False)
     */
    public boolean callCheat(Hand h,Bid b){
        System.out.println("Would you like to call cheat? (Y/N)");
        int loop = 0;
        boolean cheat = false;
        while (loop == 0) {
            String option = scan.nextLine();
            if (option.toLowerCase().equals("y")) {
                loop = 1;
                cheat = true;
            }
            else if (option.toLowerCase().equals("n")) {
                loop = 1;
                cheat = false;
            }
            else {
                System.out.println("Please enter a valid option");
            }
        }
        return cheat;


    }
}
