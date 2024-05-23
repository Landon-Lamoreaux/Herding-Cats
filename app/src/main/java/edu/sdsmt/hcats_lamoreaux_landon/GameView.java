package edu.sdsmt.hcats_lamoreaux_landon;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class GameView extends View {
    private Game game;
    private Paint paint;
    private String[] gridStr;
    private int gridDim;
    private int GRID_SIZE;
    private int boxColor = 0xFF03A9F4;
    private int cellWidth;
    private int cellHeight;
    private Rect rect;

    public GameView(Context context) {
        super(context);
        init(null);
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleSAttr) {
        super(context, attrs, defStyleSAttr);
        init(attrs);
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleSAttr, int defStyleRes) {
        super(context, attrs, defStyleSAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(80);
    }

    public void setGame(Game game1) {
        game = game1;
        GRID_SIZE = game.getGridSize();
        gridDim = game.getWidth();
        gridStr = game.numCats;
        cellWidth = Resources.getSystem().getDisplayMetrics().widthPixels / gridDim;
        cellHeight = (Resources.getSystem().getDisplayMetrics().heightPixels / 2) / gridDim;

        int left, right, top, bottom = 0;
        left = 2 * cellWidth;
        top = (int)(3.5 * cellHeight);
        right = left + cellWidth;
        bottom = top + cellHeight;
        rect = new Rect(left, top, right, bottom);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        String text = "";
        float textSize;
        String[] textArr;
        int i, j, y = 0;
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLACK);

        // Draw grid lines
        for (i = 0; i < gridDim + 1; i++) {
            canvas.drawLine(0, (int)((i+1.5) * cellHeight), cellWidth * gridDim, (int)((i+1.5) * cellHeight), paint);
        }
        for (i = 0; i < gridDim + 1; i++) {
            canvas.drawLine(i * cellWidth, (int)(cellHeight * 1.5), i * cellWidth, (int)(cellHeight * 4.5), paint);
        }

        // Drawing the text in a grid shape.
        for (i = 0; i < gridDim; i++) {
            for (j = 0; j < gridDim; j++) {

                // If last box, draw it different.
                if ((i+1) * (j+1) == gridStr.length) {
                    paint.setColor(boxColor); // Color of the box.
                    canvas.drawRect(rect, paint);
                    paint.setColor(Color.WHITE); // White

                    // Drawing the 2 final lines.
                    textArr = gridStr[i * gridDim + j].split("\n");
                    y = (i+2) * cellHeight;
                    for(String line : textArr) {
                        textSize = paint.measureText(line) / 2;
                        canvas.drawText(line, (j * cellWidth) + (((float)cellWidth / 2) - textSize), y, paint);
                        y += (int)paint.getTextSize();
                    }
                    continue;

                } else {
                    paint.setColor(Color.BLACK); // Black
                }

                text = gridStr[i * gridDim + j];
                if (text != null) {
                    textSize = paint.measureText(text) / 2;
                    canvas.drawText(text, (j * cellWidth) + (((float)cellWidth / 2) - textSize), (i+2) * cellHeight, paint);
                }
            }
        }
    }

    public void updateText(String[] numCats) {

        // Updating the text to be what it changed to in the model.
        gridStr = numCats;

        // Invalidating so the view gets redrawn.
        invalidate();
    }

    public int getColor() { return boxColor; }

    // Updating the color of the collection area.
    public void setColor(int color) {
        boxColor = color;
        invalidate();
    }
}
