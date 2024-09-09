import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class prefixSuffixProblemTest {

    private prefixSuffixProblem problem;

    @Before
    public void setup() {
        // Sample genome and k value for testing
        String genome = "GATCACAGGTCTATCACCCTATTAACCACTCACGGGAGCTCTCCATGCATTTGGTATTTTCGTCTGGGGGGTGTGCACGCGATAGCATTGCGAGACGCTGGAGCCGGAGCACCCTATGTCGCAGTATCTGTCTTTGATTCCTGCCTCATTCTATTATTTATCGCACCTACGTTCAATATTACAGGCGAACATACCTACTAAAGTGTGTTAATTAATTAATGCTTGTAGGACATAATAATAACAATTGAAT";
        int k = 4;

        problem = new prefixSuffixProblem(k, genome);
    }

    @Test
    public void testGenerate() {
        problem.generate();

        assertFalse(problem.store.isEmpty());
        assertFalse(problem.inDegree.isEmpty());
        assertFalse(problem.outDegree.isEmpty());

        assertTrue(problem.store.containsKey("GAT"));
        assertTrue(problem.store.containsKey("ATC"));

        assertTrue(problem.inDegree.containsKey("ATC"));
        assertTrue(problem.outDegree.containsKey("CAC"));


        assertEquals(64, problem.store.size());  // Expected size of the De Bruijn graph
    }

    @Test
    public void testReconstructGenome() {
        prefixSuffixProblem prefixSuffixProblem = new prefixSuffixProblem(4, problem.genome);
        StringBuilder correctGenome = new StringBuilder(problem.genome);
        assertEquals(correctGenome.toString(), prefixSuffixProblem.doWork().toString());
    }

    @Test
    public void testDoWork() {
        problem.doWork();
    }
}
