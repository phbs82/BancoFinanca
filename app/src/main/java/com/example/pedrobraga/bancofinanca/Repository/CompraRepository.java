package com.example.pedrobraga.bancofinanca.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.example.pedrobraga.bancofinanca.Dao.CompraDao;
import com.example.pedrobraga.bancofinanca.Database.AppDatabase;
import com.example.pedrobraga.bancofinanca.Entity.Compra;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by pedro.braga on 19/04/2018.
 */

public class CompraRepository    {

    private CompraDao compraDao;
    private LiveData<List<Compra>> compraAll;

    public CompraRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        compraDao = db.compraDao();
        compraAll = new MutableLiveData<List<Compra>>();

    }


   public  LiveData<List<Compra>> getCompraAll() {

       return compraDao.loadAllCompra();

    }


    public Long insert (Compra compra) {
        
        Long codigocompra = null;
        
        try {
            codigocompra = new insertAsyncTask(compraDao).execute(compra).get().longValue();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        
        return codigocompra;
    }

    private static class insertAsyncTask extends AsyncTask<Compra, Void, Long> {

        private CompraDao asyncCompraDao;
        private Long codigocompra;

        insertAsyncTask(CompraDao dao) {
            asyncCompraDao = dao;
        }

        @Override
        protected Long doInBackground(final Compra... params) {

            return this.codigocompra = asyncCompraDao.insert(params[0]).longValue();

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



    public void delete (Compra compra) {
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


/*  public int getcodigoCompra() {

        return new getCodigoCompra().getcodigo();
    }

    private static  class getCodigoCompra extends AsyncTask<Void, Void, Integer> {

        private CompraDao asyncCompraDao;
        private int codigo;


        @Override
        protected Integer doInBackground(Void... voids) {
            codigo = asyncCompraDao.getCodigoCompra();
            return codigo;
        }


        public int getcodigo() {

            return this.codigo;

        }
    }*/



}
