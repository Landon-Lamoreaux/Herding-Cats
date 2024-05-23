package edu.sdsmt.hcats_lamoreaux_landon;

public class HighCats implements State {
    private StateMachine stateMachine;
    private Game game;
    private int totalCats;
    public HighCats(StateMachine statemachine, Game game1, int numCats) {
        stateMachine = statemachine;
        game = game1;
        totalCats = numCats;
    }
    @Override
    public void endTask() {

    }

    @Override
    public void startTask() {

    }

    // GRADING: SWEEP
    @Override
    public void sweepRight() {
        game.sweepRight(3, 50);
        if(totalCats - game.getCatsCaught() < 30) {
            stateMachine.setState(StateMachine.StateEnum.Middle);
        }
        if(game.isWon() || game.isLost()) {
            stateMachine.setState(StateMachine.StateEnum.Ended);
        }
    }

    // GRADING: SWEEP
    @Override
    public void sweepDown() {
        game.sweepDown(3, 50);
        if(totalCats - game.getCatsCaught() < 30) {
            stateMachine.setState(StateMachine.StateEnum.Middle);
        }
        if(game.isWon() || game.isLost()) {
            stateMachine.setState(StateMachine.StateEnum.Ended);
        }
    }
}
