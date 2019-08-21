package com.oreilly.redesign.design;

import android.graphics.LinearGradient;
import android.graphics.Shader;

public class OreillyGradientDynamic extends LinearGradient {

  private static final int START_COLOR = 0xFFEFCC01;
  private static final int END_COLOR = 0xFFEE0000;
  // the gradient is efcc01 at 0%, becoming ee0000 at 50% and continuing that color to 100%
  private static final int[] COLORS = { START_COLOR, END_COLOR, END_COLOR };
  private static final float[] POSITIONS = { 0f, 0.5f, 1f };

  public OreillyGradientDynamic(float width, float height) {
    super(0, 0, width, height, COLORS, POSITIONS, Shader.TileMode.CLAMP);
  }

}
