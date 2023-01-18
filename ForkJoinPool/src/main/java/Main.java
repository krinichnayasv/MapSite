import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ForkJoinPool;


public class Main {
    static String urlSite = "https://metanit.com/";
    private  int l = 0;

    public static void main(String[] args) {

String start = "https://metanit.com/";

String dirSource = "data/mapSite.txt";

long startTime = System.currentTimeMillis();

        Node root = new Node(start, 0);
        CreateMapSite map = new CreateMapSite(root, start, 0);
        ForkJoinPool pool = new ForkJoinPool();
        String mapSite = pool.invoke(map);
          System.out.println(mapSite);
        System.out.println(System.currentTimeMillis() - startTime + " ms");
       //   System.out.println(root);


//       MapSiteWithoutNode maps = new MapSiteWithoutNode(start,start, 0);
//        ForkJoinPool pool = new ForkJoinPool();
//        String mapSite = pool.invoke(maps);
//        System.out.println(mapSite);


        try {
            Files.deleteIfExists(Paths.get(dirSource));
            Files.createFile(Path.of(dirSource));
            File file = new File(dirSource);

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(mapSite);
            writer.close();
            System.out.println("Файл записан в - " + dirSource);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }




}
