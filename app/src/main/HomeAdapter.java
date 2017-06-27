package cn.studyjams.s2.notes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.studyjams.s2.notes.bean.Notes;

/**
 * Created by w_x on 2016/6/17.
 * 设置RecyclerView的适配器
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    public List<Notes> getList() {
        return list;
    }

    public void setList(List<Notes> list) {
        this.list = list;
        getRandomHeight(this.list);
    }

    //private List<Notes> list;
    private List<Notes>list;
    private Context context;
    private List<Integer> height;
    private ItemClickListener itemClickListener;

    public HomeAdapter(Context context, List<Notes> list) {
        this.context = context;
        this.list = list;
        getRandomHeight(this.list);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    /**
     * 得到随机的Item的高度
     */
    private void getRandomHeight(List<Notes> list) {
        height = new ArrayList<>();
        for (int i = 0; i < list.size(); ++i) {
            height.add((int) (200 + Math.random() * 400));
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        /**
         * 得到item的LayoutParams布局参数
         */
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = height.get(position);//把随机的高度赋予item布局
        holder.itemView.setLayoutParams(params);//把params设置item布局

        holder.textView.setText(list.get(position).getContent().toString());//为控件绑定数据
        //为TextView添加监听回调
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemSubViewClick(holder.textView, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyViewHolder(final View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.id_num);
            //为item添加普通点击回调
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(itemView, getPosition());
                    }
                }
            });
            //为item添加长按回调
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemLongClick(itemView, getPosition());
                    }
                    return true;
                }
            });
        }
    }

/*    public void addItem(int position) {
        list.add(position,"A");
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }*/
}