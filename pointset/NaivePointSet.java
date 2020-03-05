package pointset;

import java.util.ArrayList;
import java.util.List;

/**
 * Naive nearest neighbor implementation using a linear scan.
 */
public class NaivePointSet implements PointSet {
    List<Point> points;

    /**
     * Instantiates a new NaivePointSet with the given points.
     * @param points a non-null, non-empty list of points to include
     *               (makes a defensive copy of points, so changes to the list
     *               after construction don't affect the point set)
     */
    public NaivePointSet(List<Point> points) {
        if (points != null && !points.isEmpty()) {
            this.points = new ArrayList<>(points);
        }
    }

    /**
     * Returns the point in this set closest to (x, y) in O(N) time,
     * where N is the number of points in this set.
     */
    @Override
    public Point nearest(double x, double y) {
        if (points == null) { return null; }
        Point near = points.get(0);
        double distance = near.distanceSquaredTo(x, y);
        double tempD;
        for (Point p: points) {
            tempD = p.distanceSquaredTo(x, y);
            if (distance > tempD) {
                distance = tempD;
                near = p;
            }
        }
        return near;
    }
}
