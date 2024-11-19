public class Disc {
    private char symbol;
    // position of the disc in the grid
    private int xcoord; // column Number
    private int ycoord; // Row Number

    public Disc(char symbol, int xcoord, int ycoord){
        this.symbol = symbol;
        this.xcoord = xcoord;
        this.ycoord = ycoord;
    }

    public char getSymbol(){
        return this.symbol;
    }

    public int getXcoord(){
        return this.xcoord;
    }

    public int getYcoord(){
        return this.ycoord;
    }

    public void updateSymbol(char newSym) {
        this.symbol = newSym;
    }

    public String toString() {
        return this.symbol + "";
    }
}
