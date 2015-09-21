import junit.framework.Assert;
import main.Attribute;
import main.Board;
import main.Orb;
import main.Position;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by bleeben on 9/13/2015.
 */
public class BoardTest {

    Board board;
    Orb orb;

    @Before
    public void setUp() throws Exception {
        this.board = new Board(5,6);
        this.orb = new Orb(Attribute.JAMMER);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetRows() throws Exception {
        Assert.assertEquals(this.board.getRows(),5);
    }

    @Test
    public void testGetColumns() throws Exception {
        Assert.assertEquals(this.board.getColumns(),6);
    }

    @Test
    public void testGetOrb() throws Exception {
        board.placeOrb(orb, new Position(2, 3));
        Assert.assertEquals(board.getOrb(2, 3), orb);
    }

    @Test
    public void testRefreshOrbs() throws Exception {
        this.board.refreshOrbs();
        //this.board.printBoard();
    }

    @Test
    public void testSetupBoard() throws Exception {

    }

    @Test
    public void testDropDownClearedOrbs() throws Exception {
        // TODO this method isnt implemented correctly
        this.board.refreshOrbs();
        board.placeOrb(new Orb(Attribute.CLEARED), new Position(4, 4));
        board.placeOrb(new Orb(Attribute.CLEARED), new Position(2, 4));
        board.placeOrb(new Orb(Attribute.CLEARED), new Position(2, 2));
        this.board.printBoard();
        this.board.dropDownClearedOrbs();
        this.board.printBoard();
    }

    @Test
    public void testRefreshNewDropdownOrbs() throws Exception {

    }

    @Test
    public void testGenerateMatches() throws Exception {
        //this.board.refreshOrbs();
        board.placeOrb(new Orb(Attribute.JAMMER), new Position(3, 3));
        board.placeOrb(new Orb(Attribute.JAMMER), new Position(2, 3));
        board.placeOrb(new Orb(Attribute.JAMMER), new Position(1, 3));
        //this.board.printBoard();
        Map<Position,Set<Position>> matches = this.board.generateMatches();
        /*for (Position startingPos:matches.keySet()){
            //System.out.println(startingPos);
            for (Position matchPos:matches.get(startingPos)) {
                System.out.print(matchPos);
            }
            System.out.println();
        }*/
    }
}