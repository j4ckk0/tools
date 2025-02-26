package org.jtools.utils.gui.editor;

/*-
 * #%L
 * Java Tools - Utils
 * %%
 * Copyright (C) 2024 jtools.org
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.awt.Container;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
// TODO: Auto-generated Javadoc

/**
 * The Class AEditorAction.
 */
public abstract class AEditorAction extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3954850547522284177L;

	/** The desktop pane. */
	protected JDesktopPane desktopPane;

	/**
	 * Instantiates a new a editor action.
	 */
	protected AEditorAction() {
		super();
	}

	/**
	 * Instantiates a new a editor action.
	 *
	 * @param name the name
	 */
	protected AEditorAction(String name) {
		super(name);
	}

	/**
	 * Instantiates a new a editor action.
	 *
	 * @param name the name
	 * @param icon the icon
	 */
	protected AEditorAction(String name, Icon icon) {
		super(name, icon);
	}

	/**
	 * Gets the desktop pane.
	 *
	 * @return the desktop pane
	 */
	public JDesktopPane getDesktopPane() {
		return desktopPane;
	}

	/**
	 * Sets the desktop pane.
	 *
	 * @param desktopPane the new desktop pane
	 */
	public void setDesktopPane(JDesktopPane desktopPane) {
		this.desktopPane = desktopPane;
	}

	/**
	 * Show editor.
	 *
	 * @param mappingEditor the mapping editor
	 */
	public void showEditor(AEditor mappingEditor) {
		if (desktopPane != null) {
			JInternalFrame editorFrame = mappingEditor.showEditorAsInternalFrame();
			
			desktopPane.add(editorFrame);
			
			Container parent = desktopPane.getParent();
			if(parent instanceof JTabbedPane) {
				((JTabbedPane)parent).setSelectedComponent(desktopPane);
			}
			
			editorFrame.moveToFront();
		} else {
			mappingEditor.showEditorAsDialog(null, true);
		}
	}

}
