package com.oreilly.redesign;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;

public class OreillyGradientDynamicPaint extends Paint {

  private float mWidth;
  private float mHeight;

  public void update(float width, float height) {
    mWidth = width;
    mHeight = height;
    LinearGradient gradient = new OreillyGradientDynamic(width, height);
    setShader(gradient);
  }

  public void draw(Canvas canvas) {
    canvas.drawRect(0, 0, mWidth, mHeight, this);
  }

}
