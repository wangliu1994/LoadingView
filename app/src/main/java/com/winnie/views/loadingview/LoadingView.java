package com.winnie.views.loadingview;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * @author : winnie
 * @date : 2019/12/25
 * @desc 覆盖在Activity上的loadingView, 当其不被取消时Activity不可用
 */
public class LoadingView {

    private Activity mContext;
    private ViewGroup mParentView;
    private LoadContentView mLoadingView;
    private LoadErrorView mLoadErrorView;
    private ViewGroup.LayoutParams mParams;

    private boolean mLoadingInflated = false;
    private boolean mLoadErrorInflated = false;

    /**
     * 背景透明，为false的时候全屏白色覆盖
     */
    private boolean mTransparentTag = false;

    public LoadingView(Activity context) {
        mContext = context;
        initView();
    }

    private void initView() {
        mLoadingView = new LoadContentView(mContext);
        mLoadErrorView = new LoadErrorView(mContext);
        mLoadingView.transparent(mTransparentTag);
        mLoadErrorView.transparent(mTransparentTag);

        mParentView = mContext.getWindow().getDecorView().findViewById(android.R.id.content);
        mParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public void setTransparentTag(boolean transparentTag) {
        this.mTransparentTag = transparentTag;
        mLoadingView.transparent(transparentTag);
        mLoadErrorView.transparent(transparentTag);

    }

    /**
     * 加载loading
     */
    public void show() {
        if (!mLoadingInflated) {
            mParentView.addView(mLoadingView.getContentView(), mParams);
            mLoadingInflated = true;
        }

        mLoadingView.getContentView().setVisibility(View.VISIBLE);
        //将loading置于页面最上层，防止被其余模块遮挡
        mLoadingView.getContentView().bringToFront();
        mLoadingView.show();
    }


    public void success() {
        if (!mLoadingInflated) {
            return;
        }
        mLoadingView.hide();
        slideUpInSuccess();
    }

    public void error() {
        if (!mLoadErrorInflated) {
            mParentView.addView(mLoadErrorView.getContentView(), mParams);
            mLoadErrorInflated = true;
        }
        mLoadErrorView.getContentView().setVisibility(View.GONE);
        slideUpInError();
    }

    private void slideUpInSuccess() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(mLoadingView.getContentView().getScaleX(),
                0.2f, mLoadingView.getContentView().getScaleY(), 0.2f, ScaleAnimation.RELATIVE_TO_SELF,
                0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(250);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mLoadingView.getContentView().setVisibility(View.GONE);
            }
        });
        mLoadingView.getContentView().startAnimation(scaleAnimation);
    }

    private void slideUpInError() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(mLoadingView.getContentView().getScaleX(), 0.2f,
                mLoadingView.getContentView().getScaleY(), 0.2f, ScaleAnimation.RELATIVE_TO_SELF,
                0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(250);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mLoadErrorView.getContentView().setVisibility(View.VISIBLE);
                //将loading置于页面最上层，防止被其余模块遮挡
                mLoadErrorView.getContentView().bringToFront();

                mLoadingView.getContentView().setVisibility(View.GONE);
            }
        });
        mLoadingView.getRectangleView().startAnimation(scaleAnimation);
    }

    private class LoadContentView {
        private View mContentView;
        private LinearLayout mLoadingBg;
        private LinearLayout mRectangleView;
        private ImageView mImageView;

        LoadContentView(Context context) {
            mContentView = View.inflate(context, R.layout.layout_loading_view, null);
            mContentView.setClickable(true);

            mLoadingBg = mContentView.findViewById(R.id.loading_bg);
            mRectangleView = mContentView.findViewById(R.id.show_rectangle);
            mImageView = mContentView.findViewById(R.id.loading_image);
        }

        void show() {
            RotateAnimation animation = new RotateAnimation(0, 360,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setRepeatMode(Animation.RESTART);
            animation.setRepeatCount(Animation.INFINITE);
            animation.setInterpolator(new LinearInterpolator());
            animation.setDuration(1500);
            mImageView.startAnimation(animation);
        }

        void hide() {
            mImageView.clearAnimation();
        }

        void transparent(boolean transparentTag) {
            if(transparentTag){
                mLoadingBg.setBackground(null);
                mRectangleView.setBackgroundResource(R.drawable.bg_loading);
            }else {
                mLoadingBg.setBackgroundResource(android.R.color.white);
                mRectangleView.setBackground(null);
            }
        }

        LinearLayout getRectangleView() {
            return mRectangleView;
        }

        View getContentView() {
            return mContentView;
        }
    }

    private class LoadErrorView {
        private View mContentView;
        private LinearLayout mLoadingBg;
        private LinearLayout mRectangleView;


        LoadErrorView(Context context) {
            mContentView = View.inflate(context, R.layout.layout_loading_error, null);
            mContentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContentView.setVisibility(View.GONE);
                }
            });

            mLoadingBg = mContentView.findViewById(R.id.loading_bg);
            mRectangleView = mContentView.findViewById(R.id.show_rectangle);
        }

        void transparent(boolean transparentTag) {
            if(transparentTag){
                mLoadingBg.setBackground(null);
                mRectangleView.setBackgroundResource(R.drawable.bg_loading);
            }else {
                mLoadingBg.setBackgroundResource(android.R.color.white);
                mRectangleView.setBackground(null);
            }
        }

        View getContentView() {
            return mContentView;
        }
    }
}
