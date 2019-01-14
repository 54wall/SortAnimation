package com.ukhanoff.bubblesort.ui.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.ukhanoff.bubblesort.R;

import static com.ukhanoff.bubblesort.ui.fragment.SortFragment.PADDING;

/**
 * This is custom ImageView which could draw a "Bubble with a number inside".
 */

public class BubbleView extends AppCompatImageView {
    public static final int START_X_POS = 25;
    public static final int TEXT_BASELINE_Y = 105;
    public static final int BOTTOM_POS = 120;
    public static final int TOP_POS = 60;
    public static final float TEXT_SIZE = 45f;
    //方法2 直接new 避免avoid object allocation during draw/layout operations (prelocate and reuse instead)
//    Paint paint = new Paint(Paint.LINEAR_TEXT_FLAG);
//    Rect bounds = new Rect();
    Paint paint;
    Rect bounds;
    private String TAG = BubbleView.class.getSimpleName();
    private Integer valueToDraw;
    private boolean isSelected;
    private boolean isOnFinalPlace;

    public BubbleView(Context context) {
        this(context, null);
        init();
    }

    public BubbleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public BubbleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint(Paint.LINEAR_TEXT_FLAG);
        paint.setAntiAlias(true);
        paint.setTextSize(TEXT_SIZE);
        bounds = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        Log.e(TAG,"onDraw()");
        super.onDraw(canvas);
        if (valueToDraw != null) {
            String text = valueToDraw.toString();
            paint.getTextBounds(text, 0, text.length(), bounds);
            if (isOnFinalPlace) {
                paint.setColor(getResources().getColor(R.color.colorPrimaryDark));
            } else {
                if (isSelected) {
                    paint.setColor(getResources().getColor(R.color.colorIndigo));
                } else {
                    paint.setColor(getResources().getColor(R.color.colorAccent));
                }
            }
            canvas.drawOval(0, TOP_POS, bounds.width() + PADDING, BOTTOM_POS, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText(text, START_X_POS, TEXT_BASELINE_Y, paint);
        }
    }

    /**
     * Draws a number as a bitmap inside of the bubble circle.
     * 在小球中央绘制数字
     * @param numberValueToDraw value which should appears in the center of {@link BubbleView}
     */
    public void setNumber(Integer numberValueToDraw) {
        valueToDraw = numberValueToDraw;
        invalidate();
    }

    /**
     * Background color of bubble will be changed to dark blue.
     *  设置小球处于未选中状态，背景颜色将作出相应改变
     * @param isOnFinalPlace
     */
    public void setBubbleIsOnFinalPlace(boolean isOnFinalPlace) {
        this.isOnFinalPlace = isOnFinalPlace;
        invalidate();
    }

    public boolean isBubbleSelected() {
        return isSelected;
    }

    /**
     * Background color will be changed to blue if true
     * 设置小球处于选中状态，背景颜色将作出相应改变
     *
     * @param isSelected
     */
    public void setBubbleSelected(boolean isSelected) {
        this.isSelected = isSelected;
        invalidate();
    }
}
