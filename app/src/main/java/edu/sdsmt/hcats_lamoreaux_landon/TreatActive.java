package edu.sdsmt.hcats_lamoreaux_landon;

public class TreatActive implements State {
    private StateMachine stateMachine;
    private Game game;
    private int totalCats;
    public TreatActive(StateMachine statemachine, Game game1, int numCats) {
        stateMachine = statemachine;
        game = game1;
        totalCats = numCats;
    }
    @Override
    public void endTask() {

    }

    @Override
    public void startTask() {
        game.useTreat();
    }

    // GRADING: SWEEP
    @Override
    public void sweepRight() {
        game.sweepRight(5, 75);

        // GRADING: TO_NO_TREAT
        if(totalCats - game.getCatsCaught() > 20)
            stateMachine.setState(StateMachine.StateEnum.Middle);
        else
            stateMachine.setState(StateMachine.StateEnum.Low);
    }

    // GRADING: SWEEP
    @Override
    public void sweepDown() {
        game.sweepDown(5, 75);

        // GRADING: TO_NO_TREAT
        if(totalCats - game.getCatsCaught() > 20)
            stateMachine.setState(StateMachine.StateEnum.Middle);
        else
            stateMachine.setState(StateMachine.StateEnum.Low);
    }
}
