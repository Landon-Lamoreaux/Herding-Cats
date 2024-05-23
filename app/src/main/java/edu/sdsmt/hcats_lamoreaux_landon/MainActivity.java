package edu.sdsmt.hcats_lamoreaux_landon;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.sdsmt.hcats_lamoreaux_landon.databinding.ActivityMainBinding;

/*
Yes 	Pulled the most recent unit tests at submission time, and ensure they still pass.
Yes	    All grading tags added if the tier was reached.


Tier 1: Model		42
Yes	Unit Tests all pass

Tier 2: Connect Views		16
Yes	Unit Tests all pass

Tier 3a: State Machine/Event Rules *	36
Yes	Framework there
Yes	Unit Tests all pass

Tier 3b: Floating Action		18
Yes	All buttons there
Yes	Icons set and distinguishable
Yes	Opens/closes properly
Yes	Player color updated.

Tier 3c: Layout **	(-50% each line if fails in on orientation)	26
Yes	Custom’s View’s aspect ratio constant
Yes	Relative size of objects in view maintained
Yes	Works in required screen sizes

Tier 3d: Rotation		20
Yes	Required state saved on rotation

Tier 3e: Unit Test		10
Yes	Test there
Yes	Updated all values to support check
Yes	Triggered rotation
Yes	Checked all values after rotation (does NOT require passing since those points are in 3d)

Tier 4: Extensions		30
Extension 1: 5a 5pt Disable treats button: When the treats become 0 the button becomes disabled.
Extension 2: 5d 25pt Hard/Easy mode: Click on the wrench icon and select "Hard" there will now be 7
                                        cats in each grid spot. You can beat it by clicking down/right
                                        5 times in a row, then using a treat on each subsequent click
                                        going the other direction. Example 5 down clicks, then 3 right
                                        clicks each with a treat, then 4 right clicks with out a treat.
 */

public class MainActivity extends AppCompatActivity {
    private StateMachine stateMachine;
    private Game game;
    private GameView gameView;
    private TextView treats;
    private TextView moves;
    private TextView caught;
    private Button treatsBtn;
    private AlertDialog dialog;
    private static final String savedMoves = "moves";
    private static final String savedCaught = "caught";
    private static final String savedTreats = "treats";
    private static final String[] savedCats = {"0", "1", "2", "3", "4", "5", "6", "7"};
    private static final String savedColor = "color";
    private static final String savedMode = "mode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = new Game();
        stateMachine = new StateMachine(this, game);
//        gameView = new GameView(this);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setGame(game);

        gameView = findViewById(R.id.GridView);
        gameView.setGame(game);

        // Getting the 3 text view objects.
        treats = findViewById(R.id.treats);
        caught = findViewById(R.id.caught);
        moves = findViewById(R.id.moves);

        // Getting the treat button.
        treatsBtn = findViewById(R.id.treatBtn);

        updateTextViews();

        setTitle("Herding Cats");
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    public void onTreat(View view) {
        stateMachine.doTreat();
        updateTextViews();
    }
    public void onRight(View view) {
        stateMachine.sweepRight();
        gameView.updateText(game.getNumCats());
        updateTextViews();
    }
    public void onDown(View view) {
        stateMachine.sweepDown();
        gameView.updateText(game.getNumCats());
        updateTextViews();
    }
    public void onReset(View view) {
        game.reset();
        gameView.updateText(game.getNumCats());
        stateMachine.setState(StateMachine.StateEnum.High);
        updateTextViews();
    }

    public void onHard(View view) {
        game.setStartCats(7);
        game.reset();
        stateMachine.setState(StateMachine.StateEnum.High);
        gameView.updateText(game.getNumCats());
        updateTextViews();
    }

    public void onEasy(View view) {
        game.setStartCats(5);
        game.reset();
        stateMachine.setState(StateMachine.StateEnum.High);
        gameView.updateText(game.getNumCats());
        updateTextViews();
    }

    // Changing the color of the collection area box.
    public void changeColor(View view) {
        String tag = (String)view.getTag();
        if("Purple".equals(tag)) {
            gameView.setColor(0xFF673AB7);
        }
        else if("Green".equals(tag)) {
            gameView.setColor(0xFF4CAF50);
        }
        else if("Blue".equals(tag)) {
            gameView.setColor(0xFF03A9F4);
        }
    }

    // Updating the text boxes for moves, treats, and cats caught.
    private void updateTextViews() {
        treats.setText(String.format(((Integer)game.getTreats()).toString()));
        caught.setText(String.format(((Integer)game.getCatsCaught()).toString()));
        moves.setText(String.format(((Integer)game.getMoves()).toString()));
        if(game.getTreats() == 0)
            treatsBtn.setEnabled(false);
        else
            treatsBtn.setEnabled(true);
    }
    public StateMachine getStateMachine() {
        return stateMachine;
    }

    public Game getGame() {
        return game;
    }

    // Displaying the floating action button dialog.
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.fab_color_picker);
        dialog = builder.create();
        dialog.show();
    }

    // Saving the instance state before a rotate.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        int i;
        int dim = game.getWidth();

        savedInstanceState.putInt(savedMoves, game.getMoves());
        savedInstanceState.putInt(savedCaught, game.getCatsCaught());
        savedInstanceState.putInt(savedTreats, game.getTreats());
        savedInstanceState.putInt(savedColor, gameView.getColor());
        savedInstanceState.putInt(savedMode, game.getStartCats());

        for(i = 0; i < savedCats.length; i++) {
            savedInstanceState.putInt(savedCats[i], game.getCatsAt(i%dim, i/dim));
        }
    }

    // Restoring the instance state after rotate;
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int i;

        game.setMoves(savedInstanceState.getInt(savedMoves));
        game.setCaught(savedInstanceState.getInt(savedCaught));
        game.setTreats(savedInstanceState.getInt(savedTreats));
        game.setStartCats(savedInstanceState.getInt(savedMode));

        for(i = 0; i < savedCats.length; i++) {
            game.setCatsAt(i, savedInstanceState.getInt(savedCats[i]));
        }
        gameView.updateText(game.getNumCats());

        gameView.setColor(savedInstanceState.getInt(savedColor));

        updateTextViews();
    }

}