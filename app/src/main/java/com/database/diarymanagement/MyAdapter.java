package com.database.diarymanagement;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.myviewHolder> implements Filterable {

    ArrayList<SaveDiary> dataholder;
    ArrayList<SaveDiary> dataholderAll;
    Context context;

    public MyAdapter(Context context,ArrayList<SaveDiary> dataholder)
    {
        this.context = context;
        this.dataholder = dataholder;
        this.dataholderAll = new ArrayList<>(dataholder);

    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.index_sample_layout, parent, false);
        return new myviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewHolder holder, int position) {
        final SaveDiary saveDiary = dataholder.get(position);
        holder.date.setText(dataholder.get(position).getDate());
        holder.subject.setText(dataholder.get(position).getSubject());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sub, title, date, des;
                sub = saveDiary.getSubject();
                date = saveDiary.getDate();
                des = saveDiary.getDescription();
                Intent intent = new Intent(context, ReadPage.class);
                intent.putExtra("sub", sub);
                intent.putExtra("date", date);
                intent.putExtra("des", des);
                intent.putExtra("id", "adapter");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            ArrayList<SaveDiary> filteredList = new ArrayList<>();
            if (charSequence.toString().isEmpty()){
                filteredList.addAll(dataholderAll);
            }
            else {
                for (SaveDiary list : dataholderAll){
                    if (list.getSubject().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(list);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            dataholder.clear();
            dataholder.addAll((Collection<? extends SaveDiary>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class myviewHolder extends RecyclerView.ViewHolder {

        TextView date, subject;
        ImageView edit, delete;

        DatabaseManagement databaseManagement = new DatabaseManagement(context);

        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.SampleDateID);
            subject = itemView.findViewById(R.id.SampleSubject);
            subject.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            subject.setSelected(true);
            edit = itemView.findViewById(R.id.SEditButtonID);
            delete = itemView.findViewById(R.id.SDeleteButtonID);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setCancelable(false);
                        builder.setMessage("Are you sure ?");
                        builder.setPositiveButton(Html.fromHtml("<font color='#000000'>"+"Yes" + "</font"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int pos = getAbsoluteAdapterPosition();
                                SaveDiary saveDiary =dataholder.get(pos);
                                String subject =saveDiary.getSubject();
                                int checker = 2;
                                long reowId = databaseManagement.deleteSubjectWise(subject, checker);
                                Intent intent = new Intent(context, DiaryIndex.class);
                                context.startActivity(intent);

                            }
                        });
                        builder.setNegativeButton(Html.fromHtml("<font color='#000000'>"+"No" + "</font"),new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alert=builder.create();
                        alert.show();
                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = getAbsoluteAdapterPosition();
                    SaveDiary saveDiary =dataholder.get(pos);
                    String subject = saveDiary.getSubject();
                    String date = saveDiary.getDate();
                    String description = saveDiary.getDescription();
                    Intent intent = new Intent(context, EditingPage.class);
                    intent.putExtra("subject", subject);
                    intent.putExtra("date", date);
                    intent.putExtra("description", description);
                    context.startActivity(intent);
                }
            });
        }
    }
}
