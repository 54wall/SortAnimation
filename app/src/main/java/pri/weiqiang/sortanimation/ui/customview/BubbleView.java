package pri.weiqiang.sortanimation.ui.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

import pri.weiqiang.sortanimation.R;

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
    private int mCount = 1;
    private int mWidth;
    private int mRectHeight;
    private int mRectWidth;
    private LinearGradient mLinearGradient;
    private double mRandom;
    private float mcurrentHeight;

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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mRectHeight = getMeasuredHeight();
        mRectWidth = mWidth / mCount;
        Log.e(TAG, "onSizeChanged mWidth:" + mWidth + ",mRectWidth:" + mRectWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        Log.e(TAG,"onDraw()");
        super.onDraw(canvas);
        if (valueToDraw != null) {
            String text = valueToDraw.toString();
//            paint.getTextBounds(text, 0, text.length(), bounds);
            if (isOnFinalPlace) {
                paint.setColor(getResources().getColor(R.color.colorPrimaryDark));
            } else {
                if (isSelected) {
                    paint.setColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    paint.setColor(getResources().getColor(R.color.colorAccent));
                }
            }
//            Log.e(TAG, "!!!!onDraw mcurrentHeight:" + mcurrentHeight + ",mRectWidth:" + mRectWidth + ",mRectHeight:" + mRectHeight);
            canvas.drawRect(0, 5, mRectWidth, mRectHeight, paint);
//            canvas.drawRect(0, /*TOP_POS+*/valueToDraw, bounds.width() + PADDING, BOTTOM_POS, paint);
//            canvas.drawOval(0, TOP_POS, bounds.width() + PADDING, BOTTOM_POS, paint);//绘制圆形
            paint.setColor(Color.WHITE);
            canvas.drawText(text, START_X_POS, TEXT_BASELINE_Y, paint);
        }
    }


    /**
     * Draws a number as a bitmap inside of the bubble circle.
     *
     * @param numberValueToDraw value which should appears in the center of {@link BubbleView}
     */
    public void setNumber(Integer numberValueToDraw) {
        valueToDraw = numberValueToDraw;
        invalidate();
    }

    /**
     * Background color of bubble will be changed to dark blue.
     *
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
     *
     * @param isSelected
     */
    public void setBubbleSelected(boolean isSelected) {
        this.isSelected = isSelected;
        invalidate();
    }
}
