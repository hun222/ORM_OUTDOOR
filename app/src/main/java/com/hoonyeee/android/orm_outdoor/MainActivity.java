package com.hoonyeee.android.orm_outdoor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CustomAdapter.ModifyInterface{
    EditText editTitle;
    EditText editContent;
    TextView textWriter, textDate, list;
    Button btnPost;
    RecyclerView recyclerView;
    CustomAdapter adapter;

    DBConnect con;
    List<Memo> datas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTitle = findViewById(R.id.editTitle);
        editContent = findViewById(R.id.editContent);
        textWriter = findViewById(R.id.textWriter);
        textDate = findViewById(R.id.textDate);

        con = new DBConnect(this);

        recyclerView = findViewById(R.id.recyclerView);
        //List<Memo> datas = new ArrayList<>(); // readAll() 호출해서 담아야함.
        //Memo memo = new Memo("sample title", "content", "test", 0);
        //datas.add(memo);
        adapter = new CustomAdapter(this, con);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnPost = findViewById(R.id.btnPost);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString();
                String content = editContent.getText().toString();
                String userName = textWriter.getText().toString();
                long timestamp = System.currentTimeMillis();


                try {
                    con.insert(title,content,userName,timestamp);
                    Toast.makeText(getBaseContext(), "memo has been inserted", Toast.LENGTH_SHORT).show();
                    refreshList();
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "DB error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        refreshList();
    }

    public void refreshList(){
        try {
            datas = con.selectAll();
            adapter.setData(datas);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "DB error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void change(Memo memo) {
        try {
            con.update(memo);
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "DB error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
