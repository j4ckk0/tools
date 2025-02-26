package org.jtools.gui.table.utils;

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
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.MenuElement;
import javax.swing.MenuSelectionManager;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

// TODO: Auto-generated Javadoc
/**
 * The Class TableColumnManager.
 */
public class TableColumnManager
	implements MouseListener, ActionListener, TableColumnModelListener, PropertyChangeListener
{
	
	/** The table. */
	private JTable table;
	
	/** The tcm. */
	private TableColumnModel tcm;
	
	/** The menu popup. */
	private boolean menuPopup;

	/** The all columns. */
	private List<TableColumn> allColumns;
	
	/**
	 * Instantiates a new table column manager.
	 *
	 * @param table the table
	 */
	public TableColumnManager(JTable table)
	{
		this(table, true);
	}
	
	/**
	 * Instantiates a new table column manager.
	 *
	 * @param table the table
	 * @param menuPopup the menu popup
	 */
	public TableColumnManager(JTable table, boolean menuPopup)
	{
		this.table = table;
		setMenuPopup( menuPopup );

		table.addPropertyChangeListener( this );
		reset();
	}
	
	/**
	 * Reset.
	 */
	public void reset()
	{
		table.getColumnModel().removeColumnModelListener( this );
		tcm = table.getColumnModel();
		tcm.addColumnModelListener( this );

		//  Keep a duplicate TableColumns for managing hidden TableColumns

		int count = tcm.getColumnCount();
		allColumns = new ArrayList<TableColumn>(count);

		for (int i = 0; i < count; i++)
		{
			allColumns.add( tcm.getColumn( i ) );
		}
	}

	/**
	 *  Get the popup support.
	 *
	 *  @return the popup support
	 */
	public boolean isMenuPopup()
	{
		return menuPopup;
	}

	/**
	 *  Add/remove support for a popup menu to the table header. The popup
	 *  menu will give the user control over which columns are visible.
	 *
	 * @param menuPopup the new menu popup
	 */
	public void setMenuPopup(boolean menuPopup)
	{
		table.getTableHeader().removeMouseListener( this );

		if (menuPopup)
			table.getTableHeader().addMouseListener( this );

		this.menuPopup = menuPopup;
	}

	/**
	 *  Hide a column from view in the table.
	 *
	 * @param modelColumn the model column
	 */
	public void hideColumn(int modelColumn)
	{
		int viewColumn = table.convertColumnIndexToView( modelColumn );

		if (viewColumn != -1)
		{
			TableColumn column = tcm.getColumn(viewColumn);
			hideColumn(column);
		}
	}

	/**
	 *  Hide a column from view in the table.
	 *
	 * @param columnName the column name
	 */
	public void hideColumn(Object columnName)
	{
		if (columnName == null) return;

		for (int i = 0; i < tcm.getColumnCount(); i++)
		{
			TableColumn column = tcm.getColumn( i );

			if (columnName.equals(column.getHeaderValue()))
			{
				hideColumn( column );
				break;
			}
		}
	}

	/**
	 *  Hide a column from view in the table.
	 *
	 * @param column the column
	 */
	public void hideColumn(TableColumn column)
	{
		if (tcm.getColumnCount() == 1) return;

		//  Ignore changes to the TableColumnModel made by the TableColumnManager

		tcm.removeColumnModelListener( this );
		tcm.removeColumn( column );
		tcm.addColumnModelListener( this );
	}

	/**
	 *  Show a hidden column in the table.
	 *
	 * @param modelColumn the model column
	 */
	public void showColumn(int modelColumn)
	{
		for (TableColumn column : allColumns)
		{
			if (column.getModelIndex() == modelColumn)
			{
				showColumn( column );
				break;
			}
		}
	}

	/**
	 *  Show a hidden column in the table.
	 *
	 * @param columnName the column name
	 */
	public void showColumn(Object columnName)
	{
		for (TableColumn column : allColumns)
		{
			if (column.getHeaderValue().equals(columnName))
			{
				showColumn( column );
				break;
			}
		}
	}

	/**
	 *  Show a hidden column in the table. The column will be positioned
	 *  at its proper place in the view of the table.
	 *
	 * @param column the column
	 */
	private void showColumn(TableColumn column)
	{
		//  Ignore changes to the TableColumnModel made by the TableColumnManager

		tcm.removeColumnModelListener( this );

		//  Add the column to the end of the table

		tcm.addColumn( column );

		//  Move the column to its position before it was hidden.
		//  (Multiple columns may be hidden so we need to find the first
		//  visible column before this column so the column can be moved
		//  to the appropriate position)

		int position = allColumns.indexOf( column );
		int from = tcm.getColumnCount() - 1;
		int to = 0;

		for (int i = position - 1; i > -1; i--)
		{
			try
			{
				TableColumn visibleColumn = allColumns.get( i );
				to = tcm.getColumnIndex( visibleColumn.getHeaderValue() ) + 1;
				break;
			}
			catch(IllegalArgumentException e) {}
		}

		tcm.moveColumn(from, to);

		tcm.addColumnModelListener( this );
	}
//
//  Implement MouseListener
/**
 * Mouse pressed.
 *
 * @param e the e
 */
//
	public void mousePressed(MouseEvent e)
	{
		checkForPopup( e );
	}

	/**
	 * Mouse released.
	 *
	 * @param e the e
	 */
	public void mouseReleased(MouseEvent e)
	{
		checkForPopup( e );
	}

	/**
	 * Mouse clicked.
	 *
	 * @param e the e
	 */
	public void mouseClicked(MouseEvent e) {}
	
	/**
	 * Mouse entered.
	 *
	 * @param e the e
	 */
	public void mouseEntered(MouseEvent e) {}
	
	/**
	 * Mouse exited.
	 *
	 * @param e the e
	 */
	public void mouseExited(MouseEvent e) {}

	/**
	 * Check for popup.
	 *
	 * @param e the e
	 */
	private void checkForPopup(MouseEvent e)
	{
		if (e.isPopupTrigger())
		{
			JTableHeader header = (JTableHeader)e.getComponent();
			int column = header.columnAtPoint( e.getPoint() );
			showPopup(column);
		}
	}

	/**
	 * Show popup.
	 *
	 * @param index the index
	 */
	/*
	 *  Show a popup containing items for all the columns found in the
	 *  table column manager. The popup will be displayed below the table
	 *  header columns that was clicked.
	 *
	 *  @param  index  index of the table header column that was clicked
	 */
	private void showPopup(int index)
	{
		Object headerValue = tcm.getColumn( index ).getHeaderValue();
		int columnCount = tcm.getColumnCount();
		JPopupMenu popup = new SelectPopupMenu();

		//  Create a menu item for all columns managed by the table column
		//  manager, checking to see if the column is shown or hidden.

		for (TableColumn tableColumn : allColumns)
		{
			Object value = tableColumn.getHeaderValue();
			JCheckBoxMenuItem item = new JCheckBoxMenuItem( value.toString() );
			item.addActionListener( this );

			try
			{
				tcm.getColumnIndex( value );
				item.setSelected( true );

				if (columnCount == 1)
					item.setEnabled( false );
			}
			catch(IllegalArgumentException e)
			{
				item.setSelected( false );
			}

			popup.add( item );

			if (value == headerValue)
				popup.setSelected( item );
		}

		//  Display the popup below the TableHeader

		JTableHeader header = table.getTableHeader();
		Rectangle r = header.getHeaderRect( index );
		popup.show(header, r.x, r.height);
	}
//
//  Implement ActionListener
//
	/**
 * Action performed.
 *
 * @param event the event
 */
/*
	 *  A table column will either be added to the table or removed from the
	 *  table depending on the state of the menu item that was clicked.
	 */
	public void actionPerformed(ActionEvent event)
	{
		if (event.getSource() instanceof AbstractButton)
		{
			AbstractButton button = (AbstractButton)event.getSource();
			String column = event.getActionCommand();

			if (button.isSelected())
				showColumn(column);
			else
				hideColumn(column);
		}
	}
//
//  Implement TableColumnModelListener
/**
 * Column added.
 *
 * @param e the e
 */
//
	public void columnAdded(TableColumnModelEvent e)
	{
		//  A table column was added to the TableColumnModel so we need
		//  to update the manager to track this column

		TableColumn column = tcm.getColumn( e.getToIndex() );

		if (allColumns.contains( column ))
			return;
		else
			allColumns.add( column );
	}

	/**
	 * Column moved.
	 *
	 * @param e the e
	 */
	public void columnMoved(TableColumnModelEvent e)
	{
		if (e.getFromIndex() == e.getToIndex()) return;

		//  A table column has been moved one position to the left or right
		//  in the view of the table so we need to update the manager to
		//  track the new location

		int index = e.getToIndex();
		TableColumn column = tcm.getColumn( index );
		allColumns.remove( column );

		if (index == 0)
		{
			allColumns.add(0, column);
		}
		else
		{
			index--;
			TableColumn visibleColumn = tcm.getColumn( index );
			int insertionColumn = allColumns.indexOf( visibleColumn );
			allColumns.add(insertionColumn + 1, column);
		}
	}

	/**
	 * Column margin changed.
	 *
	 * @param e the e
	 */
	public void columnMarginChanged(ChangeEvent e) {}
	
	/**
	 * Column removed.
	 *
	 * @param e the e
	 */
	public void columnRemoved(TableColumnModelEvent e) {}
	
	/**
	 * Column selection changed.
	 *
	 * @param e the e
	 */
	public void columnSelectionChanged(ListSelectionEvent e) {}
//
//  Implement PropertyChangeListener
/**
 * Property change.
 *
 * @param e the e
 */
//
	public void propertyChange(PropertyChangeEvent e)
	{
		if ("model".equals(e.getPropertyName()))
		{
			if (table.getAutoCreateColumnsFromModel())
				reset();
		}
	}

	/**
	 * The Class SelectPopupMenu.
	 */
	/*
	 *  Allows you to select a specific menu item when the popup is
	 *  displayed. (ie. this is a bug? fix)
	 */
	class SelectPopupMenu extends JPopupMenu
	{
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = -8652771941482916156L;

		/**
		 * Sets the selected.
		 *
		 * @param sel the new selected
		 */
		@Override
		public void setSelected(Component sel)
		{
			int index = getComponentIndex( sel );
			getSelectionModel().setSelectedIndex(index);
			final MenuElement me[] = new MenuElement[2];
			me[0]=(MenuElement)this;
			me[1]=getSubElements()[index];

			SwingUtilities.invokeLater(new Runnable()
			{
				public void run()
				{
					MenuSelectionManager.defaultManager().setSelectedPath(me);
				}
			});
		}
	};
}
