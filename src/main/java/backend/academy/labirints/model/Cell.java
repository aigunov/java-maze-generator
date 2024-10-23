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
@ToString(of = {"id"})
public class Cell {
    private int id;
    private int x;
    private int y;
    private boolean isVisited;
}
