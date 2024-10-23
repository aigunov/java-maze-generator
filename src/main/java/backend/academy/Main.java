package backend.academy;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        char[][] maze = {
            {'*', '*', '#', '*'},
            {'#', '*', '*'}
        };

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                System.out.print(maze[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
