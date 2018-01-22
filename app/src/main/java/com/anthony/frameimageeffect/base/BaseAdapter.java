package com.anthony.frameimageeffect.base;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harry on 6/7/16.
 */
public abstract class BaseAdapter<V, T extends ViewHolder> extends RecyclerView.Adapter<T> {
    private int lastPosition = -1;

    protected Context context;
    protected List<V> list;
    private boolean enableAnimation;
    private WeakReference<Activity> activityWeakReference;
    public BaseAdapter(Activity mActivity) {
        this.context = mActivity.getApplicationContext();
        activityWeakReference = new WeakReference<>(mActivity);
        this.list = new ArrayList<>();
        this.enableAnimation = true;
    }

    public WeakReference<Activity> getActivityWeakReference() {
        return activityWeakReference;
    }

    public BaseAdapter(Activity mActivity, boolean enableAnimation) {
        this.context = mActivity.getApplicationContext();
        this.list = new ArrayList<>();
        this.enableAnimation = enableAnimation;
    }

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onViewDetachedFromWindow(T holder) {
        if (enableAnimation) {
            holder.itemView.clearAnimation();
        }
    }

    public List<V> getList() {
        return list;
    }

    @Override
    public void onBindViewHolder(T holder, int position) {
        if (enableAnimation) {
            setAnimation(holder.itemView, position);
        }
    }

    public void add(V data) {
        list.add(data);
        notifyItemInserted(list.size());
    }

    public void addAll(List<V> data) {
        int pos = getItemCount();
        list.addAll(data);
        notifyItemRangeChanged(pos, list.size());
    }

    public void clearAndAddAll(List<V> data) {
        list.clear();
        addAll(data);
        notifyDataSetChanged();
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    /**
     * default is true, enable animation item of recycler view.
     *
     * @return boolean
     */
    protected boolean attachAnimationToView() {
        return enableAnimation;
    }

    public void deleteItem(int pos) {
        list.remove(pos);
        notifyItemRemoved(pos);
        new Handler().postDelayed(() -> notifyDataSetChanged(), 1000);
    }

    public V getItem(int position) {
        return list.get(position);
    }

    public void showMessage(String msg){
        if(activityWeakReference !=null && activityWeakReference.get() != null){
            //DialogUtils.showDialogWithMessage(activityWeakReference.get(),msg);
        }
    }


}
