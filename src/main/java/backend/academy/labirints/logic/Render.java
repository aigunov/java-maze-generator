package backend.academy.labirints.logic;

import backend.academy.labirints.model.Cell;
import backend.academy.labirints.model.Maze;
import backend.academy.labirints.model.Point;
import java.util.List;

public interface Render {
    void renderLabyrinth(final Maze maze, final Point start, final Point finish);

    void renderPathInLabyrinth(final List<Cell> path);

    void draw();

}
