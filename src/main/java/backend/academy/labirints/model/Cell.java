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

    public record Coordinates(int x, int y) {
        public int calculateGridX() {
            return x * 2 + 1;
        }

        public int calculateGridY() {
            return y * 2 + 1;
        }

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
