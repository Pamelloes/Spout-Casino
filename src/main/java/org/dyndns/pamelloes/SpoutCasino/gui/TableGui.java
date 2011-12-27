package org.dyndns.pamelloes.SpoutCasino.gui;

import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.player.SpoutPlayer;

public class TableGui extends GenericPopup {
	private Plugin plugin;
	private SpoutPlayer player;
	
	public TableGui(Plugin plugin, SpoutPlayer player) {
		this.plugin = plugin;
		this.player = player;
		makeGui();
	}
	
	private void makeGui() {
		
	}
}
