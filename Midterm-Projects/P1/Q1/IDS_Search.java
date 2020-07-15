import java.util.ArrayList;

public class IDS_Search{
    private Cube cube;
    private ArrayList<String> frontier;
    private ArrayList<String> explored_set;
    private int depth;
    public IDS_Search(Cube cube, int depth){
        this.cube = cube;
        this.depth = depth;
        frontier = new ArrayList<>();
        explored_set = new ArrayList<>();
    }

    public String simulate(){
        System.out.println("Simulating IDS Search...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = depth;; i++) {
            DLS_Search dls_search = new DLS_Search(cube, i);
            String result = dls_search.simulate(cube, i);
            System.out.println("explored set is: ");
            for (int j = 0; j < dls_search.get_explored_set().size(); j++) {
                System.out.println(dls_search.get_explored_set().get(j));
            }
            System.out.println("created nodes(frontier + explored set) size is: " + dls_search.get_explored_set().size());
            System.out.println("expanded nodes(explored set) size is: " + dls_search.get_explored_set().size());
            if (!result.equals("cutoff")){
                System.out.println("IDS stopped at depth: " + i);
                return result;
            }
        }
    }
}