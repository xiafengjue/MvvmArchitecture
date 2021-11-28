//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.sora.multistateview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class MultiStateView extends FrameLayout {
    public static final int VIEW_STATE_UNKNOWN = -1;
    public static final int VIEW_STATE_CONTENT = 0;
    public static final int VIEW_STATE_ERROR = 1;
    public static final int VIEW_STATE_EMPTY = 2;
    public static final int VIEW_STATE_LOADING = 3;
    public static final int VIEW_STATE_NET_ERROR = 4;
    public static final int VIEW_STATE_NEED_SIGN_IN = 5;
    private View mNetErrorView;
    private MultiStateView.ReloadListener reloadListener;
    private MultiStateView.SignInListener signInListener;
    private LayoutInflater mInflater;
    private View mContentView;
    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private View mSignInView;
    private boolean mAnimateViewChanges;
    @Nullable
    private MultiStateView.StateListener mListener;
    private int mViewState;

    public MultiStateView(Context context) {
        this(context, (AttributeSet) null);
    }

    public MultiStateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mAnimateViewChanges = false;
        this.mViewState = -1;
        this.init(attrs);
    }

    public MultiStateView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mAnimateViewChanges = false;
        this.mViewState = -1;
        this.init(attrs);
    }

    private void init(AttributeSet attrs) {
        this.mInflater = LayoutInflater.from(this.getContext());
        TypedArray a = this.getContext().obtainStyledAttributes(attrs, R.styleable.MultiStateView);
        int loadingViewResId = a.getResourceId(R.styleable.MultiStateView_msv_loadingView, R.layout.default_loading_view);
        if (loadingViewResId > -1) {
            this.mLoadingView = this.mInflater.inflate(loadingViewResId, this, false);
            this.addView(this.mLoadingView, this.mLoadingView.getLayoutParams());
        }

        int emptyViewResId = a.getResourceId(R.styleable.MultiStateView_msv_emptyView, R.layout.default_empty);
        if (emptyViewResId > -1) {
            this.mEmptyView = this.mInflater.inflate(emptyViewResId, this, false);
            this.addView(this.mEmptyView, this.mEmptyView.getLayoutParams());
        }

        int errorViewResId = a.getResourceId(R.styleable.MultiStateView_msv_errorView, R.layout.default_error);
        if (errorViewResId > -1) {
            this.mErrorView = this.mInflater.inflate(errorViewResId, this, false);
            View reload = this.mErrorView.findViewById(R.id.msvReload);
            if (reload != null) {
                reload.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (MultiStateView.this.reloadListener != null) {
                            MultiStateView.this.reloadListener.onReload(1);
                        }

                    }
                });
            }

            this.addView(this.mErrorView, this.mErrorView.getLayoutParams());
        }

        int netErrorViewResId = a.getResourceId(R.styleable.MultiStateView_msv_netErrorView, R.layout.default_net_error);
        View reLoad;
        if (netErrorViewResId > -1) {
            this.mNetErrorView = this.mInflater.inflate(netErrorViewResId, this, false);
            View reload = this.mNetErrorView.findViewById(R.id.msvReload);
            if (reload != null) {
                reload.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (MultiStateView.this.reloadListener != null) {
                            MultiStateView.this.reloadListener.onReload(4);
                        }

                    }
                });
            }

            reLoad = this.mNetErrorView.findViewById(R.id.msvInspectNet);
            if (reLoad != null) {
                reLoad.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Toast.makeText(MultiStateView.this.getContext(), "正在检测网络，请稍等片刻。", Toast.LENGTH_SHORT).show();
                        (MultiStateView.this.new InspectNetTask()).execute();
                    }
                });
            }

            this.addView(this.mNetErrorView, this.mNetErrorView.getLayoutParams());
        }

        int needLoginViewResId = a.getResourceId(R.styleable.MultiStateView_msv_needLoginView, R.layout.default_need_login);
        if (needLoginViewResId > -1) {
            this.mSignInView = this.mInflater.inflate(needLoginViewResId, this, false);
            reLoad = this.mSignInView.findViewById(R.id.msvReload);
            if (reLoad != null) {
                reLoad.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        if (MultiStateView.this.reloadListener != null) {
                            MultiStateView.this.reloadListener.onReload(5);
                        }

                    }
                });
            }

            View msvLogin = this.mSignInView.findViewById(R.id.msvSignIn);
            if (msvLogin != null) {
                msvLogin.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        if (MultiStateView.this.signInListener != null) {
                            MultiStateView.this.signInListener.reSignIn();
                        }

                    }
                });
            }

            this.addView(this.mSignInView, this.mSignInView.getLayoutParams());
        }

        int viewState = a.getInt(R.styleable.MultiStateView_msv_viewState, 0);
        this.mAnimateViewChanges = a.getBoolean(R.styleable.MultiStateView_msv_animateViewChanges, false);
        switch (viewState) {
            case -1:
            default:
                this.mViewState = -1;
                break;
            case 0:
                this.mViewState = 0;
                break;
            case 1:
                this.mViewState = 1;
                break;
            case 2:
                this.mViewState = 2;
                break;
            case 3:
                this.mViewState = 3;
                break;
            case 4:
                this.mViewState = 4;
                break;
            case 5:
                this.mViewState = 5;
        }

        a.recycle();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mContentView == null) {
            throw new IllegalArgumentException("Content view is not defined");
        } else {
            this.setView(-1);
        }
    }

    public void addView(View child) {
        if (this.isValidContentView(child)) {
            this.mContentView = child;
        }

        super.addView(child);
    }

    public void setReloadListener(MultiStateView.ReloadListener reloadListener) {
        this.reloadListener = reloadListener;
    }

    public void addView(View child, int index) {
        if (this.isValidContentView(child)) {
            this.mContentView = child;
        }

        super.addView(child, index);
    }

    public void addView(View child, int index, LayoutParams params) {
        if (this.isValidContentView(child)) {
            this.mContentView = child;
        }

        super.addView(child, index, params);
    }

    public void addView(View child, LayoutParams params) {
        if (this.isValidContentView(child)) {
            this.mContentView = child;
        }

        super.addView(child, params);
    }

    public void addView(View child, int width, int height) {
        if (this.isValidContentView(child)) {
            this.mContentView = child;
        }

        super.addView(child, width, height);
    }

    protected boolean addViewInLayout(View child, int index, LayoutParams params) {
        if (this.isValidContentView(child)) {
            this.mContentView = child;
        }

        return super.addViewInLayout(child, index, params);
    }

    protected boolean addViewInLayout(View child, int index, LayoutParams params, boolean preventRequestLayout) {
        if (this.isValidContentView(child)) {
            this.mContentView = child;
        }

        return super.addViewInLayout(child, index, params, preventRequestLayout);
    }

    @Nullable
    public View getView(int state) {
        switch (state) {
            case 0:
                return this.mContentView;
            case 1:
                return this.mErrorView;
            case 2:
                return this.mEmptyView;
            case 3:
                return this.mLoadingView;
            case 4:
                return this.mNetErrorView;
            case 5:
                return this.mSignInView;
            default:
                return null;
        }
    }

    public int getViewState() {
        return this.mViewState;
    }

    public void setViewState(int state) {
        if (state != this.mViewState) {
            int previous = this.mViewState;
            this.mViewState = state;
            this.setView(previous);
            if (this.mListener != null) {
                this.mListener.onStateChanged(this.mViewState);
            }
        }

    }

    public void showNetErrorView() {
        this.setViewState(4);
    }

    public void showNeedSiginView() {
        this.setViewState(5);
    }

    public void showContentView() {
        this.setViewState(0);
    }

    public void showLoadingView() {
        this.setViewState(3);
    }

    public void showErrorView() {
        this.setViewState(1);
    }

    public void showErrorView(String str) {
        this.showErrorView();
        TextView txtError = (TextView) this.mErrorView.findViewById(R.id.msvErrorMsg);
        txtError.setText(str);
    }

    public void showErrorView(@StringRes int strRes) {
        this.showErrorView();
        TextView txtError = (TextView) this.mErrorView.findViewById(R.id.msvErrorMsg);
        txtError.setText(strRes);
    }

    public void showEmptyView() {
        this.setViewState(1);
    }

    public void showEmptyView(String str) {
        this.showEmptyView();
        TextView txtError = (TextView) this.mEmptyView.findViewById(R.id.msvEmptyMsg);
        txtError.setText(str);
    }

    public void showEmptyView(@StringRes int strRes) {
        this.showEmptyView();
        TextView txtError = (TextView) this.mEmptyView.findViewById(R.id.msvEmptyMsg);
        txtError.setText(strRes);
    }

    private void setView(int previousState) {
        switch (this.mViewState) {
            case -1:
            case 0:
            default:
                if (this.mContentView == null) {
                    throw new NullPointerException("Content View");
                }

                if (this.mLoadingView != null) {
                    this.mLoadingView.setVisibility(GONE);
                }

                if (this.mErrorView != null) {
                    this.mErrorView.setVisibility(GONE);
                }

                if (this.mEmptyView != null) {
                    this.mEmptyView.setVisibility(GONE);
                }

                if (this.mNetErrorView != null) {
                    this.mNetErrorView.setVisibility(GONE);
                }

                if (this.mSignInView != null) {
                    this.mSignInView.setVisibility(GONE);
                }

                if (this.mAnimateViewChanges) {
                    this.animateLayoutChange(this.getView(previousState));
                } else {
                    this.mContentView.setVisibility(VISIBLE);
                }
                break;
            case 1:
                if (this.mErrorView == null) {
                    throw new NullPointerException("Error View");
                }

                if (this.mLoadingView != null) {
                    this.mLoadingView.setVisibility(GONE);
                }

                if (this.mContentView != null) {
                    this.mContentView.setVisibility(GONE);
                }

                if (this.mEmptyView != null) {
                    this.mEmptyView.setVisibility(GONE);
                }

                if (this.mNetErrorView != null) {
                    this.mNetErrorView.setVisibility(GONE);
                }

                if (this.mSignInView != null) {
                    this.mSignInView.setVisibility(GONE);
                }

                if (this.mAnimateViewChanges) {
                    this.animateLayoutChange(this.getView(previousState));
                } else {
                    this.mErrorView.setVisibility(VISIBLE);
                }
                break;
            case 2:
                if (this.mEmptyView == null) {
                    throw new NullPointerException("Empty View");
                }

                if (this.mLoadingView != null) {
                    this.mLoadingView.setVisibility(GONE);
                }

                if (this.mErrorView != null) {
                    this.mErrorView.setVisibility(GONE);
                }

                if (this.mContentView != null) {
                    this.mContentView.setVisibility(GONE);
                }

                if (this.mNetErrorView != null) {
                    this.mNetErrorView.setVisibility(GONE);
                }

                if (this.mSignInView != null) {
                    this.mSignInView.setVisibility(GONE);
                }

                if (this.mAnimateViewChanges) {
                    this.animateLayoutChange(this.getView(previousState));
                } else {
                    this.mEmptyView.setVisibility(VISIBLE);
                }
                break;
            case 3:
                if (this.mLoadingView == null) {
                    throw new NullPointerException("Loading View");
                }

                if (this.mContentView != null) {
                    this.mContentView.setVisibility(GONE);
                }

                if (this.mErrorView != null) {
                    this.mErrorView.setVisibility(GONE);
                }

                if (this.mEmptyView != null) {
                    this.mEmptyView.setVisibility(GONE);
                }

                if (this.mNetErrorView != null) {
                    this.mNetErrorView.setVisibility(GONE);
                }

                if (this.mSignInView != null) {
                    this.mSignInView.setVisibility(GONE);
                }

                if (this.mAnimateViewChanges) {
                    this.animateLayoutChange(this.getView(previousState));
                } else {
                    this.mLoadingView.setVisibility(VISIBLE);
                }
                break;
            case 4:
                if (this.mNetErrorView == null) {
                    throw new NullPointerException("Net Error View");
                }

                if (this.mLoadingView != null) {
                    this.mLoadingView.setVisibility(GONE);
                }

                if (this.mContentView != null) {
                    this.mContentView.setVisibility(GONE);
                }

                if (this.mErrorView != null) {
                    this.mErrorView.setVisibility(GONE);
                }

                if (this.mEmptyView != null) {
                    this.mEmptyView.setVisibility(GONE);
                }

                if (this.mSignInView != null) {
                    this.mSignInView.setVisibility(GONE);
                }

                if (this.mAnimateViewChanges) {
                    this.animateLayoutChange(this.getView(previousState));
                } else {
                    this.mNetErrorView.setVisibility(VISIBLE);
                }
                break;
            case 5:
                if (this.mSignInView == null) {
                    throw new NullPointerException("Sign In View");
                }

                if (this.mLoadingView != null) {
                    this.mLoadingView.setVisibility(GONE);
                }

                if (this.mContentView != null) {
                    this.mContentView.setVisibility(GONE);
                }

                if (this.mErrorView != null) {
                    this.mErrorView.setVisibility(GONE);
                }

                if (this.mEmptyView != null) {
                    this.mEmptyView.setVisibility(GONE);
                }

                if (this.mNetErrorView != null) {
                    this.mNetErrorView.setVisibility(GONE);
                }

                if (this.mAnimateViewChanges) {
                    this.animateLayoutChange(this.getView(previousState));
                } else {
                    this.mSignInView.setVisibility(VISIBLE);
                }
        }

    }

    private boolean isValidContentView(View view) {
        if (this.mContentView != null && this.mContentView != view) {
            return false;
        } else {
            return view != this.mLoadingView && view != this.mErrorView && view != this.mEmptyView && view != this.mNetErrorView && view != this.mSignInView;
        }
    }

    public void setViewForState(View view, int state, boolean switchToState) {
        switch (state) {
            case 0:
                if (this.mContentView != null) {
                    this.removeView(this.mContentView);
                }

                this.mContentView = view;
                this.addView(this.mContentView);
                break;
            case 1:
                if (this.mErrorView != null) {
                    this.removeView(this.mErrorView);
                }

                this.mErrorView = view;
                this.addView(this.mErrorView);
                break;
            case 2:
                if (this.mEmptyView != null) {
                    this.removeView(this.mEmptyView);
                }

                this.mEmptyView = view;
                this.addView(this.mEmptyView);
                break;
            case 3:
                if (this.mLoadingView != null) {
                    this.removeView(this.mLoadingView);
                }

                this.mLoadingView = view;
                this.addView(this.mLoadingView);
                break;
            case 4:
                if (this.mNetErrorView != null) {
                    this.removeView(this.mNetErrorView);
                }

                this.mNetErrorView = view;
                this.addView(this.mNetErrorView);
                break;
            case 5:
                if (this.mSignInView != null) {
                    this.removeView(this.mSignInView);
                }

                this.mSignInView = view;
                this.addView(this.mSignInView);
        }

        this.setView(-1);
        if (switchToState) {
            this.setViewState(state);
        }

    }

    public void setViewForState(View view, int state) {
        this.setViewForState(view, state, false);
    }

    public void setViewForState(@LayoutRes int layoutRes, int state, boolean switchToState) {
        if (this.mInflater == null) {
            this.mInflater = LayoutInflater.from(this.getContext());
        }

        View view = this.mInflater.inflate(layoutRes, this, false);
        this.setViewForState(view, state, switchToState);
    }

    public void setViewForState(@LayoutRes int layoutRes, int state) {
        this.setViewForState(layoutRes, state, false);
    }

    public void setAnimateLayoutChanges(boolean animate) {
        this.mAnimateViewChanges = animate;
    }

    public void setStateListener(MultiStateView.StateListener listener) {
        this.mListener = listener;
    }

    public void setSignInListener(MultiStateView.SignInListener signInListener) {
        this.signInListener = signInListener;
    }

    private void animateLayoutChange(@Nullable final View previousView) {
        if (previousView == null) {
            this.getView(this.mViewState).setVisibility(VISIBLE);
        } else {
            previousView.setVisibility(VISIBLE);
            ObjectAnimator anim = ObjectAnimator.ofFloat(previousView, "alpha", 1.0F, 0.0F).setDuration(250L);
            anim.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    previousView.setVisibility(GONE);
                    MultiStateView.this.getView(MultiStateView.this.mViewState).setVisibility(VISIBLE);
                    ObjectAnimator.ofFloat(MultiStateView.this.getView(MultiStateView.this.mViewState), "alpha", new float[]{0.0F, 1.0F}).setDuration(250L).start();
                }
            });
            anim.start();
        }
    }

    class InspectNetTask extends AsyncTask<Object, Object, Integer> {
        InspectNetTask() {
        }

        protected Integer doInBackground(Object[] objects) {
            Runtime runtime = Runtime.getRuntime();

            int ret;
            try {
                Process p = runtime.exec("ping -c 3 www.baidu.com");
                ret = p.waitFor();
                Log.i("Avalible", "Process:" + ret);
            } catch (Exception var5) {
                Log.d("MultiStateView", "检查网络：" + var5.getMessage());
                ret = 0;
            }

            return ret;
        }

        protected void onPostExecute(Integer o) {
            if (o == 0) {
                Toast.makeText(MultiStateView.this.getContext(), "当前网络畅通", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MultiStateView.this.getContext(), "网络不可用，请检查后重试。", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public interface SignInListener {
        void reSignIn();
    }

    public interface ReloadListener {
        void onReload(int var1);
    }

    public interface StateListener {
        void onStateChanged(int var1);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewState {
    }
}
