package pri.weiqiang.sortanimation.ui.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;

import pri.weiqiang.sortanimation.R;

public class RectView extends AppCompatImageView {
    public static final int TEXT_BASELINE_Y = 105;
    public static final float TEXT_SIZE = 45f;
    Paint paint;
    Rect bounds;
    private String TAG = RectView.class.getSimpleName();
    private Integer valueToDraw;
    private boolean isSelected;
    private boolean isOnFinalPlace;
    private int mWidth;
    private int mRectHeight;
    private int mRectWidth;

    public RectView(Context context) {
        this(context, null);
        init();
    }

    public RectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public RectView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        mRectWidth = mWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (valueToDraw != null) {
            String text = valueToDraw.toString();
            if (isOnFinalPlace) {
                paint.setColor(getResources().getColor(R.color.colorPrimaryDark));
            } else {
                if (isSelected) {
                    paint.setColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    paint.setColor(getResources().getColor(R.color.colorAccent));
                }
            }
            canvas.drawRect(0, 5, mRectWidth, mRectHeight, paint);
            paint.setColor(Color.WHITE);
            paint.setTextAlign(Paint.Align.CENTER);//以文字中间位置为坐标
            canvas.drawText(text, mRectWidth / 2, TEXT_BASELINE_Y, paint);
        }
    }

    public Integer getNumber() {
        return valueToDraw;
    }

    /**
     * 在矩形中绘制相应数字
     *
     * @param valueToDraw value which should appears in the center of {@link RectView}
     */
    public void setNumber(Integer valueToDraw) {
        this.valueToDraw = valueToDraw;
        invalidate();
    }

    /**
     * 矩形位于排序的最后位置，背景颜色将会置为最终颜色
     */
    public void setIsOnFinalPlace(boolean isOnFinalPlace) {
        this.isOnFinalPlace = isOnFinalPlace;
        invalidate();
    }


    /**
     * 处于比较中的矩形，背景颜色处于选中状态
     */
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
        invalidate();
    }
}
