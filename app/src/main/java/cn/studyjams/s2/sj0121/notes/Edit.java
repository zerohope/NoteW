package cn.studyjams.s2.sj0121.notes;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import org.litepal.crud.DataSupport;

import java.util.Date;

import cn.studyjams.s2.sj0121.notes.bean.Notes;

public class Edit extends AppCompatActivity implements View.OnClickListener{
    private EditText content;
    private Notes notes;
    private ImageButton back;
    private ImageButton del;
    private int update=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        content=(EditText)findViewById(R.id.inputNotes);
        back=(ImageButton)findViewById(R.id.back);
        del=(ImageButton)findViewById(R.id.delNotes);
        back.setOnClickListener(this);
        del.setOnClickListener(this);
        notes=new Notes();

            if(this.getIntent().getExtras()!=null)
            {
                notes.setId(this.getIntent().getExtras().getInt("id"));
                notes.setContent(this.getIntent().getExtras().getString("content"));
                content.setText(notes.getContent());
                update=1;
            }


    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.delNotes:

                new AlertDialog.Builder(this).setTitle("删除Notes").setMessage("确定要删除该Notes").setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        content.setText("");

                        if (notes.getContent()!=null&&!"".equals(notes.getContent())){
                            DataSupport.delete(Notes.class,notes.getId());
                        }
                        onBackPressed();
                    }
                })
                        .setNegativeButton("取消",null)
                        .show();

                break;
            default:
                break;
        }
    }
    public void onBackPressed() {


        notes.setDate(new Date());
        notes.setContent(content.getText().toString());
        if(!"".equals(notes.getContent()))
        {
            if(update==0)
                notes.save();
            else {
                notes.update(notes.getId());
            }
        }
        else{
               if(update==1)
                DataSupport.delete(Notes.class,notes.getId());

        }

            super.onBackPressed();
        System.out.println("按下了back键   onBackPressed()"+content.getText()+"notes??? "+notes.getContent());
    }
}
