package com.szyl.szyllibrary.view;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.szyl.szyllibrary.R;

public class TsSwitch extends View implements ValueAnimator.AnimatorUpdateListener,ValueAnimator.AnimatorListener {

    //默认的宽高比例
    private static final float DEFAULT_WIDTH_HEIGHT_PERCENT = 0.42f;
    //动画最大的比例
    private static final float ANIMATION_MAX_FRACTION = 1;

    private int mWidth,mHeight;

    //画跑道型背景
    private Paint mBackgroundPain;
    //画背景上的字
    private Paint mDisaboleTextPaint;
    private Paint mEnableTextPaint;
    //画白色圆点
    private Paint mSlidePaint;

    //是否正在动画
    private boolean isAnimation;

    private ValueAnimator mValueAnimator;

    private float mAnimationFraction;

    private String openText;
    private String closeText;
    private int mOpenColor = Color.GREEN;
    private int mCloseColor = Color.GRAY;
    private int mCurrentColor = mCloseColor;

    //监听
    private OnCheckedChangeListener mCheckedChangeListener;

    private boolean isChecked;

    public TsSwitch(Context context) {
        this(context,null);
    }

    public TsSwitch(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TsSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TsSwitch);
        openText = typedArray.getString(R.styleable.TsSwitch_openText);
        closeText = typedArray.getString(R.styleable.TsSwitch_closeText);
        mOpenColor = typedArray.getColor(R.styleable.TsSwitch_openColor,Color.GREEN);
        mCloseColor = typedArray.getColor(R.styleable.TsSwitch_closeColor,Color.GRAY);
        mCurrentColor = mCloseColor;
        typedArray.recycle();
        init();
    }

    private void init(){
        mBackgroundPain = new Paint();
        mBackgroundPain.setAntiAlias(true);
        mBackgroundPain.setDither(true);
        mBackgroundPain.setColor(Color.GRAY);
        mDisaboleTextPaint = new Paint();
        mDisaboleTextPaint.setAntiAlias(true);
        mDisaboleTextPaint.setDither(true);
        mDisaboleTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mDisaboleTextPaint.setColor(Color.WHITE);
        mDisaboleTextPaint.setTextAlign(Paint.Align.CENTER);

        mEnableTextPaint = new Paint();
        mEnableTextPaint.setAntiAlias(true);
        mEnableTextPaint.setDither(true);
        mEnableTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mEnableTextPaint.setColor(Color.WHITE);
        mEnableTextPaint.setTextAlign(Paint.Align.CENTER);

        mSlidePaint = new Paint();
        mSlidePaint.setColor(Color.WHITE);
        mSlidePaint.setAntiAlias(true);
        mSlidePaint.setDither(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width =  MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) (width*DEFAULT_WIDTH_HEIGHT_PERCENT);
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        drawSlide(canvas);
    }

    private void drawSlide(Canvas canvas){
        float distance = mWidth - mHeight;
        canvas.drawCircle(mHeight/2+distance*mAnimationFraction,mHeight/2,mHeight/3,mSlidePaint);
    }

    private void drawBackground(Canvas canvas){
        Path path = new Path();
        RectF rectF = new RectF(0,0,mHeight,mHeight);
        path.arcTo(rectF,90,180);
        rectF.left = mWidth-mHeight;
        rectF.right = mWidth;
        path.arcTo(rectF,270,180);
        path.close();
        mBackgroundPain.setColor(mCurrentColor);
        canvas.drawPath(path,mBackgroundPain);

        mDisaboleTextPaint.setTextSize(mHeight*3/5);
        mEnableTextPaint.setTextSize(mHeight*3/5);
        Paint.FontMetrics fontMetrics = mDisaboleTextPaint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;
        int baseLine = (int) (mHeight/2 + (bottom-top)*0.3);

        if (!"".equals(openText)){
            //启用
            mDisaboleTextPaint.setAlpha((int) (255*mAnimationFraction));
            canvas.drawText(openText,mWidth*0.35f,baseLine,mDisaboleTextPaint);
        }

        if (!"".equals(closeText)){
            //启用
            mEnableTextPaint.setAlpha((int) (255*(1-mAnimationFraction)));
            canvas.drawText(closeText,mWidth*0.65f,baseLine,mEnableTextPaint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                if (isAnimation){
                    return true;
                }
                if (isChecked){
                    startCloseAnimation();
                    isChecked = false;
                    if (mCheckedChangeListener!=null){
                        mCheckedChangeListener.onCheckedChanged(false);
                    }
                }else {
                    startOpeAnimation();
                    isChecked = true;
                    if (mCheckedChangeListener!=null){
                        mCheckedChangeListener.onCheckedChanged(true);
                    }
                }
                return true;


        }
        return super.onTouchEvent(event);
    }

    private void startOpeAnimation(){
        mValueAnimator = ValueAnimator.ofFloat(0.0f, ANIMATION_MAX_FRACTION);
        mValueAnimator.setDuration(500);
        mValueAnimator.addUpdateListener(this);
        mValueAnimator.addListener(this);
        mValueAnimator.start();
        startColorAnimation();
    }
    private void startCloseAnimation(){
        mValueAnimator = ValueAnimator.ofFloat(ANIMATION_MAX_FRACTION, 0.0f);
        mValueAnimator.setDuration(500);
        mValueAnimator.addUpdateListener(this);
        mValueAnimator.addListener(this);
        mValueAnimator.start();
        startColorAnimation();
    }

    private void startColorAnimation(){
        int colorFrom = isChecked?mOpenColor:mCloseColor; //mIsOpen为true则表示要启动关闭的动画
        int colorTo = isChecked? mCloseColor:mOpenColor;
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(500); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                mCurrentColor = (int)animator.getAnimatedValue();
            }

        });
        colorAnimation.start();
    }



    //设置监听
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener){
        mCheckedChangeListener = listener;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
        if (isChecked){
            mCurrentColor = mOpenColor;
            mAnimationFraction = 1.0f;
        }else {
            mCurrentColor = mCloseColor;
            mAnimationFraction = 0.0f;
        }
        invalidate();
    }

    @Override
    public void onAnimationStart(Animator animator) {
        isAnimation = true;
    }

    @Override
    public void onAnimationEnd(Animator animator) {
        isAnimation = false;
    }

    @Override
    public void onAnimationCancel(Animator animator) {
        isAnimation = false;
    }

    @Override
    public void onAnimationRepeat(Animator animator) {
        isAnimation = true;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        mAnimationFraction = (float) valueAnimator.getAnimatedValue();
        invalidate();
    }

    public interface OnCheckedChangeListener{
        void onCheckedChanged(boolean isChecked);
    }
}
