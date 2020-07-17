import javax.swing.*;
import java.awt.*;

public class MazeGenerator{
    public static void main(String[] args){
        JFrame frame = new JFrame("Maze");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1200,1200);
        frame.setBackground(Color.DARK_GRAY);
        Maze maze = new Maze(1000);
        frame.add(maze);
        frame.setVisible(true);
    }

}
