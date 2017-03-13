import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class DownloadManager extends JFrame implements Observer {

	// text field process of download
	private JTextField addTextField;

	private DownloadsTableModel tableModel;
	private JTable table;
	private JButton pauseBtn, resumeBtn, cancelBtn, clearBtn;
	// allocation memory
	private Download selectedDownload;
	// Flag,clear select or not
	private boolean clearing;

	public DownloadManager() {
		setTitle("Download Manager");
		setSize(640, 480);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				actionExit();
			}

			// Setting menu

		});
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		JMenuItem fileExitMenuItem = new JMenuItem("Exit", KeyEvent.VK_F);

		fileExitMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				actionExit();

			}
		});
		fileMenu.add(fileExitMenuItem);
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);

		// set adding Panel
		JPanel addPanel = new JPanel();
		addTextField = new JTextField(30);
		addPanel.add(addTextField);
		JButton addButton = new JButton("Add Download");
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				actionAdd();
			}
		});
		addPanel.add(addButton);

		// Setting table
		tableModel = new DownloadsTableModel();
		table = new JTable(tableModel);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				tableSelectionChanged();

			}
		});
		// only one row can be selected
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// setting progress renderer as visualizer
		ProgressRenderer renderer = new ProgressRenderer(0, 100);
		renderer.setStringPainted(true);
		table.setDefaultRenderer(JProgressBar.class, renderer);
		// set sufficiently height of row
		table.setRowHeight((int) renderer.getPreferredSize().getHeight());

		// setting download processes panel
		JPanel downloadsPanel = new JPanel();
		downloadsPanel.setBorder(BorderFactory.createTitledBorder("Downloads"));
		downloadsPanel.setLayout(new BorderLayout());
		downloadsPanel.add(new JScrollPane(table), BorderLayout.CENTER);

		// setting buttons panel
		JPanel btnPanel = new JPanel();
		pauseBtn = new JButton("Pause");
		pauseBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				actionPause();

			}
		});
		pauseBtn.setEnabled(false);
		btnPanel.add(pauseBtn);

		resumeBtn = new JButton("Resume");
		resumeBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				actionResume();

			}
		});
		resumeBtn.setEnabled(false);
		btnPanel.add(resumeBtn);

		cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				actionCancel();

			}
		});
		cancelBtn.setEnabled(false);
		btnPanel.add(cancelBtn);

		clearBtn = new JButton("Clear");
		clearBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				actionClear();

			}
		});
		clearBtn.setEnabled(false);
		btnPanel.add(clearBtn);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(addPanel, BorderLayout.NORTH);
		getContentPane().add(downloadsPanel, BorderLayout.CENTER);
		getContentPane().add(btnPanel, BorderLayout.SOUTH);

	}

	protected void actionCancel() {
		selectedDownload.cancel();
		updateButtons();

	}

	protected void actionClear() {
		clearing = true;
		tableModel.removeDownload(table.getSelectedRow());
		clearing = false;
		selectedDownload = null;
		updateButtons();
	}

	protected void actionResume() {
		selectedDownload.resume();
		updateButtons();

	}

	protected void actionPause() {
		selectedDownload.pause();
		updateButtons();

	}

	// call if row select was changed
	protected void tableSelectionChanged() {
		if (selectedDownload != null) {
			selectedDownload.deleteObserver(DownloadManager.this);
		}
		if (!clearing && table.getSelectedRow() > -1) {
			selectedDownload = tableModel.getDownload(table.getSelectedRow());
			selectedDownload.addObserver(DownloadManager.this);
			updateButtons();
		}

	}

	private void updateButtons() {
		if (selectedDownload != null) {
			int status = selectedDownload.getStatus();
			switch (status) {
			case Download.DOWNLOADING:
				pauseBtn.setEnabled(true);
				resumeBtn.setEnabled(false);
				cancelBtn.setEnabled(true);
				clearBtn.setEnabled(false);
				break;
			case Download.PAUSED:
				pauseBtn.setEnabled(false);
				resumeBtn.setEnabled(true);
				cancelBtn.setEnabled(true);
				clearBtn.setEnabled(false);
				break;
			case Download.ERROR:
				pauseBtn.setEnabled(false);
				resumeBtn.setEnabled(true);
				cancelBtn.setEnabled(false);
				clearBtn.setEnabled(true);
				break;
			default:// COMPLETE or CANCELLED
				pauseBtn.setEnabled(false);
				resumeBtn.setEnabled(false);
				cancelBtn.setEnabled(false);
				clearBtn.setEnabled(true);
			}
		} else {
			pauseBtn.setEnabled(false);
			resumeBtn.setEnabled(false);
			cancelBtn.setEnabled(false);
			clearBtn.setEnabled(false);
		}
	}

	protected void actionAdd() {
		URL veryfiedUrl = veryfiedUrl(addTextField.getText());
		if (veryfiedUrl != null) {
			tableModel.addDownload(new Download(veryfiedUrl));
			addTextField.setText("");
		} else
			JOptionPane.showMessageDialog(this, "Invalid Download URL", "Error", JOptionPane.ERROR_MESSAGE);

	}

	private URL veryfiedUrl(String url) {

		// permit only http address
//		if (!url.toLowerCase().startsWith("http://")) {
//			return null;
//		}
		URL veryfiedUrl = null;

		// check url format
		try {
			veryfiedUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
		// check if url contains file
		if (veryfiedUrl.getFile().length() < 2) {
			return null;
		}

		return veryfiedUrl;

	}

	protected void actionExit() {
		System.exit(0);

	}

	@Override
	public void update(Observable o, Object arg) {
		if (selectedDownload != null && selectedDownload.equals(o)) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					updateButtons();
				}
			});
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				DownloadManager manager = new DownloadManager();
				manager.setVisible(true);

			}

		});
	}
}
