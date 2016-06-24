package me.confuser.banmanager.events;

import lombok.Getter;
import me.confuser.banmanager.data.PlayerWarnData;

@SuppressWarnings("FieldMayBeFinal")
public class PlayerWarnedEvent extends SilentEvent {

    @Getter
    private PlayerWarnData warning;

    public PlayerWarnedEvent(PlayerWarnData warning, boolean silent) {
        super(silent);
        this.warning = warning;
    }
}
