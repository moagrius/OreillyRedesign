package com.oreilly.redesign.design;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

public class OreillyGradientLogoPassiveView extends OreillyGradientPassiveView {

  // https://safaribooksonline.invisionapp.com/d/main/#/console/15790672/327769010/inspect
  // the viewport is 360px
  // the logo seems to be to the left by -117px,
  // and extends beyond the right edge by 19px,
  // and below the bottom edge by 92px
  // so the logo total width is 117 + 19 = 136.  360 + 136 = 496, or 138%
  // the logo has the bottom 23% off screen (bottom)
  private static final float LOGO_WIDTH_MULTIPLIER = 1.38f;
  private static final float LOGO_LEFT_MULTIPLIER = -0.325f;
  private static final float LOGO_BOTTOM_MULTIIPLER = 0.23f;  // 92/406

  private OreillyLogoPath mLogo = new OreillyLogoPath();

  public OreillyGradientLogoPassiveView(Context context) {
    this(context, null);
  }

  public OreillyGradientLogoPassiveView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public OreillyGradientLogoPassiveView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  private void reconstructPaths() {
    int width = (int) (getWidth() * LOGO_WIDTH_MULTIPLIER);
    mLogo.setWidth(width);
    // TODO: use height for the bottom offset
    float offsetX = width * LOGO_LEFT_MULTIPLIER;
    float offsetY = (getHeight() - width) + width * LOGO_BOTTOM_MULTIIPLER;
    // must call offset _after_ each call to setWidth, because we reset internally
    mLogo.offset(offsetX, offsetY);
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
