package com.example.pedrobraga.bancofinanca.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.example.pedrobraga.bancofinanca.Dao.LocalDao;
import com.example.pedrobraga.bancofinanca.Database.AppDatabase;
import com.example.pedrobraga.bancofinanca.Entity.Local;

import java.util.List;

/**
 * Created by pedro.braga on 19/04/2018.
 */

public class LocalRepository {


    final private LocalDao localDao;
    private LiveData<List<Local>> localAll;

    public LocalRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        localDao = db.localDao();
      //  localAll = localDao.loadAllLocal();

    }

    public LiveData<List<Local>> getLocalAll() {
        return localAll;
    }

    public MutableLiveData<Local> getProdutoAll() {

        return new getLocalAll().getlocais();
    }

    public Integer getCodigo(String... local) {

        GetCodigoLocal getCodigoLocal = new GetCodigoLocal();
         getCodigoLocal.execute(local[0]);

        Integer codigo =getCodigoLocal.getCodigo();




        return codigo;

    }

    private  class GetCodigoLocal  extends AsyncTask<String, Void, Integer> {

        LocalDao localDao;
        Integer codigo  = 0;

        @Override
        protected Integer doInBackground(String... local) {

                try {
                    codigo = localDao.getCodigo(local[0]);
                }
                catch (Exception e ) {

                    if (codigo == null) {

                        Local localidade = new Local();
                        localidade.setDesclocal(local[0]);
                        insert(localidade);

                    }
                }
                return codigo;
        }

        public Integer getCodigo() {

            return this.codigo;

        }


    }



    private static  class getLocalAll extends AsyncTask<Void, Void, MutableLiveData<Local>> {

        private LocalDao asyncLocalDao;
        private MutableLiveData<Local> locais;

        @Override
        protected MutableLiveData<Local> doInBackground(Void... voids) {

            locais.setValue(asyncLocalDao.loadLocal().getValue());

            if (locais==null) {

                locais = new MutableLiveData<Local>();

            }

            return locais;
        }


        public MutableLiveData<Local> getlocais() {

            return this.locais;

        }

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


    public void update (Local local) {
        new updateAsyncTask(localDao).execute(local);
    }

    private static class updateAsyncTask extends AsyncTask<Local, Void, Void> {

        private LocalDao asyncLocalDao;

        updateAsyncTask(LocalDao dao) {
            asyncLocalDao = dao;
        }

        @Override
        protected Void doInBackground(final Local... params) {
            asyncLocalDao.update(params[0]);
            return null;
        }
    }



    public void delete (Local local) {
        new deleteAsyncTask(localDao).execute(local);
    }

    private static class deleteAsyncTask extends AsyncTask<Local, Void, Void> {

        private LocalDao asyncLocalDao;

        deleteAsyncTask(LocalDao dao) {
            asyncLocalDao = dao;
        }

        @Override
        protected Void doInBackground(final Local... params) {
            asyncLocalDao.delete(params[0]);
            return null;
        }
    }










}
