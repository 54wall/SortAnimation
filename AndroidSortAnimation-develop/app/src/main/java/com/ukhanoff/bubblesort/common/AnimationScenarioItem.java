package com.ukhanoff.bubblesort.common;

/**
 * Holds data about what kind of animation should be shown.
 */

public class AnimationScenarioItem {

    private boolean isShouldBeSwapped;
    private int firstItemPosition;

    private boolean isFinalPlace;

    public AnimationScenarioItem(boolean isShouldBeSwapped, int itemPosition, boolean isFinalPlace) {
        this.isShouldBeSwapped = isShouldBeSwapped;
        this.firstItemPosition = itemPosition;
        this.isFinalPlace = isFinalPlace;
    }

    public boolean isShouldBeSwapped() {
        return isShouldBeSwapped;
    }

    public int getAnimationViewItemPosition() {
        return firstItemPosition;
    }

    public boolean isFinalPlace() {
        return isFinalPlace;
    }

}
