package empiresage;

import java.util.*;

/**
 *
 * @author Lydia
 */
public class MCTSPlayer extends Player {
 
    private Random r = new Random ();
    
      /**
      * Creates a new MCTSPlayer object by initialize the state of the player 
      * and the player identifier
      * @param state  first state of the player 
      * @param player identifier
    */
    public MCTSPlayer(HashMap state,int player){
        super(state,player);
    }
     
    /**
      * Expands a node with it's children (all possible nodes) 
      * @param node
    */
    public void expand(MCTSNode node){
        if (node.isTerminal()){
            return ;
        }
        // all possible states
        for (Object x : this.possibleStates(node.getState(), playerN)){
            // create MCTSNode for a possible node
            MCTSNode mn = new MCTSNode(node, (HashMap) x,playerN); 
            node.addChild(mn); 
            mn.setState((HashMap<String, Integer>) x); 
            
        }
    }
    
    /**
      * runs until the root node is reached and updates the utility and the 
      * visitors
      * @param node
      * return MCTSNode
    */
    public MCTSNode backPropagation(MCTSNode node){
        //if there is no parent that means that it is the root node
        if (node.hasParent()){ 
            node.getParentNode().setVisitor(node.getVisitor());
            node.getParentNode().updateUtilityBackPropagation(node.getUtility());
            return backPropagation(node.getParentNode());
            
        }else{
            return node;//root node
        }
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
        
        // as long as it is not the terminal node it builts the tree
        while (!simuList.get(simuList.size()-1).isTerminal()){
            ArrayList <HashMap> possList = this.possibleStates(simuList.get(simuList.size()-1).getState(), playerN);
            int ran = r.nextInt(possList.size());
            //System.out.println(ran);
            MCTSNode mn = new MCTSNode(simuList.get(simuList.size()-1), possList.get(r.nextInt(possList.size())),playerN);
            mn.updateVisitor();
            mn.updateUtility(this.utility(mn.getState()));
            simuList.add(mn);
            expand(mn);//expand the node
        }
        
        return simuList;
    }
    
    /**
      * This method decides what will be the player's next state
      * by using the MSCT  and returns  this state
      * @param g current state
      * @return state
    */
    public HashMap updateMove(HashMap g){
        
        //current state's node
        MCTSNode currentNode = new MCTSNode(null,g,playerN); 
        currentNode.updateVisitor(); // update visitor +1
        //update utility by using backprogation Method
        currentNode.updateUtilityBackPropagation(currentNode.getUtility()); 
        // possible states from current state
        ArrayList <HashMap> possibleStates = this.possibleStates(g, playerN); 
        
        //first possible state is assumed to have the biggest value
        MCTSNode node = new MCTSNode(null,possibleStates.get(0),playerN);
        node.updateVisitor();
        node.updateUtilityBackPropagation(node.getUtility());
        ArrayList <MCTSNode> simulation = simulation(node);
        MCTSNode backPropagationNode =  backPropagation(simulation.get(simulation.size()-1));
        
        //use UCT
        double maxScore  = backPropagationNode.getUtility() + Math.sqrt(Math.log(backPropagationNode.getVisitor()) / currentNode.getVisitor() );
        int counter = 0;
      
        //calculate the node which has the biggest score from MSCT
        for(int i=1; i <possibleStates.size(); i++){
            MCTSNode node1 = new MCTSNode(null,possibleStates.get((i)),playerN);
            node1.updateVisitor();
            node1.updateUtilityBackPropagation(node1.getUtility());
            ArrayList <MCTSNode> simulation1 = simulation(node1);
            MCTSNode backPropagationNode1 =  backPropagation(simulation1.get(simulation1.size()-1));
            double maxScore1  = backPropagationNode1.getUtility() + Math.sqrt(Math.log(backPropagationNode1.getVisitor()) / currentNode.getVisitor() );
               
            if(maxScore < maxScore1){
                maxScore = maxScore1;
                counter = i;
            }
        }

        // returns the state with the biggest score from MSCT
        return possibleStates.get(counter); 
     } 
    
}