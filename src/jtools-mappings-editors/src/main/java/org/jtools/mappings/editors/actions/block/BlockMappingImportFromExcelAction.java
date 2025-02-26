package org.jtools.mappings.editors.actions.block;

/*-
 * #%L
 * Java Tools - Mappings Editors
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

import javax.swing.Icon;

import org.jtools.mappings.block.importers.BlockMappingExcelImporter;
// TODO: Auto-generated Javadoc

/**
 * The Class BlockMappingImportFromExcelAction.
 */
public class BlockMappingImportFromExcelAction extends ABlockMappingImportFromAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5366162869248310896L;

	/**
	 * Instantiates a new block mapping import from excel action.
	 *
	 * @param name the name
	 * @param icon the icon
	 */
	public BlockMappingImportFromExcelAction(String name, Icon icon) {
		super(name, icon, BlockMappingExcelImporter.instance());
	}

	/**
	 * Instantiates a new block mapping import from excel action.
	 *
	 * @param name the name
	 */
	public BlockMappingImportFromExcelAction(String name) {
		super(name, BlockMappingExcelImporter.instance());
	}

}
