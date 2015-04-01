package squid.squidgrid.map;

/**
 * Created by Tommy Ettinger on 4/1/2015.
 */
public class DungeonUtils {
    public static char[][] HashesToLines(char[][] map)
    {
        int Width = map[0].length+2;
        int Height = map.length+2;
        
        char[][] neo = new char[Height][Width], dungeon = new char[Height][Width];
        for(int i = 1; i < Height - 1; i++)
        {
            for(int j = 1; j < Width - 1; j++)
            {
                dungeon[i][j] = map[i-1][j-1];
            }
        }
            for(int i = 0; i < Height; i++)
        {
            neo[i][0] = '\1';
            neo[i][Width-1] = '\1';
            dungeon[i][0] = '\1';
            dungeon[i][Width-1] = '\1';
        }
        for(int i = 0; i < Width; i++)
        {
            neo[0][i] = '\1';
            neo[Height-1][i] = '\1';
            dungeon[0][i] = '\1';
            dungeon[Height-1][i] = '\1';
        }

        for (int y = 1; y < Height - 1; y++)
        {
            for (int x = 1; x < Width - 1; x++)
            {
                if (map[y-1][x-1] == '#')
                {
                    int q = 0;
                    q |= (y <= 1 || map[y - 2][x-1] == '#') ? 1 : 0;
                    q |= (y <= 1 || x >= Width - 2 || map[y - 2][x + 0] == '#') ? 2 : 0;
                    q |= (x >= Width - 2  || map[y - 1][x + 0] == '#') ? 4 : 0;
                    q |= (y >= Height - 2 || x >= Width - 2 || map[y + 0][x + 0] == '#') ? 8 : 0;
                    q |= (y >= Height - 2 || map[y + 0][x-1] == '#') ? 16 : 0;
                    q |= (y >= Height - 2 || x <= 1 || map[y + 0][x - 2] == '#') ? 32 : 0;
                    q |= (x <= 1 || map[y - 1][x - 2] == '#') ? 64 : 0;
                    q |= (y <= 1 || x <= 1 || map[y - 2][x - 2] == '#') ? 128 : 0;

                    if (q == 0xff)
                    {
                        neo[y][x] = '\1';
                        dungeon[y][x] = '\1';
                    }
                    else
                    {
                        neo[y][x] = '#';
                    }
                }
                else
                {
                    neo[y][x] = dungeon[y][x];
                }
            }
        }

        for (int y = 0; y < Height; y++)
        {
            for (int x = 0; x < Width; x++)
            {
                if (dungeon[y][x] == '#')
                {
                    boolean n = (y <= 0 || dungeon[y - 1][x] == '#');
                    boolean e = (x >= Width - 1 || dungeon[y][x + 1] == '#');
                    boolean s = (y >= Height - 1 || dungeon[y + 1][x] == '#');
                    boolean w = (x <= 0 || dungeon[y][x - 1] == '#');

                    if (n)
                    {
                        if (e)
                        {
                            if (s)
                            {
                                if (w)
                                {
                                    neo[y][x] = '┼';
                                }
                                else
                                {
                                    neo[y][x] = '├';
                                }
                            }
                            else if (w)
                            {
                                neo[y][x] = '┴';
                            }
                            else
                            {
                                neo[y][x] = '└';
                            }
                        }
                        else if (s)
                        {
                            if (w)
                            {
                                neo[y][x] = '┤';
                            }
                            else
                            {
                                neo[y][x] = '│';
                            }
                        }
                        else if (w)
                        {
                            neo[y][x] = '┘';
                        }
                        else
                        {
                            neo[y][x] = '│';
                        }
                    }
                    else if (e)  // ┼ ├ ┤ ┴ ┬ ┌ ┐ └ ┘ │ ─
                    {
                        if (s)
                        {
                            if (w)
                            {
                                neo[y][x] = '┬';
                            }
                            else
                            {
                                neo[y][x] = '┌';
                            }
                        }
                        else if (w)
                        {
                            neo[y][x] = '─';
                        }
                        else
                        {
                            neo[y][x] = '─';
                        }
                    }
                    else if (s)
                    {
                        if (w)
                        {
                            neo[y][x] = '┐';
                        }
                        else
                        {
                            neo[y][x] = '│';
                        }
                    }
                    else if (w)
                    {
                        neo[y][x] = '─';
                    }
                    else
                    {
                        neo[y][x] = '─';
                    }

                }
                else
                {
                        neo[y][x] = dungeon[y][x];
                }
            }
        }
        //vertical crossbar removal
        for (int y = 1; y < Height; y++)
        {
            for (int x = 0; x < Width; x++)
            {
                // ┼ ├ ┤ ┴ ┬ ┌ ┐ └ ┘ │ ─
                if (neo[y][x] == '┼' || neo[y][x] == '├' || neo[y][x] == '┤' || neo[y][x] == '┴')
                {
                    if (neo[y - 1][x] == '┼' || neo[y - 1][x] == '├' || neo[y - 1][x] == '┤' || neo[y - 1][x] == '┬')
                    {
                        if ((x >= Width - 1 || dungeon[y - 1][x + 1] == '#' || dungeon[y - 1][x + 1] == '\1') &&
                        (x <= 0 || dungeon[y - 1][x - 1] == '#' || dungeon[y - 1][x - 1] == '\1') &&
                        (x >= Width - 1 || dungeon[y][x + 1] == '#' || dungeon[y][x + 1] == '\1') &&
                        (x <= 0 || dungeon[y][x - 1] == '#' || dungeon[y][x - 1] == '\1'))
                        {
                            switch (neo[y][x])
                            {
                                case '┼':
                                    neo[y][x] = '┬';
                                    break;
                                case '├':
                                    neo[y][x] = '┌';
                                    break;
                                case '┤':
                                    neo[y][x] = '┐';
                                    break;
                                case '┴':
                                    neo[y][x] = '─';
                                    break;
                            }
                            switch (neo[y - 1][x])
                            {
                                case '┼':
                                    neo[y - 1][x] = '┴';
                                    break;
                                case '├':
                                    neo[y - 1][x] = '└';
                                    break;
                                case '┤':
                                    neo[y - 1][x] = '┘';
                                    break;
                                case '┬':
                                    neo[y - 1][x] = '─';
                                    break;

                            }
                        }
                    }
                }
            }
        }
        //horizontal crossbar removal
        for (int y = 0; y < Height; y++)
        {
            for (int x = 1; x < Width; x++)
            {
                // ┼ ├ ┤ ┴ ┬ ┌ ┐ └ ┘ │ ─
                if (neo[y][x] == '┼' || neo[y][x] == '┤' || neo[y][x] == '┬' || neo[y][x] == '┴')
                {
                    if (neo[y][x - 1] == '┼' || neo[y][x - 1] == '├' || neo[y][x - 1] == '┬' || neo[y][x - 1] == '┴')
                    {
                        if ((y >= Height - 1 || x >= Width - 1 || dungeon[y + 1][x - 1] == '#' || dungeon[y + 1][x - 1] == '\1') &&
                        (y <= 0 || dungeon[y - 1][x - 1] == '#' || dungeon[y - 1][x - 1] == '\1') &&
                        (y >= Height - 1 || dungeon[y + 1][x] == '#' || dungeon[y + 1][x] == '\1') &&
                        (y <= 0 || dungeon[y - 1][x] == '#' || dungeon[y - 1][x] == '\1'))
                        {
                            switch (neo[y][x])
                            {
                                case '┼':
                                    neo[y][x] = '├';
                                    break;
                                case '┤':
                                    neo[y][x] = '│';
                                    break;
                                case '┬':
                                    neo[y][x] = '┌';
                                    break;
                                case '┴':
                                    neo[y][x] = '└';
                                    break;
                            }
                            switch (neo[y][x - 1])
                            {
                                case '┼':
                                    neo[y][x - 1] = '┤';
                                    break;
                                case '├':
                                    neo[y][x - 1] = '│';
                                    break;
                                case '┬':
                                    neo[y][x - 1] = '┐';
                                    break;
                                case '┴':
                                    neo[y][x - 1] = '┘';
                                    break;

                            }
                        }
                    }
                }
            }
        }
        char[][] portion = new char[Height-2][Width-2];
        for(int i = 1; i < Height - 1; i++)
        {
            for(int j = 1; j < Width - 1; j++)
            {
                if(neo[i][j] == '\1')
                    portion[i-1][j-1] = ' ';
                else
                    portion[i-1][j-1] = neo[i][j];
            }
        }
        return portion;
    }
}
