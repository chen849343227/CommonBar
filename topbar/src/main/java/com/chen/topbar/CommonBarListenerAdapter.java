package com.chen.topbar;

/**
 * author long
 * date 17-7-21
 * desc 抽象类 如果只需要单个事件监听,用这个事件比较省事
 */

public abstract class CommonBarListenerAdapter implements CommonBarListener.OnClickListener {
    @Override
    public void onLeftClick() {

    }

    @Override
    public void onCenterClick() {

    }

    @Override
    public void onRightClick() {

    }
}
