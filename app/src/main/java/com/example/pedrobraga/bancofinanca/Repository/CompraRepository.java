package com.example.pedrobraga.bancofinanca.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.pedrobraga.bancofinanca.Dao.CompraDao;
import com.example.pedrobraga.bancofinanca.Database.AppDatabase;
import com.example.pedrobraga.bancofinanca.Entity.Compra;

import java.util.List;

/**
 * Created by pedro.braga on 19/04/2018.
 */

public class CompraRepository    {



    private CompraDao compraDao;
    private LiveData<List<Compra>> compraAll;

    public CompraRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        compraDao = db.compraDao();
        compraAll = compraDao.loadAllCompra();

    }

   public  LiveData<List<Compra>> getCompraAll() {
        return compraAll;
    }


    public void insert (Compra compra) {
        new CompraRepository.insertAsyncTask(compraDao).execute(compra);
    }

    private static class insertAsyncTask extends AsyncTask<Compra, Void, Void> {

        private CompraDao asyncCompraDao;

        insertAsyncTask(CompraDao dao) {
            asyncCompraDao = dao;
        }

        @Override
        protected Void doInBackground(final Compra... params) {
            asyncCompraDao.insert(params[0]);
            return null;
        }
    }


    public void update (Compra compra) {
        new CompraRepository.updateAsyncTask(compraDao).execute(compra);
    }

    private static class updateAsyncTask extends AsyncTask<Compra, Void, Void> {

        private CompraDao asyncCompraDao;

        updateAsyncTask(CompraDao dao) {
            asyncCompraDao = dao;
        }

        @Override
        protected Void doInBackground(final Compra... params) {
            asyncCompraDao.update(params[0]);
            return null;
        }
    }



    public void deelte (Compra compra) {
        new CompraRepository.deleteAsyncTask(compraDao).execute(compra);
    }

    private static class deleteAsyncTask extends AsyncTask<Compra, Void, Void> {

        private CompraDao asyncCompraDao;

        deleteAsyncTask(CompraDao dao) {
            asyncCompraDao = dao;
        }

        @Override
        protected Void doInBackground(final Compra... params) {
            asyncCompraDao.delete(params[0]);
            return null;
        }
    }






}
