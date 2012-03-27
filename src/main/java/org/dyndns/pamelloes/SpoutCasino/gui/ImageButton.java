package org.dyndns.pamelloes.SpoutCasino.gui;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.Container;
import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.RenderPriority;

public class ImageButton extends GenericContainer {
	private GenericTexture tex;
	private GenericButton button;
	
	public ImageButton(String url) {
		setLayout(ContainerType.OVERLAY);
		tex = new GenericTexture(url);
		tex.setPriority(RenderPriority.Lowest);
		addChild(tex);
		button = new GenericButton("") {
			@Override
			public void onButtonClick(ButtonClickEvent e) {
				ImageButton.this.onButtonClick(e);
			}
		};
		button.setPriority(RenderPriority.Highest);
		addChild(button);
	}
	
	@Override
	public Container setWidth(int width) {
		super.setWidth(width);
		tex.setWidth(width);
		button.setWidth(width);
		return this;
	}
	
	@Override
	public Container setHeight(int height) {
		super.setHeight(width);
		tex.setHeight(height);
		button.setHeight(height);
		return this;
	}
	
	public void onButtonClick(ButtonClickEvent e) {}
}
