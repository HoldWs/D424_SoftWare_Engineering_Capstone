package com.android.d308_pa.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.android.d308_pa.entities.Vacations;

import java.util.List;

@Dao
public interface VacationDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Vacations vacation);

    @Update
    void update(Vacations vacation);

    @Delete
    void delete(Vacations vacation);

    @Query("SELECT * FROM VACATIONS ORDER BY vacationID ASC")
    List<Vacations> getAllVacations();
}
