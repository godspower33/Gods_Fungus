package scripts.tribot.MortFungus.Methods;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;

import scripts.tribot.MortFungus.Node;
import scripts.tribot.MortFungus.Constants.Constants;
import scripts.tribot.MortFungus.Utilities.EnterLeavePortalMethod;

public class UseCwPortal extends Node {

	@Override
	public boolean isNodeValid() {
		return (Constants.CW_BANK.contains(Player.getPosition()) && Inventory.getCount(Constants.FUNGUS_ID) < 1 && Skills.getCurrentLevel(Skills.SKILLS.PRAYER) <= 40)
				|| (Constants.CW_PORTAL.contains(Player.getPosition()));
	}

	@Override
	public void execute() {
		if (Constants.CW_BANK.contains(Player.getPosition())
				&& Inventory.getCount(Constants.FUNGUS_ID) < 1
				&& Skills.getCurrentLevel(Skills.SKILLS.PRAYER) <= 40) {
			EnterLeavePortalMethod.useCWPortal(true);
		}
		if (Constants.CW_PORTAL.contains(Player.getPosition())) {
			EnterLeavePortalMethod.useCWPortal(false);
		}
	}	
}
