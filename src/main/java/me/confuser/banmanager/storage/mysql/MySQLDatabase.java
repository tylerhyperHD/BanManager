package me.confuser.banmanager.storage.mysql;

import com.j256.ormlite.db.MysqlDatabaseType;

public class MySQLDatabase extends MysqlDatabaseType {

    public MySQLDatabase() {
        setCreateTableSuffix("ENGINE=MyISAM DEFAULT CHARSET=utf8");
    }
}
