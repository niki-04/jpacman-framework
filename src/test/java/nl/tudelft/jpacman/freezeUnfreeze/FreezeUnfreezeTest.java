package nl.tudelft.jpacman.freezeUnfreeze;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.CollisionMap;
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
        launcher.launch();
        level = new Level(board, Lists.newArrayList(ghost), Lists.newArrayList(
                square1, square2), collisions);
    }
    
    /**
     * Quit the user interface when we're done.
     */
    @AfterEach
    void tearDown() {
        launcher.dispose();
    }
    
    /**
     * Launch the game, and imitate what would happen in a typical game.
     * The test is only a smoke test, and not a focused small test.
     * Therefore it is OK that the method is a bit too long.
     *
     * @throws InterruptedException Since we're sleeping in this test.
     */
    @SuppressWarnings({"magicnumber", "methodlength", "PMD.JUnitTestContainsTooManyAsserts"})
    @Test
    void smokeTest() throws InterruptedException {
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);

        // start cleanly.
        assertThat(game.isInProgress()).isFalse();
        game.start();
        assertThat(game.isInProgress()).isTrue();
        assertThat(player.getScore()).isZero();
        
        // freeze the game cleanly.
        game.freeze();
        assertThat(game.isInProgress()).isTrue();
        assertThat(player.getScore()).isZero();
        
        // unfreeze the game cleanly
        game.unfreeze();
        assertThat(game.isInProgress()).isTrue();
        assertThat(player.getScore()).isZero();
        
        // stop the game
        game.stop();
        assertThat(game.isInProgress()).isFalse();
        assertThat(player.getScore()).isZero();
        
        // start the game
        game.start();
        assertThat(game.isInProgress()).isTrue();
        assertThat(player.getScore()).isZero();
        
        // freeze the game cleanly.
        game.freeze();
        assertThat(game.isInProgress()).isTrue();
        assertThat(player.getScore()).isZero();
        
        
        // move the player EAST once and get points
        game.move(player, Direction.EAST);
        assertThat(player.getScore()).isEqualTo(10);
        
        // move the player EAST again and get points
        game.move(player, Direction.EAST);
        assertThat(player.getScore()).isEqualTo(20);
        
        //unfreeze the game 
        game.unfreeze();
        assertThat(game.isInProgress()).isTrue();
        assertThat(player.getScore()).isEqualTo(20);
        
        // move back WEST once, does not change the score
        game.move(player, Direction.WEST);
        assertThat(player.getScore()).isEqualTo(20);
        
        // freeze the game cleanly.
        game.freeze();
        assertThat(game.isInProgress()).isTrue();
        assertThat(player.getScore()).isEqualTo(20);

        // move the player far NORTH
        move(game, Direction.NORTH, 2);
        assertThat(player.getScore()).isEqualTo(40);
        
        game.unfreeze();
        assertThat(game.isInProgress()).isTrue();
        assertThat(player.getScore()).isEqualTo(40);
        
        Thread.sleep(500L);
        
        
        game.stop();
        assertThat(game.isInProgress()).isFalse();
       
    }
    

    /**
     * Testing Transition states between Start/Stop and Freeze/Unfreeze.
     */
    /**
     * Validates the state of the level when the level is frozen before starting the game.
     */
    @Test
    void freezeStart() {
    	level.freeze();
    	assertThat(level.isInProgress()).isFalse();
    	level.start();
    	assertThat(level.isInProgress()).isTrue();
    }
    
    /**
     * Validates the state of the level when the level is started and is frozen after.
     */
    @Test
    void startFreeze() {
    	level.start();
    	assertThat(level.isInProgress()).isTrue();
    	level.freeze();
    	assertThat(level.isInProgress()).isTrue();
    }
    
    /**
     * Validates the state of the level when the level is started -> frozen -> unfrozen -> stopped.
     */
    @Test
    void startFreezeUnFreezeStop() {
    	level.start();
    	assertThat(level.isInProgress()).isTrue();
    	level.freeze();
    	assertThat(level.isInProgress()).isTrue();
    	level.unfreeze();
    	assertThat(level.isInProgress()).isTrue();
    	level.stop();
    	assertThat(level.isInProgress()).isFalse();
    }
    
    /**
     * Validates the state of the level when the level is stopped -> frozen.
     */
    @Test
    void stopFreeze() {
    	level.start();
    	assertThat(level.isInProgress()).isTrue();
    	level.stop();
    	assertThat(level.isInProgress()).isFalse();
    	level.freeze();
    	assertThat(level.isInProgress()).isFalse();
    }
    
    /**
     * Validates the state of the level when the level is not started but frozen -> unfrozen is clicked.
     */
    @Test
    void freezeUnfreeze() {
    	level.freeze();
    	assertThat(level.isInProgress()).isFalse();
    	level.unfreeze();
    	assertThat(level.isInProgress()).isFalse();
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
