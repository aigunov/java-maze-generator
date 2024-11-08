package backend.academy.labirints.logic;

import backend.academy.labirints.model.Cell;
import backend.academy.labirints.model.Maze;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

@SuppressFBWarnings({"CLI_CONSTANT_LIST_INDEX"})
public class DeikstraSolver extends Solver {
    private static final int INF = Integer.MAX_VALUE;

    /**
     * Метод прокладывающий путь по алгоритму Дейкстра
     *
     * @param maze - объект Maze со всей необходимой информацией для определения пути
     * @return - список клеток определяющих сам путь {@link #findShortestPath(Maze)}
     */
    @Override
    public List<Cell> findShortestPath(final Maze maze) {
        var cells = maze.maze();
        var adjacency = maze.adjacentCells();
        var start = maze.start();
        var finish = maze.finish();
        Map<Cell, Integer> distances = new HashMap<>();

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                distances.put(cells[i][j], INF);
            }
        }
        distances.put(start, 0);

        PriorityQueue<Cell> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        queue.offer(start);

        Map<Cell, Cell> predecessors = new HashMap<>();

        while (!queue.isEmpty()) {
            Cell current = queue.poll();
            if (current.equals(finish)) {
                return reconstructPath(predecessors, finish);
            }

            for (Cell neighbor : adjacency.getOrDefault(current, new ArrayList<>())) {
                int distanceToNeighbor = distances.get(current) + neighbor.type().surfaceFactor();

                if (distanceToNeighbor < distances.get(neighbor)) {
                    distances.put(neighbor, distanceToNeighbor);
                    predecessors.put(neighbor, current);
                    queue.offer(neighbor);
                }
            }
        }
        throw new IllegalArgumentException("Невозможно найти путь, сгенерированный лабиринт - невалиден");
    }

    /**
     * Метод конвертируют результат работы алгоритма Дейкстра в список клеток(путь)
     *
     * @param predecessors маппа со всеми клетками
     * @param finish       - конечная клетка к которой прокладывался путь
     * @return - список клеток (путь)
     */
    private List<Cell> reconstructPath(Map<Cell, Cell> predecessors, Cell finish) {
        List<Cell> path = new ArrayList<>();
        Cell current = finish;

        while (current != null) {
            path.add(current);
            current = predecessors.get(current);
        }

        Collections.reverse(path);
        return path;
    }
}
