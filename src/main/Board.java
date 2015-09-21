package main;

import java.util.*;

/**
 * Created by bleeben on 9/12/2015.
 */
public class Board extends Observable{

    private int rows;
    private int cols;
    private Orb[][] board;
    public static final int MATCH=3;
    private HashMap<Orb,Position> orbPositions;

    public Board(int rows, int cols) {
        this(rows, cols, Attribute.getDefaultAttributes());
    }

    public Board(int rows, int cols, Set<Attribute> availableAttrs) {
        this.rows = rows;
        this.cols = cols;
        this.availableAttrs = availableAttrs;
        setupBoard();
    }

    public Set<Attribute> getAvailableAttrs() {
        return availableAttrs;
    }

    public void setAvailableAttrs(HashSet<Attribute> availableAttrs) {
        this.availableAttrs = availableAttrs;
    }

    private Set<Attribute> availableAttrs;

    protected void setRows(int rows) {
        this.rows = rows;
    }

    protected void setColumns(int cols) {
        this.cols = cols;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return cols;
    }

    public Orb getOrb(int row, int col) {
        return this.board[row][col];
    }

    public Orb getOrb(Position pos) {
        if (isValidPosition(pos))
            return getOrb(pos.getRow(), pos.getColumn());
        return null;
    }

    public Position getOrbPosition(Orb orb){
        if (orbPositions.containsKey(orb))
            return orbPositions.get(orb);
        return null;
    }

    public void refreshOrbs() {
        refreshOrbs(this.getAvailableAttrs());
    }

