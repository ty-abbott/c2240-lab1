package spell;

public class Trie implements ITrie {
    private Node node;
    private int wordCount;
    private int nodeCount;

    //toString
    //equals
    //hashcode
    public Trie() {
        node = new Node();
        wordCount = 0;
        nodeCount = 1;
    }

    @Override
    public void add(String word) {
        Node node2 = node;
        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';
            if (node2.getChildren()[index] == null) {
                node2.insertNode(index);
                nodeCount++;
            }
            node2 = (Node) node2.getChildren()[index];
        }
        if(node2.getValue() == 0) {
            this.wordCount++;
        }
        node2.incrementValue();



    }

    @Override
    public INode find(String word) {
        Node node2 = node;
        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';
            if (node2.getChildren()[index] == null) {
                return null;
            }
            node2 = (Node) node2.getChildren()[index];
            //System.out.println(node2.getValue());
        }
        if(node2.getValue() == 0) {
            return null;
        }
        return node2;
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    @Override
    public String toString() {
        StringBuilder currWord = new StringBuilder();
        StringBuilder output = new StringBuilder();
        toString_Helper(node, currWord, output);
        return output.toString();
    }

    private void toString_Helper(Node n, StringBuilder currWord, StringBuilder output) {
        if (n.getValue() > 0) {
            output.append(currWord.toString());
            output.append("\n");
        }
        for (int i = 0; i < 26; ++i) {
            INode child = n.getChildren()[i];
            if (child != null) {
                char childLetter = (char) ('a' + i);
                currWord.append(childLetter);
                toString_Helper((Node) child, currWord, output);
                currWord.deleteCharAt(currWord.length() - 1);
            }
        }
    }


    @Override
    public boolean equals(Object o) {
        if(o == null) {
            return false;
        }
        if(o == this) {
            return true;
        }
        if(o.getClass() != this.getClass()){
            return false;
        }

        Trie d = (Trie) o;
        if(this.getWordCount() != d.getWordCount() || this.getNodeCount() != d.getNodeCount()) {
            return false;
        }
        return equals_helper(this.node, d.node);
    }

    private boolean equals_helper(INode n1, INode n2) {
        if(n1 == null && n2 == null) {
            return true;
        }

        if (n1==null ^ n2 ==null) {
            return false;
        }

        if(n1.getValue() != n2.getValue()) {
            return false;
        }
        for (int i = 0; i < n1.getChildren().length; i++){
            if (n1.getChildren()[i] == null || n2.getChildren()[i] == null) {
                continue;
            }
            else if(n1.getChildren()[i].getValue() != n2.getChildren()[i].getValue()) {
                return false;
            }
        }

        INode n1Child = new Node();
        INode n2Child = new Node();

        for (int i = 0; i < n1.getChildren().length; i++) {
            n1Child = n1.getChildren()[i];
            n2Child = n2.getChildren()[i];
            if (!equals_helper(n1Child, n2Child)) {
                return false;
            }
        }
        return true;
    }

    private int getIndex() {
        int childrenNodeIndexTotal = 0;
        for (int i = 0; i < nodeCount; i++) {
            if(node.getChildren()[i] != null) {
                childrenNodeIndexTotal += i;
            }
        }
        return childrenNodeIndexTotal;
    }

    @Override
    public int hashCode() {
        return nodeCount + wordCount * getIndex();
    }

}
