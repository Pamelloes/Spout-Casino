package org.dyndns.pamelloes.SpoutCasino.gui;

import org.bukkit.plugin.Plugin;
import org.dyndns.pamelloes.SpoutCasino.cards.Deck;
import org.getspout.spoutapi.gui.Container;
import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

public class BlackJackGui extends GenericPopup {
	private Plugin plugin;
	private SpoutPlayer player;
	private Deck deck;
	
	public BlackJackGui(Plugin plugin, SpoutPlayer player) {
		this.plugin = plugin;
		this.player = player;
		deck = new Deck();
		deck.makeDeck(false);
		deck.shuffle();
		makeGui();
	}
	
	private void makeGui() {
		//CardWidget cw = new CardWidget(deck.dealTop());
		//cw.setX(0).setY(0);
		//attachWidget(plugin,cw);
		Container main = new GenericContainer();
		main.setLayout(ContainerType.VERTICAL);
		
		for(int i=0;i<4;i++) {
			Container row = new GenericContainer();
			row.setLayout(ContainerType.HORIZONTAL);
			for(int j=0;j<13;j++) row.addChild(new CardWidget(deck.dealTop()).setWidth(40));
			main.addChild(row);
		}
		main.setWidth(player.getMainScreen().getWidth()).setHeight(player.getMainScreen().getHeight());
		main.setAnchor(WidgetAnchor.TOP_LEFT);
		this.attachWidget(plugin, main);
	}
}
