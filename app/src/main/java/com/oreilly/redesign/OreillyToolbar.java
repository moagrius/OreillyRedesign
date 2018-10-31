package com.oreilly.redesign;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
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
 * let's try to divide these by 8...
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
 */
public class OreillyToolbar extends Toolbar {

  private static final int LOGO_COLOR = 0x8FFFFFFF;
  private static final int[] COLORS = { 0xFFEFCC01, 0xFFEE0000, 0xFFEE0000 };
  private static final float[] POSITIONS = { 0f, 0.5f, 1f };

  // the logo is drawn off screen a little
  private static final int OFFSET_X_PIXELS = -2;
  private static final int OFFSET_Y_PIXELS = -17;

  private static final float O_SIZE_MULTIPLIER = 0.75f;
  private static final float O_STROKE_MULTIPLER = 0.16f;
  private static final float DOT_SIZE_MULTIPLIER = 0.175f;
  private static final float DOT_LEFT_MULTIPLER = 0.74f;

  private Paint mGradientPaint = new Paint();
  private Paint mLogoStrokePaint = new Paint();
  private Paint mLogoFillPaint = new Paint();
  {
    mGradientPaint.setStyle(Paint.Style.FILL);
    mLogoStrokePaint.setStyle(Paint.Style.STROKE);
    mLogoFillPaint.setStyle(Paint.Style.FILL);
    mLogoStrokePaint.setColor(LOGO_COLOR);
    mLogoFillPaint.setColor(LOGO_COLOR);
  }

  private float mOffsetX;
  private float mOffsetY;

  public OreillyToolbar(Context context) {
    this(context, null);
  }

  public OreillyToolbar(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public OreillyToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setBackgroundColor(Color.TRANSPARENT);
    DisplayMetrics metrics = context.getResources().getDisplayMetrics();
    mOffsetX = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, OFFSET_X_PIXELS, metrics);
    mOffsetY = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, OFFSET_Y_PIXELS, metrics);
  }

  public void reconstructShaders() {
    LinearGradient gradient = new LinearGradient(0, 0, getWidth(), getHeight(), COLORS, POSITIONS, Shader.TileMode.CLAMP);
    mGradientPaint.setShader(gradient);
    invalidate();
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    super.onLayout(changed, l, t, r, b);
    reconstructShaders();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    Log.d("RD", "width=" + getWidth());
    // draw gradient across full width
    canvas.drawRect(0, 0, getWidth(), getHeight(), mGradientPaint);
    // draw logo parts
    float oSize = getWidth() * O_SIZE_MULTIPLIER;
    Log.d("RD", "o size=" + oSize);
    float oStroke = oSize * O_STROKE_MULTIPLER;
    mLogoStrokePaint.setStrokeWidth(oStroke);
    float oRadius = (oSize - (oStroke * 2)) * 0.5f;
    Log.d("RD", "o radius=" + oRadius);
    float dotSize = getWidth() * DOT_SIZE_MULTIPLIER;
    float dotLeft = getWidth() * DOT_LEFT_MULTIPLER;
    float dotRadius = dotSize * 0.5f;
    canvas.drawCircle(oRadius + mOffsetX, oRadius + mOffsetY, oRadius, mLogoStrokePaint);
    canvas.drawCircle(dotLeft + dotRadius + mOffsetX, dotRadius + mOffsetY, dotRadius, mLogoFillPaint);
    // draw everything else
    super.onDraw(canvas);
  }

}
