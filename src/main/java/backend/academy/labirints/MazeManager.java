package backend.academy.labirints;

import backend.academy.labirints.logic.BellmanFord;
import backend.academy.labirints.logic.ConsoleRender;
import backend.academy.labirints.logic.DeikstraSolver;
import backend.academy.labirints.logic.PrimGenerator;
import backend.academy.labirints.logic.RecursiveBacktrackGenerator;

public class MazeManager {
    public void labyrinth() {
        var params = UserCommunicator.communicate();
        var render = ConsoleRender.getInstance(params.height(), params.width());
        var generator = switch (params.generateType()) {
            case PRIM -> new PrimGenerator(params.width(), params.height());
            case RECURSIVEBACKTRACK -> new RecursiveBacktrackGenerator(params.width(), params.height());
        };
        var solver = switch (params.solverType()) {
            case DEIKSTRA -> new DeikstraSolver();
            case FORDBELLMAN -> new BellmanFord();
        };
        var maze = generator.generate(params);
        render.renderLabyrinth(maze).draw();
        maze.path(solver.findShortestPath(maze));
        render.renderPath(maze).draw();
    }
}
