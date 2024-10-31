package backend.academy;

import backend.academy.labirints.MazeManager;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        MazeManager manager = new MazeManager();
        manager.labyrinth();
    }
}
