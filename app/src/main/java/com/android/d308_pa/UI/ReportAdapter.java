package com.android.d308_pa.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.d308_pa.R;
import com.android.d308_pa.entities.Vacations;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {
    private List<Vacations> vacations;

    private final Context context;
    private final LayoutInflater inflater;

    public ReportAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class ReportViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameItemView;
        private final TextView startDateItemView;
        private final TextView endDateItemView;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);

            nameItemView = itemView.findViewById(R.id.report_name_text);
            startDateItemView = itemView.findViewById(R.id.report_start_date_text);
            endDateItemView = itemView.findViewById(R.id.report_end_date_text);

        }
    }

    @NonNull
    @Override
    public ReportAdapter.ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.report_list_item, parent, false);
        return new ReportViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.ReportViewHolder holder, int position) {
        if (vacations != null) {
            Vacations current = vacations.get(position);
            String name = current.getVacationName();
            String startDate = current.getVacationStart();
            String endDate = current.getVacationEnd();

            holder.nameItemView.setText(name);
            holder.startDateItemView.setText(startDate);
            holder.endDateItemView.setText(endDate);

        }
    }

    @Override
    public int getItemCount() {
        if (vacations != null) {
            return vacations.size();
        }
        else {
            return 0;
        }
    }

    public void setVacations(List<Vacations> vacations) {
        this.vacations = vacations;
        notifyDataSetChanged();
    }
}
