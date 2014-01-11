package squid.squidmath;

/**
 * Generic two dimensional coordinate class.
 *
 * @author Tommy Ettinger
 * @author Lewis Potter
 * @author Eben Howard - http://squidpony.com - howard@squidpony.com
 */
public class Point2D {
	public int x;
	public int y;
	
    /**
     * Creates a three dimensional coordinate with the given location.
     * 
     * @param x
     * @param y
     */
    public Point2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the linear distance between this coordinate point and the provided one.
     * 
     * @param other
     * @return 
     */
    public double distance(Point2D other) {
        return Math.sqrt(squareDistance(other));
    }

    /**
     * Returns the square of the linear distance between this coordinate
     * point and the provided one.
     * 
     * @param other
     * @return 
     */
    public double squareDistance(Point2D other) {
        double dx = x - other.x;
        double dy = y - other.y;
        return dx * dx + dy * dy;
    }

    /**
     * Returns the Manhattan distance between this point and the provided one.
     * The Manhattan distance is the distance between each point on each separate
     * axis all added together.
     * 
     * @param other
     * @return 
     */
    public int manhattanDistance(Point2D other) {
        int distance = Math.abs(x - other.x);
        distance += Math.abs(y - other.y);
        return distance;
    }

    /**
     * Returns the largest difference between the two points along any one axis.
     * 
     * @param other
     * @return 
     */
    public int maxAxisDistance(Point2D other) {
        return Math.max(Math.abs(x - other.x), Math.abs(y - other.y));
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + x;
        hash = 73 * hash + y;
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Point2D) {
            Point2D other = (Point2D) o;
            return x == other.x && y == other.y;
        } else {
            return false;
        }
    }
}
