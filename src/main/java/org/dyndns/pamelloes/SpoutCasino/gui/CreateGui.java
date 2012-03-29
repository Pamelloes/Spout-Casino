package org.dyndns.pamelloes.SpoutCasino.gui;

import org.bukkit.block.Block;
import org.dyndns.pamelloes.SpoutCasino.block.TableBlockData;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.Container;
import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.player.SpoutPlayer;

public class CreateGui extends TableGui {
	private Block block;
	
	public CreateGui(SpoutPlayer player, Block block) {
		super(player);
		this.block = block;
		makeGui();
	}

	protected void makeGui() {
		setSize(210,230);
		casino.extractFile("blackjack.jpg", true);
		casino.extractFile("texasholdem.jpg", true);
		
		Container master = new GenericContainer().setLayout(ContainerType.OVERLAY);
		GenericLabel header = new GenericLabel("Choose which game the table will play.");
		header.setPriority(RenderPriority.Lowest);
		header.setTextColor(new Color(1.0f,0.3f,0.3f));
		header.setMargin(15,13);
		master.addChild(header);
		
		Container buttons = getButtons();
		buttons.setMargin(20, 0, 0, 0);
		master.addChild(buttons);
		container.addChild(master);
		
	}
	
	private Container getButtons() {
		Container rows = new GenericContainer().setLayout(ContainerType.VERTICAL);
		Container top = new GenericContainer().setLayout(ContainerType.HORIZONTAL);
		
		ImageButton ne = getIb("plugins/SpoutCasino/texasholdem.jpg", 0);
		ne.setMargin(15, 5, 5, 15);
		
		ImageButton nw = getIb("plugins/SpoutCasino/blackjack.jpg", 1);
		nw.setMargin(15, 15, 5, 5);
		top.addChildren(ne,nw);
		rows.addChild(top);
		
		Container bottom = new GenericContainer().setLayout(ContainerType.HORIZONTAL);
		
		ImageButton se = getIb("plugins/SpoutCasino/blackjack.jpg", 2);
		se.setMargin(5, 5, 15, 15);
		
		ImageButton sw = getIb("plugins/SpoutCasino/blackjack.jpg", 3);
		sw.setMargin(5, 15, 15, 5);
		bottom.addChildren(se,sw);
		rows.addChild(bottom).setWidth(140).setHeight(140);
		return rows;
	}
	
	private ImageButton getIb(String url,final int gameType) {
		ImageButton ib = new ImageButton(url) {
			@Override
			public void onButtonClick(ButtonClickEvent e) {
				((TableBlockData) SpoutManager.getChunkDataManager().getBlockData("SpoutCasino", block.getWorld(), block.getX(), block.getY(), block.getZ())).setGameType(gameType);
				getPlayer().getMainScreen().closePopup();
			}
		};
		ib.setWidth(85).setHeight(85).setMargin(10).setFixed(true);
		return ib;
	}
}
