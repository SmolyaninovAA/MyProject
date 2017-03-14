package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FocusTraversalPolicy;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

//import org.eclipse.wb.swing.FocusTraversalOnArray;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4957374654460463194L;
	private Data data;
	private JTextField mDetTF, mCastTF, mGFSTF, percentOfMarriageTF, percentOfNewMetallTF, mGFSAnswer, mCastAnswer,
			mDetAnswer, normOfRateAnswer, mLossesAnswer, mBatchAnswer, mOfNewMetallAnswer, recoveryAnswer;

	String kindOfAlloy;
	String kindOfCasting;
	double percentOfMarriage;
	double percentOfNewMetall;
	boolean duplex;
	double mDet;
	double mCast;
	double mGFS;

	private JComboBox<String> boxKindOfCasting, boxkindOfAlloy;
	private JCheckBox duplexCB;

	public MainWindow() {

		super("Расчет шихты по ОСТ 1.42141-82");

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

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setResizable(false);
		setSize(660, 480);
		getContentPane().setLayout(new BorderLayout());

		JPanel inputPanel = new JPanel();
		getContentPane().add(inputPanel, BorderLayout.WEST);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 72, 82, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		inputPanel.setLayout(gbl_panel);

		boxKindOfCasting = new JComboBox<String>();
		boxKindOfCasting.setFont(new Font("Times New Roman", Font.ITALIC, 16));
		boxKindOfCasting.addItem("В песчаные формы");
		boxKindOfCasting.addItem("В оболочковые формы");
		boxKindOfCasting.addItem("В кокиль");
		boxKindOfCasting.addItem("ЛВМ");
		boxKindOfCasting.addItem("Под давлением");
		boxKindOfCasting.addItem("Выжиманием");

		boxkindOfAlloy = new JComboBox<String>();
		boxkindOfAlloy.setFont(new Font("Times New Roman", Font.ITALIC, 16));
		boxkindOfAlloy.addItem("Чугуны");
		boxkindOfAlloy.addItem("Констр. стали");
		boxkindOfAlloy.addItem("Жаропроч. стали");
		boxkindOfAlloy.addItem("Алюминий");
		boxkindOfAlloy.addItem("Магний с сод. Cr");
		boxkindOfAlloy.addItem("Магний с сод. Al");
		GridBagConstraints gbc_boxkindOfAlloy = new GridBagConstraints();
		gbc_boxkindOfAlloy.insets = new Insets(0, 0, 5, 5);
		gbc_boxkindOfAlloy.anchor = GridBagConstraints.NORTH;
		gbc_boxkindOfAlloy.gridx = 0;
		gbc_boxkindOfAlloy.gridy = 0;
		inputPanel.add(boxkindOfAlloy, gbc_boxkindOfAlloy);
		GridBagConstraints gbc_boxKindOfCasting = new GridBagConstraints();
		gbc_boxKindOfCasting.insets = new Insets(0, 0, 5, 5);
		gbc_boxKindOfCasting.anchor = GridBagConstraints.NORTH;
		gbc_boxKindOfCasting.gridx = 1;
		gbc_boxKindOfCasting.gridy = 0;
		inputPanel.add(boxKindOfCasting, gbc_boxKindOfCasting);

		duplexCB = new JCheckBox("Дуплекс");
		duplexCB.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		GridBagConstraints gbc_dupleks = new GridBagConstraints();
		gbc_dupleks.insets = new Insets(0, 0, 5, 5);
		gbc_dupleks.anchor = GridBagConstraints.NORTHWEST;
		gbc_dupleks.gridx = 1;
		gbc_dupleks.gridy = 1;
		inputPanel.add(duplexCB, gbc_dupleks);

		JLabel mDetL = new JLabel("Масса детали, кг:");
		GridBagConstraints gbc_mDetL = new GridBagConstraints();
		gbc_mDetL.insets = new Insets(0, 0, 5, 5);
		gbc_mDetL.gridx = 0;
		gbc_mDetL.gridy = 2;
		inputPanel.add(mDetL, gbc_mDetL);

		mDetTF = new JTextField();
		mDetTF.setColumns(10);
		GridBagConstraints gbc_mDet = new GridBagConstraints();
		gbc_mDet.insets = new Insets(0, 0, 5, 5);
		gbc_mDet.gridx = 1;
		gbc_mDet.gridy = 2;
		inputPanel.add(mDetTF, gbc_mDet);

		JLabel mCastL = new JLabel("Масса отливки, кг:");
		GridBagConstraints gbc_mCastL = new GridBagConstraints();
		gbc_mCastL.insets = new Insets(0, 0, 5, 5);
		gbc_mCastL.gridx = 0;
		gbc_mCastL.gridy = 3;
		inputPanel.add(mCastL, gbc_mCastL);

		mCastTF = new JTextField();
		mCastTF.setColumns(10);
		GridBagConstraints gbc_mCast = new GridBagConstraints();
		gbc_mCast.insets = new Insets(0, 0, 5, 5);
		gbc_mCast.gridx = 1;
		gbc_mCast.gridy = 3;
		inputPanel.add(mCastTF, gbc_mCast);

		JLabel mGFSL = new JLabel("Масса отливки с ЛПС, кг:");
		GridBagConstraints gbc_mGFSL = new GridBagConstraints();
		gbc_mGFSL.insets = new Insets(0, 0, 5, 5);
		gbc_mGFSL.gridx = 0;
		gbc_mGFSL.gridy = 4;
		inputPanel.add(mGFSL, gbc_mGFSL);

		mGFSTF = new JTextField();
		mGFSTF.setColumns(10);
		GridBagConstraints gbc_mGFS = new GridBagConstraints();
		gbc_mGFS.insets = new Insets(0, 0, 5, 5);
		gbc_mGFS.gridx = 1;
		gbc_mGFS.gridy = 4;
		inputPanel.add(mGFSTF, gbc_mGFS);

		JLabel percentOfMarriageL = new JLabel("% брака,%:");
		GridBagConstraints gbc_percentOfMarriageL = new GridBagConstraints();
		gbc_percentOfMarriageL.insets = new Insets(0, 0, 5, 5);
		gbc_percentOfMarriageL.gridx = 0;
		gbc_percentOfMarriageL.gridy = 5;
		inputPanel.add(percentOfMarriageL, gbc_percentOfMarriageL);
//		inputPanel.setFocusTraversalPolicy(
//				new FocusTraversalPolicy(new Component[] { boxKindOfCasting, mDetL, mGFSL, boxkindOfAlloy, mCastL,
//						mDetTF, percentOfMarriageL, percentOfMarriageTF, percentOfNewMetallTF, duplexCB }));

		percentOfMarriageTF = new JTextField();
		percentOfMarriageTF.setColumns(10);
		GridBagConstraints gbc_percentOfMarriage = new GridBagConstraints();
		gbc_percentOfMarriage.insets = new Insets(0, 0, 5, 5);
		gbc_percentOfMarriage.gridx = 1;
		gbc_percentOfMarriage.gridy = 5;
		inputPanel.add(percentOfMarriageTF, gbc_percentOfMarriage);

		JLabel percentOfNewMetallL = new JLabel("% освежения металла,%:");
		GridBagConstraints gbc_percentOfNewMetallL = new GridBagConstraints();
		gbc_percentOfNewMetallL.insets = new Insets(0, 0, 0, 5);
		gbc_percentOfNewMetallL.gridx = 0;
		gbc_percentOfNewMetallL.gridy = 6;
		inputPanel.add(percentOfNewMetallL, gbc_percentOfNewMetallL);

		percentOfNewMetallTF = new JTextField();
		percentOfNewMetallTF.setColumns(10);
		GridBagConstraints gbc_percentOfNewMetall = new GridBagConstraints();
		gbc_percentOfNewMetall.insets = new Insets(0, 0, 0, 5);
		gbc_percentOfNewMetall.gridx = 1;
		gbc_percentOfNewMetall.gridy = 6;
		inputPanel.add(percentOfNewMetallTF, gbc_percentOfNewMetall);

		JButton process = new JButton("Рассчитать!");
		process.setBackground(Color.GREEN);
		process.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		process.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent arg0) {
				try {
					kindOfAlloy = (String) boxkindOfAlloy.getSelectedItem();
					kindOfCasting = (String) boxKindOfCasting.getSelectedItem();
					percentOfMarriage = isNull(percentOfMarriageTF.getText());
					percentOfNewMetall = isNull(percentOfNewMetallTF.getText());
					duplex = duplexCB.isSelected();
					mDet = isNull(mDetTF.getText());
					mCast = isNull(mCastTF.getText());
					mGFS = isNull(mGFSTF.getText());

					data = new Data(kindOfAlloy, kindOfCasting, percentOfMarriage, percentOfNewMetall, duplex,
							mDet, mCast, mGFS);

					data.processing();

					mDetAnswer.setText(data.getmDet());
					mCastAnswer.setText(data.getmCast());
					mGFSAnswer.setText(data.getmGFS());
					normOfRateAnswer.setText(data.getNormOfOutgo());
					mLossesAnswer.setText(data.getmLosses());
					mBatchAnswer.setText(data.getmBatch());
					mOfNewMetallAnswer.setText(data.getNewMetall());
					recoveryAnswer.setText(data.getReconvery());

				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Введите положительные числа,десячичную дробь через точку");
					System.out.println("ошибка при расчете");
					e.printStackTrace();

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Проверте входные данные");
					System.out.println("ошибка при расчете");
					e.printStackTrace();

				}

			}
		});

		getContentPane().add(process, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.EAST);
		GridBagLayout gbl_panel1 = new GridBagLayout();
		gbl_panel1.columnWidths = new int[] { 119, 0, 0 };
		gbl_panel1.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel1.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel1.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel1);

		JLabel mDetAnswer_1 = new JLabel("Масса детали, кг:");
		GridBagConstraints gbc_mDetAnswer_1 = new GridBagConstraints();
		gbc_mDetAnswer_1.insets = new Insets(0, 0, 5, 5);
		gbc_mDetAnswer_1.gridx = 0;
		gbc_mDetAnswer_1.gridy = 0;
		panel.add(mDetAnswer_1, gbc_mDetAnswer_1);

		mDetAnswer = new JTextField();
		GridBagConstraints gbc_mDetAnswer = new GridBagConstraints();
		gbc_mDetAnswer.insets = new Insets(0, 0, 5, 0);
		gbc_mDetAnswer.gridx = 1;
		gbc_mDetAnswer.gridy = 0;
		panel.add(mDetAnswer, gbc_mDetAnswer);
		mDetAnswer.setEditable(false);
		mDetAnswer.setBounds(180, 300, 86, 20);
		mDetAnswer.setColumns(10);

		JLabel mCastAnswer_1 = new JLabel("Масса отливки, кг:");
		GridBagConstraints gbc_mCastAnswer_1 = new GridBagConstraints();
		gbc_mCastAnswer_1.insets = new Insets(0, 0, 5, 5);
		gbc_mCastAnswer_1.gridx = 0;
		gbc_mCastAnswer_1.gridy = 1;
		panel.add(mCastAnswer_1, gbc_mCastAnswer_1);

		mCastAnswer = new JTextField();
		GridBagConstraints gbc_mCastAnswer = new GridBagConstraints();
		gbc_mCastAnswer.insets = new Insets(0, 0, 5, 0);
		gbc_mCastAnswer.gridx = 1;
		gbc_mCastAnswer.gridy = 1;
		panel.add(mCastAnswer, gbc_mCastAnswer);
		mCastAnswer.setEditable(false);
		mCastAnswer.setBounds(180, 343, 86, 20);
		mCastAnswer.setColumns(10);

		JLabel mGFSAnswer_1 = new JLabel("Масса отливки с ЛПС, кг:");
		GridBagConstraints gbc_mGFSAnswer_1 = new GridBagConstraints();
		gbc_mGFSAnswer_1.insets = new Insets(0, 0, 5, 5);
		gbc_mGFSAnswer_1.gridx = 0;
		gbc_mGFSAnswer_1.gridy = 2;
		panel.add(mGFSAnswer_1, gbc_mGFSAnswer_1);
		//
		mGFSAnswer = new JTextField();
		GridBagConstraints gbc_mGFSAnswer = new GridBagConstraints();
		gbc_mGFSAnswer.insets = new Insets(0, 0, 5, 0);
		gbc_mGFSAnswer.gridx = 1;
		gbc_mGFSAnswer.gridy = 2;
		panel.add(mGFSAnswer, gbc_mGFSAnswer);
		mGFSAnswer.setEditable(false);
		mGFSAnswer.setBounds(180, 386, 86, 20);
		mGFSAnswer.setColumns(10);

		JLabel mBatchAnswerL = new JLabel("Масса шихты, кг:");
		GridBagConstraints gbc_mBatchAnswerL = new GridBagConstraints();
		gbc_mBatchAnswerL.insets = new Insets(0, 0, 5, 5);
		gbc_mBatchAnswerL.gridx = 0;
		gbc_mBatchAnswerL.gridy = 3;
		panel.add(mBatchAnswerL, gbc_mBatchAnswerL);
		mBatchAnswerL.setBounds(525, 285, 86, 20);

		mBatchAnswer = new JTextField();
		GridBagConstraints gbc_mBatchAnswer = new GridBagConstraints();
		gbc_mBatchAnswer.insets = new Insets(0, 0, 5, 0);
		gbc_mBatchAnswer.gridx = 1;
		gbc_mBatchAnswer.gridy = 3;
		panel.add(mBatchAnswer, gbc_mBatchAnswer);
		mBatchAnswer.setEditable(false);
		mBatchAnswer.setBounds(525, 240, 86, 20);
		mBatchAnswer.setColumns(10);

		JLabel mLossesAnswerL = new JLabel("Масса б/в потерь, кг:");
		GridBagConstraints gbc_mLossesAnswerL = new GridBagConstraints();
		gbc_mLossesAnswerL.insets = new Insets(0, 0, 5, 5);
		gbc_mLossesAnswerL.gridx = 0;
		gbc_mLossesAnswerL.gridy = 4;
		panel.add(mLossesAnswerL, gbc_mLossesAnswerL);
		mLossesAnswerL.setBounds(525, 369, 86, 20);

		mLossesAnswer = new JTextField();
		GridBagConstraints gbc_mLossesAnswer = new GridBagConstraints();
		gbc_mLossesAnswer.insets = new Insets(0, 0, 5, 0);
		gbc_mLossesAnswer.gridx = 1;
		gbc_mLossesAnswer.gridy = 4;
		panel.add(mLossesAnswer, gbc_mLossesAnswer);
		mLossesAnswer.setEditable(false);
		mLossesAnswer.setBounds(525, 285, 86, 20);
		mLossesAnswer.setColumns(10);

		JLabel normOfRateAnswerL = new JLabel("Норма расхода св. металла, кг:");
		GridBagConstraints gbc_normOfRateAnswerL = new GridBagConstraints();
		gbc_normOfRateAnswerL.insets = new Insets(0, 0, 5, 5);
		gbc_normOfRateAnswerL.gridx = 0;
		gbc_normOfRateAnswerL.gridy = 5;
		panel.add(normOfRateAnswerL, gbc_normOfRateAnswerL);

		normOfRateAnswer = new JTextField();
		GridBagConstraints gbc_normOfRateAnswer = new GridBagConstraints();
		gbc_normOfRateAnswer.insets = new Insets(0, 0, 5, 0);
		gbc_normOfRateAnswer.gridx = 1;
		gbc_normOfRateAnswer.gridy = 5;
		panel.add(normOfRateAnswer, gbc_normOfRateAnswer);
		normOfRateAnswer.setEditable(false);
		normOfRateAnswer.setBounds(525, 328, 86, 20);
		normOfRateAnswer.setColumns(10);

		JLabel mOfNewMetallAnswerL = new JLabel("Масса свежего металла, кг:");
		GridBagConstraints gbc_mOfNewMetallAnswerL = new GridBagConstraints();
		gbc_mOfNewMetallAnswerL.insets = new Insets(0, 0, 5, 5);
		gbc_mOfNewMetallAnswerL.gridx = 0;
		gbc_mOfNewMetallAnswerL.gridy = 6;
		panel.add(mOfNewMetallAnswerL, gbc_mOfNewMetallAnswerL);
		mOfNewMetallAnswerL.setBounds(316, 369, 179, 14);

		mOfNewMetallAnswer = new JTextField();
		GridBagConstraints gbc_mOfNewMetallAnswer = new GridBagConstraints();
		gbc_mOfNewMetallAnswer.insets = new Insets(0, 0, 5, 0);
		gbc_mOfNewMetallAnswer.gridx = 1;
		gbc_mOfNewMetallAnswer.gridy = 6;
		panel.add(mOfNewMetallAnswer, gbc_mOfNewMetallAnswer);
		mOfNewMetallAnswer.setEditable(false);
		mOfNewMetallAnswer.setBounds(525, 369, 86, 20);
		mOfNewMetallAnswer.setColumns(10);

		JLabel recoveryAnswerL = new JLabel("Масса возврата:");
		GridBagConstraints gbc_recoveryAnswerL = new GridBagConstraints();
		gbc_recoveryAnswerL.insets = new Insets(0, 0, 0, 5);
		gbc_recoveryAnswerL.gridx = 0;
		gbc_recoveryAnswerL.gridy = 7;
		panel.add(recoveryAnswerL, gbc_recoveryAnswerL);
		recoveryAnswerL.setBounds(316, 413, 179, 14);

		recoveryAnswer = new JTextField();
		GridBagConstraints gbc_recoveryAnswer = new GridBagConstraints();
		gbc_recoveryAnswer.gridx = 1;
		gbc_recoveryAnswer.gridy = 7;
		panel.add(recoveryAnswer, gbc_recoveryAnswer);
		recoveryAnswer.setEditable(false);
		recoveryAnswer.setBounds(525, 413, 86, 20);
		recoveryAnswer.setColumns(10);

		JButton cancel = new JButton("Сброс");
		cancel.setBackground(Color.YELLOW);
		cancel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cancelAction();
			}
		});
		cancel.setBounds(194, 213, 94, 49);
		getContentPane().add(cancel, BorderLayout.SOUTH);

	}

	public void cancelAction() {
		mDetTF.setText(null);
		mCastTF.setText(null);
		mGFSTF.setText(null);
		percentOfMarriageTF.setText(null);
		percentOfNewMetallTF.setText(null);
		mGFSAnswer.setText(null);
		mCastAnswer.setText(null);
		mDetAnswer.setText(null);
		normOfRateAnswer.setText(null);
		mLossesAnswer.setText(null);
		mBatchAnswer.setText(null);
		mOfNewMetallAnswer.setText(null);
		recoveryAnswer.setText(null);
	}

	public double isNull(String d) {

		if (d.isEmpty() || Integer.valueOf(d) <= 0) {
			return 0;
		}
		return Double.valueOf(d);

	}

	public boolean isduplex() {
		if (duplexCB.isSelected()) {
			return true;
		} else
			return false;
	}

	protected void actionExit() {
		System.exit(0);

	}

	public static void main(String[] args) {

		MainWindow window = new MainWindow();
		window.setVisible(true);

	}

}
