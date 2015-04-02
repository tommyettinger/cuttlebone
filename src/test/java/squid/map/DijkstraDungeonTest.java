package squid.map;

import squid.squidgrid.map.DungeonUtility;
import squid.squidgrid.map.styled.DungeonGen;
import squid.squidgrid.map.styled.TilesetType;
import squid.squidgrid.util.Dijkstra;
import squid.squidmath.Point2D;
import squid.squidmath.XorRNG;

/**
 * Created by Tommy Ettinger on 4/2/2015.
 */
public class DijkstraDungeonTest {
    public static void main(String[] args) {
        XorRNG rng = new XorRNG(0x1337deadbeefc000l);
        DungeonGen bg = new DungeonGen(rng);

        bg.generate(TilesetType.DEFAULT_DUNGEON, 80, 80);
        bg.wallWrap();

        char[][] dun = bg.dungeon;
        Dijkstra dijkstra = new Dijkstra(dun);

        System.out.println(bg);

        Point2D entry = DungeonUtility.randomFloor(dun, rng), goal1 = DungeonUtility.randomFloor(dun, rng),
                goal2 = DungeonUtility.randomFloor(dun, rng), goal3 = DungeonUtility.randomFloor(dun, rng);
        dijkstra.findPath(100, null, null, entry, goal1, goal2, goal3);
        int[][] cm = dijkstra.combinedMap;
        char[][] dd = bg.dungeon, hl = DungeonUtility.hashesToLines(bg.dungeon);
        for(int x = 0; x < dd.length; x++)
        {
            for(int y = 0; y < dd[x].length; y++)
            {
                char t = (char)33;
                if(cm[x][y] < 200)
                    t = '.';
                else if(cm[x][y] == Dijkstra.WALL)
                    t = hl[x][y];
                else
                    t = ' ';
                dd[x][y] = t;
            }
        }
        for(Point2D pt : dijkstra.path)
        {
            dd[pt.x][pt.y] = '@';
        }
        bg.setDungeon(dd);
        System.out.println(bg);

        System.out.println(dijkstra.path.size());

    }
}
