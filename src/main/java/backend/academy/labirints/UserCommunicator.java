package backend.academy.labirints;

import backend.academy.labirints.model.Cell;
import backend.academy.labirints.model.GenerateParameters;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

@SuppressWarnings({"MagicNumber", "MultipleStringLiterals",
    "IllegalIdentifierName"})
@SuppressFBWarnings({"DM_DEFAULT_ENCODING"})
public class UserCommunicator {
    private static final Scanner SC = new Scanner(System.in);
    private static final GenerateParameters PARAMETERS = new GenerateParameters();
    private static final PrintStream OUT = System.out;

    private UserCommunicator() {
    }

    public static GenerateParameters communicate() {
        while (true) {
            OUT.println("Введите пожалуйста ширину и длину вашего лабиринта в перечисленном порядке: ");
            try {
                var width = SC.nextInt();
                var height = SC.nextInt();
                if (width >= 3 && height >= 3) {
                    OUT.println("Спасибо за введенные данные.");
                    PARAMETERS.width(width).height(height);
                    break;
                } else {
                    OUT.println("Введенные данные - невалидны");
                }
            } catch (InputMismatchException e) {
                OUT.println("Введите пожалуйста число, а не строку.");
            }
        }

        while (PARAMETERS.generateType() == null) {
            OUT.println("Выберите пожалуйста номер, чтобы определить каким алгоритмом сгенерировать лабиринт:");
            OUT.println("№1. Алгоритм Прима.");
            OUT.println("№2. Алгоритм Обратного рекурсивного отслеживания.");
            PARAMETERS.generateType(
                switch (SC.nextInt()) {
                    case 1 -> GenerateParameters.GenerateType.PRIM;
                    case 2 -> GenerateParameters.GenerateType.RECURSIVEBACKTRACK;
                    default -> {
                        OUT.println("Такого варианта нет");
                        yield null;
                    }
                }
            );
        }

        while (PARAMETERS.solverType() == null) {
            OUT.println("Выберите пожалуйста номер, чтобы определить каким алгоритмом проложить путь:");
            OUT.println("№1. Алгоритм Дейкстра");
            OUT.println("№2. Алгоритм Беллмана-Форда.");
            PARAMETERS.solverType(
                switch (SC.nextInt()) {
                    case 1 -> GenerateParameters.SolverType.DEIKSTRA;
                    case 2 -> GenerateParameters.SolverType.FORDBELLMAN;
                    default -> {
                        OUT.println("Такого варианта нет");
                        yield null;
                    }
                }
            );
        }

        while (true) {
            OUT.println("Теперь введите координаты для старта:");
            OUT.print("Старт, номер столба: ");
            int startColumn = SC.nextInt();
            OUT.print("\nСтарт, номер ряда: ");
            int startRow = SC.nextInt();
            if (checkValidationCoordinates(startColumn, startRow)) {
                PARAMETERS.start(new Cell.Coordinates(--startColumn, --startRow));
                break;
            }
        }
        while (true) {
            OUT.println("Теперь введите координаты для финиша:");
            OUT.print("Финиш, номер столба: ");
            var finishColumn = SC.nextInt();
            OUT.print("\nФиниш, номер ряда: ");
            var finishRow = SC.nextInt();
            if (checkValidationCoordinates(finishColumn, finishRow)) {
                PARAMETERS.finish(new Cell.Coordinates(--finishColumn, --finishRow));
                break;
            }
        }
        return PARAMETERS;
    }

    private static boolean checkValidationCoordinates(int column, int row) {
        if (column > 0 && column <= PARAMETERS.width()
            && row > 0 && row <= PARAMETERS.height()) {
            OUT.printf("Введенные данные валидны. Точка: (%d;%d)%n", column, row);
            return true;
        } else {
            OUT.println("Введенные данные невалидны.");
        }
        return false;
    }
}
