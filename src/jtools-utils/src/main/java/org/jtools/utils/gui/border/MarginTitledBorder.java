package org.jtools.utils.gui.border;

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

import java.awt.Color;

import javax.swing.border.TitledBorder;
// TODO: Auto-generated Javadoc

/**
 * The Class MarginTitledBorder.
 */
public class MarginTitledBorder extends TitledBorder {

	//////////////////////////////////////////////////
	//
	// Class variables and constants
	//
	//////////////////////////////////////////////////
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4813496499535613615L;

	//////////////////////////////////////////////////
	//
	// Constructors
	//
	//////////////////////////////////////////////////
	
	/**
	 * Instantiates a new margin titled border.
	 *
	 * @param title the title
	 * @param lineColor the line color
	 * @param topMargin the top margin
	 * @param leftMargin the left margin
	 * @param bottomMargin the bottom margin
	 * @param rightMargin the right margin
	 */
	public MarginTitledBorder(String title, Color lineColor, final int topMargin, final int leftMargin,
			final int bottomMargin, final int rightMargin) {
		super(new MarginLineBorder(lineColor, topMargin, leftMargin, bottomMargin, rightMargin), title);
	}
	
	/**
	 * Instantiates a new margin titled border.
	 *
	 * @param title the title
	 * @param topMargin the top margin
	 * @param leftMargin the left margin
	 * @param bottomMargin the bottom margin
	 * @param rightMargin the right margin
	 */
	public MarginTitledBorder(String title, final int topMargin, final int leftMargin, final int bottomMargin,
			final int rightMargin) {
		super(new MarginLineBorder(topMargin, leftMargin, bottomMargin, rightMargin), title);
	}

}
