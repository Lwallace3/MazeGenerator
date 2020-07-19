import javax.sound.midi.SysexMessage;
import javax.swing.*;
import java.awt.*;
import java.lang.constant.Constable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class Maze extends JPanel {
    public int side_size;
    public Cell[][] grid;
    public ArrayList<Wall> walls = new ArrayList<>();
    public int w;
    public Cell current_cell;
    Stack<Cell> stack = new Stack<>();

    public Maze(int num_of_cells) {
        this.side_size = (int) Math.sqrt(num_of_cells);
        this.w = 1000 / side_size;
        this.grid = new Cell[side_size][side_size];
        initialiseGrid();
        initialiseWalls();
        this.current_cell = grid[0][0];
    }

    public void initialiseGrid(){
        for (int i = 0; i < side_size; i++) {
            for (int j = 0; j < side_size; j++) {
                grid[i][j] = new Cell(i, j, w);
            }
        }
    }

    public void initialiseWalls(){
        for (int i = 0; i < side_size; i++) {
            for (int j = 0; j < side_size; j++) {
                if(i < side_size - 1){
                    Wall wall = new Wall(grid[i][j],grid[i+1][j]);
                    walls.add(wall);
                    grid[i][j].walls.add(wall);
                    grid[i+1][j].walls.add(wall);
                }
                if(j < side_size - 1){
                    Wall wall = new Wall(grid[i][j],grid[i][j+1]);
                    walls.add(wall);
                    grid[i][j].walls.add(wall);
                    grid[i][j+1].walls.add(wall);
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        for (int i = 0; i < grid[0].length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j].draw(g);
            }
        }

        backtrack(g);
    }

    public void backtrack(Graphics g){
        current_cell.visited = true;
        current_cell.mark(g);
        Cell next = current_cell.getNeighbor(grid);
        System.out.println(stack.size());
        if(next != null){
            next.visited = true;
            stack.push(current_cell);

            removeWalls(current_cell, next);
            current_cell = next;
            // uncomment to slow down animation
//            try {
//                Thread.sleep(50);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            repaint();

        } else if(stack.size() > 0) {
            current_cell = stack.pop();
            repaint();
        }
    }

    public void kruskals(Graphics g){
        ArrayList<Cell> joined_cells = new ArrayList<>();
        while(joined_cells.size() < side_size*side_size-1) {
            int rand_x = ThreadLocalRandom.current().nextInt(0, side_size);
            int rand_y = ThreadLocalRandom.current().nextInt(0, side_size);
            Cell cell = grid[rand_x][rand_y];
            Cell next = cell.getNeighbor(grid);
            if(!joined_cells.contains(next)){
                cell.mark(g);
                removeWalls(cell, next);
                joined_cells.add(cell);
                joined_cells.add(next);
                repaint();
            }

        }
    }

    private void removeWalls(Cell current_cell, Cell next) {
        for(int i = 0; i < current_cell.walls.size(); i++){
            Wall current_wall = current_cell.walls.get(i);
            if (current_wall.side_a.equals(current_cell) && current_wall.side_b.equals(next)){
                current_cell.walls.remove(i);
            } else if (current_wall.side_a.equals(next) && current_wall.side_b.equals(current_cell)){
                current_cell.walls.remove(i);
            }
        }

        for(int i = 0; i < next.walls.size(); i++){
            Wall current_wall = next.walls.get(i);
            if (current_wall.side_a.equals(current_cell) && current_wall.side_b.equals(next)){
                next.walls.remove(i);
            } else if (current_wall.side_a.equals(next) && current_wall.side_b.equals(current_cell)){
                next.walls.remove(i);
            }
        }
    }

}
