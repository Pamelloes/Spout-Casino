package org.dyndns.pamelloes.SpoutCasino.cards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.dyndns.pamelloes.SpoutCasino.cards.Card.CardNumber;
import org.dyndns.pamelloes.SpoutCasino.cards.Card.Suit;

public class Deck {
	private final Card[] cards = new Card[52];
	private final Card[] jokers;
	
	private ArrayList<Card> deck = new ArrayList<Card>();
	
	/**
	 * Creates a deck. This creates all of the cards needed for the deck, however
	 * the active deck remains empty. Before any use makeDeck() needs to be called
	 * to fill the active deck.
	 */
	public Deck() {
		jokers = new Card[]{new Card(this,null,CardNumber.Joker), new Card(this,null,CardNumber.Joker)};
		for(Suit suit : Suit.values()) {
			for(CardNumber number : CardNumber.values()) {
				if(number == CardNumber.Joker) continue;
				int location = (suit.getValue()-1)*13;
				location+=(number.getValue()-1);
				cards[location] = new Card(this,suit,number);
			}
		}
	}
	
	/**
	 * This method prepares a deck. It moves all of the cards into
	 * the active deck.
	 * 
	 * @param withJokers if the prepared deck should have Jokers in it.
	 */
	public void makeDeck(boolean withJokers) {
		deck.clear();
		deck.ensureCapacity(52);
		deck.addAll(Arrays.asList(cards));
		if(withJokers) {
			deck.ensureCapacity(54);
			deck.addAll(Arrays.asList(jokers));
		}
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
	 * @return false if the Card is not a member of this Deck, if the Card is
	 * already in the active deck, or if something went wrong inserting the
	 * Card into the Deck.
	 */
	public boolean restoreCard(Card c) {
		if(!c.getDeck().equals(this)) return false;
		if(deck.contains(c)) return false;
		return deck.add(c);
	}
	
	/**
	 * Restore the card with the specified suit and number. If the specified CardNumber
	 * is Joker, then this method tries to restore the first Joker. If the first Joker
	 * can't be restored, then the second is restored. The method returns true if either
	 * Joker is restored and false if neither is restored. To make sure both are restored,
	 * then this method must be called twice. This is not the recommended method to restore
	 * a Joker, it is preferred to acquire a reference to Joker object and call restoreCard(Card).
	 * 
	 * @return false if the Card is already in the active deck, or if something
	 * went wrong inserting the Card into the Deck.
	 */
	public boolean restoreCard(Suit suit, CardNumber number) {
		if(number!=CardNumber.Joker) {
			int location = (suit.getValue()-1)*13;
			location+=number.getValue();
			Card c = cards[location];
			if(deck.contains(c)) return false;
			return deck.add(c);
		} else {
			Card c = jokers[0];
			boolean success = true;
			if(deck.contains(c)) success =  false;
			if(success) success = deck.add(c);
			if(success) return true;
			c = jokers[1];
			if(deck.contains(c)) return false;
			return deck.add(c);
		}
	}
	
	/**
	 * Gets the card with the specified suit and number. If Joker is specified
	 * this method returns null.
	 */
	public Card getCard(Suit suit, CardNumber number) {
		if(number!=CardNumber.Joker) return null;
		int location = (suit.getValue()-1)*13;
		location+=number.getValue();
		return cards[location];
	}
	
	/**
	 * Gets the Jokers in the deck.
	 * @return
	 */
	public Card[] getJokers() {
		return jokers;
	}
}
