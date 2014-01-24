package squid.map;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import squid.squidgrid.map.HerringboneDungeon;

public class HerringboneDungeonTest
{
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }
    
    @Before
    public void setUp() throws Exception
    {
    }
    
    @Test
    public void testHerringboneDungeon()
    {
        HerringboneDungeon hbd = new HerringboneDungeon();
        System.out.println(hbd);
        assertTrue(true);
    }
    
    @Test
    public void testHerringboneDungeonIntInt()
    {
        HerringboneDungeon hbd = new HerringboneDungeon(80,20);
        System.out.println(hbd);
        assertTrue(true);
    }
    
}
