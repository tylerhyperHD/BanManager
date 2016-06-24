package me.confuser.banmanager.storage.global;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.j256.ormlite.table.TableUtils;
import me.confuser.banmanager.BanManager;
import me.confuser.banmanager.data.global.GlobalPlayerMuteRecordData;
import me.confuser.banmanager.util.DateUtils;

import java.sql.SQLException;

public class GlobalPlayerMuteRecordStorage extends BaseDaoImpl<GlobalPlayerMuteRecordData, Integer> {

    public GlobalPlayerMuteRecordStorage(ConnectionSource connection) throws SQLException {
        super(connection, (DatabaseTableConfig<GlobalPlayerMuteRecordData>) BanManager.getPlugin().getConfiguration()
                .getGlobalDb()
                .getTable("playerUnmutes"));

        if (!this.isTableExists()) {
            TableUtils.createTable(connection, tableConfig);
        }
    }

    public CloseableIterator<GlobalPlayerMuteRecordData> findUnmutes(long fromTime) throws SQLException {
        if (fromTime == 0) {
            return iterator();
        }

        long checkTime = fromTime + DateUtils.getTimeDiff();

        QueryBuilder<GlobalPlayerMuteRecordData, Integer> query = queryBuilder();
        query.setWhere(query.where().ge("created", checkTime));

        return query.iterator();

    }
}
