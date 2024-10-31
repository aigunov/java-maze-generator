package backend.academy.labirints.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class GenerateParameters {
    private int width;
    private int height;
    private Cell.Coordinates start;
    private Cell.Coordinates finish;
    private GenerateType generateType;
    private SolverType solverType;

    public enum SolverType {
        DEIKSTRA,
        FORDBELLMAN
    }

    public enum GenerateType {
        PRIM,
        RECURSIVEBACKTRACK
    }

}
