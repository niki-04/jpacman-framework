package nl.tudelft.jpacman.freezeUnfreeze;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.CollisionMap;
import nl.tudelft.jpacman.level.DefaultPlayerInteractionMap;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.Ghost;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;

public class FreezeUnfreezeTest {
	
	private Launcher launcher;
	
	/**
     * The level under test.
     */
    private Level level;
    
    /**
     * An NPC on this level.
     */
    private final Ghost ghost = mock(Ghost.class);
    
    /**
     * Starting position 1.
     */
    private final Square square1 = mock(Square.class);
    
    /**
     * Starting position 2.
     */
    private final Square square2 = mock(Square.class);
    
    /**
     * The board for this level.
     */
    private final Board board = mock(Board.class);
    
    /**
     * The collision map.
     */
    private final CollisionMap collisions = mock(CollisionMap.class);
    
    
	/**
     * Launch the user interface.
     */
    @BeforeEach
    void setUpPacman() {
        launcher = new Launcher();
    
        level = new Level(board, Lists.newArrayList(ghost), Lists.newArrayList(
                square1, square2), collisions);
    }
    
 
  //Decision Table Strategy Tests
  
  /**
   * Validates the state of the game when: 
   * Start Button is clicked -> Stop Button is clicked -> Freeze Button is clicked.
   */
  @Test
  void startStopFreeze() {
  	level.start();
  	level.stop();
  	level.freeze();
  	assertThat(level.isInProgress()).isFalse();
  }
  
  /**
   * Validates the state of the game when: 
   * Start Button is clicked -> Stop Button is clicked -> Freeze Button is clicked -> Freeze Button is clicked again.
   */
  @Test
  void startStopFreezeUnfreeze() {
  	level.start();
  	level.stop();
  	level.freeze();
  	level.unfreeze();
  	assertThat(level.isInProgress()).isFalse();
  }
  
  /**
   * Validates the state of the game when: 
   * Start Button is clicked -> Freeze Button is clicked
   */
  @Test
  void startFreeze() {
  	level.start();
  	assertThat(level.isInProgress()).isTrue();
  	level.freeze();
  	assertThat(level.isInProgress()).isTrue();
  }
  
  /**
   * Validates the state of the game when: 
   * Start Button is clicked -> Freeze Button is clicked  -> Freeze Button is clicked again
   */
  @Test
  void startFreezeUnfreeze() {
  	level.start();
  	level.freeze();
  	level.unfreeze();
  	assertThat(level.isInProgress()).isTrue();
  }
  
  /**
   * Validates the state of the game when: 
   * Freeze Button is clicked  -> Freeze Button is clicked again, before game has started
   */
  @Test
  void freezeUnfreeze() {
  	level.freeze();
  	level.unfreeze();
  	assertThat(level.isInProgress()).isFalse();
  }
  
  
  /**
   * Validates the state of the game when: 
   * Stop Button is clicked -> Freeze Button is clicked, before game has started
   */
  @Test
  void stopFreeze() {
  	level.stop();
  	level.freeze();
  	assertThat(level.isInProgress()).isFalse();
  }
  
  /**
   * Validates the state of the game when: 
   * Stop Button is clicked -> Freeze Button is clicked -> Freeze Button is clicked again, before game has started
   */
  @Test
  void stopFreezeUnfreeze() {
  	level.stop();
  	level.freeze();
  	level.unfreeze();
  	assertThat(level.isInProgress()).isFalse();
  }
  
    
    //Functionality testing strategy tests;
    
    /**
     * Launch the game, and imitate what would happen in a typical game.
     * Player starts a game, then freezes the game and moves 1 unit to the EAST and 2 units North
     */
    @Test
    void startFreezeMoveEastNorth() {
    	Game game = launcher.makeGame();
    	Player player = game.getPlayers().get(0);
    	game.start();
    	game.freeze();
    	move(game, Direction.EAST, 1);
    	move(game, Direction.NORTH, 2);
    	assertThat(player.getScore()).isEqualTo(30);
    }
    
    /**
     * Launch the game, and imitate what would happen in a typical game.
     * Player starts a game, then freezes the game and moves 6 units to the EAST and 2 units South
     */
    @Test
    void startFreezeMoveEastSouth() {
    	Game game = launcher.makeGame();
    	Player player = game.getPlayers().get(0);
    	game.start();
    	game.freeze();
    	move(game, Direction.EAST, 6);
    	move(game, Direction.SOUTH, 2);
    	assertThat(player.getScore()).isEqualTo(80);
    }
    
    /**
     * Launch the game, and imitate what would happen in a typical game.
     * Players starts a game, then freezes the game and moves 1 unit to the WEST and 2 units North
     */
    @Test
    void startFreezeMoveWestNorth() {
    	Game game = launcher.makeGame();
    	Player player = game.getPlayers().get(0);
    	game.start();
    	game.freeze();
    	move(game, Direction.WEST, 1);
    	move(game, Direction.NORTH, 2);
    	assertThat(player.getScore()).isEqualTo(30);
    }
    
    /**
     * Launch the game, and imitate what would happen in a typical game.
     * Players starts a game, then freezes the game and moves 6 units to the WEST and 2 units South
     */
    @Test
    void startFreezeMoveWestSouth() {
    	Game game = launcher.makeGame();
    	Player player = game.getPlayers().get(0);
    	game.start();
    	game.freeze();
    	move(game, Direction.WEST, 6);
    	move(game, Direction.SOUTH, 2);
    	assertThat(player.getScore()).isEqualTo(80);
    }
    
    /**
     * Launch the game, and imitate what would happen in a typical game.
     * Players starts a game, then freezes the game and but does not move
     */
    @Test
    void startFreezeNoMovement() {
    	Game game = launcher.makeGame();
    	Player player = game.getPlayers().get(0);
    	game.start();
    	game.freeze();
    	move(game, Direction.WEST, 0);
    	assertThat(player.getScore()).isEqualTo(0);
    }
    
    
    /**
     * Launch the game, and imitate what would happen in a typical game.
     * Players starts a game, then freezes the game and but does not move
     * Player then unfreezes the game, and still does not move
     * Player should get eaten by ghost
     */
    @Test
    void startFreezeNoMovementUnfreezeNoMovement() throws InterruptedException {
    	Game game = launcher.makeGame();
    	Player player = game.getPlayers().get(0);
    	DefaultPlayerInteractionMap map = new DefaultPlayerInteractionMap();
    	game.start();
    	game.freeze();
    	move(game, Direction.WEST, 0);
    	assertThat(player.isAlive()).isTrue();
    	
    	game.unfreeze();
    	move(game, Direction.EAST, 0);
    	Thread.sleep(16000);
    	
    	assertThat(player.isAlive()).isFalse();
    }
   
    /**
     * Make number of moves in given direction.
     *
     * @param game The game we're playing
     * @param dir The direction to be taken
     * @param numSteps The number of steps to take
     */
    public static void move(Game game, Direction dir, int numSteps) {
        Player player = game.getPlayers().get(0);
        for (int i = 0; i < numSteps; i++) {
            game.move(player, dir);
        }
    }

}
