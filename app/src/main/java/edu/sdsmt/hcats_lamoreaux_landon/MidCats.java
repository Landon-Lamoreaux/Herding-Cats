package edu.sdsmt.hcats_lamoreaux_landon;

public class MidCats implements State {
    private StateMachine stateMachine;
    private Game game;
    private int totalCats;
    public MidCats(StateMachine statemachine, Game game1, int numCats) {
        stateMachine = statemachine;
        game = game1;
        totalCats = numCats;
    }
    @Override
    public void endTask() {

    }

    @Override
    public void startTask() {
        if(game.isLost()) {
            stateMachine.setState(StateMachine.StateEnum.Ended);
        }
    }

    // GRADING: SWEEP
    @Override
    public void sweepRight() {
        game.sweepRight(2, 50);
        if(totalCats - game.getCatsCaught() < 20) {
            stateMachine.setState(StateMachine.StateEnum.Low);
        }
        if(game.isLost())
            stateMachine.setState(StateMachine.StateEnum.Ended);
    }

    // GRADING: SWEEP
    @Override
    public void sweepDown() {
        game.sweepDown(2, 50);
        if(totalCats - game.getCatsCaught() < 20) {
            stateMachine.setState(StateMachine.StateEnum.Low);
        }
        if(game.isLost())
            stateMachine.setState(StateMachine.StateEnum.Ended);
    }
}
