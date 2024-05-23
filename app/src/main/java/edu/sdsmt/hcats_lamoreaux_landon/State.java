package edu.sdsmt.hcats_lamoreaux_landon;

public interface State {
    GameView gameView = null;
    public void endTask();
    public void startTask();
    void sweepRight();
    void sweepDown();
}
