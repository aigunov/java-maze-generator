package backend.academy.labirints.logic;

import backend.academy.labirints.model.Cell;
import backend.academy.labirints.model.GenerateParameters;
import backend.academy.labirints.model.Maze;
import java.util.ArrayDeque;
import java.util.Deque;

public class RecursiveBacktrackGenerator extends Generator {
    private final Deque<Cell> tracker;

    public RecursiveBacktrackGenerator(final int width, final int height) {
        super(width, height);
        tracker = new ArrayDeque<>();
    }

    /**
     * Метод содержащий в себе алгоритм Рекурсивного Возврата Трекера для генерации лабиринтов
     * Создается стек клеток начиная с первой и каждый выбирается верхняя из него
     * Случайным образом определяется следующая клетка из соседей текущей, к ней прокладывается путь
     * И добавляется в стек повторяя это пока все соседи новой клетки не будут уже посещенным
     * В таком случае берется следующая клетка из стека
     * До тех пор, пока сам стек не станет пустым - лабиринт сгенерирован
     *
     * @param params - объект класса GenerateParameters содержащий все необходимые для генерации поля
     * @return - объект класса Maze содержащий всю информацию о сгенерированном лабиринте
     */
    @Override
    public Maze generate(final GenerateParameters params) {
        var start = maze[random.nextInt(height)][random.nextInt(width)];
        start.isVisited(true);
        tracker.push(start);
        while (!tracker.isEmpty()) {
            var cell = tracker.peek();
            var neighbors = getUnvisitedNeighbors(cell);

            if (!neighbors.isEmpty()) {
                var neighbour = neighbors.get(random.nextInt(neighbors.size()));
                removeWall(cell, neighbour);
                neighbour.isVisited(true);
                tracker.push(neighbour);
            } else {
                tracker.pop();
            }
        }
        createAdditionalPaths();
        generateRandomCells();
        return Maze.builder()
            .maze(maze)
            .adjacentCells(adjacency)
            .walls(walls)
            .start(maze[params.start().y()][params.start().x()])
            .finish(maze[params.finish().y()][params.finish().x()])
            .build();
    }

}
