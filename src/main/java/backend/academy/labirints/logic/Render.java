package backend.academy.labirints.logic;

import backend.academy.labirints.model.Maze;

public interface Render {
    Render renderLabyrinth(Maze maze);

    Render renderPath(Maze maze);

    void draw();

}
