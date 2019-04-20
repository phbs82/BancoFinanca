package com.example.pedrobraga.bancofinanca.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.pedrobraga.bancofinanca.Dao.ItemDao;
import com.example.pedrobraga.bancofinanca.Database.AppDatabase;
import com.example.pedrobraga.bancofinanca.Entity.Item;

import java.util.List;

/**
 * Created by pedro.braga on 19/04/2018.
 */

public class ItemRepository {



    private ItemDao itemDao;
    private LiveData<List<Item>> itemAll;

    public ItemRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        itemDao = db.itemDao();
        itemAll = itemDao.loadallItem();

    }

    public LiveData<List<Item>> getItemAll() {
        return itemAll;
    }

    public void insert (Item item) {
        new ItemRepository.insertAsyncTask(itemDao).execute(item);
    }

    private static class insertAsyncTask extends AsyncTask<Item, Void, Void> {

        private ItemDao asyncItemDao;

        insertAsyncTask(ItemDao dao) {
            asyncItemDao = dao;
        }

        @Override
        protected Void doInBackground(final Item... params) {
            asyncItemDao.insert(params[0]);
            return null;
        }
    }


    public void update (Item item) {
        new ItemRepository.updateAsyncTask(itemDao).execute(item);
    }

    private static class updateAsyncTask extends AsyncTask<Item, Void, Void> {

        private ItemDao asyncItemDao;

        updateAsyncTask(ItemDao dao) {
            asyncItemDao = dao;
        }

        @Override
        protected Void doInBackground(final Item... params) {
            asyncItemDao.update(params[0]);
            return null;
        }
    }



    public void delete (Item item) {
        new ItemRepository.deleteAsyncTask(itemDao).execute(item);
    }

    private static class deleteAsyncTask extends AsyncTask<Item, Void, Void> {

        private ItemDao asyncItemDao;

        deleteAsyncTask(ItemDao dao) {
            asyncItemDao = dao;
        }

        @Override
        protected Void doInBackground(final Item... params) {
            asyncItemDao.delete(params[0]);
            return null;
        }
    }








}
