package backend.academy.labirints;

import backend.academy.labirints.model.Cell;
import backend.academy.labirints.model.GenerateParameters;
import java.util.InputMismatchException;
import java.util.Scanner;

@SuppressWarnings({"RegexpSinglelineJava", "MagicNumber", "CatchParameterName", "MultipleStringLiterals",
    "IllegalIdentifierName"})
public class UserCommunicator {
    private static final Scanner SC = new Scanner(System.in);
    private static final GenerateParameters PARAMETERS = new GenerateParameters();

    private UserCommunicator() {
    }

    public static GenerateParameters communicate() {
        while (true) {
            System.out.println("Введите пожалуйста ширину и длину вашего лабиринта в перечисленном порядке: ");
            try {
                var width = SC.nextInt();
                var height = SC.nextInt();
                if (width >= 3 && height >= 3) {
                    System.out.println("Спасибо за введенные данные.");
                    PARAMETERS.width(width).height(height);
                    break;
                } else {
                    System.out.println("Введенные данные - невалидны");
                }
            } catch (InputMismatchException _) {
                System.out.println("Введите пожалуйста число, а не строку.");
            }
        }

        while (PARAMETERS.generateType() == null) {
            System.out.println("Выберите пожалуйста номер, чтобы определить каким алгоритмом сгенерировать лабиринт:");
            System.out.println("№1. Алгоритм Прима.");
            System.out.println("№2. Алгоритм Обратного рекурсивного отслеживания.");
            PARAMETERS.generateType(
                switch (SC.nextInt()) {
                    case 1 -> GenerateParameters.GenerateType.PRIM;
                    case 2 -> GenerateParameters.GenerateType.RECURSIVEBACKTRACK;
                    default -> {
                        System.out.println("Такого варианта нет");
                        yield null;
                    }
                }
            );
        }

        while (PARAMETERS.solverType() == null) {
            System.out.println("Выберите пожалуйста номер, чтобы определить каким алгоритмом проложить путь:");
            System.out.println("№1. Алгоритм Дейкстра");
            System.out.println("№2. Алгоритм Беллмана-Форда.");
            PARAMETERS.solverType(
                switch (SC.nextInt()) {
                    case 1 -> GenerateParameters.SolverType.DEIKSTRA;
                    case 2 -> GenerateParameters.SolverType.FORDBELLMAN;
                    default -> {
                        System.out.println("Такого варианта нет");
                        yield null;
                    }
                }
            );
        }

        while (true) {
            System.out.println("Теперь введите координаты для старта:");
            System.out.print("Старт, номер столба: ");
            int startColumn = SC.nextInt();
            System.out.print("\nСтарт, номер ряда: ");
            int startRow = SC.nextInt();
            if (checkValidationCoordinates(startColumn, startRow)) {
                PARAMETERS.start(new Cell.Coordinates(--startColumn, --startRow));
                break;
            }
        }
        while (true) {
            System.out.println("Теперь введите координаты для финиша:");
            System.out.print("Финиш, номер столба: ");
            var finishColumn = SC.nextInt();
            System.out.print("\nФиниш, номер ряда: ");
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
            System.out.printf("Введенные данные валидны. Точка: (%d;%d)%n", column, row);
            return true;
        } else {
            System.out.println("Введенные данные невалидны.");
        }
        return false;
    }
}
