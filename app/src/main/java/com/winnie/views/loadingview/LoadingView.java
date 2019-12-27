package com.winnie.views.loadingview;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author : winnie
 * @date : 2019/12/25
 * @desc 覆盖在Activity上的loadingView, 当其不被取消时Activity不可用
 */
public class LoadingView {

    private Activity mContext;
    private ViewGroup mParentView;
    private LoadContentView mLoadingView;
    private ViewGroup.LayoutParams mParams;

    private boolean mInflated = false;
    private boolean mVisible = false;

    public LoadingView(Activity context) {
        mContext = context;
        initView();
    }

    private void initView(){
        mLoadingView = new LoadContentView(mContext);

        mParentView = mContext.getWindow().getDecorView().findViewById(android.R.id.content);
        mParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    /**
     * 加载loading
     */
    public void show(){
        if(!mInflated){
            mParentView.addView(mLoadingView.getContentView(), mParams);
            mInflated = true;
        }

        mLoadingView.getContentView().setVisibility(View.VISIBLE);
        mVisible = true;
        bringToFront();
        mLoadingView.show();
    }

    /**
     * 将loading置于页面最上层，防止被其余模块遮挡
     */
    private void bringToFront() {
        if (!mVisible) {
            return;
        }

        mLoadingView.getContentView().bringToFront();
    }

    public void success(){
        if(!mInflated){
            return;
        }
        mLoadingView.hide();
        slideUpInSuccess();
    }

    public void error(){
        if(!mInflated){
            return;
        }
        mLoadingView.hide();
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
                mLoadingView.getContentView().setVisibility(View.INVISIBLE);
                mLoadingView.getContentView().setTranslationY(-mLoadingView.getContentView().getHeight());
            }
        });
        mLoadingView.getContentView().startAnimation(scaleAnimation);
        mVisible = false;
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
                mLoadingView.getContentView().setVisibility(View.INVISIBLE);
            }
        });
        mLoadingView.getContentView().startAnimation(scaleAnimation);
        mVisible = false;
    }

    private class LoadContentView{
        private Context mContext;
        private View mContentView;
        private ImageView mImageView;
        private TextView mTextView;

        LoadContentView(Context context) {
            mContext = context;
            mContentView = View.inflate(mContext, R.layout.layout_loading_view, null);
            mImageView = mContentView.findViewById(R.id.loading_image);
            mTextView = mContentView.findViewById(R.id.loading_text);
        }

        public void show(){
            RotateAnimation animation = new RotateAnimation(0, 360,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setRepeatMode(Animation.RESTART);
            animation.setRepeatCount(Animation.INFINITE);
            animation.setInterpolator(new LinearInterpolator());
            animation.setDuration(1500);
            mImageView.startAnimation(animation);
        }

        public void hide(){
            mImageView.clearAnimation();
        }

        public View getContentView() {
            return mContentView;
        }
    }
}
