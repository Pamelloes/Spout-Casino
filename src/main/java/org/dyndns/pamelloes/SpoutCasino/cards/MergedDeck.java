package org.dyndns.pamelloes.SpoutCasino.cards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.dyndns.pamelloes.SpoutCasino.cards.Card.CardNumber;
import org.dyndns.pamelloes.SpoutCasino.cards.Card.Suit;

/**
 * A MergedDeck is a deck made up of other Deck instances. A MergedDeck allows for a "super" Deck
 * from, for example, 2 decks. The resulting MergedDeck will behave almost exactly like a normal
 * Deck, except that instead of having 52 cards, it will have 104 (2 of each). Because MergedDeck
 * subclasses Deck, in almost every case where a Deck is acceptable, a MergedDeck will also be
 * acceptable. You can even make a MergedDeck out of MergedDecks!
 * <br />
 * <br />
 * One important thing to note, however, is that the getCard() method doesn't work and that the
 * restoreCard(Suit,CardNumber) method requires that a Deck be specified as well. The inherited
 * method declarations will return false/null respectively. This may make it impossible to substitute
 * a MergedDeck for a Deck in certain situations requiring use of an alternative method.
 */
public class MergedDeck extends Deck {
	private final Card[] cards;
	private final Card[] jokers;
	
	private final Deck[] decks;
	private final ArrayList<Card> deck = new ArrayList<Card>();
	
	/**
	 * Creates a MergedDeck with the given amount of decks.
	 */
	@SuppressWarnings("serial")
	public MergedDeck(final int count) {
		this(new ArrayList<Deck>() {{
			for(int i = 0; i < count; i++) add(new Deck());
		}}.toArray(new Deck[0]));
	}
	
	/**
	 * Creates a MergedDeck from the given Decks.
	 */
	public MergedDeck(Deck... decks) {
		this.decks = decks;
		
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.ensureCapacity(decks.length * 52);
		for(Deck d : decks) cards.addAll(Arrays.asList(d.getCards()));
		this.cards = cards.toArray(new Card[0]);
		
		ArrayList<Card> jokers = new ArrayList<Card>();
		jokers.ensureCapacity(decks.length * 2);
		for(Deck d : decks) jokers.addAll(Arrays.asList(d.getJokers()));
		this.jokers = jokers.toArray(new Card[0]);
	}
	
	/**
	 * This method prepares a deck. It moves all of the cards into
	 * the active deck.
	 * 
	 * @param withJokers if the prepared deck should have Jokers in it.
	 */
	public void makeDeck(boolean withJokers) {
		deck.clear();
		deck.ensureCapacity(withJokers ? cards.length + jokers.length : cards.length);
		deck.addAll(Arrays.asList(cards));
		if(withJokers) deck.addAll(Arrays.asList(jokers));
	}
	
	/**
	 * Shuffles the active deck.
	 */
	public void shuffle() {
		Collections.shuffle(deck);
	}
	
	/**
	 * Gets the card at the top of the active deck. The card is removed
	 * from the from the active deck, however so be warned.
	 */
	public Card dealTop() {
		return deck.remove(0);
	}
	
	/**
	 * Gets the card at the bottom of the active deck. The card is removed
	 * from the from the active deck, however so be warned.
	 */
	public Card dealBottom() {
		return deck.remove(deck.size()-1);
	}
	
	/**
	 * Restores the given card to the active deck.
	 * @return false if the Card is already in the active deck, or if something
	 * went wrong inserting the Card into the Deck.
	 */
	public boolean restoreCard(Card c) {
		if(deck.contains(c)) return false;
		return deck.add(c);
	}
	
	/**
	 * Restore the card in the given Deck with the specified suit and number. 
	 * 
	 * @return false if the Card is already in the active deck, or if something
	 * went wrong inserting the Card into the Deck.
	 */
	public boolean restoreCard(Deck d, Suit suit, CardNumber number) {
		Card c = d.getCard(suit, number);
		if(deck.contains(c)) return false;
		return deck.add(c);
	}
	
	/**
	 * Won't work with a MergedDeck.
	 */
	public boolean restoreCard(Suit s, CardNumber n) { return false; }
	
	/**
	 * Won't work with a MergedDeck.
	 */
	public Card getCard(Suit suit, CardNumber number) { return null; }
	
	/**
	 * Gets the Jokers in the deck.
	 */
	public Card[] getJokers() {
		return jokers;
	}
	
	/**
	 * Gets the Cards in the deck (not including Jokers).
	 */
	public Card[] getCards() {
		return cards;
	}
	
	/**
	 * Gets the "subdecks" that make up this deck.
	 */
	public Deck[] getDecks() {
		return decks;
	}
}
