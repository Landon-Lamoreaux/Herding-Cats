package edu.sdsmt.hcats_lamoreaux_landon;

import static java.lang.Math.sqrt;

public class Game {
    private int treats = 3;
    private int moves = 15;
    private final int GRID_SIZE = 9;
    private int gridDim;
    public String[] numCats = new String[GRID_SIZE];
    private int[] intCats = new int[GRID_SIZE];
    private int catsCaught = 0;
    private int totalCats = 40;
    private int startCats = 5;

    Game() {
        int i;

        for(i = 0; i < GRID_SIZE - 1; i++) {
            intCats[i] = startCats;
            numCats[i] = intCats[i] + " Cats";
        }
        intCats[GRID_SIZE - 1] = 0;
        numCats[GRID_SIZE - 1] = "Collection\nArea";
        gridDim = (int)sqrt(GRID_SIZE);
    }

    public int getTreats() { return treats; }

    public int getMoves() { return moves; }
    public int getCatsCaught() { return catsCaught; }
    public int getWidth() { return gridDim; }
    public int getHeight() { return gridDim; }
    public int getGridSize() { return GRID_SIZE; }
    public String[] getNumCats() { return numCats; }
    public int getTotalCats() { return totalCats; }
    public int getStartCats() { return startCats; }

    public void sweepRight(int cats, int percent) {
        int i, j;
        int sqrt = gridDim;
        int num, tempCats;

        if (moves < 0) { return; } // Return if you don't have any more moves available.

        for(i = sqrt - 2; i >= 0; i--)
        {
            for(j = 0; j < sqrt; j++)
            {
                tempCats = Math.min(intCats[j * sqrt + i], cats); // Making sure we don't try to move more cats then we have.

                // Finding the number of cats to move, and making sure we don't go below 0.
                num = Math.min(Math.max(tempCats, (int)((double)intCats[j * sqrt + i] * ((double)percent/100))), intCats[j * sqrt + i]);

                // Moving the cats to the grid spot on the right.
                intCats[j * sqrt + i] -= num;
                intCats[j * sqrt + i + 1] += num;
            }
        }
        updateAfterSweep();
    }

    public void sweepDown(int cats, int percent) {
        int i, j;
        int sqrt = gridDim;
        int num, tempCats;

        if (moves < 0) { return; } // Return if you don't have any more moves available.

        for(i = sqrt - 2; i >= 0; i--)
        {
            for(j = 0; j < sqrt; j++)
            {
                tempCats = Math.min(intCats[i * sqrt + j], cats); // Making sure we don't try to move more cats then we have.

                // Finding the number of cats to move, and making sure we don't go below 0.
                num = Math.min(Math.max(tempCats, (int)((double)intCats[i * sqrt + j] * ((double)percent/100))), intCats[i * sqrt + j]);

                // Moving the cats to the grid spot on the right.
                intCats[i * sqrt + j] -= num;
                intCats[(i + 1) * sqrt + j] += num;
            }
        }
        updateAfterSweep();
    }

    public void useTreat() {
        if(treats > 0 && moves > -1) {
            treats--;
            moves--;
        }
    }

    // Updating the string array for what to display for each grid spot.
    private void updateAfterSweep() {
        int i;
        catsCaught = intCats[GRID_SIZE - 1];
        moves--;

        for(i = 0; i < GRID_SIZE - 1; i++ ) {
            numCats[i] = intCats[i] + " Cats";
        }
    }

    // Returning the number of cats at the ith column, and the jth row.
    public int getCatsAt(int i, int j) {
        return intCats[i + (j * (int)sqrt(GRID_SIZE))];
    }

    // Counting the number of cats left on the field.
    public int getTotalCatsLeft() {
        int i, count = 0;

        for(i = 0; i < intCats.length - 1; i++) {
            count += intCats[i];
        }
        return count;
    }

    public boolean isLost() {
        return moves < 0;
    }

    public boolean isWon() {
        return catsCaught == totalCats;
    }

    // Resetting everything back to the starting state.
    public void reset() {
        int i;
        treats = 3;
        moves = 15;
        catsCaught = 0;
        for(i = 0; i < GRID_SIZE - 1; i++) {
            intCats[i] = startCats;
            numCats[i] = intCats[i] + " Cats";
        }
        intCats[GRID_SIZE - 1] = 0;
        numCats[GRID_SIZE - 1] = "Collection\nArea";
    }

    public void setMoves(int newMoves) {
        moves = newMoves;
    }

    public void setCaught(int newCaught) {
        catsCaught = newCaught;
    }

    public void setTreats(int newTreats) {
        treats = newTreats;
    }

    // Setting the cats at position pos to num.
    public void setCatsAt(int pos, int num) {
        intCats[pos] = num;
        numCats[pos] = intCats[pos] + " Cats";
    }

    // Setting the starting number of cats in each box of the grid.
    public void setStartCats(int newStart) {
        startCats = newStart;
        totalCats = startCats * (GRID_SIZE - 1);
    }
}
