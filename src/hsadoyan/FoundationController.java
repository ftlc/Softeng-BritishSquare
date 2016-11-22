package hsadoyan;

import heineman.klondike.MoveCardToFoundationMove;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Pile;
import ks.common.view.*;

import java.awt.event.MouseEvent;

/**
 * Created by ftlc on 11/19/16.
 */
public class FoundationController extends java.awt.event.MouseAdapter {

    protected BritishSquare theGame;
    protected PileView src;

    public FoundationController(BritishSquare theGame, PileView foundation) {
        super();

        this.theGame = theGame;
        this.src = foundation;
    }

    public void mouseReleased(MouseEvent me)
    {
        Container c = theGame.getContainer();



        Widget draggingWidget = c.getActiveDraggingObject();
        if(draggingWidget == Container.getNothingBeingDragged()) {
            System.err.println("FoundationController::mouseReleased() unexpectedly found nothing being dragged.");
            c.releaseDraggingObject();
            return;
        }

        /** Recover the from BuildablePile OR waste Pile */
        Widget fromWidget = c.getDragSource();
        if (fromWidget == null) {
            System.err.println ("FoundationController::mouseReleased(): somehow no dragSource in container.");
            c.releaseDraggingObject();
            return;
        }


        Pile foundation = (Pile) src.getModelElement();

        if(fromWidget instanceof PileView) {


            Pile wastePile = (Pile) fromWidget.getModelElement();

            CardView cardView = (CardView) draggingWidget;
            Card theCard = (Card) cardView.getModelElement();

            if (theCard == null) {
                System.err.println("FoundationController::mouseReleased(): somehow CardView model element is null.");
                c.releaseDraggingObject();
                return;
            }


            Move m = new MoveWasteToFoundationMove(wastePile, theCard, foundation, theGame);
            if (m.doMove(theGame)) {
                theGame.pushMove(m);
            } else {
                fromWidget.returnWidget(draggingWidget);
            }
        }
        else
        {
            BritishColumn col = (BritishColumn) fromWidget.getModelElement();

            CardView cardView = (CardView) draggingWidget;
            Card theCard = (Card) cardView.getModelElement();

            if(theCard == null)
            {
                System.err.println("FoundationController::mouseReleased(): somehow CardView model element is null.");
                c.releaseDraggingObject();
                return;
            }

            Move m = new MoveColumnToFoundationMove(col, theCard, foundation, theGame);

            if(m.doMove(theGame)) {
                theGame.pushMove(m);

                if(col.empty())
                {
                    col.setDirection(0);
                } else
                if(col.suit() != foundation.suit())
                {
                    col.setDirection(0);
                }
            }
            else
            {
                fromWidget.returnWidget(draggingWidget);
            }
        }


        c.releaseDraggingObject();

        c.repaint();
    }
}
