package scripts.tribot.MortFungus.Utilities;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSObject;

import scripts.tribot.MortFungus.FungusMain;
import scripts.tribot.MortFungus.Constants.Constants;

public class EnterLeavePortalMethod {

    // Enter/exit the castle wars portal to restore health/pray
    public static void useCWPortal(final boolean enter) {
        FungusMain.status = "Entering CW portal";
        final RSObject[] CW_PORTAL = Objects.findNearest(20, enter ? Constants.ZAMMY_PORTAL_ENTRANCE : Constants.ZAMMY_PORTAL_EXIT);  
        if (CW_PORTAL.length > 0) {
            final RSObject portal = CW_PORTAL[0];
            if (!portal.isOnScreen())
                Camera.turnToTile(portal);
            if(!portal.isOnScreen())
            	Walking.walkTo(portal);
            if (DynamicClicking.clickRSObject(portal, enter ? "Enter" : "Exit"))
                Timing.waitCondition(new Condition() {
                    @Override
                    public boolean active() {
                        General.sleep(100);
                        return enter ? Constants.CW_PORTAL.contains(Player.getPosition()) : Constants.CW_BANK.contains(Player.getPosition());
                    }
                }, General.random(4800, 6000));
        }
    }
}
