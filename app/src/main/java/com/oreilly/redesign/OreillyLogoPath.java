package com.oreilly.redesign;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

public class OreillyLogoPath extends Path {

  /**
   * 1984 total width (divided by 32 is 62)
   * 1632 width of "O" (divided by 32 is 51) [this is actually 1628 originally, @stephhall ok'ed the fudging]
   * 1600 dot left edge (divided by 32 is 50)
   * 384 dot width (divided by 32 is 12)
   * 256 stroke width (divided by 32 is 8) [this is actually original 261]
   */

  private static final int COLOR = 0x2FFFFFFF;  // 12% is 0x1EFFFFFF, but that's barely visible

  private static final float CIRCLE_SIZE_MULTIPLIER = 0.82f;  // 51/62
  private static final float DOT_LEFT_MULTIPLIER = 0.81f;  // 50/62
  private static final float DOT_SIZE_MULTIPLIER = 0.19f;  // 12/62
  private static final float STROKE_THICKNESS_MULTIPLIER = 0.13f;  // 8/62

  // lazily evaluate - sometimes we may want the Path without calling `draw`
  private Paint mPaint;

  // the value drives all other dimensions
  private int mWidth;

  // oval that represents the dimensions of `this` Path
  private RectF mOval = new RectF();

  // oval that represents the apostrophe "dot"
  private RectF mDotOval = new RectF();

  // oval and path that punches a hole in `this` Path
  private RectF mInnerOval = new RectF();
  private Path mInnerPath = new Path();

  public void draw(Canvas canvas) {
    canvas.drawPath(this, getPaint());
  }

  public int getWidth() {
    return mWidth;
  }

  public void setWidth(int width) {
    mWidth = width;
    reconfigure();
  }

  private Paint getPaint() {
    if (mPaint == null) {
      mPaint = new Paint();
      mPaint.setStyle(Paint.Style.FILL);
      mPaint.setColor(COLOR);
    }
    return mPaint;
  }

  private void reconfigure() {
    // recompute relative dimensions
    float circleSize = mWidth * CIRCLE_SIZE_MULTIPLIER;
    float dotSize = mWidth * DOT_SIZE_MULTIPLIER;
    float dotLeft = mWidth * DOT_LEFT_MULTIPLIER;
    float strokeSize = mWidth * STROKE_THICKNESS_MULTIPLIER;
    float innerRadius = circleSize - strokeSize;

    // define and add the oval that represents the "O"/circle
    mOval.set(0, 0, circleSize, circleSize);
    reset();
    addOval(mOval, Path.Direction.CW);

    // define and add the oval that will be removed
    mInnerOval.set(strokeSize, strokeSize, innerRadius, innerRadius);
    mInnerPath.reset();
    mInnerPath.addOval(mInnerOval, Path.Direction.CW);

    // do the actual removal
    op(mInnerPath, Path.Op.DIFFERENCE);

    // add the apostrophe dot
    mDotOval.set(dotLeft, 0, dotLeft + dotSize, dotSize);
    addOval(mDotOval, Path.Direction.CW);

  }

}