    /*
        Initialization for the board, with newly created main.Orb slots. Meant to be called once, during Constructor
     */
    protected void setupBoard() {
        orbPositions = new HashMap<>();
        board = new Orb[getRows()][getColumns()];
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                placeOrb(new Orb(),new Position(i,j));
                //board[i][j] = new Orb();
                //updateOrbPosition(board[i][j], new Position(i, j));
            }
        }
        setChanged();
    }

    /*
        Helper method to pick a random attribute out of the input. TODO might be better off in Attribute
     */
    private Attribute generateRandomAttribute(Set<Attribute> availableAttrs) {
        if (availableAttrs == null || availableAttrs.isEmpty())
            return null;
        if (availableAttrs.size() == 1) {
            return availableAttrs.toArray(Attribute.values())[0];
        } else {
            while (true) {
                Attribute candidate = Attribute.randomAttribute();
                if (availableAttrs.contains(candidate))
                    return candidate;
            }
        }
    }

    /*
        Refreshes the current board randomly according to the valid attributes in availableAttrs
    */
    public void refreshOrbs(Set<Attribute> availableAttrs) {
        for (Orb[] boardRow : this.board) {
            for (Orb orb : boardRow) {
                orb.clearOrb();
                orb.setAttribute(generateRandomAttribute(availableAttrs));
            }
        }
        setChanged();
    }

    /*
        Takes the current board and clears any Orbs that have attribute CLEARED from the bottom up.
        Shifts CLEARED orbs to the top. Essentially rotates each board column downwards to start on a non-CLEARED orb.
     */
    public void dropDownClearedOrbs() {
        for (int col = 0; col < getColumns(); col++) {
            int dropDownDepth = 0;
            for (int row = getRows() - 1; row >= 0; row--) {
                if (getOrb(row,col).isCleared()) {
                    dropDownDepth+=1;
                } else if (dropDownDepth>0) {
                    placeOrb(getOrb(row, col), new Position(row + dropDownDepth, col));
                    //updateOrbPosition(board[row][col], new Position(row + dropDownDepth, col));
                    //board[row + dropDownDepth][col] = board[row][col];
                    //board[row][col] = new Orb();
                    //updateOrbPosition(board[row][col],new Position(row,col));
                }
            }
        }
        setChanged();
    }

    /*
        Places orb into Position pos, removing orb from any previous position
     */
    public void placeOrb(Orb orb,Position pos){
        if (getOrbPosition(orb)!=null){
            Position previousOrbPos = getOrbPosition(orb);
            if (isValidPosition(previousOrbPos)){
                board[previousOrbPos.getRow()][previousOrbPos.getColumn()] = new Orb();
                updateOrbPosition(board[previousOrbPos.getRow()][previousOrbPos.getColumn()],previousOrbPos);
            }
        }
        this.board[pos.getRow()][pos.getColumn()]=orb;
        updateOrbPosition(orb, pos);
    }

    private void updateOrbPosition(Orb orb, Position pos){
        if (orbPositions.putIfAbsent(orb, pos)!=null){
            orbPositions.replace(orb,pos);
        }
    }

    /*
        Creates randomly generated Orbs to populate the board from the top and downwards
        once dropdowns have occurred and cleared.
     */
    public void refreshNewDropdownOrbs() {
        for (int col = 0; col < getColumns(); col++) {
            for (int row = 0; row < getRows(); row++) {
                if (!getOrb(row, col).isCleared())
                    break;
                getOrb(row, col).clearOrb();
                getOrb(row, col).setAttribute(generateRandomAttribute(getAvailableAttrs()));
            }
        }
        setChanged();
    }

    private boolean comparePositionAttributes(Position pos1,Position pos2){
        return getOrb(pos1).getAttribute().equals(getOrb(pos2).getAttribute());
    }

    /*
        Helper method to determine if a position is within row and column bounds for the board
     */
    private boolean isValidPosition(Position pos) {
        if (pos.getRow()>=0 && pos.getRow()<getRows())
            if (pos.getColumn()>=0 && pos.getColumn()<getColumns())
                return true;
        return false;
    }

    /*
        Partitions the board into disjoint floodfilled sets based on Attribute representing match3's. Non-match orbs
        are not included.
    */
    public Map<Position,Set<Position>> generateMatches() {
        Set<Position> visitedPositions = new HashSet<>();
        Map<Position,Set<Position>> starterPositionToMatches = new HashMap<>();
        Set<Position> matchOrbs = generateMatchOrbs();
        for (Position pos:matchOrbs) {
            if (visitedPositions.contains(pos) || !matchOrbs.contains(pos))
                continue;
            //visitedPositions.add(new Position(row,col));
            Set<Position> posFill = new HashSet<>();
            //Attribute posAttribute = getOrb(pos).getAttribute();
            Queue<Position> fillQueue = new ArrayDeque<>();
            fillQueue.add(pos);
            while (!fillQueue.isEmpty()) {
                Position floodPosition = fillQueue.poll();
                if (visitedPositions.contains(floodPosition) || posFill.contains(floodPosition) || !matchOrbs.contains(pos))
                    continue;
                posFill.add(floodPosition);
                visitedPositions.add(floodPosition);
                if (isValidPosition(floodPosition.up()) && comparePositionAttributes(floodPosition, floodPosition.up()) && !visitedPositions.contains(floodPosition.up()) && matchOrbs.contains(floodPosition.up())) {
                    // up is same attribute and has not been visited yet
                    fillQueue.add(floodPosition.up());
                }
                if (isValidPosition(floodPosition.down()) && comparePositionAttributes(floodPosition, floodPosition.down()) && !visitedPositions.contains(floodPosition.down()) && matchOrbs.contains(floodPosition.down())) {
                    // up is same attribute and has not been visited yet
                    fillQueue.add(floodPosition.down());
                }
                if (isValidPosition(floodPosition.left()) && comparePositionAttributes(floodPosition, floodPosition.left()) && !visitedPositions.contains(floodPosition.left()) && matchOrbs.contains(floodPosition.left())) {
                    // up is same attribute and has not been visited yet
                    fillQueue.add(floodPosition.left());
                }
                if (isValidPosition(floodPosition.right()) && comparePositionAttributes(floodPosition, floodPosition.right()) && !visitedPositions.contains(floodPosition.right()) && matchOrbs.contains(floodPosition.right())) {
                    // up is same attribute and has not been visited yet
                    fillQueue.add(floodPosition.right());
                }
            }
            starterPositionToMatches.put(pos, posFill);
        }
        return starterPositionToMatches;
    }

    /*
        Returns a set of every Position that is part of a match.
     */
    private HashSet<Position> generateMatchOrbs() {
        HashMap<Attribute, Integer> matchWindow = new HashMap<>();
        HashSet<Position> matchOrbs = new HashSet<>();
        for (int row = 0; row < getRows(); row++) {
            matchWindow.clear();
            for (int col = 0; col < getColumns(); col++) {
                if (col - Board.MATCH >= 0) {
                    Attribute eldestAttribute = getOrb(row, col - Board.MATCH).getAttribute();
                    if (!matchWindow.remove(eldestAttribute, 1)) {
                        matchWindow.replace(eldestAttribute, matchWindow.get(eldestAttribute) - 1);
                    }
                }
                Integer existingValue;
                if ((existingValue = matchWindow.putIfAbsent(getOrb(row, col).getAttribute(), 1)) != null) {
                    matchWindow.replace(getOrb(row, col).getAttribute(), 1 + existingValue);
                }
                // Now test for a 3 match, there should only be size==1 in this map
                if (matchWindow.size() == 1 && !matchWindow.containsKey(Attribute.CLEARED)) {
                    Attribute matchedAttribute = matchWindow.keySet().toArray(new Attribute[1])[0];
                    if (matchWindow.get(matchedAttribute)<Board.MATCH)
                        continue;
                    // found a match
                    for (int i = 0; i < 3; i++) {
                        Position pos = new Position(row, col - i);
                        matchOrbs.add(pos);
                    }
                }
            }
        }
        for (int col = 0; col < getColumns(); col++) {
            matchWindow.clear();
            for (int row = 0; row < getRows(); row++) {
                if (row - Board.MATCH >= 0) {
                    Attribute eldestAttribute = getOrb(row - Board.MATCH, col).getAttribute();
                    if (!matchWindow.remove(eldestAttribute, 1)) {
                        matchWindow.replace(eldestAttribute, matchWindow.get(eldestAttribute) - 1);
                    }
                }
                Integer existingValue;
                if ((existingValue = matchWindow.putIfAbsent(getOrb(row, col).getAttribute(), 1)) != null) {
                    matchWindow.replace(getOrb(row, col).getAttribute(), 1 + existingValue);
                }
                // Now test for a 3 match, there should only be size==1 in this map
                if (matchWindow.size() == 1 && !matchWindow.containsKey(Attribute.CLEARED)) {
                    Attribute matchedAttribute = matchWindow.keySet().toArray(new Attribute[1])[0];
                    if (matchWindow.get(matchedAttribute)<Board.MATCH)
                        continue;
                    // found a match
                    for (int i = 0; i < 3; i++) {
                        Position pos = new Position(row - i, col);
                        matchOrbs.add(pos);
                    }
                }
            }
        }
        /*System.out.println();
        for (Position matchOrb:matchOrbs) {
            System.out.print(matchOrb);
        }
        System.out.println();*/
        return matchOrbs;
    }

    public void clearMatches(){
        Set<Position> matches = generateMatchOrbs();
        clearPositions(matches);
    }

    public void clearPositions(Set<Position> clearSet) {
        //TODO

        setChanged();
    }

    public void printBoard(){
        System.out.println(toString());
    }

    @Override
    public String toString() {
        String result="Board{}:";
        for (Orb[] boardRow : this.board) {
            result += "\n";
            for (Orb orb : boardRow) {
                result += orb;
            }
        }
        return result;
    }
}
