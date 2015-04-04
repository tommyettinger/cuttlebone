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
        DungeonUtility.rng = rng;
        DungeonGen bg = new DungeonGen(rng);

        bg.generate(TilesetType.DEFAULT_DUNGEON, 40, 40);
        bg.wallWrap();

        char[][] dun = bg.getDungeon();
        Dijkstra dijkstra = new Dijkstra(dun);

        System.out.println(bg);

        Point2D entry = DungeonUtility.randomFloor(dun), goal1 = DungeonUtility.randomFloor(dun),
                goal2 = DungeonUtility.randomFloor(dun), goal3 = DungeonUtility.randomFloor(dun);
        dijkstra.findPath(100, null, null, entry, goal1, goal2, goal3);
        int[][] cm = dijkstra.combinedMap;
        char[][] md = DungeonUtility.doubleWidth(dun),
                hl = DungeonUtility.doubleWidth(DungeonUtility.hashesToLines(dun));
        for(int x = 0; x < md.length; x++)
        {
            for(int y = 0; y < md[x].length; y++)
            {
                char t = (char)33;
                if(x%2 == 0 && cm[x/2][y] < 200)
                    t = '.';
                else if(cm[x/2][y] == Dijkstra.WALL)
                    t = hl[x][y];
                else
                    t = ' ';
                md[x][y] = t;
            }
        }
        for(Point2D pt : dijkstra.path)
        {
            md[pt.x * 2][pt.y] = '@';
        }
        bg.setDungeon(md);
        System.out.println(bg);

        System.out.println(dijkstra.path.size());

    }
}
