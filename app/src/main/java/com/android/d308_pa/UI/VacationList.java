package com.android.d308_pa.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.android.d308_pa.entities.Excursions;
import com.android.d308_pa.entities.Vacations;


import java.util.List;

public class VacationList extends AppCompatActivity {
private Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_list);

        Button button=findViewById(R.id.addVacationButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(VacationList.this, VacationDetails.class);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        repository = new Repository(getApplication());
        List<Vacations> allVacations=repository.getAllVacations();
        final VacationAdapter vacationAdapter= new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);

        ImageButton backButton=findViewById(R.id.backtohome);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(VacationList.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_vacation_list, menu);
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        List<Vacations> allVacations = repository.getAllVacations();
        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        final VacationAdapter vacationAdapter=new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.lol){
            Toast.makeText(this, "Isn't planning your next vacation fun???", Toast.LENGTH_SHORT).show();
        }

        if(item.getItemId()==android.R.id.home){
            this.finish();
            return true;
        }
        return true;
    }

}