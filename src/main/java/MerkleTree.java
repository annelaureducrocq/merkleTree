import java.util.List;
import java.util.ArrayList;

public class MerkleTree {
    private MerkleNode root;

    public MerkleTree(List<String> datas) {
        constructTree(datas);
    }

    private void constructTree(List<String> datas) {
        if (datas == null || datas.size() <= 1) {
            throw new IllegalArgumentException("Data blocks size has to be > 1.");
        }
        List<MerkleNode> parents = createLeaves(datas);
        while (parents.size() > 1) {
            parents = internalLevel(parents);
        }
        this.root = parents.get(0);
    }

    List<MerkleNode> internalLevel(List<MerkleNode> children) {
        List<MerkleNode> parents = new ArrayList<>(children.size() / 2);

        for (int i = 0; i < children.size() - 1; i += 2) {
            MerkleNode child1 = children.get(i);
            MerkleNode child2 = children.get(i + 1);

            MerkleNode parent = new MerkleNode(child1, child2);
            parents.add(parent);
        }

        if (children.size() % 2 != 0) {
            MerkleNode child = children.get(children.size() - 1);
            MerkleNode parent = new MerkleNode(child, null);
            parents.add(parent);
        }

        return parents;
    }

    private List<MerkleNode> createLeaves(List<String> datas) {
        List<MerkleNode> parents = new ArrayList<>();
        int dataSize = datas.size();
        for (int i = 0; i < dataSize - 1; i += 2) {
            MerkleNode leftLeaf = createLeaf(datas.get(i));
            MerkleNode rightLeaf = createLeaf(datas.get(i + 1));
            MerkleNode parent = createParent(leftLeaf, rightLeaf);
            parents.add(parent);
        }
        if (dataSize % 2 != 0) {
            MerkleNode leftLeaf = createLeaf(datas.get(dataSize - 1));
            MerkleNode parent = createParent(leftLeaf, null);
            parents.add(parent);
        }
        return parents;
    }

    private MerkleNode createParent(MerkleNode leftLeaf, MerkleNode rightLeaf) {
        return new MerkleNode(leftLeaf, rightLeaf);
    }

    private MerkleNode createLeaf(String data) {
        return new MerkleNode(data);
    }

    public byte[] getMerkleRootHash() {
        return this.root.getHash();
    }

    public int height() {
        int height = 1;
        MerkleNode node = root.getLeftChild();
        while (node != null) {
            node = node.getLeftChild();
            height++;
        }
        return height;
    }

    public List<byte[]> level(int index) {
        if (index >= this.height()) {
            throw new IllegalArgumentException("This Merkle tree has height " + height()
                    + ". There is no level + " + index);
        }
        return this.root.level(index);
    }
}
