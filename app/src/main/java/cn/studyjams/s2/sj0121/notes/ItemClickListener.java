package cn.studyjams.s2.sj0121.notes;

import android.view.View;

/**
 * Created by kwinter on 2017/6/6.
 */

public interface ItemClickListener {

    /**
     * Item的普通点击
     */
    public void onItemClick(View view, int position);

    /**
     * Item长按
     */
    public void onItemLongClick(View view, int position);

    public void onItemSubViewClick(View view, int postion);
}

