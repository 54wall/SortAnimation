package pri.weiqiang.sortanimation.algorithm;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import pri.weiqiang.sortanimation.common.AnimationScenarioItem;

public class SortArrayList {
    static int[] a8 = {40, 60, 30, 80, 50, 20, 90, 20, 10, 70};
    static int[] a7 = {6, 22, 2, 1, 0, 5, 4};
    static int[] a5 = {22, 3, 2, 1, 0, 5, 4};
    static int[] a4 = {22, 3, 2, 1, 0, 5, 4};
    static int[] a3 = {22, 3, 2, 1, 0, 5, 4};
//    static int[] a = {2, 3, 6, 11};
    static int[] b = {1, 4, 8, 9};
//    static int[] c = new int[a.length + b.length];
    static int[] a1 = {2, 3, 6, 11, 2, 3, 4};
//    static int[] a6 = {5, 4, 3, 1, 0, 2, 6};
    private static String TAG = SortArrayList.class.getSimpleName();

    // 直接在Debug下查看数组变化，特别容易理解
    public static void main(String[] args) {

//        quickSort(a6, 0, a6.length - 1);
//		memeryArray(a, a.length, b, b.length, c);
//		sort(a1, 0, a1.length-1);
//		selectSort(a3);
//		pubbleSort(a4);
//		InsertSort(a5);
//		HeerSort(a7);
//		HeapSort.sortHeap();
        //查找
//		binarySearch(a8);
        System.out.println("FinpubbleSortished……");
    }

    // 希尔排序 https://blog.csdn.net/csdn_aiyang/article/details/73108606
    private static void binarySearch(int[] a) {

        System.out.println("二分法查找");
        int target = 80;
        Arrays.sort(a);
        boolean result = false;
        int min = 0;
        int b = 0;
        int max = a.length - 1;
        while (min <= max) {
            b = (min + max) / 2;
            if (target > a[b]) {
                min = b + 1;
            }
            if (target < a[b]) {
                max = b - 1;
            }
            if (target == a[b]) {
                min++;
                result = true;

            }

        }
        System.out.println("result:" + result + ",b:" + b);
    }





    // 冒泡 https://blog.csdn.net/csdn_aiyang/article/details/73108606
    public static void pubbleSort(ArrayList<Integer> unsortedValues, ArrayList<AnimationScenarioItem> animationioList) {
        Log.e(TAG, "冒泡排序");
        Integer temp;// 记录临时变量
        boolean isLastInLoop;
        int size = unsortedValues.size();// 数组大小
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {// 索引不同的两层for循环
                if (j == unsortedValues.size() - i - 2) {
                    isLastInLoop = true;
                    Log.e(TAG, "j:" + j + " ,Sort isLastInLoop:" + isLastInLoop);
                } else {
                    isLastInLoop = false;
                    Log.e(TAG, "j:" + j + " ,Sort isLastInLoop:" + isLastInLoop);
                }
                if (unsortedValues.get(j + 1) < unsortedValues.get(j)) {// 交互数据从大到小排列顺序 大的放前面
                    temp = unsortedValues.get(j);
                    unsortedValues.set(j, unsortedValues.get(j + 1));
                    unsortedValues.set(j + 1, temp);

                    animationioList.add(new AnimationScenarioItem(true, j, j + 1, isLastInLoop));
                } else {

                    animationioList.add(new AnimationScenarioItem(false, j, j, isLastInLoop));
                }
            }
        }

    }
    // 插入排序 https://blog.csdn.net/csdn_aiyang/article/details/73108606
