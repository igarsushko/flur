package by.inhw.flur.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class LevelLoader
{
    public static int[][] loadLevel(String fileName)
    {
        List<String[]> valuesList = new ArrayList<String[]>();

        try
        {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);

            String line = null;
            while ((line = br.readLine()) != null)
            {
                String[] values = line.split("\\s");
                valuesList.add(values);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        int xMax = valuesList.get(0).length;
        int yMax = valuesList.size();

        int[][] map = new int[yMax][xMax];
        for (int y = 0; y < yMax; y++)
        {
            String[] values = valuesList.get(y);
            for (int x = 0; x < xMax; x++)
            {
                map[y][x] = Integer.parseInt(values[x]);
            }
        }

        return map;
    }
}
