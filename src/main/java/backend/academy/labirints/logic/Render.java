package backend.academy.labirints.logic;

import backend.academy.labirints.model.Cell;
import backend.academy.labirints.model.Maze;
import java.util.List;

public interface Render {
    Render renderLabyrinth(final Maze maze);

    Render renderPath(final Maze maze);

    void draw();

}
