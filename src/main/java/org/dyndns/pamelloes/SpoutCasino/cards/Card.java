package org.dyndns.pamelloes.SpoutCasino.cards;

public class Card {
	public static enum Suit {
		Joker(0),
		Clubs(1),
		Diamonds(2),
		Hearts(3),
		Spades(4);
		
		private final int value;
		
		Suit(int value) {
			this.value=value;
		}
		
		public int getValue() {
			return value;
		}
	};
	public static enum CardNumber {
		Joker1(0),
		Ace(1),
		Two(2),
		Three(3),
		Four(4),
		Five(5),
		Six(6),
		Seven(7),
		Eight(8),
		Nine(9),
		Ten(10),
		Jack(11),
		Queen(12),
		King(13),
		Joker2(14);
		
		private final int value;
		
		CardNumber(int value) {
			this.value=value;
		}
		
		public int getValue() {
			return value;
		}
	}
	public static enum CardBack {
		RedSmall1("red-75-1"),
		RedSmall2("red-75-2"),
		RedSmall3("red-75-3"),
		BlueSmall1("blue-75-1"),
		BlueSmall2("blue-75-2"),
		BlueSmall3("blue-75-3"),
		
		RedLarge1("red-150-1"),
		RedLarge2("red-150-2"),
		RedLarge3("red-150-3"),
		RedLarge4("red-150-4"),
		BlueLarge1("blue-150-1"),
		BlueLarge2("blue-150-2"),
		BlueLarge3("blue-150-3"),
		BlueLarge4("blue-150-4");
		
		private final String path;
		
		CardBack(String path) {
			this.path = "Card Deck/back-" + path + ".png";
		}
		
		public String getPath() {
			return path;
		}
	}
	
	private final Deck deck;
	private final Suit suit;
	private final CardNumber number;
	
	public Card(Deck deck, Suit suit, CardNumber number) {
		this.deck = deck;
		this.suit = suit;
		this.number = number;
	}
	
	public Deck getDeck() {
		return deck;
	}
	
	public Suit getSuit() {
		return suit;
	}
	
	public CardNumber getNumber() {
		return number;
	}
	
	@Override
	public boolean equals(Object another) {
		if(!(another instanceof Card)) return false;
		Card alt = (Card) another;
		if(!alt.deck.equals(deck)) return false;
		if(!alt.suit.equals(suit)) return false;
		if(!alt.number.equals(number)) return false;
		return true;
	}
	
	public String getPath(boolean large) {
		StringBuilder sb = new StringBuilder("Card Deck/");
		if(getNumber().getValue()==0) {
			sb.append("joker-r");
		} else if (getNumber().getValue()==14) {
			sb.append("joker-b");
		} else {
			sb.append(getSuit().name().toLowerCase() + "-");
			if (getNumber().getValue() == 1) sb.append("a");
			else if (getNumber().getValue() == 11) sb.append("j");
			else if (getNumber().getValue() == 12) sb.append("q");
			else if (getNumber().getValue() == 13) sb.append("k");
			else sb.append(getNumber().getValue());
		}
		if(large) sb.append("-150.png");
		else sb.append("-75.png");
		return sb.toString();
	}
}
