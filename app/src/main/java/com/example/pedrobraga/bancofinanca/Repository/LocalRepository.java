package com.example.pedrobraga.bancofinanca.Repository;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.os.AsyncTask;

import com.example.pedrobraga.bancofinanca.Dao.LocalDao;
import com.example.pedrobraga.bancofinanca.Database.AppDatabase;
import com.example.pedrobraga.bancofinanca.Entity.Local;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by pedro.braga on 19/04/2018.
 */

public class LocalRepository {


    final private LocalDao localDao;
    private LiveData<List<Local>> localAll;
    private LiveData<Map> locais;


    public LocalRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        localDao = db.localDao();
        localAll = new MutableLiveData<List<Local>>();

    }


    public LiveData<Map> getMapLocais() {

        LiveData<Map> locais = null;
        try {
            locais = new loadMapLocais(localDao).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return locais;
    }


    private static  class loadMapLocais extends AsyncTask<Void, Void,LiveData<Map> > {


        private LocalDao asyncLocalDao;
        private LiveData<Map> locais;

        public loadMapLocais(LocalDao localDao) {
            this.asyncLocalDao = localDao;
        }


        @Override
        protected LiveData<Map> doInBackground(Void... voids) {


            locais =
                     Transformations.map(asyncLocalDao.loadAllLocal(), new Function<List<Local>, Map>() {
                        @Override
                        public Map apply(List<Local> input) {
                            Map mapLocais = new HashMap();
                            for(int i =0; i < input.size(); i++) {

                                mapLocais.put(input.get(i).getCodigolocal(),input.get(i).getDesclocal());
                            }
                            return mapLocais;
                        }
                    });

            return locais;
        }

    }

        public LiveData<List<Local>> getLocalAll() {

        return localDao.loadAllLocal();
    }

    private static  class getLocalAll extends AsyncTask<Void, Void, LiveData<List<Local>>> {

        private LocalDao asyncLocalDao;
        private LiveData<List<Local>> locais;

        public getLocalAll(LocalDao localDao) {
            this.asyncLocalDao = localDao;
        }

        @Override
        protected LiveData<List<Local>> doInBackground(Void... voids) {

            locais = asyncLocalDao.loadAllLocal();
            if (locais==null) {
                locais = new MutableLiveData<List<Local>>();
            }
            return locais;
        }

        public LiveData<List<Local>> getlocais() {
            return this.locais;

        }
    }

    public Long insert (Local local) {

        Long codigoproduto = null;

        try {
            codigoproduto = new LocalRepository.insertAsyncTask(localDao).execute(local).get().longValue();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return codigoproduto;


    }

    private static class insertAsyncTask extends AsyncTask<Local, Void, Long> {

        private LocalDao localDao;

        insertAsyncTask(LocalDao dao) {
            localDao = dao;
        }

        @Override
        protected Long doInBackground(final Local... params) {

            return localDao.insert(params[0]).longValue();
        }
    };



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





    public Integer getCodigo(String... local) {

        GetCodigoLocal getCodigoLocal = new GetCodigoLocal(localDao);
         getCodigoLocal.execute(local[0].toString());

        Integer codigo =getCodigoLocal.getCodigo();

       return codigo;

    }

    private  class GetCodigoLocal  extends AsyncTask<String, Void, Integer> {

        LocalDao asyncLocalDao;
        Integer codigo  = 1;
            GetCodigoLocal (LocalDao dao) {
            asyncLocalDao = dao;
        }
        @Override
        protected Integer doInBackground(String... local) {

            if (asyncLocalDao.getCodigo(local[0]) != null ) {

                this.codigo = asyncLocalDao.getCodigo(local[0]);

            }


            return this.codigo;
        }

        public Integer getCodigo() {

            return this.codigo;

        }


    }



 /*  public void insert (Local local) {

        new insertAsyncTask(localDao).execute(local);
    }*/

  /*  private static class insertAsyncTask extends AsyncTask<Local, Void, Void> {

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
*/





}
