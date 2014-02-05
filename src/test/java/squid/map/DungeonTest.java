package squid.map;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import squid.squidgrid.map.BrickDungeon;
import squid.squidgrid.map.HerringboneDungeon;
import squid.squidgrid.map.RunningBondDungeon;
import squid.squidmath.RNG;
public class DungeonTest
{
    
    public static void main(String[] args)
    {
        BrickDungeon bd;
        HerringboneDungeon hbd;
        RunningBondDungeon rbd;
        byte[] seed = {15, 17, 1, 57, -100, -77, 78, 86, 66, -120, 42, 93, 19, 127, -127, -2};
        try
        {
            bd = new BrickDungeon(80, 30, new RNG(seed), new FileInputStream(new File("resources/centeredHoriz.txt")), new FileInputStream(new File("resources/centeredVert.txt")));
            hbd = new HerringboneDungeon(80, 30, new RNG(seed), new FileInputStream(new File("resources/centeredHoriz.txt")), new FileInputStream(new File("resources/centeredVert.txt")));
            rbd = new RunningBondDungeon(80, 30, new RNG(seed), new FileInputStream(new File("resources/centeredHoriz.txt")), new FileInputStream(new File("resources/centeredVert.txt")));
            System.out.println(hbd);
            System.out.println();
            System.out.println(bd);
            System.out.println();
            System.out.println(rbd);
            System.out.println();
        } catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
