package scripts.tribot.MortFungus.Methods;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;

import scripts.AntiBan;
import scripts.tribot.MortFungus.FungusMain;
import scripts.tribot.MortFungus.Node;
import scripts.tribot.MortFungus.Constants.Constants;
import scripts.tribot.MortFungus.Utilities.BankingUtilities;

public class BankDepositAndWithdraw extends Node {

	@Override
	public boolean isNodeValid() {
		return Banking.isBankScreenOpen() && BankingUtilities.checkInv();
	}

	@Override
	public void execute() {
		FungusMain.status = "Depositing & Withdrawing supplies";
		Banking.depositAllExcept(Constants.TABS_AND_SILVER);
		Timing.waitCondition(new Condition() {
			public boolean active() {
				General.sleep(100, 200);
				return !Inventory.isFull();
			}
		}, General.random(1500, 2500));
		if (!Equipment.isEquipped(Constants.RINGS_OF_DUELING)) {
			if (Banking.withdraw(1, Constants.RINGS_OF_DUELING)) {
				AntiBan.waitItemInteractionDelay(General.random(2, 6));
				Timing.waitCondition(new Condition() {
					public boolean active() {
						General.sleep(100, 200);
						return (Inventory.getCount(Constants.RINGS_OF_DUELING) > 0);
					}
				}, General.random(1500, 2500));
			}
		}
		if (Inventory.getCount(Constants.STAM_POTS) < 1) {
			if (Banking.withdraw(1, Constants.STAM_POTS)) {
				AntiBan.waitItemInteractionDelay(General.random(2, 6));
				Timing.waitCondition(new Condition() {
					public boolean active() {
						General.sleep(100, 200);
						return (Inventory.getCount(Constants.STAM_POTS) > 0);
					}
				}, General.random(1500, 2500));
			}
		}
		if (Banking.close()) {
			Bank.equipRing();
		}
	}
}
