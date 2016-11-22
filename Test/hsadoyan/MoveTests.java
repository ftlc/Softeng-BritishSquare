/**
 * Created by ftlc on 11/21/16.
 */

package hsadoyan;

import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.MultiDeck;
import ks.launcher.Main;
import ks.tests.KSTestCase;

import java.awt.event.MouseEvent;


public class MoveTests extends KSTestCase{


    BritishSquare game;

    // window for game.
    GameWindow gw;

    protected void setUp() {
        game = new BritishSquare();

        // Because solitaire variations are expected to run within a container, we need to
        // do this, even though the Container will never be made visible. Note that here
        // we select the "random seed" by which the deck will be shuffled. We use the
        // special constant Deck.OrderBySuit (-2) which orders the deck from Ace of clubs
        // right to King of spades.
        gw = Main.generateWindow(game, MultiDeck.OrderBySuit);

    }

    // clean up properly
    protected void tearDown() {
        gw.setVisible(false);
        gw.dispose();
    }

    public void testDealOne() {
        DealCardMove dfm = new DealCardMove(game.deck, game.wastePile);

        assertTrue (game.wastePile.empty());
        assertTrue (dfm.valid(game));
        assertTrue (dfm.doMove(game));
        assertEquals (new Card(Card.TEN, Card.HEARTS), game.wastePile.peek());
        assertTrue (dfm.undo(game));


        // fix things so they stay broke
        game.deck.removeAll();

        assertFalse (dfm.valid(game));
        assertFalse (dfm.doMove(game));

    }


    public void testDeckController() {

        // first create a mouse event
        MouseEvent pr = createPressed (game, game.deckView, 0, 0);
        game.deckView.getMouseManager().handleMouseEvent(pr);

        assertEquals (new Card(Card.TEN, Card.HEARTS), game.wastePile.peek());
        assertTrue (game.undoMove());
        assertTrue (game.wastePile.empty());

    }



    //Test Move Foundation
    public void testToFoundationMove() {

        int colHeight = game.getCardImages().getHeight();
        MouseEvent pr = createPressed(game, game.deckView, 0, 0);
        for (int i = 0; i < 10; i++) {
            game.deckView.getMouseManager().handleMouseEvent(pr);
        }
        MouseEvent prs = createPressed(game, game.wastePileView, 0, 0);

        game.wastePileView.getMouseManager().handleMouseEvent(prs);

        MouseEvent rls = createReleased(game, game.f1view, 0, 0);
        game.f1view.getMouseManager().handleMouseEvent(rls);

        assertEquals(1, game.f1.count());

        //undo
        assertTrue(game.undoMove());
        assertEquals(0, game.f1.count());


        for(int i = 0; i < 10; i++) {
            game.wastePileView.getMouseManager().handleMouseEvent(prs);
            game.f1view.getMouseManager().handleMouseEvent(rls);
        }

        MouseEvent fromCol = createPressed(game, game.c4view, 0, colHeight);
        game.c4view.getMouseManager().handleMouseEvent(fromCol);


        MouseEvent toCol = createReleased(game, game.c3view, 0 ,colHeight);
        game.c3view.getMouseManager().handleMouseEvent(toCol);

        assertEquals(5, game.c3.count());
        assertEquals(-1, game.c3.direction);

        MouseEvent colToF = createPressed(game, game.c3view, 0, colHeight);

        MouseEvent toF = createReleased(game, game.c3view, 0, colHeight);

        for(int i = 0; i < 2; i++) {
            game.c3view.getMouseManager().handleMouseEvent(colToF);
            game.f1view.getMouseManager().handleMouseEvent(toF);
        }

        assertEquals(12, game.f1.count());
        assertEquals(0, game.c3.direction);


        assertTrue(game.undoMove());


    }

    //Test Column to Foundation Move
    public void testColumnToFoundationMove(){
        int colHeight = game.getCardImages().getHeight();
        MouseEvent prs = createPressed(game, game.c1view, 0, colHeight);
        game.c1view.getMouseManager().handleMouseEvent(prs);

        MouseEvent rls = createReleased(game, game.f1view, 0, 0);
        game.f1view.getMouseManager().handleMouseEvent(rls);

        assertEquals(1, game.f1.count());

        assertTrue(game.undoMove());
        assertEquals(0, game.f1.count());
    }

    public void testColumnToColumnMove() {
        int colHeight = game.getCardImages().getHeight();
        MouseEvent prs = createPressed(game, game.c4view, 0, colHeight);
        game.c4view.getMouseManager().handleMouseEvent(prs);

        MouseEvent rls = createReleased(game, game.c3view, 0, colHeight);
        game.c3view.getMouseManager().handleMouseEvent(rls);


        assertEquals(5, game.c3.count());
        assertEquals(3, game.c4.count());

        assertTrue(game.undoMove());
        assertEquals(4, game.c3.count());
        assertEquals(4, game.c4.count());

        //Test change direction

        MouseEvent take2 = createPressed(game, game.c2view, 0, colHeight);
        game.c2view.getMouseManager().handleMouseEvent(take2);

        MouseEvent rls2 = createReleased(game, game.c3view, 0, colHeight);
        game.c3view.getMouseManager().handleMouseEvent(rls2);


        assertEquals(5, game.c3.count());
        assertEquals(3, game.c2.count());

        assertTrue(game.undoMove());
        assertEquals(4, game.c3.count());
        assertEquals(4, game.c2.count());

    }

