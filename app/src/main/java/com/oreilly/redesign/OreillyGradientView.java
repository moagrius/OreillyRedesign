package com.oreilly.redesign;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class OreillyGradientView extends FrameLayout {

  private OreillyGradientDynamicPaint mGradient = new OreillyGradientDynamicPaint();

  public OreillyGradientView(Context context) {
    this(context, null);
  }

  public OreillyGradientView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public OreillyGradientView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setBackgroundColor(Color.TRANSPARENT);
  }

  public void reconstructShaders() {
    mGradient.update(getWidth(), getHeight());
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    super.onLayout(changed, l, t, r, b);
    reconstructShaders();
    invalidate();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    mGradient.draw(canvas);
    super.onDraw(canvas);
  }

}
