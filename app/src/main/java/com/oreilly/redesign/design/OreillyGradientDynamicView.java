package com.oreilly.redesign.design;

import android.content.Context;
import android.util.AttributeSet;

public class OreillyGradientDynamicView extends OreillyGradientView {

  private OreillyGradientPaint mPaint;

  public OreillyGradientDynamicView(Context context) {
    super(context);
  }

  public OreillyGradientDynamicView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public OreillyGradientDynamicView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  public OreillyGradientPaint getOreillyGradientPaint() {
    if (mPaint == null) {
      mPaint = new OreillyGradientDynamicPaint();
    }
    return mPaint;
  }

}
