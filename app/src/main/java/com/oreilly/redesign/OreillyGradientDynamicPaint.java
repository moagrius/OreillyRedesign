package com.oreilly.redesign;

import android.graphics.Shader;

public class OreillyGradientDynamicPaint extends OreillyGradientPaint {

  @Override
  public Shader getShader(float width, float height) {
    return new OreillyGradientDynamic(width, height);
  }

}
