package hsadoyan;

import ks.common.model.Column;

/**
 * Created by ftlc on 11/21/16.
 */
public class BritishColumn extends Column{

    int direction;
    int oldDirection;
    public BritishColumn()
    {
        super();
        setDirection(0);
        setOldDirection(0);
    }

    public BritishColumn(String name)
    {
        super(name);
        setDirection(0);
    }
    int getDirection(){ return direction;}
    void setDirection(int d) { direction = d;}



    int getOldDirection(){ return oldDirection;}
    void setOldDirection(int d) { oldDirection = d;}
}
