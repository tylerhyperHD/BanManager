package me.confuser.banmanager.events;

import lombok.Getter;
import me.confuser.banmanager.data.PlayerBanData;

@SuppressWarnings("FieldMayBeFinal")
public class PlayerBanEvent extends SilentCancellableEvent {

    @Getter
    private PlayerBanData ban;

    public PlayerBanEvent(PlayerBanData ban, boolean isSilent) {
        super(isSilent);
        this.ban = ban;
    }

}
