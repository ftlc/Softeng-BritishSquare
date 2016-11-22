package hsadoyan;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Pile;

/**
 * Created by ftlc on 11/20/16.
 */
public class MoveWasteToColumnMove extends ks.common.model.Move {

    protected Pile waste;
    protected BritishColumn to;
    protected Card draggingCard;
    protected BritishSquare theGame;
    protected int localDirection;
    protected int oldDirection;
    protected boolean directionChanged;





    public MoveWasteToColumnMove(Pile waste, Card draggingCard, BritishColumn toCol, BritishSquare theGame) {
        super();
        this.waste = waste;
        this.to = toCol;
        this.draggingCard = draggingCard;
        this.theGame = theGame;

    }


    public void setLocalDirection(int localDirection) {
        this.localDirection = localDirection;
    }

    public int getLocalDirection() {
        return localDirection;
    }


    public void setOldDirection(int oldDirection) {
        this.oldDirection = oldDirection;
    }

    public int getOldDirection() {
        return oldDirection;
    }



    public boolean isDirectionChanged() {
        return directionChanged;
    }

    public void setDirectionChanged(boolean directionChanged) {
        this.directionChanged = directionChanged;
    }

    @Override
    public boolean doMove(Solitaire theGame) {
        if(!valid(theGame))
        {
            return false;
        }


        setOldDirection(to.getDirection());



        to.setDirection(getLocalDirection());
        if(draggingCard == null) {
            to.add(waste.get());
        }
        else {
            to.add(draggingCard);
        }

        return true;
    }

    @Override
    public boolean undo(Solitaire game) {
        if (to.empty()) {
            return false;
        }

        waste.add(to.get());
        to.setDirection(to.getOldDirection());

        return true;
    }


    @Override
    public boolean valid(Solitaire game) {
        boolean v = false;


        if (to.empty()) {
            v = true;
        } else {


            if (to.suit() != draggingCard.getSuit()) {
                return false;
            }

            if (to.rank() == Card.KING && draggingCard.getRank() == Card.KING) {
                v = true;
                setLocalDirection(-1);
                setDirectionChanged(true);
            } else if (to.rank() == Card.ACE && draggingCard.getRank() == Card.ACE) {
                v = true;
                setLocalDirection(1);
                setDirectionChanged(true);
            } else if (to.getDirection() == 0) {
                if (draggingCard.getRank() == to.rank() + 1) {
                    v = true;
                    setLocalDirection(1);
                    setDirectionChanged(true);
                }else if (draggingCard.getRank() == to.rank() - 1) {
                    v = true;
                    setLocalDirection(-1);
                    setDirectionChanged(true);
                }
            } else if (to.getDirection() < 0) {
                if (draggingCard.getRank() == to.rank() - 1) {
                    v = true;
                }
            } else if (to.getDirection() > 0) {
                if (draggingCard.getRank() == to.rank() + 1) {
                    v = true;
                    setLocalDirection(1);
                }
            }

        }

        return v;
    }
}
