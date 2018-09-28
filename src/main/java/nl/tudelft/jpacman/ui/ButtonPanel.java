package nl.tudelft.jpacman.ui;

import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.ui.PacManUiBuilder;

/**
 * A panel containing a button for every registered action.
 *
 * @author Jeroen Roosen 
 */
class ButtonPanel extends JPanel {

    /**
     * Default serialisation ID.
     */
    private static final long serialVersionUID = 1L;
    private static boolean clickedOnce = true;

    /**
     * Create a new button panel with a button for every action.
     * @param buttons The map of caption - action for each button.
     * @param parent The parent frame, used to return window focus.
     */
    ButtonPanel(final Map<String, Action> buttons, final JFrame parent) {
        super();
        assert buttons != null;
        assert parent != null;
        
        
        for (final String caption : buttons.keySet()) {
            JButton button = new JButton(caption);
            if (caption.equals("Freeze")) {
            	button.addActionListener(e -> {
            		if(clickedOnce) {
                        buttons.get(caption).doAction();
                        parent.requestFocusInWindow();
                        clickedOnce = false;
                        
            		} else {
            			
            			buttons.get("UnFreeze").doAction();
            			parent.requestFocusInWindow();
            			clickedOnce = true;
            		}	
                 });
            	
            	add(button);
            	
            } else if (!caption.equals("UnFreeze")){
            	button.addActionListener(e -> {
                    buttons.get(caption).doAction();
                    parent.requestFocusInWindow();  
                });
            	add(button);
            }    
        }
    }
}
