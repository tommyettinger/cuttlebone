package squid.squidgrid.fov;

import squid.squidmath.Point2D;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import squid.annotation.Beta;
import squid.squidmath.Elias;

/**
 * Uses Wu's Algorithm as modified by Elias to draw the line.
 *
 * The side view parameter is how far along the antialiased path to consider
 * when determining if the far end can be seen, from 0.0 to 1.0 with 1.0
 * considering the entire line. The closer to 1.0 this value is, the more
 * complete and symmetrical end-to-end the line is, but the computation cost
 * also increases.
 *
 * If undesired artifacts are seen when running the lines (missing squares
 * perhaps), try increasing the sideview value.
 *
 * @author Eben Howard - http://squidpony.com - howard@squidpony.com
 */
@Beta
public class EliasLOS implements LOSSolver {

    private Queue<Point2D> lastPath = new LinkedList<Point2D>();
    private float sideview = 0.75f;

    /**
     * Creates this solver with the default side view parameter.
     */
    public EliasLOS() {
    }

    /**
     * Creates this solver with the provided value for how far along the
     * antialiased line the antialiased portion will be considered.
     *
     * @param sideview
     */
    public EliasLOS(float sideview) {
        this.sideview = sideview;
    }

    public float getSideview() {
        return sideview;
    }

    public void setSideview(float sideview) {
        this.sideview = sideview;
    }

    @Override
    public boolean isReachable(float[][] resistanceMap, int startx, int starty, int targetx, int targety, float force, float decay, RadiusStrategy radiusStrategy) {
        List<Point2D> path = Elias.line(startx, starty, targetx, targety);
        lastPath = new LinkedList<Point2D>(path);//save path for later retreival

        BresenhamLOS los1 = new BresenhamLOS(),
                los2 = new BresenhamLOS();

        float checkRadius = radiusStrategy.radius(startx, starty) * 0.75f;
        while (!path.isEmpty()) {
            Point2D p = path.remove(0);

            //if a non-solid midpoint on the path can see both the start and end, consider the two ends to be able to see each other
            if (resistanceMap[p.x][p.y] < 1
                    && radiusStrategy.radius(startx, starty, p.x, p.y) < checkRadius
                    && los1.isReachable(resistanceMap, p.x, p.y, targetx, targety, force - (radiusStrategy.radius(startx, starty, p.x, p.y) * decay), decay, radiusStrategy)
                    && los2.isReachable(resistanceMap, startx, starty, p.x, p.y, force, decay, radiusStrategy)) {

                //record actual sight path used
                lastPath = new LinkedList<Point2D>(los1.lastPath);
                lastPath.addAll(los2.lastPath);
                return true;
            }
        }
        return false;//never got to the target point
    }
    public boolean isReachable(float[] resistanceMap1D, int wide, int startx, int starty, int targetx, int targety, float force, float decay, RadiusStrategy radiusStrategy) {
        List<Point2D> path = Elias.line(startx, starty, targetx, targety);
        lastPath = new LinkedList<Point2D>(path);//save path for later retreival

        BresenhamLOS los1 = new BresenhamLOS(),
                los2 = new BresenhamLOS();

        float checkRadius = radiusStrategy.radius(startx, starty) * 0.75f;
        while (!path.isEmpty()) {
            Point2D p = path.remove(0);

            //if a non-solid midpoint on the path can see both the start and end, consider the two ends to be able to see each other
            if (resistanceMap1D[p.x + (wide * p.y)] < 1
                    && radiusStrategy.radius(startx, starty, p.x, p.y) < checkRadius
                    && los1.isReachable(resistanceMap1D, wide, p.x, p.y, targetx, targety, force - (radiusStrategy.radius(startx, starty, p.x, p.y) * decay), decay, radiusStrategy)
                    && los2.isReachable(resistanceMap1D, wide, startx, starty, p.x, p.y, force, decay, radiusStrategy)) {

                //record actual sight path used
                lastPath = new LinkedList<Point2D>(los1.lastPath);
                lastPath.addAll(los2.lastPath);
                return true;
            }
        }
        return false;//never got to the target point
    }

    @Override
    public boolean isReachable(float[][] resistanceMap, int startx, int starty, int targetx, int targety) {
        return isReachable(resistanceMap, startx, starty, targetx, targety, Float.MAX_VALUE, 0f, BasicRadiusStrategy.CIRCLE);
    }
    public boolean isReachable(float[] resistanceMap1D, int wide, int startx, int starty, int targetx, int targety) {
        return isReachable(resistanceMap1D, wide, startx, starty, targetx, targety, Float.MAX_VALUE, 0f, BasicRadiusStrategy.CIRCLE);
    }

    @Override
    public Queue<Point2D> getLastPath() {
        return lastPath;
    }
}
