package nl.tudelft.jpacman.level;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Level.LevelObserver;
import nl.tudelft.jpacman.npc.Ghost;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests various aspects of level.
 *
 * @author Jeroen Roosen 
 */
// The four suppress warnings ignore the same rule, which results in 4 same string literals
@SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.TooManyStaticImports"})
class LevelTest {

    /**
     * The level under test.
     */
    private Level level;

    /**
     * An NPC on this level.
     */
    private final Ghost ghost = mock(Ghost.class);

    /**
     * An NPC on this level.
     */
    private final Ghost ghost2 = mock(Ghost.class);
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
     * The default interaction map.
     */
    private DefaultPlayerInteractionMap map;

    /**
     * Sets up the level with the default board, a single NPC and a starting
     * square.
     */
    
    private Launcher launcher;
    
    @BeforeEach
    void setUp() {
        final long defaultInterval = 100L;
        level = new Level(board, Lists.newArrayList(ghost, ghost2), Lists.newArrayList(
            square1, square2), collisions);
        map = new DefaultPlayerInteractionMap();
        launcher = new Launcher();
        when(ghost.getInterval()).thenReturn(defaultInterval);
        
    }

    /**
     * Validates the state of the level when it isn't started yet.
     */
    @Test
    void noStart() {
        assertThat(level.isInProgress()).isFalse();
    }

    /**
     * Validates the state of the level when it is stopped without starting.
     */
    @Test
    void stop() {
        level.stop();
        assertThat(level.isInProgress()).isFalse();
    }

    /**
     * Validates the state of the level when it is started.
     */
    @Test
    void start() {
        level.start();
        assertThat(level.isInProgress()).isTrue();
    }

    /**
     * Validates the state of the level when it is started then stopped.
     */
    @Test
    void startStop() {
        level.start();
        level.stop();
        assertThat(level.isInProgress()).isFalse();
    }
    
   
    /**
     * Verifies registering a player puts the player on the correct starting
     * square.
     */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    void registerPlayer() {
        Player p = mock(Player.class);
        level.registerPlayer(p);
        verify(p).occupy(square1);
    }

    /**
     * Verifies registering a player twice does not do anything.
     */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    void registerPlayerTwice() {
        Player p = mock(Player.class);
        level.registerPlayer(p);
        level.registerPlayer(p);
        verify(p, times(1)).occupy(square1);
    }

    /**
     * Verifies registering a second player puts that player on the correct
     * starting square.
     */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    void registerSecondPlayer() {
        Player p1 = mock(Player.class);
        Player p2 = mock(Player.class);
        level.registerPlayer(p1);
        level.registerPlayer(p2);
        verify(p2).occupy(square2);
    }

    /**
     * Verifies registering a third player puts the player on the correct
     * starting square.
     */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    void registerThirdPlayer() {
        Player p1 = mock(Player.class);
        Player p2 = mock(Player.class);
        Player p3 = mock(Player.class);
        level.registerPlayer(p1);
        level.registerPlayer(p2);
        level.registerPlayer(p3);
        verify(p3).occupy(square1);
    }
    
    //Testing DefaultInteractionMap by partions
    
    //Testing when player collides with ghost
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    void collidePlayerGhost() {
    	Game game = launcher.makeGame();
        Player p1 = game.getPlayers().get(0);
    	Ghost g1 = mock(Ghost.class);
    	map.collide(p1, g1);
    	assertThat(p1.isAlive()).isFalse();
    }
    
  //Testing when ghost collides with player
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    void collideGhostPlayer() {
    	Game game = launcher.makeGame();
        Player p1 = game.getPlayers().get(0);
    	Ghost g1 = mock(Ghost.class);
    	map.collide(g1, p1);
    	assertThat(p1.isAlive()).isFalse();
    }
    
    
    //Testing when player collides with pellet
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    void collidePlayerPellet() {
    	Game game = launcher.makeGame();
        Player p1 = game.getPlayers().get(0);
    	Pellet g1 = mock(Pellet.class);
    	map.collide(p1, g1);
    	assertThat(p1.isAlive()).isTrue();
    	assertEquals(p1.getScore(),g1.getValue());
    	
    }
    
    //Testing when pellet collides with player
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    void collidePelletPlayer() {
    	Game game = launcher.makeGame();
        Player p1 = game.getPlayers().get(0);
    	Pellet g1 = mock(Pellet.class);
    	map.collide(g1, p1);
    	assertThat(p1.isAlive()).isTrue();
    	assertEquals(p1.getScore(),g1.getValue());
    }

    //Testing when ghost collides with ghost
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    void collideGhostGhost() {
    	Game game = launcher.makeGame();
        Player p1 = game.getPlayers().get(0);
        Ghost g1 = mock(Ghost.class);
        Ghost g2 = mock(Ghost.class);
        
        //score of player before collide
        int oldScore = p1.getScore();
        map.collide(g1, g2);
        assertThat(p1.isAlive()).isTrue();
        
        //assert ghost collides have no effect on player's score
        assertEquals(oldScore, p1.getScore());
    }

    //Testing when pellet collides with pellet (Negative case)
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    void collidePelletPellet() {
        Pellet pe1 = mock(Pellet.class);
        Pellet pe2 = mock(Pellet.class);
        Game game = launcher.makeGame();
        Player p1 = game.getPlayers().get(0);
        
        //score of player before collide
        int oldScore = p1.getScore();
        map.collide(pe1, pe2);
        assertThat(p1.isAlive()).isTrue();
        //assert pellet collides have no effect on player's score
        assertEquals(oldScore, p1.getScore());

    }

    //Testing when Pellet collides with ghost
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    void collidePelletGhost() {
    	Pellet pe1 = mock(Pellet.class);
        Game game = launcher.makeGame();
        Player p1 = game.getPlayers().get(0);
        Ghost g1 = mock(Ghost.class);
        
        //score of player before collide
        int oldScore = p1.getScore();
        map.collide(pe1, g1);
        assertThat(p1.isAlive()).isTrue();
        
        //assert pellet collides with ghost have no effect on player's score
        assertEquals(oldScore, p1.getScore());
    }

    //Testing when Ghost collides with Pellet
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    void collideGhostPellet() {
        Pellet pe1 = mock(Pellet.class);
        Game game = launcher.makeGame();
        Player p1 = game.getPlayers().get(0);
        Ghost g1 = mock(Ghost.class);
        
        //score of player before collide
        int oldScore = p1.getScore();
        
        map.collide(g1, pe1);
        assertThat(p1.isAlive()).isTrue();
        
        //assert pellet collides with ghost have no effect on player's score
        assertEquals(oldScore, p1.getScore());
    }



}
