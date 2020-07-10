import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private Point[] points;
    private LineSegment[] segments;
    private int segmentCount;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        checksPoints(points);

        this.points = points.clone();
        this.segments = new LineSegment[2];
        this.segmentCount = 0;

        Arrays.sort(this.points);

        for (int i = 0; i < this.points.length - 3; i++) {
            for (int j = i + 1; j < this.points.length - 2; j++) {
                for (int k = j + 1; k < this.points.length - 1; k++) {
                    for (int l = k + 1; l < this.points.length; l++) {
                        if (this.points[i].slopeTo(this.points[j]) == this.points[j].slopeTo(this.points[k]) &&
                                this.points[j].slopeTo(this.points[k]) == this.points[k].slopeTo(this.points[l])) {
                            add(new LineSegment(this.points[i], this.points[l]));
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segmentCount;
    }
    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, numberOfSegments());
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}