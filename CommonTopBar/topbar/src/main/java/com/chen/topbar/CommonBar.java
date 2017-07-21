package com.chen.topbar;

import android.content.Context;
import android.content.res.TypedArray;
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

    /**
     * 控件属性
     */
    //左边是图片
    private boolean left_btn_visible;
    private int left_btn_src;
    //左边是文字
    private boolean left_tv_visible;
    private String left_tv_text;
    private int left_tv_size;
    //右边是图片
    private boolean right_btn_visible;
    private int right_btn_src;
    //右边是文字
    private boolean right_tv_visible;
    private String right_tv_text;
    private int right_tv_size;
    //中间title
    private String center_title_text;
    private int center_title_size;
    //背景颜色
    private int commonBar_background;
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
        mViewHeight = DisplayUtils.dp2px(context, mViewHeight);
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
        //这里需要注意：设置字体大小时,用getDimensionPixelSize这个方法获取设置的值
        //getDimension返回的是float类型的数值,getDimensionPixelSize返回的是int类型的数值
        left_tv_size = ta.getDimensionPixelSize(R.styleable.CommonBar_left_tv_size, DisplayUtils.sp2px(context, DEFAULT_TEXT_SIZE));
        Log.e("left_tv_size", String.valueOf(left_tv_size));
        //右边图片
        right_btn_visible = ta.getBoolean(R.styleable.CommonBar_right_btn_visible, false);
        right_btn_src = ta.getResourceId(R.styleable.CommonBar_right_btn_src, -1);

        // 右边文字
        right_tv_visible = ta.getBoolean(R.styleable.CommonBar_right_tv_visible, false);
        if (ta.hasValue(R.styleable.CommonBar_right_text)) {
            right_tv_visible = true;
            right_tv_text = ta.getString(R.styleable.CommonBar_right_text);
        }
        right_tv_size = ta.getDimensionPixelSize(R.styleable.CommonBar_right_tv_size, DisplayUtils.sp2px(context, DEFAULT_TEXT_SIZE));
        Log.e("right_tv_size", String.valueOf(right_tv_size));

        //中间title
        if (ta.hasValue(R.styleable.CommonBar_center_text)) {
            center_title_text = ta.getString(R.styleable.CommonBar_center_text);
        }
        center_title_size = ta.getDimensionPixelSize(R.styleable.CommonBar_center_title_size, DisplayUtils.sp2px(context, DEFAULT_TEXT_SIZE));
        Log.e("center_title_size", String.valueOf(center_title_size));

        if (ta.hasValue(R.styleable.CommonBar_commonBar_background)) {
            //背景颜色
            commonBar_background = ta.getResourceId(R.styleable.CommonBar_commonBar_background, -1);
        }
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
            LinearLayout.LayoutParams vlp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            left_image.setLayoutParams(vlp);// 设置TextView的布局
            left_image.setImageResource(left_btn_src);
            left_image.setScaleType(ImageView.ScaleType.CENTER);
            left_image.setPadding(defaultOffset, 0, 0, 0);
            addView(left_image, vlp);
            left_image.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    commonBarOnClickListener.onLeftClick();
                }
            });
        }
        //左边文字可见
        else if (left_tv_visible) {
            TextView left_tv = new TextView(context);//子View TextView
            if (left_tv_size != -1) {
                //在设置字体大小的时候,需要指定TypedValue，因为这里的值是px
                left_tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, left_tv_size);
            }
            // 为子View设置布局参数
            LinearLayout.LayoutParams vlp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            left_tv.setLayoutParams(vlp);// 设置TextView的布局
            left_tv.setGravity(Gravity.CENTER);
            left_tv.setText(left_tv_text);
            //设置最大行数为1行
            left_tv.setMaxLines(1);
            //设置超过5个字符的长度后,后面的用省略号代替
            left_tv.setMaxEms(5);
            left_tv.setEllipsize(TextUtils.TruncateAt.END);
            //设置内边距
            left_tv.setPadding(defaultOffset, 0, 0, 0);
            addView(left_tv, vlp);
            left_tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    commonBarOnClickListener.onLeftClick();
                }
            });
        }
        //右边图片可见
        if (right_btn_visible) {
            ImageView right_image = new ImageView(context);//子View TextView
            // 为子View设置布局参数
            LayoutParams vlp = new LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            vlp.addRule(ALIGN_PARENT_RIGHT);
            right_image.setLayoutParams(vlp);// 设置ImageView的布局
            right_image.setImageResource(right_btn_src);
            right_image.setScaleType(ImageView.ScaleType.CENTER);
            right_image.setPadding(0, 0, defaultOffset, 0);  //设置padding
            addView(right_image, vlp);
            right_image.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    commonBarOnClickListener.onRightClick();
                }
            });
        }
        //右边文字可见
        else if (right_tv_visible) {
            TextView right_tv = new TextView(context);//子View TextView
            if (right_tv_size != -1) {
                right_tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, right_tv_size);
            }
            // 为子View设置布局参数
            LayoutParams vlp = new LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            //设置该控件在父控件之中的位置
            vlp.addRule(ALIGN_PARENT_RIGHT);
            //设置TextView的布局
            right_tv.setLayoutParams(vlp);
            //文字居中
            right_tv.setGravity(Gravity.CENTER);
            right_tv.setText(right_tv_text);
            //设置最大行数为1行
            right_tv.setMaxLines(1);
            //设置超过5个字符的长度后,后面的用省略号代替
            right_tv.setMaxEms(5);
            right_tv.setEllipsize(TextUtils.TruncateAt.END);
            //设置内边距
            right_tv.setPadding(0, 0, defaultOffset, 0);
            //添加到父容器之中
            addView(right_tv, vlp);
            right_tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    commonBarOnClickListener.onRightClick();
                }
            });
        }
        if (center_title_text != null) {
            TextView tv_title = new TextView(context);
            if (center_title_size != -1) {
                tv_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, center_title_size);
            }
            LayoutParams vlp = new LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            vlp.addRule(CENTER_IN_PARENT);
            tv_title.setLayoutParams(vlp);// 设置TextView的布局
            tv_title.setGravity(Gravity.CENTER);
            tv_title.setText(center_title_text);
            //设置最大行数为1行
            tv_title.setMaxLines(1);
            //设置超过5个字符的长度后,后面的用省略号代替
            tv_title.setMaxEms(5);
            tv_title.setEllipsize(TextUtils.TruncateAt.END);
            addView(tv_title, vlp);
        }
        setBackgroundResource(commonBar_background);
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
