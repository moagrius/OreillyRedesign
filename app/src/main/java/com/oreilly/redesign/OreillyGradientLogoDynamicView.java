package com.oreilly.redesign;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * This can be used to wrap a Toolbar, or as a sibling
 */
public class OreillyGradientLogoDynamicView extends OreillyGradientDynamicView {

  // TODO: express these are percentages, rather than pixel values
  // the logo is drawn off screen a little, this is a best approximation looking at how the graphic was constructed
  private static final int OFFSET_LEFT_PIXELS = -2;
  private static final int OFFSET_TOP_PIXELS = -17;
  private static final int CROP_TOP_PIXELS = -21;

  // most toolbars draw the logo across the left-most 90% of available space
  private static final float LOGO_FILL_PERCENT = 0.9f;

  // we can populate these until construction because we need the density
  private float mOffsetX;
  private float mOffsetY;

  private OreillyLogoPath mLogo = new OreillyLogoPath();

  public OreillyGradientLogoDynamicView(Context context) {
    this(context, null);
  }

  public OreillyGradientLogoDynamicView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public OreillyGradientLogoDynamicView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    DisplayMetrics metrics = context.getResources().getDisplayMetrics();
    mOffsetX = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, OFFSET_LEFT_PIXELS, metrics);
    mOffsetY = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, OFFSET_TOP_PIXELS + CROP_TOP_PIXELS, metrics);
  }

  private void reconstructPaths() {
    mLogo.setWidth((int) (getWidth() * LOGO_FILL_PERCENT));
    // must call offset _after_ each call to setWidth, because we reset internally
    mLogo.offset(mOffsetX, mOffsetY);
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    super.onLayout(changed, l, t, r, b);
    reconstructPaths();
    invalidate();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    mLogo.draw(canvas);
  }

}
