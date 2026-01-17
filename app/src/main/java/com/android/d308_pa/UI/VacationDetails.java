package com.android.d308_pa.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.android.d308_pa.entities.Excursions;
import com.android.d308_pa.entities.Vacations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VacationDetails extends AppCompatActivity {
    String name;
    double price;
    static int notificationID;
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

    DatePickerDialog.OnDateSetListener startVacationDate;
    DatePickerDialog.OnDateSetListener endVacationDate;

    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();

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
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(VacationDetails.this, ExcursionDetails.class);
                intent.putExtra("vacationID", vacationID);
                startActivity(intent);
            }
        });
        Button button1 = findViewById(R.id.button4);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
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

        startVacationDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelStart();
            }
        };

        endVacationDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, monthOfYear);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelEnd();
            }
        };

        editVacaStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editVacaStart.getText().toString();
                if(info.equals("")) {
                    info = "01/15/26";
                }
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(VacationDetails.this, startVacationDate, myCalendarStart.get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        editVacaEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editVacaEnd.getText().toString();
                if(info.equals("")) {
                    info = "01/15/26";
                }
                try {
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DatePickerDialog datePickerDialog = new DatePickerDialog(VacationDetails.this, endVacationDate, myCalendarStart.get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(myCalendarStart.getTimeInMillis());
                datePickerDialog.show();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void updateLabelStart() {
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf= new SimpleDateFormat(dateFormat, Locale.US);
        editVacaStart.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateLabelEnd() {
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        editVacaEnd.setText(sdf.format(myCalendarEnd.getTime()));
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_details, menu);
        return true;
    }
    private void scheduleAlarm(AlarmManager alarmManager, long triggerTime, String message, int notificationID) {
        Intent intent = new Intent(VacationDetails.this, VacationReceiver.class);
        intent.putExtra("key", message);
        intent.putExtra("notification_id", notificationID);
        PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, notificationID, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, sender);
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
                String dateFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
                Date startDate = null;
                Date endDate = null;
                try {
                    startDate = sdf.parse(editVacaStart.getText().toString());
                    endDate = sdf.parse(editVacaEnd.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (startDate.after(endDate)) {
                    Toast.makeText(VacationDetails.this, "You can't choose a day before the end of the vacation as the start!", Toast.LENGTH_LONG).show();
                }
                else {
                    vacation = new Vacations(vacationID, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()), editHotel.getText().toString(), editVacaStart.getText().toString(), editVacaEnd.getText().toString());
                    repository.insert(vacation);
                    this.finish();
                }
            }
            else {
                String dateFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
                Date startDate = null;
                Date endDate = null;
                try {
                    startDate = sdf.parse(editVacaStart.getText().toString());
                    endDate = sdf.parse(editVacaEnd.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (startDate.after(endDate)) {
                    Toast.makeText(VacationDetails.this, "You can't choose a day before the end of the vacation as the start!", Toast.LENGTH_LONG).show();
                }
                else {
                    vacation = new Vacations(vacationID, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()), editHotel.getText().toString(), editVacaStart.getText().toString(), editVacaEnd.getText().toString());
                    repository.update(vacation);
                    this.finish();
                }
            }
            return true;
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
           return true;
        }
        if (item.getItemId() == R.id.notifyvacation) {
            String startDate = editVacaStart.getText().toString();
            String endDate = editVacaEnd.getText().toString();
            String dateFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
            Date myStartDate = null;
            Date myEndDate = null;
            try {
                myStartDate = sdf.parse(startDate);
                myEndDate = sdf.parse(endDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);


                scheduleAlarm(alarmManager, myStartDate.getTime(), "This vacation is starting soon!: " + name, notificationID++);


                scheduleAlarm(alarmManager, myEndDate.getTime(), "This vacation is ending soon!: " + name, notificationID++);

            } catch (Exception e) {
                e.printStackTrace();
            }
            Toast.makeText(this, "Alarm Set!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(item.getItemId() == R.id.vacationshare) {
            Intent sendIntent = new Intent();
            List<Excursions> filteredExcursions = new ArrayList<>();
            for (Excursions e: repository.getAllExcursions()) {
                if (e.getVacationID() == vacationID) filteredExcursions.add(e);
            }
            StringBuilder excursionsDetails = new StringBuilder();
            for (Excursions e: filteredExcursions) {
                excursionsDetails.append("Excursion Name: ")
                        .append(e.getExcursionName())
                        .append(", Price: $")
                        .append(e.getPrice())
                        .append("\n");
            }
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "These are the vacation details! Vacation ID: " + vacationID + ", name: " +
                    editName.getText().toString() + ", price: $" +
                    Double.parseDouble(editPrice.getText().toString()) + ", the hotel name: " +
                    editHotel.getText().toString() + ", this is our start date: " +
                    editVacaStart.getText().toString() + ", this is our end date: " +
                    editVacaEnd.getText().toString() + ", associated excursions: " +  excursionsDetails
                    + " Let us know what you think!");
            sendIntent.putExtra(Intent.EXTRA_TITLE, editName.getText().toString() + "EXTRA_TITLE");
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursions> filteredExcursions = new ArrayList<>();
        for (Excursions e: repository.getAllExcursions()) {
            if (e.getVacationID() == vacationID) filteredExcursions.add(e);
        }
        excursionAdapter.setExcursions(filteredExcursions);
        }

    }
