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



    public int getcodigoCompra() {

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

            System.out.println("********************************");
            System.out.println(params[0]);
            System.out.println(params[0].getCodigolocal());
            System.out.println(params[0].getCodigocompra());


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
