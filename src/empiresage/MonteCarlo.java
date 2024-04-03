package empiresage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author Lydia
 */
public class MonteCarlo extends Player{

    Random r = new Random ();
    
    /**
      * Creates a new MonteCarlo object by initialize the state of the player
      * and the player identifier
      * @param state  first state of the player 
      * @param player identifier
    */
    public MonteCarlo (HashMap state,int player){
        super(state,player);
    }
    
    
    /**
      * returns the score value of the monteCarlo algorithm
      * @param HashMap state 
      * @return  double score
    */
    public double mondeCarlo(HashMap state){
        int total =0 ;
        MCTSNode currentNode = new MCTSNode(null,state,playerN);
        ArrayList <MCTSNode> simulationList =  this.simulation(currentNode) ;
        for (MCTSNode x :simulationList){
            total = total + x.getUtility();
        }
        
        return total/simulationList.size();
    }
    
    /**
      * runs the simulation for a node and returns the tree that has been
      * produced
      * @param MCTSNode node
      * @return  ArrayList <MCTSNode> tree of the simulation
    */
    public ArrayList <MCTSNode> simulation(MCTSNode node){
        ArrayList <MCTSNode> simuList = new ArrayList <MCTSNode>();
        simuList.add(node);
        
        while (!simuList.get(simuList.size()-1).isTerminal()){
            ArrayList <HashMap> possList = this.possibleStates(simuList.get(simuList.size()-1).getState(), playerN);
            int ran = r.nextInt(possList.size());
            MCTSNode mn = new MCTSNode(simuList.get(simuList.size()-1), possList.get(r.nextInt(possList.size())),playerN);
            mn.updateUtility(this.utility(mn.getState()));
            simuList.add(mn);
        }
        
        return simuList;
    }
    
    /**
      * This method decides what will be the player's next state
      * by using the minimax (alpha beta pruning )  and returns 
      * this state
      * @param g current state
      * @param state
    */
    public HashMap updateMove(HashMap g){
        ArrayList <HashMap> possibleStates = this.possibleStates(g, playerN);

        
        double maxScore  = mondeCarlo( possibleStates.get(0));
        int counter = 0;
      
        for(int i=1; i <possibleStates.size(); i++){
            double maxScore1  = mondeCarlo( possibleStates.get(i));
  
            if(maxScore < maxScore1){
                maxScore = maxScore1;
                counter = i;
            }
        }

        return possibleStates.get(counter);
     } 
   
}
