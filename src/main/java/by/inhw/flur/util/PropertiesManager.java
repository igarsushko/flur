package by.inhw.flur.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesManager
{
    public static Properties load(String file)
    {
        Properties props = new Properties();
        try
        {
            FileInputStream in = new FileInputStream(file);
            props.load(in);
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return props;
    }
}
