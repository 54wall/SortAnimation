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
import pri.weiqiang.sortanimation.ui.customview.RectView;
import pri.weiqiang.sortanimation.util.Util;

/**
 * Main fragment where sorting visualisation appears
 */

public class SortFragment extends Fragment {
    public static final int PADDING = 50;
    public static final int RECT_MARGIN = 1;//4
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
    private int maxRectHeight;
    private int minRectHeight;
    private View view;
    private StringBuffer stringBuffer;
    private int rectCount = 0;

    View.OnClickListener buttonClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String inputUserArray = mEtInput.getText().toString();
            if (!TextUtils.isEmpty(inputUserArray)) {
                resetPreviousData();
                animationioList = new ArrayList<>();
                ArrayList<Integer> integerArrayList = new ArrayList<>(convertToIntArray(inputUserArray));
                rectCount = integerArrayList.size();
                drawRects(integerArrayList);
                sort(integerArrayList, animationioList);
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
                SortArrayList.quickSort(unsortedValues, 0,unsortedValues.size()-1,animationioList);
                break;
            case Constant.ALGORITHM_MERGE:
                SortArrayList.mergeSort(unsortedValues, 0,unsortedValues.size()-1,animationioList);
                break;
            case Constant.ALGORITHM_HEER:
                SortArrayList.heerSort(unsortedValues,animationioList);
                break;
            case Constant.ALGORITHM_HEAP:
                SortArrayList.heapSort(unsortedValues, unsortedValues.size(),animationioList);
                break;

        }
        stringBuffer= new StringBuffer();
        for (int q = 0; q < unsortedValues.size(); q++) {
            stringBuffer.append(unsortedValues.get(q)+",");
        }
        Log.e(TAG,"排序结束后:"+stringBuffer);
    }

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
                    case Constant.ALGORITHM_HEAP:
                        algorithmSelected = Constant.ALGORITHM_HEAP;
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        algorithmSpinner.setSelection(Constant.ALGORITHM_HEER, true);
        mLlContainer = view.findViewById(R.id.ll_container);
        mWidth = view.getMeasuredWidth();
        mRectHeight = view.getMeasuredHeight();
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

    private void drawRects(ArrayList<Integer> listToDraw) {
        Log.e(TAG, "drawRects");
        if (mLlContainer != null) {
            mLlContainer.removeAllViews();
            mLlContainer.clearAnimation();
        }

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int marginInPx = Util.dpToPx(getContext(), RECT_MARGIN);
        lp.setMargins(0, 0, marginInPx, 0);
        int pos = 0;
        for (Integer currentIntValue : listToDraw) {
            RectView rectView = new RectView(getContext());
            rectView.setImageBitmap(createCalculatedBitmap(currentIntValue));
            rectView.setMinimumHeight(150);//避免0等较小数值，没有高度
            rectView.setNumber(currentIntValue);
            rectView.setId(pos);
            if (mLlContainer != null) {
                mLlContainer.addView(rectView, lp);
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
        //maxRectHeight-minRectHeight+1:+1 是因为计算的是距离，否则最高的矩形还是会超出屏幕
        mRectHeight = view.getMeasuredHeight() * currentIntValue / (maxRectHeight-minRectHeight+1)+1;
        Log.e(TAG,"createCalculatedBitmap currentIntValue:"+currentIntValue+",mRectHeight:"+mRectHeight);
        final Rect bounds = new Rect();
        Paint paint = new Paint(Paint.LINEAR_TEXT_FLAG);
        paint.setTextSize(RectView.TEXT_SIZE);
        paint.getTextBounds(currentIntValue.toString(), 0, currentIntValue.toString().length(), bounds);
        return Bitmap.createBitmap(mWidth / rectCount-RECT_MARGIN, mRectHeight, Bitmap.Config.ALPHA_8);
    }

    private ArrayList convertToIntArray(String inputUserArray) {
        ArrayList<Integer> parsedUserArray = new ArrayList<>();
        String[] stringArray = inputUserArray.split(",");
        int numberOfElements = stringArray.length;
        minRectHeight = Integer.parseInt(stringArray[0]);
        maxRectHeight = Integer.parseInt(stringArray[0]);
        for (int i = 0; i < numberOfElements; i++) {
            if (!TextUtils.isEmpty(stringArray[i])) {
                int hegiht = Integer.parseInt(stringArray[i]);
                if (hegiht > maxRectHeight){
                    maxRectHeight = hegiht;
                }
                if (hegiht < minRectHeight){
                    minRectHeight = hegiht;
                }
                parsedUserArray.add(hegiht);
            }
        }
        return parsedUserArray;

    }

}
