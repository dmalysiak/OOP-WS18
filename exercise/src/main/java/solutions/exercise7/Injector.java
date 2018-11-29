package solutions.exercise7;

import java.io.*;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;

public class Injector {

    private final static String SEPERATOR = ";";
    private final static Injector INSTANCE = new Injector();

    private Injector(){}

    public static Injector getInstance()
    {
        return INSTANCE;
    }

    public World getWorld(InputStream config) throws Exception
    {
        List<Entity> entities = getEntities(config);
        World w = new World();
        w.setE1(entities.get(0));
        w.setE2(entities.get(1));
        w.setE3(entities.get(2));

        return w;
    }
    public void getWorld(World w, InputStream config) throws Exception
    {
        List<Entity> entities = getEntities(config);
        w.setE1(entities.get(0));
        w.setE2(entities.get(1));
        w.setE3(entities.get(2));
    }

    private List<Entity> getEntities(InputStream config) throws Exception
    {
        List<Entity> list = new ArrayList<>();

        Reader fr = new InputStreamReader(config, "utf-8");
        BufferedReader br = new BufferedReader(fr);
        String line = null;

        while((line = br.readLine()) != null)
        {
            String[] subs = line.split(SEPERATOR);
            if(subs.length != 2)
            {
                throw new IllegalArgumentException();
            }
            list.add(constructEntity(subs[0],subs[1]));
        }

        return list;
    }

    private Entity constructEntity(String clazz, String name) throws Exception
    {
        Class cls = Class.forName(clazz);
        Entity entity = (Entity)cls.newInstance();
        entity.setName(name);

        return entity;
    }
}
