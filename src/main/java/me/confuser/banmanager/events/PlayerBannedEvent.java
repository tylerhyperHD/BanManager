package me.confuser.banmanager.events;

import lombok.Getter;
import me.confuser.banmanager.data.PlayerBanData;

@SuppressWarnings("FieldMayBeFinal")
public class PlayerBannedEvent extends SilentEvent {

    @Getter
    private PlayerBanData ban;

    public PlayerBannedEvent(PlayerBanData ban, boolean isSilent) {
        super(isSilent);
        this.ban = ban;
    }

}
