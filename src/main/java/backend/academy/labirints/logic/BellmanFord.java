package backend.academy.labirints.logic;

import backend.academy.labirints.model.Cell;
import backend.academy.labirints.model.Maze;
import java.util.ArrayList;
import java.util.List;

public class BellmanFord extends Solver {

    /**
     * Метод прокладывающий путь по алгоритму Беллмана-Форда
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
        int countVertex = cells.length * cells[0].length;
        int[] dist = new int[countVertex];
        Cell[] prev = new Cell[countVertex];

        // Инициализация dist и prev
        for (int i = 0; i < dist.length; i++) {
            dist[i] = Integer.MAX_VALUE;
            prev[i] = null;
        }
        dist[getIndex(start, cells.length)] = 0;

        for (int k = 0; k < countVertex - 1; k++) {
            boolean updated = false;  // Флаг, отслеживающий изменения
            for (var entry : adjacency.entrySet()) {
                Cell u = entry.getKey();
                int uIndex = getIndex(u, cells.length);
                if (dist[uIndex] != Integer.MAX_VALUE) {
                    for (Cell v : entry.getValue()) {
                        int vIndex = getIndex(v, cells.length);
                        if (dist[uIndex] + u.type().surfaceFactor() < dist[vIndex]) {
                            dist[vIndex] = dist[uIndex] + u.type().surfaceFactor();
                            prev[vIndex] = u;
                            updated = true;  // Обновление произошло
                        }
                    }
                }
            }
            // Если не было изменений, алгоритм завершает работу
            if (!updated) {
                break;
            }
        }
        // Восстановление пути
        return reconstructPath(prev, finish, cells.length);
    }

    /**
     * Вычисляет индекс клетки из лабиринта в списке distance
     */
    private int getIndex(Cell cell, int n) {
        return cell.x() * n + cell.y();
    }

    /**
     * Метод конвертируют результат работы алгоритма Беллмана-Форда в список клеток(путь)
     *
     * @param end    - конечная клетка к которой прокладывался путь
     * @param arrLen - длина списка distance
     * @param prev   - список составленным алгоритмом
     * @return - список клеток (путь)
     */
    private List<Cell> reconstructPath(final Cell[] prev, final Cell end, final int arrLen) {
        List<Cell> path = new ArrayList<>();
        int endIndex = getIndex(end, arrLen);
        if (prev[endIndex] == null) {
            throw new IllegalArgumentException("Невозможно найти путь, сгенерированный лабиринт - невалиден");
        }
        Cell current = end;
        while (current != null) {
            path.add(current);
            current = prev[getIndex(current, arrLen)];
        }
        return path;
    }
}
