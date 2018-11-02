package com.oreilly.redesign;

import android.graphics.Path;

public class OreillyLogoPath extends Path {

  /**
   * 1984 total width (divided by 32 is 62)
   * 1632 width of "O" (divided by 32 is 51) [this is actually 1628 originally, @stephhall ok'ed the fudging]
   * 1600 dot left edge (divided by 32 is 50)
   * 384 dot width (divided by 32 is 12)
   * 256 stroke width (divided by 32 is 8) [this is actually original 261]
   */

  private static final float O_SIZE_MULTIPLIER = 0.82f;  // 51/62
  private static final float DOT_LEFT_MULTIPLIER = 0.81f;  // 50/62

  private int mWidth;



  public int getWidth() {
    return mWidth;
  }

  public void setWidth(int width) {
    mWidth = width;
  }

}
