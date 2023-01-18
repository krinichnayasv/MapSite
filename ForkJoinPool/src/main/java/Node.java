import java.util.ArrayList;

public class Node {

    private String url;
    private ArrayList<Node> children;
    private int level;
    private String site;

    public Node (String url, int level)    {
        this.url = url;
        children = new ArrayList<>();
        this.level = level;
    }

    public void addChild (Node node) {
        node.setLevel(level + 1);
        children.add(node);
    }

    private void setLevel(int level) {
        this.level = level;
    }


    public int getLevel() {
        return level;
    }

    public String getSite() {
        return site;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }


    public void setSite(String site) {
        this.site = site;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String toString () {

        StringBuilder builder = new StringBuilder();
        builder.append(url + "\n");
        for (Node child : children) {
            {
                int l = child.getLevel();
                builder.append("  ".repeat(l + 1) + child.getUrl() + "\n");
            }
        }

        return builder.toString();
    }


}
