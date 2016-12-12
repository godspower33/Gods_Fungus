package scripts.tribot.MortFungus.Methods;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;

import scripts.tribot.MortFungus.FungusMain;
import scripts.tribot.MortFungus.Node;
import scripts.tribot.MortFungus.Constants.Constants;

public class CastBloom extends Node {

	@Override
	public boolean isNodeValid() {
		return Skills.SKILLS.PRAYER.getCurrentLevel() > 0 && Objects.findNearest(6, "Rotting log").length > 0 && !Inventory.isFull()
    	&& Player.getPosition().equals(Constants.FUNGUS_TILE) && Objects.find(6, "Fungi on log").length < 1;
	}

	@Override
	public void execute() {
		FungusMain.status = "Casting bloom";
		final RSItem[] SICKLE = Inventory.find(Constants.SICKLE);
		final RSObject[] FUNGI_ON_LOG = Objects.findNearest(6, "Fungi on log");
		if (SICKLE.length > 0) {
			if (Clicking.click("Cast Bloom", SICKLE[0])) {
				Timing.waitCondition(new Condition() {
					@Override
					public boolean active() {
						General.sleep(100, 250);
						return FUNGI_ON_LOG.length > 0&& FUNGI_ON_LOG[0].isOnScreen();
					}
				}, General.random(2500, 3250));
			}
		}
	}
}
