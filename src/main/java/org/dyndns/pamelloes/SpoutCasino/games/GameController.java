package org.dyndns.pamelloes.SpoutCasino.games;

import org.getspout.spoutapi.player.SpoutPlayer;

public interface GameController {
	public void onTick();
	public void handleEntryRequest(SpoutPlayer player);
	public void handleForcedTermination();
}
