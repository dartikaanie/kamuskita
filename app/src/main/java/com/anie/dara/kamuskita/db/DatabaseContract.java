package com.anie.dara.kamuskita.db;

import android.provider.BaseColumns;

public class DatabaseContract {

    private DatabaseContract() {
    }

    public static class EngDo implements BaseColumns {
        public static final String TABLE_NAME = "engdo";
        public static final String COLUMN_NAME_KEYWORD = "keyword";
        public static final String COLUMN_NAME_ARTI= "arti";
    }

    public static class DoEng implements BaseColumns {
        public static final String TABLE_NAME = "doeng";
        public static final String KEY = "keyword";
        public static final String ARTI= "arti";
    }
}
