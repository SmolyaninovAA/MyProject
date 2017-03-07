package Main;

public class Main {

	public static void main(String[] args) {

		MainWindow window = new MainWindow("Расчет шихты по ОСТ 1.42141-82");

		Data data = new Data(window);
		window.setData(data);
	}

}
