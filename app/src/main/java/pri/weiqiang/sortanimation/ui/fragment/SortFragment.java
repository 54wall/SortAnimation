package pri.weiqiang.sortanimation.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import pri.weiqiang.sortanimation.R;
import pri.weiqiang.sortanimation.algorithm.SortArrayList;
import pri.weiqiang.sortanimation.common.AlgorithmAnimationListener;
import pri.weiqiang.sortanimation.common.AnimationScenarioItem;
import pri.weiqiang.sortanimation.common.AnimationsCoordinator;
import pri.weiqiang.sortanimation.constant.Constant;
import pri.weiqiang.sortanimation.ui.customview.BubbleView;
import pri.weiqiang.sortanimation.util.Util;

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
    private Spinner algorithmSpinner;
    private int algorithmSelected = Constant.ALGORITHM_PUBBLE;
    private int mWidth;
    private int mRectHeight;
    private View view;
    View.OnClickListener buttonClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String inputUserArray = mEtInput.getText().toString();
            if (!TextUtils.isEmpty(inputUserArray)) {
                resetPreviousData();
                animationioList = new ArrayList<>();
                ArrayList<Integer> integerArrayList = new ArrayList<>(convertToIntArray(inputUserArray));
                drawBubbles(integerArrayList);
//                generateSortScenario(integerArrayList);
//                SortArrayList.pubbleSort(integerArrayList,animationioList);
                sort(integerArrayList, animationioList);
//                Log.e(TAG, "runAnimationIteration onClick");
                runAnimationIteration();
            } else {
                Toast.makeText(getContext(), R.string.empty_field_warning, Toast.LENGTH_LONG).show();
            }
        }
    };

    private void sort(ArrayList<Integer> unsortedValues, ArrayList<AnimationScenarioItem> animationioList) {
        switch (algorithmSelected) {

            case Constant.ALGORITHM_PUBBLE:
                SortArrayList.pubbleSort(unsortedValues, animationioList);
                break;
            case Constant.ALGORITHM_INSERT:
                SortArrayList.insertSort(unsortedValues, animationioList);
                break;
            case Constant.ALGORITHM_SELECT:
                SortArrayList.selectSort(unsortedValues, animationioList);
                break;
            case Constant.ALGORITHM_QUICK:

                break;
            case Constant.ALGORITHM_MERGE:

                break;
            case Constant.ALGORITHM_HEER:

                break;
        }
    }

    private void resetPreviousData() {
//        Log.e(TAG, "resetPreviousData");
        if (isAnimationRunning && animationsCoordinator != null) {
            animationsCoordinator.cancelAllVisualisations();
            isAnimationRunning = false;
        }
        scenarioItemIndex = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        Log.e(TAG, "onCreateView");
        view = inflater.inflate(R.layout.fragment_sort, container, false);
        mEtInput = view.findViewById(R.id.et_input);
        mBtnStart = view.findViewById(R.id.btn_start);
        mBtnStart.setOnClickListener(buttonClickListener);
        algorithmSpinner = view.findViewById(R.id.spinner_algorithm);
        algorithmSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "algorithmSpinner:" + parent.getItemAtPosition(position).toString());
                switch (position) {
                    case Constant.ALGORITHM_PUBBLE:
                        algorithmSelected = Constant.ALGORITHM_PUBBLE;
                        break;
                    case Constant.ALGORITHM_INSERT:
                        algorithmSelected = Constant.ALGORITHM_INSERT;
                        break;
                    case Constant.ALGORITHM_SELECT:
                        algorithmSelected = Constant.ALGORITHM_SELECT;
                        break;
                    case Constant.ALGORITHM_QUICK:
                        algorithmSelected = Constant.ALGORITHM_QUICK;
                        break;
                    case Constant.ALGORITHM_MERGE:
                        algorithmSelected = Constant.ALGORITHM_MERGE;
                        break;
                    case Constant.ALGORITHM_HEER:
                        algorithmSelected = Constant.ALGORITHM_HEER;
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        algorithmSpinner.setSelection(1, true);
        mLlContainer = view.findViewById(R.id.ll_container);
        mWidth = view.getMeasuredWidth();
        mRectHeight = view.getMeasuredHeight();
//        Log.e(TAG, "ll_container mWidth:" + mWidth + ",mRectHeight:" + mRectHeight);
        animationsCoordinator = new AnimationsCoordinator(mLlContainer);
        animationsCoordinator.addListener(new AlgorithmAnimationListener() {
            @Override
            public void onSwapStepAnimationEnd(int endedPosition) {
//                Log.e(TAG, "addListener AlgorithmAnimationListener:runAnimationIteration!!!!");
                runAnimationIteration();
            }
        });
        return view;
    }

    private void runAnimationIteration() {
//        Log.e(TAG, "runAnimationIteration");
        isAnimationRunning = true;
        if (animationioList != null && animationioList.size() == scenarioItemIndex) {
            animationsCoordinator.showFinish();
            return;
        }
        if (animationioList != null && !animationioList.isEmpty() && animationioList.size() > scenarioItemIndex) {

            AnimationScenarioItem animationStep = animationioList.get(scenarioItemIndex);
//            Log.e(TAG,"scenarioItemIndex:"+scenarioItemIndex+"animationStep.getCurPosition():"+animationStep.getCurPosition()+",animationStep.getNextPosition():"+animationStep.getNextPosition());
            scenarioItemIndex++;
            if (animationStep.isShouldBeSwapped()) {
                animationsCoordinator.showSwapStep(animationStep.getCurPosition(), animationStep.getNextPosition(), animationStep.isFinalPlace());
            } else {
                animationsCoordinator.showNonSwapStep(animationStep.getCurPosition(), animationStep.getNextPosition(), animationStep.isFinalPlace());
            }
        }

    }

    private void swap(final ArrayList<Integer> list, final int inner) {
//        Log.e(TAG, "swap");
        int temp = list.get(inner);
        list.set(inner, list.get(inner + 1));
        list.set(inner + 1, temp);
    }

    private void drawBubbles(ArrayList<Integer> listToDraw) {
//        Log.e(TAG, "drawBubbles");
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
//        Log.e(TAG, "createCalculatedBitmap");
        mWidth = view.getMeasuredWidth();
        mRectHeight = view.getMeasuredHeight() * currentIntValue / 10;
//        Log.e(TAG, "ll_container mWidth:" + mWidth + ",mRectHeight:" + mRectHeight);
        final Rect bounds = new Rect();
        Paint paint = new Paint(Paint.LINEAR_TEXT_FLAG);
        paint.setTextSize(BubbleView.TEXT_SIZE);
        paint.getTextBounds(currentIntValue.toString(), 0, currentIntValue.toString().length(), bounds);
//        Log.e(TAG, "bounds:height " + bounds.height() + ",padding:" + PADDING);
//        return Bitmap.createBitmap(bounds.width() + PADDING, bounds.height() + PADDING, Bitmap.Config.ALPHA_8);
        return Bitmap.createBitmap(mWidth / 10, mRectHeight, Bitmap.Config.ALPHA_8);
    }

    private ArrayList convertToIntArray(String inputUserArray) {
//        Log.e(TAG, "convertToIntArray");
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

    private void generateSortScenario(ArrayList<Integer> unsortedValues) {
//        Log.e(TAG, "Start generateSortScenario");
        boolean isLastInLoop;
        for (int i = 0; i < unsortedValues.size() - 1; i++) {
            Log.e(TAG, "Sort i:" + i);
            for (int j = 0; j < unsortedValues.size() - i - 1; j++) {
                if (j == unsortedValues.size() - i - 2) {
                    isLastInLoop = true;
                    Log.e(TAG, "j:" + j + " ,Sort isLastInLoop:" + isLastInLoop);
                } else {
                    isLastInLoop = false;
                    Log.e(TAG, "j:" + j + " ,Sort isLastInLoop:" + isLastInLoop);
                }
                if (unsortedValues.get(j) > unsortedValues.get(j + 1)) {
                    swap(unsortedValues, j);
                    animationioList.add(new AnimationScenarioItem(true, j, j + 1, isLastInLoop));
                } else {
                    animationioList.add(new AnimationScenarioItem(false, j, j, isLastInLoop));
                }
            }
        }
    }

}
