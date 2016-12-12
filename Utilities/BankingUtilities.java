package scripts.tribot.MortFungus.Utilities;

import org.tribot.api2007.Banking;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSInterface;

import scripts.tribot.MortFungus.Constants.Constants;

public class BankingUtilities {

	// Checks if bank items are loaded or not
    public static boolean isBankItemsLoaded() {
        return getCurrentBankSpace() == Banking.getAll().length;
    }

    // Gets current bank space
    public static int getCurrentBankSpace() {
           RSInterface amount = Interfaces.get(Constants.BANK_MASTER,Constants.BANK_AMOUNT_CHILD);
           if(amount != null) {
               String txt = amount.getText();
               if(txt != null) {
                   try {
                       int toInt = Integer.parseInt(txt);
                       if(toInt > 0)
                           return toInt;
                   } catch(NumberFormatException e) {
                       return -1;
                   }
               }
           }
           return -1;
   }
    
    public static boolean suppliesStill() {
    	return Banking.find(Constants.RINGS_OF_DUELING).length > 0;
    }
	
    // check if inventory contains fungus
    public static boolean checkInv() {
        return Inventory.getCount(Constants.FUNGUS_ID) > 0;
    }
}
