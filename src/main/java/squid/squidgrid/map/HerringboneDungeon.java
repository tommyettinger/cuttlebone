/**
 * 
 */
package squid.squidgrid.map;

//import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import squid.squidmath.RNG;

/**
 * @author Tommy Ettinger
 * 
 */
public class HerringboneDungeon
{
    private Scanner vertScanner;
    // public ArrayList<char[][]> tilesVert = new ArrayList<char[][]>(64);
    private Scanner horizScanner;
    // public ArrayList<char[][]> tilesHoriz = new ArrayList<char[][]>(64);
    private char[][] shown;
    public int wide;
    public int high;
    public boolean colorful;
    private Random rng;
    public static ArrayList<char[][]> tilesVertShared = null,
            tilesHorizShared = null;
    
    private void loadStreams(InputStream horizStream, InputStream vertStream)
    {
        if (horizStream == null)
            horizStream = getClass().getResourceAsStream(
                    "/herringbonesHoriz.txt");
        if (vertStream == null)
            vertStream = getClass()
                    .getResourceAsStream("/herringbonesVert.txt");
        vertScanner = new Scanner(vertStream);
        vertScanner.useDelimiter("\r?\n\r?\n");
        horizScanner = new Scanner(horizStream);
        horizScanner.useDelimiter("\r?\n\r?\n");
        tilesVertShared = new ArrayList<char[][]>(64);
        tilesHorizShared = new ArrayList<char[][]>(64);
        try
        {
            while (vertScanner.hasNext())
            {
                String[] nx = vertScanner.next().split("\r?\n");
                char[][] curr = new char[nx.length][nx[0].length()];
                for (int i = 0; i < nx.length; i++)
                {
                    curr[i] = nx[i].toCharArray();
                }
                tilesVertShared.add(curr);
            }
        } finally
        {
            if (vertScanner != null)
            {
                vertScanner.close();
            }
        }
        try
        {
            while (horizScanner.hasNext())
            {
                String[] nx = horizScanner.next().split("\r?\n");
                char[][] curr = new char[nx.length][nx[0].length()];
                for (int i = 0; i < nx.length; i++)
                {
                    curr[i] = nx[i].toCharArray();
                }
                tilesHorizShared.add(curr);
            }
        } finally
        {
            if (horizScanner != null)
            {
                horizScanner.close();
            }
        }
    }
    
    public HerringboneDungeon()
    {
        this(20, 80);
    }
    
    public HerringboneDungeon(int wide, int high)
    {
        this(wide, high, new RNG());
    }
    
    public HerringboneDungeon(int wide, int high, Random random)
    {
        this(wide, high, random, null, null);
    }
    
    public HerringboneDungeon(int wide, int high, InputStream horizStream,
            InputStream vertStream)
    {
        this(wide, high, new RNG(), horizStream, vertStream);
    }
    
    public HerringboneDungeon(int wide, int high, Random random,
            InputStream horizStream, InputStream vertStream)
    {
        this(wide, high, random, horizStream, vertStream, false);
    }
    
