package com.hoonyeee.android.orm_outdoor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.util.List;

public class DBConnect extends OrmLiteSqliteOpenHelper {

    private static final String databaseName ="memo.db";
    private static final int databaseVersion = 1;

    public DBConnect(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try{
            TableUtils.createTable(connectionSource, Memo.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        // alter table 조회수 컬럼 추가
    }

    // Memo 테이블에 접근하기 위한 변수
    private Dao<Memo, Integer> memoDao = null;
    public Dao<Memo, Integer> getMemoDao() throws Exception{    // DB오류시 호출한 곳으로 예외전달
        if(memoDao == null)
            memoDao = getDao(Memo.class);
        return memoDao;
    }

    public void insert(String title, String content, String userName, long timestamp) throws Exception{
            Memo memo = new Memo(title, content, userName, timestamp);
            Dao<Memo, Integer> memoDao = getMemoDao();
            memoDao.create(memo); // insert
    }

    public List<Memo> selectAll() throws Exception{
        List<Memo> datas = null;

        Dao<Memo, Integer> memoDao = getMemoDao();
        datas = memoDao.queryForAll();

        return datas;
    }

    public void update(Memo memo) throws Exception{
        Dao<Memo, Integer> memoDao = getMemoDao();
        memoDao.update(memo);
    }

    public void delete(int id) throws Exception{
        Dao<Memo, Integer> memoDao = getMemoDao();
        memoDao.deleteById(id);
    }
}
