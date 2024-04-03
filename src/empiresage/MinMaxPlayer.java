package empiresage;

import java.util.*;

/**
 *
 * @author Lydia
 */
public class MinMaxPlayer extends Player{
    
    private Random r = new Random ();
    
    /**
      * Creates a new MinMaxPlayer object by initialize the state of the player 
      * and the player identifier
      * @param state  first state of the player 
      * @param player identifier
    */
    public MinMaxPlayer(HashMap state,int player){
        super(state,player);
    }
    /**
     * Use minimax with alpha-beta pruning 
     * runs until a state is terminal or the depth becomes 0
     * 
     * @param depth the depth the search
     * @param player identifier
     * @param stateMM for the state that the minimax will run
     * @param alpha
     * @param beta
     * @return utility of the the final state
     **/
    public int alphabeta(HashMap stateMM, int depth, int alpha, int beta, int player){
        
        if (depth == 0 || this.endGame(stateMM)){ 
            return this.utility(stateMM);
        }else{
            if(player == 1){//max player
                for(Object xo : this.possibleStates(stateMM,1)){
                    int al = this.alphabeta((HashMap) xo, depth-1, alpha, beta, 2);
                    alpha = Math.max(alpha, al);
                    if (alpha >= beta){break;}
                }
                return alpha;
                
            }else{//min
                for(Object xo : this.possibleStates(stateMM,2)){
                    int be = this.alphabeta((HashMap) xo, depth-1, alpha, beta, 1);
                    beta = Math.min(beta, be);
                   if (alpha >= beta){break;}

                }
                return beta;
             }        
        }
        
    }
    
    
     /**
      * This method decides what will be the player's next state
      * by using the minimax (alpha beta pruning )  and returns 
      * this state
      * @param g current state
      * @return state
    */
    public HashMap updateMove(HashMap g){
        ArrayList <HashMap> possibleStates = this.possibleStates(g, playerN);
        ArrayList <Integer> list = new ArrayList <> ();

        int max = alphabeta(possibleStates.get(0), 6, -999, 999, 1);        
        list.add(0);
        
        for (int i=1; i<possibleStates.size();i++){
            int alpha = alphabeta(possibleStates.get(i), 6, -999, 999, 1);
            if (alpha > max){
                max = alpha;
                list.removeAll(list);
                list.add(i);
            }
            
            if(alpha == max){
                list.add(i);
            }
        }
        return possibleStates.get(list.get(r.nextInt(list.size())));
        
    }
    
    
}
