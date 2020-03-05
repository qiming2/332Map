package pointset;

import java.util.List;

public class KDTreePointSet implements PointSet {
    private KdNode root;

    private static class KdNode {
        public Point p;
        public KdNode left;
        public KdNode right;

        public KdNode(Point p) {
            this.p = p;
            left = null;
            right = null;
        }
    }

    /**
     * Instantiates a new KDTree with the given points.
     * @param points a non-null, non-empty list of points to include
     *               (makes a defensive copy of points, so changes to the list
     *               after construction don't affect the point set)
     */
    public KDTreePointSet(List<Point> points) {
        if (points != null && !points.isEmpty()) {
            for (Point p: points) {
                root = insertPoint(root, p, true);
            }
        }
    }

    private KdNode insertPoint(KdNode node, Point p, boolean isVer) {
        if (node == null) {
            return new KdNode(p);
        }

        if (node.p.equals(p)) {
            return node;
        }

        if (isVer) {
            if (p.x() > node.p.x()) {
                node.right = insertPoint(node.right, p, false);
            } else {
                node.left = insertPoint(node.left, p, false);
            }
        } else {
            if (p.y() > node.p.y()) {
                node.right = insertPoint(node.right, p, true);
            } else {
                node.left = insertPoint(node.left, p, true);
            }
        }
        return node;
    }

    /**
     * Returns the point in this set closest to (x, y) in (usually) O(log N) time,
     * where N is the number of points in this set.
     */
    @Override
    public Point nearest(double x, double y) {
        return near(root, new Point(x, y), root.p, true);
    }

    // Helper method to find the nearest node
    private Point near(KdNode node, Point dst, Point nearest, boolean isVer) {
        if (node == null) {
            return nearest;
        }

        // Record the good and bad side
        KdNode good;
        KdNode bad;
        if (node.p.distanceSquaredTo(dst) < nearest.distanceSquaredTo(dst)) {
            nearest = node.p;
        }

        if (isVer) {
            if (dst.x() > node.p.x()) {
                good = node.right;
                bad = node.left;
            } else {
                good = node.left;
                bad = node.right;
            }
        } else {
            if (dst.y() > node.p.y()) {
                good = node.right;
                bad = node.left;
            } else {
                good = node.left;
                bad = node.right;
            }
        }
        nearest = near(good, dst, nearest, !isVer);
        if (isBadSidePossible(node, dst, nearest, isVer)) {
            nearest = near(bad, dst, nearest, !isVer);
        }

        return nearest;
    }

    private boolean isBadSidePossible(KdNode cur, Point dst, Point nearest, boolean isVer) {
        double coorDis;
        double coorCur;
        if (isVer) {
            coorDis = dst.x();
            coorCur = cur.p.x();
        } else {
            coorDis = dst.y();
            coorCur = cur.p.y();
        }
        double dstToSplitPlane = Math.pow(coorCur - coorDis, 2);

        return Double.compare(dstToSplitPlane, nearest.distanceSquaredTo(dst)) <= 0;
    }
}
