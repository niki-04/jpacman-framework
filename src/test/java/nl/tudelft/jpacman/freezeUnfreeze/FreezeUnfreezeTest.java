package nl.tudelft.jpacman.freezeUnfreeze;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.npc.ghost.Navigation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the Freeze/Unfreeze feature provided by the {@link Level} class.
 *
 * @author Nikita Dua,Radhika Krishnan
 *
 */

public class FreezeUnfreezeTest {
	
	private Launcher launcher;
	
	/**
     * The game under test.
     */
    private Game game;
    
	/**
     * Launch the user interface.
     */
    @BeforeEach
    void setUpPacman() {
        launcher = new Launcher();
        launcher.launch();
        game = launcher.getGame();
    }

    /**
     * Quit the user interface when we're done.
     */
    @AfterEach
    void tearDown() {
        launcher.dispose();
    }
    
     
      //Decision Table Strategy Tests
      
      /**
       * Validates the state of the game when: 
       * Start Button is clicked -> Stop Button is clicked -> Freeze Button is clicked.
       */
      @Test
      void startStopFreeze() {
      	game.start();
      	game.stop();
      	game.freeze();
      	assertThat(game.isInProgress()).isFalse();
      }
      
      /**
       * Validates the state of the game when: 
       * Start Button is clicked -> Stop Button is clicked -> Freeze Button is clicked -> Freeze Button is clicked again.
       */
      @Test
      void startStopFreezeUnfreeze() {
      	game.start();
      	game.stop();
      	game.freeze();
      	game.unfreeze();
      	assertThat(game.isInProgress()).isFalse();
      }
      
      /**
       * Validates the state of the game when: 
       * Start Button is clicked -> Freeze Button is clicked
       */
      @Test
      void startFreeze() {
      	game.start();
      	assertThat(game.isInProgress()).isTrue();
      	game.freeze();
      	assertThat(game.isInProgress()).isTrue();
      }
      
      /**
       * Validates the state of the game when: 
       * Start Button is clicked -> Freeze Button is clicked  -> Freeze Button is clicked again
       */
      @Test
      void startFreezeUnfreeze() {
      	game.start();
      	game.freeze();
      	game.unfreeze();
      	assertThat(game.isInProgress()).isTrue();
      }
      
      /**
       * Validates the state of the game when: 
       * Freeze Button is clicked  -> Freeze Button is clicked again, before game has started
       */
      @Test
      void freezeUnfreeze() {
      	game.freeze();
      	game.unfreeze();
      	assertThat(game.isInProgress()).isFalse();
      }
      
      
      /**
       * Validates the state of the game when: 
       * Stop Button is clicked -> Freeze Button is clicked, before game has started
       */
      @Test
      void stopFreeze() {
      	game.stop();
      	game.freeze();
      	assertThat(game.isInProgress()).isFalse();
      }
      
      /**
       * Validates the state of the game when: 
       * Stop Button is clicked -> Freeze Button is clicked -> Freeze Button is clicked again, before game has started
       */
      @Test
      void stopFreezeUnfreeze() {
      	game.stop();
      	game.freeze();
      	game.unfreeze();
      	assertThat(game.isInProgress()).isFalse();
      }
      
        
        //Functionality testing strategy tests;
        
        /**
         * Launch the game, and imitate what would happen in a typical game.
         * Player starts a game, then freezes the game and moves 1 unit to the EAST and 2 units North
         */
        @Test
        void startFreezeMoveEastNorth() {
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
        	Player player = game.getPlayers().get(0);
        	game.start();
        	game.freeze();
        	move(game, Direction.WEST, 6);
        	move(game, Direction.SOUTH, 2);
        	assertThat(player.getScore()).isEqualTo(80);
        }
        
        /**
         * Launch the game, and imitate what would happen in a typical game.
         * Players starts a game, then freezes the game and moves 6 units to the WEST 
         * Player unfreezes the game and moves 2 units South
         */
        @Test
        void startFreezeMoveWestUnfreezeMoveSouth() {
        	Player player = game.getPlayers().get(0);
        	game.start();
        	game.freeze();
        	move(game, Direction.WEST, 6);
        	game.unfreeze();
        	move(game, Direction.SOUTH, 2);
        	assertThat(player.getScore()).isEqualTo(80);
        }
        
        
        /**
         * Launch the game, and imitate what would happen in a typical game.
         * Players starts a game, then freezes the game and but does not move
         */
        @Test
        void startFreezeNoMovement() {
        	Player player = game.getPlayers().get(0);
        	game.start();
        	game.freeze();
        	move(game, Direction.WEST, 0);
        	assertThat(player.getScore()).isEqualTo(0);
        }
        
        
        /**
         * Launch the game, and imitate what would happen in a typical game.
         * Players starts a game, then freezes the game, and moves 1 unit to the WEST
         * The thread is put to sleep for 3 seconds to let the ghosts move.
         * Assertion is done to ensure the ghost's initial square unit location is not changed after thread.sleep
         * Player then unfreezes the game, and moves 2 units to the SOUTH
         * The thread is put to sleep again for 3 seconds to let the ghosts move.
         * Assertion is done to ensure the ghost's initial square unit location is changed after thread.sleep
         */
        @Test
        void startFreezeNoMovementUnfreezeMovement() throws InterruptedException {
            game.start();
            game.freeze();
            Ghost ghost = Navigation.findUnitInBoard(Ghost.class, game.getLevel().getBoard());
            Square ghostInitialLocation = ghost.getSquare();
            move(game, Direction.WEST, 1);
            Thread.sleep(3000);
            Square ghostNewLocation = ghost.getSquare();
            assertThat(ghostInitialLocation).isEqualTo(ghostNewLocation);
            game.unfreeze();
            move(game, Direction.SOUTH, 2);
            Thread.sleep(3000);
            Square ghostNewLocation2 = ghost.getSquare();
            assertThat(ghostNewLocation).isNotEqualTo(ghostNewLocation2);
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
