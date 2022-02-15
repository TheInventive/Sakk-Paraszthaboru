package hu.chess.engine.board;

public class SourceDestination {
    private int source;
    private int destination;
    private boolean sourceSet;
    private boolean destinationSet;

    public SourceDestination(int source, int destination) {
        this.source = source;
        this.destination = destination;
    }

    public boolean checkIfBothSet(){
        return sourceSet && destinationSet;
    }

    public int getSource() {
        return source;
    }

    public int getDestination() {
        return destination;
    }

    public void setSource(int source) {
        this.source = source;
        if(this.sourceSet)
            this.destinationSet = false;
        this.sourceSet = true;
    }

    public void setDestination(int destination) {
        this.destination = destination;
        if(this.destinationSet)
            this.sourceSet = false;
        this.destinationSet = true;
    }

}
