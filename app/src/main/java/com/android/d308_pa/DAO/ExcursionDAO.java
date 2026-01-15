package com.android.d308_pa.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.android.d308_pa.entities.Excursions;

import java.util.List;

@Dao
public interface ExcursionDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Excursions excursion);

    @Update
    void update(Excursions excursion);

    @Delete
    void delete(Excursions excursion);

    @Query("SELECT * FROM EXCURSIONS ORDER BY excursionID ASC")
    List<Excursions> getAllExcursions();

    @Query("SELECT * FROM EXCURSIONS WHERE vacationID=:prod ORDER BY excursionID ASC")
    List<Excursions> getAssociatedExcursions(int prod);
}
