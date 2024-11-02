package backend.academy.labirints.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(of = {"type"})
public class Cell {
    private Coordinates coordinates;
    private CellType type;
    private boolean isVisited;

    public int y() {
        return coordinates.y;
    }

    public int x() {
        return coordinates.x;
    }

    @Getter()
    public enum CellType {
        GOOD(0),
        BAD(4),
        NOTHING(2);

        private final int surfaceFactor;

        CellType(int surfaceFactor) {
            this.surfaceFactor = surfaceFactor;
        }

    }

    /**
     * Record отвечающий за хранение координат клетки в лабиринте и также операции связанные с координатами
     *
     * @param x - столбец
     * @param y - ряд
     */
    public record Coordinates(int x, int y) {

        /**
         * Вычисляет x координату в grid
         *
         * @return x в grid
         */
        public int calculateGridX() {
            return x * 2 + 1;
        }

        /**
         * Вычисляет y координату в grid
         *
         * @return y в grid
         */
        public int calculateGridY() {
            return y * 2 + 1;
        }

        /**
         * Определяет относительное положение следующей клетки от this,
         * для того чтобы определить координаты промежуточной клетки между двумя клетками PATH
         * @param otherCoordinates - следующая от this клетка в пути
         * @return координаты промежуточной клетки в grid
         */
        public Coordinates getRelativePosition(final Coordinates otherCoordinates) {
            if (otherCoordinates.x == x && otherCoordinates.y == y + 1) {
                return new Coordinates(calculateGridX(), calculateGridY() + 1);
            } else if (otherCoordinates.x == x && otherCoordinates.y == y - 1) {
                return new Coordinates(calculateGridX(), calculateGridY() - 1);
            } else if (otherCoordinates.x == x + 1 && otherCoordinates.y == y) {
                return new Coordinates(calculateGridX() + 1, calculateGridY());
            } else {
                return new Coordinates(calculateGridX() - 1, calculateGridY());
            }
        }

    }
}
