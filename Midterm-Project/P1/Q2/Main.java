import java.util.Scanner;
import java.util.ArrayList;

public class Main{
    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        int delay = 2000;//duration of delay between steps

        String iran_provinces[] = {"W", "E", "A", "K", "Z", "G", "K'", "H", "Q", "I", "L", "M'", "A'", "M", "G'", "N", "Q'", "T", "S", "R", "U", "C", "I'", "S'", "B'", "Y", "B", "F", "J", "H'", "T'"};//each character represents a province(instructions on which is which are in the Q2.Instructions.pdf file)
        String iran_provinces_connections[][] = {{"W", "E"}, {"W", "Z"}, {"W", "K"}, {"E", "Z"}, {"E", "A"}, {"A", "Z"}, {"A", "G"}, {"K", "Z"}, {"K", "H"}, {"K", "K'"}, {"Z", "G"}, {"Z", "Q"}, {"Z", "H"}, {"G", "Q"}, {"G", "M"}, {"K'", "H"}, {"K'", "L"} ,{"K'", "I"}, {"H", "Q"}, {"H", "M'"}, {"H", "L"}, {"Q", "M"}, {"Q", "A'"}, {"Q", "M'"}, {"I", "L"}, {"I", "U"}, {"L", "M'"}, {"L", "I'"}, {"L", "C"}, {"L", "U"}, {"M'", "A'"}, {"M'", "T"}, {"M'", "Q'"}, {"M'", "I'"}, {"A'", "M"}, {"A'", "T"}, {"M", "G'"}, {"M", "S"}, {"M", "T"}, {"G'", "N"}, {"G'", "S"}, {"N", "S"}, {"N", "R"}, {"Q'", "T"}, {"Q'", "S"}, {"Q'", "I'"}, {"T", "S"}, {"S", "R"}, {"S", "S'"}, {"S", "I'"}, {"R", "S'"}, {"U", "C"}, {"U", "B'"}, {"U", "B"}, {"C", "I'"}, {"C", "B'"}, {"I'", "S'"}, {"I'", "Y"}, {"I'", "F"}, {"I'", "B'"}, {"S'", "Y"}, {"S'", "J"}, {"S'", "T'"}, {"B'", "F"}, {"B'", "B"}, {"Y", "J"}, {"Y", "F"}, {"B", "F"}, {"B", "H'"}, {"F", "J"}, {"F", "H'"}, {"J", "H'"}, {"J", "T'"}, {"H'", "T'"}};

        ArrayList<String> province_list = new ArrayList<>();
        ArrayList<String[]> province_connections_list = new ArrayList<>();
        for (int i = 0; i < iran_provinces.length; i++)
            province_list.add(iran_provinces[i]);
        for (int i = 0; i < iran_provinces_connections.length; i++) {
            province_connections_list.add(iran_provinces_connections[i]);
        }

        Graph graph = new Graph(province_list, province_connections_list);

        System.out.println("Welcome to the coloring problem solver!");
        
        wait(delay);

        String mode = "";
        for (int i = 0; i < 1; i++) {
            System.out.println("Select 1 for Genetic Algorithm or 2 for Annealing Algorithm");
            mode = scanner.next();
            if(mode.equals("1") == false && mode.equals("2") == false){
                System.out.println("Please try again!");
                i--;
            }
        }
        int mode_int = Integer.valueOf(mode);


        switch(mode_int){
            case 1:
                System.out.println("Plese enter population size: ");
                int population_size = scanner.nextInt();
                System.out.println("Plese enter tournament size: ");
                int tournament_size = scanner.nextInt();
                System.out.println("Plese enter mutation rate: ");
                double mutation_rate = scanner.nextDouble();
                System.out.println("Plese enter number of generations: ");
                int number_of_generations = scanner.nextInt();
                Genetic genetic = new Genetic(graph, population_size, tournament_size, mutation_rate, number_of_generations);
                //Genetic genetic = new Genetic(graph, 100, 10, 0.02, 100);
                break;
            case 2:
                System.out.println("Plese enter initial temperature: ");
                double t0 = scanner.nextDouble();
                System.out.println("Plese enter cooling factor: ");
                double alpha = scanner.nextDouble();
                System.out.println("Plese enter cooling method: ");
                int method = scanner.nextInt();
                Annealing annealing = new Annealing(graph, t0, alpha, method);
                //Annealing annealing = new Annealing(graph, 100, 0.8, 4);
                break;
        }
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