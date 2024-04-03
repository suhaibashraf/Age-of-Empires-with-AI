package empiresage;
 
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Lydia
 */
public class MCTSNode {
    
     private int visitors = 0; // visitors of the node
     private int utility = 0; //utility of the node
     private boolean terminal = false ; 
     private MCTSNode parentNode = null;
     private HashMap <String,Integer> state = new HashMap <String,Integer> ();
    
     private ArrayList <MCTSNode> childrenList = new ArrayList <MCTSNode> ();
   
     /**
      * Creates MCTSNode object
      * @param parent  hm state of the node
      * @param parent  parent node in the tree 
      * @param player identifier
     */
     public MCTSNode(MCTSNode parent, HashMap hm,int player){
         this.parentNode = parent ;
         this.state =hm;
         terminal = this.endGame(hm, player);
     }
     
     /**
     * Sets the state.
     * 
     * @param adds child node to the childrenList
     */
     public void addChild(MCTSNode n){
         childrenList.add(n);
     }
     
     /**
     * update visitor value by 1
      */
     public void updateVisitor(){
         visitors++;
     }
     
      /**
     * set visitor
     * @param int visitor
     */
     public void setVisitor(int visitorN){
         visitors = visitors + visitorN;
     }
     
     /**
     * Update utility
     * @param int new utility
     */
     public void updateUtility(int utility){
         this.utility = utility;
     }
     
     /**
     * Update utility for backpropagation purpose
     * @param int new utility
     */
     public void updateUtilityBackPropagation(int utilityP){
         this.utility = this.utility + utilityP;
     }
     
     /**
     * Returns visitor
     * @return boolean
     */
     public int getVisitor(){
         return visitors;
     }

     /**
     * Returns list of children
     * @return ArrayList
     */
    public ArrayList<MCTSNode> getChildrenList() {
        return childrenList;
    }

    /**
     * Returns if node is terminal
     * @return boolean
     */
    public boolean isTerminal() {
        return terminal;
    }
    
    /**
     * Returns if it has parent
     * @return boolean
     */
    public boolean hasParent(){
         return parentNode != null;
             
    } 
    
    /**
     * Returns the parent of the node
     * @return MCTSNode
     */
    public MCTSNode getParentNode() {
        return parentNode;
    }
    
    /**
     * Returns the state
     * @return HashMap
     */
    public HashMap<String, Integer> getState() {
        return state;
    }
    
    /**
     * Returns the utility
     * @return integer
     */
    public int getUtility(){
        return utility;
    }
    
    
    /**
     * sets state
     * @param HashMap<String, Integer> state
     */
    public void setState(HashMap<String, Integer> state) {
        this.state = state;
    }
    
     /**
     *This method returns if the game has ended in the specific state.
     * @param statePS - state of a game
     * @return int player identifier
     * @return boolean true if there is a winner false if there is none
    */
    public boolean endGame(HashMap statePS,int playerN){
        String  playerString = (playerN == 1) ? "One" : "Two";
        int villagers = (int) statePS.get("numberOfVillagersPlayer"+playerString);
        int warriors = (int) statePS.get("numberOfFightersPlayer"+playerString);
        int fightingUnit = (int) statePS.get("numberOfFightingUnit"+playerString);

        return villagers > 50 && warriors > 50 && fightingUnit > 30;

    }
    
}
