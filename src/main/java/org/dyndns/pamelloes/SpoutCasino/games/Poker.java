package org.dyndns.pamelloes.SpoutCasino.games;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dyndns.pamelloes.SpoutCasino.cards.Card;

public class Poker {
	public static enum Hand {
		HighCard,
		Pair,
		TwoPair,
		ThreeKind,
		Straight,
		Flush,
		FullHouse,
		FourKind,
		StraightFlush;
	}

	/**
	 * Returns the higher High Card of the two provided.
	 * <br />
	 * <br />
	 * <strong>Note:</strong> This method doesn't validate that the
	 * provided hands are actually High Cards. If they are not,
	 * expect unpredictable results.
	 */
	public static Card[] compareHighCard(Card[] a, Card[] b) {
		List<Integer> na = new ArrayList<Integer>();
		List<Integer> nb = new ArrayList<Integer>();
		for(Card c : a) na.add(c.getNumber().getValue());
		for(Card c : b) nb.add(c.getNumber().getValue());
		Collections.sort(na, Collections.reverseOrder());
		Collections.sort(nb, Collections.reverseOrder());
		for(int i = 0; i < na.size();i++) {
			if(na.get(i)==nb.get(i)) continue;
			return na.get(i) > nb.get(i) ? a : b;
		}
		return null;
	}
	
	/**
	 * Determines if the given hand contains a pair.
	 * @param hand a hand of cards, can be any size.
	 * @return true if the hand contains a pair.
	 */
	public static boolean pair(Card[] hand) {
		for(int i = 0; i < hand.length - 1; i++) {
			for(int j = i+1; j < hand.length; j++) {
				if(hand[i].getNumber().getValue()==hand[j].getNumber().getValue()) return true;
			}
		}
		return false;
	}

	/**
	 * Returns the higher Pair of the two provided.
	 * <br />
	 * <br />
	 * <strong>Note:</strong> This method doesn't validate that the
	 * provided hands are actually Pairs. If they are not,
	 * expect unpredictable results.
	 */
	public static Card[] comparePair(Card[] a, Card[] b) {
		int val1 = 0;
		int val2 = 0;
		l1: for(int i = 0; i < a.length - 1; i++) {
			for(int j = i+1; j < a.length; j++) {
				if(a[i].getNumber().getValue()==a[j].getNumber().getValue()) {
					val1 = a[i].getNumber().getValue();
					break l1;
				}
			}
		}
		l2: for(int i = 0; i < b.length - 1; i++) {
			for(int j = i+1; j < b.length; j++) {
				if(b[i].getNumber().getValue()==b[j].getNumber().getValue()) {
					val2 = b[i].getNumber().getValue();
					break l2;
				}
			}
		}
		return val1 == val2 ? compareHighCard(a,b) : val1 > val2 ? a : b;
	}
		
