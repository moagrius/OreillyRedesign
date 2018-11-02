package com.oreilly.redesign;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.FrameLayout;

public class OreillyToolbarWrapper extends FrameLayout {

  // the logo is drawn off screen a little, this is a best approximation looking at how the graphic was constructed
  private static final int OFFSET_LEFT_PIXELS = -2;
  private static final int OFFSET_TOP_PIXELS = -17;
  private static final int CROP_TOP_PIXELS = -21;

  // we can populate these until construction because we need the density
  private float mOffsetX;
  private float mOffsetY;

  private OreillyLogoPath mLogo = new OreillyLogoPath();
  private OreillyGradientDynamicPaint mGradient = new OreillyGradientDynamicPaint();

  public OreillyToolbarWrapper(Context context) {
    this(context, null);
  }

  public OreillyToolbarWrapper(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public OreillyToolbarWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setBackgroundColor(Color.TRANSPARENT);
    DisplayMetrics metrics = context.getResources().getDisplayMetrics();
    mOffsetX = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, OFFSET_LEFT_PIXELS, metrics);
    mOffsetY = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, OFFSET_TOP_PIXELS + CROP_TOP_PIXELS, metrics);
  }

  public void reconstructShaders() {
    mGradient.update(getWidth(), getHeight());
  }

  public void reconstructPaths() {
    mLogo.setWidth(getWidth());
    // must call offset _after_ each call to setWidth, because we reset internally
    mLogo.offset(mOffsetX, mOffsetY);
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    super.onLayout(changed, l, t, r, b);
    reconstructShaders();
    reconstructPaths();
    invalidate();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    mGradient.draw(canvas);
    mLogo.draw(canvas);
    super.onDraw(canvas);
  }

}
