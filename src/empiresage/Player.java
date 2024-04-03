package empiresage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Lydia 
 */
public class Player {
    
   
    protected int playerN = 0; // identifier of player
    //current state of the player
    protected HashMap <String,Integer> state = new HashMap <String,Integer>();
    private final int WARRIORCOST = 100; // cost of warrior
    private final int VILLAGERCOST = 50; //cost of villager
    private final int HOUSECOST = 100; //cost of house
    private final int FIGHTINGUNITCOST = 150; //cost of fighting unit

    /**
      * Creates a new Player object by initialize the state of the player and 
      * the player's identifier
      * @param state  the first state of the player 
      * @param player identifier
   */
    public Player(HashMap state,int PlayerNc){
        this.state = state;
        this.playerN = PlayerNc;
    }
    
  
    
    /*
    * return and ArrayList of possible moves
    * 1 for collect wood
    * 2 for collect food
    * 3 create new house
    * 4 create fightUnit
    * 5 create new villager
    * 6 create new warrior
    * @param statePS - state of the game
    * @param player identifier
    * @return ArrayList of possible moves
    */
    private ArrayList <Integer> possibleMoves(HashMap statePM,int playerPM) {
        ArrayList <Integer> possibleMoves = new ArrayList <Integer> ();
        
        String  playerString = (playerPM == 1) ? "One" : "Two"; //for which player should get data from the state
        
        //get the date from the state
        int villagers = (int)statePM.get("numberOfVillagersPlayer"+playerString);
        int house =  (int)statePM.get("numberOfHousesPlayer"+playerString);
        int warriors =  (int)statePM.get("numberOfFightersPlayer"+playerString);
        int fightingUnit =  (int)statePM.get("numberOfFightingUnit"+playerString);
        int wood =  (int)statePM.get("woodPlayer"+playerString);
        int food =  (int)statePM.get("foodPlayer"+playerString);
        
        int allowerPopulation = house *5; //allow poulation is count 5 x house
        int population = warriors +villagers; 
        
        possibleMoves.add(1); // always can collect wood
        possibleMoves.add(2); // always can collect food
        
        // can build  a house if the available wood resourses is bigger than 
        //the cost of the house
        if ( wood >= HOUSECOST){ 
            possibleMoves.add(3);
        }
        if( wood >= FIGHTINGUNITCOST){ //can built a fighting unit
            possibleMoves.add(4);
        }
        //can produce new villager if available food is bigger than the cost 
        //and the population hasn't overcome the available population
        if(food > VILLAGERCOST && population < allowerPopulation){ 
            possibleMoves.add(5);
        }
        //can produce a new warrior
        if(food > WARRIORCOST && population < allowerPopulation){ 
            possibleMoves.add(6);
        }
        
          
        return possibleMoves;
        
    }
    
    
    
   /*
    * Return the utility of a state 
    * @param statePS - state of the game
    * @return int utility of the state
    */
    public int utility(HashMap state){
        int utilityLevel =0;
        String  playerString = (playerN == 1) ? "One" : "Two";
        
        //get the date from the state
        int villagers = (int) state.get("numberOfVillagersPlayer"+playerString);
        int house = (int) state.get("numberOfHousesPlayer"+playerString);
        int warriors = (int) state.get("numberOfFightersPlayer"+playerString);
        int fightingUnit = (int) state.get("numberOfFightingUnit"+playerString);
        int wood = (int) state.get("woodPlayer"+playerString);
        int food = (int) state.get("foodPlayer"+playerString);
        
        int allowedPolulationU = house * 5;
        int populationU = warriors + villagers;
        
        //the utility is better when every attitube is analogue                   
        utilityLevel = (allowedPolulationU - populationU) + ( warriors/fightingUnit) + (warriors - villagers) + (food / villagers) + (wood / villagers) + (food / warriors) + (wood / warriors);
        
        utilityLevel *= -1; // changing the sign of utility because the smaller
        //is the better utility but minimax looks for the bigger value
        return utilityLevel;
    }
    
   
    
    
    
