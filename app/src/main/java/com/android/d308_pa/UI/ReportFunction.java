package com.android.d308_pa.UI;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.d308_pa.R;
import com.android.d308_pa.database.Repository;
import com.android.d308_pa.entities.Vacations;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class ReportFunction extends AppCompatActivity {
    private Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_report_function);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView dateCreated = findViewById(R.id.report_timestamp);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US);
        LocalDateTime now = LocalDateTime.now();
        dateCreated.setText(dateTimeFormatter.format(now));

        RecyclerView recyclerView = findViewById(R.id.report_recycler_view);
        repository = new Repository(getApplication());
        List<Vacations> vacations;
        try {
            vacations = repository.getAllVacations();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ReportAdapter reportAdapter = new ReportAdapter(this);
        recyclerView.setAdapter(reportAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reportAdapter.setVacations(vacations);

    }

}