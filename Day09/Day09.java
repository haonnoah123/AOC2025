import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Point;
import java.util.Arrays;
import java.util.List;

import Tools.FileImporter;
import Tools.Pair;

class Day09 {
    public static void main(String[] args) {
        List<String> input = FileImporter.getInput("Day09/Day09Input.txt");
        Point[] points = getPoints(input);
        System.out.println(partOne(points));
        System.out.println(partTwo(points));
    }

    public static long partOne(Point[] points) {
        Pair<Point, Point> largestAreaPair = findLargestArea(points);
        return getArea(largestAreaPair.a(), largestAreaPair.b());
    }

    public static long partTwo(Point[] points) {
        int[][] expandedPoints = new int[2][points.length];
        for (int i = 0; i < points.length; i++) {
            expandedPoints[0][i] = points[i].x;
            expandedPoints[1][i] = points[i].y;
        }

        Polygon poly = new Polygon(
                expandedPoints[0],
                expandedPoints[1],
                points.length);

        long maxRectangleSize = 0;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                Point Pt1 = points[i];
                Point Pt2 = points[j];

                Rectangle rect = new Rectangle(
                        Math.min(Pt1.x, Pt2.x),
                        Math.min(Pt1.y, Pt2.y),
                        Math.abs(Pt1.x - Pt2.x),
                        Math.abs(Pt1.y - Pt2.y));

                if (poly.contains(rect))
                    maxRectangleSize = Math.max(maxRectangleSize, getArea(Pt1, Pt2));
            }
        }
        return maxRectangleSize;
    }

    public static Pair<Point, Point> findLargestArea(Point[] points) {
        long largestArea = 0;
        Pair<Point, Point> largestAreaPair = null;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (getArea(points[i], points[j]) > largestArea) {
                    largestArea = getArea(points[i], points[j]);
                    largestAreaPair = new Pair<>(points[i], points[j]);
                }
            }
        }
        return largestAreaPair;
    }

    public static Point[] getPoints(List<String> input) {
        Point[] points = new Point[input.size()];
        for (int i = 0; i < points.length; i++) {
            int[] temp = Arrays.stream(input.get(i).split(",")).mapToInt(Integer::parseInt).toArray();
            points[i] = new Point(temp[0], temp[1]);
        }
        return points;
    }

    public static long getArea(Point PtA, Point PtB) {
        return (long) (Math.abs(PtA.getX() - PtB.getX()) + 1) * (long) (Math.abs(PtA.getY() - PtB.getY()) + 1);
    }
}
