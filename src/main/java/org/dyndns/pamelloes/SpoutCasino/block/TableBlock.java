package org.dyndns.pamelloes.SpoutCasino.block;

import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.dyndns.pamelloes.SpoutCasino.gui.BlackJackGui;
import org.dyndns.pamelloes.SpoutCasino.gui.CreateGui;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.block.design.Texture;
import org.getspout.spoutapi.material.block.GenericCustomBlock;
import org.getspout.spoutapi.player.SpoutPlayer;

public class TableBlock extends GenericCustomBlock {

	public TableBlock(Plugin plugin, Texture tex) {
		super(plugin, "CasinoTable", false, new TableBlockDesign(plugin, tex));
	}
	
	@Override
	public void onBlockPlace(World world, int x, int y, int z, LivingEntity entity) {
		SpoutManager.getChunkDataManager().setBlockData("SpoutCasino", world, x, y, z, new TableBlockData(world.getUID(),x,y,z));
		if(!(entity instanceof SpoutPlayer)) return;
		CreateGui gui = new CreateGui((SpoutPlayer) entity, world.getBlockAt(x, y, z));
		((SpoutPlayer) entity).getMainScreen().attachPopupScreen(gui);
	}
	
	@Override
	public boolean onBlockInteract(World world, int x, int y, int z, SpoutPlayer player) {
		BlackJackGui gui = new BlackJackGui(getPlugin(), player);
		player.getMainScreen().attachPopupScreen(gui);
		((TableBlockData) SpoutManager.getChunkDataManager().getBlockData("SpoutCasino", world, x, y, z)).players.add(player);
		return true;
	}

	@Override
    public void onBlockDestroyed(World world, int x, int y, int z) {
		TableBlockData dat = (TableBlockData) SpoutManager.getChunkDataManager().getBlockData("SpoutCasino", world, x, y, z);
		if(dat==null)  return;
		SpoutManager.getChunkDataManager().removeBlockData("SpoutCasino", world, x, y, z);
		HandlerList.unregisterAll(dat);
	}
}
