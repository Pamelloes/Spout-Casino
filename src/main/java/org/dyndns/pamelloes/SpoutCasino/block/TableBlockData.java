package org.dyndns.pamelloes.SpoutCasino.block;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.dyndns.pamelloes.SpoutCasino.games.GameController;
import org.dyndns.pamelloes.SpoutCasino.games.HoldemController;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.spout.ServerTickEvent;
import org.getspout.spoutapi.player.SpoutPlayer;

@SuppressWarnings("serial")
public class TableBlockData implements Listener, Serializable {
	private transient GameController control;
	
	private int gametype = 0;
	
    private int x,y,z;
    private UUID world;

    public TableBlockData(UUID world, int x, int y, int z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        Bukkit.getPluginManager().getPlugin("SpoutCasino").getServer().getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugin("SpoutCasino"));
    }
    
    @EventHandler
    public void onServerTick(ServerTickEvent e) {
    	if(control!=null) control.onTick();
    }
    
    public void onPlayerInteract(SpoutPlayer player) {
    	control.handleEntryRequest(player);
    }
    
    public void onBlockDestroyed() {
    	control.handleForcedTermination();
    }
    
    public void setGameType(int game) {
    	gametype = game;
    	if(control != null) control.handleForcedTermination();
    	control = getNewControl();
    }
    
    public int getGameType() {
    	return gametype;
    }
    
    private GameController getNewControl() {
    	switch(getGameType()) {
    	case 0:
    		return new HoldemController();
    	default:
    		return null;
    	}
    }
    
    private void writeObject(ObjectOutputStream out) throws IOException {
    	out.defaultWriteObject();
    }
    
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    	in.defaultReadObject();
    	control = getNewControl();
        Bukkit.getPluginManager().getPlugin("Spout").getServer().getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugin("Spout"));
        Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Spout"), new Runnable() {
        	public void run() {
        		SpoutManager.getChunkDataManager().setBlockData("SpoutCasino", Bukkit.getWorld(world), x, y, z, TableBlockData.this);
        	}
        }, 1L);
    }
}
