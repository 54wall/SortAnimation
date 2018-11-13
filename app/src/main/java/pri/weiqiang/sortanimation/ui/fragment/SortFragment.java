package pri.weiqiang.sortanimation.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
    public static final int minHeight = 150;
    public static final int RECT_MARGIN = 1;//4
    private LinearLayout viewLine;
    private SparseIntArray lineHeightArray = new SparseIntArray();
    private String TAG = SortFragment.class.getSimpleName();
    private EditText mEtInput;
    private Button mBtnStart;
    private boolean isAnimationRunning;
    private int scenarioItemIndex = 0;
    private LinearLayout mLlContainer;
    private LinearLayout mLlContainerMerge;
    private LinearLayout mLlContainerMerge0;
    private LinearLayout mLlContainerMerge1;
    private RelativeLayout mRlContainerParent;
    private AnimationsCoordinator animationsCoordinator;
    private ArrayList<AnimationScenarioItem> animationList;
    private ArrayList<Integer> keyList;
    private Spinner algorithmSpinner;
    private int algorithmSelected = Constant.ALGORITHM_PUBBLE;
    private int mWidth;
    private int mRectHeight;
    private int maxRectHeight;
    private int minRectHeight;
    private View view;
    private StringBuffer stringBuffer;
    private int rectCount = 0;
    private View.OnClickListener buttonClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String input = mEtInput.getText().toString();
            if (!TextUtils.isEmpty(input)) {
                resetPreviousData();
                animationList = new ArrayList<>();
                ArrayList<Integer> integerList = new ArrayList<>(Util.convertToIntArray(input));
                //更新数据，minRectHeight maxRectHeight 同步重置
                minRectHeight = integerList.get(0);
                maxRectHeight = integerList.get(0);
                for (int i = 0; i < integerList.size(); i++) {
                    int height = integerList.get(i);

                    if (height > maxRectHeight) {
                        maxRectHeight = height;
                    }
                    if (height < minRectHeight) {
                        minRectHeight = height;
                    }
                }
                rectCount = integerList.size();
                drawRects(integerList);
                sort(integerList, animationList);
                runAnimationIteration();
            } else {
                Toast.makeText(getContext(), R.string.empty_field_warning, Toast.LENGTH_LONG).show();
            }
        }
    };

    private void sort(ArrayList<Integer> unsortedValues, ArrayList<AnimationScenarioItem> animationioList) {
        mRlContainerParent.setVisibility(View.VISIBLE);
        mLlContainerMerge.setVisibility(View.GONE);
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
                keyList = new ArrayList<>();
                SortArrayList.quickSort(unsortedValues, 0, unsortedValues.size() - 1, animationioList, keyList);
                break;
            case Constant.ALGORITHM_MERGE:
                mRlContainerParent.setVisibility(View.GONE);
                mLlContainerMerge.setVisibility(View.VISIBLE);
                SortArrayList.mergeSort(unsortedValues, 0, unsortedValues.size() - 1, animationioList);
                break;
            case Constant.ALGORITHM_HEER:
                SortArrayList.heerSort(unsortedValues, animationioList);
                break;
            case Constant.ALGORITHM_HEAP:
                SortArrayList.heapSort(unsortedValues, unsortedValues.size(), animationioList);
                break;

        }
        stringBuffer = new StringBuffer();
        for (int i = 0; i < unsortedValues.size(); i++) {
            stringBuffer.append(unsortedValues.get(i) + ",");
        }
        Log.e(TAG, "排序结束后:" + stringBuffer);
    }

    private void resetPreviousData() {
        Log.e(TAG, "resetPreviousData");
        if (isAnimationRunning && animationsCoordinator != null) {
            animationsCoordinator.cancelAllVisualisations();
            isAnimationRunning = false;
        }
        scenarioItemIndex = 0;
        if (viewLine != null) {
            Log.e(TAG, "removeView viewLine!");
            mRlContainerParent.clearAnimation();
            mRlContainerParent.removeView(viewLine);
        }
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
        algorithmSpinner.setSelection(Constant.ALGORITHM_MERGE, true);
        mLlContainer = view.findViewById(R.id.ll_container);
        mRlContainerParent = view.findViewById(R.id.rl_container_parent);
        mLlContainerMerge = view.findViewById(R.id.ll_container_merge);
        mLlContainerMerge0 = view.findViewById(R.id.ll_container_merge_0);
        mLlContainerMerge1 = view.findViewById(R.id.ll_container_merge_1);

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
        Log.e(TAG, "runAnimationIteration");
        isAnimationRunning = true;
        if (animationList != null && animationList.size() == scenarioItemIndex) {
            animationsCoordinator.showFinish();
            return;
        }
        if (animationList != null && !animationList.isEmpty() && animationList.size() > scenarioItemIndex) {

            AnimationScenarioItem animationStep = animationList.get(scenarioItemIndex);
            scenarioItemIndex++;
            if (keyList != null && !keyList.isEmpty() && keyList.size() > scenarioItemIndex) {
                int height = lineHeightArray.get(keyList.get(scenarioItemIndex));
                if (height < minHeight) {
                    viewLine.setY(minHeight);
                } else {
                    viewLine.setY(height);
                }
            }
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
        if (mLlContainerMerge0 != null) {
            mLlContainerMerge0.removeAllViews();
            mLlContainerMerge0.clearAnimation();
        }
        if (mLlContainerMerge1 != null) {
            mLlContainerMerge1.removeAllViews();
            mLlContainerMerge1.clearAnimation();
        }

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int marginInPx = Util.dpToPx(getContext(), RECT_MARGIN);
        lp.setMargins(0, 0, marginInPx, 0);
        int pos = 0;

        for (Integer currentIntValue : listToDraw) {
            RectView rectView = new RectView(getContext());
            rectView.setImageBitmap(createCalculatedBitmap(currentIntValue));
            rectView.setMinimumHeight(minHeight);//避免0等较小数值，没有高度
            rectView.setNumber(currentIntValue);
            rectView.setId(pos);

            if (algorithmSelected == Constant.ALGORITHM_MERGE) {
                mLlContainerMerge0.addView(rectView, lp);
                RectView rectView1 = new RectView(getContext());
                rectView1.setImageBitmap(createCalculatedBitmap(currentIntValue));
                rectView1.setMinimumHeight(minHeight);//避免0等较小数值，没有高度
                rectView1.setNumber(currentIntValue);
                rectView1.setId(pos);
                mLlContainerMerge1.addView(rectView1, lp);
            } else {
                if (mLlContainer != null) {
                    mLlContainer.addView(rectView, lp);
                }
            }
            pos++;
        }
        //快速排序中，绘制关键字比较线
        if (algorithmSelected == Constant.ALGORITHM_QUICK) {
            RelativeLayout.LayoutParams lpLine = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            lpLine.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            lpLine.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            viewLine = (LinearLayout) inflater.inflate(R.layout.view_line, null);
            mRlContainerParent.addView(viewLine, lpLine);
        }
    }

    /**
     * Calculates size of ImageView which would be generated with current text value.
     *
     * @param currentIntValue
     * @return empty bitmap with calculated size
     */
    private Bitmap createCalculatedBitmap(Integer currentIntValue) {
        mWidth = view.getMeasuredWidth();
        //maxRectHeight-minRectHeight+1:+1 是因为计算的是距离，否则最高的矩形还是会超出屏幕，minRectHeight即minIntValue
        //4/5 是为了使最高高度不超出屏幕，否则当最大值为比较的关键值时，快拍的关键字线viewLine将超出视野，
        mRectHeight = view.getMeasuredHeight() * 4 / 5 * (currentIntValue - minRectHeight) / (maxRectHeight - minRectHeight + 1) + 1;
        lineHeightArray.put(currentIntValue, mRectHeight);
        final Rect bounds = new Rect();
        Paint paint = new Paint(Paint.LINEAR_TEXT_FLAG);
        paint.setTextSize(RectView.TEXT_SIZE);
        paint.getTextBounds(currentIntValue.toString(), 0, currentIntValue.toString().length(), bounds);
        return Bitmap.createBitmap(mWidth / rectCount - RECT_MARGIN, mRectHeight, Bitmap.Config.ALPHA_8);
    }

}
