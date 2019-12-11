import java.util.ArrayList;

public class Solutions{
    private static volatile ArrayList<ArrayList<Block>> allBlockNeighbors = new ArrayList<>();
    private boolean done = false;
    int count = 0;
    int blocksLeft = 0;

    public Solutions() {
    }

    public ArrayList<Grid> getNextGrids(Grid g) throws CloneNotSupportedException {
        ArrayList<Grid> grids = new ArrayList<>();

        for (int i = 0; i < 25; i++) {
            Grid clone = (Grid) g.clone();
            Block temp = (Block) clone.getBlocks().getList()[i].clone();
            ArrayList<Block> tempNeighbors = clone.getBlockNeighbors(temp, new ArrayList<>());

            if (tempNeighbors.size() >= 20){
                done = true;
            } else if (tempNeighbors.size() > 1 && tempNeighbors.get(0).getColor() != 3) {

                Move move = new Move(tempNeighbors.get(0).getColor(),tempNeighbors.size(),count);

                clone.removeBlocks(tempNeighbors);
                move.setScore(clone.getScore());
                System.out.println(move.toString());


                grids.add((Grid) clone.clone());
            }
        }

        return grids;
    }

    public void solve(ArrayList<Grid> grids) throws CloneNotSupportedException {
        if (blocksLeft == 24){
            done = true;
        }
        while (count !=100){
            for (Grid g : grids){
                int blocks = 0;
                count++;
                for (Block block : g.getBlocks().getList()){
                    if (block.getColor() != 3){
                        ++blocks;
                    }
                }

                if (blocks <=6 ){
                    blocksLeft++;
                }

                System.out.println("Solving grid: " + count + " Score: " + g.getScore() + "   blocks left: " + blocks);
                solve(getNextGrids(g));
            }
        }
    }
}

class Move{
    String color;
    int size;
    int count;
    int score;

    public Move(int c, int size, int count) {
        if (c == 0){
            color = "red";
        } else if(c == 1){
            color = "blue";
        } else if(c == 2){
            color = "green";
        }

        this.size = size;

        this.count = count;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Move " + count + ": group of " + size + " " + color + " blocks; Current score: " + score;
    }
}

/*public class Solutions extends RecursiveAction implements Comparable {
    private Grid grid;
    private static volatile ArrayList<ArrayList<Block>> allBlockNeighbors = new ArrayList<>();
    private Solutions parent;
    private boolean done = false;
    private static volatile Solutions solution;

    public Solutions(Grid grid, Solutions parent) {
        this.grid = grid;
        this.parent = parent;
    }

    public ArrayList<Solutions> getNextGrids(){
        this.parent = this;
        ArrayList<Solutions> grids = new ArrayList<>();

        for (int i = 0; i < 25; i++) {
            Block temp = this.grid.getBlocks().getList()[i];
            ArrayList<Block> tempNeighbors = this.grid.getBlockNeighbors(temp, new ArrayList<>());

            grid.getBlocks().removeBlocks(tempNeighbors);

            grids.add(this);
        }

        return grids;
    }

    @Override
    protected void compute() {
        if (!done && solution == null) {

            ArrayList<Solutions> subtasks = new ArrayList<>(getNextGrids());

            for (Solutions g : subtasks) {
                System.out.println(g.toString());
                g.fork();
            }

        }




    }

    @Override
    public String toString() {
        for (Block b : grid.getBlocks().getList()){
            return b.getColor() + " ";
        }
        return "";
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}*/
