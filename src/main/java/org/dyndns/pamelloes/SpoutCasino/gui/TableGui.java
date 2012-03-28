package org.dyndns.pamelloes.SpoutCasino.gui;

import org.dyndns.pamelloes.SpoutCasino.SpoutCasino;
import org.getspout.spoutapi.gui.Container;
import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.Texture;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

public abstract class TableGui extends GenericPopup {
	protected SpoutCasino casino;
	protected SpoutPlayer player;
	private Texture tex;
	protected Container container = new GenericContainer().setLayout(ContainerType.OVERLAY);
	
	public TableGui(SpoutPlayer player) {
		this.player = player;
		casino = SpoutCasino.casino;
		casino.extractFile("bg.png", true);
		tex = new GenericTexture("plugins/SpoutCasino/bg.png");
		tex.setPriority(RenderPriority.High);
		container.addChild(tex).setAnchor(WidgetAnchor.CENTER_CENTER);
		setSize(200,200);
		attachWidget(casino,container);
		makeGui();
	}
	
	protected abstract void makeGui();
	
	protected void setSize(int width, int height) {
		tex.setWidth(width).setHeight(height);
		container.setWidth(width).setHeight(height).setX(-(width/2)).setY(-(height/2));
	}
}
