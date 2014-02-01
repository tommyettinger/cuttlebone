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
public class HerringboneDungeon
{
    private InputStream verticalStream = getClass().getResourceAsStream("/herringbonesVert.txt");
    private InputStream horizontalStream = getClass().getResourceAsStream("/herringbonesHoriz.txt");
    private Scanner vertScanner;
    public ArrayList<char[][]> herringbonesVert = new ArrayList<char[][]>(128);
    private Scanner horizScanner;
    public ArrayList<char[][]> herringbonesHoriz = new ArrayList<char[][]>(128);
    private char[][] shown;
    public int wide;
    public int high;
    
    private RNG rng;
    
    public HerringboneDungeon()
    {
        this(20, 80);
    }
    
    public HerringboneDungeon(int wide, int high)
    {
        this(wide, high, new RNG());
    }
    public HerringboneDungeon(int wide, int high, RNG random)
    {
        this(wide, high, random, null, null);
    }

    public HerringboneDungeon(int wide, int high, InputStream horizStream, InputStream vertStream)
    {
        this(wide, high, new RNG(), horizStream, vertStream);
    }
    public HerringboneDungeon(int wide, int high, RNG random, InputStream horizStream, InputStream vertStream)
    {
        if(horizStream == null)
            horizStream = horizontalStream;
        if(vertStream == null)
            vertStream = verticalStream;
        vertScanner = new Scanner(vertStream);
        vertScanner.useDelimiter("\n\n");
        horizScanner = new Scanner(horizStream);
        horizScanner.useDelimiter("\n\n");
        try {
            while (vertScanner.hasNext()) {
                String[] nx = vertScanner.next().split("\n");
                char[][] curr = new char[nx.length][nx[0].length()];
                for(int i = 0; i < nx.length; i++)
                {
                    curr[i] = nx[i].toCharArray();
                }
                herringbonesVert.add(curr);
            }
        } finally {
            if (vertScanner != null) {
                vertScanner.close();
            }
        }
        try {
            while (horizScanner.hasNext()) {
                String[] nx = horizScanner.next().split("\n");
                char[][] curr = new char[nx.length][nx[0].length()];
                for(int i = 0; i < nx.length; i++)
                {
                    curr[i] = nx[i].toCharArray();
                }
                herringbonesHoriz.add(curr);
            }
        } finally {
            if (horizScanner != null) {
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
        int nextFillX = 0;
        int nextFillY = 0;
        int startingIndent = 0;
        while ((nextFillX < wide + 40) && ((nextFillY < 30 + high)))
        {
            char[][] horiz = herringbonesHoriz.get(rng.between(0,
                    herringbonesHoriz.size() - 1));
            if ((nextFillX < 20 + wide) && ((nextFillY < 30 + high)))
            {
                for (int i = 0; i < 20; i++)
                {
                    for (int j = 0; j < 10; j++)
                    {
                        outer[nextFillX + i][nextFillY + j] = horiz[i][j];
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
            char[][] vert = herringbonesVert.get(rng.between(0,
                    herringbonesVert.size() - 1));
            if ((nextFillX < wide + 30) && ((nextFillY < high + 30)))
            {
                for (int i = 0; i < 10; i++)
                {
                    for (int j = 0; j < 20; j++)
                    {
                        outer[nextFillX + i][nextFillY + j] = vert[i][j];
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
                    shown[i][j] = outer[i + 20][j + 20];
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
        for(int x = 0; x < wide; x++)
        {
            for(int y = 0; y < high; y++)
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
        for(int x = 0; x < wide; x++)
        {
            for(int y = 0; y < high; y++)
            {
                this.shown[x][y] = shown[(y * wide) + x];
            }
        }
    }
    
    public String toString()
    {
        StringBuffer s = new StringBuffer("");
        
        for (int j = 0; j < shown[0].length; j++)
        {
            for (int i = 0; i < shown.length; i++)
            {
                s.append(shown[i][j]);
            }
            s.append('\n');
        }
        return s.toString();
    }
}
