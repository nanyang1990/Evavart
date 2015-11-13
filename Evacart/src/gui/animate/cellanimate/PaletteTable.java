package gui.animate.cellanimate;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

/**
 * Modifies the JTable to allow colors or strings in a column
 */
public class PaletteTable extends JTable
{

	private JTextField textField = new JTextField();
	private DefaultCellEditor defaultCellEditor =
		new DefaultCellEditor(textField);

	PaletteTableModel dataModel = new PaletteTableModel();

	public PaletteTable()
	{

		this.setModel(dataModel);

/*		dataModel.addTableModelListener(new TableModelListener()
		{

			public void tableChanged(TableModelEvent e)
			{
				int deletedRow = e.getFirstRow();
				int rowCount = getRowCount();

				clearSelection();
				if (e.getType() == TableModelEvent.DELETE)
				{
					if (deletedRow == rowCount)
					{
						if (rowCount > 0)
						{
							setRowSelectionInterval(deletedRow - 1,	deletedRow - 1);
						}
					}
					else
					{
//						if (deletedRow == rowCount - 1)
//						{
//							setRowSelectionInterval(deletedRow, deletedRow);
//						}
//						else
//						{
							setRowSelectionInterval(deletedRow ,deletedRow );
//						}
					}
				}
			}
		});
*/
		TableColumn colorColumn = this.getColumn("Color");

		setDefaultEditor(Object.class, defaultCellEditor);

		// Change the color column's renderer to show the color or the file name.
		DefaultTableCellRenderer colorColumnRenderer =
			new DefaultTableCellRenderer()
		{
			public void setValue(Object value)
			{
				Color cellValueC =
					(value instanceof Color) ? ((Color)value) : null;
				String cellValueT =
					(value instanceof String)
						? ((String)value)
						: new String("");
				setBackground(cellValueC);
				setForeground(Color.black);
				super.setValue(cellValueT);
			}
		};
		colorColumn.setCellRenderer(colorColumnRenderer);
		setSelectionMode(0);
		setDragEnabled(false);
		this.getColumn("To").setPreferredWidth(6);
		this.getColumn("From").setPreferredWidth(6);
	}
	protected void setData(Object[][] data)
	{
		dataModel.setData(data);
	}

	protected Object[][] getData()
	{
		return (dataModel.getData());
	}

	public Component prepareEditor(TableCellEditor editor, int row, int col)
	{
		Component result = super.prepareEditor(editor, row, col);
		if (result instanceof JTextField)
		{
			((JTextField)result).selectAll();
			textField.requestFocus();
		}
		return result;
	}

}
