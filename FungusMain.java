package scripts.tribot.MortFungus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Login;
import org.tribot.api2007.Login.STATE;
import org.tribot.api2007.types.RSTile;
import org.tribot.api2007.util.ThreadSettings;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.MessageListening07;
import org.tribot.script.interfaces.Painting;
import org.tribot.script.interfaces.Starting;

import scripts.AntiBan;
import scripts.tribot.MortFungus.Constants.Constants;
import scripts.tribot.MortFungus.Methods.*;

@ScriptManifest(authors = { "Godspower33" }, description = "Picks mort fungus using fairy rings "
		+ " uses castle wars to recharge hp, pray ", category = "Money making", name = "Gods Fungus")
public class FungusMain extends Script implements MessageListening07, Painting,
		Starting {

	public ArrayList<Node> nodes = new ArrayList<Node>();
	
	// ----- Paths ----- \\
	public static  RSTile[] walkToCWRing = { new RSTile(2443, 3088, 0), new RSTile(2247, 3089, 0), new RSTile(2452, 3089,0),
            new RSTile(2455, 3084, 0), new RSTile(2455, 3075, 0), new RSTile(2449, 3070,0),
            new RSTile(2445, 3066, 0), new RSTile(2440, 3064, 0), new RSTile(2435, 3063,0),
            new RSTile(2431, 3059, 0), new RSTile(2427, 3054, 0), new RSTile(2421, 3051,0),
            new RSTile(2415, 3050, 0), new RSTile(2409, 3049, 0), new RSTile(2407, 3041,0),
            new RSTile(2403, 3039, 0), new RSTile(2396, 3039, 0), new RSTile(2390, 3037,0),
            new RSTile(2383, 3034, 0)};
    
    //String, stuff needed for script \\
    public static String status; // status
    public static boolean stopScript = false;
    private int fungusPicked = 0; // fungus picked
	private int price = getPrice(Constants.FUNGUS_ID);
	
	@Override
	public void run() {
		Mouse.setSpeed(General.random(88, 108));
		General.useAntiBanCompliance(true);
		ThreadSettings.get().setClickingAPIUseDynamic(true);
		initialize();
		while (!stopScript) {
			if (Login.getLoginState() != STATE.LOGINSCREEN) {
				for (final Node n : nodes) {
					if (n.isNodeValid()) {
						n.execute();
					} else {
						status = "AntiBan";
			            AntiBan.timedActions();
					}
					General.sleep(15, 25);
				}
			}
			General.sleep(65, 105);
		}
	}
	
	public void initialize() {
		Collections.addAll(nodes, new Bank(), new BankDepositAndWithdraw(),
				new CastBloom(), new UseCwPortal(), new PickFungus(),
				new WalkToCwRing(), new WalkToFungusFromSwamp());
	}
	
	@Override
	public void onStart() {
        AntiBan.setPrintDebug(true);
	}  
	
    // Gets the price of an item from the rsbuddy api
    private int getPrice(final int id){
        try {
            URL url = new URL("http://api.rsbuddy.com/grandExchange?a=guidePrice&i=" + id);
            URLConnection con = url.openConnection();
            con.setUseCaches(true);
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String[] data = br.readLine().replace("{", "").replace("}", "").split(",");
            return Integer.parseInt(data[0].split(":")[1]);
        } catch(Exception e){
        	
        }
        return -1;
    }
	
    private static final long startTime = System.currentTimeMillis();
    Font font = new Font("Verdana", Font.BOLD, 12);
    
    private Image getImage(String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch(IOException e) {
            return null;
        }
    }
    private final Image IMG = getImage("http://imgur.com/sNhCwQT.png"); // http://i.imgur.com/TUlYgDr.png
    
	public void onPaint(Graphics r) {
		Graphics2D gg = (Graphics2D)r;
        gg.drawImage(IMG, 200, 345, null);
 
        long timeRan = System.currentTimeMillis() - startTime;
        int fungusPerHour = (int) (fungusPicked * 3600000d / timeRan);
		int profit = price * fungusPicked;
		int profitPerHour = (int) (profit * 3600000d / timeRan);
 
        r.setFont(font);
        r.setColor(Color.WHITE);
        r.drawString("Gods Fungus Picker V2.0", 220, 360);
        r.drawString("Running for: " + Timing.msToString(timeRan), 220, 375);
        r.drawString("Status: " + status, 220, 390);
        r.drawString("Mort Myer Fungus : " + fungusPicked + " (" + fungusPerHour + "/hr)", 220, 405);
        r.drawString("Profit: " + profit + " (" + profitPerHour + "/hr)", 220, 420);
 
    }
 
    @Override
    public void clanMessageReceived(String arg0, String arg1) { }
 
    @Override
    public void duelRequestReceived(String arg0, String arg1) { }
 
    @Override
    public void personalMessageReceived(String arg0, String arg1) { }
 
    @Override
    public void playerMessageReceived(String arg0, String arg1) { }
 
    @Override
    public void serverMessageReceived(String msg) {
        if (msg.contains("You pick a mushroom from the log."))
            fungusPicked++;
    }
 
    @Override
    public void tradeRequestReceived(String arg0) { }
}
