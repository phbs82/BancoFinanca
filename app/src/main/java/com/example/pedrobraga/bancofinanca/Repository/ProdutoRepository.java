package com.example.pedrobraga.bancofinanca.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.example.pedrobraga.bancofinanca.Dao.ProdutoDao;
import com.example.pedrobraga.bancofinanca.Database.AppDatabase;
import com.example.pedrobraga.bancofinanca.Entity.Produto;

import java.util.List;

/**
 * Created by pedro.braga on 19/04/2018.
 */

public class ProdutoRepository {

    private ProdutoDao produtoDao;
    private LiveData<List<String>> produtoAll;
    private int codigoproduto;
    private List<String> produtos;

    public ProdutoRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        produtoDao = db.produtoDao();
        produtoAll = new MutableLiveData<List<String>>();

    }


    public Integer getcodigoProduto() {

        return new getCodigoProduto().getcodigo();

    }

    public LiveData<List<String>> getProdutoAll() {

        produtoAll = produtoDao.loadallProduto();

        return  produtoAll;

    }


    private static  class getCodigoProduto extends AsyncTask<Void, Void, Integer> {

        private ProdutoDao asyncProdutoDao;
        private Integer codigo=1;


        @Override
        protected Integer doInBackground(Void... voids) {
            codigo = asyncProdutoDao.getCodigoProduto();

            if (codigo==null || codigo==0) {

                codigo = 1;

            }

            return codigo;
        }


        public int getcodigo() {

            return this.codigo;

        }

    }





    public void insert (Produto produto) {
        new ProdutoRepository.insertAsyncTask(produtoDao).execute(produto);
    }

    private static class insertAsyncTask extends AsyncTask<Produto, Void, Void> {

        private ProdutoDao asyncProdutoDao;

        insertAsyncTask(ProdutoDao dao) {
            asyncProdutoDao = dao;
        }

        @Override
        protected Void doInBackground(final Produto... params) {
            asyncProdutoDao.insert(params[0]);
            return null;
        }
    };


    public void update (Produto produto) {
        new ProdutoRepository.updateAsyncTask(produtoDao).execute(produto);
    }

    private static class updateAsyncTask extends AsyncTask<Produto, Void, Void> {

        private ProdutoDao asyncProdutoDao;

        updateAsyncTask(ProdutoDao dao) {
            asyncProdutoDao = dao;
        }

        @Override
        protected Void doInBackground(final Produto... params) {
            asyncProdutoDao.update(params[0]);
            return null;
        }
    }



    public void delete (Produto produto) {
        new ProdutoRepository.deleteAsyncTask(produtoDao).execute(produto);
    }

    private static class deleteAsyncTask extends AsyncTask<Produto, Void, Void> {

        private ProdutoDao asyncProdutoDao;

        deleteAsyncTask(ProdutoDao dao) {
            asyncProdutoDao = dao;
        }

        @Override
        protected Void doInBackground(final Produto... params) {
            asyncProdutoDao.delete(params[0]);
            return null;
        }
    }




}
