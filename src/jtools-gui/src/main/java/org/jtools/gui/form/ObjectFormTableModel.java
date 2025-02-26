package org.jtools.gui.form;

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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.jtools.gui.table.tableModels.ITableModelWithCellsCustomAlignment;
import org.jtools.gui.table.tableModels.ITableModelWithParameterizedObjectWrapper;
import org.jtools.utils.objects.ObjectInfoProvider.ObjectInfo;
// TODO: Auto-generated Javadoc

/**
 * The Class ObjectFormTableModel.
 */
class ObjectFormTableModel extends DefaultTableModel
implements ITableModelWithCellsCustomAlignment, ITableModelWithParameterizedObjectWrapper {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5429348199335029848L;

	/** The Constant FIELD_LABEL_COLUMN_INDEX. */
	private static final int FIELD_LABEL_COLUMN_INDEX = 0;
	
	/** The Constant FIELD_VALUE_COLUMN_INDEX. */
	private static final int FIELD_VALUE_COLUMN_INDEX = 1;

	/** The Constant columns. */
	private static final int[] columns = new int[] { FIELD_LABEL_COLUMN_INDEX, FIELD_VALUE_COLUMN_INDEX };

	/** The object. */
	protected final transient Object object;
	
	/** The object info. */
	protected final transient ObjectInfo objectInfo;

	/**
	 * Instantiates a new object form table model.
	 *
	 * @param object the object
	 * @param objectInfo the object info
	 */
	public ObjectFormTableModel(Object object, ObjectInfo objectInfo) {
		this.object = object;
		this.objectInfo = objectInfo;
	}

	/**
	 * Gets the field.
	 *
	 * @param row the row
	 * @return the field
	 */
	public Field getField(int row) {
		return objectInfo.getPossibleFields().get(row);
	}

	/**
	 * Gets the row count.
	 *
	 * @return the row count
	 */
	@Override
	public int getRowCount() {
		if (objectInfo != null) {
			return objectInfo.getPossibleFieldsCount();
		} else {
			return 0;
		}
	}

	/**
	 * Gets the value at.
	 *
	 * @param row the row
	 * @param column the column
	 * @return the value at
	 */
	@Override
	public Object getValueAt(int row, int column) {
		Field field = getField(row);
		switch (column) {
		case FIELD_LABEL_COLUMN_INDEX:
			return field.getName() + ": ";
		case FIELD_VALUE_COLUMN_INDEX:
			try {
				Method getter = objectInfo.findGetter(field);
				if (getter != null) {
					return getter.invoke(object);
				} else {
					Logger.getLogger(getClass().getName()).log(Level.FINE,
							"getter not found for field " + field.getName());
					return null;
				}
			} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
				Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
				return null;
			}
		default:
			throw new IllegalArgumentException("Unexpected value: " + column);
		}
	}

	/**
	 * Sets the value at.
	 *
	 * @param value the value
	 * @param row the row
	 * @param column the column
	 */
	@Override
	public void setValueAt(Object value, int row, int column) {
		Field field = getField(row);
		switch (column) {
		case FIELD_LABEL_COLUMN_INDEX:
			return;
		case FIELD_VALUE_COLUMN_INDEX:
			try {
				Method setter = objectInfo.findSetter(field);
				if (setter != null) {
					setter.invoke(object, value);
					return;
				} else {
					Logger.getLogger(getClass().getName()).log(Level.FINE,
							"setter not found for field " + field.getName());
				}
			} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
				Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
				return;
			}
		default:
			throw new IllegalArgumentException("Unexpected value: " + column);
		}
	}

	/**
	 * Gets the column class.
	 *
	 * @param column the column
	 * @return the column class
	 */
	@Override
	public Class<?> getColumnClass(int column) {
		switch (column) {
		case FIELD_LABEL_COLUMN_INDEX:
			return String.class;
		case FIELD_VALUE_COLUMN_INDEX:
			return ObjectWrapper.class;
		default:
			throw new IllegalArgumentException("Unexpected value: " + column);
		}
	}

	/**
	 * Gets the column count.
	 *
	 * @return the column count
	 */
	@Override
	public int getColumnCount() {
		return columns.length;
	}

	/**
	 * Gets the column name.
	 *
	 * @param column the column
	 * @return the column name
	 */
	@Override
	public String getColumnName(int column) {
		switch (column) {
		case FIELD_LABEL_COLUMN_INDEX:
			return "Name";
		case FIELD_VALUE_COLUMN_INDEX:
			return "Value";
		default:
			throw new IllegalArgumentException("Unexpected value: " + column);
		}
	}

	/**
	 * Checks if is cell editable.
	 *
	 * @param row the row
	 * @param column the column
	 * @return true, if is cell editable
	 */
	@Override
	public boolean isCellEditable(int row, int column) {
		switch (column) {
		case FIELD_LABEL_COLUMN_INDEX:
			return false;
		case FIELD_VALUE_COLUMN_INDEX:
			return true;
		default:
			throw new IllegalArgumentException("Unexpected value: " + column);
		}
	}

	/**
	 * Gets the cell horizontal alignment.
	 *
	 * @param row the row
	 * @param column the column
	 * @return the cell horizontal alignment
	 */
	@Override
	public int getCellHorizontalAlignment(int row, int column) {
		switch (column) {
		case FIELD_LABEL_COLUMN_INDEX:
			return SwingConstants.RIGHT;
		case FIELD_VALUE_COLUMN_INDEX:
			return SwingConstants.CENTER;
		default:
			throw new IllegalArgumentException("Unexpected value: " + column);
		}
	}

	/**
	 * Gets the wrapped class at.
	 *
	 * @param row the row
	 * @param column the column
	 * @return the wrapped class at
	 */
	@Override
	public Class<?> getWrappedClassAt(int row, int column) {
		Field field = getField(row);
		return field.getType();
	}

	/**
	 * Gets the wrapped value at.
	 *
	 * @param row the row
	 * @param column the column
	 * @return the wrapped value at
	 */
	@Override
	public Object getWrappedValueAt(int row, int column) {
		return getValueAt(row, column);
	}

	/**
	 * Gets the wrapped parameterized class.
	 *
	 * @param row the row
	 * @param column the column
	 * @return the wrapped parameterized class
	 */
	@Override
	public Class<?> getWrappedParameterizedClass(int row, int column) {
		Field field = getField(row);
		if(field.getGenericType() instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
			return (Class<?>) parameterizedType.getActualTypeArguments()[0];
		}
		return null;
	}
}
