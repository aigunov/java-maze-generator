package backend.academy.labirints.logic;

import backend.academy.labirints.GeneratorTestMaze;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConsoleRenderTest {
    @Test
    void testRenderLabyrinthCorrect() {
        var render = ConsoleRender.getInstance(3, 3);
        var expected = renderGrid();
        var actual = ((ConsoleRender)render.renderLabyrinth(GeneratorTestMaze.generateTestMaze())).grid();
        Assertions.assertEquals(expected.length, actual.length, "Массивы имеют разную длину");
        for (int i = 0; i < expected.length; i++) {
            Assertions.assertArrayEquals(expected[i], actual[i], "Строки массивов не равны");
        }
    }

    @Test
    void testRenderPathCorrect() {
        var render = ConsoleRender.getInstance(3, 3);
        var maze = GeneratorTestMaze.generateTestMaze();
        var expected = renderPathInGrid();
        var actual = ((ConsoleRender)render.renderLabyrinth(maze).renderPath(maze)).grid();
        Assertions.assertEquals(expected.length, actual.length, "Массивы имеют разную длину.");
        for(int i = 0; i< expected.length; i++){
            System.out.println("exp: " + Arrays.toString(expected[i]) + " actual: " + Arrays.toString(actual[i]));
            Assertions.assertArrayEquals(expected[i], actual[i], "Строки массивов не равны.");
        }
    }

    private String[][] renderGrid() {
        return new String[][] {
            {"⬛", "⬛", "⬛", "⬛", "⬛", "⬛", "⬛"},
            {"⬛", "🟢", "⬛", "⬜️", "⬛", "⬜️", "⬛"},
            {"⬛", "⬜️", "⬛", "⬜️", "⬛", "⬜️", "⬛"},
            {"⬛", "⬜️", "⬜️", "⬜️", "⬜️", "⬜️", "⬛"},
            {"⬛", "⬛", "⬛", "⬛", "⬛", "⬜️", "⬛"},
            {"⬛", "⬜️", "⬜️", "⬜️", "⬜️", "🛑", "⬛"},
            {"⬛", "⬛", "⬛", "⬛", "⬛", "⬛", "⬛"}
        };
    }

    private String[][] renderPathInGrid(){
        return new String[][] {
            {"⬛", "⬛", "⬛", "⬛", "⬛", "⬛", "⬛"},
            {"⬛", "🟢", "⬛", "⬜️", "⬛", "⬜️", "⬛"},
            {"⬛", "🟪", "⬛", "⬜️", "⬛", "⬜️", "⬛"},
            {"⬛", "🟪", "🟪", "🟪", "🟪", "🟪", "⬛"},
            {"⬛", "⬛", "⬛", "⬛", "⬛", "🟪", "⬛"},
            {"⬛", "⬜️", "⬜️", "⬜️", "⬜️", "🛑", "⬛"},
            {"⬛", "⬛", "⬛", "⬛", "⬛", "⬛", "⬛"}
        };
    }
}
