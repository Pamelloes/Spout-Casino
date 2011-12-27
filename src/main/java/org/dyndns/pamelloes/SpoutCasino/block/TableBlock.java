package org.dyndns.pamelloes.SpoutCasino.block;

import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.dyndns.pamelloes.SpoutCasino.gui.TableGui;
import org.getspout.spoutapi.block.design.Texture;
import org.getspout.spoutapi.material.block.GenericCustomBlock;
import org.getspout.spoutapi.player.SpoutPlayer;

public class TableBlock extends GenericCustomBlock {

	public TableBlock(Plugin plugin, Texture tex) {
		super(plugin, "CasinoTable", false, new TableBlockDesign(plugin, tex));
	}
	
	@Override
	public boolean onBlockInteract(World world, int x, int y, int z, SpoutPlayer player) {
		TableGui gui = new TableGui(getPlugin(), player);
		player.getMainScreen().attachPopupScreen(gui);
		return true;
	}

}
