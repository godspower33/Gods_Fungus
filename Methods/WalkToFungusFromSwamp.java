package scripts.tribot.MortFungus.Methods;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Walking;

import scripts.tribot.MortFungus.FungusMain;
import scripts.tribot.MortFungus.Node;
import scripts.tribot.MortFungus.Constants.Constants;

public class WalkToFungusFromSwamp extends Node {

	@Override
	public boolean isNodeValid() {
		return Skills.getCurrentLevel(Skills.SKILLS.PRAYER) >= 1 && Player.getPosition().distanceTo(Constants.SWAMP_TILE) < 60 
				&& !Inventory.isFull() && !Player.getPosition().equals(Constants.FUNGUS_TILE) && Objects.find(6, "Fungi on log").length < 1;
	}

	@Override
	public void execute() {
        FungusMain.status = "Walking to fungus spot";
        if (Player.getPosition().distanceTo(Constants.FUNGUS_TILE) >= 6) {
	        if (Walking.walkPath(Walking.generateStraightPath(Constants.FUNGUS_TILE))) {
	            Timing.waitCondition(new Condition() {
	                public boolean active() {
	                    General.sleep(100, 200);
	                    return Objects.findNearest(6, "Rotting log").length > 0 && Player.getPosition().equals(Constants.FUNGUS_TILE);
	                }
	            }, General.random(3000,4000));
	        }
        }
        if (Player.getPosition().distanceTo(Constants.FUNGUS_TILE) <= 6) {
        	if (!Constants.FUNGUS_TILE.isOnScreen())
        		Camera.turnToTile(Constants.FUNGUS_TILE);
        	if (Walking.clickTileMS(Constants.FUNGUS_TILE, "Walk here")) {
	            Timing.waitCondition(new Condition() {
	                public boolean active() {
	                    General.sleep(100, 200);
	                    return Objects.findNearest(6, "Rotting log").length > 0 && Player.getPosition().equals(Constants.FUNGUS_TILE);
	                }
	            }, General.random(3000,4000));
        	}
        }
	}	
}
