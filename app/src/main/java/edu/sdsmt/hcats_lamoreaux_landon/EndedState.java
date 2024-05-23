package edu.sdsmt.hcats_lamoreaux_landon;

import androidx.appcompat.app.AlertDialog;

public class EndedState implements State {
    private StateMachine stateMachine;
    private Game game;
    private int totalCats;
    public EndedState(StateMachine statemachine, Game game1, int numCats) {
        stateMachine = statemachine;
        game = game1;
        totalCats = numCats;
    }

    // GRADING: RESET
    @Override
    public void endTask() {
        game.reset();
    }

    @Override
    public void startTask() {
        if(game.isWon()) {  // Telling the player they won.
            showDialogWithText("Victory! You have beaten the game!");
        }
        else if(game.isLost()) {  // Telling the player they lost.
            showDialogWithText("Out of moves! You have lost the game!");
        }
    }

    @Override
    public void sweepRight() {

    }

    @Override
    public void sweepDown() {

    }

    // Showing a dialog with the winning or losing message.
    private void showDialogWithText(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(stateMachine.getMainActivity());
        builder.setMessage(text);

        // Create and show the dialog.
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
