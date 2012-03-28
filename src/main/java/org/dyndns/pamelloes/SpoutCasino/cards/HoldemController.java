package org.dyndns.pamelloes.SpoutCasino.cards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dyndns.pamelloes.SpoutCasino.SpoutCasino;
import org.dyndns.pamelloes.SpoutCasino.gui.WaitingGui;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.player.SpoutPlayer;

public class HoldemController implements GameController {
	private List<SpoutPlayer> requests = new ArrayList<SpoutPlayer>();
	
	private SpoutPlayer[] players = new SpoutPlayer[4];
	private Map<SpoutPlayer, Card[]> hands = new HashMap<SpoutPlayer, Card[]>();
	private List<Card> center = new ArrayList<Card>();
	
	private boolean inprogress = false;
	private int pot = 0;
	private int timeremaining = 300;
	
	public void onTick() {
		//Iterator<SpoutPlayer> player = 
		if(!inprogress) {
			
		}
	}

	public void handleEntryRequest(final SpoutPlayer player) {
		requests.add(player);
		player.getMainScreen().attachPopupScreen(new WaitingGui(player, new Runnable() {
			public void run() {
				requests.remove(player);
			}
		}));
	}

	public void handleForcedTermination() {
		if(getPlayerCount() == 0) return;
		int amount = pot/getPlayerCount();
		for(SpoutPlayer p : players) if(p!=null) {
			giveChips(p, amount);
			p.getMainScreen().closePopup();
		}
	}
	
	private void addToGame(boolean active) {
		
	}
	
	private int getPlayerCount() {
		int in = 0;
		for(SpoutPlayer p : players) if(p!=null) in++;
		return in;
	}
	
	private void giveChips(SpoutPlayer p, int amount) {
		int left = amount;
		
		int chips = 0;
		while(left > SpoutCasino.diamondrate)  {
			left -= SpoutCasino.diamondrate;
			chips++;
			if(chips == 64) {
				chips = 0;
				p.getInventory().addItem(new SpoutItemStack(SpoutCasino.diamondchip,64));
			}
		}
		p.getInventory().addItem(new SpoutItemStack(SpoutCasino.diamondchip,chips));
		
		chips = 0;
		while(left > SpoutCasino.goldrate)  {
			left -= SpoutCasino.goldrate;
			chips++;
			if(chips == 64) {
				chips = 0;
				p.getInventory().addItem(new SpoutItemStack(SpoutCasino.goldchip,64));
			}
		}
		p.getInventory().addItem(new SpoutItemStack(SpoutCasino.goldchip,chips));
		
		chips = 0;
		while(left > 0) {
			chips++;
			if(chips == 64) {
				chips = 0;
				p.getInventory().addItem(new SpoutItemStack(SpoutCasino.ironchip,64));
			}
		}
		p.getInventory().addItem(new SpoutItemStack(SpoutCasino.ironchip,chips));
	}
}
