package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

public class GuideActivity extends AppCompatActivity {

    private int[] bgImage = {R.mipmap.guide_two, R.mipmap.guide_three, R.mipmap.guide_four};
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.view_guide);
        super.onCreate(savedInstanceState);
        RelativeLayout relativeLayout = findViewById(R.id.rl_parent);

        findViewById(R.id.tv_know).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();
            }
        });

        findViewById(R.id.tv_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentIndex <= 2) {
                    relativeLayout.setBackground(getResources().getDrawable(bgImage[currentIndex]));
                    currentIndex++;
                    if (currentIndex == 3) {
                        findViewById(R.id.tv_know).setVisibility(View.GONE);
                        ((TextView) findViewById(R.id.tv_next)).setText("开始使用易安防");
                    }
                } else {

                    startActivity(new Intent(GuideActivity.this, MainActivity.class));
                    finish();

                }
            }
        });

        findViewById(R.id.ll_remind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
