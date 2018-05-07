package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Assignment 1: Each of the blank methods below require implementation to get AcesUp to build/run
 * dealFour(), remove(), move(), and columnHasCards()
 */
public class Game {

    public java.util.List<Card> deck = new ArrayList<>();
    public java.util.List<Card> initDeckSnapShot = new ArrayList<>();
    public java.util.List<java.util.List<Card>> cols = new ArrayList<>();


    public Game(){
        cols.add(new ArrayList<Card>());
        cols.add(new ArrayList<Card>());
        cols.add(new ArrayList<Card>());
        cols.add(new ArrayList<Card>());
    }

    public void buildDeck() {
        for(int i = 2; i < 15; i++){
            deck.add(new Card(i,Suit.Clubs));
            deck.add(new Card(i,Suit.Hearts));
            deck.add(new Card(i,Suit.Diamonds));
            deck.add(new Card(i,Suit.Spades));
        }
    }

    public void start(){
        buildDeck();
        shuffle();
        takeSnapshot();
        dealFour();
    }

    public void shuffle() {
        long seed = System.nanoTime();
        Collections.shuffle(deck, new Random(seed));
    }

    // remove the top card from the deck and add it to a column; repeat for each of the four columns
    public void dealFour() {

        //if all cards dealt out
        if (deck.size() == 0)
            return;

        //move the top cards from the deck to columns
        for(int i =0; i < 4; i++){
            cols.get(i).add(deck.remove(deck.size() - 1));
        }
   }

    public void resetGame() {
        //clear the columns
        for(int i = 0; i < cols.size() ; i++) {
            cols.get(i).clear();
        }
        //reset the deck to the initial state( after shuffling)
        resetDeck();

        //deal
        dealFour();

    }


    // remove the top card from the indicated column
    public void remove(int columnNumber) {

        //if the column number is invalid
        if (columnNumber >= cols.size() )
            return;

        java.util.List<Card> col = cols.get(columnNumber);

        //if the column is empty
        if(col.size()== 0)
            return;

        Card cardToRemove = col.get(col.size() - 1);

        //check all other columns for a card of the same suit with higher rank
        for(int i = 0; i < cols.size() ; i++)
        {
            java.util.List<Card> otherCol = cols.get(i);

            //if other column is not empty
            if(otherCol.size() > 0){
                Card otherCard = otherCol.get(otherCol.size() -1);

                //if the other card has the same suit of the card with higher rank, remove the card.
                if(otherCard.suit == cardToRemove.suit && otherCard.value > cardToRemove.value){
                    col.remove(col.size() - 1);
                    return;
                }
            }
        }

    }

    // check indicated column for number of cards; if no cards return false, otherwise return true
    private boolean columnHasCards(int columnNumber) {

        //check colunmNumber be in the range
        if (columnNumber >= cols.size())
            return false;

        return (cols.get(columnNumber).size() > 0);

    }

    private Card getTopCard(int columnNumber) {
        return this.cols.get(columnNumber).get(this.cols.get(columnNumber).size()-1);
    }


    // remove the top card from the columnFrom column, add it to the columnTo column
    public void move(int columnFrom, int columnTo) {

        //if the column numbers are invalid
        if (columnFrom >= cols.size() || columnTo >= cols.size())
            return;

        //If the "from column"  is empty or "to column" is not empty
        if (! columnHasCards(columnFrom) || columnHasCards(columnTo))
            return;

        java.util.List<Card> colFrom = cols.get(columnFrom);
        java.util.List<Card> colTo = cols.get(columnTo);

        Card cardToMove = colFrom.remove(colFrom.size() - 1);
        colTo.add(cardToMove);

    }

    private void addCardToCol(int columnTo, Card cardToMove) {
        cols.get(columnTo).add(cardToMove);
    }

    private void removeCardFromCol(int colFrom) {
        this.cols.get(colFrom).remove(this.cols.get(colFrom).size()-1);
    }

    private void takeSnapshot(){

        for(int i = 0; i < deck.size(); i++)
        {
            initDeckSnapShot.add(new Card(deck.get(i).value, deck.get(i).suit));
        }
    }

    private void resetDeck(){

        deck.clear();

        for(int i = 0; i < initDeckSnapShot.size(); i++)
        {
            deck.add(new Card(initDeckSnapShot.get(i).value, initDeckSnapShot.get(i).suit));
        }
    }
}
