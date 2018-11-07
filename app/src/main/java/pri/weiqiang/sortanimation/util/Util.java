package pri.weiqiang.sortanimation.util;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Typical android helpful staff lives here.
 */

public class Util {

    public static int dpToPx(Context context, int sizeInDp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (sizeInDp * scale + 0.5f);
    }

    public static ArrayList<Integer> convertToIntArray(String input) {
        ArrayList<Integer> integerList = new ArrayList<>();
        String[] stringArray = input.split(",");
        int len = stringArray.length;
        for (int i = 0; i < len; i++) {
            if (!TextUtils.isEmpty(stringArray[i])) {
                int height = Integer.parseInt(stringArray[i]);
                integerList.add(height);
            }
        }
        return integerList;

    }

}
