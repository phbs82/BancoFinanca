package com.example.pedrobraga.bancofinanca.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.example.pedrobraga.bancofinanca.Dao.ProdutoDao;
import com.example.pedrobraga.bancofinanca.Database.AppDatabase;
import com.example.pedrobraga.bancofinanca.Entity.Produto;

import java.util.List;
import java.util.concurrent.ExecutionException;

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


    public Integer getcodigoProduto(String descricao) {

        getCodigoProduto getCode  = new getCodigoProduto(produtoDao);
        getCode.execute(descricao);

        return getCode.getcodigo()  ;

    }

    public LiveData<List<String>> getProdutoAll() {

        produtoAll = produtoDao.loadallProduto();

        return  produtoAll;

    }

    private static  class getCodigoProduto extends AsyncTask<String, Void, Integer> {

        private ProdutoDao asyncProdutoDao;
        private Integer codigo=0;


        private getCodigoProduto(ProdutoDao dao) {
            asyncProdutoDao = dao;
        }

        @Override
        protected Integer doInBackground(String... strings) {

            if (asyncProdutoDao.getCodigo(strings[0])==null) {

                this.codigo=1;

            }
            else {

                this.codigo = asyncProdutoDao.getCodigo(strings[0]);
            }

            return this.codigo;


        }

        public Integer getcodigo() {

            return this.codigo;

        }



    }


    public Long insert (Produto produto) {

        Long codigoproduto = null;

        try {
            codigoproduto = new ProdutoRepository.insertAsyncTask(produtoDao).execute(produto).get().longValue();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return codigoproduto;


    }

    private static class insertAsyncTask extends AsyncTask<Produto, Void, Long> {

        private ProdutoDao asyncProdutoDao;

        insertAsyncTask(ProdutoDao dao) {
            asyncProdutoDao = dao;
        }

        @Override
        protected Long doInBackground(final Produto... params) {

            return asyncProdutoDao.insert(params[0]).longValue();
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
