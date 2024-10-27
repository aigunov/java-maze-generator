package backend.academy.labirints.logic;

import backend.academy.labirints.model.Cell;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class DeikstraSolver extends Solver {
    private static final int INF = Integer.MAX_VALUE;

    @Override
    public List<Cell> findShortestPath(Cell[][] cells, Map<Cell, List<Cell>> adjacency, Cell start, Cell finish) {
        Map<Cell, Integer> distances = new HashMap<>();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                distances.put(cells[i][j], INF);
            }
        }
        distances.put(start, 0);

        //TODO задать компаратор на surfaceFactor CellType
        PriorityQueue<Cell> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        queue.offer(start);

        //TODO Заменить на LinkedList
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
        return null;
    }

    private static List<Cell> reconstructPath(Map<Cell, Cell> predecessors, Cell finish) {
        List<Cell> path = new ArrayList<>();
        Cell current = finish;

        while (current != null) {
            path.add(current);
            current = predecessors.get(current);
        }

        Collections.reverse(path);
        return path;
    }

//    public static void main(String[] args) {
//        // Пример использования
//        Cell[][] cells = new Cell[5][5];
//        for (int i = 0; i < cells.length; i++) {
//            for (int j = 0; j < cells[0].length; j++) {
//                cells[i][j] = new Cell(i, j);
//            }
//        }
//
//        Map<Cell, List<Cell>> adjacency = new HashMap<>();
//        // ... (инициализация adjacency, например, из вашей матрицы лабиринта)
//
//        Cell start = cells[0][1]; // (0, 1)
//        Cell finish = cells[3][3]; // (3, 3)
//
//        List<Cell> shortestPath = findShortestPath(cells, adjacency, start, finish);
//        if (shortestPath != null) {
//            System.out.println("Кратчайший путь:");
//            for (Cell cell : shortestPath) {
//                System.out.println("(" + cell.x + ", " + cell.y + ")");
//            }
//        } else {
//            System.out.println("Путь не найден");
//        }
//    }
}
