package scripts.tribot.MortFungus.Methods;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSObject;

import scripts.tribot.MortFungus.FungusMain;
import scripts.tribot.MortFungus.Node;
import scripts.tribot.MortFungus.Constants.Constants;

public class PickFungus extends Node {

	@Override
	public boolean isNodeValid() {
		return !Inventory.isFull() && Objects.find(6, "Fungi on log").length > 0;
	}

	@Override
	public void execute() {
		FungusMain.status = "Picking fungus";
		final RSObject[] FUNGUS_ON_LOG = Objects.findNearest(6, "Fungi on log");
		if (FUNGUS_ON_LOG.length > 0) {
			if (Clicking.click("Pick", FUNGUS_ON_LOG[0])) {
				Timing.waitCondition(new Condition() {
					@Override
					public boolean active() {
						General.sleep(100, 200);
						return Player.getAnimation() == Constants.FUNGUS_PICKING_ANIMATION
							   && !Player.isMoving();
					}
				}, General.random(2500, 2950));
			}
		}
	}
}
