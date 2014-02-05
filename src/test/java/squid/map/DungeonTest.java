package squid.map;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import squid.squidgrid.map.BrickDungeon;
import squid.squidgrid.map.HerringboneDungeon;
public class DungeonTest
{
    
    public static void main(String[] args)
    {
        BrickDungeon hbd;
        try
        {
            hbd = new BrickDungeon(80, 30, new FileInputStream(new File("resources/centeredHoriz.txt")), new FileInputStream(new File("resources/centeredVert.txt")));
            System.out.println(hbd);
        } catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
