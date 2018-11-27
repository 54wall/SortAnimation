package pri.weiqiang.sortanimation.algorithm;

import android.util.Log;

import java.util.ArrayList;

import pri.weiqiang.sortanimation.animation.AnimationScenarioItem;
import pri.weiqiang.sortanimation.animation.MergeAnimationScenarioItem;

public class SortArrayList {

    private static String TAG = SortArrayList.class.getSimpleName();

    // 冒泡 https://blog.csdn.net/csdn_aiyang/article/details/73108606
    public static void pubbleSort(ArrayList<Integer> unsortedValues, ArrayList<AnimationScenarioItem> animationioList) {
        Log.e(TAG, "冒泡排序");
        Integer temp;// 记录临时变量
        boolean isLastInLoop;
        int size = unsortedValues.size();
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                isLastInLoop = (j == unsortedValues.size() - i - 2);
                if (unsortedValues.get(j + 1) < unsortedValues.get(j)) {
                    temp = unsortedValues.get(j);
                    unsortedValues.set(j, unsortedValues.get(j + 1));
                    unsortedValues.set(j + 1, temp);
                    animationioList.add(new AnimationScenarioItem(true, j, j + 1, isLastInLoop));
                } else {
                    animationioList.add(new AnimationScenarioItem(false, j, j + 1, isLastInLoop));
                }
            }
        }

    }

    // 插入排序 https://blog.csdn.net/csdn_aiyang/article/details/73108606
    public static void insertSort(ArrayList<Integer> unsortedValues, ArrayList<AnimationScenarioItem> animationioList) {
        Log.e(TAG, "插入排序!");
        // 直接插入排序
        for (int i = 1; i < unsortedValues.size(); i++) {
            // 待插入元素
            int temp = unsortedValues.get(i);
            int j;
            for (j = i - 1; j >= 0; j--) {
                // 将大于temp的往后移动一位
                if (unsortedValues.get(j) > temp) {
                    unsortedValues.set(j + 1, unsortedValues.get(j));
                    //插入排序，直到最后排序完成后，才会得知是最后位置
                    animationioList.add(new AnimationScenarioItem(true, j, j + 1, false));
                } else {
                    animationioList.add(new AnimationScenarioItem(false, j, j + 1, false));
                    break;

                }
            }
            //务必全部是j+1,因为j最后为-1,
            unsortedValues.set(j + 1, temp);// 插入进来
            Log.e(TAG, "168 AnimationScenarioItem j:" + j + "temp:" + temp);
        }
    }


    // 选择排序 https://blog.csdn.net/csdn_aiyang/article/details/73108606
    public static void selectSort(ArrayList<Integer> unsortedValues, ArrayList<AnimationScenarioItem> animationioList) {
        Log.e(TAG, "选择排序");
        int min;
        int tmp;
        for (int i = 0; i < unsortedValues.size(); i++) {
            min = unsortedValues.get(i);
            for (int j = i + 1; j < unsortedValues.size(); j++) {
                if (unsortedValues.get(j) < min) {
                    min = unsortedValues.get(j);// 最小值
                    tmp = unsortedValues.get(i);
                    unsortedValues.set(i, min);
                    unsortedValues.set(j, tmp);
                    animationioList.add(new AnimationScenarioItem(true, i, j, false));
                } else {
                    animationioList.add(new AnimationScenarioItem(false, i, j, false));
                }
            }
            //AnimationScenarioItem中的isFinalPlace是针对互换位置后的后边那个项目来讲，如果不增加此处代码，必须更改AnimationScenarioItem
            animationioList.add(new AnimationScenarioItem(false, i, i, true));
        }
    }

    // 快速排序
    public static void quickSort(ArrayList<Integer> unsortedValues, int low, int high, ArrayList<AnimationScenarioItem> animationioList, ArrayList<Integer> keyList) {

        int start = low;
        int end = high;
        int key = unsortedValues.get(start);
        while (end > start) {

            // 从后往前比较，如果没有比关键值小的，比较下一个，直到有比关键值小的交换位置，然后又从前往后比较
            while (end > start && unsortedValues.get(end) >= key) {
                end--;
                animationioList.add(new AnimationScenarioItem(false, start, end, false));
                keyList.add(key);
            }
            if (unsortedValues.get(end) <= key) {
                int temp = unsortedValues.get(end);
                unsortedValues.set(end, unsortedValues.get(start));
                unsortedValues.set(start, temp);
                animationioList.add(new AnimationScenarioItem(true, start, end, false));
                keyList.add(key);
            }
            // 从前往后比较，如果没有比关键值大的，比较下一个，直到有比关键值大的交换位置
            while (end > start && unsortedValues.get(start) <= key) {
                start++;
                animationioList.add(new AnimationScenarioItem(false, start, end, false));
                keyList.add(key);
            }
            if (unsortedValues.get(start) >= key) {
                int temp = unsortedValues.get(start);
                unsortedValues.set(start, unsortedValues.get(end));
                unsortedValues.set(end, temp);
                animationioList.add(new AnimationScenarioItem(true, start, end, false));
                keyList.add(key);
            }
            // 此时第一次循环比较结束，关键值的位置已经确定了。左边的值都比关键值小，右边的值都比关键值大，但是两边的顺序还有可能是不一样的，进行下面的递归调用
        }

        // 递归
        if (start > low) {
            quickSort(unsortedValues, low, start - 1, animationioList, keyList);// 左边序列，第一个索引位置到关键值索引-1
        }
        if (end < high) {
            quickSort(unsortedValues, end + 1, high, animationioList, keyList);// 右边序列，从关键值索引+1到最后一个
        }
    }


    // 归并算法 https://www.cnblogs.com/of-fanruice/p/7678801.html
    public static void mergeSort(ArrayList<Integer> unsortedValues, int low, int high, ArrayList<MergeAnimationScenarioItem> mergeAnimationioList) {
        Log.e(TAG, "归并排序! mergeSort");
        int mid = (low + high) / 2;
        if (low < high) {
            mergeSort(unsortedValues, low, mid, mergeAnimationioList);
            mergeSort(unsortedValues, mid + 1, high, mergeAnimationioList);
            // 左右归并
            merge(unsortedValues, low, mid, high, mergeAnimationioList);
        }
    }

    private static void merge(ArrayList<Integer> unsortedValues, int low, int mid, int high, ArrayList<MergeAnimationScenarioItem> mergeAnimationioList) {
        ArrayList<Integer> temp = new ArrayList<>();
        int i = low;
        int j = mid + 1;
        int k = 0;
        // 把较小的数先移到新数组中
        Log.e(TAG, "开始拆分");
        while (i <= mid && j <= high) {
            Log.e(TAG, "子归并merge i:" + i + ":" + unsortedValues.get(i) + ",j:" + j + ":" + unsortedValues.get(j) + ",mid:" + mid);
            if (unsortedValues.get(i) < unsortedValues.get(j)) {
                //选择原始数组中的较小值直接移动到新数组的最末位
                mergeAnimationioList.add(new MergeAnimationScenarioItem(i, k, false));
                temp.add(k++, unsortedValues.get(i++));
            } else {
                mergeAnimationioList.add(new MergeAnimationScenarioItem(j, k, false));
                temp.add(k++, unsortedValues.get(j++));
            }
        }
        // i<=mid是剩余全部中的较小的，把左边剩余的数移入数组
        while (i <= mid) {
            mergeAnimationioList.add(new MergeAnimationScenarioItem(i, k, false));
            temp.add(k++, unsortedValues.get(i++));
        }
        // j <= high是剩余全部中的大的，把右边边剩余的数移入数组，所以在while (i <= mid) 执行
        while (j <= high) {
            mergeAnimationioList.add(new MergeAnimationScenarioItem(j, k, false));
            temp.add(k++, unsortedValues.get(j++));
        }
        // 把新数组中的数覆盖nums数组
        Log.e(TAG, "合并开始");
        for (int x = 0; x < temp.size(); x++) {
            unsortedValues.set(x + low, temp.get(x));
            //返回原始数组
            mergeAnimationioList.add(new MergeAnimationScenarioItem(x + low, x, true));
        }
    }

    // 希尔排序 https://blog.csdn.net/csdn_aiyang/article/details/73108606
    public static void heerSort(ArrayList<Integer> unsortedValues, ArrayList<AnimationScenarioItem> animationioList) {
        Log.e(TAG, "希尔排序");
        int d = unsortedValues.size() / 2;
        while (true) {
            for (int i = 0; i < d; i++) {
                for (int j = i; j + d < unsortedValues.size(); j += d) {
                    int temp;
                    if (unsortedValues.get(j) > unsortedValues.get(j + d)) {
                        temp = unsortedValues.get(j);
                        unsortedValues.set(j, unsortedValues.get(j + d));
                        unsortedValues.set(j + d, temp);
                        animationioList.add(new AnimationScenarioItem(true, j, j + d, false));

                    } else {
                        animationioList.add(new AnimationScenarioItem(false, j, j + d, false));
                    }
                }
            }
            if (d == 1) {
                break;
            }
            d--;
        }
    }

    /*
     * 堆排序(从小到大)
     *
     * 参数说明：
     *     a -- 待排序的数组
     *     n -- 数组的长度
     */
    // 堆排序代码实现 https://www.cnblogs.com/Java3y/p/8639937.html
    public static void heapSort(ArrayList<Integer> unsortedValues, int n, ArrayList<AnimationScenarioItem> animationioList) {
        int i, tmp;
        // 从(n/2-1) --> 0逐次遍历。遍历之后，得到的数组实际上是一个(最大)二叉堆。
        for (i = n / 2 - 1; i >= 0; i--) {
            maxHeapDown(unsortedValues, i, n - 1, animationioList);
        }
        // 从最后一个元素开始对序列进行调整，不断的缩小调整的范围直到第一个元素
        for (i = n - 1; i > 0; i--) {
            // 交换a[0]和a[i]。交换后，a[i]是a[0...i]中最大的。
            tmp = unsortedValues.get(0);
            unsortedValues.set(0, unsortedValues.get(i));
            unsortedValues.set(i, tmp);
            animationioList.add(new AnimationScenarioItem(true, 0, i, true));
            // 调整a[0...i-1]，使得a[0...i-1]仍然是一个最大堆。
            // 即，保证a[i-1]是a[0...i-1]中的最大值。
            maxHeapDown(unsortedValues, 0, i - 1, animationioList);
        }
    }

    private static void maxHeapDown(ArrayList<Integer> unsortedValues, int start, int end, ArrayList<AnimationScenarioItem> animationioList) {
        int c = start; // 当前(current)节点的位置
        int l = 2 * c + 1; // 左(left)孩子的位置
        int tmp = unsortedValues.get(c); // 当前(current)节点的大小
        for (; l <= end; c = l, l = 2 * l + 1) {
            // "l"是左孩子，"l+1"是右孩子
            if (l < end && unsortedValues.get(l) < unsortedValues.get(l + 1))
                l++; // 左右两孩子中选择较大者，即m_heap[l+1]
            if (tmp >= unsortedValues.get(l))
                break; // 调整结束
            else { // 交换值
                unsortedValues.set(c, unsortedValues.get(l));
                unsortedValues.set(l, tmp);
                animationioList.add(new AnimationScenarioItem(true, c, l, false));
            }
        }
    }

    public static void logList(ArrayList<Integer> unsortedValues) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < unsortedValues.size(); i++) {
            stringBuffer.append(unsortedValues.get(i) + ",");
        }
        Log.e(TAG, "一趟排序后:" + stringBuffer);
    }
}
