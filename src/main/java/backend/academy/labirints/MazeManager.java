package backend.academy.labirints;

import backend.academy.labirints.logic.BellmanFord;
import backend.academy.labirints.logic.ConsoleRender;
import backend.academy.labirints.logic.DeikstraSolver;
import backend.academy.labirints.logic.Generator;
import backend.academy.labirints.logic.PrimGenerator;
import backend.academy.labirints.logic.RecursiveBacktrackGenerator;
import backend.academy.labirints.logic.Render;
import backend.academy.labirints.logic.Solver;

public class MazeManager {
    private final UserInterface userInterface = new UserInterface();
    private final Render render = new ConsoleRender();
    private Generator generator;
    private Solver solver;


    public void Labyrinth(){
        var params = userInterface.communicate();
        generator = switch (params.generateType()){
            case PRIM -> new PrimGenerator(params.width(), params.height());
            case RECURSIVEBACKTRACK -> new RecursiveBacktrackGenerator(params.width(), params.height());
        };
        solver = switch (params.solverType()) {
            case DEIKSTRA -> new DeikstraSolver();
            case FORDBELLMAN -> new BellmanFord();
        };
        var maze = generator.generate();
        render.renderLabyrinth(maze, params.start(), params.finish());
        var start = maze.maze()[params.start().y()][params.start().x()];
        var finish = maze.maze()[params.finish().y()][params.finish().x()];
        var path = solver.findShortestPath(maze.maze(), maze.adjacentCells(), start, finish);
        System.out.println("ПУТЬ: ");
        render.renderPathInLabyrinth(path);
    }

    public static void main(String[] args) {
        var manager = new MazeManager();
        manager.Labyrinth();
    }
}
