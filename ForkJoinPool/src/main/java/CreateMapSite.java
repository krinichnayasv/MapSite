import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

public class CreateMapSite extends RecursiveTask <String> {


    private static String urlStart;
    private Node node;
    private String url;
    private static ArrayList<String> gettedSites = new ArrayList<>();
    private int l = 0;

    public CreateMapSite(Node node, int l) {
        this.node = node;
        this.l = l;
    }

    public CreateMapSite(Node node, String urlStart, int l) {
        this.node = node;
        this.l = l;
        this.urlStart = urlStart;

    }

    @Override
    protected String compute() {

        int delay = new Random().nextInt(150, 250);

        // создаем лист задач
        List<CreateMapSite> subTasks = new LinkedList<>();
        url = node.getUrl().trim();
        // для каждого адреса сразу добавляются в строку данные
        StringBuffer sb = new StringBuffer("    ".repeat(l) + url + "\n");

     //   System.out.println(sb);
        // достаем все дочерние задачи для каждого сайта:
        try {
            Thread.sleep(delay);
            Document document = Jsoup.connect(url).get();
            Elements elements = document.select("a");
            // для каждого найденного элемента переданного сюда адреса:
            for (Element el : elements) {
                String site = el.attr("abs:href");
                // идет проверка по заданию + проверка на повтор
                if (!site.isEmpty() && site.startsWith(urlStart) && !site.contains("#")
                        && !site.matches(urlStart) && !site.matches(url) && !gettedSites.contains(site))
                {   //   System.out.println("site: " + site);
                    //  если сайт подходит - формируется задача и дочерняя нода
                    Node child = new Node(site.trim(), node.getLevel());
                    // дочерняя нода при передаче в этот класс будет иметь на 1 сдвиг табуляции больше
                    l = node.getLevel() + 1;
                    // создается задача для дочерней ноды
                    CreateMapSite task = new CreateMapSite(child, l);
                    // создали отдельную задачу для каждой ссылки?
                    task.fork(); // запуск асинхронно
                    subTasks.add(task); // добавили задачу в лист
                    node.addChild(child); // добавили дочернюю ноду
                    gettedSites.add(site.trim()); // добавили сайт в список, чтобы отслеживать задвоение
                }
            }

        } catch (Exception ex) {
            ex.getStackTrace();
        }

        // теперь все дочерние задачи должны по идее пройти через этот же метод, и таким образом - по циклу все перебрать
        // но почему-то ничего не идет циклом,  а останавливается на первом поколении сайтов.
        for (CreateMapSite task : subTasks) {
            sb.append(task.join());
        }

      // sb.append(ForkJoinTask.invokeAll(subTasks).stream().map(ForkJoinTask::join).collect(Collectors.joining()));



        return  sb.toString();

    }




}



