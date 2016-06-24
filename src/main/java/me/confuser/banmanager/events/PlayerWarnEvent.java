package me.confuser.banmanager.events;

import lombok.Getter;
import me.confuser.banmanager.data.PlayerWarnData;

@SuppressWarnings("FieldMayBeFinal")
public class PlayerWarnEvent extends SilentCancellableEvent {

    @Getter
    private PlayerWarnData warning;

    public PlayerWarnEvent(PlayerWarnData warning, boolean silent) {
        super(silent);
        this.warning = warning;
    }
}
