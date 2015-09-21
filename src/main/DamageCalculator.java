package main;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by bleeben on 9/20/2015.
 * Calculates damages blah
 */

public class DamageCalculator implements Observer{

    @Override
    public void update(Observable o, Object arg) {
        calculate((Board) o);
    }

    protected void calculate(Board board) {

    }
}
