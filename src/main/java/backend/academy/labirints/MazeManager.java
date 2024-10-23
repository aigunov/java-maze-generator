package backend.academy.labirints;

public class MazeManager {
    private final UserInterface userInterface = new UserInterface();

    public void Labyrinth(){
        var params = userInterface.communicate();

    }

    public static void main(String[] args) {
        var manager = new MazeManager();
        manager.Labyrinth();
    }
}
