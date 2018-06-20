package com.hoonyeee.android.orm_outdoor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder>{
    Context context;
    DBConnect con;
    List<Memo> datas = null;
    public CustomAdapter(Context context, DBConnect con){
        this.context = context;
        this.con = con;
    }
    public void setData(List<Memo> datas){
        this.datas = datas;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Memo memo = datas.get(position);
        holder.setId(memo.id);
        holder.setNo(memo.id);
        holder.setContent(memo.memo);
        holder.setName(memo.username);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    class Holder extends RecyclerView.ViewHolder{
        private int id;
        TextView text_no,text_content,text_name;
        Button btn_x;
        public Holder(View itemView) {
            super(itemView);
            text_no = itemView.findViewById(R.id.text_no);
            text_content = itemView.findViewById(R.id.text_content);
            text_name = itemView.findViewById(R.id.text_name);
            btn_x = itemView.findViewById(R.id.btn_x);

            // 리스트 아이템 삭제
            btn_x.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        // 삭제
                        con.delete(id);
                        for(int i=0; i<datas.size(); i++){
                            if(Holder.this.id == datas.get(i).id) {
                                datas.remove(i);
                                break;
                            }
                        }
                        Toast.makeText(context, id+" has been deleted!", Toast.LENGTH_SHORT).show();

                        // 갱신
                        notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            });
        }
        public void setId(int id){
            this.id = id;
        }
        public void setNo(int no){
            text_no.setText(no+"");
        }
        public void setContent(String content){
            text_content.setText(content);
        }
        public void setName(String content){
            text_name.setText(content);
        }
    }
}
