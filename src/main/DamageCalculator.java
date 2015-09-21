package main;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by bleeben on 9/20/2015.
 */

/*
    Design plan: Solvers will work via listeners, subscribe to a game, receive a board, and receive requests for solutions.

    GameManager will call Board.addObserver(Solver), Board extends Observable, Solver implements Observer, Solver uses new data to come up with
    solutions

    Maybe there should also be an eventlistener? should solver notify when it has a solution? i can worry about this later

    Solutions would be in ArrayList<Position> signifying the path to take?
 */

public class DamageCalculator implements Observer{

    @Override
    public void update(Observable o, Object arg) {
        calculate((Board) o);
    }

    protected void calculate(Board board) {

    }
}
