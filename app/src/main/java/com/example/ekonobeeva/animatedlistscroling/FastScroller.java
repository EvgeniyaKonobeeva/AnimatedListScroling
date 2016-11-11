package com.example.ekonobeeva.animatedlistscroling;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by e.konobeeva on 08.11.2016.
 */

public class FastScroller extends LinearLayout {
    private static final int HANDLE_ANIMATION_DURATION = 100;

    private static final String SCALE_X = "scaleX";
    private static final String SCALE_Y = "scaleY";
    private static final String ALPHA = "alpha";
    private static final String TAG = "FASTSCROLLER";

    private int height;
    private TextView drop;
    private ImageView track;
    private RecyclerView recyclerView;



    public FastScroller(Context context) {
        super(context);
        init();
    }

    public FastScroller(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FastScroller(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

//    public FastScroller(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }

    public void init(){
        setClipChildren(false);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.fastscroll_drawables, this);
        drop = (TextView) findViewById(R.id.drop);
        track = (ImageView)findViewById(R.id.track);

    }



    private AnimatorSet currentAnimator = null;

    public void showHandle() {
        AnimatorSet animatorSet = new AnimatorSet();
        drop.setPivotX(drop.getWidth());
        drop.setPivotY(drop.getHeight());
        drop.setVisibility(VISIBLE);
        Animator growerX = ObjectAnimator.ofFloat(drop, SCALE_X, 0f, 1f).setDuration(HANDLE_ANIMATION_DURATION);
        Animator growerY = ObjectAnimator.ofFloat(drop, SCALE_Y, 0f, 1f).setDuration(HANDLE_ANIMATION_DURATION);
        Animator alpha = ObjectAnimator.ofFloat(drop, ALPHA, 0f, 1f).setDuration(HANDLE_ANIMATION_DURATION);
        animatorSet.playTogether(growerX, growerY, alpha);
        animatorSet.start();
    }

    private void hideHandle() {
        currentAnimator = new AnimatorSet();
        drop.setPivotX(drop.getWidth());
        drop.setPivotY(drop.getHeight());
        Animator shrinkerX = ObjectAnimator.ofFloat(drop, SCALE_X, 1f, 0f).setDuration(HANDLE_ANIMATION_DURATION);
        Animator shrinkerY = ObjectAnimator.ofFloat(drop, SCALE_Y, 1f, 0f).setDuration(HANDLE_ANIMATION_DURATION);
        Animator alpha = ObjectAnimator.ofFloat(drop, ALPHA, 1f, 0f).setDuration(HANDLE_ANIMATION_DURATION);
        currentAnimator.playTogether(shrinkerX, shrinkerY, alpha);
        currentAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                drop.setVisibility(INVISIBLE);
                currentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                drop.setVisibility(INVISIBLE);
                currentAnimator = null;
            }
        });
        currentAnimator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
    }

    public void setPosition(float y){

        float proportion = y/height;
        int dropHeight = drop.getHeight();
        int trackHeight = track.getHeight();

        track.setY(getValueInRange(0 , height - trackHeight, (int)((height)*proportion)));

        drop.setY(getValueInRange(0, height - dropHeight, (int)track.getY()- dropHeight));

        Log.d(TAG, "from method position to drop = " + (int)((height - trackHeight)*proportion));
        getText();

    }

    public void setPositionFromRecyclerView(float y){

        float proportion = y/height;
        int dropHeight = drop.getHeight();
        int trackHeight = track.getHeight();

        track.setY(getValueInRange(0 , height - trackHeight, (int)((height - trackHeight)*proportion)));

        drop.setY(track.getY()- dropHeight);

        Log.d(TAG, "from method position to drop = " + (int)((height - trackHeight)*proportion));

        getText();
    }

    private int getValueInRange(int min, int max, int val){
        int mm = Math.max(min, val);
        return Math.min(mm, max);
    }

    private ScrollListener scrollListener = new ScrollListener();


    public void setRecyclerView(RecyclerView rw){
        this.recyclerView = rw;
        recyclerView.addOnScrollListener(scrollListener);

    }

    public class ScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView rv, int dx, int dy) {
            View firstVisibleView = rv.getChildAt(0);
            int firstVisiblePosition = rv.getChildPosition(firstVisibleView);

            int visibleRange = rv.getChildCount();
            int lastVisiblePosition = firstVisiblePosition + visibleRange-1;
            int itemCount = rv.getAdapter().getItemCount();
            int position;
            if (firstVisiblePosition == 0) {
                position = 0;
            } else if (lastVisiblePosition == itemCount - 1) {
                position = itemCount - 1;
            } else {
                position = firstVisiblePosition;
            }
            float proportion = (float) position / (float) itemCount;
//            Log.d(TAG, "from listener position to drop = " + height * proportion);
            setPosition(height*proportion);

////            int fp = ((LinearLayoutManager)rv.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
//            int lp = ((LinearLayoutManager)rv.getLayoutManager()).findLastCompletelyVisibleItemPosition();
//            RecyclerView.ViewHolder vh = rv.findViewHolderForAdapterPosition(lp);
//            String s = ((MyAdapter.MyViewHolder)vh).textView.getText().toString();
//            String ss = s.substring(0, 1).toUpperCase();
//            drop.setText(ss);
        }
    }

    public void getText(){
        int lp = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
        RecyclerView.ViewHolder vh = recyclerView.findViewHolderForAdapterPosition(lp);
        String s = ((MyAdapter.MyViewHolder)vh).textView.getText().toString();
        String ss = s.substring(0, 1).toUpperCase();
        drop.setText(ss);
    }

    private static final int HANDLE_HIDE_DELAY = 250;
    private static final int TRACK_SNAP_RANGE = 2;

    private final HandleHider handleHider = new HandleHider();

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            Log.d(TAG, "from touch position to drop = " + event.getY());
            setPosition(event.getY());
            if (currentAnimator != null) {
                currentAnimator.cancel();
            }
            getHandler().removeCallbacks(handleHider);
            if (drop.getVisibility() == INVISIBLE) {
                showHandle();
            }
            setRecyclerViewPosition(event.getY());
            recyclerView.clearOnScrollListeners();
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            recyclerView.addOnScrollListener(scrollListener);
            getHandler().postDelayed(handleHider, HANDLE_HIDE_DELAY);
            return true;
        }
        return super.onTouchEvent(event);
    }

    private class HandleHider implements Runnable {
        @Override
        public void run() {
            hideHandle();
        }
    }

    private void setRecyclerViewPosition(float y) {
        if (recyclerView != null) {
            int itemCount = recyclerView.getAdapter().getItemCount();
            float proportion;
//            if (drop.getY() == 0) {
//                proportion = 0f;
//            } else if (drop.getY() + drop.getHeight() >= height - TRACK_SNAP_RANGE) {
//                proportion = 1f;
//            } else {
//                proportion = y / (float) height;
//            }
            proportion = y / (float) height;
//            int targetPos = (int)getValueInRange(0, itemCount - 1, (int) (proportion * (float) itemCount));
            int targetPos = (int) (proportion * (float) itemCount);
            recyclerView.scrollToPosition(targetPos);
        }
    }



}
