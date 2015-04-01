package squid.map;

import squid.squidgrid.map.styled.DungeonGen;
import squid.squidgrid.map.styled.TilesetType;
import squid.squidgrid.map.DungeonUtils;
public class DungeonGenTest
{
    public static void main( String[] args )
    {
        DungeonGen bg = new DungeonGen();
        for(TilesetType tt : TilesetType.values())
        {
            bg.generate(tt, 80, 80);
            bg.wallWrap();
            System.out.println(bg);
            bg.setDungeon(DungeonUtils.HashesToLines(bg.getDungeon()));
            System.out.println(bg);

            System.out.println();

        }

    }
}
