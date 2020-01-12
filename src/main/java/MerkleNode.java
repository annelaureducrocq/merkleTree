import com.google.common.primitives.Bytes;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MerkleNode {
    private static final String ALGORITHM = "SHA-256";

    private byte[] hash;
    private MerkleNode leftChild;
    private MerkleNode rightChild;

    public MerkleNode(String data) {
        this.hash = computeHash(data.getBytes(StandardCharsets.UTF_8));
    }

    public MerkleNode(MerkleNode leftChild, MerkleNode rightChild) {
        this.leftChild = leftChild;
        this.rightChild = rightChild;

        if (this.rightChild == null) {
            this.hash = this.leftChild.hash;
        } else {
            this.hash = computeHash(leftChild.hash, rightChild.hash);
        }
    }

    private byte[] computeHash(byte[] hashLeft, byte[] hashRight) {
        return computeHash(Bytes.concat(hashLeft, hashRight));
    }

    private byte[] computeHash(byte[] buffer) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(ALGORITHM + " is not a valid algorithm !", e);
        }
        return digest.digest(buffer);
    }

    public byte[] getHash() {
        return this.hash;
    }

    public MerkleNode getLeftChild() {
        return this.leftChild;
    }

    public MerkleNode getRightChild() {
        return this.rightChild;
    }

    public List<byte[]> level(int index) {
        if (index == 0) {
            return Arrays.asList(this.hash);
        }
        if (index == 1) {
            if (this.rightChild == null) {
                return Arrays.asList(this.leftChild.hash);
            }
            return Arrays.asList(this.leftChild.hash, this.rightChild.hash);
        }
        List<byte[]> leftHashs = this.leftChild.level(index - 1);
        if (this.rightChild == null) {
            return leftHashs;
        }
        List<byte[]> rightHashs = this.rightChild.level(index - 1);

        List<byte[]> hashs = new ArrayList<>(leftHashs);
        hashs.addAll(rightHashs);
        return hashs;
    }
}
