package cn.studyjams.s2.notes;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

import cn.studyjams.s2.notes.bean.Notes;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private List<Notes> list;
    private HomeAdapter homeAdapter;
    private static int DEFAULT_VIEW=0;
    private static int LIST_VIEW=1;


    private int status=DEFAULT_VIEW;
    private Button addItem;
    private Button removeItem;
    private Button change_listView;
    private Button change_gridView;
    private Button change_waterfall;
    private ImageButton addNotes;
    private ImageButton changView;
    private boolean isFirstView = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        initData();
        TextView tv=(TextView)findViewById(R.id.aboutMe);
        tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent=new Intent(MainActivity.this,Edit.class);
                Bundle bundle=new Bundle();
                Notes notes=null;
                if(notes==null){
                    notes=new Notes();
                    notes.setType(0);
                    notes.setContent("About This Application,\n" +
                            "A android Notes APP.\n" +
                            "All Rights Reserved \n" +
                            "By Xdreamer \n" +
                            "(Blog：http://xdream.cf) ");
                    notes.save();
                    bundle.putInt("id",DataSupport.findLast(Notes.class).getId());
                    bundle.putString("content", DataSupport.findLast(Notes.class).getContent());

                }else{
                    bundle.putInt("id",notes.getId());
                    bundle.putString("content",notes.getContent());

                }

                intent.putExtras(bundle);
                startActivity(intent);
                return false;
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_demo);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        homeAdapter = new HomeAdapter(this, list);
        recyclerView.setAdapter(homeAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        homeAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(MainActivity.this,Edit.class);
                Bundle bundle=new Bundle();
                bundle.putInt("id",list.get(position).getId());
                bundle.putString("content",list.get(position).getContent());
                intent.putExtras(bundle);
                startActivity(intent);
                //Toast.makeText(MainActivity.this, "点击了Item" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                DataSupport.delete(Notes.class,list.get(position).getId());
                Refresh();
                Toast.makeText(MainActivity.this, "删除成功" , Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onItemSubViewClick(View view, int position) {

            }
        });
    }

    public void findViewById() {
       /* addItem = (Button) findViewById(R.id.addItem);
        removeItem = (Button) findViewById(R.id.removeItem);*/
      /*  change_listView = (Button) findViewById(R.id.change_listView);
        change_gridView = (Button) findViewById(R.id.change_gridView);
        change_waterfall = (Button) findViewById(R.id.change_waterfall);*/
        addNotes=(ImageButton) findViewById(R.id.addN);
        changView=(ImageButton) findViewById(R.id.changeV);
        addNotes.setOnClickListener(this);
        changView.setOnClickListener(this);
        changView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("删除所有Notes").setMessage("确定要删除所有Notes吗").setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DataSupport.deleteAll(Notes.class);
                                Refresh();
                            }
                        })
                        .setNegativeButton("取消",null)
                        .show();

                return false;
            }
        });

      /*  addItem.setOnClickListener(this);
        removeItem.setOnClickListener(this);*/
      /*  change_listView.setOnClickListener(this);
        change_gridView.setOnClickListener(this);
        change_waterfall.setOnClickListener(this);*/

    }

    private void initData() {
       /* list = new ArrayList<>();
        for (int i = 'A'; i <= 'Z'; ++i) {
            list.add("" + (char) i);
        }*/
       list= DataSupport.findAll(Notes.class);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            list=DataSupport.findAll(Notes.class);
            homeAdapter.notifyDataSetChanged();

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addN:
                startActivity(new Intent(MainActivity.this,Edit.class));
                break;
            case R.id.changeV:

                if (getStatus()!=LIST_VIEW){
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));

                    if (isFirstView) {
                        isFirstView = false;
                        onClick(findViewById(R.id.recyclerView_demo));
                    }
                    v.setBackground(getDrawable(R.mipmap.square));
                    setStatus(LIST_VIEW);
                }
                else {
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                   setStatus(DEFAULT_VIEW);
                    v.setBackground(getDrawable(R.mipmap.list));
                }
                break;
           /* case R.id.addItem:
                homeAdapter.addItem(1);
                break;
            case R.id.removeItem:
                homeAdapter.removeItem(1);
                break;
            case R.id.change_listView:
                /**
                 * ListView的效果
                 */
      /*          recyclerView.setLayoutManager(new LinearLayoutManager(this));
                if (isFirstView) {
                    isFirstView = false;
                    onClick(findViewById(R.id.recyclerView_demo));
                }
                break;
            case R.id.change_gridView:
                *//**
                 * GridView的效果
                 *//*
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                break;
            case R.id.change_waterfall:
                *//**
                 * 瀑布流的效果
                 *//*
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                break;*/
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Refresh();
        System.out.println("onresume");

    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public  void  Refresh(){
        list=DataSupport.findAll(Notes.class);
        homeAdapter.setList(list);
        homeAdapter.notifyDataSetChanged();
    }
}