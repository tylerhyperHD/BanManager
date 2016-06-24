package me.confuser.banmanager.data;

import me.confuser.banmanager.storage.mysql.ByteArray;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;

@SuppressWarnings("FieldMayBeFinal")
@DatabaseTable
public class IpBanData {

    @Getter
    @DatabaseField(generatedId = true)
    private int id;
    @Getter
    @DatabaseField(canBeNull = false, columnDefinition = "INT UNSIGNED NOT NULL")
    private long ip;
    @Getter
    @DatabaseField(canBeNull = false)
    private String reason;
    @Getter
    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true, persisterClass = ByteArray.class, columnDefinition = "BINARY(16) NOT NULL")
    private PlayerData actor;

    // Should always be database time
    @Getter
    @DatabaseField(index = true, columnDefinition = "INT(10) NOT NULL")
    private long created = System.currentTimeMillis() / 1000L;
    @Getter
    @DatabaseField(index = true, columnDefinition = "INT(10) NOT NULL")
    private long updated = System.currentTimeMillis() / 1000L;
    @Getter
    @DatabaseField(index = true, columnDefinition = "INT(10) NOT NULL")
    private long expires = 0;

    IpBanData() {

    }

    public IpBanData(long ip, PlayerData actor, String reason) {
        this.ip = ip;
        this.reason = reason;
        this.actor = actor;
    }

    public IpBanData(long ip, PlayerData actor, String reason, long expires) {
        this.ip = ip;
        this.reason = reason;
        this.actor = actor;
        this.expires = expires;
    }

    public IpBanData(long ip, PlayerData actor, String reason, long expires, long created) {
        this.ip = ip;
        this.reason = reason;
        this.actor = actor;
        this.expires = expires;
        this.created = created;
    }

    public boolean hasExpired() {
        return getExpires() != 0 && getExpires() <= (System.currentTimeMillis() / 1000L);
    }
}
