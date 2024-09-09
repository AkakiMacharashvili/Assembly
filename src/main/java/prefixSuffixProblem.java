
import java.util.*;

public class prefixSuffixProblem {

    HashMap<String, List<String>> store;
    HashMap<String, Integer> inDegree;
    HashMap<String, Integer> outDegree;
    int k;
    String genome;

    public prefixSuffixProblem(int k, String genome) {
        this.k = k;
        this.genome = genome;
        store = new HashMap<>();
        inDegree = new HashMap<>();
        outDegree = new HashMap<>();
    }

    public StringBuilder doWork(){
        generate();
//        displayDeBruijnGraph();
        List<String> EulerianPath = findEulerianPath();
        if (EulerianPath != null) {
//            displayEulerianPath(EulerianPath);
            return reconstructGenome(EulerianPath);
        }

        return new StringBuilder();
    }

    private static void displayEulerianPath(List<String> EulerianPath) {
        for(int i = 0; i < EulerianPath.size(); i++){
            System.out.print(EulerianPath.get(i));
            if(i < EulerianPath.size() - 1) System.out.print(" -> ");
        }
        System.out.println();
    }

    // Part 1: Generate De Bruijn graph
    public void generate(){
        for(int i = 0; i < genome.length() - k + 1; i++){
            String source = genome.substring(i, i + k - 1);
            String destination = genome.substring(i + 1, i + k);
            store.putIfAbsent(source, new ArrayList<>());
            store.get(source).add(destination);

            outDegree.put(source, outDegree.getOrDefault(source, 0) + 1);
            inDegree.put(destination, inDegree.getOrDefault(destination, 0) + 1);
        }
    }

    // Display De Bruijn graph
    public void displayDeBruijnGraph(){
        for(var key : store.keySet()){
            List<String> current = store.get(key);
            System.out.print(key + " -> ");
            for(int i = 0; i < current.size(); i++){
                System.out.print(current.get(i));
                if(i < current.size() - 1){
                    System.out.print(" - ");
                }
            }
            System.out.println();
        }
    }

    // Part 2: Find the Eulerian path
    public List<String> findEulerianPath() {
        String startNode = null;
        String endNode = null;

        for (String node : store.keySet()) {
            int out = outDegree.getOrDefault(node, 0);
            int in = inDegree.getOrDefault(node, 0);
            if (out > in) {
                startNode = node;
            } else if (in > out) {
                endNode = node;
            }
        }

        if (startNode == null) {
            System.out.println("Eulerian path not possible.");
            return null;
        }

        // Hierholzer's algorithm
        Stack<String> stack = new Stack<>();
        List<String> path = new ArrayList<>();
        stack.push(startNode);

        while (!stack.isEmpty()) {
            String current = stack.peek();
            if (store.containsKey(current) && !store.get(current).isEmpty()) {
                String next = store.get(current).removeFirst();
                stack.push(next);
            } else {
                path.add(stack.pop());
            }
        }

        Collections.reverse(path);
        return path;
    }

    // Reconstructing genome
    public StringBuilder reconstructGenome(List<String> path) {
        StringBuilder reconstructedGenome = new StringBuilder(path.get(0));
        for (int i = 1; i < path.size(); i++) {
            reconstructedGenome.append(path.get(i).charAt(path.get(i).length() - 1));
        }
        return reconstructedGenome;
    }


}
