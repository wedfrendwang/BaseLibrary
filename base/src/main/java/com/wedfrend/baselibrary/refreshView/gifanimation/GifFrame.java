package com.wedfrend.baselibrary.refreshView.gifanimation;

import android.graphics.Bitmap;

/**
 * Created by welive on 2016/9/19.
 */

public class GifFrame {
    /**
     * 构造函数
     *
     * @param im
     *            图片
     * @param del
     *            延时
     */
    public GifFrame(Bitmap im, int del) {
        image = im;
        delay = del;
    }

    /** 图片 */
    public Bitmap image;
    /** 延时 */
    public int delay;
    /** 下一帧 */
    public GifFrame nextFrame = null;

}
