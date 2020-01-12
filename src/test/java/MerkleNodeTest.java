import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.*;

public class MerkleNodeTest {

    @Test
    public void shouldCreateLeafNode() {
        // When
        MerkleNode merkleNode = new MerkleNode("55d884171cc2c6863d1dbffbbf8c2baf8dfa28264df562c3f333bc4add422112");

        // Then
        assertNull(merkleNode.getLeftChild());
        assertNull(merkleNode.getRightChild());
        assertEquals(-5670818130490974115L, ByteBuffer.wrap(merkleNode.getHash()).getLong());
    }

    @Test
    public void shouldCreateInternalNode() {
        // Given
        MerkleNode leftNode = new MerkleNode("55d884171cc2c6863d1dbffbbf8c2baf8dfa28264df562c3f333bc4add422112");
        MerkleNode rightNode = new MerkleNode("57ef80ea79260a2a9b9351fab4bd2b1ad5f56672616e1520187c7d979683e02f");

        // When
        MerkleNode merkleNode = new MerkleNode(leftNode, rightNode);

        // Then
        assertEquals(leftNode, merkleNode.getLeftChild());
        assertEquals(rightNode, merkleNode.getRightChild());
        assertEquals(8154470088966649001L, ByteBuffer.wrap(merkleNode.getHash()).getLong());
    }
}
