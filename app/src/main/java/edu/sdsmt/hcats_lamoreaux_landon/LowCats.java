package edu.sdsmt.hcats_lamoreaux_landon;

public class LowCats implements State {
    private StateMachine stateMachine;
    private Game game;
    private int totalCats;
    public LowCats(StateMachine statemachine, Game game1, int numCats) {
        stateMachine = statemachine;
        game = game1;
        totalCats = numCats;
    }
    @Override
    public void endTask() {

    }

    @Override
    public void startTask() {
        if(game.isWon() || game.isLost()) {
            stateMachine.setState(StateMachine.StateEnum.Ended);
        }
    }

    // GRADING: SWEEP
    @Override
    public void sweepRight() {
        game.sweepRight(1, 25);
        if(game.isWon() || game.isLost()) {
            stateMachine.setState(StateMachine.StateEnum.Ended);
        }
    }

    // GRADING: SWEEP
    @Override
    public void sweepDown() {
        game.sweepDown(1, 25);
        if(game.isWon() || game.isLost()) {
            stateMachine.setState(StateMachine.StateEnum.Ended);
        }
    }
}
