import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;

public class Annealing{
    private Graph graph;
    private ArrayList<Graph> neighbours;
    private double t0;//initial temperature
    private double t;// current temperature
    private double alpha;//The coeffecient used for temperature cooling
    private static final int EXPONENTIAL_COOLING = 1;
    private static final int LOGARITHMIC_COOLING = 2;
    private static final int NEGATIVE_ONE_ORDER_COOLING = 3;
    private static final int NEGATIVE_TWO_ORDER_COOLING = 4;
    private int mode;
    private int round = 0;//number of the round
    public Annealing(Graph graph, double t0, double alpha, int mode){
        this.graph = graph;
        this.t0 = t0;
        this.t = this.t0;//current temperature is initialized with initial temperature
        this.alpha = alpha;
        switch(mode){
            case 1:
                this.mode = EXPONENTIAL_COOLING;
                break;
            case 2:
                this.mode = LOGARITHMIC_COOLING;
                break;
            case 3:
                this.mode = NEGATIVE_ONE_ORDER_COOLING;
                break;
            case 4:
                this.mode = NEGATIVE_TWO_ORDER_COOLING;
                break;
        }
        color_graph(graph);
        while(this.fitness_function(graph) < 1.0 && t >= 0.0001 ){//we haven't reached the goal or we will stay in our current state forever!
            System.out.println("initial graph fit: " + this.fitness_function(graph));
            System.out.println("round #" + (round));
            neighbours = find_neighbours();

            for (int i = 0; i < neighbours.size(); i++) {
                System.out.println(this.fitness_function(neighbours.get(i)));
            }

            int rand_neighbour = (Math.abs(new Random().nextInt())) % neighbours.size();//selecting a random node
            if(this.fitness_function(neighbours.get(rand_neighbour)) > this.fitness_function(graph)){
                System.out.println("neighbour #" + (rand_neighbour + 1) + "  has better fitness function than the initial graph");//definite transition to neighbour
                this.graph = neighbours.get(rand_neighbour).clone();//a neighbour which is better
            }
            else{
                System.out.println("neighbour #" + (rand_neighbour + 1) + " doesn't have better fitness function than the initial graph");//probable transition to neighbour
                int rand_probability = (Math.abs(new Random().nextInt())) % 100;
                double delta_e = this.fitness_function(neighbours.get(rand_neighbour)) - this.fitness_function(graph);
                System.out.println("probability is: " + Math.pow(Math.E, (delta_e / t)));
                if(rand_probability < Math.pow(Math.E, (delta_e / t)) * 100){//the bad neighbour is selected
                    System.out.println("bad neighbour selected by probability");
                    this.graph = neighbours.get(rand_neighbour).clone();
                }
                else{//the bad neighbour isn't selected
                    System.out.println("rand probability is: " + rand_probability);
                    System.out.println("bad neighbour ignored by probability");
                }  
    
            }

            round++;
            System.out.println("t is: " + t);
            t = change_temperature(alpha, round, mode);
        }
        System.out.println("annealing finished in " + round + " rounds");
        System.out.println("current fitness is: " + this.fitness_function(graph));
    }

    private void color_graph(Graph graph){
        ArrayList<Integer> colors_list = new ArrayList<>();
        for (int i = 0; i < graph.get_vertices().size(); i++) {
            int rand_color = (Math.abs(new Random().nextInt())) % 4 + 1;
            colors_list.add(rand_color);
        }
        graph.set_colors(colors_list);
        graph.show_colors();
    }

    private double fitness_function(Graph graph){//this method is accessed only from within the class!
        double fit = 0;
        for (int i = 0; i < graph.get_edges().size(); i++) {//iterating on edges
            if(graph.get_colors().get(graph.get_vertices().indexOf(graph.get_edges().get(i)[0])) != graph.get_colors().get(graph.get_vertices().indexOf(graph.get_edges().get(i)[1]))){
                //System.out.println(graph.get_edges().get(i)[0] + " and " + graph.get_edges().get(i)[1] + " are neighbours and don't have the same color");
                fit++;
            }
        }
        return fit / graph.get_edges().size();
    }

    private ArrayList<Graph> find_neighbours(){//this method is accessed only from within the class!
        neighbours = new ArrayList<>();
        for (int i = 0; i < this.graph.get_vertices().size(); i++) {//every vertice color can be changed(except the current color)
            for (int j= 1; j <= 4; j++) {//every color can be selected
                if(this.graph.get_colors().get(i) == j)//the vertice is already colored with this color
                    continue;//nothing happens
                else{
                    Graph neighbour = this.graph.clone();
                    neighbour.get_colors().set(i, j);//coloring the vertice so that thid graph becomes different from the original graph!
                    neighbours.add(neighbour);
                }
            }
        }
        return neighbours;
    }

    private double change_temperature(double alpha, int k, int mode){//this method is accessed only from within the class!
        switch(mode){
            case 1:
                return this.t0 * Math.pow(alpha, k);
            case 2:
                return this.t0 / (1 + alpha * Math.log10(1 + k));
            case 3:
                return this.t0 / (1 + alpha * k);
            case 4:
                return this.t0 / (1 + alpha * Math.pow(k, 2));
            default:
                return this.t0;
        }
    }
}