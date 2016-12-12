package scripts.tribot.MortFungus.Constants;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

public class Constants {

    // ---- RSAreas ---- \\
    public final static RSArea CW_BANK = new RSArea(new RSTile(2433, 3099, 0), new RSTile(2445, 3081, 0)); // cw bank area
    public final static RSArea CW_PORTAL = new RSArea(new RSTile(2411, 9533, 0), new RSTile(2430, 9515)); // inside cw portal
    public final static RSArea CW_RING = new RSArea(new RSTile(2379, 3031), new RSTile(2388, 3038, 0)); // cw ring area
 
    // ---- Tiles ---- \\
    public final static RSTile SWAMP_TILE = new RSTile(3469, 3431, 0); // Tile of swamp ring
    public final static RSTile FUNGUS_TILE = new RSTile(3474, 3419, 0); // Tile of south fungus log spot
    
    // --- Gear --- \\
    public final static int[] RINGS_OF_DUELING = { 2552, 2554, 2558, 2560, 2562, 2564, 2556, 2566 }; // ids of rings
    public final static int[] TABS_AND_SILVER = { 2963, 8013, 12625, 12627, 12629, 12631 }; // Ids of blessed sickle and house tabs
    public final static int[] STAM_POTS = { 12625, 12627, 12629, 12631 }; // ids for stamina pots
    public final static int SICKLE = 2963; // sickle
    
    // ---- RSObjects ---- \\
    public final static int CW_FAIRY_RING_ID = 29495;
    public final static int ZAMMY_PORTAL_ENTRANCE = 4388;
    public final static int ZAMMY_PORTAL_EXIT = 4390;
	
    // ---- IDs ---- \\
    public final static int FUNGUS_ID = 2970;
    public final static int EMPTY_VIAL = 229;
    
    // ---- Animations ---- \\
    public final static int FUNGUS_PICKING_ANIMATION = 827;
    
    // ---- RSInterface Master ---- \\
    public final static int FAIRY_RING_CODE_SCREEN_MASTER = 398;
    public final static int TRAVEL_LOG_MASTER = 381;
    public final static int TRAVEL_LOG_CONFIRM_CODE_MASTER = 398;
    public final static int BANK_MASTER = 12;
    
    // ----- RSInterface Child ----- \\
    public final static int TRAVEL_LOG_FAVORITE_CHILD = 140;
    public final static int TRAVEL_LOG_CONFIRM_CODE_CHILD = 26;
    public final static int BANK_AMOUNT_CHILD = 5;
    
    // ---- Settings ---- \\
    public final static int TRAVEL_LOG_SETTING = 816;
    public final static int MORT_MYRE_CODE_SETTING = 1048619;
    public final static int STAMINA_RUN_SETTING = 638;
    
}
