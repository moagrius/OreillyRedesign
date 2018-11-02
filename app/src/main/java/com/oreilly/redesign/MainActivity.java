package com.oreilly.redesign;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    DrawerLayout drawerLayout = findViewById(R.id.drawerlayout);
    ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
        this,
        drawerLayout,
        toolbar,
        R.string.navigation_open,
        R.string.navigation_close
    );
    drawerLayout.addDrawerListener(actionBarDrawerToggle);
    actionBarDrawerToggle.syncState();

  }
}