//    private static void InsertSort(int[] a) {
    public static void insertSort(ArrayList<Integer> unsortedValues, ArrayList<AnimationScenarioItem> animationioList) {
        Log.e(TAG, "插入排序!");
        StringBuffer stringBuffer;
        boolean isLastInLoop = false;
        // 直接插入排序
        for (int i = 1; i < unsortedValues.size(); i++) {
            // 待插入元素
            int temp = unsortedValues.get(i);
            int j;
            for (j = i - 1; j >= 0; j--) {
                if (j == 0) {
                    isLastInLoop = true;
                } else {
                    isLastInLoop = false;
                }
                // 将大于temp的往后移动一位
                if (unsortedValues.get(j) > temp) {
                    unsortedValues.set(j+1,unsortedValues.get(j));
                    animationioList.add(new AnimationScenarioItem(true, j, j+1, isLastInLoop));
                    stringBuffer= new StringBuffer();
                    for (int q = 0; q < unsortedValues.size(); q++) {
                        stringBuffer.append(unsortedValues.get(q)+",");
                    }
                    Log.e(TAG,"151 排序后:"+stringBuffer);
                } else {
                    animationioList.add(new AnimationScenarioItem(false, j, j, isLastInLoop));
                    stringBuffer= new StringBuffer();
                    for (int q = 0; q < unsortedValues.size(); q++) {
                        stringBuffer.append(unsortedValues.get(q)+",");
                    }
                    Log.e(TAG,"158 排序后:"+stringBuffer);
                    break;

                }
            }
                unsortedValues.set(j + 1, temp);// 插入进来
                Log.e(TAG, "168 AnimationScenarioItem j:" + j);
                animationioList.add(new AnimationScenarioItem(true, j+1, j + 1, isLastInLoop));//务必全部是j+1,因为j最后为-1,
                stringBuffer= new StringBuffer();
                for (int q = 0; q < unsortedValues.size(); q++) {
                stringBuffer.append(unsortedValues.get(q)+",");
                 }
                Log.e(TAG,"174 排序后:"+stringBuffer);
//            }
        }
        stringBuffer= new StringBuffer();
        for (int q = 0; q < unsortedValues.size(); q++) {
            stringBuffer.append(unsortedValues.get(q)+",");
        }
        Log.e(TAG,"Last 排序后:"+stringBuffer);
    }


    // 选择排序 https://blog.csdn.net/csdn_aiyang/article/details/73108606
    public static void selectSort(ArrayList<Integer> unsortedValues, ArrayList<AnimationScenarioItem> animationioList) {
        Log.e(TAG, "选择排序");
        int min;
        int tmp = 0;
        boolean isLastInLoop;
        for (int i = 0; i < unsortedValues.size(); i++) {
            min = unsortedValues.get(i);
            for (int j = i + 1; j < unsortedValues.size(); j++) {

                if (j == unsortedValues.size() - 1) {
                    isLastInLoop = true;
                } else {
                    isLastInLoop = false;
                }
                if (unsortedValues.get(j) < min) {
                    min = unsortedValues.get(j);// 最小值
                    tmp = unsortedValues.get(i);
                    unsortedValues.set(i, min);
                    unsortedValues.set(j, tmp);
                    animationioList.add(new AnimationScenarioItem(true, i, j, isLastInLoop));
                } else {
                    animationioList.add(new AnimationScenarioItem(false, i, j, isLastInLoop));
                }

            }
        }
        for (int q = 0; q < unsortedValues.size(); q++) {
            Log.e(TAG, "Select Sort:" + unsortedValues.get(q));
        }

    }

    // 快速排序
    public static void quickSort(ArrayList<Integer> unsortedValues,int low,int high, ArrayList<AnimationScenarioItem> animationioList) {

        int start = low;
        int end = high;
        int key = unsortedValues.get(start);
        boolean isLastInLoop = false;
        while (end > start) {

            // 从后往前比较// 如果没有比关键值小的，比较下一个，直到有比关键值小的交换位置，然后又从前往后比较
            while (end > start && unsortedValues.get(end) >= key) {
                end--;
                animationioList.add(new AnimationScenarioItem(false, end, end, isLastInLoop));
            }
            if (unsortedValues.get(end) <= key) {
                int temp = unsortedValues.get(end);
                unsortedValues.set(end,unsortedValues.get(start));
                unsortedValues.set(start,temp);
                animationioList.add(new AnimationScenarioItem(true, end, start, isLastInLoop));
                Log.e(TAG,"key:" + key + ",从后往前比较 end:" + end + ",unsortedValues.get(end):" + unsortedValues.get(end) +
                        ",start:" + start + ",unsortedValues.get(start):" + unsortedValues.get(start));
                Log.e(TAG,"---start:" + start);
            }
            // 从前往后比较// 如果没有比关键值大的，比较下一个，直到有比关键值大的交换位置
            while (end > start && unsortedValues.get(start) <= key) {
                start++;
                animationioList.add(new AnimationScenarioItem(false, start, start, isLastInLoop));
            }
            if (unsortedValues.get(start) >= key) {
                int temp = unsortedValues.get(start);
                unsortedValues.set(start,unsortedValues.get(end));
                unsortedValues.set(end,temp);
                animationioList.add(new AnimationScenarioItem(true, start, end, isLastInLoop));
                Log.e(TAG,"key:" + key + ",从前往后比较 end:" + end + ",unsortedValues.get(end):" + unsortedValues.get(end) + ",start:"
                        + start + ",unsortedValues.get(start):" + unsortedValues.get(start));
                Log.e(TAG,"---end:" + end);
            }
            // 此时第一次循环比较结束，关键值的位置已经确定了。左边的值都比关键值小，右边的值都比关键值大，但是两边的顺序还有可能是不一样的，进行下面的递归调用
        }

        // 递归
        if (start > low) {
            Log.e(TAG,"**************************************************");
            Log.e(TAG,"迭代******start > low!" + ",start:" + start + ",low:" + low);
            quickSort(unsortedValues, low, start - 1,animationioList);// 左边序列。第一个索引位置到关键值索引-1
        }
        if (end < high) {
            Log.e(TAG,"**************************************************");
            Log.e(TAG,"迭代*******end < high!" + ",end:" + end + ",high:" + high);
            quickSort(unsortedValues, end + 1, high,animationioList);// 右边序列。从关键值索引+1到最后一个
        }
    }


    // 归并算法 https://www.cnblogs.com/of-fanruice/p/7678801.html
    public static void mergeSort(ArrayList<Integer> unsortedValues,int low,int high, ArrayList<AnimationScenarioItem> animationioList) {
        Log.e(TAG,"归并排序!");
        int mid = (low + high) / 2;
        Log.e(TAG,"mid mid:"+mid);
        if (low < high) {
            mergeSort(unsortedValues, low, mid,animationioList);
            mergeSort(unsortedValues, mid + 1, high,animationioList);
            // 左右归并
            merge(unsortedValues, low, mid, high,animationioList);
        }
    }

    public static void merge(ArrayList<Integer> unsortedValues,int low,int mid,int high, ArrayList<AnimationScenarioItem> animationioList) {
        ArrayList<Integer> temp = new ArrayList<>();
        Log.e(TAG,"temp.size():"+temp.size());
        int i = low;
        int j = mid + 1;
        int k = 0;
        // 把较小的数先移到新数组中
        while (i <= mid && j <= high) {
            Log.e(TAG,"子归并merge i:"+i+",j:"+j);
            if (unsortedValues.get(i) < unsortedValues.get(j)) {
                temp.add(k++,unsortedValues.get(i++));
            } else {
                temp.add(k++,unsortedValues.get(j++));
            }
        }
        // 把左边剩余的数移入数组
        while (i <= mid) {
            temp.add(k++,unsortedValues.get(i++));
        }
        // 把右边边剩余的数移入数组
        while (j <= high) {
            temp.add(k++,unsortedValues.get(j++));
        }
        // 把新数组中的数覆盖nums数组
        for (int x = 0; x < temp.size(); x++) {
            unsortedValues.set(x+low,temp.get(x));
        }
        StringBuffer stringBuffer= new StringBuffer();
        for (int q = 0; q < unsortedValues.size(); q++) {
            stringBuffer.append(unsortedValues.get(q)+",");
        }
        Log.e(TAG,"287 归并排序后:"+stringBuffer);
    }

    // 希尔排序 https://blog.csdn.net/csdn_aiyang/article/details/73108606
    public static void heerSort(ArrayList<Integer> unsortedValues, ArrayList<AnimationScenarioItem> animationioList) {
        Log.e(TAG,"希尔排序");
        boolean isLastInLoop = false;
        int d = unsortedValues.size() / 2;
        while (true) {
            for (int i = 0; i < d; i++) {
                for (int j = i; j + d < unsortedValues.size(); j += d) {
                    int temp;
                    if (unsortedValues.get(j) > unsortedValues.get(j + d)) {
                        temp = unsortedValues.get(j);
                        unsortedValues.set(j,unsortedValues.get(j+d));
                        unsortedValues.set(j+d,temp);
                        animationioList.add(new AnimationScenarioItem(true, j, j+d, isLastInLoop));

                    }else {
                        animationioList.add(new AnimationScenarioItem(false, j, j, isLastInLoop));
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
    public static void heapSort(ArrayList<Integer> unsortedValues,int n, ArrayList<AnimationScenarioItem> animationioList) {
        int i, tmp;

        // 从(n/2-1) --> 0逐次遍历。遍历之后，得到的数组实际上是一个(最大)二叉堆。
        for (i = n / 2 - 1; i >= 0; i--)
            maxHeapDown(unsortedValues, i, n - 1);


        // 从最后一个元素开始对序列进行调整，不断的缩小调整的范围直到第一个元素
        for (i = n - 1; i > 0; i--) {
            // 交换a[0]和a[i]。交换后，a[i]是a[0...i]中最大的。
            tmp = unsortedValues.get(0);
            unsortedValues.set(0,unsortedValues.get(i));
//            a[0] = a[i];
//            a[i] = tmp;
            unsortedValues.set(i,tmp);
            // 调整a[0...i-1]，使得a[0...i-1]仍然是一个最大堆。
            // 即，保证a[i-1]是a[0...i-1]中的最大值。
            maxHeapDown(unsortedValues, 0, i - 1);
        }
    }

    public static void maxHeapDown(ArrayList<Integer> unsortedValues, int start, int end) {
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
//                a[c] = a[l];
                unsortedValues.set(c,unsortedValues.get(l));
//                a[l] = tmp;
                unsortedValues.set(l,tmp);
            }
        }

    }


}
