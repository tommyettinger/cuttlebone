package squid.squidgrid.util;

import squid.squidmath.Point2D;
import squid.squidmath.XorRNG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dijkstra {
    public int[][] physicalMap;
    public int[][] combinedMap;
    public int height, width;
    public ArrayList<Point2D> path;
    public static int[][] dirShuffledY = new int[][]
            {
                    new int[]{-1, 0, 1, 0},
                    new int[]{-1, 0, 0, 1},
                    new int[]{-1, 1, 0, 0},
                    new int[]{-1, 0, 0, 1},
                    new int[]{-1, 1, 0, 0},
                    new int[]{-1, 0, 1, 0},

                    new int[]{1, 0, -1, 0},
                    new int[]{1, 0, 0, -1},
                    new int[]{1, -1, 0, 0},
                    new int[]{1, 0, 0, -1},
                    new int[]{1, -1, 0, 0},
                    new int[]{1, 0, -1, 0},

                    new int[]{0, 1, -1, 0},
                    new int[]{0, 1, 0, -1},
                    new int[]{0, -1, 1, 0},
                    new int[]{0, 0, 1, -1},
                    new int[]{0, -1, 0, 1},
                    new int[]{0, 0, -1, 1},

                    new int[]{0, 1, -1, 0},
                    new int[]{0, 1, 0, -1},
                    new int[]{0, -1, 1, 0},
                    new int[]{0, 0, 1, -1},
                    new int[]{0, -1, 0, 1},
                    new int[]{0, 0, -1, 1},

            }, dirShuffledX = new int[][]
            {
                    new int[]{0, 1, 0, -1},
                    new int[]{0, 1, -1, 0},
                    new int[]{0, 0, 1, -1},
                    new int[]{0, -1, 1, 0},
                    new int[]{0, 0, -1, 1},
                    new int[]{0, -1, 0, 1},

                    new int[]{0, 1, 0, -1},
                    new int[]{0, 1, -1, 0},
                    new int[]{0, 0, 1, -1},
                    new int[]{0, -1, 1, 0},
                    new int[]{0, 0, -1, 1},
                    new int[]{0, -1, 0, 1},

                    new int[]{1, 0, 0, -1},
                    new int[]{1, 0, -1, 0},
                    new int[]{1, 0, 0, -1},
                    new int[]{1, -1, 0, 0},
                    new int[]{1, 0, -1, 0},
                    new int[]{1, -1, 0, 0},

                    new int[]{-1, 0, 0, 1},
                    new int[]{-1, 0, 1, 0},
                    new int[]{-1, 0, 0, 1},
                    new int[]{-1, 1, 0, 0},
                    new int[]{-1, 0, 1, 0},
                    new int[]{-1, 1, 0, 0},

            };
    public static final int GOAL = 0, FLOOR = Integer.MAX_VALUE - 500, WALL = Integer.MAX_VALUE - 300,
            DARK = Integer.MAX_VALUE - 100;
    public HashMap<Point2D, Integer> goals;
    private HashMap<Point2D, Integer> fresh, closed, open, filled;
    public static XorRNG rng;
    private static int frustration = 0;

    public Dijkstra() {
        rng = new XorRNG();
        path = new ArrayList<Point2D>();

        goals = new HashMap<Point2D, Integer>();
        fresh = new HashMap<Point2D, Integer>();
        closed = new HashMap<Point2D, Integer>();
        open = new HashMap<Point2D, Integer>();
        filled = new HashMap<Point2D, Integer>();
    }

    public Dijkstra(final int[][] level) {
        width = level.length;
        height = level[0].length;
        combinedMap = new int[width][height];
        physicalMap = new int[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                combinedMap[x][y] = level[x][y];
                physicalMap[x][y] = level[x][y];
            }
        }


        rng = new XorRNG();
        path = new ArrayList<Point2D>();

        goals = new HashMap<Point2D, Integer>();
        fresh = new HashMap<Point2D, Integer>();
        closed = new HashMap<Point2D, Integer>();
        open = new HashMap<Point2D, Integer>();
        filled = new HashMap<Point2D, Integer>();
    }

    /**
     * Constructor meant to take a char[][] returned by DungeonGen.generate(), or any other
     * char[][] where '#' means a wall and anything else is a walkable tile.
     *
     * @param level
     */
    public Dijkstra(final char[][] level) {
        width = level.length;
        height = level[0].length;
        combinedMap = new int[width][height];
        physicalMap = new int[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int t = (level[x][y] == '#') ? WALL : FLOOR;
                combinedMap[x][y] = t;
                physicalMap[x][y] = t;
            }
        }


        rng = new XorRNG();
        path = new ArrayList<Point2D>();

        goals = new HashMap<Point2D, Integer>();
        fresh = new HashMap<Point2D, Integer>();
        closed = new HashMap<Point2D, Integer>();
        open = new HashMap<Point2D, Integer>();
        filled = new HashMap<Point2D, Integer>();
    }

    public void resetMap() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                combinedMap[x][y] = physicalMap[x][y];
            }
        }
    }

    public void reset() {
        resetMap();
        goals.clear();
        path.clear();
        closed.clear();
        fresh.clear();
        open.clear();
        frustration = 0;
    }

    public void setGoal(int x, int y) {
        if (physicalMap[x][y] > FLOOR) {
            return;
        }

        goals.put(new Point2D(x, y), GOAL);
    }

    public void setGoal(Point2D pt) {
        if (physicalMap[pt.x][pt.y] > FLOOR) {
            return;
        }

        goals.put(pt, GOAL);
    }

    public void setOccupied(int x, int y) {
        combinedMap[x][y] = WALL;
    }

    public void resetCell(int x, int y) {
        combinedMap[x][y] = physicalMap[x][y];
    }

    public void resetCell(Point2D pt) {
        combinedMap[pt.x][pt.y] = physicalMap[pt.x][pt.y];
    }

    public void clearGoals() {
        for (Map.Entry<Point2D, Integer> entry : goals.entrySet()) {
            resetCell(entry.getKey());
        }
        goals.clear();
    }

    protected void setFresh(int x, int y, int counter) {
        combinedMap[x][y] = counter;
        fresh.put(new Point2D(x, y), counter);
    }

    protected void setFresh(final Point2D pt, int counter) {
        combinedMap[pt.x][pt.y] = counter;
        fresh.put(pt, counter);
    }

    /**
     * Recalculate the Dijkstra map and return it. Cells that were marked as goals with setGoal will have
     * a value of 0, the cells adjacent to goals will have a value of 1, and cells progressively further
     * from goals will have a value equal to the distance from the nearest goal. The exceptions are walls,
     * which will have a value defined by the WALL constant in this class, and areas that the scan was
     * unable to reach, which will have a value defined by the DARK constant in this class. (typically,
     * these areas should not be used to place NPCs or items and should be filled with walls).
     *
     * @param impassable A Map of Position keys to any values (values will be ignored). Positions should be
     *                 those of enemies or other moving obstacles to a path that cannot be moved through.
     * @return A 2D int[width][height] using the width and height of what this knows about the physical map.
     */
    public int[][] scan(Map<Point2D, Object> impassable) {
        if(impassable == null)
            impassable = new HashMap<Point2D, Object>();
        resetMap();
        HashMap<Point2D, Integer> blocking = new HashMap<Point2D, Integer>(impassable.size());
        for (Point2D pt : impassable.keySet()) {
            blocking.put(pt, WALL);
        }
        closed.putAll(blocking);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (combinedMap[x][y] > FLOOR)
                    closed.put(new Point2D(x, y), physicalMap[x][y]);
            }
        }
        for (Map.Entry<Point2D, Integer> entry : goals.entrySet()) {
            if (closed.containsKey(entry.getKey()))
                closed.remove(entry.getKey());
            combinedMap[entry.getKey().x][entry.getKey().y] = GOAL;
        }
        int numAssigned = goals.size();
        int iter = 0;
        open.putAll(goals);
        Point2D[] dirs = {new Point2D(-1, 0), new Point2D(0, -1), new Point2D(1, 0), new Point2D(0, 1)};
        while (numAssigned > 0) {
            ++iter;
            numAssigned = 0;

            for (Map.Entry<Point2D, Integer> cell : open.entrySet()) {
                Point2D adj = cell.getKey();
                for (int d = 0; d < 4; d++) {
                    adj = cell.getKey().add(dirs[d]);
                    if (!closed.containsKey(adj) && !open.containsKey(adj) && combinedMap[cell.getKey().x][cell.getKey().y] + 1 < combinedMap[adj.x][adj.y]) {
                        setFresh(adj, iter);
                        ++numAssigned;
                    }
                }
            }
            closed.putAll(open);
            open = (HashMap<Point2D, Integer>) (fresh.clone());
            fresh.clear();
        }
        closed.clear();
        open.clear();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (combinedMap[y][x] == FLOOR) {
                    combinedMap[y][x] = DARK;
                }
            }
        }

        return combinedMap;
    }

    /**
     * Scans the dungeon using Dijkstra.scan with the listed goals and start point, and returns a list
     * of Point2D positions (using Manhattan distance) needed to get closer to the closest reachable
     * goal. The maximum length of the returned list is given by length; if moving the full length of
     * the list would place the mover in a position shared by one of the positions in onlyPassable
     * (which is typically filled with friendly units that can be passed through in multi-tile-
     * movement scenarios), it will recalculate a move so that it does not pass into that cell.
     * The keys in impassable should be the positions of enemies and obstacles that cannot be moved
     * through, and will be ignored if there is a goal overlapping one.
     *
     * @param length
     * @param impassable
     * @param onlyPassable
     * @param start
     * @param goals
     * @return
     */
    public ArrayList<Point2D> findPath(int length, Map<Point2D, Object> impassable,
                                       Map<Point2D, Object> onlyPassable, Point2D start, Point2D... goals) {
        path = new ArrayList<Point2D>();
        if(impassable == null)
            impassable = new HashMap<Point2D, Object>();
        if(onlyPassable == null)
            onlyPassable = new HashMap<Point2D, Object>();
        for (Point2D goal : goals) {
            setGoal(goal.x, goal.y);
        }
        scan(impassable);
        Point2D currentPos = start;
        while (combinedMap[currentPos.x][currentPos.y] > 0) {
            if (frustration > 500) {
                path = new ArrayList<Point2D>();
                frustration = 0;
                clearGoals();
                filled.clear();
                return path;
            }
            int best = Integer.MAX_VALUE - 1000, choice = 0, whichOrder = rng.nextInt(24);
            int[] dirsY = dirShuffledY[whichOrder], dirsX = dirShuffledX[whichOrder];
            for (int d = 0; d < 4; d++) {
                Point2D pt = currentPos.add(dirsX[d], dirsY[d]);
                if (combinedMap[pt.x][pt.y] < best) {
                    best = combinedMap[pt.x][pt.y];
                    choice = d;
                }
            }
            if (best >= Integer.MAX_VALUE - 1000) {
                frustration = 0;
                clearGoals();
                filled.clear();
                path = new ArrayList<Point2D>();
                return path;
            }
            currentPos.y += dirsY[choice];
            currentPos.x += dirsX[choice];
            path.add(new Point2D(currentPos.x, currentPos.y));
            frustration++;
            if (path.size() >= length) {
                if (onlyPassable.containsKey(currentPos)) {

                    closed.put(currentPos, WALL);
                    filled.put(currentPos, WALL);
                    scan(impassable);
                    return findPath(length, impassable, onlyPassable, start, goals);
                }
                clearGoals();
                filled.clear();
                return path;
            }
        }
        frustration = 0;
        clearGoals();
        filled.clear();
        return path;
    }

}
