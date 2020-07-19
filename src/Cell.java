import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.*;

public class Cell extends JPanel {
    public int x;
    public int y;
    public int width;
    public boolean visited;
    public ArrayList<Wall> walls = new ArrayList<>();

    public Cell(int x, int y, int width) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.visited = false;
    }

    public void mark(Graphics g){
        int x1 = x * width;
        int y1 = y * width;
        g.setColor(Color.GREEN);
        g.fillRect(x1, y1, width, width);
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(4.0F));
        int x1 = x * width;
        int y1 = y * width;

        if (walls.get(0) != null) {
            g2d.drawLine(x1, y1, x1 + width, y1);
        }
        if (walls.get(1) != null) {
            g2d.drawLine(x1 + width, y1, x1 + width, y1 + width);
        }
        if (walls.get(2) != null) {
            g2d.drawLine(x1 + width, y1 + width, x1, y1 + width);
        }
        if (walls.get(3) != null) {
            g2d.drawLine(x1, y1 + width, x1, y1);
        }
        if (visited) {
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillRect(x1, y1, width, width);
        }
    }

    public Cell getNeighbor(Cell[][] grid) {
        ArrayList<Cell> neighbors = new ArrayList<>();
        if (x > 0 && !grid[x - 1][y].visited) {
            neighbors.add(grid[x - 1][y]);
        }
        if (x < grid[0].length - 1 && !grid[x + 1][y].visited) {
            neighbors.add(grid[x + 1][y]);
        }
        if (y < grid[0].length - 1 && !grid[x][y + 1].visited) {
            neighbors.add(grid[x][y + 1]);
        }
        if (y > 0 && !grid[x][y - 1].visited) {
            neighbors.add(grid[x][y - 1]);
        }

        if (neighbors.size() > 0) {
            int rand = ThreadLocalRandom.current().nextInt(0, neighbors.size());
            return neighbors.get(rand);
        } else {
            return null;
        }

    }
}
