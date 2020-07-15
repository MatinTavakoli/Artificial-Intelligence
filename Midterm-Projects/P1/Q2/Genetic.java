import java.util.ArrayList;
import java.util.Random;

public class Genetic{
    private static final int RED = 1;//color red
    private static final int YELLOW = 2;//color yellow
    private static final int GREEN = 3;//color green
    private static final int BLUE = 4;//color blue
    private int population_size;//number of chromosomes
    private int tournament_size;//number of chromosomes in a tournament
    private double mutation_rate;//the rate of mutation
    private int number_of_generations;//number of times we simulate the genetic algorithm!
    private ArrayList<Graph> chromosomes;//every graph is a chromosome in our genetic model
    private ArrayList<Graph> parent_chromosomes;//in every round, the chromosomes whicj
    private ArrayList<Graph> new_generation;//the next generation

    private ArrayList<Graph> best_fitness;//stores the chromosome with the best fitness function of every generation
    private ArrayList<Graph> worst_fitness;//stores the chromosome with the worst fitness function of every generation
    private ArrayList<Double> average_fitness;//stores the average fitness function of every generation

    public Genetic(Graph graph, int population_size, int tournament_size, double mutation_rate, int number_of_generations){
        this.population_size = population_size;
        this.tournament_size = tournament_size;
        this.mutation_rate = mutation_rate;
        this.number_of_generations = number_of_generations;
        chromosomes = new ArrayList<>();
        parent_chromosomes = new ArrayList<>();
        new_generation = new ArrayList<>();
        chromosomes.add(graph);//adding the uncolored graph as the first chromosome

        best_fitness = new ArrayList<>();
        worst_fitness = new ArrayList<>();
        average_fitness = new ArrayList<>();

        create_first_population();

        for (int i = 0; i < number_of_generations; i++) {
            parent_chromosomes.clear();//clearing the previous round
            System.out.println("generation #" + (i + 1));
            tournament_selection();

            new_generation = generate_new_population();
            new_generation = apply_mutation();
    
            chromosomes = new_generation;//getting ready for the next round!
        }
        System.out.println("genetic algorithm simulated successfully!");
        wait(2000);
        print_best_worst_and_average_fitness();//prints the best, worst and average fitness of every generation
        wait(3000);
        for (Graph chrom : best_fitness) {
            if(this.fitness_function(chrom) == 1.0){
                System.out.println("an example of a solved graph is: ");
                chrom.show_colors();
                break;
            }
        }
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

    private void create_first_population(){//this method is accessed only from within the class!
        Graph graph = chromosomes.get(0);
        color_graph(graph);//coloring the first choromosome at the end
        System.out.println("graph 0 is: ");
        System.out.println(this.fitness_function(graph));

        Graph min = graph;
        Graph max = graph;
        double avg = this.fitness_function(graph);

        for (int i = 1; i < population_size; i++) {
            Graph new_graph = graph.clone();
            color_graph(new_graph);
            chromosomes.add(new_graph);
            System.out.println("graph " + i + " is: ");
            System.out.println(this.fitness_function(new_graph));

            if(this.fitness_function(new_graph) > this.fitness_function(max)){//we have a new max fit
                max = new_graph;
            }

            if(this.fitness_function(new_graph) < this.fitness_function(min)){//we have a new min fit
                min = new_graph;
            }

            avg += this.fitness_function(new_graph);
        }

        best_fitness.add(max);//best fit of this generation
        worst_fitness.add(min);//worst fit of this generation
        average_fitness.add(avg / chromosomes.size());//average fit of this generation
    }

    private void tournament_selection(){//this method is accessed only from within the class!
        for (int i = 0; i < (population_size / tournament_size); i++) {
            ArrayList<Graph> selected_chromosomes = new ArrayList<>();
            for (int j = 0; j < tournament_size; j++) {
                int rand = (Math.abs(new Random().nextInt())) % population_size;
                selected_chromosomes.add(chromosomes.get(rand));
            }
            double best_fit = this.fitness_function(selected_chromosomes.get(0));
            Graph best_chromosome = selected_chromosomes.get(0);
            for (int j = 0; j < tournament_size; j++) {
                System.out.print("chromosome fit is: " + this.fitness_function(selected_chromosomes.get(j)) + " ");
                if(this.fitness_function(selected_chromosomes.get(j)) > best_fit){
                    best_fit = this.fitness_function(selected_chromosomes.get(j));
                    best_chromosome = selected_chromosomes.get(j);
                }
            }
            System.out.println();
            parent_chromosomes.add(best_chromosome);
        }
        for (int i = 0; i < parent_chromosomes.size(); i++) {
            System.out.println("parent chromosome " + i + " fitness function is: " + this.fitness_function(parent_chromosomes.get(i)));
        }
    }

    private ArrayList<Graph> generate_new_population(){//this method is accessed only from within the class!
        ArrayList<Graph> new_generation = new ArrayList<>();
        for (int i = 0; i < population_size; i++) {
            int rand1 = (Math.abs(new Random().nextInt())) % parent_chromosomes.size();
            int rand2 = (Math.abs(new Random().nextInt())) % parent_chromosomes.size();
            if(rand1 == rand2)
                i--;//random numbers must me distinct!
            else{
                Graph parent_1 = parent_chromosomes.get(rand1);
                Graph parent_2 = parent_chromosomes.get(rand2);
                int split_point = (Math.abs(new Random().nextInt())) % parent_chromosomes.get(0).get_vertices().size();//select a point in the vertices to split
                ArrayList<Integer> crossover_colors = new ArrayList<>();
                Graph new_chromosome = parent_1.clone();
                for (int j = 0; j <= split_point; j++) {//first part
                    crossover_colors.add(parent_1.get_colors().get(j));
                }
                for (int j = split_point + 1; j < parent_1.get_vertices().size(); j++) {//second part
                    crossover_colors.add(parent_2.get_colors().get(j));
                }
                new_chromosome.set_colors(crossover_colors);
                new_generation.add(new_chromosome);
            }
        }

        for (int i = 0; i < new_generation.size(); i++) {
            System.out.print("new chromosome fit is: " + this.fitness_function(new_generation.get(i)) + " ");
        }
        System.out.println();
        
        //calulating best, worst and average chromosome of this generation
        Graph min = new_generation.get(0);
        Graph max = new_generation.get(0);
        double avg = this.fitness_function(new_generation.get(0));
        for (int i = 1; i < population_size; i++) {
            if(this.fitness_function(new_generation.get(i)) > this.fitness_function(max)){//we have a new max fit
                max = new_generation.get(i);
            }

            if(this.fitness_function(new_generation.get(i)) < this.fitness_function(min)){//we have a new min fit
                min = new_generation.get(i);
            }

            avg += this.fitness_function(new_generation.get(i));
        }

        best_fitness.add(max);//best fit of this generation
        worst_fitness.add(min);//worst fit of this generation
        average_fitness.add(avg / new_generation.size());//average fit of this generation

        return new_generation;
    }

    private ArrayList<Graph> apply_mutation(){//this method is accessed only from within the class!
        double mutated_genomes = (population_size * new_generation.get(0).get_vertices().size() * mutation_rate);
        for (int i = 0; i < mutated_genomes; i++) {
            int rand_chromosome = (Math.abs(new Random().nextInt())) % new_generation.size();//which chromosome?
            int rand_vertice = (Math.abs(new Random().nextInt())) % new_generation.get(0).get_vertices().size();//which vertice?
            int rand_color = (Math.abs(new Random().nextInt())) % 4 + 1;//which color
            //System.out.println(new_generation.get(rand_chromosome).get_vertices().get(rand_vertice));
            if(new_generation.get(rand_chromosome).get_colors().get(new_generation.get(rand_chromosome).get_vertices().indexOf(new_generation.get(rand_chromosome).get_vertices().get(rand_vertice))) == rand_color)
                i--;//we need to choose another color which is different
            else{
                System.out.println(new_generation.get(rand_chromosome).get_vertices().get(rand_vertice) + " changed from " + new_generation.get(rand_chromosome).get_colors().get(new_generation.get(rand_chromosome).get_vertices().indexOf(new_generation.get(rand_chromosome).get_vertices().get(rand_vertice))) + " to " + rand_color);
                new_generation.get(rand_chromosome).get_colors().set(rand_vertice, rand_color);
                System.out.println("mutation done!");
            }
        }
        for (int i = 0; i < new_generation.size(); i++) {
            System.out.print("mutated fit is: " + this.fitness_function(new_generation.get(i)) + " ");
        }
        System.out.println();
        return new_generation;
    }

    private double fitness_function(Graph graph){//this method is accessed only from within the class!
        double fit = 0;
        for (int i = 0; i < graph.get_edges().size(); i++) {//iterating on edges
            if(graph.get_colors().get(graph.get_vertices().indexOf(graph.get_edges().get(i)[0])) != graph.get_colors().get(graph.get_vertices().indexOf(graph.get_edges().get(i)[1]))){
                //System.out.println(graph.get_edges().get(i)[0] + " and " + graph.get_edges().get(i)[1] + " are neighbours and don't have the same color");
                fit++;
            }
        }
        return fit/graph.get_edges().size();
    }

    private void print_best_worst_and_average_fitness(){//this method is accessed only from within the class!
        System.out.println("best fitness of all generations are: ");
        for (int i = 0; i < best_fitness.size(); i++) {
            System.out.print(this.fitness_function(best_fitness.get(i)) + " ");
        }
        System.out.println();

        System.out.println("worst fitness of all generations are: ");
        for (int i = 0; i < worst_fitness.size(); i++) {
            System.out.print(this.fitness_function(worst_fitness.get(i)) + " ");
        }
        System.out.println();
        
        System.out.println("average fitness of all generations are: ");
        for (int i = 0; i < average_fitness.size(); i++) {
            System.out.print(average_fitness.get(i) + " ");
        }
        System.out.println();
        
    }

    //initiates some delay so the user can see what's happening!
    public static void wait(int delay){
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}