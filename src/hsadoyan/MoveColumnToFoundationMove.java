package hsadoyan;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Pile;

/**
 * Created by ftlc on 11/20/16.
 */
public class MoveColumnToFoundationMove extends ks.common.model.Move {

    protected BritishColumn col;
    protected Card draggingCard;
    protected Pile foundation;
    protected BritishSquare theGame;
    protected boolean aceSet;



    public MoveColumnToFoundationMove(BritishColumn col, Card card, Pile foundation, BritishSquare theGame){
        super();
        this.col = col;
        this.draggingCard = card;
        this.foundation = foundation;
        this.theGame = theGame;
        aceSet = false;
    }

    public boolean isAceSet() {
        return aceSet;
    }

    public void setAceSet(boolean aceSet) {
        this.aceSet = aceSet;
    }

    @Override
    public boolean doMove(Solitaire theGame) {
        if(!valid(theGame))
        {
            return false;
        }

        if(draggingCard == null)
        {
            foundation.add(col.get());
        }
        else
        {
            foundation.add(draggingCard);
        }

        theGame.updateScore(1);
        return true;
    }

    @Override
    public boolean undo(Solitaire game) {
        if(foundation.empty()){ return false;}

        col.add(foundation.get());

        if(isAceSet())
        {
            int[] suits = theGame.getSuitsUsed();
            suits[draggingCard.getSuit() -1] = 0;

            theGame.setSuitsUsed(suits);
        }
        game.updateScore(-1);
        return true;
    }

    @Override
    public boolean valid(Solitaire game) {
         boolean v = false;



        System.out.println(draggingCard);
        Card c;
        if(draggingCard == null) {
            if (col.empty()) {
                return false;
            }
            c = col.peek();
        } else {
            c = draggingCard;
        }

        if(foundation.empty())
        {
            if(c.getRank() == Card.ACE)
            {
                int[] suits = theGame.getSuitsUsed();

                if(suits[c.getSuit() -1] == 0) {
                    v = true;
                    suits[c.getSuit() -1] = 1;
                    theGame.setSuitsUsed(suits);
                    setAceSet(true);
                }

            }
        }
        else
        {
            if(foundation.count() < 13)
            {
                if((c.getRank() == foundation.rank() + 1) && (c.getSuit() == foundation.suit()))
                {
                    v = true;
                }

            }
            if(foundation.count() == 13)
            {
                if ((c.getRank() == Card.KING) && (foundation.rank() == Card.KING) && (foundation.suit() == c.getSuit()))
                {
                    v = true;

                }
            }
            if(foundation.count() > 13)
            {

                if((c.getRank() == foundation.rank() - 1) && (c.getSuit() == foundation.suit()))
                {
                    v = true;
                }
            }
        }

        return v;
    }



}



