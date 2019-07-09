package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.MainActivity;

public class GuideActivity extends AppCompatActivity {


    private int[] bgImage = {R.mipmap.guide_two, R.mipmap.guide_three, R.mipmap.guide_four, R.mipmap.guide_five};
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
                if (currentIndex <= 3) {
                    relativeLayout.setBackground(getResources().getDrawable(bgImage[currentIndex]));
                    currentIndex++;
                    if (currentIndex == 4) {
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
