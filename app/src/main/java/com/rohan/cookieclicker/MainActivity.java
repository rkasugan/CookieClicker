package com.rohan.cookieclicker;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView imageViewCookie;
    ImageView imageViewGrandma;
    ImageView imageViewFactory;
    TextView textViewCookieCount;
    TextView textViewPassiveCount;
    TextView textViewGrandmaCost;
    TextView textViewFactoryCost;

    volatile int grandmaCost = 5;     //grandma gives 1 per second, factory gives 10 per second
    volatile int factoryCost = 15;
    volatile int grandmaCount = 0;
    volatile int factoryCount = 0;

    ConstraintLayout constraintLayout;

    volatile int cookieCount = 0;
    volatile int passiveCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageViewCookie = (ImageView)findViewById(R.id.imageViewCookie);
        imageViewGrandma = (ImageView)findViewById(R.id.imageViewGrandma);
        imageViewFactory = (ImageView)findViewById(R.id.imageViewFactory);
        textViewCookieCount = (TextView)findViewById(R.id.textViewCookieCount);
        textViewPassiveCount = (TextView)findViewById(R.id.textViewPassiveCount);
        constraintLayout = (ConstraintLayout)findViewById(R.id.constraintLayout);
        textViewGrandmaCost = (TextView)findViewById(R.id.textViewGrandmaCost);
        textViewFactoryCost = (TextView)findViewById(R.id.textViewFactoryCost);

        textViewCookieCount.setText("" + cookieCount + " cookies");
        textViewPassiveCount.setText("" + passiveCount + " /s");
        textViewGrandmaCost.setText("" + grandmaCost);
        textViewFactoryCost.setText("" + factoryCost);

        final ScaleAnimation scaleAnimation1 = new ScaleAnimation(1.0f, 0.9f, 1.0f, 0.9f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation1.setDuration(100);
        final ScaleAnimation scaleAnimation2 = new ScaleAnimation(0.9f, 1.0f, 0.9f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation2.setDuration(100);

        imageViewCookie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cookieCount ++;
                textViewCookieCount.setText("" + cookieCount + " cookies");
                view.startAnimation(scaleAnimation1);
                view.startAnimation(scaleAnimation2);

                final TextView textViewInCode = new TextView(MainActivity.this);
                textViewInCode.setId(View.generateViewId());
                textViewInCode.setText("+1");

                int x = (int)(Math.random()*100);
                int xRandom = (int)(Math.random()*100);

                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                textViewInCode.setLayoutParams(params);

                constraintLayout.addView(textViewInCode);

                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);

                constraintSet.connect(textViewInCode.getId(), ConstraintSet.TOP, imageViewCookie.getId(), ConstraintSet.TOP);
                constraintSet.connect(textViewInCode.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM);
                constraintSet.connect(textViewInCode.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT);
                constraintSet.connect(textViewInCode.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT);

                constraintSet.applyTo(constraintLayout);

                textViewInCode.setPadding(xRandom, 10, x, 60);
                TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -4.0f, Animation.RELATIVE_TO_SELF, -5.0f);
                translateAnimation.setDuration(1000);

                textViewInCode.startAnimation(translateAnimation);

                translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        constraintLayout.removeView(textViewInCode);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                updateViews();
            }
        });

        imageViewGrandma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (grandmaCost <= cookieCount) {
                    ScaleAnimation scaleAnimation = new ScaleAnimation(1.1f, 0.9f, 1.1f, 0.9f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    scaleAnimation.setDuration(150);
                    imageViewGrandma.startAnimation(scaleAnimation);

                    grandmaCount ++;
                    passiveCount += 1;
                    cookieCount -= grandmaCost;
                    grandmaCost *= 1.5;

                    textViewCookieCount.setText("" + cookieCount + " cookies");
                    textViewPassiveCount.setText("" + passiveCount + " /s");
                    textViewGrandmaCost.setText("" + grandmaCost);

                    updateViews();

                    //add a grandma at bottom
                    ImageView imageViewInCode = new ImageView(MainActivity.this);
                    imageViewInCode.setId(View.generateViewId());
                    imageViewInCode.setImageResource(R.drawable.cookiegrandma);

                    //defining wrap content for any view and assinging it to the textview
                    ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                    imageViewInCode.setLayoutParams(params);

                    //add the new view to the layout
                    constraintLayout.addView(imageViewInCode);

                    //make a copy of the existing contraightlayout so I can add stuff to it
                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(constraintLayout);

                    //constrain the sides of the new textview to the sides of the old textview and overall constraintlayout
                    constraintSet.connect(imageViewInCode.getId(), ConstraintSet.TOP, imageViewCookie.getId(), ConstraintSet.BOTTOM);
                    constraintSet.connect(imageViewInCode.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM);
                    constraintSet.connect(imageViewInCode.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT);
                    constraintSet.connect(imageViewInCode.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT);

                    constraintSet.setVerticalBias(imageViewInCode.getId(), (float)(Math.random()));
                    constraintSet.setHorizontalBias(imageViewInCode.getId(), (float)(Math.random()/2.5));

                    imageViewInCode.startAnimation(scaleAnimation);

                    constraintSet.applyTo(constraintLayout);

                    imageViewInCode.getLayoutParams().width = 100;
                }
            }
        });
        imageViewFactory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (factoryCost <= cookieCount) {
                    ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.9f, 1.0f, 0.9f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    scaleAnimation.setDuration(100);
                    imageViewFactory.startAnimation(scaleAnimation);

                    factoryCount ++;
                    passiveCount += 10;
                    cookieCount -= factoryCost;
                    factoryCost *= 2;

                    textViewCookieCount.setText("" + cookieCount + " cookies");
                    textViewPassiveCount.setText("" + passiveCount + " /s");
                    textViewFactoryCost.setText("" + factoryCost);

                    updateViews();

                    //add a factory at bottom
                    ImageView imageViewInCode = new ImageView(MainActivity.this);
                    imageViewInCode.setId(View.generateViewId());
                    imageViewInCode.setImageResource(R.drawable.cookiefactory);

                    //defining wrap content for any view and assinging it to the textview
                    ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                    imageViewInCode.setLayoutParams(params);

                    //add the new view to the layout
                    constraintLayout.addView(imageViewInCode);

                    //make a copy of the existing contraightlayout so I can add stuff to it
                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(constraintLayout);

                    //constrain the sides of the new textview to the sides of the old textview and overall constraintlayout
                    constraintSet.connect(imageViewInCode.getId(), ConstraintSet.TOP, imageViewCookie.getId(), ConstraintSet.BOTTOM);
                    constraintSet.connect(imageViewInCode.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM);
                    constraintSet.connect(imageViewInCode.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT);
                    constraintSet.connect(imageViewInCode.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT);

                    constraintSet.setVerticalBias(imageViewInCode.getId(), (float)(Math.random()));
                    constraintSet.setHorizontalBias(imageViewInCode.getId(), (float)(0.6+(Math.random()/2.5)));

                    imageViewInCode.startAnimation(scaleAnimation);

                    constraintSet.applyTo(constraintLayout);

                    imageViewInCode.getLayoutParams().width = 100;
                }
            }
        });

        final Thread passiveIncome = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    cookieCount += (1*grandmaCount) + (10*factoryCount);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textViewCookieCount.setText("" + cookieCount + " cookies");
                        }
                    });

                    updateViews();

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        passiveIncome.start();
    }

    public void updateViews() {
        final ScaleAnimation scaleAnimation = new ScaleAnimation(1.1f, 0.9f, 1.1f, 0.9f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(150);
        if (grandmaCost > cookieCount) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    imageViewGrandma.setClickable(false);
                    imageViewGrandma.setAlpha(0.15f);
                }
            });
        }
        else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!imageViewGrandma.isClickable()) {
                        imageViewGrandma.setClickable(true);
                        imageViewGrandma.setAlpha(1.0f);
                        imageViewGrandma.startAnimation(scaleAnimation);
                    }
                }
            });
        }
        if (factoryCost > cookieCount) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    imageViewFactory.setClickable(false);
                    imageViewFactory.setAlpha(0.15f);
                }
            });
        }
        else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!imageViewFactory.isClickable()) {
                        imageViewFactory.setClickable(true);
                        imageViewFactory.setAlpha(1.0f);
                        imageViewFactory.startAnimation(scaleAnimation);
                    }
                }
            });
        }
    }
}
