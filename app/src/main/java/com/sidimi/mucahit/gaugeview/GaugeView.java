package com.sidimi.mucahit.gaugeview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by mucahit on 22/09/15.
 */
public class GaugeView extends View {
    private float internalArcStrokeWidth;

    private int colorFirstItem = Color.parseColor("#59F859");
    private int colorSecondItem = Color.parseColor("#F8AE59");
    private int colorThirdItem = Color.parseColor("#F85959");
    private int colorMainCenterCircle = Color.WHITE;
    private int colorCenterCircle = Color.parseColor("#434854");
    private int colorPointerLine = Color.parseColor("#434854");

    private float paddingMain;
    private float paddingInnerCircle;

    private float rotateDegree = 0; // for pointer line

    private float sweepAngleFirstChart = 0;
    private float sweepAngleSecondChart = 0;
    private float sweepAngleThirdChart = 0;
    private float strokePointerLineWidth = 4.5f;

    private float x;
    private float y;
    private float constantMeasure;
    private boolean isWidthBiggerThanHeight;

    private double internalArcStrokeWidthScale = 0.215;
    private double paddingInnerCircleScale = 0.27;
    private double pointerLineStrokeWidthScale = 0.006944;
    private float mainCircleScale = 5;

    public GaugeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        x = getWidth();
        y = getHeight();

        if (x >= y) {
            constantMeasure = y;
            isWidthBiggerThanHeight = true;
        } else {
            constantMeasure = x;
            isWidthBiggerThanHeight = false;
        }

        internalArcStrokeWidth = (float) (constantMeasure * internalArcStrokeWidthScale);

        paddingInnerCircle = (float) (constantMeasure * paddingInnerCircleScale);

        strokePointerLineWidth = (float) (constantMeasure * pointerLineStrokeWidthScale);
        int mainCircleStroke = (int) (mainCircleScale * constantMeasure / 60);

        // middle arcs START
        Paint paintInnerArc1 = new Paint();
        paintInnerArc1.setStyle(Paint.Style.STROKE);
        paintInnerArc1.setStrokeWidth(internalArcStrokeWidth);
        paintInnerArc1.setColor(colorFirstItem);
        paintInnerArc1.setAntiAlias(true);

        Paint paintInnerArc2 = new Paint();
        paintInnerArc2.setStyle(Paint.Style.STROKE);
        paintInnerArc2.setStrokeWidth(internalArcStrokeWidth);
        paintInnerArc2.setColor(colorSecondItem);
        paintInnerArc2.setAntiAlias(true);

        Paint paintInnerArc3 = new Paint();
        paintInnerArc3.setStyle(Paint.Style.STROKE);
        paintInnerArc3.setStrokeWidth(internalArcStrokeWidth);
        paintInnerArc3.setColor(colorThirdItem);
        paintInnerArc3.setAntiAlias(true);

        RectF rectfInner;
        if (isWidthBiggerThanHeight) {
            rectfInner = new RectF((x - constantMeasure) / 2 + paddingInnerCircle, paddingInnerCircle, (x - constantMeasure) / 2 + paddingInnerCircle + constantMeasure - 2
                    * paddingInnerCircle, constantMeasure - paddingInnerCircle);
        } else {
            rectfInner = new RectF(paddingInnerCircle, (y - constantMeasure) / 2 + paddingInnerCircle, constantMeasure - paddingInnerCircle, (y - constantMeasure) / 2
                    + constantMeasure - paddingInnerCircle);
        }

        canvas.drawArc(rectfInner, 135, sweepAngleFirstChart, false, paintInnerArc1); // 135
        canvas.drawArc(rectfInner, 225, sweepAngleSecondChart, false, paintInnerArc2);
        canvas.drawArc(rectfInner, 315, sweepAngleThirdChart, false, paintInnerArc3);

        // middle arcs END

        // pointer line START
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setColor(colorPointerLine);
        p.setStrokeWidth(strokePointerLineWidth);
        canvas.rotate(rotateDegree, x / 2, y / 2);

        int a = 8;
        if (isWidthBiggerThanHeight) {
            float stopX = (x - constantMeasure) / 2 + paddingInnerCircle + constantMeasure - 2 * paddingInnerCircle + mainCircleStroke;
            float stopY = y / 2;
            Path path = new Path();
            path.setFillType(Path.FillType.EVEN_ODD);
            path.moveTo(x / 2 + mainCircleStroke / a, y / 2 - mainCircleStroke);
            path.lineTo(x / 2 + mainCircleStroke / a, y / 2 + mainCircleStroke);
            path.lineTo(stopX, stopY);
            path.close();
            canvas.drawPath(path, p);
        } else {
            float stopX = constantMeasure - paddingInnerCircle + mainCircleStroke;
            float stopY = y / 2;

            Path path = new Path();
            path.setFillType(Path.FillType.EVEN_ODD);
            path.moveTo(x / 2 + mainCircleStroke / a, y / 2 - mainCircleStroke);
            path.lineTo(x / 2 + mainCircleStroke / a, y / 2 + mainCircleStroke);
            path.lineTo(stopX, stopY);
            path.close();
            canvas.drawPath(path, p);
        }

