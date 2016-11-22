package hsadoyan;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Pile;

/**
 * Created by ftlc on 11/19/16.
 */
public class MoveWasteToFoundationMove extends ks.common.model.Move{

    protected Pile waste;
    protected Card draggingCard;
    protected Pile foundation;
    protected BritishSquare theGame;
    protected boolean aceSet;

    public MoveWasteToFoundationMove(Pile waste, Card draggingCard, Pile foundation, BritishSquare theGame) {
        super();

        this.waste = waste;
        this.draggingCard = draggingCard;
        this.foundation = foundation;
        this.theGame = theGame;
        aceSet = false;
    }

    public boolean doMove (Solitaire theGame) {

        if(valid(theGame) == false)
        {   return false; }

        if (draggingCard == null)
        {
            foundation.add(waste.get());
        }
        else
        {
            foundation.add (draggingCard);
        }

        theGame.updateScore(1);
        return true;
    }

    @Override
    public boolean undo(Solitaire game) {
        // VALIDATE:
        if (foundation.empty()) {
            return false;
        }

        
        
        // EXECUTE:
        // remove card and move to waste.
        waste.add (foundation.get());
        if(isAceSet())
        {
            int[] suits = theGame.getSuitsUsed();
            suits[draggingCard.getSuit() -1] = 0;

            theGame.setSuitsUsed(suits);
        }
        // reverse score advance
        game.updateScore (-1);
        return true;
    }

    @Override
    public boolean valid(Solitaire game) {
        boolean v = false;


        Card c;
        if (draggingCard == null) {
            if (waste.empty()) {
                return false;
            }
            c = waste.peek();
        } else {
            c = draggingCard;
        }

        if (foundation.empty()) {
            if (c.getRank() == Card.ACE) {
                int[] suits = theGame.getSuitsUsed();

                if (suits[c.getSuit() - 1] == 0) {
                    v = true;
                    suits[c.getSuit() - 1] = 1;
                    setAceSet(true);
                    theGame.setSuitsUsed(suits);
                }
            }
        } else {
            if (foundation.count() < 13) {
                if ((c.getRank() == foundation.rank() + 1) && (c.getSuit() == foundation.suit())) {
                    v = true;
                }

            }
            if (foundation.count() == 13) {
                if ((c.getRank() == Card.KING) && (foundation.rank() == Card.KING) && (foundation.suit() == c.getSuit())) {
                    v = true;

                }
            }
            if (foundation.count() > 13) {

                if ((c.getRank() == foundation.rank() - 1) && (c.getSuit() == foundation.suit())) {
                    v = true;
                }
            }
        }

        return v;

    }

    public boolean isAceSet() {
        return aceSet;
    }

    public void setAceSet(boolean aceSet) {
        this.aceSet = aceSet;
    }
}
