package org.dyndns.pamelloes.SpoutCasino.gui.holdem;

import org.dyndns.pamelloes.SpoutCasino.cards.Card;
import org.dyndns.pamelloes.SpoutCasino.cards.Card.CardNumber;
import org.dyndns.pamelloes.SpoutCasino.cards.Card.Suit;
import org.dyndns.pamelloes.SpoutCasino.games.HoldemController;
import org.dyndns.pamelloes.SpoutCasino.gui.CardWidget;
import org.dyndns.pamelloes.SpoutCasino.gui.TableGui;
import org.getspout.spoutapi.event.screen.ScreenCloseEvent;
import org.getspout.spoutapi.gui.Container;
import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.player.SpoutPlayer;

public class HoldemGui extends TableGui {
	private HoldemController control;
	private CardWidget left, right;
	private OtherPlayerGui[] others;
	private int id;

	public HoldemGui(SpoutPlayer player, HoldemController control, int id) {
		super(player);
		this.control = control;
		this.id = id;
		makeGui();
	}

	protected void makeGui() {
		setSize(200);
		Container rows = new GenericContainer();
		rows.setLayout(ContainerType.VERTICAL);
		rows.addChild(getTopRow());
		rows.addChild(getMiddleRow());
		rows.addChild(getBottomRow());
		rows.setMargin(10);
		container.addChild(rows);
	}
	
	private Container getTopRow() {
		Container top = new GenericContainer();
		top.setLayout(ContainerType.HORIZONTAL);
		top.addChild(new GenericLabel("Left"));
		//top.addChild(getCardsHolder());
		OtherPlayerGui opg = new OtherPlayerGui(control.getDeck());
		opg.setState(1);
		top.addChild(opg);
		others[(id + 2) % 4] = opg;
		top.addChild(new GenericLabel("Right"));
		return top;
	}
	
	private Container getMiddleRow() {
		Container mid = new GenericContainer();
		mid.setLayout(ContainerType.HORIZONTAL);
		//mid.addChild(getCardsHolder());
		OtherPlayerGui opg = new OtherPlayerGui(control.getDeck());
		opg.setState(0);
		mid.addChild(opg);
		others[(id + 1) % 4] = opg;
		mid.addChild(new GenericLabel("Center"));
		//mid.addChild(getCardsHolder());
		opg = new OtherPlayerGui(control.getDeck());
		opg.setDealer(true);
		opg.setState(2);
		mid.addChild(opg);
		others[(id + 3) % 4] = opg;
		return mid;
	}
	
	private Container getBottomRow() {
		Container bot = new GenericContainer();
		bot.setLayout(ContainerType.HORIZONTAL);
		bot.addChild(new GenericLabel("Left"));
		Container cards = new GenericContainer();
		cards.setLayout(ContainerType.HORIZONTAL);
		left = new CardWidget(new Card(control.getDeck(),Suit.Joker, CardNumber.Joker1));
		left.setWidth(34).setFixed(true);
		left.setMargin(3);
		right = new CardWidget(new Card(control.getDeck(),Suit.Joker, CardNumber.Joker2));
		right.setWidth(34).setFixed(true);
		right.setMargin(3);
		cards.addChildren(left,right);
		bot.addChild(cards);
		bot.addChild(new GenericLabel("Right"));
		return bot;
	}
	
	@Override
	public void onClose(ScreenCloseEvent e) {
		control.handlePlayerExit(e.getPlayer());
	}
	
	public void showCards(Card left, Card right) {
		this.left.setCard(left);
		this.right.setCard(right);
	}
	
	public void setState(int id, int state) {
		if(others[id] == null) return;
		others[id].setState(state);
	}
	
	public void setTurn(int id) {
		for(int i = 0; i <4; i++) if(others[i]!=null) {
			if(i == id) others[i].setTurn(true);
			else others[i].setTurn(false);
		}
	}
	
	public void setDealer(int id) {
		for(int i = 0; i <4; i++) if(others[i]!=null) {
			if(i == id) others[i].setDealer(true);
			else others[i].setDealer(false);
		}
	}
	
	public void flop(Card a, Card b, Card c) { }
	public void turn(Card a) { }
	public void river(Card a) { }
}
