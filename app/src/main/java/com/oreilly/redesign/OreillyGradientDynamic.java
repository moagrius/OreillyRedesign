package com.oreilly.redesign;

import android.graphics.LinearGradient;
import android.graphics.Shader;

public class OreillyGradientDynamic extends LinearGradient {

  // the gradient is efcc01 at 0%, becoming ee0000 at 50% and continuing that color to 100%
  private static final int[] COLORS = { 0xFFEFCC01, 0xFFEE0000, 0xFFEE0000 };
  private static final float[] POSITIONS = { 0f, 0.5f, 1f };

  public OreillyGradientDynamic(float width, float height) {
    super(0, 0, width, height, COLORS, POSITIONS, Shader.TileMode.CLAMP);
  }

}
