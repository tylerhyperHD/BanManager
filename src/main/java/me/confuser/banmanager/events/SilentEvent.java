package me.confuser.banmanager.events;

import lombok.Getter;
import lombok.Setter;

public abstract class SilentEvent extends CustomEvent {

    @Getter
    @Setter
    private boolean silent = false;

    public SilentEvent(boolean silent) {
        this.silent = silent;
    }

}
