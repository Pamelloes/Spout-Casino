package org.dyndns.pamelloes.SpoutCasino.gui.holdem;

import org.dyndns.pamelloes.SpoutCasino.cards.Card;
import org.dyndns.pamelloes.SpoutCasino.cards.Deck;
import org.dyndns.pamelloes.SpoutCasino.cards.Card.CardNumber;
import org.dyndns.pamelloes.SpoutCasino.cards.Card.Suit;
import org.dyndns.pamelloes.SpoutCasino.gui.CardWidget;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.Container;
import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericGradient;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.Gradient;
import org.getspout.spoutapi.gui.Label;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.WidgetAnchor;

public class OtherPlayerGui extends GenericContainer {
	private Container active;
	private Label empty, waiting, caption;
	private Gradient bg;
	private Color dealercolor = new Color(49,70,224,128), turncolor = new Color(224,70,49,128);
	private boolean dealer, turn;
	
	public OtherPlayerGui(Deck deck) {
		makeElements(deck);
		setLayout(ContainerType.OVERLAY);
		addChild(empty);
	}
	
	public void setDealer(boolean dealer) {
		this.dealer = dealer;
		if(turn) return;
		if(dealer) {
			bg.setColor(dealercolor);
			caption.setText("Dealer");
			addChildren(this.bg,caption);
		} else {
			removeChild(this.bg).removeChild(caption);
		}
	}
	
	public void setTurn(boolean turn) {
		this.turn = turn;
		if(turn) {
			bg.setColor(turncolor);
			caption.setText("Playing");
			addChildren(bg,caption);
		} else if(dealer) {
			bg.setColor(dealercolor);
			caption.setText("Dealer");
		} else {
			removeChild(this.bg).removeChild(caption);
		}
	}
	
	/**
	 * 0 = empty
	 * 1 = waiting
	 * 2 = active
	 */
	public void setState(int state) {
		removeChild(active).removeChild(empty).removeChild(waiting);
		switch(state) {
		case 0:
			addChild(empty);
			break;
		case 1:
			addChild(waiting);
			break;
		case 2:
			addChild(active);
			break;
			default:
				throw new IllegalArgumentException("Illegal state " + state + " - state must be between 0 and 2 (inclusive)");
		}
	}
		
	private void makeElements(Deck deck) {
		bg = new GenericGradient(new Color(49,70,224,128));
		bg.setPriority(RenderPriority.Low).setMarginBottom(12);
		caption = new GenericLabel("Dealer");
		caption.setAlign(WidgetAnchor.BOTTOM_CENTER).setPriority(RenderPriority.Lowest);	
		
		active = new GenericContainer();
		active.setLayout(ContainerType.HORIZONTAL);
 		CardWidget left = new CardWidget(new Card(deck,Suit.Joker, CardNumber.Joker1));
		left.showBack(true);
		left.setMargin(3).setPriority(RenderPriority.Lowest);
		left.setWidth(23).setFixed(true);
		CardWidget right = new CardWidget(new Card(deck,Suit.Joker, CardNumber.Joker2));
		right.showBack(true);
		right.setMargin(3).setPriority(RenderPriority.Lowest);
		right.setWidth(23).setFixed(true);
		active.addChildren(left,right);
		
		empty = new GenericLabel("Empty");
		empty.setTextColor(new Color(1.0f,0.3f,0.3f)).setAlign(WidgetAnchor.CENTER_CENTER).setPriority(RenderPriority.Lowest);
		
		waiting = new GenericLabel("Waiting");
		waiting.setTextColor(new Color(1.0f,0.3f,0.3f)).setAlign(WidgetAnchor.CENTER_CENTER).setPriority(RenderPriority.Lowest);	
	}
	
	@Override
	public Container setWidth(int width) {
		super.setWidth(width);
		empty.setMarginLeft(width/2);
		waiting.setMarginLeft(width/2);
		caption.setMarginLeft(width/2);
		return this;
	}
	
	@Override
	public Container setHeight(int height) {
		super.setHeight(height);
		empty.setMarginTop(height/2);
		waiting.setMarginTop(height/2);
		caption.setMarginTop(height - 12);
		return this;
	}
}
