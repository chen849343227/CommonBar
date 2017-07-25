package com.chen.topbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by long on 17-6-10.
 */

public class CommonBar extends RelativeLayout {

    //默认左右两边的偏移
    private static final int DEFAULT_OFFSET = 10;
    //默认高度
    private static final int DEFAULT_HEIGHT = 50;
    //默认文字大小
    private static final int DEFAULT_TEXT_SIZE = 16;

    private Context context;

    /**
     * 控件属性
     */
    //左边是图片
    private boolean left_btn_visible;
    private int left_btn_src;
    //左边是文字
    private boolean left_tv_visible;
    private String left_tv_text;
    private float left_tv_size;
    //右边是图片
    private boolean right_btn_visible;
    private int right_btn_src;
    //右边是文字
    private boolean right_tv_visible;
    private String right_tv_text;
    private float right_tv_size;
    //中间title
    private String center_title_text;
    private float center_title_size;
    //背景
    private Drawable commonBar_background;
    //文字颜色
    private int commonBar_textColor;
    /**
     * 数据
     */
    private int defaultOffset = DEFAULT_OFFSET;
    private int mViewHeight = DEFAULT_HEIGHT;


    public CommonBar(Context context) {
        this(context, null);
    }

    public CommonBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 初始化
     */
    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        mViewHeight = DisplayUtils.dp2px(context, mViewHeight);
        Log.e("info", String.valueOf(mViewHeight));
        defaultOffset = DisplayUtils.dp2px(context, DEFAULT_OFFSET);
        initAttrs(context, attrs);
    }

    /**
     * 加载属性
     *
     * @param context
     * @param attrs
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CommonBar);
        //左边图片
        left_btn_visible = ta.getBoolean(R.styleable.CommonBar_left_btn_visible, false);
        if (ta.hasValue(R.styleable.CommonBar_left_btn_src)) {
            left_btn_visible = true;
            left_btn_src = ta.getResourceId(R.styleable.CommonBar_left_btn_src, -1);
        }
        //左边文字
        left_tv_visible = ta.getBoolean(R.styleable.CommonBar_left_tv_visible, false);
        if (ta.hasValue(R.styleable.CommonBar_left_text)) {
            left_tv_visible = true;
            left_tv_text = ta.getString(R.styleable.CommonBar_left_text);
        }
        //没有设置字体大小的时候,使用默认的大小16sp
        left_tv_size = ta.getDimension(R.styleable.CommonBar_left_tv_size, DisplayUtils.sp2px(context, DEFAULT_TEXT_SIZE));
        left_tv_size = DisplayUtils.px2sp(context, left_tv_size);
        //右边图片
        right_btn_visible = ta.getBoolean(R.styleable.CommonBar_right_btn_visible, false);
        right_btn_src = ta.getResourceId(R.styleable.CommonBar_right_btn_src, -1);

        // 右边文字
        right_tv_visible = ta.getBoolean(R.styleable.CommonBar_right_tv_visible, false);
        if (ta.hasValue(R.styleable.CommonBar_right_text)) {
            right_tv_visible = true;
            right_tv_text = ta.getString(R.styleable.CommonBar_right_text);
        }
        right_tv_size = ta.getDimension(R.styleable.CommonBar_right_tv_size, DisplayUtils.sp2px(context, DEFAULT_TEXT_SIZE));
        right_tv_size = DisplayUtils.px2sp(context, right_tv_size);

        Log.e("right_tv_size", String.valueOf(right_tv_size));

        //中间title
        if (ta.hasValue(R.styleable.CommonBar_center_text)) {
            center_title_text = ta.getString(R.styleable.CommonBar_center_text);
        }
        center_title_size = ta.getDimension(R.styleable.CommonBar_center_title_size, DisplayUtils.sp2px(context, DEFAULT_TEXT_SIZE));
        center_title_size = DisplayUtils.px2sp(context, center_title_size);
        Log.e("center_title_size", String.valueOf(center_title_size));

        if (ta.hasValue(R.styleable.CommonBar_commonBar_background)) {
            //背景颜色
            commonBar_background = ta.getDrawable(R.styleable.CommonBar_commonBar_background);
            Log.e("textColor", String.valueOf(commonBar_background));
        }
        commonBar_textColor = ta.getColor(R.styleable.CommonBar_commonBar_textColor, -1);
        ta.recycle();
        initLayout(context);
    }

    /**
     * 初始化布局
     *
     * @param context
     */
    private void initLayout(Context context) {
        //左边图片可见
        if (left_btn_visible) {
            ImageView left_image = new ImageView(context);//子View TextView
            // 为子View设置布局参数
            LayoutParams rlp = new LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    mViewHeight);
            //设置该控件在父控件之中的位置
            rlp.addRule(CENTER_VERTICAL);
            //设置TextView的布局参数
            left_image.setLayoutParams(rlp);
            left_image.setImageResource(left_btn_src);
            left_image.setScaleType(ImageView.ScaleType.CENTER);
            left_image.setPadding(defaultOffset, 0, 0, 0);
            addView(left_image, rlp);
            left_image.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (commonBarOnClickListener != null) {
                        commonBarOnClickListener.onLeftClick();
                    }
                }
            });
        }
        //左边文字可见
        else if (left_tv_visible) {
            TextView left_tv = new TextView(context);//子View TextView
            left_tv.setTextSize(left_tv_size);
            // 为子View设置布局参数
            LayoutParams rlp = new LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    mViewHeight);
            //设置该控件在父控件之中的位置
            rlp.addRule(CENTER_VERTICAL);
            left_tv.setLayoutParams(rlp);// 设置TextView的布局
            left_tv.setGravity(Gravity.CENTER);
            left_tv.setText(left_tv_text);
            if (commonBar_textColor != -1) {
                left_tv.setTextColor(commonBar_textColor);
            }
            //设置最大行数为1行
            left_tv.setMaxLines(1);
            //设置超过5个字符的长度后,后面的用省略号代替
            left_tv.setMaxEms(5);
            left_tv.setEllipsize(TextUtils.TruncateAt.END);
            //设置内边距
            left_tv.setPadding(defaultOffset, 0, 0, 0);
            addView(left_tv, rlp);
            left_tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (commonBarOnClickListener != null) {
                        commonBarOnClickListener.onLeftClick();
                    }
                }
            });
        }
        //右边图片可见
        if (right_btn_visible) {
            ImageView right_image = new ImageView(context);//子View TextView
            // 为子View设置布局参数
            LayoutParams rlp = new LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    mViewHeight);
            //设置该控件在父控件之中的位置
            rlp.addRule(ALIGN_PARENT_RIGHT);
            rlp.addRule(CENTER_VERTICAL);
            // 设置ImageView的布局
            right_image.setLayoutParams(rlp);
            right_image.setImageResource(right_btn_src);
            right_image.setScaleType(ImageView.ScaleType.CENTER);
            //设置padding
            right_image.setPadding(0, 0, defaultOffset, 0);
            addView(right_image, rlp);
            right_image.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (commonBarOnClickListener != null) {
                        commonBarOnClickListener.onRightClick();
                    }
                }
            });
        }
        //右边文字可见
        else if (right_tv_visible) {
            TextView right_tv = new TextView(context);//子View TextView
            right_tv.setTextSize(right_tv_size);
            LayoutParams rlp = new LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    mViewHeight);
            //设置该控件在父控件之中的位置
            rlp.addRule(ALIGN_PARENT_RIGHT);
            rlp.addRule(CENTER_VERTICAL);
            //设置TextView的布局参数
            right_tv.setLayoutParams(rlp);
            //文字居中
            right_tv.setGravity(Gravity.CENTER);
            right_tv.setText(right_tv_text);
            if (commonBar_textColor != -1) {
                right_tv.setTextColor(commonBar_textColor);
            }
            //设置最大行数为1行
            right_tv.setMaxLines(1);
            //设置超过5个字符的长度后,后面的用省略号代替
            right_tv.setMaxEms(5);
            right_tv.setEllipsize(TextUtils.TruncateAt.END);
            //设置内边距
            right_tv.setPadding(0, 0, defaultOffset, 0);
            //添加到父容器之中
            addView(right_tv, rlp);
            right_tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (commonBarOnClickListener != null) {
                        commonBarOnClickListener.onRightClick();
                    }
                }
            });
        }
        if (center_title_text != null) {
            TextView tv_title = new TextView(context);
            tv_title.setTextSize(center_title_size);
            LayoutParams rlp = new LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    mViewHeight);
            rlp.addRule(CENTER_IN_PARENT);
            tv_title.setLayoutParams(rlp);// 设置TextView的布局
            tv_title.setGravity(Gravity.CENTER);
            tv_title.setText(center_title_text);
            if (commonBar_textColor != -1) {
                tv_title.setTextColor(commonBar_textColor);
            }
            //设置最大行数为1行
            tv_title.setMaxLines(1);
            //设置超过5个字符的长度后,后面的用省略号代替
            tv_title.setMaxEms(5);
            tv_title.setEllipsize(TextUtils.TruncateAt.END);
            addView(tv_title, rlp);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(commonBar_background);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, mViewHeight);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, mViewHeight);
        }
    }

    /**
     * 对外开放的接口
     */
    private CommonBarListener.OnClickListener commonBarOnClickListener;

    public void setOnCommonBarClickListener(CommonBarListener.OnClickListener commonBarOnClickListener) {
        this.commonBarOnClickListener = commonBarOnClickListener;
    }

}
