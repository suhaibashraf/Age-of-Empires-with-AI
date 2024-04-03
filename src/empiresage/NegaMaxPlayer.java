/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package empiresage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author Haad Bodla
 */
public class NegaMaxPlayer extends Player{
    
    private Random r = new Random ();
    
    /**
      * Creates a new MinMaxPlayer object by initialize the state of the player 
      * and the player identifier
      * @param state  first state of the player 
      * @param player identifier
    */
    public NegaMaxPlayer(HashMap state,int player){
        super(state,player);
    }
    /**
     * Use minimax with alpha-beta pruning 
     * runs until a state is terminal or the depth becomes 0
     * 
     * @param depth the depth the search
     * @param player identifier
     * @param MM for the state that the minimax will run
     * @return utility of the the final state
     **/
    
    
    public int negamax(HashMap MM, int depth, int alpha, int beta, int player){
        if(depth == 0 || this.endGame(MM)){
            return this.utility(MM);
        }
        int value = (int) Double.NEGATIVE_INFINITY;
        for(Object xo : this.possibleStates(MM, player)) {
            if(player == 1) {
                value = Math.max(value, -negamax((HashMap) xo, (depth - 1), (-1 * beta), (-1 * alpha), 2));
            }
            else {
                value = Math.min(value, -negamax((HashMap) xo, (depth - 1), (-1 * beta), (-1 * alpha), 1));
            }
        }
        return value;
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

        int max = negamax(possibleStates.get(0), 6, -999, 999, 1);        
        list.add(0);
        
        for (int i=1; i<possibleStates.size();i++){
            int alpha = negamax(possibleStates.get(i), 6, -999, 999, 1);
            if (alpha > max){
                max = alpha;
//                list.removeAll(list);
                list.add(i);
            }
            if(alpha == max){
                list.add(i);
            }
        }
        return possibleStates.get(list.get(r.nextInt(list.size())));
    }
}
