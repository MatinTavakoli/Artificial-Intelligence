import java.util.ArrayList;

import javax.net.ssl.ExtendedSSLSession;

public class DLS_Search{
    private Cube cube;
    private int depth;
    private boolean cutoff_occured;
    private ArrayList<String> explored_set;
    public DLS_Search(Cube cube, int depth){
        this.cube = cube;
        this.depth = depth;
        explored_set = new ArrayList<>();
    }

    public ArrayList<String> get_explored_set(){
        return explored_set;
    }

    public String simulate(Cube cube,int depth){
        System.out.println("Simulating DLS Search...");
        //wait(1000);
        System.out.println("Testing cube...");
        //wait(1000);
        System.out.println("depth is " + depth);
        //wait(1000);
        //wait(1000);
        if(cube.goal_test()){
            System.out.println("cube solved!");
            return cube.state_to_string_converter();
        }
        else if(depth==0){
            return "cutoff";
        }
        else{
            cutoff_occured = false;
            ArrayList<String> moves = new ArrayList<>();
            explored_set.add(cube.state_to_string_converter());//adding the expanded node
            for (int i = 0; i < 6; i++) {//branching factor is 6
                String code = cube.generate_move();
                if(code.charAt(1) == '1'){
                    code = cube.twin_code(code);//we only use clockwise rotations
                }
                if(!moves.contains(code)){//this move is new and has not been tested yet
                    moves.add(code);//adding move to the list
                    System.out.println("code is " + code);
                    Cube child_cube = cube.clone();
                    child_cube.rotate(Integer.valueOf(code.charAt(0)) - 48, Integer.valueOf(code.charAt(1) - 48));//because of ASCII, we subtract it from 48
                    if(!explored_set.contains(child_cube.state_to_string_converter())){
                        //wait(1000);
                        System.out.println("new move!");
                        //wait(1000);
                        String result = this.simulate(child_cube, depth - 1);
                        if(result.equals("cutoff"))
                            cutoff_occured = true;
                        else if(!result.equals("failure"))
                            return result;
                    }
                    else
                        continue;
                    
                }
                else
                    i--;//try another different move
                
            }
            if(cutoff_occured)
                return "cutoff";
            else
                return "failure";
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