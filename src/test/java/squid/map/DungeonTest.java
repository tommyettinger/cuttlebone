package squid.map;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

import squid.squidgrid.map.BrickDungeon;
import squid.squidgrid.map.BrickDungeon1D;
import squid.squidgrid.map.HerringboneDungeon;
import squid.squidgrid.map.RunningBondDungeon;
import squid.squidmath.RNG;

public class DungeonTest
{
    
    public static void main(String[] args)
    {
        BrickDungeon1D bd;
        BrickDungeon bd2;
        HerringboneDungeon hbd;
        RunningBondDungeon rbd;
        byte[] seed =
            {
                    15, 17, 1, 57, -100, -77, 78, 86, 66, -120, 42, 93, 19,
                    127, -127, -2
            };
        Random random = new Random();
        try
        {
            bd = new BrickDungeon1D(80, 24, random, new FileInputStream(
                    new File("resources/centeredVert.txt")),
                    new FileInputStream(new File("resources/centeredVert.txt")));
            bd2 = new BrickDungeon(80, 24, random, new FileInputStream(
                    new File("resources/centeredHoriz.txt")),
                    new FileInputStream(new File("resources/centeredVert.txt")));
            hbd = new HerringboneDungeon(
                    80,
                    24,
                    random,
                    new FileInputStream(new File("resources/centeredHoriz.txt")),
                    new FileInputStream(new File("resources/centeredVert.txt")));
            rbd = new RunningBondDungeon(
                    80,
                    24,
                    random,
                    new FileInputStream(new File("resources/centeredHoriz.txt")),
                    new FileInputStream(new File("resources/centeredVert.txt")));
            for (int i = 0; i < 100; i++)
            {
                
                bd = new BrickDungeon1D(80, 24, random, null, null);
                bd2 = new BrickDungeon(80, 24, random, null, null);
                hbd = new HerringboneDungeon(80, 24, random, null, null);
                rbd = new RunningBondDungeon(80, 24, random, null, null);
                //System.out.print(bd.get1DShown()[0] + hbd.get1DShown()[0] + rbd.get1DShown()[0]);
                bd = new BrickDungeon1D(39, 40, random, null, null);
                bd2 = new BrickDungeon(39, 40, random, null, null);
                hbd = new HerringboneDungeon(39, 40, random, null, null);
                rbd = new RunningBondDungeon(39, 40, random, null, null);
                //System.out.print(bd.get1DShown()[0] + hbd.get1DShown()[0] + rbd.get1DShown()[0]);
                bd = new BrickDungeon1D(133, 43, random, null, null);
                bd2 = new BrickDungeon(133, 43, random, null, null);
                hbd = new HerringboneDungeon(133, 43, random, null, null);
                rbd = new RunningBondDungeon(133, 43, random, null, null);
                //System.out.print(bd.get1DShown()[0] + hbd.get1DShown()[0] + rbd.get1DShown()[0]);
            }

            System.out.println(bd);
            System.out.println();
            System.out.println(bd2);
            System.out.println();
            /*
            System.out.println(hbd);
            System.out.println();
            System.out.println(rbd);
            System.out.println();*/
            
        } catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
