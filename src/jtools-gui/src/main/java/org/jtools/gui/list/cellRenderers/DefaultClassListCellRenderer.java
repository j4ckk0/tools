package org.jtools.gui.list.cellRenderers;

/*-
 * #%L
 * Java Tools - GUI
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

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
// TODO: Auto-generated Javadoc

/**
 * The Class DefaultClassListCellRenderer.
 */
public class DefaultClassListCellRenderer extends DefaultListCellRenderer {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6782564778984334994L;

	/**
	 * Instantiates a new default class list cell renderer.
	 */
	public DefaultClassListCellRenderer() {
		super();
	}

	/**
	 * Gets the list cell renderer component.
	 *
	 * @param list the list
	 * @param value the value
	 * @param index the index
	 * @param isSelected the is selected
	 * @param cellHasFocus the cell has focus
	 * @return the list cell renderer component
	 */
	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		if (c instanceof JLabel && value instanceof Class<?>) {
			String className = ((Class<?>) value).getCanonicalName();
			((JLabel) c).setText(className);
		}
		return c;
	}

}
