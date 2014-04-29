package com.changhong.smarthome.phone.cinema.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.changhong.smarthome.phone.cinema.entry.VideoFile;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DataHelper extends OrmLiteSqliteOpenHelper{
    //OrmliteVideofile.db
    private static final String DATABASE_NAME = "Ormlite2.db";
    public static int DATABASE_VERSION = 1;

    private Dao<VideoFile, String> simpledataDao = null;        

    public DataHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase arg0, ConnectionSource connectionSource) {
            try {
                    try {
                            TableUtils.createTable(connectionSource, VideoFile.class);
                    } catch (java.sql.SQLException e) {
                            e.printStackTrace();
                    }

            } catch (SQLException e) {
        e.printStackTrace();
            }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int arg2, int arg3) {
            System.out.println("onUpgrade()...");
            try
    {
        try {
                            TableUtils.dropTable(connectionSource, VideoFile.class, true);
                    } catch (java.sql.SQLException e) {
                            e.printStackTrace();
                    }
        onCreate(db, connectionSource);
    } catch (SQLException e)
    {
        e.printStackTrace();
    }
    }

    @Override
    public void close() {
            super.close();
    }

    public Dao<VideoFile, String> getSimpleDataDao() throws SQLException{
            if(simpledataDao == null){
                    try {
                            //Get a DAO for our class. 
                            //This uses the DaoManager to cache the DAO for future gets.
                            //��ȡjava bean��Ӧ��DAO
                            simpledataDao = getDao(VideoFile.class);
                    } catch (java.sql.SQLException e) {
                            e.printStackTrace();
                    }
            }
            return simpledataDao;
    }

}