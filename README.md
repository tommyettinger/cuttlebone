# Cuttlebone
A library to help with game logic and map generation, for use with Java roguelike-style games.

## How to "Install"
[Declare cuttlebone as a dependency to download it from Maven Central](http://search.maven.org/#artifactdetails%7Ccom.github.tommyettinger%7Ccuttlebone%7C1.96.0%7Cjar). The one dependency of this project is Google GSON, and Maven (or any of the other tools that make use of Maven, like Gradle, SBT, Leiningen, and so on) should download that automatically.

## How to Use the Dungeon Generator
This prints a sample 80x80 dungeon in each of the predefined TilesetType styles.
```java
import squid.squidgrid.map.styled.*;
public class Example
{
    public static void main( String[] args )
    {
        DungeonGen dg = new DungeonGen();
        for(TilesetType tt : TilesetType.values())
        {
            dg.generate(tt, 80, 80);
            dg.wallWrap();
            System.out.println(dg + "\n");
        }
    }
}
```
