package hsadoyan;

import heineman.klondike.DealCardMove;
import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.games.Solitaire;
import ks.common.model.Deck;
import ks.common.model.Move;
import ks.common.model.Pile;



/**
 * Created by ftlc on 11/18/16.
 */
public class BritishDeckController extends SolitaireReleasedAdapter{

    /**
     * SolitaireReleasedAdapter constructor comment.
     *
     * @param theGame game under play.
     */

   protected BritishSquare theGame;
   protected Pile wastepile;
   protected Deck deck;

    public BritishDeckController(BritishSquare theGame, Deck deck, Pile wastePile) {
        super(theGame);
        this.theGame = theGame;
        this.wastepile = wastePile;
        this.deck = deck;
    }

    public void mousePressed (java.awt.event.MouseEvent me) {
        Move m = new DealCardMove(deck, wastepile);
        if(m.doMove(theGame)){
            theGame.pushMove(m);
            theGame.refreshWidgets();
        }
    }


}
