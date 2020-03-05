package astar;

import pq.ArrayHeapMinPQ;
import pq.ExtrinsicMinPQ;
import edu.princeton.cs.algs4.Stopwatch;


import java.util.*;

import static astar.ShortestPathsSolver.SolverOutcome.*;

/**
 * @see ShortestPathsSolver for more method documentation
 */
public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome = SOLVED;
    private List<Vertex> result = new LinkedList<>();
    private double solutionW = 0.0;
    private int numStates = 0;
    private double exploreT;

    /**
     * Immediately solves and stores the result of running memory optimized A*
     * search, computing everything necessary for all other methods to return
     * their results in constant time. The timeout is given in seconds.
     */
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        if (input == null || start == null || end == null || timeout < 0.0) {
            exploreT = sw.elapsedTime();
            outcome = UNSOLVABLE;
            throw new IllegalArgumentException();
        }
        ExtrinsicMinPQ<Vertex> fringe = new ArrayHeapMinPQ<>();
        Map<Vertex, Vertex> edgeTo = new HashMap<>();
        Map<Vertex, Double> distTo = new HashMap<>();
        fringe.add(start, 0.0 + input.estimatedDistanceToGoal(start, end));
        edgeTo.put(start, start);
        distTo.put(start, 0.0);
        while (!fringe.isEmpty()) {
            Vertex temp = fringe.removeSmallest();
            if (temp.equals(end)) {
                break;
            }
            if (sw.elapsedTime() > timeout) {
                outcome = TIMEOUT;
                exploreT = sw.elapsedTime();
                break;
            }
            numStates++;
            for (WeightedEdge<Vertex> e: input.neighbors(temp)) {
                Vertex cur = e.to();
                double distance = distTo.get(temp) + e.weight();
                if (distTo.get(cur) == null || distTo.get(cur) > distance) {
                    edgeTo.put(cur, temp);
                    distTo.put(cur, distance);
                    double estimate = input.estimatedDistanceToGoal(cur, end);
                    if (!fringe.contains(cur)) {
                        fringe.add(cur, distance + estimate);
                    } else {
                        fringe.changePriority(cur, distance + estimate);
                    }
                }
            }
        }

        if (outcome != TIMEOUT) {
            if (distTo.get(end) == null) {
                outcome = UNSOLVABLE;
            } else {
                Vertex present = end;
                solutionW = distTo.get(end);
                result.add(0, end);
                while (!present.equals(start)) {
                    present = edgeTo.get(present);
                    result.add(0, present);
                }
            }
            exploreT = sw.elapsedTime();
        }
    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        return result;
    }

    @Override
    public double solutionWeight() {
        if (outcome == UNSOLVABLE || outcome == TIMEOUT) {
            return Double.POSITIVE_INFINITY;
        }
        return solutionW;
    }

    /** The total number of priority queue removeSmallest operations. */
    @Override
    public int numStatesExplored() {
        return numStates;
    }

    @Override
    public double explorationTime() {
        return exploreT;
    }
}
