package backend.academy.labirints.logic;

import backend.academy.labirints.model.Maze;
import backend.academy.labirints.model.Point;

public interface Render {
    void render(final Maze maze, final Point start, final Point finish);

    void draw();
}
