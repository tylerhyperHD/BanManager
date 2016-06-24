package me.confuser.banmanager.events;

import lombok.Getter;
import me.confuser.banmanager.data.IpBanData;

@SuppressWarnings("FieldMayBeFinal")
public class IpBanEvent extends SilentCancellableEvent {

    @Getter
    private IpBanData ban;

    public IpBanEvent(IpBanData ban, boolean silent) {
        super(silent);
        this.ban = ban;
    }
}
