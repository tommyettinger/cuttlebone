/**
 * 
 */
package squid.squidgrid.map;

//import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import squid.squidmath.RNG;

/**
 * @author Tommy Ettinger
 * 
 */
public class RunningBondDungeon
{
    private InputStream verticalStream = getClass().getResourceAsStream(
            "/centeredVert.txt");
    private InputStream horizontalStream = getClass().getResourceAsStream(
            "/centeredHoriz.txt");
    private Scanner vertScanner;
    public ArrayList<char[][]> tilesVert = new ArrayList<char[][]>(128);
    private Scanner horizScanner;
    public ArrayList<char[][]> tilesHoriz = new ArrayList<char[][]>(128);
    private char[][] shown;
    public int wide;
    public int high;
    public boolean colorful;
    private RNG rng;
    
    public RunningBondDungeon()
    {
        this(20, 80);
    }
    
    public RunningBondDungeon(int wide, int high)
    {
        this(wide, high, new RNG());
    }
    
    public RunningBondDungeon(int wide, int high, RNG random)
    {
        this(wide, high, random, null, null);
    }
    
    public RunningBondDungeon(int wide, int high, InputStream horizStream,
            InputStream vertStream)
    {
        this(wide, high, new RNG(), horizStream, vertStream);
    }
    
    public RunningBondDungeon(int wide, int high, RNG random,
            InputStream horizStream, InputStream vertStream)
    {
        this(wide, high, random, horizStream, vertStream, false);
    }
    
    public RunningBondDungeon(int wide, int high, RNG random,
            InputStream horizStream, InputStream vertStream, boolean colorful)
    {
        if (horizStream == null) horizStream = horizontalStream;
        if (vertStream == null) vertStream = verticalStream;
        this.colorful = colorful;
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
        
        this.wide = wide;
        this.high = high;
        rng = random;
        // char[][] base = herringbonesHoriz[rng.between(0,
        // herringbonesHoriz.length - 1)];
        char[][] outer = new char[wide + 40][high + 40];
        this.shown = new char[wide][high];
        for (int i = 0; i < wide + 40; i++)
        {
            for (int j = 0; j < high + 40; j++)
            {
                outer[i][j] = '#';
            }
        }
        int nextFillX, startingFillX, startingDedent;
        startingDedent = nextFillX = startingFillX = (wide / 2) + 0;
        int nextFillY, startingFillY;
        nextFillY = startingFillY = (high / 2) + 10;
        int numToFill = 1;
        int filled = 0;
        while ((nextFillY > 0))
        {
            if ((filled < numToFill) && (nextFillX < 40 + wide) && (nextFillX > 0) && (nextFillY > 0))
            {

                char[][] horiz = tilesHoriz.get(rng.between(0,
                        tilesHoriz.size() - 1));
                int randColor = (colorful) ? random.between(1, 7) * 128 : 0;
                for (int i = 0; i < 20; i++)
                {
                    for (int j = 0; j < 10; j++)
                    {
                        outer[nextFillX + i][nextFillY + j] = (char) ((int) (horiz[i][j]) + randColor);
                    }
                }
                horiz = tilesHoriz.get(rng.between(0,
                        tilesHoriz.size() - 1));
                randColor = (colorful) ? random.between(1, 7) * 128 : 0;
                for (int i = 0; i < 20; i++)
                {
                    for (int j = 0; j < 10; j++)
                    {
                        outer[nextFillX + i][high + 10 - nextFillY + j] = (char) ((int) (horiz[i][j]) + randColor);
                    }
                }
            }
            if ((20 + nextFillX) % (wide + 30) < nextFillX)
            {
                nextFillY -= 10;
                startingDedent = (startingDedent - 10);
                nextFillX = startingDedent;
                numToFill++;
                filled = 0;
            } else
            {
                nextFillX += 20;
                filled++;
            }
        }

        nextFillX = startingFillX = (wide / 2) - 10;
        startingDedent = nextFillY = startingFillY = (high / 2) + 10;
        
        numToFill = 1;
        filled = 0;
        while ((nextFillX > 0))
        {
            if ((filled < numToFill) && (nextFillY < 30 + high) && (nextFillX > 0) && (nextFillY > 0))
            {

                char[][] vert = tilesVert.get(rng.between(0,
                        tilesVert.size() - 1));
                int randColor = (colorful) ? random.between(10, 16) * 128 : 0;
                for (int i = 0; i < 10; i++)
                {
                    for (int j = 0; j < 20; j++)
                    {
                        outer[nextFillX + i][nextFillY + j] = (char) ((int) (vert[i][j]) + randColor);
                    }
                }
                vert = tilesVert.get(rng.between(0,
                        tilesVert.size() - 1));
                randColor = (colorful) ? random.between(10, 16) * 128 : 0;
                for (int i = 0; i < 10; i++)
                {
                    for (int j = 0; j < 20; j++)
                    {
                        outer[wide + 10 - nextFillX + i][nextFillY + j] = (char) ((int) (vert[i][j]) + randColor);
                    }
                }
            }
            if ((20 + nextFillY) % (high + 20) < nextFillY)
            {
                nextFillX -= 10;
                startingDedent = (startingDedent - 10);
                nextFillY = startingDedent;
                numToFill++;
                filled = 0;
            } else
            {
                nextFillY += 20;
                filled++;
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
                    shown[i][j] = outer[i + 10][j + 10];
                }
            }
        }
        try
        {
            horizStream.close();
            vertStream.close();
        } catch (IOException e)
        {
            e.printStackTrace();
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
