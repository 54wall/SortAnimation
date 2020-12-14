package pri.weiqiang.sortanimation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import io.noties.markwon.Markwon;
import pri.weiqiang.sortanimation.R;
import pri.weiqiang.sortanimation.algorithm.SortArrayList;
import pri.weiqiang.sortanimation.constant.Constant;
import pri.weiqiang.sortanimation.ui.fragment.SortFragment;

public class CodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int algorithmSelected = intent.getIntExtra(Constant.EXTRA_CODE,Constant.ALGORITHM_PUBBLE);//从intent中读取信息

        setContentView(R.layout.activity_code);
        TextView mTvCode = findViewById(R.id.tv_code);
        final Markwon markwon = Markwon.create(this);
        String code = "null";
        switch (algorithmSelected) {

            case Constant.ALGORITHM_PUBBLE:
                code = "'    " +
                        "\n" +
                        "private static void pubbleSort(int[] a) {\n" +
                        "        System.out.println(\"冒泡排序\");\n" +"\n"+
                        "        int temp;// 记录临时变量\n" +
                        "        int size = a.length;// 数组大小\n" +
                        "        for (int i = 0; i < size - 1; i++) {\n" +
                        "            for (int j = i + 1; j < size; j++) {// 索引不同的两层for循环\n" +
                        "                if (a[i] < a[j]) {// 交互数据从大到小排列顺序 大的放前面\n" +
                        "                    temp = a[i];\n" +
                        "                    a[i] = a[j];\n" +
                        "                    a[j] = temp;\n" +
                        "                }\n" +
                        "            }\n" +
                        "        }\n" +
                        "        for (int q = 0; q < a.length; q++) {\n" +
                        "            System.out.print(a[q] + \",\");\n" +
                        "        }\n" +
                        "    }'";
                break;
            case Constant.ALGORITHM_INSERT:
                code ="   ' " +
                        "\n" +"\n"+
                        "'private static void insertSort(int[] a) {\n" +"\n"+
                        "        System.out.println(\"插入排序\");\n" +
                        "        // 直接插入排序\n" +
                        "        for (int i = 1; i < a.length; i++) {\n" +
                        "            // 待插入元素\n" +
                        "            int temp = a[i];\n" +
                        "            int j;\n" +
                        "            for (j = i - 1; j >= 0; j--) {\n" +
                        "                // 将大于temp的往后移动一位\n" +
                        "                if (a[j] > temp) {\n" +
                        "                    a[j + 1] = a[j];\n" +
                        "                } else {\n" +
                        "                    break;\n" +
                        "                }\n" +
                        "            }\n" +
                        "            a[j + 1] = temp;// 插入进来\n" +
                        "        }\n" +
                        "        for (int q = 0; q < a.length; q++) {\n" +
                        "            System.out.print(a[q] + \",\");\n" +
                        "        }\n" +
                        "        }\n" +
                        "    }'";
                break;
            case Constant.ALGORITHM_SELECT:
                code = "'    " +
                        "\n" +"\n"+
                        "public static void selectSort(int[] a) {\n" +"\n"+
                        "        System.out.println(\"选择排序\");\n" +
                        "        int min;\n" +
                        "        int tmp;\n" +
                        "        for (int i = 0; i < a.length; i++) {\n" +
                        "            min = a[i];\n" +
                        "            for (int j = i; j < a.length; j++) {\n" +
                        "                if (a[j] < min) {\n" +
                        "                    min = a[j];// 最小值\n" +
                        "                    tmp = a[i];\n" +
                        "                    a[i] = a[j];//这么写，更好理解 a[i] = min;//没有前者好理解\n" +
                        "                    a[j] = tmp;\n" +
                        "                }\n" +
                        "            }\n" +
                        "        }\n" +
                        "        for (int q = 0; q < a.length; q++) {\n" +
                        "            System.out.print(a[q] + \",\");\n" +
                        "        }\n" +

                        "    }'";
                break;
            case Constant.ALGORITHM_QUICK:
                code = "'    // 快速排序\n" +
                        "\n" +"\n"+
                        "    private static void quickSort(int[] a, int low, int high) {\n" +"\n"+
                        "        int start = low;\n" +
                        "        int end = high;\n" +
                        "        int key = a[low];\n" +
                        "\n" +
                        "        while (end > start) {\n" +
                        "\n" +
                        "            // 从后往前比较// 如果没有比关键值小的，比较下一个，直到有比关键值小的交换位置，然后又从前往后比较\n" +
                        "            while (end > start && a[end] >= key)\n" +
                        "                end--;\n" +
                        "            if (a[end] <= key) {\n" +
                        "                int temp = a[end];\n" +
                        "                a[end] = a[start];\n" +
                        "                a[start] = temp;\n" +
                        "                System.out.println(\"key:\" + key + \",从后往前比较 end:\" + end + \",a[end]:\" + a[end] + \",start:\" + start + \",a[start]:\" + a[start]);\n" +
                        "                System.out.println(\"---start:\" + start);\n" +
                        "            }\n" +
                        "            // 从前往后比较// 如果没有比关键值大的，比较下一个，直到有比关键值大的交换位置\n" +
                        "            while (end > start && a[start] <= key)\n" +
                        "                start++;\n" +
                        "            if (a[start] >= key) {\n" +
                        "                int temp = a[start];\n" +
                        "                a[start] = a[end];\n" +
                        "                a[end] = temp;\n" +
                        "                System.out.println(\"key:\" + key + \",从前往后比较 end:\" + end + \",a[end]:\" + a[end] + \",start:\" + start + \",a[start]:\" + a[start]);\n" +
                        "                System.out.println(\"---end:\" + end);\n" +
                        "            }\n" +
                        "            // 此时第一次循环比较结束，关键值的位置已经确定了。左边的值都比关键值小，右边的值都比关键值大，但是两边的顺序还有可能是不一样的，进行下面的递归调用\n" +
                        "        }\n" +
                        "        StringBuilder sb = new StringBuilder(\"a6 =\");\n" +
                        "        for (int i = 0; i < a6.length; i++) {\n" +
                        "            sb.append(a6[i] + \",\");\n" +
                        "\n" +
                        "        }\n" +
                        "        System.out.println(\"sb:\" + sb.toString());\n" +
                        "        // 递归\n" +
                        "        if (start > low) {\n" +
                        "            System.out.println(\"**************************************************\");\n" +
                        "            System.out.println(\"迭代******start > low!\" + \",start:\" + start + \",low:\" + low);\n" +
                        "            quickSort(a, low, start - 1);// 左边序列。第一个索引位置到关键值索引-1\n" +
                        "        }\n" +
                        "        if (end < high) {\n" +
                        "            System.out.println(\"**************************************************\");\n" +
                        "            System.out.println(\"迭代*******end < high!\" + \",end:\" + end + \",high:\" + high);\n" +
                        "            quickSort(a, end + 1, high);// 右边序列。从关键值索引+1到最后一个\n" +
                        "        }\n" +
                        "    }'";
                break;
            case Constant.ALGORITHM_HEER:
                code = "'    " +
                        "\n" +"\n"+
                        "private static void heerSort(int[] a) {\n" +"\n"+
                        "        System.out.println(\"希尔排序\");\n" +
                        "        int d = a.length / 2;\n" +
                        "        while (true) {\n" +
                        "            for (int i = 0; i < d; i++) {\n" +
                        "                for (int j = i; j + d < a.length; j += d) {\n" +
                        "                    int temp;\n" +
                        "                    if (a[j] > a[j + d]) {\n" +
                        "                        temp = a[j];\n" +
                        "                        a[j] = a[j + d];\n" +
                        "                        a[j + d] = temp;\n" +
                        "                    }\n" +
                        "                }\n" +
                        "            }\n" +
                        "            if (d == 1) {\n" +
                        "                break;\n" +
                        "            }\n" +
                        "            d--;\n" +
                        "        }\n" +
                        "        for (int anA : a) {\n" +
                        "            System.out.print(anA + \",\");\n" +
                        "        }\n" +
                        "    }'";
                break;
            case Constant.ALGORITHM_HEAP:
                code = "'       " +
                        "\n" +"\n"+
                        " /*\n" +
                        "         * 堆排序(从小到大)\n" +
                        "         *\n" +
                        "         * 参数说明：\n" +
                        "         *     a -- 待排序的数组\n" +
                        "         *     n -- 数组的长度\n" +
                        "         */\n" +
                        "        static void heapSortAsc(int[] a, int n) {\n" +
                        "            int i, tmp;\n" +
                        "\n" +
                        "            // 从(n/2-1) --> 0逐次遍历。遍历之后，得到的数组实际上是一个(最大)二叉堆。\n" +
                        "            for (i = n / 2 - 1; i >= 0; i--)\n" +
                        "                maxHeapDown(a, i, n - 1);\n" +
                        "\n" +
                        "\n" +
                        "            // 从最后一个元素开始对序列进行调整，不断的缩小调整的范围直到第一个元素\n" +
                        "            for (i = n - 1; i > 0; i--) {\n" +
                        "                // 交换a[0]和a[i]。交换后，a[i]是a[0...i]中最大的。\n" +
                        "                tmp = a[0];\n" +
                        "                a[0] = a[i];\n" +
                        "                a[i] = tmp;\n" +
                        "                // 调整a[0...i-1]，使得a[0...i-1]仍然是一个最大堆。\n" +
                        "                // 即，保证a[i-1]是a[0...i-1]中的最大值。\n" +
                        "                maxHeapDown(a, 0, i - 1);\n" +
                        "            }\n" +
                        "        }\n" +
                        "\n" +
                        "        /*\n" +
                        "         * (最小)堆的向下调整算法\n" +
                        "         *\n" +
                        "         * 注：数组实现的堆中，第N个节点的左孩子的索引值是(2N+1)，右孩子的索引是(2N+2)。\n" +
                        "         *     其中，N为数组下标索引值，如数组中第1个数对应的N为0。\n" +
                        "         *\n" +
                        "         * 参数说明：\n" +
                        "         *     a -- 待排序的数组\n" +
                        "         *     start -- 被下调节点的起始位置(一般为0，表示从第1个开始)\n" +
                        "         *     end   -- 截至范围(一般为数组中最后一个元素的索引)\n" +
                        "         */\n" +
                        "        static void minHeapDown(int[] a, int start, int end) {\n" +
                        "            int c = start; // 当前(current)节点的位置\n" +
                        "            int l = 2 * c + 1; // 左(left)孩子的位置\n" +
                        "            int tmp = a[c]; // 当前(current)节点的大小\n" +
                        "\n" +
                        "            for (; l <= end; c = l, l = 2 * l + 1) {\n" +
                        "                // \"l\"是左孩子，\"l+1\"是右孩子\n" +
                        "                if (l < end && a[l] > a[l + 1])\n" +
                        "                    l++; // 左右两孩子中选择较小者\n" +
                        "                if (tmp <= a[l])\n" +
                        "                    break; // 调整结束\n" +
                        "                else { // 交换值\n" +
                        "                    a[c] = a[l];\n" +
                        "                    a[l] = tmp;\n" +
                        "                }\n" +
                        "            }\n" +
                        "        }\n" +
                        "\n" +
                        "        /*\n" +
                        "         * 堆排序(从大到小)\n" +
                        "         *\n" +
                        "         * 参数说明：\n" +
                        "         *     a -- 待排序的数组\n" +
                        "         *     n -- 数组的长度\n" +
                        "         */\n" +
                        "        public static void heapSortDesc(int[] a, int n) {\n" +
                        "            int i, tmp;\n" +
                        "\n" +
                        "            // 从(n/2-1) --> 0逐次遍历每。遍历之后，得到的数组实际上是一个最小堆。\n" +
                        "            for (i = n / 2 - 1; i >= 0; i--)\n" +
                        "                minHeapDown(a, i, n - 1);\n" +
                        "\n" +
                        "            // 从最后一个元素开始对序列进行调整，不断的缩小调整的范围直到第一个元素\n" +
                        "            for (i = n - 1; i > 0; i--) {\n" +
                        "                // 交换a[0]和a[i]。交换后，a[i]是a[0...i]中最小的。\n" +
                        "                tmp = a[0];\n" +
                        "                a[0] = a[i];\n" +
                        "                a[i] = tmp;\n" +
                        "                // 调整a[0...i-1]，使得a[0...i-1]仍然是一个最小堆。\n" +
                        "                // 即，保证a[i-1]是a[0...i-1]中的最小值。\n" +
                        "                minHeapDown(a, 0, i - 1);\n" +
                        "            }\n" +
                        "        }\n" +
                        "\n" +
                        "        public static void sortHeap() {\n" +
                        "            System.out.println(\"堆排序\");\n" +
                        "            int i;\n" +
                        "            int a[] = {1, 2, 4, 3, 0};\n" +
                        "\n" +
                        "            System.out.printf(\"before sort:\");\n" +
                        "            for (i = 0; i < a.length; i++)\n" +
                        "                System.out.printf(\"%d \", a[i]);\n" +
                        "            System.out.printf(\"\\n\");\n" +
                        "\n" +
                        "            heapSortAsc(a, a.length); // 升序排列\n" +
                        "            // heapSortDesc(a, a.length); // 降序排列\n" +
                        "\n" +
                        "            System.out.printf(\"after  sort:\");\n" +
                        "            for (i = 0; i < a.length; i++)\n" +
                        "                System.out.printf(\"%d \", a[i]);\n" +
                        "            System.out.printf(\"\\n\");\n" +
                        "        }\n" +
                        "    }'";
                break;

        }

        markwon.setMarkdown(mTvCode, code);

    }
}
