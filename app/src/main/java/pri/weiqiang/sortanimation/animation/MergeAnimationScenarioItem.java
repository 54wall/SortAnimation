package pri.weiqiang.sortanimation.animation;

/**
 * 归并排序动画 单元类
 */
public class MergeAnimationScenarioItem {


    private int originalPosition;
    private int tempPosition;
    private boolean isMerge;

    /**
     * @param originalPosition 在原数组中的位置
     * @param tempPosition     在新生成的数组中的位置
     * @param isMerge          是否是处于将新生成的数组放置回原数组的那个步骤
     */
    public MergeAnimationScenarioItem(int originalPosition, int tempPosition, boolean isMerge) {

        this.originalPosition = originalPosition;
        this.tempPosition = tempPosition;
        this.isMerge = isMerge;
    }


    public int getOriginalPosition() {
        return originalPosition;
    }

    public int getTempPosition() {
        return tempPosition;
    }

    public boolean isMerge() {
        return isMerge;
    }
}
