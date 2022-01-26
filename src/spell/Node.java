package spell;

public class Node implements INode{
    private int count;
    private Node[] childArray;

    public Node(){
        childArray = new Node[26];
        count = 0;
    }
    @Override
    public int getValue() {
        return count;
    }

    @Override
    public void incrementValue() {
        count++;
    }

    @Override
    public INode[] getChildren() {
        INode[] returnArray;
        returnArray = childArray;
        return returnArray;
    }

    public void insertNode(int index) {
        childArray[index] = new Node();
    }
}
