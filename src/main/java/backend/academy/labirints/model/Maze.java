package backend.academy.labirints.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder(toBuilder = true)
public class Maze {
    private Cell[][] maze;
    private List<Cell> path;
    private Map<Cell, List<Cell>> adjacentCells;
    private Map<Cell, List<Cell>> walls;
    private final Cell start, finish;
}
