package backend.academy.labirints;

import backend.academy.labirints.logic.ConsoleRender;
import backend.academy.labirints.logic.Generator;
import backend.academy.labirints.logic.PrimGenerator;
import backend.academy.labirints.logic.RecursiveBacktrackGenerator;
import backend.academy.labirints.logic.Render;

public class MazeManager {
    private final UserInterface userInterface = new UserInterface();
    private Generator generator;
    private Render render = new ConsoleRender();

    public void Labyrinth(){
        var params = userInterface.communicate();
        generator = switch (params.generateType()){
            case PRIM -> new PrimGenerator(params.width(), params.height());
            case RECURSIVEBACKTRACK -> new RecursiveBacktrackGenerator(params.width(), params.height());
        };
        var maze = generator.generate();
        render.render(maze, params.start(), params.finish());
    }

    public static void main(String[] args) {
        var manager = new MazeManager();
        manager.Labyrinth();
    }
}
