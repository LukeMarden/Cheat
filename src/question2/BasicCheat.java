package question2;

import java.util.*;

public class BasicCheat implements CardGame{
    private Player[] players;
    private int nosPlayers;
    public static final int MINPLAYERS=5;
    private int currentPlayer;
    private Hand discards;
    private Bid currentBid;
    

    public BasicCheat(){
        this(MINPLAYERS);
    }
    public BasicCheat(int n){
/** new */
        int[] strat = {0, 1, 2, 3};
        StrategyFactory strategyFactory = new StrategyFactory();
        nosPlayers=n;
        players=new Player[nosPlayers];
        for(int i=0;i<nosPlayers;i++) {
            players[i]=(new BasicPlayer(strategyFactory.initiateStrategy(StrategyFactory.Strategies.values()[strat[i%4]]),this));
            players[i].setStrategy(strategyFactory.initiateStrategy(StrategyFactory.Strategies.values()[strat[i%4]]));
        }

        currentBid=new Bid();
        currentBid.setRank(Card.Rank.TWO);
        currentPlayer=0;
    }

    @Override
    public boolean playTurn(){
        //lastBid=currentBid;
        //Ask player for a play,
        System.out.println("current bid = "+currentBid);
/** new */
        System.out.println(players[currentPlayer].getStrategy());

        currentBid=players[currentPlayer].playHand(currentBid);
        //
        System.out.println("Player bid = "+currentBid);
         //Add hand played to discard pile
        discards.add(currentBid.getHand());
        //Offer all other players the chance to call cheat
        boolean cheat=false;
        for(int i=0;i<players.length && !cheat;i++){
            if(i!=currentPlayer){
                cheat=players[i].callCheat(currentBid);
                if(cheat){
                    System.out.println("Player called cheat by Player "+(i+1));
                    if(isCheat(currentBid)){	
//CHEAT CALLED CORRECTLY
//Give the discard pile of cards to currentPlayer who then has to play again                      
                        players[currentPlayer].addHand(discards);
                        System.out.println("Player cheats!");
                        System.out.println("Adding cards to player "+
                                (currentPlayer+1)+players[currentPlayer]);

                    }
                    else{
//CHEAT CALLED INCORRECTLY
//Give cards to caller i who is new currentPlayer
                        System.out.println("Player Honest");
                        currentPlayer=i;
                        players[currentPlayer].addHand(discards);
                        System.out.println("Adding cards to player "+
                                (currentPlayer+1)+players[currentPlayer]);
                    }
//If cheat is called, current bid reset to an empty bid with rank two whatever 
//the outcome
                    currentBid=new Bid();
//Discards now reset to empty
                    discards=new Hand();
/** new */
                    for (Player player : players) {
                        if (player.getStrategy() instanceof ThinkerStrategy){
                            ((ThinkerStrategy) player.getStrategy()).clearInDiscard();
                        }
                        else if (player.getStrategy() instanceof MyStrategy) {
                            ((MyStrategy) player.getStrategy()).clearInDiscard();
                        }
                    }

                }
            }


        }
        if(!cheat){
//Go to the next player       
            System.out.println("No Cheat Called");

            currentPlayer=(currentPlayer+1)%nosPlayers;
        }
        return true;
    }
    public int winner(){
            for(int i=0;i<nosPlayers;i++){
                    if(players[i].cardsLeft()==0)
                            return i;
            }
            return -1;

    }
    public void initialise(){
            //Create question1.Deck of cards
            Deck d=new Deck();
            d.shuffle();
            //Deal cards to players
/** now uses deal instead*/
            int count=0;
            while(d.size() > 0){
                players[count%nosPlayers].addCard(d.deal());
//                    it.remove();
                count++;
            }
            //Initialise Discards
            discards=new Hand();
            //Chose first player
            currentPlayer=0;
            currentBid=new Bid();
            currentBid.setRank(Card.Rank.TWO);
    }
    public void playGame(){
            initialise();
            int c=0;
            Scanner in = new Scanner(System.in);
            boolean finished=false;
            while(!finished){
                    //Play a hand
                    System.out.println(" Cheat turn for player "+(currentPlayer+1));
                    playTurn();
                    System.out.println(" Current discards =\n"+discards);
                    c++;
                    System.out.println(" Turn "+c+ " Complete. Press any key to continue or enter Q to quit>");
                    String str=in.nextLine();
                    if(str.equals("Q")||str.equals("q")||str.equals("quit"))
                            finished=true;
                    int w=winner();
                    if(w>0){
                            System.out.println("The Winner is Player "+(w+1));
                            finished=true;
                    }

            }
    }
    public static boolean isCheat(Bid b){
            for(Card c:b.getHand()){
                    if(c.getRank()!=b.r)
                            return true;
            }
            return false;
    }
    public static void main(String[] args){
            BasicCheat cheat=new BasicCheat();
            cheat.playGame();
    }
}
