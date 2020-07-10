import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;

public class FastCollinearPoints {
    private Point[] points;
    private LineSegment[] segments;
    private int segmentCount;

//    // finds all line segments containing 4 or more points
//    public FastCollinearPoints(Point[] points) {
//        checksPoints(points);
//
//        this.points = points.clone();
//        this.segments = new LineSegment[2];
//        this.segmentCount = 0;
//
//        for (Point p : this.points) {
//            Arrays.sort(this.points, p.slopeOrder());
//
//            for (int j = 0; j < points.length - 2; j++) {
//                if (p.slopeTo(this.points[j]) == p.slopeTo(this.points[j + 1]) && p.slopeTo(this.points[j + 1]) == p.slopeTo(this.points[j + 2])) {
//
//                    add(new LineSegment(points[j], points[j + 2]));
//                }
//            }
//        }
//    }

    public FastCollinearPoints(Point[] points) {

        // check to see if argument matches constraints
        checksPoints(points);

        this.points = points.clone();
        this.segments = new LineSegment[2];
        this.segmentCount = 0;
        LinkedList<Point> collinearPoints = new LinkedList<Point>(); // store collinear points relative to each reference origin point

        // check to see if argument matches constraints
        for (Point p : this.points) // reference origin point
        {
            Arrays.sort(this.points, p.slopeOrder());
            double prevSlope = 0.0;

            for (int j = 0; j < this.points.length; j++) {
                double currSlope = p.slopeTo(this.points[j]);
                // if the current slope doesn't match the previous slope, add line segment if more than 3 collinear points, then clear collinearpoints
                if (j == 0 || currSlope != prevSlope) // j==0 condition exists for the off-chance the currSlope equals 0.0 on the first iteration
                {

                    if (collinearPoints.size() >= 3) {
                        // add line segment before clearing
                        this.add(new LineSegment(collinearPoints.getFirst(), collinearPoints.getLast()));
                    }

                    collinearPoints.clear();
                }
                // add to collinear if slope matches
                collinearPoints.add(this.points[j]);
                prevSlope = currSlope;
            }
        }

    }
    // the number of line segments
    public int numberOfSegments() {
        return segmentCount;
    }
    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segmentCount);
    }

    private void checksPoints(Point[] points){
        if (points == null) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < points.length; i ++) {
            for (int j = 0; j < points.length; j++) {

                if (points[i] == null || points[j] == null) {
                    throw new IllegalArgumentException();
                }

                if (i != j && points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    private void resize(int capacity) {
        assert capacity >= this.segmentCount;

        // textbook implementation
        LineSegment[] temp = new LineSegment[capacity];
        for (int i = 0; i < segments.length; i++) {
            temp[i] = segments[i];
        }
        this.segments = temp;
    }

    private void add(LineSegment item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if(this.segmentCount == this.segments.length) {
            resize(2 * this.segments.length);
        }

        this.segments[this.segmentCount++] = item;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}