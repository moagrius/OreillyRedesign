package com.oreilly.redesign.design;

import android.content.Context;
import android.util.AttributeSet;

public class OreillyGradientPassiveView extends OreillyGradientView {

  private OreillyGradientPaint mPaint;

  public OreillyGradientPassiveView(Context context) {
    super(context);
  }

  public OreillyGradientPassiveView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public OreillyGradientPassiveView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  public OreillyGradientPaint getOreillyGradientPaint() {
    if (mPaint == null) {
      mPaint = new OreillyGradientPassivePaint();
    }
    return mPaint;
  }

}
