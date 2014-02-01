package squid.squidgrid.fov;

import squid.annotation.Beta;
import squid.squidmath.Elias;

/**
 * Uses the Elias line running to raycast.
 *
 * Does not currently support translucency.
 * 
 * For information on the sideview parameter, see the EliasLOS documentation.
 *
 * @author Eben Howard - http://squidpony.com - howard@squidpony.com
 */
@Beta
public class EliasFOV implements FOVSolver {

    private float[][] lightMap, resistanceMap;
    private float maxRadius, force, decay;
    private int width, height;
    private RadiusStrategy rStrat;
    private float sideview = 0.75f;

    /**
     * Creates a solver which will use the default sideview on the internal
     * EliasLOS solver.
     */
    public EliasFOV() {
    }

    /**
     * Creates a solver which will use the provided sideview value on the
     * internal EliasLOS solver.
     *
     * @param sideview
     */
    public EliasFOV(float sideview) {
        this.sideview = sideview;
    }

    @Override
    public float[][] calculateFOV(float[][] resistanceMap, int startx, int starty, float force, float decay, RadiusStrategy radiusStrategy) {
        this.resistanceMap = resistanceMap;
        width = resistanceMap.length;
        height = resistanceMap[0].length;
        lightMap = new float[width][height];
        this.force = force;
        this.decay = decay;
        rStrat = radiusStrategy;

        maxRadius = force / decay;
        int left = (int) Math.max(0, startx - maxRadius - 1);
        int right = (int) Math.min(width - 1, startx + maxRadius + 1);
        int top = (int) Math.max(0, starty - maxRadius - 1);
        int bottom = (int) Math.min(height - 1, starty + maxRadius + 1);


        //run rays out to edges
        for (int x = left; x <= right; x++) {
            runLineGroup(startx, starty, x, top);
            runLineGroup(startx, starty, x, bottom);
        }
        for (int y = top; y <= bottom; y++) {
            runLineGroup(startx, starty, left, y);
            runLineGroup(startx, starty, right, y);
        }

        return lightMap;
    }
    public float[] calculateFOV(float[] resistanceMap1D, int wide, int startx, int starty, float force, float decay, RadiusStrategy radiusStrategy) {
        width = wide;
        height = resistanceMap1D.length / width;
        this.resistanceMap = new float[width][height];
        for(int x = 0; x < width; x++)
        {
            for(int y = 0; y < height; y++)
            {
                this.resistanceMap[x][y] = resistanceMap1D[(y * width) + x];
            }
        }
        width = resistanceMap.length;
        height = resistanceMap[0].length;
        lightMap = new float[width][height];
        this.force = force;
        this.decay = decay;
        rStrat = radiusStrategy;

        maxRadius = force / decay;
        int left = (int) Math.max(0, startx - maxRadius - 1);
        int right = (int) Math.min(width - 1, startx + maxRadius + 1);
        int top = (int) Math.max(0, starty - maxRadius - 1);
        int bottom = (int) Math.min(height - 1, starty + maxRadius + 1);


        //run rays out to edges
        for (int x = left; x <= right; x++) {
            runLineGroup(startx, starty, x, top);
            runLineGroup(startx, starty, x, bottom);
        }
        for (int y = top; y <= bottom; y++) {
            runLineGroup(startx, starty, left, y);
            runLineGroup(startx, starty, right, y);
        }

        float[] light1D = new float[width * height];
        for(int x = 0; x < width; x++)
        {
            for(int y = 0; y < height; y++)
            {
                light1D[(y * width) + x] = lightMap[x][y];
            }
        }
        return light1D;
    }

    private void runLineGroup(int startx, int starty, int endx, int endy) {
        float[][] tempMap = Elias.lightMap(startx, starty, endx, endy);
        EliasLOS los = new EliasLOS(sideview);
//        boolean xpositive = endx > startx;
//        boolean ypositive = endy > starty;
        for (int x = Math.min(startx, endx); x <= Math.max(startx, endx); x++) {
            for (int y = Math.min(starty, endy); y <= Math.max(starty, endy); y++) {
                float radius = rStrat.radius(startx, starty, x, y);
                if (radius < maxRadius && los.isReachable(resistanceMap, startx, starty, x, y)) {
                    lightMap[x][y] = Math.max(lightMap[x][y], tempMap[x][y] * (force - radius * decay));
                }
            }
        }
    }

    @Override
    public float[][] calculateFOV(float[][] resistanceMap, int startx, int starty, float radius) {
        return calculateFOV(resistanceMap, startx, starty, 1, 1 / radius, BasicRadiusStrategy.CIRCLE);
    }
    public float[] calculateFOV(float[] resistanceMap1D, int wide, int startx, int starty, float radius) {
        return calculateFOV(resistanceMap1D, wide, startx, starty, 1, 1 / radius, BasicRadiusStrategy.CIRCLE);
    }
}
