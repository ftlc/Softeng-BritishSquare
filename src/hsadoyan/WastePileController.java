package hsadoyan;

import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.games.Solitaire;
import ks.common.model.Pile;
import ks.common.view.CardView;
import ks.common.view.Container;
import ks.common.view.PileView;
import ks.common.view.Widget;

import java.awt.event.MouseEvent;

/**
 * Created by ftlc on 11/18/16.
 */
public class WastePileController extends SolitaireReleasedAdapter{

    protected BritishSquare theGame;
    protected PileView src;
    /**
     * SolitaireReleasedAdapter constructor comment.
     *
     * @param theGame game under play.
     */
    public WastePileController(BritishSquare theGame, PileView waste) {
        super(theGame);

        this.theGame = theGame;
        this.src = waste;
    }



    public void mousePressed(MouseEvent me)
    {
        Container c = theGame.getContainer();

        Pile wastePile = (Pile) src.getModelElement();

        if(wastePile.count() == 0){
            c.releaseDraggingObject();
            return;
        }

        CardView cardView = src.getCardViewForTopCard(me);

        if(cardView == null){

            c.releaseDraggingObject();
            return;
        }

        Widget w = c.getActiveDraggingObject();

        if(w != Container.getNothingBeingDragged()) {
            System.err.println ("WastePileController::mousePressed(): Unexpectedly encountered a Dragging Object during a Mouse press.");
            return;
        }

		c.setActiveDraggingObject (cardView, me);

		c.setDragSource (src);

		src.redraw();
    }
}
