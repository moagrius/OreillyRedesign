package com.oreilly.redesign.design;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public abstract class OreillyGradientView extends FrameLayout {

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

  public abstract OreillyGradientPaint getOreillyGradientPaint();

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    super.onLayout(changed, l, t, r, b);
    getOreillyGradientPaint().update(getWidth(), getHeight());
    invalidate();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    getOreillyGradientPaint().draw(canvas);
    super.onDraw(canvas);
  }

}
