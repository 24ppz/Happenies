package com.example.happiness;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ImageView thumbButton;
    private RelativeLayout container;
    private boolean isFirstClick = true;
    private Handler handler = new Handler();
    private Runnable showDialogRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        thumbButton = findViewById(R.id.thumb_button);
        container = findViewById(R.id.container);

        thumbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 按压动画
                ScaleAnimation scaleAnimation = new ScaleAnimation(
                        1.0f, 0.9f, 1.0f, 0.9f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(100);
                scaleAnimation.setRepeatCount(1);
                scaleAnimation.setRepeatMode(Animation.REVERSE);
                thumbButton.startAnimation(scaleAnimation);

                // 改变颜色为抖音红
                thumbButton.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.douyin_red));

                // 创建冒泡的大拇指特效
                createThumbBubbles();

                // 第一次点击后20秒显示对话框
                if (isFirstClick) {
                    isFirstClick = false;
                    showDialogRunnable = new Runnable() {
                        @Override
                        public void run() {
                            showSuccessDialog();
                        }
                    };
                    handler.postDelayed(showDialogRunnable, 20000);
                }
            }
        });
    }

    private void createThumbBubbles() {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            final ImageView thumb = new ImageView(this);
            thumb.setImageResource(R.drawable.ic_thumb_up);
            thumb.setColorFilter(getRandomColor());

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    80, 80);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            params.bottomMargin = 300;

            thumb.setLayoutParams(params);
            container.addView(thumb);

            // 动画
            thumb.animate()
                    .translationYBy(-1000)
                    .translationX(random.nextInt(400) - 200)
                    .alpha(0)
                    .setDuration(2000)
                    .withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            container.removeView(thumb);
                        }
                    })
                    .start();
        }
    }

    private int getRandomColor() {
        Random random = new Random();
        return Color.rgb(
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256)
        );
    }

    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("助力成功")
                .setMessage("感谢您的助力！")
                .setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null && showDialogRunnable != null) {
            handler.removeCallbacks(showDialogRunnable);
        }
    }
}