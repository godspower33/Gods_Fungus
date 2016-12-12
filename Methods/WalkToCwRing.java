package scripts.tribot.MortFungus.Methods;

import org.tribot.api.Clicking;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Camera;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;

import scripts.AntiBan;
import scripts.tribot.MortFungus.FungusMain;
import scripts.tribot.MortFungus.Node;
import scripts.tribot.MortFungus.Constants.Constants;

public class WalkToCwRing extends Node {
	
	@Override
	public boolean isNodeValid() {
		return ((Constants.CW_BANK.contains(Player.getPosition()) || Player.getPosition().distanceTo(Constants.CW_BANK.getRandomTile()) < 75) 
    			&& Skills.getCurrentLevel(Skills.SKILLS.PRAYER) >= 40) || (Objects.findNearest(10, Constants.CW_FAIRY_RING_ID).length > 0);
	}

	@Override
	public void execute() {
		FungusMain.status = "Walking to CW fairy ring";
		final RSItem[] EMPTY_VIAL = Inventory.find(Constants.EMPTY_VIAL);
		AntiBan.activateRun();
		if (!isStaminaActive()) {
			drinkStamina();
		}
		if (!Constants.CW_RING.contains(Player.getPosition())) {
			if (Walking.walkPath(Walking.randomizePath(FungusMain.walkToCWRing,1, 1))) {
				if (EMPTY_VIAL.length > 0)
					if (Clicking.click("Drop", EMPTY_VIAL[0]))
						AntiBan.waitItemInteractionDelay(General.random(3, 8));
				Timing.waitCondition(new Condition() {
					@Override
					public boolean active() {
						General.sleep(100, 200);
						return Constants.CW_RING.contains(Player.getPosition());
					}
				}, General.random(4800, 6000));
			}
		}
		if (Constants.CW_RING.contains(Player.getPosition()) && !codeScreenVisible()) {
			useCWRing();
			if (codeScreenVisible()) {
				selectCode();
			}
		}
	}

	private boolean codeScreenVisible() {
    	RSInterface codeScreen = Interfaces.get(Constants.FAIRY_RING_CODE_SCREEN_MASTER); // code screen interface
    	return codeScreen != null;
	}
	
    // Check if stamina is active method
	private boolean isStaminaActive() {
		return Game.getSetting(Constants.STAMINA_RUN_SETTING) > 0;
	} 
	
	// Drinks stamina
    private void drinkStamina() {
        if (Inventory.getCount(Constants.STAM_POTS) > 0 && !isStaminaActive()) {
            final RSItem[] POTS = Inventory.find(Constants.STAM_POTS);
            if (POTS.length > 0) {
                if (Clicking.click("Drink", POTS[0])) {
                    Timing.waitCondition(new Condition() {
                        @Override
                        public boolean active() {
                            General.sleep(100, 200);
                            return isStaminaActive();
                        }
                    }, General.random(4800, 6000));
                }
            }
        }
    }

	private void useCWRing() {
		FungusMain.status = "Using CW Ring";
		final RSObject[] CW_FAIRY_RING = Objects.findNearest(10,Constants.CW_FAIRY_RING_ID); // fairy ring object near castle wars
		
		if (CW_FAIRY_RING.length > 0) {
			final RSObject RING = CW_FAIRY_RING[0];
			if (!RING.isOnScreen())
				Camera.turnToTile(RING);
			if (DynamicClicking.clickRSObject(RING, 3)) {
				Timing.waitCondition(new Condition() {
					@Override
					public boolean active() {
						General.sleep(100, 200);
						return ChooseOption.isOpen();
					}
				}, General.random(5000, 6000));
			}
			if (ChooseOption.isOpen()) {
				if (ChooseOption.getOptions().length > 0) {
					if (ChooseOption.select("Configure")) {
						Timing.waitCondition(new Condition() {
							@Override
							public boolean active() {
								RSInterface codeScreen = Interfaces.get(Constants.FAIRY_RING_CODE_SCREEN_MASTER); // code screen interface
								General.sleep(100, 200);
								return codeScreen != null;
							}
						}, General.random(6500, 7500));
					}
				}

			}
		}
    }
    
    // Selects code from opened code screen interface
    private void selectCode() {
		FungusMain.status = "Selecting code";
    	RSInterface codeScreen = Interfaces.get(Constants.FAIRY_RING_CODE_SCREEN_MASTER); // code screen interface
        RSInterface selectCode = Interfaces.get(Constants.TRAVEL_LOG_MASTER, Constants.TRAVEL_LOG_FAVORITE_CHILD); // Select favorite code interface
        RSInterface confirm = Interfaces.get(Constants.TRAVEL_LOG_CONFIRM_CODE_MASTER, Constants.TRAVEL_LOG_CONFIRM_CODE_CHILD); // Confirm interface button
		if (codeScreen != null) {
			if (Game.getSetting(Constants.TRAVEL_LOG_SETTING) != Constants.MORT_MYRE_CODE_SETTING) {
				if (selectCode != null) {
					if (Clicking.click(selectCode)) {
						Timing.waitCondition(new Condition() {
							@Override
							public boolean active() {
								General.sleep(100, 200);
								return Game.getSetting(Constants.TRAVEL_LOG_SETTING) == Constants.MORT_MYRE_CODE_SETTING;
							}
						}, General.random(6500, 7500));
					}
				}
			}
			if (Game.getSetting(Constants.TRAVEL_LOG_SETTING) == Constants.MORT_MYRE_CODE_SETTING) {
				if (confirm != null) {
					if (Clicking.click(confirm)) {
						Timing.waitCondition(new Condition() {
							@Override
							public boolean active() {
								General.sleep(100, 200);
								return Player.getPosition().distanceTo(Constants.SWAMP_TILE) <= 5;
							}
						}, General.random(7500, 8600));
					}
				}
			}
		}
    }
}
