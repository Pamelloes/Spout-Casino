package org.dyndns.pamelloes.SpoutCasino.gui;

import org.dyndns.pamelloes.SpoutCasino.cards.Card;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.Widget;

public class CardWidget extends GenericTexture {
	private Card card;
	private boolean large, back;
	private double ratio;
	
	public CardWidget(Card card) {
		this(card, false);
	}
	
	public CardWidget(Card card, boolean large) {
		this.card=card;
		setLarge(large);
		setUrl("plugins/SpoutCasino/" + card.getPath(false));
		//texture.setLeft((card.getNumber().getValue()-1)*24).setTop((card.getSuit().getValue()-1)*32).setWidth(24).setHeight(32).setMaxHeight(32).setMaxWidth(24).setMinHeight(32).setMinWidth(24);
		//setLayout(ContainerType.OVERLAY);
	}
	
	public void setLarge(boolean large) {
		this.large = large;
		if(large) {
			setMaxHeight(215).setMaxWidth(150);
			ratio = 215d/150d;
		} else {
			setMaxHeight(107).setMaxWidth(75);
			ratio = 107d/75d;
		}
	}
	
	public boolean isLarge() {
		return large;
	}
	
	public void setCard(Card c) {
		card = c;
		setUrl("plugins/SpoutCasino/" + card.getPath(large));
		//texture.setLeft((card.getNumber().getValue()-1)*24).setTop((card.getSuit().getValue()-1)*32);
	}
	
	public Card getCard() {
		return card;
	}
	
	public void showBack(boolean show) {
		back = show;
		if(show) setUrl("plugins/SpoutCasino/" + card.getDeck().getBack(large).getPath());
		else setUrl("plugins/SpoutCasino/" + card.getPath(large));
	}
	
	public boolean isBackShowing() {
		return back;
	}
	
	@Override
	public Widget setWidth(int width) {
		//System.out.println("width: " + width);
		super.setWidth(width);
		//int height = (int) (((double)width) * ratio);
		//System.out.println("height: " + height);
		super.setHeight((int)(width * ratio));
		return this;
	}
	
	@Override
	public Widget setHeight(int height) {
		super.setHeight(height);
		super.setWidth((int) (height * (1d/ratio)));
		return this;
	}
}
