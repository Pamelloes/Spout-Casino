package org.dyndns.pamelloes.SpoutCasino.gui;

import org.dyndns.pamelloes.SpoutCasino.cards.Card;
import org.getspout.spoutapi.gui.Container;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.Texture;

public class CardWidget extends GenericContainer {
	private Card card;
	private Texture texture;
	
	public CardWidget(Card card) {
		this.card=card;
		texture = new GenericTexture("plugins/SpoutCasino/" + card.getPath(false));
		//texture.setLeft((card.getNumber().getValue()-1)*24).setTop((card.getSuit().getValue()-1)*32).setWidth(24).setHeight(32).setMaxHeight(32).setMaxWidth(24).setMinHeight(32).setMinWidth(24);
		//setLayout(ContainerType.OVERLAY);
		addChild(texture);
		this.setMaxHeight(107).setMaxWidth(75);//.setMinHeight(107).setMinWidth(75);
	}
	
	public Card getCard() {
		return card;
	}
	
	public void setCard(Card c) {
		card = c;
		texture.setUrl("plugins/SpoutCasino/" + card.getPath(false));
		//texture.setLeft((card.getNumber().getValue()-1)*24).setTop((card.getSuit().getValue()-1)*32);
	}
	
	@Override
	public Container setWidth(int width) {
		super.setWidth(width);
		super.setHeight((int) (width * (107d/75d)));
		return this;
	}
	
	@Override
	public Container setHeight(int width) {
		super.setHeight(height);
		super.setWidth((int) (height * (75d/107d)));
		return this;
	}
}
