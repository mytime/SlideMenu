package com.chebao.slidemenu;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.chebao.slidemenu.view.SlideMenu;

/**
 * 未完成： 8 平滑移动
 */
public class MainActivity extends Activity {

    private ImageView btn_back;
    private SlideMenu slideMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        btn_back = (ImageView) findViewById(R.id.btn_back);
        slideMenu = (SlideMenu) findViewById(R.id.slideMenu);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideMenu.switchMenu();
            }
        });
    }
}
