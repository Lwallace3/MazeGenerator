import javax.sound.midi.SysexMessage;
import javax.swing.*;
import java.awt.*;
import java.lang.constant.Constable;
import java.util.Stack;

public class Maze extends JPanel {
    public int side_size;
    public Cell[][] grid;
    public int w;
    public Cell current_cell;
    Stack<Cell> stack = new Stack<>();

    public Maze(int num_of_cells) {
        this.side_size = (int) Math.sqrt(num_of_cells);
        this.w = 1000 / side_size;
        this.grid = new Cell[side_size][side_size];
        initialiseGrid();

        this.current_cell = grid[0][0];
    }

    public void initialiseGrid(){
        for (int i = 0; i < side_size; i++) {
            for (int j = 0; j < side_size; j++) {
                grid[i][j] = new Cell(i, j, w);
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

    private void removeWalls(Cell current_cell, Cell next) {
        int i = current_cell.x - next.x;
        int j = current_cell.y - next.y;

        if (i == 1) {
            current_cell.walls[3] = false;
            next.walls[1] = false;
        }else if (i == -1) {
            current_cell.walls[1] = false;
            next.walls[3] = false;
        }

        if (j == 1) {
            current_cell.walls[0] = false;
            next.walls[2] = false;
        }else if (j == -1) {
            current_cell.walls[2] = false;
            next.walls[0] = false;
        }
    }

}
