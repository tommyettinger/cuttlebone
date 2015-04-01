package squid.map;

import squid.squidgrid.map.styled.DungeonGen;
import squid.squidgrid.map.styled.TilesetType;

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

            System.out.println();

        }

    }
}