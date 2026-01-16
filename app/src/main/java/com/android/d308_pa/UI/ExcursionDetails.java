package com.android.d308_pa.UI;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.d308_pa.R;
import com.android.d308_pa.database.Repository;
import com.android.d308_pa.entities.Excursions;
import com.android.d308_pa.entities.Vacations;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ExcursionDetails extends AppCompatActivity {

    String title;
    Double price;

    int excursionID;

    int vacationID;

    EditText editName;
    String excursionDate;

    EditText editPrice;
    EditText editDate;
    Excursions currentExcursion;

    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_excursion_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        repository = new Repository(getApplication());
        title = getIntent().getStringExtra("name");
        editName = findViewById(R.id.excursionTitle);
        editName.setText(title);
        price = getIntent().getDoubleExtra("price", 0.0);
        editPrice = findViewById(R.id.excursionPrice);
        editPrice.setText(Double.toString(price));
        excursionID = getIntent().getIntExtra("id", -1);
        vacationID = getIntent().getIntExtra("vacationID", -1);
        excursionDate = getIntent().getStringExtra("excursionDate");
        editDate=findViewById(R.id.excursiondate);
        editDate.setText(excursionDate);
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        ArrayList<Vacations> vacationArrayList = new ArrayList<>();
        vacationArrayList.addAll(repository.getAllVacations());
        ArrayList<Integer> vacationIdList = new ArrayList<>();
        for (Vacations vacation : vacationArrayList) {
            vacationIdList.add(vacation.getVacationID());
        }
        ArrayAdapter<Integer> vacationIdAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, vacationIdList);
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(vacationIdAdapter);
        spinner.setSelection(vacationID - 1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position < vacationIdList.size()) {
                    vacationID = vacationIdList.get(position);
                } else {
                    Log.e("DebugTag", "Invalid position: " + position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursion_details, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        if (item.getItemId() == R.id.excursionsave) {
            Excursions excursion;
            if (excursionID == -1) {
                if (repository.getAllExcursions().size() == 0)
                    excursionID = 1;
                else
                    excursionID = repository.getAllExcursions().get(repository.getAllExcursions().size() - 1).getExcursionID() + 1;
                excursion = new Excursions(excursionID, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()), vacationID, editDate.getText().toString());
                repository.insert(excursion);
                this.finish();
            } else {
                excursion = new Excursions(excursionID, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()), vacationID, editDate.getText().toString());
                repository.update(excursion);
                this.finish();
            }
            return true;
        }
        if (item.getItemId() == R.id.excursiondelete) {
            for (Excursions e: repository.getAllExcursions()) {
                if (e.getExcursionID() == excursionID) currentExcursion = e;
            }
            repository.delete(currentExcursion);
            this.finish();
        }
        return true;
    }
}