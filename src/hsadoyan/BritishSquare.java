package hsadoyan;

import ks.common.controller.*;//SolitaireMouseMotionAdapter;
import ks.common.games.Solitaire;
import ks.common.games.SolitaireUndoAdapter;
import ks.common.model.Column;
import ks.common.model.MultiDeck;
import ks.common.model.Pile;
import ks.common.view.*;
import ks.launcher.Main;

import java.awt.*;

/**
 * Created by ftlc on 11/9/16.
 */

public class BritishSquare  extends Solitaire {

    MultiDeck deck;
    DeckView deckView;


    //Cards
    BritishColumn c1, c2, c3, c4;
    Pile wastePile;
    Pile f1, f2, f3, f4;


    public Dimension getPreferredSize() {
        return new Dimension(1024, 1024);
    }






    // Card Views
    ColumnView c1view, c2view, c3view, c4view;
    PileView f1view, f2view, f3view, f4view;
    PileView wastePileView;

    //Integer Views
    IntegerView scoreView;
    IntegerView numLeftView;

    int[] suitsUsed = {0 , 0, 0, 0};



    @Override
    public String getName() {
        return "British Square";
    }

    @Override
    public boolean hasWon() {
        return getScoreValue() == 104;
    }

    @Override
    public void initialize() {

        initializeModel(getSeed());
        initializeView();
        initializeControllers();

        for(int i = 0; i<4; i++) {
            c1.add(deck.get());
            c2.add(deck.get());
            c3.add(deck.get());
            c4.add(deck.get());
        }
        updateNumberCardsLeft(-16);


    }

    int[] getSuitsUsed() { return suitsUsed;}
    void setSuitsUsed(int[] s) {
        suitsUsed = s;
    }

    private void initializeControllers()
    {
        //Initialize controllers for DeckView
        deckView.setMouseAdapter(new BritishDeckController (this, deck, wastePile));
        deckView.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
        deckView.setUndoAdapter (new SolitaireUndoAdapter(this));


        // WastePile
        wastePileView.setMouseAdapter (new WastePileController (this, wastePileView));
        wastePileView.setMouseMotionAdapter (new SolitaireMouseMotionAdapter (this));
        wastePileView.setUndoAdapter (new SolitaireUndoAdapter(this));

        // Now for each Foundation.
        f1view.setMouseAdapter (new FoundationController (this, f1view));
        f1view.setMouseMotionAdapter (new SolitaireMouseMotionAdapter (this));
        f1view.setUndoAdapter (new SolitaireUndoAdapter(this));

        f2view.setMouseAdapter (new FoundationController (this, f2view));
        f2view.setMouseMotionAdapter (new SolitaireMouseMotionAdapter (this));
        f2view.setUndoAdapter (new SolitaireUndoAdapter(this));


        f3view.setMouseAdapter (new FoundationController (this, f3view));
        f3view.setMouseMotionAdapter (new SolitaireMouseMotionAdapter (this));
        f3view.setUndoAdapter (new SolitaireUndoAdapter(this));

        f4view.setMouseAdapter (new FoundationController (this, f4view));
        f4view.setMouseMotionAdapter (new SolitaireMouseMotionAdapter (this));
        f4view.setUndoAdapter (new SolitaireUndoAdapter(this));


        //Columns
        c1view.setMouseAdapter (new ColumnController (this, c1view));
        c1view.setMouseMotionAdapter (new SolitaireMouseMotionAdapter (this));
        c1view.setUndoAdapter (new SolitaireUndoAdapter(this));


        c2view.setMouseAdapter (new ColumnController (this, c2view));
        c2view.setMouseMotionAdapter (new SolitaireMouseMotionAdapter (this));
        c2view.setUndoAdapter (new SolitaireUndoAdapter(this));


        c3view.setMouseAdapter (new ColumnController (this, c3view));
        c3view.setMouseMotionAdapter (new SolitaireMouseMotionAdapter (this));
        c3view.setUndoAdapter (new SolitaireUndoAdapter(this));


        c4view.setMouseAdapter (new ColumnController (this, c4view));
        c4view.setMouseMotionAdapter (new SolitaireMouseMotionAdapter (this));
        c4view.setUndoAdapter (new SolitaireUndoAdapter(this));

        // Ensure that any releases (and movement) are handled by the non-interactive widgets
        numLeftView.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
        numLeftView.setMouseAdapter (new SolitaireReleasedAdapter(this));
        numLeftView.setUndoAdapter (new SolitaireUndoAdapter(this));

        // same for scoreView
        scoreView.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
        scoreView.setMouseAdapter (new SolitaireReleasedAdapter(this));
        scoreView.setUndoAdapter (new SolitaireUndoAdapter(this));

        // Finally, cover the Container for any events not handled by a widget:
        getContainer().setMouseMotionAdapter(new SolitaireMouseMotionAdapter(this));
        getContainer().setMouseAdapter (new SolitaireReleasedAdapter(this));
        getContainer().setUndoAdapter (new SolitaireUndoAdapter(this));



    }