  /**
   * This method returns if the game has ended in the specific state.
   * @param statePS - state of the game
   * @return boolean true if there is a winner false if there is none
   */
    public boolean endGame(HashMap statePS){
        String  playerString = (playerN == 1) ? "One" : "Two";
        int villagers = (int) statePS.get("numberOfVillagersPlayer"+playerString);
        int warriors = (int) statePS.get("numberOfFightersPlayer"+playerString);
        int fightingUnit = (int) statePS.get("numberOfFightingUnit"+playerString);

        return villagers > 50 && warriors > 50 && fightingUnit > 30; 

    }
    

   /*
    * return an ArrayList of possible states
    * @return int player identifier
    * @param statePS - state of the game
    * @return ArrayList <HAshMap> of possible moves
    */
    public ArrayList possibleStates(HashMap statePS,int player){
        
        ArrayList <HashMap> list = new  ArrayList <> ();
        String  playerString = (player == 1) ? "One" : "Two";
        for (Integer x: this.possibleMoves(statePS,player)){

            HashMap <String,Integer> possibleStateH = statePS;         
        
            int wood = possibleStateH.get("woodPlayer" + playerString);
            int food = possibleStateH.get("foodPlayer" + playerString);
            int house = possibleStateH.get("numberOfHousesPlayer" + playerString);
            int fighting = possibleStateH.get("numberOfFightingUnit" + playerString);
            int villager = possibleStateH.get("numberOfVillagersPlayer" + playerString);
            int fighters = possibleStateH.get("numberOfFightersPlayer" + playerString);

          
            switch (x) {
                case 1:
                    wood = 60 + wood;
                    break;
               case 2:
                    food = 60 + food;

                    break;
               case 3:
                   house = 1 + house;
                    wood = wood - HOUSECOST;
                    break;
               case 4:

                   fighting = 1 + fighting;
                    wood = wood - FIGHTINGUNITCOST;
                    break;
               case 5:
                    villager = 1 + villager;
                    food = food - VILLAGERCOST;
                    break;
               case 6:

                    fighters = 1 + fighters;
                    food = food - WARRIORCOST;
                    break;                   
            }
            
            HashMap <String,Integer> xc= new HashMap <>();
            
            String  playerString2 = (player == 1) ? "Two" : "One";

            // update values of possible states
            xc.put("woodPlayer" + playerString, wood);
            xc.put("foodPlayer" + playerString, food);
            xc.put("numberOfHousesPlayer" + playerString, house);
            xc.put("numberOfFightingUnit" + playerString, fighting);
            xc.put("numberOfVillagersPlayer" + playerString, villager);
            xc.put("numberOfFightersPlayer" + playerString, fighters);            
           
            xc.put("woodPlayer" + playerString2,  possibleStateH.get("woodPlayer" + playerString2));
            xc.put("foodPlayer" + playerString2, possibleStateH.get("foodPlayer" + playerString2));
            xc.put("numberOfHousesPlayer" + playerString2, possibleStateH.get("numberOfHousesPlayer" + playerString2));
            xc.put("numberOfFightingUnit" + playerString2, possibleStateH.get("numberOfFightingUnit" + playerString2));
            xc.put("numberOfVillagersPlayer" + playerString2, possibleStateH.get("numberOfVillagersPlayer" + playerString2));
            xc.put("numberOfFightersPlayer" + playerString2, possibleStateH.get("numberOfFightersPlayer" + playerString2));

            list.add(xc);

        }
        return list;
    }

    /**
    * Returns the state in which the player is .
    * @return HashMap <Sting,Integer> state
    */
    public HashMap getState(){
        return state;
    }
    
    /**
     * Sets the state.
     * 
     * @param h to set state
    */
    public void setState(HashMap h){
        state = h ;
    }

    /**
     * Returns which player is - it can be 1 or 2
     * @return int player identifier
    */
    public int getPlayerN() {
        return playerN;
    }
    

}
 
