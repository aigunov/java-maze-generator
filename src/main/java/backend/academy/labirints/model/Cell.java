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
@ToString(of = {"id", "type"})
public class Cell {
    private int id;
    private int x;
    private int y;
    private CellType type;
    private boolean isVisited;

    public int getRelativePosition(final Cell nextCell) {
        if (nextCell.x == x && nextCell.y == y + 1) {
            return 3; // снизу
        } else if (nextCell.x == x && nextCell.y == y - 1) {
            return 1; // сверху
        } else if (nextCell.x == x + 1 && nextCell.y == y) {
            return 2; // справа
        } else {
            return 4; // снизу
        }
    }

    @Getter()
    public enum CellType{
        GOOD(0),
        BAD(4),
        NOTHING(2);

        private final int surfaceFactor;

        CellType(int surfaceFactor) {
            this.surfaceFactor = surfaceFactor;
        }
    }

}
