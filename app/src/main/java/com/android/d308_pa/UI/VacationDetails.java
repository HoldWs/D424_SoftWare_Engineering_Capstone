package com.android.d308_pa.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.d308_pa.R;
import com.android.d308_pa.database.Repository;
import com.android.d308_pa.database.VacationBuilder;
import com.android.d308_pa.entities.Excursions;
import com.android.d308_pa.entities.Vacations;

import java.util.ArrayList;
import java.util.List;

public class VacationDetails extends AppCompatActivity {
    String name;
    double price;

    String hotel;
    int vacationID;
    int numExcursions;

    String vacationStart;

    String vacationEnd;
    Vacations currentVacation;
    EditText editName;
    EditText editPrice;

    EditText editHotel;

    TextView editVacaStart;

    TextView editVacaEnd;
    Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_details);
        Button button=findViewById(R.id.button2);

        editName=findViewById(R.id.titletext);
        editPrice=findViewById(R.id.pricetext);
        editHotel = findViewById(R.id.hoteltext);
        editVacaStart = findViewById(R.id.startvacationdate);
        editVacaEnd = findViewById(R.id.endvacationdate);
        vacationID= getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        hotel = getIntent().getStringExtra("hotel");
        price = getIntent().getDoubleExtra("price", 0.0);
        vacationStart = getIntent().getStringExtra("vacationStart");
        vacationEnd = getIntent().getStringExtra("vacationEnd");
        editName.setText(name);
        editHotel.setText(hotel);
        editPrice.setText(Double.toString(price));
        editVacaStart.setText(vacationStart);
        editVacaEnd.setText(vacationEnd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(VacationDetails.this, ExcursionDetails.class);
                intent.putExtra("vacationID", vacationID);
                intent.putExtra("vacationStart", vacationStart);
                intent.putExtra("vacationEnd", vacationEnd);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this, vacationStart, vacationEnd);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursions> filteredExcursions = new ArrayList<>();
        for (Excursions e: repository.getAllExcursions()) {
            if (e.getVacationID() == vacationID) {
                filteredExcursions.add(e);
            }
            excursionAdapter.setExcursions(filteredExcursions);
        }
        excursionAdapter.setExcursions(repository.getAllExcursions());


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_details, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.savevacation){
            Vacations vacation;
            if (vacationID==-1) {
                if (repository.getAllVacations().size() ==0) {
                    vacationID = 1;
                }
                else {
                    vacationID = repository.getAllVacations().get(repository.getAllVacations().size() -1).getVacationID() +1;
                }
                vacation = new Vacations(vacationID, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()), editHotel.getText().toString(), editVacaStart.getText().toString(), editVacaEnd.getText().toString());
                repository.insert(vacation);
                this.finish();
            }
            else {
                vacation = new Vacations(vacationID, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()), editHotel.getText().toString(), editVacaStart.getText().toString(), editVacaEnd.getText().toString());
                repository.update(vacation);
                this.finish();
            }
        }
        if (item.getItemId()== R.id.deletevacation)  {
           for (Vacations vacation : repository.getAllVacations()) {
               if (vacation.getVacationID() == vacationID) {
                   currentVacation = vacation;
               }
           }
           numExcursions = 0;
           for (Excursions excursion : repository.getAllExcursions()) {
               if (excursion.getVacationID()==vacationID) {
                   ++numExcursions;
               }
           }
           if (numExcursions == 0) {
               repository.delete(currentVacation);
               VacationDetails.this.finish();
           }
           else {
               Toast.makeText(VacationDetails.this, "There is an excursion associated with this vacation! Cancelling request.", Toast.LENGTH_LONG).show();
           }
        }
        return true;
    }
}