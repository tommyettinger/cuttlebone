package squid.squidgrid.fov;

import squid.squidmath.Point2D;

import java.util.LinkedList;
import java.util.Queue;
import squid.squidmath.Bresenham;

/**
 * A Bresenham-based line-of-sight algorithm.
 *
 * @author Eben Howard - http://squidpony.com - howard@squidpony.com
 */
public class BresenhamLOS implements LOSSolver {

    Queue<Point2D> lastPath = new LinkedList<Point2D>();

    @Override
    public boolean isReachable(float[][] resistanceMap, int startx, int starty, int targetx, int targety, float force, float decay, RadiusStrategy radiusStrategy) {
        Queue<Point2D> path = Bresenham.line2D(startx, starty, targetx, targety);
        lastPath = new LinkedList<Point2D>(path);//save path for later retreival
        float currentForce = force;
        for (Point2D p : path) {
            if (p.x == targetx && p.y == targety) {
                return true;//reached the end 
            }
            if (p.x != startx || p.y != starty) {//don't discount the start location even if on resistant cell
                currentForce *= (1 - resistanceMap[p.x][p.y]);
            }
            double radius = radiusStrategy.radius(startx, starty, p.x, p.y);
            if (currentForce - (radius * decay) <= 0) {
                return false;//too much resistance
            }
        }
        return false;//never got to the target point
    }
    public boolean isReachable(float[] resistanceMap1D, int wide, int startx, int starty, int targetx, int targety, float force, float decay, RadiusStrategy radiusStrategy) {
        Queue<Point2D> path = Bresenham.line2D(startx, starty, targetx, targety);
        lastPath = new LinkedList<Point2D>(path);//save path for later retreival
        float currentForce = force;
        for (Point2D p : path) {
            if (p.x == targetx && p.y == targety) {
                return true;//reached the end 
            }
            if (p.x != startx || p.y != starty) {//don't discount the start location even if on resistant cell
                currentForce *= (1 - resistanceMap1D[p.x + (wide * p.y)]);
            }
            double radius = radiusStrategy.radius(startx, starty, p.x, p.y);
            if (currentForce - (radius * decay) <= 0) {
                return false;//too much resistance
            }
        }
        return false;//never got to the target point
    }

    @Override
    public Queue<Point2D> getLastPath() {
        return lastPath;
    }

    @Override
    public boolean isReachable(float[][] resistanceMap, int startx, int starty, int targetx, int targety) {
        return isReachable(resistanceMap, startx, starty, targetx, targety, Float.MAX_VALUE, 0f, BasicRadiusStrategy.CIRCLE);
    }
    public boolean isReachable(float[] resistanceMap1D, int wide, int startx, int starty, int targetx, int targety) {
        return isReachable(resistanceMap1D, wide, startx, starty, targetx, targety, Float.MAX_VALUE, 0f, BasicRadiusStrategy.CIRCLE);
    }
}
