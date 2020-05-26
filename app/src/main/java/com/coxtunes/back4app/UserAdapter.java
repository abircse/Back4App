package com.coxtunes.back4app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.Holder> {

    private List<UserModel> userlist;
    private Context context;

    // create object for Onitemclick interface
    private OnItemClickListener mListerner;

    public UserAdapter(List<UserModel> userlist, Context context) {
        this.userlist = userlist;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item,parent,false);
        return new Holder(view,mListerner);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        holder.name.setText("Name: "+userlist.get(position).getName());
        holder.email.setText("Email: "+userlist.get(position).getEmail());
        holder.userid.setText("ID: "+userlist.get(position).getUserid());

        // Go to update
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent(context, UpdateActivity.class);
                data.putExtra("ID",userlist.get(position).getUserid());
                data.putExtra("Name",userlist.get(position).getName());
                data.putExtra("Email",userlist.get(position).getEmail());
                context.startActivity(data);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    class Holder extends RecyclerView.ViewHolder
    {
        private TextView userid,name,email;
        private ImageView delete,update;

        public Holder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            userid = itemView.findViewById(R.id.userid);
            name = itemView.findViewById(R.id.username);
            email = itemView.findViewById(R.id.useremail);
            delete = itemView.findViewById(R.id.delete);
            update = itemView.findViewById(R.id.update);

            // delete call to back to activity
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (listener!= null)
                    {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(position);
                        }
                    }

                }
            });
        }
    }

    //interface
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    // this will call in mainactivity
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListerner = listener;
    }
}
