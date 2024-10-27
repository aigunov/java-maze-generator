package backend.academy.labirints.logic;

import backend.academy.labirints.model.Cell;
import backend.academy.labirints.model.Maze;
import java.util.List;

public interface Render {
    void renderLabyrinth(final Maze maze, final Cell.Coordinates start, final Cell.Coordinates finish);

    void renderPathInLabyrinth(final List<Cell> path);

    void draw();

}
