package com.example.ekonobeeva.animatedlistscroling;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

        getText();

    }


    private float getValueInRange(float min, float max, float val){
        float mm = Math.max(min, val);
        return Math.min(mm, max);
    }

    private ScrollListener scrollListener = new ScrollListener();


    public void setRecyclerView(RecyclerView rw){
        this.recyclerView = rw;
        recyclerView.addOnScrollListener(scrollListener);

    }

    public class ScrollListener extends RecyclerView.OnScrollListener {

        int increaseProportion = 1;

        @Override
        public void onScrolled(RecyclerView rv, int dx, int dy) {

            int position;
            int firstVisiblePosition = ((LinearLayoutManager) rv.getLayoutManager()).findFirstVisibleItemPosition();
            int lastVisiblePosition = ((LinearLayoutManager) rv.getLayoutManager()).findLastVisibleItemPosition();

            if(rv.getLayoutManager() instanceof GridLayoutManager) {

                int spanCount = ((GridLayoutManager) rv.getLayoutManager()).getSpanCount();
                int firstVisibleRow = firstVisiblePosition / spanCount;


                int lastVisibleRow = lastVisiblePosition / spanCount;
                int countRows = rv.getAdapter().getItemCount() / spanCount;

                if(countRows < 50){
                    increaseProportion = 2;
                }

                int curPos = firstVisibleRow + (lastVisibleRow - firstVisibleRow)/2;

                if (firstVisibleRow == 0) {
                    position = 0;
                } else if (lastVisibleRow == countRows-1) {
                    position = countRows/increaseProportion; }
                else {
                    position = firstVisibleRow;
                }
                float proportion = (float) position / ((float) countRows/increaseProportion);
                float pos = height*proportion;
                setPosition(pos);



            } else{


                int itemCount = rv.getAdapter().getItemCount();
                if(itemCount < 100){
                    increaseProportion = 2;
                }

                int curPos = firstVisiblePosition + (lastVisiblePosition - firstVisiblePosition)/2;
                if (firstVisiblePosition == 0) {
                    position = 0;
                } else if (lastVisiblePosition == itemCount - 1) {
                    position = (itemCount - 1)/increaseProportion;
                } else {
                    position = curPos;
                }
                float proportion = (float) position / ((float) itemCount)/increaseProportion;
                setPosition(height*proportion);
            }

        }
    }

    public void getText(){
        int lp = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        RecyclerView.ViewHolder vh = recyclerView.findViewHolderForAdapterPosition(lp);
        String s = ((MyAdapter.MyViewHolder)vh).textView.getText().toString();
        String ss = s.substring(0, 1).toUpperCase();
        drop.setText(ss);
    }

    private static final int HANDLE_HIDE_DELAY = 250;

    private final HandleHider handleHider = new HandleHider();

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
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
            proportion = y / (float) height;
            int targetPos = (int) (proportion * (float) itemCount);
            recyclerView.scrollToPosition(targetPos);
        }
    }



}
