package com.szyl.szyllibrary.view;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//使用时注意单位 px
public class TsGridSpaceDecoration extends RecyclerView.ItemDecoration {

    private final String TAG = "GridSpaceItemDecoration";

    private int mSpanCount;//横条目数量
    private int mRowSpacing;//行间距
    private int mColumnSpacing;// 列间距

    /**
     * @param spanCount     列数
     * @param rowSpacing    行间距
     * @param columnSpacing 列间距
     */
    public TsGridSpaceDecoration(int spanCount, int rowSpacing, int columnSpacing) {
        this.mSpanCount = spanCount;
        this.mRowSpacing = rowSpacing;
        this.mColumnSpacing = columnSpacing;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // 获取view 在adapter中的位置。
        int column = position % mSpanCount; // view 所在的列

        outRect.left = column * mRowSpacing / mSpanCount; // column * (列间距 * (1f / 列数))
        outRect.right = mRowSpacing - (column + 1) * mRowSpacing / mSpanCount; // 列间距 - (column + 1) * (列间距 * (1f /列数))

       /* Log.e(TAG, "position:" + position
                + "    columnIndex: " + column
                + "    left,right ->" + outRect.left + "," + outRect.right);*/
        // 如果position > 行数，说明不是在第一行，则不指定行高，其他行的上间距为 top=mRowSpacing
        if (position >= mSpanCount) {
            outRect.top = mColumnSpacing; // item top
        }
    }
}