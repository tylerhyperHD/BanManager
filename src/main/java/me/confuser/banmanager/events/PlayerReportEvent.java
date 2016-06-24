package me.confuser.banmanager.events;

import lombok.Getter;
import me.confuser.banmanager.data.PlayerReportData;

@SuppressWarnings("FieldMayBeFinal")
public class PlayerReportEvent extends SilentCancellableEvent {

    @Getter
    private PlayerReportData report;

    public PlayerReportEvent(PlayerReportData report, boolean isSilent) {
        super(isSilent);
        this.report = report;
    }

}
