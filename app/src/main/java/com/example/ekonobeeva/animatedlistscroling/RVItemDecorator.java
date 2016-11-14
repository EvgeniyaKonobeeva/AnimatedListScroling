package com.example.ekonobeeva.animatedlistscroling;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by e.konobeeva on 11.11.2016.
 */

public class RVItemDecorator extends RecyclerView.ItemDecoration {

    private final Drawable mDivider;
    int verticalOffset;
    int horizontalOffset;
    Paint paint;



    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.top = (int)verticalOffset;

    }

    public RVItemDecorator(Context context, int marginHorizontal, int marginVertical) {
        super();
        paint = new Paint();
        mDivider = ContextCompat.getDrawable(context, R.drawable.item_divider);
        verticalOffset = marginVertical;
        horizontalOffset = marginHorizontal;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = horizontalOffset;
        int right = parent.getWidth() - left;

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if(parent.getAdapter().getItemViewType(parent.getChildAdapterPosition(child)) == MyAdapter.WORD) {

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }
}
