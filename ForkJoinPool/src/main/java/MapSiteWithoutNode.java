import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

public class MapSiteWithoutNode  extends RecursiveTask<String> {


    private String urlStart;
    private String url;
    private ArrayList<String> gettedSites = new ArrayList<>();
    private int l = 0;

    public MapSiteWithoutNode(String url, int l) {
        this.url = url;
        this.l = l;
    }

    public MapSiteWithoutNode(String url, String urlStart, int l) {
        this.url = url;
        this.l = l;
        this.urlStart = urlStart;

    }


    @Override
    protected String compute() {
        StringBuffer sb = new StringBuffer(url + "\n");

        Set<MapSiteWithoutNode> subTask = new HashSet<>();

        getChildren(subTask);

        for (MapSiteWithoutNode task : subTask) {
            sb.append(task.join());
        }

        return sb.toString();
    }


    private void getChildren(Set<MapSiteWithoutNode> subTask) {

            int delay = new Random().nextInt(150, 250);
        try {
            Thread.sleep(delay);
            Document document = Jsoup.connect(url).get();
            Elements elements = document.select("a");
            for(Element el : elements) {
               String site = el.attr("abs:href");
                if (!site.isEmpty() && site.startsWith(urlStart) && !site.contains("#")
                        && !site.matches(urlStart) && !site.matches(url) && !gettedSites.contains(site))
                {
                    MapSiteWithoutNode mapSite = new MapSiteWithoutNode(site, l);
                    mapSite.fork();
                    subTask.add(mapSite);
                    gettedSites.add(site);
                }
            }

        } catch (Exception ex) {ex.getStackTrace();}

    }



}


