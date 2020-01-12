import org.junit.Test;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

public class MerkleTreeTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldFindMerkleRootHash_nullDatas() {
        // Given
        List<String> datas = null;

        // When
        new MerkleTree(datas);

        // Then => boom
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFindMerkleRootHash_emptyDatas() {
        // Given
        List<String> datas = Arrays.asList();

        // When
        new MerkleTree(datas);

        // Then => boom
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFindMerkleRootHash_onlyOneData() {
        // Given
        List<String> datas = Arrays.asList("salut");

        // When
        new MerkleTree(datas);

        // Then => boom
    }

    @Test
    public void shouldFindMerkleRootHash_evenNumberOfDatas() {
        // Given
        List<String> datas = Arrays.asList(
                "55d884171cc2c6863d1dbffbbf8c2baf8dfa28264df562c3f333bc4add422112",
                "57ef80ea79260a2a9b9351fab4bd2b1ad5f56672616e1520187c7d979683e02f",
                "460e312b24b68ec53b7de74c2713ad0915ae541735b5dbb8f81cc2e5e811534e",
                "b508e15089956bf647d366d67688384f89bd8c36718ca3c5dc4521e3dcd52542"
        );
        MerkleTree merkleTree = new MerkleTree(datas);

        // When
        byte[] merkleRootHash = merkleTree.getMerkleRootHash();
        int height = merkleTree.height();

        // Then
        assertEquals(6206150258827238818L, ByteBuffer.wrap(merkleRootHash).getLong());
        assertEquals(3, height);
    }

    @Test
    public void shouldFindMerkleRootHash_oddNumberOfDatas() {
        // Given
        List<String> datas = Arrays.asList(
                "55d884171cc2c6863d1dbffbbf8c2baf8dfa28264df562c3f333bc4add422112",
                "57ef80ea79260a2a9b9351fab4bd2b1ad5f56672616e1520187c7d979683e02f",
                "460e312b24b68ec53b7de74c2713ad0915ae541735b5dbb8f81cc2e5e811534e"
        );
        MerkleTree merkleTree = new MerkleTree(datas);

        // When
        byte[] merkleRootHash = merkleTree.getMerkleRootHash();
        int height = merkleTree.height();

        // Then
        assertEquals(-5688262724300107909L, ByteBuffer.wrap(merkleRootHash).getLong());
        assertEquals(3, height);
    }

    @Test
    public void shouldFindLevelHashs_evenNumberOfDatas() {
        // Given
        List<String> datas = Arrays.asList(
                "55d884171cc2c6863d1dbffbbf8c2baf8dfa28264df562c3f333bc4add422112",
                "57ef80ea79260a2a9b9351fab4bd2b1ad5f56672616e1520187c7d979683e02f",
                "460e312b24b68ec53b7de74c2713ad0915ae541735b5dbb8f81cc2e5e811534e",
                "b508e15089956bf647d366d67688384f89bd8c36718ca3c5dc4521e3dcd52542"
        );
        MerkleTree merkleTree = new MerkleTree(datas);

        // When
        List<byte[]> hashsLevel0 = merkleTree.level(0);

        // Then
        byte[] merkleRootHash = merkleTree.getMerkleRootHash();
        assertEquals(merkleRootHash, hashsLevel0.get(0));

        // When
        List<byte[]> hashsLevel1 = merkleTree.level(1);

        // Then
        assertEquals(2, hashsLevel1.size());
        assertEquals(8154470088966649001L, ByteBuffer.wrap(hashsLevel1.get(0)).getLong());
        assertEquals(3127898788141606122L, ByteBuffer.wrap(hashsLevel1.get(1)).getLong());

        // When
        List<byte[]> hashsLevel2 = merkleTree.level(2);

        // Then
        assertEquals(4, hashsLevel2.size());
        assertEquals(-5670818130490974115L, ByteBuffer.wrap(hashsLevel2.get(0)).getLong());
        assertEquals(-2101754992086116333L, ByteBuffer.wrap(hashsLevel2.get(1)).getLong());
        assertEquals(3924658723841340739L, ByteBuffer.wrap(hashsLevel2.get(2)).getLong());
        assertEquals(-1733361714412243994L, ByteBuffer.wrap(hashsLevel2.get(3)).getLong());
    }

    @Test
    public void shouldFindLevelHashs_oddNumberOfDatas() {
        // Given
        List<String> datas = Arrays.asList(
                "55d884171cc2c6863d1dbffbbf8c2baf8dfa28264df562c3f333bc4add422112",
                "57ef80ea79260a2a9b9351fab4bd2b1ad5f56672616e1520187c7d979683e02f",
                "460e312b24b68ec53b7de74c2713ad0915ae541735b5dbb8f81cc2e5e811534e",
                "b508e15089956bf647d366d67688384f89bd8c36718ca3c5dc4521e3dcd52542",
                "b508e15089956bf647d366d67688384f89bd8c36718ca3c5dc4521e3dcd52543"
        );
        MerkleTree merkleTree = new MerkleTree(datas);

        // When
        List<byte[]> hashsLevel0 = merkleTree.level(0);

        // Then
        byte[] merkleRootHash = merkleTree.getMerkleRootHash();
        assertEquals(merkleRootHash, hashsLevel0.get(0));

        // When
        List<byte[]> hashsLevel1 = merkleTree.level(1);

        // Then
        assertEquals(2, hashsLevel1.size());
        assertEquals(6206150258827238818L, ByteBuffer.wrap(hashsLevel1.get(0)).getLong());
        assertEquals(-4087230433851774205L, ByteBuffer.wrap(hashsLevel1.get(1)).getLong());

        // When
        List<byte[]> hashsLevel2 = merkleTree.level(2);

        // Then
        assertEquals(3, hashsLevel2.size());
        assertEquals(8154470088966649001L, ByteBuffer.wrap(hashsLevel2.get(0)).getLong());
        assertEquals(3127898788141606122L, ByteBuffer.wrap(hashsLevel2.get(1)).getLong());
        assertEquals(-4087230433851774205L, ByteBuffer.wrap(hashsLevel2.get(2)).getLong());

        // When
        List<byte[]> hashsLevel3 = merkleTree.level(3);

        // Then
        assertEquals(5, hashsLevel3.size());
        assertEquals(-5670818130490974115L, ByteBuffer.wrap(hashsLevel3.get(0)).getLong());
        assertEquals(-2101754992086116333L, ByteBuffer.wrap(hashsLevel3.get(1)).getLong());
        assertEquals(3924658723841340739L, ByteBuffer.wrap(hashsLevel3.get(2)).getLong());
        assertEquals(-1733361714412243994L, ByteBuffer.wrap(hashsLevel3.get(3)).getLong());
        assertEquals(-4087230433851774205L, ByteBuffer.wrap(hashsLevel3.get(4)).getLong());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException_indexOutOfBound() {
        // Given
        List<String> datas = Arrays.asList(
                "55d884171cc2c6863d1dbffbbf8c2baf8dfa28264df562c3f333bc4add422112",
                "57ef80ea79260a2a9b9351fab4bd2b1ad5f56672616e1520187c7d979683e02f");
        MerkleTree merkleTree = new MerkleTree(datas);

        // When
        merkleTree.level(3);
    }
}