    public HerringboneDungeon(int wide, int high, Random random,
            InputStream horizStream, InputStream vertStream, boolean colorful)
    {
        if ((tilesVertShared == null && tilesVertShared == null)
                || (horizStream != null || vertStream != null))
        {
            loadStreams(horizStream, vertStream);
            // tilesVertShared = tilesVert;
            // tilesHorizShared = tilesHoriz;
        }
        this.colorful = colorful;
        
        this.wide = wide;
        this.high = high;
        rng = random;
        
        this.shown = new char[wide][high];
        
        int tileWidth = 10;
        int tileHeight = 20;
        
        int startX = 0;
        int startY = 0;
        while (startX < wide)
        {
            int x = startX;
            int y = -startY;
            while (y < high)
            {
                char[][] vert = tilesVertShared.get(rng.nextInt(tilesVertShared
                        .size()));
                int randColor = (colorful) ? (random.nextInt(7) + 10) * 128 : 0;
                for (int i = 0; i < 10; i++)
                {
                    if (x + i >= 0 && x + i < wide)
                    {
                        for (int j = 0; j < 20; j++)
                        {
                            if (y + j >= 0 && y + j < high)
                                shown[x + i][y + j] = (char) ((int) (vert[i][j]) + randColor);
                        }
                    }
                }
                x += tileWidth;
                
                char[][] horiz = tilesHorizShared.get(rng
                        .nextInt(tilesHorizShared.size()));
                randColor = (colorful) ? (random.nextInt(7) + 1) * 128 : 0;
                for (int i = 0; i < 20; i++)
                {
                    if (x + i >= 0 && x + i < wide)
                    {
                        for (int j = 0; j < 10; j++)
                        {
                            if (y + j >= 0 && y + j < high)
                                shown[x + i][y + j] = (char) ((int) (horiz[i][j]) + randColor);
                        }
                    }
                }
                y += tileWidth;
            }
            startX += tileHeight + tileWidth;
            startY += (tileHeight - tileWidth);
            startY %= 2 * tileHeight;
        }
        
        startX = tileHeight;
        startY = tileHeight;
        while (startY < high)
        {
            int x = -startX;
            int y = startY;
            while (y < high)
            {
                char[][] vert = tilesVertShared.get(rng.nextInt(tilesVertShared
                        .size()));
                int randColor = (colorful) ? (random.nextInt(7) + 10) * 128 : 0;
                for (int i = 0; i < 10; i++)
                {
                    if (x + i >= 0 && x + i < wide)
                    {
                        for (int j = 0; j < 20; j++)
                        {
                            if (y + j >= 0 && y + j < high)
                                shown[x + i][y + j] = (char) ((int) (vert[i][j]) + randColor);
                        }
                    }
                }
                x += tileWidth;
                
                char[][] horiz = tilesHorizShared.get(rng
                        .nextInt(tilesHorizShared.size()));
                randColor = (colorful) ? (random.nextInt(7) + 1) * 128 : 0;
                for (int i = 0; i < 20; i++)
                {
                    if (x + i >= 0 && x + i < wide)
                    {
                        for (int j = 0; j < 10; j++)
                        {
                            if (y + j >= 0 && y + j < high)
                                shown[x + i][y + j] = (char) ((int) (horiz[i][j]) + randColor);
                        }
                    }
                }
                y += tileWidth;
            }
            startY += tileHeight + tileWidth;
            startX += (tileHeight - tileWidth);
            startX %= 2 * tileHeight;
        }
        
        for (int i = 0; i < wide; i++)
        {
            shown[i][0] = '#';
            shown[i][high - 1] = '#';
            
        }
        
        for (int j = 0; j < high; j++)
        {
            shown[0][j] = '#';
            shown[wide - 1][j] = '#';
        }
    }
    
    public char[][] getShown()
    {
        return shown;
    }
    
    public char[] get1DShown()
    {
        char[] shown1D = new char[wide * high];
        for (int x = 0; x < wide; x++)
        {
            for (int y = 0; y < high; y++)
            {
                shown1D[(y * wide) + x] = shown[x][y];
            }
        }
        return shown1D;
    }
    
    public void setShown(char[][] shown)
    {
        this.wide = shown.length;
        this.high = (this.wide > 0) ? shown[0].length : 0;
        this.shown = shown;
    }
    
    public void set1DShown(char[] shown, int wide)
    {
        this.wide = wide;
        this.high = shown.length / wide;
        for (int x = 0; x < wide; x++)
        {
            for (int y = 0; y < high; y++)
            {
                this.shown[x][y] = shown[(y * wide) + x];
            }
        }
    }
    
    public String toString()
    {
        StringBuffer s = new StringBuffer("");
        int currentColor = 0;
        for (int j = 0; j < shown[0].length; j++)
        {
            for (int i = 0; i < shown.length; i++)
            {
                if (colorful)
                {
                    if (currentColor != (30 + (shown[i][j] / 128))
                            && ((int) shown[i][j] / 128) != 0)
                    {
                        s.append("\u001B[0m\u001B["
                                + (30 + (shown[i][j] / 128)) + "m");
                        currentColor = (30 + (shown[i][j] / 128));
                    } else if (((int) shown[i][j] / 128) == 0)
                    {
                        s.append("\u001B[0m");
                        currentColor = 0;
                    }
                    s.append((char) (shown[i][j] % 128));
                } else
                {
                    s.append(shown[i][j]);
                }
            }
            s.append('\n');
        }
        
        if (colorful) s.append("\u001B[0m");
        return s.toString();
    }
}
