package scripts.tribot.MortFungus.Methods;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSItem;

import scripts.tribot.MortFungus.FungusMain;
import scripts.tribot.MortFungus.Node;
import scripts.tribot.MortFungus.Constants.Constants;
import scripts.tribot.MortFungus.Utilities.BankingUtilities;

public class Bank extends Node {

	@Override
	public boolean isNodeValid() {
		return (Constants.CW_BANK.contains(Player.getPosition()) && BankingUtilities.checkInv())
				|| ((Skills.getCurrentLevel(Skills.SKILLS.PRAYER) < 1 && !Constants.CW_BANK.contains(Player.getPosition())) 
				|| (Inventory.isFull() && !Constants.CW_BANK.contains(Player.getPosition())));
	}

	@Override
	public void execute() {
		FungusMain.status = "Banking";
		if (!Equipment.isEquipped(Constants.RINGS_OF_DUELING) && !Constants.CW_BANK.contains(Player.getPosition())) {
			equipRing();
		}
		if (Equipment.isEquipped(Constants.RINGS_OF_DUELING) && 
			(!Constants.CW_BANK.contains(Player.getPosition()) && !Constants.CW_PORTAL.contains(Player.getPosition()))) {
			useDuelRing();
		}
		if (Constants.CW_BANK.contains(Player.getPosition())) {
			FungusMain.status = "Banking";
			if (!Banking.isBankScreenOpen()) {
				if (Banking.openBank()) {
					if (BankingUtilities.isBankItemsLoaded()) {
						if (BankingUtilities.suppliesStill()) {
							General.println("You've run out of supplies, stopping script");
							FungusMain.stopScript = true;
						}
					}
				}
			}
		}
	}

	public static void equipRing() {
		RSItem[] duelRingsInInv = Inventory.find(Constants.RINGS_OF_DUELING);
		if (duelRingsInInv.length > 0) {
			if (Clicking.click("Wear", duelRingsInInv[0])) {
				Timing.waitCondition(new Condition() {
					public boolean active() {
						General.sleep(100, 200);
						return Equipment.isEquipped(Constants.RINGS_OF_DUELING);
					}
				}, General.random(1500, 2500));
			}
		}
	}

	private void useDuelRing() {
		RSItem[] duelRingEquipped = Equipment.find(Constants.RINGS_OF_DUELING);
		if (duelRingEquipped.length > 0) {
			if (Clicking.click("Castle Wars", duelRingEquipped[0])) {
				Timing.waitCondition(new Condition() {
					@Override
					public boolean active() {
						General.sleep(100, 200);
						return Constants.CW_BANK.contains(Player.getPosition());
					}
				}, General.random(4800, 6000));
			}
		}
	}
}
