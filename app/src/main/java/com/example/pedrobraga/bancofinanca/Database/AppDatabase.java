package com.example.pedrobraga.bancofinanca.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.example.pedrobraga.bancofinanca.Dao.CompraDao;
import com.example.pedrobraga.bancofinanca.Dao.ItemDao;
import com.example.pedrobraga.bancofinanca.Dao.LocalDao;
import com.example.pedrobraga.bancofinanca.Dao.ProdutoDao;
import com.example.pedrobraga.bancofinanca.Entity.Compra;
import com.example.pedrobraga.bancofinanca.Entity.Item;
import com.example.pedrobraga.bancofinanca.Entity.Local;
import com.example.pedrobraga.bancofinanca.Entity.Produto;
import com.example.pedrobraga.bancofinanca.Utils.DateTypeConverter;

/**
 * Created by pedro.braga on 19/04/2018.
 */
@TypeConverters(DateTypeConverter.class)
@Database(entities = {Produto.class,Item.class,Local.class,Compra.class}, version = 2,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

        public abstract CompraDao compraDao();
        public abstract ItemDao itemDao();
        public abstract ProdutoDao produtoDao();
        public abstract LocalDao localDao();


        private static AppDatabase INSTANCE;


        public static AppDatabase getDatabase(final Context context) {
            if (INSTANCE == null) {
                synchronized (AppDatabase.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                AppDatabase.class, "financas")

                                .build();

                    }
                }
            }
            return INSTANCE;
        }


    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.

        }
    };




}






