import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private Node root;
    private int size;

    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;
    }

    private static class Node {
        private final Point2D p;      // the point
        private final RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node(Point2D p, RectHV rect) {
            if (rect == null) {
                rect = new RectHV(0, 0, 1, 1);
            }
            this.rect = rect;
            this.p = p;
        }
    }


    public boolean isEmpty() { return root == null; } // is the set empty?


    public int size() { return size; } // number of points in the set

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty())
            root = insert(root, p, null, true);
        else
            root = insert(root, p, root.rect, true);
    }

    private Node insert(Node x, Point2D p, RectHV rect, boolean useX) {
        if (x == null) {
            size++;
            return new Node(p, rect);
        }
        if (x.p.equals(p)) return x;
        // select comparator by depth
        int cmp = useX ? Point2D.X_ORDER.compare(p, x.p) : Point2D.Y_ORDER.compare(p, x.p);
        if (cmp < 0) {
            if (useX) x.lb = insert(x.lb, p, x.lb == null ? new RectHV(rect.xmin(), rect.ymin(), x.p.x(), rect.ymax()) : x.lb.rect, !useX);
            else x.lb = insert(x.lb, p, x.lb == null ? new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), x.p.y()) : x.lb.rect, !useX);
        } else {
            if (useX) x.rt = insert(x.rt, p, x.rt == null ? new RectHV(x.p.x(), rect.ymin(), rect.xmax(), rect.ymax()) : x.rt.rect, !useX);
            else x.rt = insert(x.rt, p, x.rt == null ? new RectHV(rect.xmin(), x.p.y(), rect.xmax(), rect.ymax()) : x.rt.rect, !useX);
        }
        return x;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return contains(root, p, true);
    }

    private boolean contains(Node x, Point2D p, boolean useX) {
        if (x == null) return false;
        if (x.p.equals(p)) return true;
        int cmp = useX ? Point2D.X_ORDER.compare(p, x.p) : Point2D.Y_ORDER.compare(p, x.p);
        return cmp < 0 ? contains(x.lb, p, !useX) : contains(x.rt, p, !useX);
    }
    // draw all points to standard draw
    public void draw() {
        StdDraw.rectangle(0.5, 0.5, 0.5, 0.5);
        if (isEmpty()) return;
        draw(root, true);
    }

    private void draw(Node x, boolean vertical) {
        if (x.lb != null) draw(x.lb, !vertical);
        if (x.rt != null) draw(x.rt, !vertical);

        // draw point
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(x.p.x(), x.p.y());

        // draw line
        double xmin, ymin, xmax, ymax;

        if (vertical) {
            StdDraw.setPenColor(StdDraw.RED);
            xmin = x.p.x();
            xmax = x.p.x();
            ymin = x.rect.ymin();
            ymax = x.rect.ymax();
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            ymin = x.p.y();
            ymax = x.p.y();
            xmin = x.rect.xmin();
            xmax = x.rect.xmax();
        }
        StdDraw.setPenRadius();
        StdDraw.line(xmin, ymin, xmax, ymax);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> q = new Stack<>();
        range(root, rect, q);
        return q;
    }

    private void range(Node x, RectHV rect, Stack<Point2D> q) {
        if (x == null) return;
        if (rect.contains(x.p)) q.push(x.p);

        if (x.lb != null && rect.intersects(x.lb.rect)) range(x.lb, rect, q);
        if (x.rt != null && rect.intersects(x.rt.rect)) range(x.rt, rect, q);
    }
    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty()) return null;
        return nearest(root, p, root.p, true);
    }

    private Point2D nearest(Node x, Point2D p, Point2D minPoint, boolean useX) {
        Point2D min = minPoint;

        if (x == null) return min;
        if (p.distanceSquaredTo(x.p) < p.distanceSquaredTo(min)) min = x.p;


        if (useX ? x.p.x() < p.x() : x.p.y() < p.y()) {
                min = nearest(x.rt, p, min, !useX);
                if (x.lb != null && (min.distanceSquaredTo(p) > x.lb.rect.distanceSquaredTo(p))) {
                    min = nearest(x.lb, p, min, !useX);
                }
        }
        else {
            min = nearest(x.lb, p, min, !useX);
            if (x.rt != null && (min.distanceSquaredTo(p) > x.rt.rect.distanceSquaredTo(p))) {
                min = nearest(x.rt, p, min, !useX);
            }
        }

        return min;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}