package tallyBook.bo;

public class ExtTree {
    private String id;

    private String text;

    private String cls;
    
    private boolean leaf;

    public boolean isLeaf() {
            return leaf;
    }

    public void setLeaf(boolean leaf) {
            this.leaf = leaf;
    }

    public String getCls() {
            return cls;
    }

    public void setCls(String cls) {
            this.cls = cls;
    }

    public String getId() {
            return id;
    }

    public void setId(String id) {
            this.id = id;
    }

    public String getText() {
            return text;
    }

    public void setText(String text) {
            this.text = text;
    }
}