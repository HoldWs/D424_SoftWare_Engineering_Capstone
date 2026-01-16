package com.android.d308_pa.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.android.d308_pa.DAO.ExcursionDAO;
import com.android.d308_pa.DAO.VacationDAO;
import com.android.d308_pa.entities.Excursions;
import com.android.d308_pa.entities.Vacations;

@Database(entities = {Vacations.class, Excursions.class}, version = 2, exportSchema = false)
public abstract class VacationBuilder extends RoomDatabase {
    public abstract VacationDAO vacationDAO();
    public abstract ExcursionDAO excursionDAO();
    private static volatile VacationBuilder INSTANCE;

    static VacationBuilder getDatabase(final Context context){
        if (INSTANCE==null){
            synchronized (VacationBuilder.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),VacationBuilder.class, "MyVacationDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}
