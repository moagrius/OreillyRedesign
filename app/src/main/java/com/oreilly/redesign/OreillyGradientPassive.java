package com.oreilly.redesign;

import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;

public class OreillyGradientPassive extends LinearGradient {

  private static final int START_COLOR = 0xFF16DFDA;
  private static final int END_COLOR = 0xFF0045FF;
  // the gradient is 0045ff at 0%, becoming 16dfda at 50% and continuing that color to 100%
  private static final int[] COLORS = { START_COLOR, END_COLOR, END_COLOR };
  private static final float[] POSITIONS = { 0f, 0.5f, 1f };

  public OreillyGradientPassive(float width, float height) {
    super(0, 0, width, height, COLORS, POSITIONS, Shader.TileMode.CLAMP);
    Matrix matrix = new Matrix();
    matrix.setRotate(45);
    setLocalMatrix(matrix);
  }

}