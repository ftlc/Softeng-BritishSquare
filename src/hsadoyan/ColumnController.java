package hsadoyan;

import ks.common.model.*;
import ks.common.view.*;

import javax.swing.plaf.ComponentUI;
import java.awt.event.MouseEvent;

/**
 * Created by ftlc on 11/20/16.
 */
public class ColumnController extends java.awt.event.MouseAdapter {

    protected BritishSquare theGame;
    protected ColumnView src;

    public ColumnController(BritishSquare theGame, ColumnView cv)
    {
        super();

        this.theGame = theGame;
        this.src = cv;
    }

    public void mousePressed(MouseEvent me)
    {
        Container c = theGame.getContainer();

        BritishColumn col = (BritishColumn) src.getModelElement();
        if(col.count() == 0)
        {
            c.releaseDraggingObject();
            return;
        }

        CardView cardView = src.getCardViewForTopCard(me);
        if(cardView == null)
        {
            c.releaseDraggingObject();
            return;
        }

        Widget w = c.getActiveDraggingObject();
        if(w != Container.getNothingBeingDragged()){
            System.err.println ("BritishColumnController::mousePressed(): Unexpectedly encountered a Dragging Object during a Mouse press.");
            return;
        }
        c.setActiveDraggingObject(cardView, me);
        c.setDragSource(src);
        src.redraw();

    }

    public void mouseReleased(MouseEvent me)
    {
        Container c = theGame.getContainer();

        Widget w = c.getActiveDraggingObject();
        if(w == Container.getNothingBeingDragged()) {
            c.releaseDraggingObject();
            return;
        }

        Widget fromWidget = c.getDragSource();
        if(fromWidget == null) {
            System.err.println ("BritishColumnController::mouseReleased(): somehow no dragSource in container.");
            c.releaseDraggingObject();
            return;
        }

        BritishColumn toBritishColumn = (BritishColumn) src.getModelElement();
        if(fromWidget instanceof PileView)
        {
            CardView cardView = (CardView) w;
            Card theCard = (Card) cardView.getModelElement();
            if(theCard == null) {
                System.err.println ("BritishColumnController::mouseReleased(): somehow CardView model element is null.");
                return;
            }

            Pile wastePile = (Pile) fromWidget.getModelElement();
            Move m = new MoveWasteToColumnMove(wastePile, theCard, toBritishColumn, theGame);
            if(m.doMove(theGame)) {
                theGame.pushMove(m);

            } else {
                wastePile.add(theCard);
            }

        } else {
            CardView cardView = (CardView) w;
            Card theCard = (Card) cardView.getModelElement();
            if(theCard == null) {
                System.err.println ("BritishColumnController::mouseReleased(): somehow CardView model element is null.");
                return;
            }

            BritishColumn from = (BritishColumn) fromWidget.getModelElement();
            Move m = new MoveColumnToColumnMove(from, theCard, toBritishColumn, theGame);
            if(m.doMove(theGame)) {
                theGame.pushMove(m);

                if(from.empty())
                {
                    from.setDirection(0);
                }
                if(from.suit() != toBritishColumn.suit())
                {
                    from.setDirection(0);
                }
            } else {
                from.add(theCard);
            }



        }



        c.releaseDraggingObject();
        c.repaint();
    }
}
