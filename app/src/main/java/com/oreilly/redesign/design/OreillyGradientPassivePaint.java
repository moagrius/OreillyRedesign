package com.oreilly.redesign.design;

import android.graphics.Shader;

public class OreillyGradientPassivePaint extends OreillyGradientPaint {

  @Override
  public Shader getShader(float width, float height) {
    return new OreillyGradientPassive(width, height);
  }

}
