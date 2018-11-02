package com.oreilly.redesign;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * document is 2132x1800 (1.184)
 * pixels are 1982x1628 (1.22) or 93% of pixels
 * the "O" is 1628x1628 (square)
 * the dot is 384x384 (square)
 * the stroke is 260
 * inner radius is 1109, which is 1628 - (260 * 2)
 * the dot's x position is equal to the O's right position - 24, so 1604
 *
 * these are all divisible by 4, so let's do that...
 *
 * pixels are 496x407
 * the "O" is 407x407 (square)
 * the dot is 96x96 (square)
 * the stroke is 65
 * the dot's x is 401
 *
 * let's try to divide these by 8...  some small cheating required
 *
 * document is 67
 * pixels are 62x51
 * the "O" is 51x51
 * the dot is 12
 * the stroke is 8
 * the dot's x is 50
 *
 *
 * in the mockup, the toolbar is 360
 * the dot is x:262, width: 62
 * so the right edge is at 324 (262 + 62)
 * the difference is 36, which is 10% of total width
 * which means the graphic occupies 90% of total width
 * the stroke is 16% of the width of the O
 * the 0's left is 0, right is 267, so 74%
 * the dot's left is 261, so 72.5%
 * the dot's right is 324, so 90%
 * the dot's size is then 17.5%
 *
 * the toolbar in inspect mode is 21 pixels taller than the one in preview mode
 * with the top cropped, so everything is 21px farther up
 */
public class OreillyToolbar extends Toolbar {

  /**
   * These are all constants and not configurable
   * because no one should be configuring the drawing
   * values of the brand.
   */

  private static final int LOGO_COLOR = 0x2FFFFFFF;  // 12% is 0x1EFFFFFF, but that's barely visible

  // the gradient is efcc01 at 0%, becoming ee0000 at 50% and continuing that color to 100%
  private static final int[] COLORS = { 0xFFEFCC01, 0xFFEE0000, 0xFFEE0000 };
  private static final float[] POSITIONS = { 0f, 0.5f, 1f };

  // the logo is drawn off screen a little, this is a best approximation looking at how the graphic was constructed
  private static final int OFFSET_LEFT_PIXELS = -2;
  private static final int OFFSET_TOP_PIXELS = -17;
  private static final int CROP_TOP_PIXELS = -21;

  // all sizes are relative to the available drawing width
  private static final float O_SIZE_MULTIPLIER = 0.75f;
  private static final float O_STROKE_MULTIPLIER = 0.16f;
  private static final float DOT_SIZE_MULTIPLIER = 0.175f;
  private static final float DOT_LEFT_MULTIPLIER = 0.74f;

  // allocate memory for all of out drawing objects now for smooth drawing
  private Path mOuterOPath = new Path();
  private Path mInnerOPath = new Path();

  private RectF mOuterOval = new RectF();
  private RectF mInnerOval = new RectF();

  private Paint mGradientPaint = new Paint();
  private Paint mFillPaint = new Paint();
  {
    mGradientPaint.setStyle(Paint.Style.FILL);
    mFillPaint.setStyle(Paint.Style.FILL);
    mFillPaint.setColor(LOGO_COLOR);
  }

  // we can populate these until construction because we need the density
  private float mOffsetX;
  private float mOffsetY;

  // layout happens less frequently than drawing, so let's compute and store those values duringn the former
  private float mDotRadius;
  private float mDotLeft;

  public OreillyToolbar(Context context) {
    this(context, null);
  }

  public OreillyToolbar(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public OreillyToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    DisplayMetrics metrics = context.getResources().getDisplayMetrics();
    mOffsetX = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, OFFSET_LEFT_PIXELS, metrics);
    mOffsetY = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, OFFSET_TOP_PIXELS + CROP_TOP_PIXELS, metrics);
  }

  public void reconstructShaders() {
    LinearGradient gradient = new LinearGradient(0, 0, getWidth(), getHeight(), COLORS, POSITIONS, Shader.TileMode.CLAMP);
    mGradientPaint.setShader(gradient);
  }

  public void reconstructPaths() {
    // draw an oval and punch a whole, so stroke and box models don't make a mess out of our math
    float outerSize = getWidth() * O_SIZE_MULTIPLIER;
    float strokeSize = outerSize * O_STROKE_MULTIPLIER;
    float innerSize = outerSize - (strokeSize * 2);

    // this is the oval-containing-path that will be drawn
    mOuterOval.set(mOffsetX, mOffsetY, outerSize, outerSize);
    mOuterOPath.reset();
    mOuterOPath.addOval(mOuterOval, Path.Direction.CW);

    // this is the oval that will be removed
    float innerX = mOffsetX + strokeSize;
    float innerY = mOffsetY + strokeSize;
    mInnerOval.set(innerX, innerY, innerX + innerSize, innerY + innerSize);
    mInnerOPath.reset();
    mInnerOPath.addOval(mInnerOval, Path.Direction.CW);

    // remove the inner path from the outer path
    mOuterOPath.op(mInnerOPath, Path.Op.DIFFERENCE);
  }

  private void recomputeComponentSizes() {
    mDotRadius = getWidth() * DOT_SIZE_MULTIPLIER * 0.5f;
    mDotLeft = getWidth() * DOT_LEFT_MULTIPLIER;
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    super.onLayout(changed, l, t, r, b);
    reconstructShaders();
    reconstructPaths();
    recomputeComponentSizes();
    invalidate();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    // draw gradient across full width
    canvas.drawRect(0, 0, getWidth(), getHeight(), mGradientPaint);
    // draw circle
    canvas.drawPath(mOuterOPath, mFillPaint);
    // draw dot
    canvas.drawCircle(mDotLeft + mDotRadius + mOffsetX, mDotRadius + mOffsetY, mDotRadius, mFillPaint);
    // draw everything else
    super.onDraw(canvas);
  }

}
