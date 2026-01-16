package com.android.d308_pa.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.d308_pa.R;
import com.android.d308_pa.entities.Excursions;

import java.util.List;

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {
    private List<Excursions> mExcursions;
    private final Context context;
    private final LayoutInflater mInflater;

    private String vacationStart;

    private String vacationEnd;

    class ExcursionViewHolder extends RecyclerView.ViewHolder {
        private final TextView excursionItemView;

        private final TextView excursionItemView2;

        private ExcursionViewHolder(View itemView) {
            super(itemView);
            excursionItemView = itemView.findViewById(R.id.textView5);
            excursionItemView2 = itemView.findViewById(R.id.textView6);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAbsoluteAdapterPosition();
                    final Excursions current = mExcursions.get(position);
                    Intent intent = new Intent(context, ExcursionDetails.class);
                    intent.putExtra("id", current.getExcursionID());
                    intent.putExtra("name", current.getExcursionName());
                    intent.putExtra("price", current.getPrice());
                    intent.putExtra("vacationID", current.getVacationID());
                    intent.putExtra("vacationStart", vacationStart);
                    intent.putExtra("vacationEnd", vacationEnd);

                    context.startActivity(intent);
                }
            });
        }
    }
public ExcursionAdapter(Context context, String vacationStart, String vacationEnd){
    mInflater=LayoutInflater.from(context);
    this.context=context;
    this.vacationStart = vacationStart;
    this.vacationEnd = vacationEnd;
}
@Override
    public ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemView=mInflater.inflate(R.layout.excursion_list_item, parent, false);
    return new ExcursionViewHolder(itemView);
}
    @Override
    public void onBindViewHolder(@NonNull ExcursionAdapter.ExcursionViewHolder holder, int position) {
        if (mExcursions != null) {
            Excursions current = mExcursions.get(position);
            String name = current.getExcursionName();
            int vacaID = current.getVacationID();
            holder.excursionItemView.setText(name);
            holder.excursionItemView2.setText(Integer.toString(vacaID));
        } else {
            holder.excursionItemView.setText("No excursion name");
            holder.excursionItemView.setText("No vacation id");
        }
    }
    public void setExcursions(List<Excursions> excursions) {
        mExcursions=excursions;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mExcursions!=null) return mExcursions.size();
        else return 0;
    }
}
