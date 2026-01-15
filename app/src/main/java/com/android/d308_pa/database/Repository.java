package com.android.d308_pa.database;

import android.app.Application;

import com.android.d308_pa.DAO.ExcursionDAO;
import com.android.d308_pa.DAO.VacationDAO;
import com.android.d308_pa.entities.Excursions;
import com.android.d308_pa.entities.Vacations;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private ExcursionDAO mExcursionDAO;
    private VacationDAO mVacationDAO;

    private List<Vacations> mAllVacations;
    private List<Excursions> mAllExcursions;

    private static int NUMBER_OF_THREADS=4;
    static  final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        VacationBuilder db=VacationBuilder.getDatabase(application);
        mExcursionDAO= db.excursionDAO();
        mVacationDAO= db.vacationDAO();
    }

    public List<Vacations>getAllVacations(){
        databaseExecutor.execute(()->{
            mAllVacations=mVacationDAO.getAllVacations();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return mAllVacations;
    }
    public void insert(Vacations vacation){
        databaseExecutor.execute(()-> {
            mVacationDAO.insert(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Vacations vacation){
        databaseExecutor.execute(()-> {
            mVacationDAO.update(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Vacations vacation){
        databaseExecutor.execute(()-> {
            mVacationDAO.delete(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Excursions>getAllExcursions(){
        databaseExecutor.execute(()->{
            mAllExcursions=mExcursionDAO.getAllExcursions();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllExcursions;
    }

    public List<Excursions>getAssociatedExcursions(int vacationID){
        databaseExecutor.execute(()->{
            mAllExcursions=mExcursionDAO.getAssociatedExcursions(vacationID);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllExcursions;
    }
    public void insert(Excursions excursion){
        databaseExecutor.execute(()->{
            mExcursionDAO.insert(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Excursions excursion){
        databaseExecutor.execute(()->{
            mExcursionDAO.update(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Excursions excursion){
        databaseExecutor.execute(()->{
            mExcursionDAO.delete(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
