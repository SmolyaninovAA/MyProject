import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JProgressBar;
import javax.swing.table.AbstractTableModel;

public class DownloadsTableModel extends AbstractTableModel implements Observer {

	private static final String[] columnNames = { "URL", "Size", "Progress", "Status" };

	private static final Class[] columnClasses = { String.class, String.class, JProgressBar.class, String.class };

	private ArrayList<Download> downloadList = new ArrayList<Download>();

	public void addDownload(Download download) {
		// registration to getting notification
		download.addObserver(this);
		downloadList.add(download);

		// creating notification for table about inserted row
		fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
	}

	public Download getDownload(int row) {
		return downloadList.get(row);
	}

	public void removeDownload(int row) {
		downloadList.remove(row);
		// creating notification for table about deleted row
		fireTableRowsDeleted(row, row);
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public int getRowCount() {
		return downloadList.size();
	}

	public Class getColumnClass(int col) {
		return columnClasses[col];
	}

	@Override
	public Object getValueAt(int row, int col) {

		Download download = downloadList.get(row);

		switch (col) {
		case 0: // URL
			return download.getUrl();
		case 1: // Size
			int size = download.getSize();
			return (size == -1) ? "" : Integer.toString(size);
		case 2: // Progress
			return new Float(download.getProgress());
		case 3: // Status
			return Download.STATUSES[download.getStatus()];

		}
		return "";
	}

	@Override
	public void update(Observable o, Object arg) {
		int index = downloadList.indexOf(o);

		fireTableRowsUpdated(index, index);

	}

}
