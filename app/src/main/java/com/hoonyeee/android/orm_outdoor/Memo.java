package com.hoonyeee.android.orm_outdoor;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "memo")
public class Memo {
    @DatabaseField(generatedId = true)
    int id;
    @DatabaseField
    String title;
    @DatabaseField
    String memo;
    @DatabaseField
    String username;
    @DatabaseField
    long timestamp;

    public Memo(){

    }

    public Memo(String title, String memo, String username, long timestamp) {
        this.title = title;
        this.memo = memo;
        this.username = username;
        this.timestamp = timestamp;
    }
}
