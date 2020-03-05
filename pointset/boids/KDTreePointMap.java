package pointset.boids;

import pointset.Point;

import java.util.*;



public class KDTreePointMap<V> {
    private Map<Point, V> map = new HashMap<>();
    private KdNode root = null;

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

    private static class PointComparator implements Comparator<Point> {
        Point dst;
        public PointComparator(Point dst) {
            this.dst = dst;
        }
        public int compare(Point one, Point two) {
            double oneDst = one.distanceSquaredTo(dst);
            double twoDst = two.distanceSquaredTo(dst);
            return Double.compare(twoDst, oneDst);
        }

    }

    /** Return the value associated with the point p. */
    public V get(Point p) {
        return map.get(p);
    }

    /** Associates the value with the point p. */
    public void put(Point p, V value) {
        root = insertPoint(root, p, true);
        map.put(p, value);
    }

    /** Returns the point in this map closest to (x, y). */
    public Point nearest(double x, double y) {
        Point dst = new Point(x, y);
        Queue<Point> queue = new PriorityQueue<>(new PointComparator(dst));
        queue = near(root, dst, true, queue, 1);
        if (queue.size() > 0) {
            return queue.remove();
        }
        return null;
    }

    /** Returns the k-nearest points in this map closest to (x, y). */
    public Iterable<Point> nearest(double x, double y, int k) {
        Point dst = new Point(x, y);
        Queue<Point> queue = new PriorityQueue<>(new PointComparator(dst));
        queue = near(root, dst, true, queue, k);
        if (queue.size() >= k) {
            return queue;
        }
        return null;
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

    // Helper method to find the nearest node
    private Queue<Point> near(KdNode node, Point dst, boolean isVer, Queue<Point> queue, int k) {
        if (node == null) {
            return queue;
        }

        // Record the good and bad side
        KdNode good;
        KdNode bad;
        if (queue.size() < k) {
            queue.add(node.p);
        }  else if (node.p.distanceSquaredTo(dst) < queue.peek().distanceSquaredTo(dst)) {
            queue.remove();
            queue.add(node.p);
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
        near(good, dst, !isVer, queue, k);
        if (isBadSidePossible(node, dst, queue.peek(), isVer) || queue.size() < k) {
            near(bad, dst, !isVer, queue, k);
        }

        return queue;
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

        return dstToSplitPlane < nearest.distanceSquaredTo(dst);
    }


}
