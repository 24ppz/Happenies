package com.example.happiness;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private ImageView fingerImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        fingerImageView = findViewById(R.id.iv_finger);
        fingerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLickAnimation();
            }
        });

//        ImageView thumbUpImageView = findViewById(R.animator.thumb_up_animator);
//        Drawable thumbUpDrawable = thumbUpImageView.getDrawable();
//        if (thumbUpDrawable instanceof AnimatedVectorDrawable) {
//            ((AnimatedVectorDrawable) thumbUpDrawable).start();
//        }

    }

    private void startLickAnimation() {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(fingerImageView, "scaleX", 0.8f, 1.2f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(fingerImageView, "scaleY", 0.8f, 1.2f);


        ObjectAnimator colorAnimator = ObjectAnimator.ofArgb
                (fingerImageView, "backgroundColor", 0xFF000000, 0xFFFF0000, 0xFF000000);
        colorAnimator.setEvaluator(new ArgbEvaluator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleX).with(scaleY).with(colorAnimator);
        animatorSet.setDuration(300);
        animatorSet.start();


    }
}