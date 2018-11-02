package com.oreilly.redesign;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

public abstract class OreillyGradientPaint extends Paint {

  private float mWidth;
  private float mHeight;

  public abstract Shader getShader(float width, float height);

  public void update(float width, float height) {
    mWidth = width;
    mHeight = height;
    Shader shader = getShader(width, height);
    setShader(shader);
  }

  public void draw(Canvas canvas) {
    canvas.drawRect(0, 0, mWidth, mHeight, this);
  }

}
