package com.android.d308_pa.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.d308_pa.R;
import com.android.d308_pa.database.Repository;
import com.android.d308_pa.entities.Excursions;
import com.android.d308_pa.entities.Vacations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExcursionDetails extends AppCompatActivity {

    String title;
    Double price;

    int excursionID;

    int vacationID;

    String vacationStart;

    String vacationEnd;

    EditText editName;
    String excursionDate;

    EditText editPrice;
    TextView editDate;
    Excursions currentExcursion;

    Vacations currentVacation;

    Repository repository;
    Date startDate = null;
    Date endDate = null;

    DatePickerDialog.OnDateSetListener dateStart;

    final Calendar myCalendarStart = Calendar.getInstance();


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
        vacationStart = getIntent().getStringExtra("vacationStart");
        vacationEnd = getIntent().getStringExtra("vacationEnd");
        editDate = findViewById(R.id.excursiondate);
        editDate.setText(excursionDate);
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        Button button = findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

        dateStart = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelStart();
            }
        };

        if (vacationStart != null && vacationEnd != null) {

            try {
                startDate = sdf.parse(vacationStart);
                endDate = sdf.parse(vacationEnd);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("ExcursionDetails", "The value for the vacation start or end is null");
        }

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editDate.getText().toString();
                if (info.equals("")) {
                    info = "01/15/26";
                }
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ExcursionDetails.this, dateStart, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH));
                if (startDate != null) {
                    datePickerDialog.getDatePicker().setMinDate(startDate.getTime());
                }
                if (endDate != null) {
                    datePickerDialog.getDatePicker().setMaxDate(endDate.getTime());
                }
                datePickerDialog.show();
            }
        });
    }

    private void updateLabelStart() {
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        editDate.setText(sdf.format(myCalendarStart.getTime()));
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
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
            Date excurCompDate = null;
            Date vacaStart = null;
            Date vacaEnd = null;
            if (excursionID == -1) {
                if (repository.getAllExcursions().size() == 0)
                    excursionID = 1;
                else
                    excursionID = repository.getAllExcursions().get(repository.getAllExcursions().size() - 1).getExcursionID() + 1;
                excursion = new Excursions(excursionID, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()), vacationID, editDate.getText().toString());
                for (Vacations vacation : repository.getAllVacations()) {
                    if (vacation.getVacationID() == excursion.getVacationID()) {
                        currentVacation = vacation;
                    }
                }
                try {
                    excurCompDate = sdf.parse(excursion.getExcursionDate());
                    vacaStart = sdf.parse(currentVacation.getVacationStart());
                    vacaEnd = sdf.parse(currentVacation.getVacationEnd());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if ((excurCompDate.before(vacaStart) || excurCompDate.after(vacaEnd))) {

                    Toast.makeText(this, "Choose a date within the range of your vacation!", Toast.LENGTH_LONG).show();
                } else {
                    repository.insert(excursion);
                    Toast.makeText(this, "Excursion Added! Return to the Details Screen to continue planning, or stay here to edit your excursion!", Toast.LENGTH_LONG).show();
                }
            } else {
                excursion = new Excursions(excursionID, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()), vacationID, editDate.getText().toString());
                for (Vacations vacation : repository.getAllVacations()) {
                    if (vacation.getVacationID() == vacationID) {
                        currentVacation = vacation;
                    }
                }
                try {
                    excurCompDate = sdf.parse(excursion.getExcursionDate());
                    vacaStart = sdf.parse(currentVacation.getVacationStart());
                    vacaEnd = sdf.parse(currentVacation.getVacationEnd());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if ((excurCompDate.before(vacaStart) || excurCompDate.after(vacaEnd))) {

                    Toast.makeText(this, "Choose a date within the range of your vacation!", Toast.LENGTH_LONG).show();
                } else {
                    repository.update(excursion);
                    Toast.makeText(this, "Update successful! Returning to Vacation Details", Toast.LENGTH_SHORT).show();
                    this.finish();
                }
            }
            return true;
        }
        if (item.getItemId() == R.id.excursiondelete) {
            for (Excursions e : repository.getAllExcursions()) {
                if (e.getExcursionID() == excursionID) currentExcursion = e;
            }
            repository.delete(currentExcursion);
            Toast.makeText(this, "Excursion deleted! Returning to Vacation Details", Toast.LENGTH_SHORT).show();
            this.finish();
        }
        if (item.getItemId() == R.id.excursionnotify) {
            String dateFromScreen = editDate.getText().toString();
            String dateFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
            Date myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                Long trigger = myDate.getTime();
                Intent intent = new Intent(ExcursionDetails.this, ExcursionReceiver.class);
                intent.putExtra("key", "Excursion title: " + getIntent().getStringExtra("name"));
                PendingIntent sender = PendingIntent.getBroadcast(ExcursionDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            } catch (Exception e) {
            }
            Toast.makeText(this, "Alarm set!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return true;
    }
}