package squid.map;

import squid.squidgrid.map.styled.DungeonGen;
import squid.squidgrid.map.styled.TilesetType;
import squid.squidgrid.map.DungeonUtility;
import squid.squidmath.XorRNG;

public class DungeonGenTest
{
    public static void main( String[] args )
    {
        DungeonGen bg = new DungeonGen(new XorRNG(0x1337deadbeef5c00l));
        for(TilesetType tt : TilesetType.values())
        {
            bg.generate(tt, 80, 80);
            bg.wallWrap();
            System.out.println(bg);
            bg.setDungeon(DungeonUtility.hashesToLines(bg.getDungeon()));
            System.out.println(bg);

            System.out.println();
        }
    }
}
