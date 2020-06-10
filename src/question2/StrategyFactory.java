package question2;

public class StrategyFactory {
    Strategy strategy;
    enum Strategies{BASIC, HUMAN, THINKER, MY;}

    /**
     * Selects which strategy to use
     * @param strategies the strategy to use
     * @return the strategy to assign to the playerΩ
     */
    public Strategy initiateStrategy(Strategies strategies) {
        switch (strategies) {
            case MY:
                strategy = new MyStrategy();
                break;
            case THINKER:
                strategy = new ThinkerStrategy();
                break;
            case HUMAN:
                strategy = new HumanStrategy();
                break;
            case BASIC:
                strategy = new BasicStrategy();
                break;
            default:
                strategy = new BasicStrategy();
                break;
        }
        return strategy;
    }
}
