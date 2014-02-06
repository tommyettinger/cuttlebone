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
    public ArrayList<char[][]> tilesVert = new ArrayList<char[][]>(128);
    private Scanner horizScanner;
    public ArrayList<char[][]> tilesHoriz = new ArrayList<char[][]>(128);
    private char[][] shown;
    public int wide;
    public int high;
    public boolean colorful;
    private Random rng;
    public static ArrayList<char[][]> tilesVertShared = null,
            tilesHorizShared = null;
    private void loadStreams(InputStream horizStream, InputStream vertStream)
    {
        if (horizStream == null) horizStream = getClass().getResourceAsStream(
                "/herringbonesHoriz.txt");
        if (vertStream == null) vertStream = getClass().getResourceAsStream(
                "/herringbonesVert.txt");
        vertScanner = new Scanner(vertStream);
        vertScanner.useDelimiter("\r?\n\r?\n");
        horizScanner = new Scanner(horizStream);
        horizScanner.useDelimiter("\r?\n\r?\n");
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
                tilesVert.add(curr);
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
                tilesHoriz.add(curr);
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
        if ((tilesVertShared == null && tilesVertShared == null) || (horizStream != null || vertStream != null))
        {
            loadStreams(horizStream, vertStream);
            tilesVertShared = tilesVert;
            tilesHorizShared = tilesHoriz;
        }
        this.colorful = colorful;
        
        this.wide = wide;
        this.high = high;
        rng = random;
        // char[][] base = herringbonesHoriz[rng.between(0,
        // herringbonesHoriz.length - 1)];
        char[][] outer = new char[wide + 50][high + 50];
        this.shown = new char[wide][high];
        for (int i = 0; i < wide + 50; i++)
        {
            for (int j = 0; j < high + 50; j++)
            {
                outer[i][j] = '#';
            }
        }
        int nextFillX = 0;
        int nextFillY = 0;
        int startingIndent = 0;
        while ((nextFillX < wide + 40) && ((nextFillY < 30 + high)))
        {
            char[][] horiz = tilesHorizShared.get(rng.nextInt(tilesHorizShared.size()));
            int randColor = (colorful) ? (random.nextInt(7)+ 1) * 128 : 0;
            if ((nextFillX < 20 + wide) && ((nextFillY < 30 + high)))
            {
                for (int i = 0; i < 20; i++)
                {
                    for (int j = 0; j < 10; j++)
                    {
                        outer[nextFillX + i][nextFillY + j] = (char) ((int) (horiz[i][j]) + randColor);
                    }
                }
            }
            int tempNextFill = nextFillX;
            if ((40 + nextFillX) % (wide + 40) < nextFillX)
            {
                switch (startingIndent)
                {
                case 0:
                    nextFillX = 10;
                    nextFillY += 10;
                    break;
                case 1:
                    nextFillX = 20;
                    nextFillY += 10;
                    break;
                case 2:
                    nextFillX = 30;
                    nextFillY += 10;
                    break;
                case 3:
                    nextFillX = 0;
                    nextFillY += 10;
                    break;
                }
            } else
            {
                nextFillX += 40;
            }
            if ((tempNextFill + 40) % (wide + 40) < tempNextFill)
            {
                startingIndent = (startingIndent + 1) % 4;
            }
        }
        startingIndent = 1;
        nextFillY = 10;
        nextFillX = 0;
        while (nextFillX <= wide + 30)
        {
            char[][] vert = tilesVertShared.get(rng.nextInt(tilesVertShared.size()));
            int randColor = (colorful) ? (random.nextInt(7) + 10) * 128 : 0;
            if ((nextFillX < wide + 30) && ((nextFillY < high + 30)))
            {
                for (int i = 0; i < 10; i++)
                {
                    for (int j = 0; j < 20; j++)
                    {
                        outer[nextFillX + i][nextFillY + j] = (char) ((int) (vert[i][j]) + randColor);
                    }
                }
            }
            int tempNextFill = nextFillY;
            if ((nextFillY + 40) % (high + 40) < nextFillY)
            {
                switch (startingIndent)
                {
                case 0:
                    nextFillX += 10;
                    nextFillY = 10;
                    break;
                case 1:
                    nextFillX += 10;
                    nextFillY = 20;
                    break;
                case 2:
                    nextFillX += 10;
                    nextFillY = 30;
                    break;
                case 3:
                    nextFillX += 10;
                    nextFillY = 0;
                    break;
                }
            } else
            {
                nextFillY += 40;
            }
            if ((tempNextFill + 40) % (high + 40) < tempNextFill)
            {
                startingIndent = (startingIndent + 1) % 4;
            }
        }
        for (int i = 0; i < wide; i++)
        {
            for (int j = 0; j < high; j++)
            {
                if (i == 0 || j == 0 || i == wide - 1 || j == high - 1)
                {
                    shown[i][j] = '#';
                } else
                {
                    shown[i][j] = outer[i + 25][j + 25];
                }
            }
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
                    if(currentColor != (30 + (shown[i][j] / 128)) && ((int)shown[i][j] / 128) != 0)
                    {
                        s.append("\u001B[0m\u001B[" + (30 + (shown[i][j] / 128)) + "m");
                        currentColor = (30 + (shown[i][j] / 128));
                    }
                    else if(((int)shown[i][j] / 128) == 0)
                    {
                        s.append("\u001B[0m");
                        currentColor = 0;
                    }
                    s.append((char) (shown[i][j] % 128));
                }
                else
                {
                    s.append(shown[i][j]);
                }
            }
            s.append('\n');
        }

        if (colorful)
            s.append("\u001B[0m");
        return s.toString();
    }
}