    private void initializeView() {


        CardImages ci = getCardImages();
        deckView = new DeckView(deck);
        int h = ci.getHeight();
        int w = ci.getWidth();
        deckView.setBounds(20, 20, w, h);
        addViewWidget(deckView);

        f1view = new PileView(f1);
        f1view.setBounds(100+2*w, 20, w, h);
        addViewWidget(f1view);

        f2view = new PileView(f2);
        f2view.setBounds(120+3*w, 20, w, h);
        addViewWidget(f2view);

        f3view = new PileView(f3);
        f3view.setBounds(140+4*w, 20, w, h);
        addViewWidget(f3view);

        f4view = new PileView(f4);
        f4view.setBounds(160+5*w, 20, w, h);
        addViewWidget(f4view);



        c1view = new ColumnView(c1);
        c1view.setBounds(100+2*w, 60+h, w, 10*h);
        addViewWidget(c1view);

        c2view = new ColumnView(c2);
        c2view.setBounds(120+3*w, 60+h, w, 10*h);
        addViewWidget(c2view);

        c3view = new ColumnView(c3);
        c3view.setBounds(140+4*w, 60+h, w, 10*h);
        addViewWidget(c3view);

        c4view = new ColumnView(c4);
        c4view.setBounds(160+5*w, 60+h, w, 10*h);
        addViewWidget(c4view);


        wastePileView = new PileView(wastePile);
        wastePileView.setBounds(60+w, 20, w, h);
        addViewWidget(wastePileView);


        scoreView = new IntegerView(getScore());
        scoreView.setBounds(200 + 6*w, 20, 100, 60);
        addViewWidget(scoreView);

        numLeftView = new IntegerView(getNumLeft());
        numLeftView.setBounds(200 + 6*w, 100, 100, 60);
        addViewWidget(numLeftView);


        updateNumberCardsLeft(104);
        updateScore(0);
    }

    private void initializeModel(int seed) {
        deck = new MultiDeck(2);
        deck.create(seed);
        model.addElement(deck);

        c1 = new BritishColumn("c1");
        c2 = new BritishColumn("c2");
        c3 = new BritishColumn("c3");
        c4 = new BritishColumn("c4");

        model.addElement(c1);
        model.addElement(c2);
        model.addElement(c3);
        model.addElement(c4);


        f1 = new Pile("f1");
        f2 = new Pile("f2");
        f3 = new Pile("f3");
        f4 = new Pile("f4");

        model.addElement(f1);
        model.addElement(f2);
        model.addElement(f3);
        model.addElement(f4);

        wastePile = new Pile("waste");
        model.addElement(wastePile);


    }



    public static void main(String args[])
    {
        Main.generateWindow(new BritishSquare(), MultiDeck.OrderBySuit);
    }
}