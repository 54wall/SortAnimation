package com.ukhanoff.bubblesort.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ukhanoff.bubblesort.R;
import com.ukhanoff.bubblesort.common.AlgorithmAnimationListener;
import com.ukhanoff.bubblesort.common.AnimationScenarioItem;
import com.ukhanoff.bubblesort.common.AnimationsCoordinator;
import com.ukhanoff.bubblesort.ui.customview.BubbleView;
import com.ukhanoff.bubblesort.util.Util;

import java.util.ArrayList;

/**
 * Main fragment where sorting visualisation appears
 */

public class SortFragment extends Fragment {
    public static final int PADDING = 50;
    public static final int BUBBLE_MARGIN = 4;
    private String TAG = SortFragment.class.getSimpleName();
    private EditText mEtInput;
    private Button mBtnStart;
    private boolean isAnimationRunning;
    private int scenarioItemIndex = 0;
    private LinearLayout mLlContainer;
    private AnimationsCoordinator animationsCoordinator;
    private ArrayList<AnimationScenarioItem> animationioList;
    View.OnClickListener buttonClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String inputUserArray = mEtInput.getText().toString();
            if (!TextUtils.isEmpty(inputUserArray)) {
                resetPreviousData();
                animationioList = new ArrayList<>();
                ArrayList<Integer> integerArrayList = new ArrayList<>(convertToIntArray(inputUserArray));
                drawBubbles(integerArrayList);
                generateSortScenario(integerArrayList);
                Log.e(TAG, "runAnimationIteration onClick");
                runAnimationIteration();
            } else {
                Toast.makeText(getContext(), R.string.empty_field_warning, Toast.LENGTH_LONG).show();
            }
        }
    };

    private void resetPreviousData() {
        Log.e(TAG, "resetPreviousData");
        if (isAnimationRunning && animationsCoordinator != null) {
            animationsCoordinator.cancelAllVisualisations();
            isAnimationRunning = false;
        }
        scenarioItemIndex = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_sort, container, false);
        mEtInput = view.findViewById(R.id.et_input);
        mBtnStart = view.findViewById(R.id.btn_start);
        mBtnStart.setOnClickListener(buttonClickListener);
        mLlContainer = view.findViewById(R.id.ll_container);
        animationsCoordinator = new AnimationsCoordinator(mLlContainer);
        animationsCoordinator.addListener(new AlgorithmAnimationListener() {
            @Override
            public void onSwapStepAnimationEnd(int endedPosition) {
                Log.e(TAG, "onSwapStepAnimationEnd endedPosition:"+endedPosition);
                runAnimationIteration();
            }
        });

        mBtnStart.callOnClick();
        return view;
    }

    private void runAnimationIteration() {
        Log.e(TAG, "runAnimationIteration");
        isAnimationRunning = true;
        if (animationioList != null && animationioList.size() == scenarioItemIndex) {
            animationsCoordinator.showFinish();
            return;
        }
        if (animationioList != null && !animationioList.isEmpty() && animationioList.size() > scenarioItemIndex) {
            AnimationScenarioItem animationStep = animationioList.get(scenarioItemIndex);
            scenarioItemIndex++;
            if (animationStep.isShouldBeSwapped()) {
                animationsCoordinator.showSwapStep(animationStep.getAnimationViewItemPosition(), animationStep.isFinalPlace());
            } else {
                animationsCoordinator.showNonSwapStep(animationStep.getAnimationViewItemPosition(), animationStep.isFinalPlace());
            }
        }

    }

    private void swap(final ArrayList<Integer> list, final int inner) {
        Log.e(TAG, "swap");
        int temp = list.get(inner);
        list.set(inner, list.get(inner + 1));
        list.set(inner + 1, temp);
    }

    private void drawBubbles(ArrayList<Integer> listToDraw) {
        Log.e(TAG, "drawBubbles");
        if (mLlContainer != null) {
            mLlContainer.removeAllViews();
            mLlContainer.clearAnimation();
        }

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int marginInPx = Util.dpToPx(getContext(), BUBBLE_MARGIN);
        lp.setMargins(0, 0, marginInPx, 0);

        int pos = 0;
        for (Integer currentIntValue : listToDraw) {
            BubbleView bubbleView = new BubbleView(getContext());
            bubbleView.setImageBitmap(createCalculatedBitmap(currentIntValue));
            bubbleView.setMinimumHeight(250);
            bubbleView.setNumber(currentIntValue);
            bubbleView.setId(pos);
            if (mLlContainer != null) {
                mLlContainer.addView(bubbleView, lp);
            }
            pos++;
        }
    }

    /**
     * Calculates size of ImageView which would be generated with current text value.
     *
     * @param currentIntValue
     * @return empty bitmap with calculated size
     */
    private Bitmap createCalculatedBitmap(Integer currentIntValue) {
        Log.e(TAG, "createCalculatedBitmap");
        final Rect bounds = new Rect();
        Paint paint = new Paint(Paint.LINEAR_TEXT_FLAG);
        paint.setTextSize(BubbleView.TEXT_SIZE);
        paint.getTextBounds(currentIntValue.toString(), 0, currentIntValue.toString().length(), bounds);
        return Bitmap.createBitmap(bounds.width() + PADDING, bounds.height() + PADDING, Bitmap.Config.ALPHA_8);
    }

    private ArrayList convertToIntArray(String inputUserArray) {
        Log.e(TAG, "convertToIntArray");
        ArrayList<Integer> parsedUserArray = new ArrayList<>();
        String[] stringArray = inputUserArray.split(",");
        int numberOfElements = stringArray.length;
        for (int i = 0; i < numberOfElements; i++) {
            if (!TextUtils.isEmpty(stringArray[i])) {
                parsedUserArray.add(Integer.parseInt(stringArray[i]));
            }
        }
        return parsedUserArray;

    }

    private ArrayList<Integer> generateSortScenario(ArrayList<Integer> unsortedValues) {
        Log.e(TAG, "generateSortScenario");
        ArrayList<Integer> values = new ArrayList<>(unsortedValues);
        boolean isLastInLoop;
        for (int i = 0; i < values.size() - 1; i++) {
            for (int j = 0; j < values.size() - i - 1; j++) {
                if (j == values.size() - i - 2) {
                    isLastInLoop = true;
                } else {
                    isLastInLoop = false;
                }
                if (values.get(j) > values.get(j + 1)) {
                    swap(values, j);
                    animationioList.add(new AnimationScenarioItem(true, j, isLastInLoop));
                } else {
                    animationioList.add(new AnimationScenarioItem(false, j, isLastInLoop));
                }
            }
        }
        return values;
    }

}
