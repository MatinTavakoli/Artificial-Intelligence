import java.util.ArrayList;

public class Graph{
    private static final int RED = 1;//color red
    public static final int YELLOW = 2;//color yellow
    private static final int GREEN = 3;//color green
    private static final int BLUE = 4;//color blue
    private ArrayList<String> vertices;//set of vertices
    private ArrayList<String[]> edges;//from one vertice to another vertice
    private ArrayList<Integer> degrees;//degree correspondent to that node
    private ArrayList<Integer> colors;//colors correspondent to that node

    public Graph(ArrayList<String> vertices,ArrayList<String[]> edges){
        this.vertices = vertices;
        this.edges = edges;
        this.degrees = new ArrayList<>();
        calculate_degrees();
    }

    public ArrayList<String> get_vertices(){
        return vertices;
    }

    public ArrayList<String[]> get_edges(){
        return edges;
    }

    public ArrayList<Integer> get_colors(){
        return colors;
    }

    public void set_edges(ArrayList<String[]> edges){
        this.edges = edges;
    }

    public void set_colors(ArrayList<Integer> colors){//this method will be used in the genetic & annealing algorithtms
        this.colors = colors;
    }

    private void calculate_degrees(){//this method is only accessed once from within the constructor!
        //initializing all degrees with 0
        for (int i = 0; i < vertices.size(); i++) {
            degrees.add(0);//initial value for all vertices
        }

        for (int i = 0; i < edges.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                if(edges.get(i)[0].equals(vertices.get(j)) || edges.get(i)[1].equals(vertices.get(j)))
                    degrees.set(j, degrees.get(j) + 1);//incrementing vertice i's degree!
            }
        }
    }

    public void show_vertices_and_edges(){
        System.out.println("vertices are: ");
        for (int i = 0; i < vertices.size(); i++) {
            System.out.print(vertices.get(i) + " with degree " + degrees.get(i) + " ");
        }
        System.out.println();

        System.out.println("edges are: ");
        for (int i = 0; i < edges.size(); i++) {
            System.out.print("from " + edges.get(i)[0] + " to " + edges.get(i)[1] + " ");
        }
        System.out.println();
    }

    public void show_colors(){
        for (int i = 0; i < vertices.size(); i++) {
            System.out.print(vertices.get(i) + " with color " + colors.get(i) + " ");
        }
        System.out.println();
    }

    @Override
    public Graph clone() {
        Graph newGraph = new Graph(vertices, edges);
        newGraph.vertices = (ArrayList<String>)vertices.clone();
        newGraph.edges = (ArrayList<String[]>)edges.clone();
        newGraph.colors = (ArrayList<Integer>)colors.clone();
        return newGraph;
    }
}