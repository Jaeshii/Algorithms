import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private SET<Point2D> points;

    // construct an empty set of points
    public PointSET() { points = new SET<>(); }

    // is the set empty?
    public boolean isEmpty() { return points.isEmpty(); }

    // number of points in the set
    public int size() { return points.size(); }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        points.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);

        for (Point2D p : points) {
            StdDraw.point(p.x(), p.y());
        }

        StdDraw.show();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Stack<Point2D> q = new Stack<>();

        for (Point2D p : points) {
            if (rect.contains(p)) {
                q.push(p);
            }
        }
        return q;
    }
    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        double minDistance = 0;
        Point2D nearestPoint = null;

        for (Point2D p2D : points) {
            double distance = p2D.distanceSquaredTo(p);
            if (distance > minDistance) {
                minDistance = distance;
                nearestPoint = p;
            }
        }
        return nearestPoint;
    }
    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}