    public void testWasteToColumnMove() {

        int colHeight = game.getCardImages().getHeight();

        //Deal one card
        MouseEvent pr = createPressed(game, game.deckView, 0, 0);
        game.deckView.getMouseManager().handleMouseEvent(pr);


        //Pick up card
        MouseEvent prs = createPressed(game, game.wastePileView, 0, 0);
        game.wastePileView.getMouseManager().handleMouseEvent(prs);

        //Place on c4
        MouseEvent rls = createReleased(game, game.c4view, 0, colHeight);
        game.c4view.getMouseManager().handleMouseEvent(rls);


        assertEquals(5, game.c4.count());

        //undo
        assertTrue(game.undoMove());
        assertEquals(4, game.c4.count());

    }

    public void testColumnToColumnFail() {



        BritishColumn fromCol = game.c4;
        Card draggingCard = game.c4.peek();
        BritishColumn toCol = game.c1;

        MoveColumnToColumnMove newMove = new MoveColumnToColumnMove(fromCol, draggingCard, toCol, game);
        assertFalse(newMove.doMove(game));

    }

    public void testKingToKing() {
        BritishColumn fromCol = new BritishColumn("c5");

        Card draggingCard = new Card(Card.KING, Card.HEARTS);
        fromCol.add(draggingCard);

        BritishColumn toCol = game.c2;

        MoveColumnToColumnMove newMove = new MoveColumnToColumnMove(fromCol, draggingCard, toCol, game);
        assertTrue(newMove.doMove(game));
        assertEquals(5, game.c2.count());

    }


    public void testAceToAce() {
        BritishColumn fromCol = new BritishColumn("c5");

        Card draggingCard = new Card(Card.ACE, Card.SPADES);
        fromCol.add(draggingCard);

        BritishColumn toCol = game.c1;

        MoveColumnToColumnMove newMove = new MoveColumnToColumnMove(fromCol, draggingCard, toCol, game);
        assertTrue(newMove.doMove(game));
        assertEquals(5, game.c1.count());
    }

    public void testLessThan() {

        BritishColumn fromCol = game.c3;

        Card draggingCard = game.c3.peek();
        BritishColumn toCol = game.c2;

        MoveColumnToColumnMove newMove = new MoveColumnToColumnMove(fromCol, draggingCard, toCol, game);
        assertTrue(newMove.doMove(game));
        assertEquals(5, game.c2.count());



        BritishColumn fromCol2 = game.c4;
        Card draggingCard2 = game.c4.peek();

        MoveColumnToColumnMove newMove2 = new MoveColumnToColumnMove(fromCol2, draggingCard2, toCol, game);
        assertTrue(newMove2.doMove(game));
        assertEquals(6, game.c2.count());

    }

        public void testGreaterThan() {

        BritishColumn fromCol = game.c3;

        Card draggingCard = game.c3.peek();
        BritishColumn toCol = game.c4;

        MoveColumnToColumnMove newMove = new MoveColumnToColumnMove(fromCol, draggingCard, toCol, game);
        assertTrue(newMove.doMove(game));
        assertEquals(5, game.c4.count());



        BritishColumn fromCol2 = game.c2;
        Card draggingCard2 = game.c2.peek();

        MoveColumnToColumnMove newMove2 = new MoveColumnToColumnMove(fromCol2, draggingCard2, toCol, game);
        assertTrue(newMove2.doMove(game));
        assertEquals(6, game.c4.count());

    }


    public void testColumnWraparound() {

        int colHeight = game.getCardImages().getHeight();



        MouseEvent prs = createPressed(game, game.c3view, 0, colHeight);
        game.c3view.getMouseManager().handleMouseEvent(prs);

        MouseEvent rls = createReleased(game, game.c4view, 0, colHeight);
        game.c4view.getMouseManager().handleMouseEvent(rls);


        MouseEvent prs2 = createPressed(game, game.c2view, 0, colHeight);
        game.c2view.getMouseManager().handleMouseEvent(prs2);


        game.c4view.getMouseManager().handleMouseEvent(rls);

        assertEquals(6, game.c4.count());

        MouseEvent deckprs = createPressed(game, game.deckView, 0, 0);
        for(int i = 0; i < 50; i++)
        {
            game.deckView.getMouseManager().handleMouseEvent(deckprs);
        }

        System.out.println(game.wastePile.peek());

        MouseEvent wastePress = createPressed(game, game.wastePileView, 0, 0);

        for(int i = 0; i < 2; i++) {
            game.wastePileView.getMouseManager().handleMouseEvent(wastePress);
            game.c4view.getMouseManager().handleMouseEvent(rls);
            game.deckView.getMouseManager().handleMouseEvent(deckprs);
        }

        assertEquals(8, game.c4.count());


    }






}
