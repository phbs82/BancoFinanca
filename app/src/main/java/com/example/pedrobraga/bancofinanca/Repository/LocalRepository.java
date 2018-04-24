package com.example.pedrobraga.bancofinanca.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.pedrobraga.bancofinanca.Dao.LocalDao;
import com.example.pedrobraga.bancofinanca.Database.AppDatabase;
import com.example.pedrobraga.bancofinanca.Entity.Local;

import java.util.List;

/**
 * Created by pedro.braga on 19/04/2018.
 */

public class LocalRepository {


    private LocalDao localDao;
    private LiveData<List<Local>> localAll;

    LocalRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        localDao = db.localDao();
        localAll = localDao.loadAllLocal();

    }

    LiveData<List<Local>> getLocalAll() {
        return localAll;
    }


    public void insert (Local local) {
        new insertAsyncTask(localDao).execute(local);
    }

    private static class insertAsyncTask extends AsyncTask<Local, Void, Void> {

        private LocalDao asyncLocalDao;

        insertAsyncTask(LocalDao dao) {
            asyncLocalDao = dao;
        }

        @Override
        protected Void doInBackground(final Local... params) {
            asyncLocalDao.insert(params[0]);
            return null;
        }
    }




}
