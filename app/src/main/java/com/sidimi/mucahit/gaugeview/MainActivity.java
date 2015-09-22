package com.sidimi.mucahit.gaugeview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private GaugeView gaugeView;
    private GaugeView gaugeView1;
    private GaugeView gaugeView2;
    private GaugeView gaugeView3;
    private float degree = -225;
    private float sweepAngleControl = 0;
    private float sweepAngleFirstChart = 1;
    private float sweepAngleSecondChart = 1;
    private float sweepAngleThirdChart = 1;
    private boolean isInProgress = false;
    private boolean resetMode = false;
    private boolean canReset = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gaugeView = (GaugeView) findViewById(R.id.gaugeView);
        gaugeView1 = (GaugeView) findViewById(R.id.gaugeView1);
        gaugeView2 = (GaugeView) findViewById(R.id.gaugeView2);
        gaugeView3 = (GaugeView) findViewById(R.id.gaugeView3);

        gaugeView.setRotateDegree(degree);
        gaugeView1.setRotateDegree(degree);
        gaugeView2.setRotateDegree(degree);
        gaugeView3.setRotateDegree(degree);

        ((Button) findViewById(R.id.btnStart)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isInProgress) {
                    isInProgress = true;
                    startRunning();
                }
            }

        });

        ((Button) findViewById(R.id.btnReset)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isInProgress && !resetMode && canReset) {
                    resetMode = true;
                    resetGauges();
                }
            }
        });
    }

    private void resetGauges() {
        new Thread() {
            public void run() {
                for (int i = 0; i < 300; i++) {
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sweepAngleControl--;
                                sweepAngleFirstChart = 1;
                                sweepAngleSecondChart = 1;
                                sweepAngleThirdChart = 1;

                                degree--;
                                gaugeView.setSweepAngleFirstChart(0);
                                gaugeView.setSweepAngleSecondChart(0);
                                gaugeView.setSweepAngleThirdChart(0);
                                gaugeView.setRotateDegree(degree);

                                gaugeView1.setSweepAngleFirstChart(0);
                                gaugeView1.setSweepAngleSecondChart(0);
                                gaugeView1.setSweepAngleThirdChart(0);
                                gaugeView1.setRotateDegree(degree);

                                gaugeView2.setSweepAngleFirstChart(0);
                                gaugeView2.setSweepAngleSecondChart(0);
                                gaugeView2.setSweepAngleThirdChart(0);
                                gaugeView2.setRotateDegree(degree);

                                gaugeView3.setSweepAngleFirstChart(0);
                                gaugeView3.setSweepAngleSecondChart(0);
                                gaugeView3.setSweepAngleThirdChart(0);
                                gaugeView3.setRotateDegree(degree);
                            }
                        });
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (i == 299) {
                        resetMode = false;
                        canReset = false;
                    }

                }
            }
        }.start();
    }

    private void startRunning() {
        new Thread() {
            public void run() {
                for (int i = 0; i < 300; i++) {
                    try {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                degree++;
                                sweepAngleControl++;
                                if (degree < 45) {
                                    gaugeView.setRotateDegree(degree);
                                    gaugeView1.setRotateDegree(degree);
                                    gaugeView2.setRotateDegree(degree);
                                    gaugeView3.setRotateDegree(degree);
                                }

                                if (sweepAngleControl <= 90) {
                                    sweepAngleFirstChart++;
                                    gaugeView.setSweepAngleFirstChart(sweepAngleFirstChart);
                                    gaugeView1.setSweepAngleFirstChart(sweepAngleFirstChart);
                                    gaugeView2.setSweepAngleFirstChart(sweepAngleFirstChart);
                                    gaugeView3.setSweepAngleFirstChart(sweepAngleFirstChart);
                                } else if (sweepAngleControl <= 180) {
                                    sweepAngleSecondChart++;
                                    gaugeView.setSweepAngleSecondChart(sweepAngleSecondChart);
                                    gaugeView1.setSweepAngleSecondChart(sweepAngleSecondChart);
                                    gaugeView2.setSweepAngleSecondChart(sweepAngleSecondChart);
                                    gaugeView3.setSweepAngleSecondChart(sweepAngleSecondChart);
                                } else if (sweepAngleControl <= 270) {
                                    sweepAngleThirdChart++;
                                    gaugeView.setSweepAngleThirdChart(sweepAngleThirdChart);
                                    gaugeView1.setSweepAngleThirdChart(sweepAngleThirdChart);
                                    gaugeView2.setSweepAngleThirdChart(sweepAngleThirdChart);
                                    gaugeView3.setSweepAngleThirdChart(sweepAngleThirdChart);
                                }

                            }
                        });
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (i == 299) {
                        isInProgress = false;
                        canReset = true;
                    }

                }
            }
        }.start();
    }
}
