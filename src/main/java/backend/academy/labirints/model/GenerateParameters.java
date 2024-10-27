package backend.academy.labirints.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GenerateParameters {
    private int width, height;
    private Cell.Coordinates start, finish;
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
