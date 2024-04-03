package empiresage;

import java.util.ArrayList;
import java.util.HashMap;
import ageofempiresgui.*;
import javax.swing.SwingWorker;
import java.util.List;

/**
 *
 * @author Lydia
 */
public class EmpiresAge  extends SwingWorker <List<HashMap>, HashMap> {

    /**
     * @param args the command line arguments
     * just checking team stuff with this comment
     * another check
     */
    public HashMap <String,Integer> gameState;
    private String Algo = "Select";
    private MinMaxPlayer P1;
    private MonteCarlo P2;
    private MCTSPlayer P3;
    private NegaMaxPlayer P4;
    private gameGUI gg;
    private double executeTime;
    private String Winner;
    String[] props ;
    
    public EmpiresAge(String Algo)
    {
        this.Algo = Algo;
        props = new String[]{"numberOfVillagersPlayer","numberOfFightersPlayer","numberOfHousesPlayer",
                        "numberOfFightingUnit","woodPlayer","foodPlayer"};
        initialize();
    }
    
    public void setUI(gameGUI gg)
    {
        this.gg = gg;
    }
    
            
    public void initialize() {
        gameState = new HashMap <String,Integer>();
        gameState.put("numberOfVillagersPlayerOne", 1);
        gameState.put("numberOfVillagersPlayerTwo", 1);
        gameState.put("numberOfFightersPlayerOne", 2);
        gameState.put("numberOfFightersPlayerTwo", 2);
        gameState.put("numberOfHousesPlayerOne", 1);
        gameState.put("numberOfHousesPlayerTwo", 1);
        gameState.put("numberOfFightingUnitOne", 1);
        gameState.put("numberOfFightingUnitTwo", 1);
        gameState.put("woodPlayerOne", 0);
        gameState.put("woodPlayerTwo", 0);
        gameState.put("foodPlayerOne", 0);
        gameState.put("foodPlayerTwo", 0);
        
        
    }
    
    
    public HashMap <String,Integer> getGameState() {return gameState;}
    
    public boolean isRunning()
    {
        if("MiniMax".equals(Algo)) {
            return (!P1.endGame(gameState) && !P4.endGame(gameState));
        }
        else if("MonteCarlo".equals(Algo)) {
            return (!P2.endGame(gameState) && !P4.endGame(gameState));
        }
        else {
            return (!P3.endGame(gameState) && !P4.endGame(gameState));
        }
    }
    
    public void runGame()
    {
        long startTime = System.nanoTime();
        //HashMap <String,Integer> state2 = (HashMap) state.clone();
        //HashMap <String,Integer> state3 = (HashMap) state.clone();

        boolean negamaxAnnounced=false;
        
        P4 = new NegaMaxPlayer(gameState,2);
        if("MiniMax".equals(Algo)) {
            boolean minmaxAnnounced= false;
            P1 = new MinMaxPlayer(gameState,1);
            while (!P4.endGame(gameState) && !P1.endGame(gameState)){
                gameState= P4.updateMove(gameState);
                P1.setState(gameState);
                P4.setState(gameState);
                gameState = P1.updateMove(gameState);
                P1.setState(gameState);
                P4.setState(gameState);
                if (!minmaxAnnounced && P1.endGame(gameState)) 
                {
                    System.out.println("MiniMax Finished");
                    minmaxAnnounced=true;
                }
                if (!negamaxAnnounced && P4.endGame(gameState)) 
                {
                    System.out.println("NegaMax Finished");
                    negamaxAnnounced=true;
                }
                publish(gameState);
            }
            if(P1.endGame(gameState)){
                System.out.println("And the winnerrrrrrrrr of the battle between MiniMax and NegaMax isssssss MiniMax");
                Winner = Algo;
            }
        }
        else if("MonteCarlo".equals(Algo)) {
            boolean montecarloAnnounced= false;
            P2 = new MonteCarlo(gameState,1);
            while (!P4.endGame(gameState) && !P2.endGame(gameState)){
                gameState= P4.updateMove(gameState);
                P2.setState(gameState);
                P4.setState(gameState);
                gameState = P2.updateMove(gameState);
                P2.setState(gameState);
                P4.setState(gameState);
                if (!montecarloAnnounced && P2.endGame(gameState)) 
                {
                    System.out.println("MonteCarlo Finished");
                    montecarloAnnounced=true;
                }
                if (!negamaxAnnounced && P4.endGame(gameState)) 
                {
                    System.out.println("NegaMax Finished");
                    negamaxAnnounced=true;
                }
                publish(gameState);
            }
            if(P2.endGame(gameState)){
                System.out.println("And the winnerrrrrrrrr of the battle between MonteCarlo and NegaMax isssssss MonteCarlo");
                Winner = Algo;
            }
        }
        else {
            boolean mctsAnnounced= false;
            P3 = new MCTSPlayer(gameState,1);
            while (!P4.endGame(gameState) && !P3.endGame(gameState)){
                gameState= P4.updateMove(gameState);
                P3.setState(gameState);
                P4.setState(gameState);
                gameState = P3.updateMove(gameState);
                P3.setState(gameState);
                P4.setState(gameState);
                if (!mctsAnnounced && P3.endGame(gameState)) 
                {
                    System.out.println("MCTS Finished");
                    mctsAnnounced=true;
                }
                if (!negamaxAnnounced && P4.endGame(gameState)) 
                {
                    System.out.println("NegaMax Finished");
                    negamaxAnnounced=true;
                }
                publish(gameState);
            }
            if(P3.endGame(gameState)){
                System.out.println("And the winnerrrrrrrrr of the battle between MCTS and NegaMax isssssss MCTS");
                Winner = Algo;
            }
        }
        
        if(P4.endGame(gameState)){
            System.out.println("And the winnerrrrrrrrr of the battle between " + Algo + " and NegaMax  isssssss NegaMax");
            Winner = "NegaMax";
        }
        long endTime = System.nanoTime();
        executeTime = (endTime - startTime)/1_000_000_000.0;
        System.out.println("Took "+executeTime + " s"); 
    }

    @Override
    protected List<HashMap> doInBackground() throws Exception {
        System.out.println("doing it");
        runGame();
        return null;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
     protected void process(List<HashMap> hms) {
         for (HashMap hm : hms) {
             gg.showState(hm,executeTime,Winner);
         }
     }


}
