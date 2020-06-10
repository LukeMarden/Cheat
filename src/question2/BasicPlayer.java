package question2;

/**
 * @author Luke Marden
 */
public class BasicPlayer implements Player{
    private Strategy strategy;
    private CardGame cardGame;
    private Hand hand;

    /**
     * method to test the other methods
     */
    public static void main(String[] args) {

    }
    /**
     * constructor for the BasicPlayer class
     * @param strat sets the strategy for the player
     * @param game sets the game for the player
     */
    public BasicPlayer(Strategy strat, CardGame game){
        this.setStrategy(strat);
        this.setGame(game);
        this.hand = new Hand();
    }
    /**
     * add card to the players hand
     * @param c: Card to add
     */
    @Override
    public void addCard(Card c) {
        hand.addCard(c);
    }
    /**
     * adds a hand to the player
     * @param h: hand to add
     */
    @Override
    public void addHand(Hand h){
        hand.add(h);
    }
    /**
     *
     * @return the amount of cards left in the hand
     */
    @Override
    public int cardsLeft() {
        return hand.size();
    }
    /**
     * sets the game of the player
     * @param g: the player should contain a reference to the game it is playing in
     */
    @Override
    public void setGame(CardGame g) {
        this.cardGame = g;
    }
    /**
     * sets the strategy of the player
     * @param s: the player should contain a reference to its strategy
     */
    @Override
    public void setStrategy(Strategy s) {
        this.strategy = s;
    }
    /**
     * retrieve the strategy of the player
     * @return the strategy of the player
     */
    @Override
    public Strategy getStrategy() {
        return strategy;
    }
    /**
     * works out what cards to play
     * @param b: the last bid accepted by the game. .
     * @return the bid to play
     */
    @Override
    public Bid playHand(Bid b) {
        return this.strategy.chooseBid(b, this.hand, this.strategy.cheat(b, hand));
    }
    /**
     * calls cheat
     * @param b: the last players bid
     *
     * @return T/F, whether to call cheat or not
     */
    @Override
    public boolean callCheat(Bid b) {
        return strategy.callCheat(hand, b);
    }

}
