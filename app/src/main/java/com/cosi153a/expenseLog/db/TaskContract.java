package com.cosi153a.expenseLog.db;

/**
 * Created by apple on 10/21/15.
 */
import android.provider.BaseColumns;

public class TaskContract {
    public static final String DB_NAME = "com.cosi153a.expenseLog.db";
    public static final int DB_VERSION = 2;
    public static final String TABLE = "tasks";

    public class Columns {
        public static final String TITLE = "title";
        public static final String DETAILS = "details";
        public static final String _ID = BaseColumns._ID;
    }
}