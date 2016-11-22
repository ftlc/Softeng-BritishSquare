package hsadoyan;

import ks.common.games.Solitaire;
import ks.common.model.Deck;
import ks.common.model.Pile;

/**
 * Created by ftlc on 11/18/16.
 */
public class DealCardMove extends ks.common.model.Move{
    protected Deck deck;

    protected Pile wastePile;

    public DealCardMove(Deck deck, Pile wastePile){
        super();
        this.deck = deck;
        this.wastePile = wastePile;
    }


    @Override
    public boolean doMove(Solitaire game) {
        if(valid(game) == false) {
            return false;
        }

        //Execute
        //Get card from deck

        wastePile.add(deck.get());
        game.updateNumberCardsLeft(-1);
        return true;
    }

    @Override
    public boolean undo(Solitaire game) {
        if(wastePile.empty())
        {
            return false;
        }

        deck.add(wastePile.get());

        game.updateNumberCardsLeft(+1);
        return true;
    }

    @Override
    public boolean valid(Solitaire game) {
        if(deck.empty())
        {
            return false;
        }
        else {
            return true;
        }


    }
}
