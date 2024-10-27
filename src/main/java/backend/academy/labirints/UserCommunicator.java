package backend.academy.labirints;

import backend.academy.labirints.model.Cell;
import backend.academy.labirints.model.GenerateParameters;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserCommunicator {
    private static final Scanner sc = new Scanner(System.in);
    private static final GenerateParameters params = new GenerateParameters();
    private UserCommunicator() {}

    public static GenerateParameters communicate() {
        while (true) {
            System.out.println("Введите пожалуйста ширину и длину вашего лабиринта в перечисленном порядке: ");
            try {
                var width = sc.nextInt();
                var height = sc.nextInt();
                if (width >= 3 && height >= 3) {
                    System.out.println("Спасибо за введенные данные.");
                    params.width(width).height(height);
                    break;
                } else {
                    System.out.println("Введенные данные - невалидны");
                }
            } catch (InputMismatchException _) {
                System.out.println("Введите пожалуйста число, а не строку.");
            }
        }

        while (params.generateType() == null) {
            System.out.println("Выберите пожалуйста номер, чтобы определить каким алгоритмом сгенерировать лабиринт:");
            System.out.println("№1. Алгоритм Прима.");
            System.out.println("№2. Алгоритм Обратного рекурсивного отслеживания.");
            params.generateType(
                switch (sc.nextInt()) {
                    case 1 -> GenerateParameters.GenerateType.PRIM;
                    case 2 -> GenerateParameters.GenerateType.RECURSIVEBACKTRACK;
                    default -> {
                        System.out.println("Такого варианта нет");
                        yield null;
                    }
                }
            );
        }

        while (params.solverType() == null) {
            System.out.println("Выберите пожалуйста номер, чтобы определить каким алгоритмом проложить путь:");
            System.out.println("№1. Алгоритм Дейкстра");
            System.out.println("№2. Алгоритм Беллмана-Форда.");
            params.solverType(
                switch (sc.nextInt()) {
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
            int start_column = sc.nextInt();
            System.out.print("\nСтарт, номер ряда: ");
            int start_row = sc.nextInt();
            if (checkValidationCoordinates(start_column, start_row)) {
                params.start(new Cell.Coordinates(--start_column, --start_row));
                break;
            }
        }
        while(true){
            System.out.println("Теперь введите координаты для финиша:");
            System.out.print("Финиш, номер столба: ");
            var finish_column = sc.nextInt();
            System.out.print("\nФиниш, номер ряда: ");
            var finish_row = sc.nextInt();
            if (checkValidationCoordinates(finish_column, finish_row)) {
                params.finish(new Cell.Coordinates(--finish_column, --finish_row));
                break;
            }
        }
        return params;
    }

    private static boolean checkValidationCoordinates(int column, int row) {
        if (column > 0 && column <= params.width() &&
            row > 0 && row <= params.height()) {
            System.out.printf("Введенные данные валидны. Точка: (%d;%d)%n", column,row);
            return true;
        }else {
            System.out.println("Введенные данные невалидны.");
        }
        return false;
    }
}
