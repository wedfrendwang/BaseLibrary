package com.wedfrend.baselibrary.refreshView;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.wedfrend.baselibrary.R;
import com.wedfrend.baselibrary.refreshView.gifanimation.GifView;

/**
* author:wedfrend
* email:wedfrend@yeah.net
* create:2017/11/16 17:17
* desc: 该类为实现RefreshViewHolder中的类
 *
 *      在自己的应用中，都可以实现这个类来实现自己的下拉，上拉的个性化
*/

public class WeLiveRefreshHolder extends RefreshViewHolder {

	public WeLiveRefreshHolder(Context context, boolean isLoadingMoreEnabled) {
		super(context, isLoadingMoreEnabled);
		// TODO Auto-generated constructor stub
	}

	GifView iv_refresh_gif,iv_load_gif;
	TextView mHintTextView;
	@Override
	public View getRefreshHeaderView() {
		// TODO Auto-generated method stub
//		if(mRefreshHeaderView == null){
			mRefreshHeaderView = View.inflate(mContext, R.layout.refresh_holder, null);
			iv_refresh_gif = (GifView) mRefreshHeaderView.findViewById(R.id.iv_refresh_gif);
		    mHintTextView = (TextView)mRefreshHeaderView.findViewById(R.id.tv_refresh);
			iv_refresh_gif.setGifImage(R.drawable.gif_preloader);
			mHintTextView.setText(R.string.pull_refresh);
			mHintTextView.setTextColor(Color.parseColor("#333333"));
//		}
		return mRefreshHeaderView;
	}

	@Override
	public View getLoadMoreFooterView() {
		if (!mIsLoadingMoreEnabled) {
			return null;
		}
		if (mLoadMoreFooterView == null) {
			mLoadMoreFooterView = View.inflate(mContext, R.layout.loading_holder, null);
			iv_load_gif = (GifView) mLoadMoreFooterView.findViewById(R.id.iv_load_gif);
			TextView HintTextView = (TextView)mLoadMoreFooterView.findViewById(R.id.tv_loadMore);
			iv_load_gif.setGifImage(R.drawable.gif_preloader);
			HintTextView.setText(R.string.load_more);
			HintTextView.setTextColor(Color.parseColor("#333333"));
		}
		return mLoadMoreFooterView;
	}

	@Override
	public void handleScale(float scale, int moveYDistance) {
		// TODO Auto-generated method stub
		 scale = 0.6f + 0.4f * scale;
//	     ViewCompat.setScaleX(mMoocRefreshView, scale);
//	     ViewCompat.setScaleY(mMoocRefreshView, scale);
	}

	@Override
	public void onEndLoading() {
			//停止动画加载
	}

	@Override
	public void changeToIdle() {
		// TODO Auto-generated method stub
		if(mHintTextView != null)
			mHintTextView.setText(R.string.pull_refresh);
	}

	@Override
	public void changeToPullDown() {
		// TODO Auto-generated method stub

		
	}

	@Override
	public void changeToReleaseRefresh() {
		// TODO Auto-generated method stub

	}

	@Override
	public void changeToRefreshing() {
		// TODO Auto-generated method stub
		if(mHintTextView != null)
			mHintTextView.setText(R.string.pull_releaseRefresh);
	}

	@Override
	public void onEndRefreshing() {
		// TODO Auto-generated method stub
		if(mHintTextView != null)
			mHintTextView.setText(R.string.pull_refresh);
	}
}