        // center circles START
        Paint paintInnerCircle = new Paint();
        paintInnerCircle.setStyle(Paint.Style.FILL);
        paintInnerCircle.setColor(colorCenterCircle);
        paintInnerCircle.setAntiAlias(true);
        canvas.drawCircle(x / 2, y / 2, mainCircleStroke, paintInnerCircle);

        Paint paintCenterCircle = new Paint();
        paintCenterCircle.setStyle(Paint.Style.FILL);
        paintCenterCircle.setColor(colorMainCenterCircle);
        canvas.drawCircle(x / 2, y / 2, mainCircleStroke / 2, paintCenterCircle);
        // center circles END

    }

    public void setInternalArcStrokeWidth(float internalArcStrokeWidth) {
        this.internalArcStrokeWidth = internalArcStrokeWidth;
        invalidate();
    }

    public void setColorFirstItem(int colorFirstItem) {
        this.colorFirstItem = colorFirstItem;
        invalidate();
    }

    public void setColorSecondItem(int colorSecondItem) {
        this.colorSecondItem = colorSecondItem;
        invalidate();
    }

    public void setColorThirdItem(int colorThirdItem) {
        this.colorThirdItem = colorThirdItem;
        invalidate();
    }

    public void setColorCenterCircle(int colorCenterCircle) {
        this.colorCenterCircle = colorCenterCircle;
        invalidate();
    }

    public void setColorMainCenterCircle(int colorMainCenterCircle) {
        this.colorMainCenterCircle = colorMainCenterCircle;
        invalidate();
    }

    public void setColorPointerLine(int colorPointerLine) {
        this.colorPointerLine = colorPointerLine;
        invalidate();
    }

    public void setPaddingMain(float paddingMain) {
        this.paddingMain = paddingMain;
        invalidate();
    }

    public void setPaddingInnerCircle(float paddingInnerCircle) {
        this.paddingInnerCircle = paddingInnerCircle;
        invalidate();
    }

    public void setRotateDegree(float rotateDegree) {
        this.rotateDegree = rotateDegree;
        invalidate();
    }

    public void setSweepAngleFirstChart(float sweepAngleFirstChart) {
        this.sweepAngleFirstChart = sweepAngleFirstChart;
        invalidate();
    }

    public void setSweepAngleSecondChart(float sweepAngleSecondChart) {
        this.sweepAngleSecondChart = sweepAngleSecondChart;
        invalidate();
    }

    public void setSweepAngleThirdChart(float sweepAngleThirdChart) {
        this.sweepAngleThirdChart = sweepAngleThirdChart;
        invalidate();
    }

    public void setStrokePointerLineWidth(float strokePointerLineWidth) {
        this.strokePointerLineWidth = strokePointerLineWidth;
        invalidate();
    }

    public void setX(float x) {
        this.x = x;
        invalidate();
    }

    public void setY(float y) {
        this.y = y;
        invalidate();
    }

    public void setConstantMeasure(float constantMeasure) {
        this.constantMeasure = constantMeasure;
        invalidate();
    }

    public void setWidthBiggerThanHeight(boolean isWidthBiggerThanHeight) {
        this.isWidthBiggerThanHeight = isWidthBiggerThanHeight;
        invalidate();
    }

    public void setInternalArcStrokeWidthScale(double internalArcStrokeWidthScale) {
        this.internalArcStrokeWidthScale = internalArcStrokeWidthScale;
        invalidate();
    }

    public void setPaddingInnerCircleScale(double paddingInnerCircleScale) {
        this.paddingInnerCircleScale = paddingInnerCircleScale;
        invalidate();
    }

    public void setPointerLineStrokeWidthScale(double pointerLineStrokeWidthScale) {
        this.pointerLineStrokeWidthScale = pointerLineStrokeWidthScale;
        invalidate();
    }

    public float getInternalArcStrokeWidth() {
        return internalArcStrokeWidth;
    }

    public int getColorFirstItem() {
        return colorFirstItem;
    }

    public int getColorSecondItem() {
        return colorSecondItem;
    }

    public int getColorThirdItem() {
        return colorThirdItem;
    }

    public int getColorCenterCircle() {
        return colorCenterCircle;
    }

    public int getColorMainCenterCircle() {
        return colorMainCenterCircle;
    }

    public int getColorPointerLine() {
        return colorPointerLine;
    }

    public float getPaddingMain() {
        return paddingMain;
    }

    public float getPaddingInnerCircle() {
        return paddingInnerCircle;
    }

    public float getRotateDegree() {
        return rotateDegree;
    }

    public float getSweepAngleFirstChart() {
        return sweepAngleFirstChart;
    }

    public float getSweepAngleSecondChart() {
        return sweepAngleSecondChart;
    }

    public float getSweepAngleThirdChart() {
        return sweepAngleThirdChart;
    }

    public float getStrokePointerLineWidth() {
        return strokePointerLineWidth;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getConstantMeasure() {
        return constantMeasure;
    }

    public boolean isWidthBiggerThanHeight() {
        return isWidthBiggerThanHeight;
    }

    public double getInternalArcStrokeWidthScale() {
        return internalArcStrokeWidthScale;
    }

    public double getPaddingInnerCircleScale() {
        return paddingInnerCircleScale;
    }

    public double getPointerLineStrokeWidthScale() {
        return pointerLineStrokeWidthScale;
    }
}
