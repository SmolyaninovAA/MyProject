package Main;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4957374654460463194L;

	private Data data;

	private JTextField mDet;
	private JTextField mCast;
	private JTextField mGFS;
	private JTextField percentOfMarriage;
	private JTextField percentOfNewMetall;
	private JTextField mPiece;
	private JTextField mGFSAnswer;
	private JTextField mCastAnswer;
	private JTextField mDetAnswer;
	private JTextField normOfRateAnswer;
	private JTextField mLossesAnswer;
	private JTextField mBatchAnswer;
	private JTextField mOfNewMetallAnswer;
	private JTextField recoveryAnswer;
	private JComboBox<String> boxKindOfCasting;
	private JComboBox<String> boxkindOfAlloy;
	private JCheckBox dupleks;

	public MainWindow(String s) {

		super(s);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLocationRelativeTo(null);
		setResizable(false);
		setSize(640, 480);
		getContentPane().setLayout(null);

		boxKindOfCasting = new JComboBox<String>();
		boxKindOfCasting.setFont(new Font("Tahoma", Font.ITALIC, 14));
		boxKindOfCasting.setBounds(23, 12, 161, 31);
		getContentPane().add(boxKindOfCasting);
		boxKindOfCasting.addItem("В песчаные формы");
		boxKindOfCasting.addItem("В оболочковые формы");
		boxKindOfCasting.addItem("В кокиль");
		boxKindOfCasting.addItem("ЛВМ");
		boxKindOfCasting.addItem("Под давлением");
		boxKindOfCasting.addItem("Выжиманием");

		boxkindOfAlloy = new JComboBox<String>();
		boxkindOfAlloy.setFont(new Font("Tahoma", Font.ITALIC, 14));
		boxkindOfAlloy.setBounds(23, 54, 161, 37);
		getContentPane().add(boxkindOfAlloy);
		boxkindOfAlloy.addItem("Чугуны");
		boxkindOfAlloy.addItem("Констр. стали");
		boxkindOfAlloy.addItem("Жаропроч. стали");
		boxkindOfAlloy.addItem("Алюминий");
		boxkindOfAlloy.addItem("Магний с сод. Cr");
		boxkindOfAlloy.addItem("Магний с сод. Al");

		dupleks = new JCheckBox("Дуплекс");
		dupleks.setBounds(23, 107, 161, 20);
		getContentPane().add(dupleks);

		JLabel lbln = new JLabel("Введите Массу детали, кг:");
		lbln.setBounds(225, 12, 171, 37);
		getContentPane().add(lbln);

		mDet = new JTextField();
		mDet.setBackground(Color.WHITE);
		mDet.setBounds(482, 17, 86, 20);
		getContentPane().add(mDet);
		mDet.setColumns(10);

		JLabel label = new JLabel("Введите массу отливки, кг:");
		label.setBounds(225, 60, 171, 14);
		getContentPane().add(label);

		mCast = new JTextField();
		mCast.setBackground(Color.WHITE);
		mCast.setBounds(482, 57, 86, 20);
		getContentPane().add(mCast);
		mCast.setColumns(10);

		JLabel label_1 = new JLabel("Установите % брака,%:");
		label_1.setBounds(225, 118, 141, 37);
		getContentPane().add(label_1);

		percentOfMarriage = new JTextField();
		percentOfMarriage.setBackground(Color.WHITE);
		percentOfMarriage.setBounds(482, 120, 86, 20);
		getContentPane().add(percentOfMarriage);
		percentOfMarriage.setColumns(10);

		JLabel label_2 = new JLabel("Установите % освежения металла,%:");
		label_2.setBounds(225, 160, 224, 14);
		getContentPane().add(label_2);

		percentOfNewMetall = new JTextField();
		percentOfNewMetall.setBackground(Color.WHITE);
		percentOfNewMetall.setBounds(482, 151, 86, 20);
		getContentPane().add(percentOfNewMetall);
		percentOfNewMetall.setColumns(10);

		JButton process = new JButton("Рассчитать!");
		process.setBackground(Color.GREEN);
		process.setFont(new Font("Tahoma", Font.PLAIN, 16));
		process.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					data.processing();

				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Введите положительные числа, десячичную дробь через точку");
					System.out.println("ошибка при расчете");
					e.printStackTrace();

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Проверте входные данные");
					System.out.println("ошибка при расчете");
					e.printStackTrace();

				}

			}
		});
		process.setBounds(23, 213, 161, 49);
		getContentPane().add(process);

		JLabel label_3 = new JLabel("Масса детали, кг:");
		label_3.setBounds(23, 303, 117, 14);
		getContentPane().add(label_3);

		JLabel label_4 = new JLabel("Масса отливки, кг:");
		label_4.setBounds(23, 346, 117, 14);
		getContentPane().add(label_4);

		JLabel label_5 = new JLabel("Масса отливки с ЛПС, кг:");
		label_5.setBounds(23, 389, 141, 14);
		getContentPane().add(label_5);

		mGFSAnswer = new JTextField();
		mGFSAnswer.setEditable(false);
		mGFSAnswer.setBounds(180, 386, 86, 20);
		getContentPane().add(mGFSAnswer);
		mGFSAnswer.setColumns(10);

		mCastAnswer = new JTextField();
		mCastAnswer.setEditable(false);
		mCastAnswer.setBounds(180, 343, 86, 20);
		getContentPane().add(mCastAnswer);
		mCastAnswer.setColumns(10);

		mDetAnswer = new JTextField();
		mDetAnswer.setEditable(false);
		mDetAnswer.setBounds(180, 300, 86, 20);
		getContentPane().add(mDetAnswer);
		mDetAnswer.setColumns(10);

		JLabel label_6 = new JLabel("Масса шихты, кг:");
		label_6.setBounds(316, 240, 179, 14);
		getContentPane().add(label_6);

		JLabel label_7 = new JLabel("Масса б/в потерь, кг:");
		label_7.setBounds(316, 285, 179, 14);
		getContentPane().add(label_7);

		JLabel label_8 = new JLabel("Норма расхода св. металла, кг:");
		label_8.setBounds(316, 328, 179, 14);
		getContentPane().add(label_8);

		normOfRateAnswer = new JTextField();
		normOfRateAnswer.setEditable(false);
		normOfRateAnswer.setBounds(525, 328, 86, 20);
		getContentPane().add(normOfRateAnswer);
		normOfRateAnswer.setColumns(10);

		mLossesAnswer = new JTextField();
		mLossesAnswer.setEditable(false);
		mLossesAnswer.setBounds(525, 285, 86, 20);
		getContentPane().add(mLossesAnswer);
		mLossesAnswer.setColumns(10);

		mBatchAnswer = new JTextField();
		mBatchAnswer.setEditable(false);
		mBatchAnswer.setBounds(525, 240, 86, 20);
		getContentPane().add(mBatchAnswer);
		mBatchAnswer.setColumns(10);

		JLabel label_9 = new JLabel("Масса свежего металла, кг:");
		label_9.setBounds(316, 369, 179, 14);
		getContentPane().add(label_9);

		JLabel label_10 = new JLabel("Масса возврата:");
		label_10.setBounds(316, 413, 179, 14);
		getContentPane().add(label_10);

		mOfNewMetallAnswer = new JTextField();
		mOfNewMetallAnswer.setEditable(false);
		mOfNewMetallAnswer.setBounds(525, 369, 86, 20);
		getContentPane().add(mOfNewMetallAnswer);
		mOfNewMetallAnswer.setColumns(10);

		recoveryAnswer = new JTextField();
		recoveryAnswer.setEditable(false);
		recoveryAnswer.setBounds(525, 413, 86, 20);
		getContentPane().add(recoveryAnswer);
		recoveryAnswer.setColumns(10);

		JLabel label_11 = new JLabel("Введите массу отливки с ЛПС, кг:");
		label_11.setBounds(225, 93, 208, 14);
		getContentPane().add(label_11);

		mGFS = new JTextField();
		mGFS.setBackground(Color.WHITE);
		mGFS.setBounds(482, 88, 86, 20);
		getContentPane().add(mGFS);
		mGFS.setColumns(10);

		JButton cancel = new JButton("Сброс");
		cancel.setBackground(Color.YELLOW);
		cancel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mDet.setText(null);
				mCast.setText(null);
				mGFS.setText(null);
				percentOfMarriage.setText(null);
				percentOfNewMetall.setText(null);
				mPiece.setText(null);
				mGFSAnswer.setText(null);
				mCastAnswer.setText(null);
				mDetAnswer.setText(null);
				normOfRateAnswer.setText(null);
				mLossesAnswer.setText(null);
				mBatchAnswer.setText(null);
				mOfNewMetallAnswer.setText(null);
				recoveryAnswer.setText(null);
			}
		});
		cancel.setBounds(194, 213, 94, 49);
		getContentPane().add(cancel);

		JLabel label_12 = new JLabel("Введите массу образцов:");
		label_12.setBounds(225, 185, 208, 14);
		getContentPane().add(label_12);

		mPiece = new JTextField();
		mPiece.setBackground(Color.WHITE);
		mPiece.setBounds(482, 179, 86, 20);
		getContentPane().add(mPiece);
		mPiece.setColumns(10);

		setVisible(true);

	}

	public String getkindOfCasting() {

		return (String) boxKindOfCasting.getSelectedItem();

	}

	public String getkindOfAlloyW() {

		return (String) boxkindOfAlloy.getSelectedItem();

	}

	public double getmDet() {
		String s = mDet.getText();

		if (!s.isEmpty()) {
			return Double.parseDouble(s);
		}
		return 0;

	}

	public double getmCast() {
		String s = mCast.getText();
		if (!s.isEmpty()) {

			return Double.parseDouble(s);

		}

		return 0;

	}

	public double getmGFS() {
		String s = mGFS.getText();
		if (!s.isEmpty()) {

			return Double.parseDouble(s);

		}

		return -1;

	}

	public double getpercentOfMarriage() {

		String s = percentOfMarriage.getText();
		if (!s.isEmpty()) {

			return Double.parseDouble(s);

		}

		return -1;

	}

	public double getpercentOfNewMetall() {

		String s = percentOfNewMetall.getText();
		if (!s.isEmpty()) {

			return Double.parseDouble(s);

		}

		return -1;

	}

	public double getmPiece() {

		String s = mPiece.getText();
		if (!s.isEmpty()) {

			return Double.parseDouble(s);

		}

		return -1;

	}

	public boolean isduplex() {
		if (dupleks.isSelected()) {
			return true;
		} else
			return false;
	}

	public void setmDetAnswer(String mDet) {
		mDetAnswer.setText(mDet);
	}

	public void setmCastAnswer(String mCast) {
		mCastAnswer.setText(mCast);
	}

	public void setmGFSAnswer(String mGFS) {
		mGFSAnswer.setText(mGFS);
	}

	public void setmBatchAnswer(String mBatch) {
		mBatchAnswer.setText(mBatch);
		;
	}

	public void setmLossesAnswer(String mLosses) {
		mLossesAnswer.setText(mLosses);
	}

	public void setnormOfRateAnswer(String normOfRate) {
		normOfRateAnswer.setText(normOfRate);
	}

	public void setmOfNewMetall(String mOfNewMetall) {
		mOfNewMetallAnswer.setText(mOfNewMetall);
	}

	public void setrecoveryAnswer(String recovery) {
		recoveryAnswer.setText(recovery);
	}

	void setData(Data data) {
		this.data = data;

	}

}
