package backend.academy.labirints.logic;

import backend.academy.labirints.model.Maze;

public class ConsoleRender implements Render{
    private final static String PASSAGE = "⬜️";
    private final static String WALL = "⬛";
    private final static String BAD = "⛔";
    private final static String GOOD = "✅";

    private final Maze maze;
    private final int width, height;

    public ConsoleRender(final Maze maze) {
        this.maze = maze;
        width = maze.maze()[0].length;
        height = maze.maze().length;
    }

    @Override
    public void render(Maze maze) {
        for (int i = 0; i < width; i++) {
            System.out.print(WALL+"\t");
        }
    }

    public static void main(String[] args) {
    }
}
