package tallyBook.bo;

public class ExtTreeNode extends ExtTree {
    private String parent;
     /**
     * 设置一个是否打印了的标志
     */
    private int f;
    
    public String getParent() {
            return parent;
    }

    public void setParent(String parent) {
            this.parent = parent;
    }

    public int getF() {
            return f;
    }

    public void setF(int f) {
            this.f = f;
    }
}