	/**
	 * Determines if the given hand contains two pairs.
	 * @param hand a hand of cards, can be any size.
	 * @return true if the hand contains two pairs.
	 */
	public static boolean twoPair(Card[] hand) {
		int first = -1;
		for(int i = 0; i < hand.length - 1; i++) {
			for(int j = i+1; j < hand.length; j++) {
				if(hand[i].getNumber().getValue()==hand[j].getNumber().getValue()) {
					if(first==-1) {
						first = hand[i].getNumber().getValue();
					} else if(hand[i].getNumber().getValue()!=first) return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns the higher Two Pair of the two provided.
	 * <br />
	 * <br />
	 * <strong>Note:</strong> This method doesn't validate that the
	 * provided hands are actually Two Pairs. If they are not,
	 * expect unpredictable results.
	 */
	public static Card[] compareTwoPair(Card[] a, Card[] b) {
		int val1a = -1, val1b = 0;
		int val2a = -1, val2b = 0;
		l1: for(int i = 0; i < a.length - 1; i++) {
			for(int j = i+1; j < a.length; j++) {
				if(a[i].getNumber().getValue()==a[j].getNumber().getValue()) {
					if(val1a==-1) {
						val1a = a[i].getNumber().getValue();
					} else if(a[i].getNumber().getValue()!=val1a) {
						val1b = a[i].getNumber().getValue();
						break l1;
					}
				}
			}
		}
		if(val1b > val1a) {
			int temp = val1a;
			val1a = val1b;
			val1b = temp;
		}
		l2: for(int i = 0; i < b.length - 1; i++) {
			for(int j = i+1; j < b.length; j++) {
				if(b[i].getNumber().getValue()==b[j].getNumber().getValue()) {
					if(val2a==-1) {
						val2a = b[i].getNumber().getValue();
					} else if(b[i].getNumber().getValue()!=val2a) {
						val2b = b[i].getNumber().getValue();
						break l2;
					}
				}
			}
		}
		if(val2b > val2a) {
			int temp = val2a;
			val2a = val2b;
			val2b = temp;
		}
		return val1a > val2a ? a : val2a > val1a ? b : val1b > val2b ? a : val2b > val1b ? b : compareHighCard(a,b);
	}
	
	/**
	 * Determines if the given hand contains 3 of a kind.
	 * @param hand a hand of cards, can b3 of a kind.
	 */
	public static boolean tkind(Card[] hand) {
		for(int i = 0; i < hand.length - 2; i++) {
			for(int j = i+1; j < hand.length - 1; j++) {
				for(int b = j+1; b < hand.length; b++) {
					if(hand[i].getNumber().getValue()==hand[j].getNumber().getValue() && hand[i].getNumber().getValue()==hand[b].getNumber().getValue()) return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns the higher Three of a Kind of the two provided.
	 * <br />
	 * <br />
	 * <strong>Note:</strong> This method doesn't validate that the
	 * provided hands are actually Three of a Kind. If they are not,
	 * expect unpredictable results.
	 */
	public static Card[] compareTKind(Card[] a, Card[] b) {
		int val1 = 0;
		int val2 = 0;
		l1: for(int i = 0; i < a.length - 2; i++) {
			for(int j = i+1; j < a.length - 1; j++) {
				for(int k = j+1; k < a.length; k++) {
					if(a[i].getNumber().getValue()==a[j].getNumber().getValue() && a[i].getNumber().getValue()==a[k].getNumber().getValue()){
						val1 = a[i].getNumber().getValue();
						break l1;
					}
				}
			}
		}
		l2: for(int i = 0; i < b.length - 2; i++) {
			for(int j = i+1; j < b.length - 1; j++) {
				for(int k = j+1; k < b.length; k++) {
					if(b[i].getNumber().getValue()==b[j].getNumber().getValue() && b[i].getNumber().getValue()==b[k].getNumber().getValue()) {
						val2 = b[i].getNumber().getValue();
						break l2;
					}
				}
			}
		}
		return val1 > val2 ? a : val2 > val1 ? b : compareHighCard(a,b);
	}
	
	/**
	 * Determines if the given hand is a straight (cards form a consecutive run. i.e 1,2,3,4,5 or 8,9,10,J,K,Q)
	 * @param hand a hand of cards, can be any size.
	 * @return true if the hand is a straight.
	 */
	public static boolean straight(Card[] hand) {
		int[] vals = new int[hand.length];
		for(int i = 0; i < vals.length;i++) vals[i] = hand[i].getNumber().getValue();
		Arrays.sort(vals);
		for(int i = 1; i < vals.length; i++) {		
			if(vals[i]!=vals[i-1]+1) return false;
		}
		return true;
	}
	
	/**
	 * Returns the higher Straight of the two provided.
	 * <br />
	 * <br />
	 * <strong>Note:</strong> This method doesn't validate that the
	 * provided hands are actually Straights. If they are not,
	 * expect unpredictable results.
	 */
	public static Card[] compareStraight(Card[] a, Card[] b) {
		return compareHighCard(a,b);
	}   
	
	/**
	 * Determines if the given hand is a flush (all the same suit).
	 * @param hand a hand of cards, can be any size.
	 * @return true if the hand is a flush.
	 */
	public static boolean flush(Card[] hand) {
		int suit = hand[0].getSuit().getValue();
		for(int i = 0; i < hand.length; i++) {
			if(hand[i].getSuit().getValue()!=suit) return false;
		}
		return true;
	}
	
	/**
	 * Returns the higher Flush of the two provided.
	 * <br />
	 * <br />
	 * <strong>Note:</strong> This method doesn't validate that the
	 * provided hands are actually Flushes. If they are not,
	 * expect unpredictable results.
	 */
	public static Card[] compareFlush(Card[] a, Card[] b) {
		return compareHighCard(a,b);
	}   
	
	/**
	 * Determines if the given hand contains a full house (a pair and a 3 of a kind).
	 * @param hand a hand of cards, can be any size.
	 * @return true if the hand contains a full house.
	 */
	public static boolean fullHouse(Card[] hand) {
		int tkind = -1;
		tkind: for (int i = 0; i < hand.length - 2; i++) {
			for (int j = i + 1; j < hand.length - 1; j++) {
				for (int b = j + 1; b < hand.length; b++) {
					if (hand[i].getNumber().getValue() == hand[j].getNumber().getValue() && hand[i].getNumber().getValue() == hand[b].getNumber().getValue()) {
						tkind = hand[i].getNumber().getValue();
						break tkind;
					}
				}
			}
		}
		if(tkind<0) return false;
		for(int i = 0; i < hand.length - 1; i++) {
			for(int j = i+1; j < hand.length; j++) {
				if(hand[i].getNumber().getValue()==hand[j].getNumber().getValue() && hand[i].getNumber().getValue()!=tkind) return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the higher Full House of the two provided.
	 * <br />
	 * <br />
	 * <strong>Note:</strong> This method doesn't validate that the
	 * provided hands are actually Full Houses. If they are not,
	 * expect unpredictable results.
	 */
	public static Card[] compareFullHouse(Card[] a, Card[] b) {
		return compareTKind(a,b);
	}

	/**
	 * Determines if the given hand contains four of a kind.
	 * @param hand a hand of cards, can be any size.
	 * @return true if the hand contains four of a kind.
	 */
	public static boolean fkind(Card[] hand) {
		for(int i = 0; i < hand.length - 3; i++) {
			for(int j = i+1; j < hand.length - 2; j++) {
				for(int b = j+1; b < hand.length - 1; b++) {
					for(int z = b+1; z < hand.length; z++) {
						if(hand[i].getNumber().getValue()==hand[j].getNumber().getValue() && hand[i].getNumber().getValue()==hand[b].getNumber().getValue() && hand[i].getNumber().getValue()==hand[z].getNumber().getValue()) return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Returns the higher Four of a Kind of the two provided.
	 * <br />
	 * <br />
	 * <strong>Note:</strong> This method doesn't validate that the
	 * provided hands are actually Four of a Kinds. If they are not,
	 * expect unpredictable results.
	 */
	public static Card[] compareFKind(Card[] a, Card[] b) {
		int val1 = 0;
		int val2 = 0;
		l1: for(int i = 0; i < a.length - 3; i++) {
			for(int j = i+1; j < a.length - 2; j++) {
				for(int k = j+1; k < a.length - 1; k++) {
					for(int z = k+1; z < a.length; z++) {
						if(a[i].getNumber().getValue()==a[j].getNumber().getValue() && a[i].getNumber().getValue()==a[k].getNumber().getValue() && a[i].getNumber().getValue()==a[z].getNumber().getValue()) {
							val1 = a[i].getNumber().getValue();
							break l1;
						}
					}
				}
			}
		}
		l2: for(int i = 0; i < b.length - 3; i++) {
			for(int j = i+1; j < b.length - 2; j++) {
				for(int k = j+1; k < b.length - 1; k++) {
					for(int z = k+1; z < b.length; z++) {
						if(b[i].getNumber().getValue()==b[j].getNumber().getValue() && b[i].getNumber().getValue()==b[k].getNumber().getValue() && b[i].getNumber().getValue()==b[z].getNumber().getValue()) {
							val2 = b[i].getNumber().getValue();
							break l2;
						}
					}
				}
			}
		}
		return val1 > val2 ? a : val2 > val1 ? b : compareHighCard(a,b);
	}

	/**
	 * Determines if the given hand is a straightflush (both a straight and a flush).
	 * @param hand a hand of cards, can be any size.
	 * @return true if the hand is a straightflush.
	 */
	public static boolean straightFlush(Card[] hand) {
		if(straight(hand) && flush(hand)) return true;
		return false;
	}
	
	/**
	 * Returns the higher Straight Flush of the two provided.
	 * <br />
	 * <br />
	 * <strong>Note:</strong> This method doesn't validate that the
	 * provided hands are actually Straight Flushes. If they are not,
	 * expect unpredictable results.
	 */
	public static Card[] compareStraightFlush(Card[] a, Card[] b) {
		return compareStraight(a,b);
	}
	
	/**
	 * Determines the best poker hand from the given set of cards.
	 * @param hand a hand of cards, can be any size.
	 * @return the best poker hand in the given cards.
	 */
	public static Hand getBestHandType(Card[] hand) {
		if(straightFlush(hand)) return Hand.StraightFlush;
		if(fkind(hand)) return Hand.FourKind;
		if(fullHouse(hand)) return Hand.FullHouse;
		if(flush(hand)) return Hand.Flush;
		if(straight(hand)) return Hand.Straight;
		if(tkind(hand)) return Hand.ThreeKind;
		if(twoPair(hand)) return Hand.TwoPair;
		if(pair(hand)) return Hand.Pair;
		return Hand.HighCard;
	}
	
	/**
	 * Returns the higher hand, or null if the hands are equal.
	 */
	public static Card[] compareHands(Card[] hand1, Card[] hand2) {
		Hand a = getBestHandType(hand1);
		Hand b = getBestHandType(hand2);
		if(a.ordinal() > b.ordinal()) return hand1;
		if(b.ordinal() > a.ordinal()) return hand2;
		switch (a) {
		case HighCard:
			return compareHighCard(hand1,hand2);
		case Pair:
			return comparePair(hand1,hand2);
		case TwoPair:
			return compareTwoPair(hand1,hand2);
		case ThreeKind:
			return compareTKind(hand1,hand2);
		case Straight:
			return compareStraight(hand1,hand2);
		case Flush:
			return compareFlush(hand1,hand2);
		case FullHouse:
			return compareFullHouse(hand1,hand2);
		case FourKind:
			return compareFKind(hand1,hand2);
		case StraightFlush:
			return compareStraightFlush(hand1,hand2);
		}
		return null;
	}
	
	/**
	 * Gets the index of the best hand in the provided list
	 * of 5-card hands.
	 */
	public static int getBestHand(List<Card[]> hands) {
		Hand best = Hand.HighCard;
		Map<Card[], Integer> bestcombs = new HashMap<Card[], Integer>();
		for(int i = 0; i < hands.size(); i ++) {
			Hand b = getBestHandType(hands.get(i));
			if(b.ordinal() > best.ordinal()) {
				best = b;
				bestcombs.clear();
				bestcombs.put(hands.get(i),i);
			} else if(b.ordinal() == best.ordinal()) bestcombs.put(hands.get(i),i);
		}
		Entry<Card[],Integer> bhand = null;
		for(Entry<Card[],Integer> entry : bestcombs.entrySet()) {
			if(bhand==null) {
				bhand = entry;
				continue;
			}
			Card[] btemp = null;
			switch(best) {
			case HighCard:
				btemp = compareHighCard(bhand.getKey(), entry.getKey());
				break;
			case Pair:
				btemp = comparePair(bhand.getKey(), entry.getKey());
				break;
			case TwoPair:
				btemp = compareTwoPair(bhand.getKey(), entry.getKey());
				break;
			case ThreeKind:
				btemp = compareTKind(bhand.getKey(), entry.getKey());
				break;
			case Straight:
				btemp = compareStraight(bhand.getKey(), entry.getKey());
				break;
			case Flush:
				btemp = compareFlush(bhand.getKey(), entry.getKey());
				break;
			case FullHouse:
				btemp = compareFullHouse(bhand.getKey(), entry.getKey());
				break;
			case FourKind:
				btemp = compareFKind(bhand.getKey(), entry.getKey());
				break;
			case StraightFlush:
				btemp = compareStraightFlush(bhand.getKey(), entry.getKey());
				break;
			}
			if(btemp!=null) if(entry.getKey().equals(btemp)) bhand = entry;
		}
		return bhand.getValue();
	}
	
	/**
	 * This method takes an array of Card of an arbitrary
	 * length and makes the best 5-card hand from them. If
	 * less than five cards are provided, an empty Card[]
	 * is returned.
	 */
	public static Card[] getBestHand(Card[] in) {
		if(in.length < 5) return new Card[0];
		if(in.length == 5) return in;
		List<Card[]> combs = collectCombinations(in);
		return combs.get(getBestHand(combs));
	}
	
	/**
	 * Gets a list of all possible 5-card hands from the given list of cards.
	 */
	private static List<Card[]> collectCombinations(Card[] list) {
        int n = list.length;

        ArrayList<Card[]> combinations = new ArrayList<Card[]>();
        
        int len = 5;

		for (int i = 0; i <= n - len; ++i) {
			for (int j = i + 1; j <= n - (len - 1); ++j) {
				for (int k = j + 1; k <= n - (len - 2); ++k) {
					for (int l = k + 1; l <= n - (len - 3); ++l) {
						for (int m = l + 1; m <= n - (len - 4); ++m) {
							combinations.add(new Card[] { list[i], list[j], list[k], list[l], list[m] });
						}
					}
				}
			}
		}
        return combinations;
    }
}
