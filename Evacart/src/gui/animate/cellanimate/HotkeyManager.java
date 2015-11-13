package gui.animate.cellanimate;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

public class HotkeyManager extends EventQueue
{
	private static final HotkeyManager instance = new HotkeyManager();
	private final InputMap keyStrokes = new InputMap();
	private final ActionMap actions = new ActionMap();
	static {
		// here we register ourselves as a new link in the chain of
		// responsibility
		Toolkit.getDefaultToolkit().getSystemEventQueue().push(instance);
	}
	
	private HotkeyManager()
	{
	}

	public static HotkeyManager getInstance()
	{
		return instance;
	}

	public InputMap getInputMap()
	{
		return keyStrokes;
	}
	
	public ActionMap getActionMap()
	{
		return actions;
	}
	
	protected void dispatchEvent(AWTEvent event)
	{
		if (event instanceof KeyEvent)
		{
			// KeyStroke.getKeyStrokeForEvent converts an ordinary KeyEvent
			// to a keystroke, as stored in the InputMap.
			KeyStroke ks = KeyStroke.getKeyStrokeForEvent((KeyEvent)event);
			String actionKey = (String)keyStrokes.get(ks);
			if (actionKey != null)
			{
				Action action = actions.get(actionKey);
				if (action != null && action.isEnabled())
				{
					action.actionPerformed(
						new ActionEvent(
							event.getSource(),
							event.getID(),
							actionKey,
							((KeyEvent)event).getModifiers()));
					return;
				}
			}
		}
		super.dispatchEvent(event);
	}
}
