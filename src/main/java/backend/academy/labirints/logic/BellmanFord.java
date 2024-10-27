package backend.academy.labirints.logic;

import backend.academy.labirints.model.Cell;
import backend.academy.labirints.model.Maze;
import backend.academy.labirints.model.Point;
import java.util.List;
import java.util.Map;

public class BellmanFord  extends Solver {
    @Override
    public List<Cell> findShortestPath(Cell[][] cells, Map<Cell, List<Cell>> adjacency, Cell start, Cell finish) {
        int[] dist = new int[cells.length * cells[0].length];
        Cell[] prev = new Cell[cells.length * cells[0].length];

        // Инициализация dist и prev
        for (int i = 0; i < dist.length; i++) {
            dist[i] = Integer.MAX_VALUE;
            prev[i] = null;
        }
        dist[getIndex(start, n)] = 0; // Начальная точка

        // Основной цикл
        for (int k = 0; k < n * n - 1; k++) {
            for (Cell u : adjacency.keySet()) {
                for (Cell v : adjacency.get(u)) {
                    int uIndex = getIndex(u, n);
                    int vIndex = getIndex(v, n);
                    if (dist[uIndex] != Integer.MAX_VALUE && dist[uIndex] + 1 < dist[vIndex]) { // Стоимость ребра = 1
                        dist[vIndex] = dist[uIndex] + 1;
                        prev[vIndex] = u;
                    }
                }
            }
        }

        // Проверка на отрицательные циклы
        for (Cell u : adjancecy.keySet()) {
            for (Cell v : adjancecy.get(u)) {
                int uIndex = getIndex(u, n);
                int vIndex = getIndex(v, n);
                if (dist[uIndex] != Integer.MAX_VALUE && dist[uIndex] + 1 < dist[vIndex]) {
                    throw new IllegalArgumentException("Лабиринт содержит отрицательный цикл!");
                }
            }
        }

        // Восстановление пути
        return reconstructPath(prev, end, n);
    }

    // Методы для индексации клеток в массиве
    private static int getIndex(Cell cell, int n) {
        return cell.x * n + cell.y;
    }

    // Метод для восстановления пути
    private static List<Cell> reconstructPath(Cell[] prev, Cell end, int n) {
        List<Cell> path = new ArrayList<>();
        int endIndex = getIndex(end, n);
        if (prev[endIndex] == null) {
            return path; // Путь не найден
        }
        Cell current = end;
        while (current != null) {
            path.add(current);
            current = prev[getIndex(current, n)];
        }
        return path;
    }
}
