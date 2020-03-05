package huskymaps.server.logic;

import huskymaps.StreetMapGraph;
import huskymaps.params.Location;
import huskymaps.params.RouteRequest;

import astar.AStarSolver;

import java.util.List;

/** Application logic for the RoutingAPIHandler. */
public class Router {

    /**
     * Overloaded method for shortestPath that has flexibility to specify a solver
     * and returns a List of longs representing the shortest path from the node
     * closest to a start location and the node closest to the destination location.
     * @param g The graph to use.
     * @param request The requested route.
     * @return A list of node id's in the order visited on the shortest path.
     */
    public static List<Long> shortestPath(StreetMapGraph g, RouteRequest request) {
        long src = g.closest(new Location(request.startLat, request.startLon));
        long dest = g.closest(new Location(request.endLat, request.endLon));
        return new AStarSolver<>(g, src, dest, 20).solution();
    }
}
