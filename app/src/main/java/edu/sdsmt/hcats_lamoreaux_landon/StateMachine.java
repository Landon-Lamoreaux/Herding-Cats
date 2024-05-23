package edu.sdsmt.hcats_lamoreaux_landon;

public class StateMachine {

    private MainActivity mainActivity;
    private Game game;

    public enum StateEnum{Entry, High, Middle, Low, Ended};
    private StateEnum state = StateEnum.Ended;
    private State[] stateArr;

    // Initializing the state machine.
    StateMachine(MainActivity main, Game game1) {
        mainActivity = main;
        game = game1;
        stateArr = new State[]{
                new TreatActive(this, mainActivity.getGame(), game.getTotalCats()),
                new HighCats(this, mainActivity.getGame(), game.getTotalCats()),
                new MidCats(this, mainActivity.getGame(), game.getTotalCats()),
                new LowCats(this, mainActivity.getGame(), game.getTotalCats()),
                new EndedState(this, mainActivity.getGame(), game.getTotalCats())
        };
        state = StateEnum.High;
    }

    public void setState(StateEnum newState) {

        // Do the exit activity.
        stateArr[state.ordinal()].endTask();

        // Updating to the new state.
        state = newState;

        // Doing the entry activity.
        stateArr[state.ordinal()].startTask();
    }

    public String getCurrentStateName() {
        return stateArr[state.ordinal()].getClass().getName();
    }

    public void sweepRight() {
        stateArr[state.ordinal()].sweepRight();
    }

    public void sweepDown() {
        stateArr[state.ordinal()].sweepDown();
    }

    // GRADING: TO_TREAT
    public void doTreat() {
        if (state == StateEnum.Middle || state == StateEnum.Low)
            if(game.getTreats() > 0 && game.getMoves() >= 0)
                setState(StateEnum.Entry);
    }

    public MainActivity getMainActivity() { return mainActivity; }
}
