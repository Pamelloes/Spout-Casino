package org.dyndns.pamelloes.SpoutCasino.games;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dyndns.pamelloes.SpoutCasino.SpoutCasino;
import org.dyndns.pamelloes.SpoutCasino.cards.Card;
import org.dyndns.pamelloes.SpoutCasino.cards.Deck;
import org.dyndns.pamelloes.SpoutCasino.gui.WaitingGui;
import org.dyndns.pamelloes.SpoutCasino.gui.holdem.HoldemGui;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.player.SpoutPlayer;

public class HoldemController implements GameController {
	private List<SpoutPlayer> requests = new ArrayList<SpoutPlayer>();
	
	private SpoutPlayer[] players = new SpoutPlayer[4];
	private Map<SpoutPlayer, Card[]> hands = new HashMap<SpoutPlayer, Card[]>();
	private Map<SpoutPlayer, HoldemGui> guis = new HashMap<SpoutPlayer, HoldemGui>();
	private List<Card> community = new ArrayList<Card>();
	
	private Deck deck = new Deck();
	
	private boolean inprogress = false;
	private int dealer = -1, turn = -1;
	private int pot = 0;
	private int timeremaining = 300;

	public HoldemController() {
		deck.makeDeck(false);
	}
	
	public Deck getDeck() {
		return deck;
	}
	
	public void onTick() {
		acceptRequests();
		if(!inprogress) {
			if(getPlayerCount() >= 2) startGame();
		} else {
			if(timeremaining == 0) {
				if(turn == dealer) {
					if(community.size() == 0)  flop();
					else if (community.size() == 3) turn();
					else if (community.size() == 4) river();
					else if (community.size() == 5) {
						int winner = 0;//get best hand somehow :D
						endGame(winner);
						return;
					}
				}
				nextTurn();
				
			} else timeremaining--;
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
	
	public void handlePlayerExit(SpoutPlayer player) {
		hands.remove(player);
		guis.remove(player);
		int id = -1;
		for(int i = 0; i < 4; i++) if(players[i]==player) {
			players[i] = null;
			id = i;
		}
		for(int i = 0; i < 4; i++) if(players[i]!=null) guis.get(players[i]).setState(id, 0);
		if(getActivePlayerCount() == 1) {
			for(int i = 0; i < 4; i++) if(players[i] != null) endGame(i);
			return;
		}
		if(id == dealer) nextDealer();
		if(id == turn) nextTurn();
	}

	public void handleForcedTermination() {
		if(getActivePlayerCount() == 0) return;
		int amount = pot/getActivePlayerCount();
		for(SpoutPlayer p : players) if(p!=null && guis.containsKey(p)) {
			giveChips(p, amount);
			p.getMainScreen().closePopup();
		}
	}
	
	public void bet(int amount) {
		pot+=amount;
	}
	
	private void flop() {
		deck.dealTop();//burn
		Card a = deck.dealTop();
		Card b = deck.dealTop();
		Card c = deck.dealTop();
		community.add(a);
		community.add(b);
		community.add(c);
		for(int i = 0; i < 4; i++) if(players[i] != null) guis.get(players[i]).flop(a, b, c);
	}
	
	private void turn() {
		deck.dealTop();//burn
		Card a = deck.dealTop();
		community.add(a);
		for(int i = 0; i < 4; i++) if(players[i] != null) guis.get(players[i]).turn(a);
	}
	
	private void river() {
		deck.dealTop();//burn
		Card a = deck.dealTop();
		community.add(a);
		for(int i = 0; i < 4; i++) if(players[i] != null) guis.get(players[i]).turn(a);
	}
	
	private void endGame(int id) {
		giveChips(players[id], pot);
		inprogress = false;
		turn = -1;
		setDealer(-1);
		hands.clear();
		community.clear();
		pot = 0;
	}
	
	private void acceptRequests() {
		Iterator<SpoutPlayer> request = requests.iterator();
		for(int i = 0; i < 4 && request.hasNext(); i++) if(players[i]==null) {
			enterGame(i,request.next());
			request.remove();
			break;
		}
	}
	
	private void enterGame(int id, SpoutPlayer player) {
		players[id] = player;
		WaitingGui.waiting.remove(player);
		player.getMainScreen().closePopup();
		HoldemGui gui = new HoldemGui(player,this,id);
		for(int i = 0; i < 4; i++) {
			if(i == id) continue;
			if(players[i] == null) {
				gui.setState(i, 0);
			} else if(hands.containsKey(players[i])){
				gui.setState(i, 2);
				guis.get(players[i]).setState(id, 1);
			} else {
				gui.setState(i, 1);
				guis.get(players[i]).setState(id, 1);
			}
		}
		gui.setDealer(inprogress ? dealer : -1);
		gui.setTurn(turn);
		if(community.size() > 2) gui.flop(community.get(0), community.get(1), community.get(2));
		if(community.size() > 3) gui.turn(community.get(3));
		if(community.size() > 4) gui.river(community.get(4));
		guis.put(player, gui);
		player.getMainScreen().attachPopupScreen(gui);
	}
	
	private void startGame() {
		inprogress = true;
		deck.makeDeck(false);
		deck.shuffle();
		for(int i = 0; i < 4; i++) if(players[i] != null) startGame(i);
		nextDealer();
		turn = dealer;
		nextTurn();
	}
	
	private void startGame(int id) {
		SpoutPlayer player = players[id];
		Card[] hand = new Card[]{deck.dealTop(),deck.dealTop()};
		hands.put(player, hand);
		guis.get(player).showCards(hand[0], hand[1]);
		for(int i = 0; i < 4; i++) {
			if(i == id || players[i] == null) continue;
			guis.get(players[i]).setState(id, 2);
		}
	}
	
	private void nextDealer() {
		while(true) {
			dealer = (dealer + 1) % 4;
			if(players[dealer] == null || !guis.containsKey(players[dealer])) continue;
			setDealer(dealer);
		}
	}
	
	private void setDealer(int dealer) {
		for(int i = 0; i < 4; i++) if(players[i] != null) guis.get(players[i]).setDealer(dealer);
	}
	
	private void nextTurn() {
		while(true) {
			turn = (turn + 1) % 4;
			if(players[turn] == null || !guis.containsKey(players[dealer])) continue;
			setTurn(turn);
		}
	}
	
	private void setTurn(int turn) {
		for(int i = 0; i < 4; i++) if(players[i] != null) guis.get(players[i]).setTurn(turn);
		timeremaining = 300;
	}
	
	private int getPlayerCount() {
		int in = 0;
		for(SpoutPlayer p : players) if(p!=null) in++;
		return in;
	}
	
	private int getActivePlayerCount() {
		int in = 0;
		for(SpoutPlayer p : players) if(p!=null && hands.containsKey(players)) in++;
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