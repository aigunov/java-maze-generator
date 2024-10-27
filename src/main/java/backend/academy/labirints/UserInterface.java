package backend.academy.labirints;

import backend.academy.labirints.model.GenerateParameters;
import backend.academy.labirints.model.Point;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInterface {
    private final Scanner sc = new Scanner(System.in);
    private final GenerateParameters params = new GenerateParameters();

    public GenerateParameters communicate() {
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
                params.start(new Point(start_column, start_row));
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
                params.finish(new Point(finish_column, finish_row));
                break;
            }
        }
        return params;
    }

    private boolean checkValidationCoordinates(int finish_column, int finish_row) {
        if (finish_column > 0 && finish_column <= params.width() &&
            finish_row > 0 && finish_row <= params.height()) {
            System.out.printf("Введенные данные валидны. Точка: (%d;%d)%n", finish_column,finish_row);
            return true;
        }else {
            System.out.println("Введенные данные невалидны.");
        }
        return false;
    }
}
