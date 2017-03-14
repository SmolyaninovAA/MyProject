package Main;

import java.text.DecimalFormat;

public class Data {

	final String ALLOYS[] = { "Iron", "StructuralSteel", "HeatResistantSteel", "Aluminium", "Magnesiun_CR",
			"Magnesiun_Al" };

	public static final int IRON = 0;
	public static final int STRUCTURALSTEEL = 1;
	public static final int HEATRESISTANTSTEEL = 2;
	public static final int ALUMINIUM = 3;
	public static final int MAGNESIUN_CR = 4;
	public static final int MAGNESIUN_AL = 5;

	final String KIND_OF_CASTING[] = { "sand_molds", "shell_molds", "chill", "precise_casting", "injection_molding",
			"squeeze_casting" };

	public static final int SAND_MOLDS = 0;
	public static final int SHELL_MOLDS = 1;
	public static final int CHILL = 2;
	public static final int PRECISE_CASTING = 3;
	public static final int INJECTION_MOLDING = 4;
	public static final int SQUEEZE_CASTING = 5;

	// input data
	private int kindOfAlloy = -1;
	private int kindOfCasting = -1;
	private int kindOfSize = -1;
	private double kIzb = 0;
	private int duplex = 0; // 0 - single remelting 1 - double remelting
	private double kLosses = 0;
	private double wasteProduction = 0;
	private double mDet = 0;
	private double mCast = 0;
	private double mGFS = 0;
	private double percentOfMarriage = 0;
	private double percentOfNewMetall = 0;

	// output data

	private double mBatch;
	private double mLosses;
	private double normOfOutgo;
	private double newMetall;
	private double reconvery;

	public Data(String kindOfAlloy, String kindOfCasting, double percentOfMarriage, double percentOfNewMetall,
			boolean duplex, double mDet, double mCast, double mGFS) {

		if (mDet > 0) {
			this.mDet = mDet;
		}
		this.kindOfAlloy = setkindOfAlloy(kindOfAlloy);
		this.kindOfCasting = setkindOfCasting(kindOfCasting);
		this.percentOfMarriage = percentOfMarriage;
		this.percentOfNewMetall = percentOfNewMetall;
		this.duplex = setDuplex(duplex);

		setkindOfSize(this.mDet);
		setmCast(mCast);
		setkindOfSize(this.mCast);
		setmGFS(mGFS);
		setwasteProduction();
		setkLosses();
		setkIzb();

	}

	// table data
	private double kLosses_Iron[][] = {

			// single , double
			{ 3.39, 4.39 }, // sand molds 0
			{ 3.39, 4.39 }, // shell molds 1
			{ 3.40, 4.40 }, // chill 2
			{ 0.0, 0.0 }, // precise casting 3
			{ 0.0, 0.0 }, // injection molding 4
			{ 0.0, 0.0 }, // squeeze casting 5
	};

	private double kLosses_StructuralSteel[][] = {

			// from 0,25 0,63 1,60 4,00 10,00 25,00 63,00 160,0
			// to 0,25 0,63 1,60 4,00 10,00 25,00 63,00 160,0
			{ 2.40, 2.40, 2.37, 2.37, 2.35, 2.35, 2.32, 2.32, 2.32 }, // sand
																		// molds
																		// 0
			{ 2.40, 2.40, 2.37, 2.37, 2.35, 2.35, 2.32, 2.32, 2.32 }, // shell
																		// molds
																		// 1
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // chill 2
			{ 2.40, 2.40, 2.40, 2.40, 2.35, 2.35, 2.32, 2.32, 2.32 }, // precise
																		// casting
																		// 3
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // injection
																// molding 4
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // squeeze casting
																// 5

	};

	private double kLosses_HeatResistantSteel[][] = {
			// from 0,25 0,63 1,60 4,00 10,00 25,00 63,00 160,0
			// to 0,25 0,63 1,60 4,00 10,00 25,00 63,00 160,0
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // sand molds
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // shell molds
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // chill
			{ 2.00, 2.00, 1.90, 1.90, 1.90, 1.90, 1.85, 1.85, 1.85 }, // precise
																		// casting
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // injection
																// molding
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // squeeze casting
	};

	private double kLosses_Aluminium[][] = {

			// single , double
			{ 2.30, 4.40 }, // sand molds 0
			{ 2.25, 2.25 }, // shell molds 1
			{ 2.20, 4.30 }, // chill 2
			{ 2.25, 2.25 }, // precise casting 3
			{ 2.20, 4.30 }, // injection molding 4
			{ 2.35, 2.35 }, // squeeze casting 5
	};

	private double kLosses_Magnesiun[][] = {

			// single , double
			{ 4.20, 5.20 }, // sand molds 0
			{ 4.15, 5.15 }, // shell molds 1
			{ 4.10, 5.10 }, // chill 2
			{ 0.0, 0.0 }, // precise casting 3
			{ 0.0, 0.0 }, // injection molding 4
			{ 0.0, 0.0 }, // squeeze casting 5
	};

	private double wasteProduction_Iron[][] = {

			// single , double
			{ 2.00, 2.00 }, // sand molds 0
			{ 2.00, 2.00 }, // shell molds 1
			{ 2.00, 2.00 }, // chill 2
			{ 0.0, 0.0 }, // precise casting 3
			{ 0.0, 0.0 }, // injection molding 4
			{ 0.0, 0.0 }, // squeeze casting 5
	};

	private double wasteProduction_StructuralSteel[][] = {

			// from 0,25 0,63 1,60 4,00 10,00 25,00 63,00 160,0
			// to 0,25 0,63 1,60 4,00 10,00 25,00 63,00 160,0
			{ 2.20, 2.20, 1.88, 1.88, 1.60, 1.60, 1.43, 1.43, 1.43 }, // sand
																		// molds
			{ 2.30, 2.30, 1.98, 1.98, 1.70, 1.70, 1.53, 1.53, 1.53 }, // shell
																		// molds
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // chill
			{ 2.60, 2.60, 2.20, 2.20, 1.95, 1.95, 1.78, 1.78, 1.78 }, // precise
																		// casting
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // injection
																// molding
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // squeeze casting
	};

	private double wasteProduction_HeatResistantSteel[][] = {
			// from 0,25 0,63 1,60 4,00 10,00 25,00 63,00 160,0
			// to 0,25 0,63 1,60 4,00 10,00 25,00 63,00 160,0
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // sand molds
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // shell molds
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // chill
			{ 2.35, 2.35, 1.95, 1.95, 1.65, 1.65, 1.40, 1.40, 1.40 }, // precise
																		// casting
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // injection
																// molding
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // squeeze casting
	};

	private double wasteProduction_Aluminium[][] = {

			// single , double
			{ 2.20, 2.50 }, // sand molds 0
			{ 2.0, 2.0 }, // shell molds 1
			{ 1.90, 2.20 }, // chill 2
			{ 2.15, 2.15 }, // precise casting 3
			{ 1.00, 1.30 }, // injection molding 4
			{ 1.00, 1.00 }, // squeeze casting 5
	};

	private double wasteProduction_Magnesiun[][] = {

			// single , double
			{ 3.60, 4.60 }, // sand molds 0
			{ 3.25, 4.25 }, // shell molds 1
			{ 3.40, 4.40 }, // chill 2
			{ 0.0, 0.0 }, // precise casting 3
			{ 0.0, 0.0 }, // injection molding 4
			{ 0.0, 0.0 }, // squeeze casting 5
	};

	private double kIzb_Iron[][] = {

			// single , double
			{ 1.06, 1.07 }, // sand molds 0
			{ 1.06, 1.07 }, // shell molds 1
			{ 1.06, 1.08 }, // chill 2
			{ 0.0, 0.0 }, // precise casting 3
			{ 0.0, 0.0 }, // injection molding 4
			{ 0.0, 0.0 }, // squeeze casting 5
	};

	private double kIzb_StructuralSteel[][] = {

			// from 0,25 0,63 1,60 4,00 10,00 25,00 63,00 160,0
			// to 0,25 0,63 1,60 4,00 10,00 25,00 63,00 160,0
			{ 1.03, 1.03, 1.03, 1.03, 1.03, 1.03, 1.03, 1.03, 1.03 }, // sand
																		// molds
			{ 1.03, 1.03, 1.03, 1.03, 1.03, 1.03, 1.03, 1.03, 1.03 }, // shell
																		// molds
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // chill
			{ 1.04, 1.04, 1.04, 1.04, 1.04, 1.04, 1.04, 1.04, 1.04 }, // precise
																		// casting
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // injection
																// molding
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // squeeze casting
	};

	private double kIzb_HeatResistantSteel[][] = {
			// from 0,25 0,63 1,60 4,00 10,00 25,00 63,00 160,0
			// to 0,25 0,63 1,60 4,00 10,00 25,00 63,00 160,0
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // sand molds
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // shell molds
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // chill
			{ 1.02, 1.02, 1.02, 1.02, 1.02, 1.02, 1.02, 1.02, 1.02 }, // precise
																		// casting
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // injection
																// molding
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // squeeze casting
	};

	private double kIzb_Aluminium[][] = {

			// single , double
			{ 1.04, 1.07 }, // sand molds 0
			{ 1.04, 1.04 }, // shell molds 1
			{ 1.04, 1.07 }, // chill 2
			{ 1.05, 1.05 }, // precise casting 3
			{ 1.04, 1.07 }, // injection molding 4
			{ 1.04, 1.04 }, // squeeze casting 5
	};

	private double kIzb_MagnesiunZr[][] = {

			// single , double
			{ 1.40, 1.43 }, // sand molds 0
			{ 1.39, 1.43 }, // shell molds 1
			{ 1.39, 1.43 }, // chill 2
			{ 0.0, 0.0 }, // precise casting 3
			{ 0.0, 0.0 }, // injection molding 4
			{ 0.0, 0.0 }, // squeeze casting 5
	};

	private double kIzb_MagnesiunAl[][] = {

			// single , double
			{ 1.30, 1.34 }, // sand molds 0
			{ 1.30, 1.33 }, // shell molds 1
			{ 1.30, 1.34 }, // chill 2
			{ 0.0, 0.0 }, // precise casting 3
			{ 0.0, 0.0 }, // injection molding 4
			{ 0.0, 0.0 }, // squeeze casting 5
	};

	// coefficient matching mDet and mCast

	private double k1_Iron[][] = {

			// from 0,25 0,63 1,60 4,00 10,00 25,00 63,00 160,0
			// to 0,25 0,63 1,60 4,00 10,00 25,00 63,00 160,0
			{ 2.05, 1.90, 1.77, 1.64, 1.52, 1.41, 1.31, 1.22, 1.13 }, // sand
																		// molds
			{ 1.80, 1.68, 1.57, 1.47, 1.57, 1.28, 1.20, 1.12, 0.0 }, // shell
																		// molds
			{ 1.75, 1.64, 1.54, 1.44, 1.35, 1.27, 1.18, 1.11, 0.0 }, // chill
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // precise casting
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // injection
																// molding
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // squeeze casting

	};

	private double k1_StructuralSteel[][] = {
			// from 0,25 0,63 1,60 4,00 10,00 25,00 63,00 160,0
			// to 0,25 0,63 1,60 4,00 10,00 25,00 63,00 160,0
			{ 1.95, 1.82, 1.70, 1.58, 1.48, 1.38, 1.28, 1.20, 1.12 }, // sand
																		// molds
			{ 1.50, 1.44, 1.37, 1.31, 1.25, 1.20, 1.15, 1.11, 0.0 }, // shell
																		// molds
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // chill
			{ 1.43, 1.37, 1.31, 1.25, 1.19, 1.14, 1.10, 0.0, 0.0 }, // precise
																	// casting
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // injection
																// molding
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // squeeze casting
	};

	private double k1_HeatResistantSteel[][] = {
			// from 0,25 0,63 1,60 4,00 10,00 25,00 63,00 160,0
			// to 0,25 0,63 1,60 4,00 10,00 25,00 63,00 160,0
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // sand molds
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // shell molds
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // chill
			{ 1.52, 1.44, 1.37, 1.31, 1.24, 1.18, 1.12, 0.0, 0.0 }, // precise
																	// casting
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // injection
																// molding
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // squeeze casting
	};

	private double k1_Aluminium[][] = {
			// from 0,25 0,63 1,60 4,00 10,00 25,00 63,00 160,0
			// to 0,25 0,63 1,60 4,00 10,00 25,00 63,00 160,0
			{ 1.70, 1.63, 1.56, 1.50, 1.44, 1.38, 1.32, 1.27, 1.22 }, // sand
																		// molds
			{ 1.60, 1.52, 1.45, 1.38, 1.31, 1.25, 0.0, 0.0, 0.0 }, // shell
																	// molds
			{ 1.55, 1.49, 1.42, 1.36, 1.30, 1.24, 1.18, 1.13, 0.0 }, // chill
			{ 1.38, 1.34, 1.30, 1.26, 1.22, 1.19, 1.16, 0.0, 0.0 }, // precise
																	// casting
			{ 1.35, 1.31, 1.28, 1.25, 1.21, 1.18, 1.15, 1.12, 1.10 }, // injection
																		// molding
			{ 0.0, 0.0, 0.0, 1.55, 1.50, 1.45, 1.40, 1.36, 0.0 }, // squeeze
																	// casting
	};

	private double k1_Magnesiun[][] = {
			// from 0,25 0,63 1,60 4,00 10,00 25,00 63,00 160,0
			// to 0,25 0,63 1,60 4,00 10,00 25,00 63,00 160,0
			{ 1.65, 1.59, 1.53, 1.47, 1.41, 1.35, 1.30, 1.25, 1.20 }, // sand
																		// molds
			{ 1.50, 1.43, 1.37, 1.31, 1.25, 1.20, 0.0, 0.0, 0.0 }, // shell
																	// molds
			{ 1.45, 1.39, 1.33, 1.28, 1.23, 1.19, 1.14, 1.10, 0.0 }, // chill
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // precise casting
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // injection
																// molding
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // squeeze casting
	};

	// coefficient matching mCast and mGFS

	private double k2_Iron[][] = {

			// from 0,25 0,63 1,60 4,00 10,00 25,00 63,00 160,0
			// to 0,25 0,63 1,60 4,00 10,00 25,00 63,00 160,0
			{ 2, 40, 2.23, 2.07, 1.93, 1.79, 1.66, 1.55, 1.45, 1.35 }, // sand
																		// molds
			{ 2.30, 2.15, 2.00, 1.88, 1.75, 1.64, 1.53, 1.43, 0.0 }, // shell
																		// molds
			{ 2.25, 2.10, 1.96, 1.83, 1.71, 1.60, 1.49, 1.39, 0.0 }, // chill
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // precise casting
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // injection
																// molding
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // squeeze casting
	};

	private double k2_StructuralSteel[][] = {
			// from 0,25 0,63 1,60 4,00 10,00 25,00 63,00 160,0
			// to 0,25 0,63 1,60 4,00 10,00 25,00 63,00 160,0
			{ 2.55, 2.36, 2.19, 2.02, 1.87, 1.73, 1.60, 1.48, 1.37 }, // sand
																		// molds
			{ 2.45, 2.28, 2.12, 1.97, 1.83, 1.70, 1.58, 1.47, 0.0 }, // shell
																		// molds
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // chill
			{ 3.27, 2.92, 2.61, 2.33, 2.08, 1.86, 1.66, 0.0, 0.0 }, // precise
																	// casting
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // injection
																// molding
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // squeeze casting
	};

	private double k2_HeatResistantSteel[][] = {
			// from 0,25 0,63 1,60 4,00 10,00 25,00 63,00 160,0
			// to 0,25 0,63 1,60 4,00 10,00 25,00 63,00 160,0
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // sand molds
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // shell molds
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // chill
			{ 4.87, 4.12, 3.50, 2.96, 2.51, 2.13, 1.80, 0.0, 0.0 }, // precise
																	// casting
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // injection
																// molding
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // squeeze casting
	};

	private double k2_Aluminium[][] = {
			// from 0,25 0,63 1,60 4,00 10,00 25,00 63,00 160,0
			// to 0,25 0,63 1,60 4,00 10,00 25,00 63,00 160,0
			{ 3.60, 3.25, 2.96, 2.69, 2.45, 2.22, 2.02, 1.84, 1.67 }, // sand
																		// molds
			{ 3.00, 2.69, 2.37, 2.09, 1.84, 1.62, 0.0, 0.0, 0.0 }, // shell
																	// molds
			{ 2.50, 2.29, 2.09, 1.91, 1.75, 1.60, 1.46, 1.34, 0.0 }, // chill
			{ 3.40, 2.94, 2.55, 2.21, 1.91, 1.65, 1.43, 0.0, 0.0 }, // precise
																	// casting
			{ 2.20, 2.05, 1.90, 1.77, 1.65, 1.53, 1.43, 1.33, 1.24 }, // injection
																		// molding
			{ 0.0, 0.0, 0.0, 4.00, 3.60, 3.30, 3.00, 2.80, 0.0 }, // squeeze
																	// casting
	};

	private double k2_magnii[][] = {
			// from 0,25 0,63 1,60 4,00 10,00 25,00 63,00 160,0
			// to 0,25 0,63 1,60 4,00 10,00 25,00 63,00 160,0
			{ 4.20, 3.83, 3.30, 3.20, 2.93, 2.68, 2.45, 2.24, 2.04 }, // sand
																		// molds
			{ 3.55, 3.16, 2.80, 2.49, 2.22, 1.97, 0.0, 0.0, 0.0 }, // shell
																	// molds
			{ 3.40, 3.11, 2.84, 2.59, 2.36, 2.16, 1.97, 1.40, 0.0 }, // chill
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // precise casting
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // injection
																// molding
			{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }, // squeeze casting
	};

	/*
	 * Iron - 0 StructuralSteel - 1 HeatResistantSteel - 2 Aluminium - 3
	 * Magnesiun Cr - 4 Magnesiun Al - 5
	 */

	public int setkindOfAlloy(String kindOfAlloy) {
		try {
			switch (kindOfAlloy) {
			case "Чугуны":
				return IRON;
			case "Констр. стали":
				return STRUCTURALSTEEL;
			case "Жаропроч. стали":
				return HEATRESISTANTSTEEL;
			case "Алюминий":
				return ALUMINIUM;
			case "Магний с сод. Cr":
				return MAGNESIUN_CR;
			case "Магний с сод. Al":
				return MAGNESIUN_AL;
			}
		} catch (Exception e) {
			System.out.println("Ошибка при установке сплава");
		}
		return -1;

	}

	public int getkindOfAlloy() {
		return kindOfAlloy;
	}

	/*
	 * sand molds - 0, shell molds - 1, chill - 2, precise casting - 3,
	 * injection molding - 4, squeeze casting - 5
	 */

	public int setkindOfCasting(String kindOfCasting) {
		try {
			switch (kindOfCasting) {
			case "В песчаные формы":
				return SAND_MOLDS;
			case "В оболочковые формы":
				return SHELL_MOLDS;
			case "В кокиль":
				return CHILL;
			case "ЛВМ":
				return PRECISE_CASTING;
			case "Под давлением":
				return INJECTION_MOLDING;
			case "Выжиманием":
				return SQUEEZE_CASTING;

			}
		} catch (Exception e) {
			System.out.println("Ошибка при установке вида литья");
		}
		return -1;

	}

	public void setkindOfSize(double mDet) {
		if (mDet < 0.25) {
			kindOfSize = 0;
		} else if (mDet > 0.25 && mDet <= 0.63) {
			kindOfSize = 1;
		} else if (mDet > 0.63 && mDet <= 1.60) {
			kindOfSize = 2;
		} else if (mDet > 1.60 && mDet <= 4.0) {
			kindOfSize = 3;
		} else if (mDet > 4.0 && mDet <= 10.0) {
			kindOfSize = 4;
		} else if (mDet > 10.0 && mDet <= 25.0) {
			kindOfSize = 5;
		} else if (mDet > 25.0 && mDet <= 63.0) {
			kindOfSize = 6;
		} else if (mDet > 63.0 && mDet <= 160.0) {
			kindOfSize = 7;
		} else if (mDet > 160.0) {
			kindOfSize = 8;
		}
	}

	public String getmDet() {
		return Formatter(mDet);
	}

	public void setmCast(double mCast) {

		if (mCast > 0) {
			this.mCast = mCast;
		} else {
			setmCast(kindOfCasting, kindOfSize);

		}
	}

	public void setmCast(int i, int j) {

		double k1 = -1;

		switch (kindOfAlloy) {
		case 0:
			k1 = k1_Iron[i][j];
			break;
		case 1:
			k1 = k1_StructuralSteel[i][j];
			break;
		case 2:
			k1 = k1_HeatResistantSteel[i][j];
			break;
		case 3:
			k1 = k1_Aluminium[i][j];
			break;
		case 4:
			k1 = k1_Magnesiun[i][j];
			break;
		case 5:
			k1 = k1_Magnesiun[i][j];
			break;

		}
		mCast = mDet * k1;

		setkindOfSize(mCast);

	}

	public String getmCast() {
		return Formatter(mCast);
	}

	public void setmGFS(double mGFS) {
		if (mGFS > 0) {
			this.mGFS = mGFS;
		} else
			setmGFS(kindOfCasting, kindOfSize);

	}

	public void setmGFS(int i, int j) {
		double k2 = 0;

		switch (kindOfAlloy) {
		case 0:
			k2 = k2_Iron[i][j];
			break;
		case 1:
			k2 = k2_StructuralSteel[i][j];
			break;
		case 2:
			k2 = k2_HeatResistantSteel[i][j];
			break;
		case 3:
			k2 = k2_Aluminium[i][j];
			break;
		case 4:
			k2 = k2_magnii[i][j];
			break;
		case 5:
			k2 = k2_magnii[i][j];
			break;

		}
		mGFS = mCast * k2;
	}

	public String getmGFS() {
		return Formatter(mGFS);
	}

	public void setkIzb() {

		if (kindOfAlloy == 0) {
			kIzb = kIzb_Iron[kindOfCasting][duplex];
		} else if (kindOfAlloy == 1) {
			kIzb = kIzb_StructuralSteel[kindOfCasting][kindOfSize];
		} else if (kindOfAlloy == 2) {
			kIzb = kIzb_HeatResistantSteel[kindOfCasting][kindOfSize];
		} else if (kindOfAlloy == 3) {
			kIzb = kIzb_Aluminium[kindOfCasting][duplex];
		} else if (kindOfAlloy == 4) {
			kIzb = kIzb_MagnesiunZr[kindOfCasting][duplex];
		} else if (kindOfAlloy == 5) {
			kIzb = kIzb_MagnesiunAl[kindOfCasting][duplex];
		}

	}

	public void setkLosses() {

		if (kindOfAlloy == 0) {
			this.kLosses = kLosses_Iron[kindOfCasting][duplex];
		} else if (kindOfAlloy == 1) {
			this.kLosses = kLosses_StructuralSteel[kindOfCasting][kindOfSize];
		} else if (kindOfAlloy == 2) {
			this.kLosses = kLosses_HeatResistantSteel[kindOfCasting][kindOfSize];
		} else if (kindOfAlloy == 3) {
			this.kLosses = kLosses_Aluminium[kindOfCasting][duplex];
		} else if (kindOfAlloy == 4) {
			this.kLosses = kLosses_Magnesiun[kindOfCasting][duplex];
		} else if (kindOfAlloy == 5) {
			this.kLosses = kLosses_Magnesiun[kindOfCasting][duplex];
		}

	}

	public void setwasteProduction() {
		if (kindOfAlloy == 0) {
			wasteProduction = wasteProduction_Iron[kindOfCasting][duplex];
		} else if (kindOfAlloy == 1) {
			wasteProduction = wasteProduction_StructuralSteel[kindOfCasting][kindOfSize];
		} else if (kindOfAlloy == 2) {
			wasteProduction = wasteProduction_HeatResistantSteel[kindOfCasting][kindOfSize];
		} else if (kindOfAlloy == 3) {
			wasteProduction = wasteProduction_Aluminium[kindOfCasting][duplex];
		} else if (kindOfAlloy == 4) {
			wasteProduction = wasteProduction_Magnesiun[kindOfCasting][duplex];
		} else if (kindOfAlloy == 5) {
			wasteProduction = wasteProduction_Magnesiun[kindOfCasting][duplex];
		}

	}

	public int setDuplex(boolean duplex) {
		if (duplex) {
			return 1;
		}
		return 0;
	}

	// getters for output data return String

	public String getmBatch() {
		return Formatter(mBatch);
	}

	public String getNormOfOutgo() {
		return Formatter(normOfOutgo);
	}

	public String getNewMetall() {
		return Formatter(newMetall);
	}

	public String getReconvery() {
		return Formatter(reconvery);
	}

	public String getmLosses() {
		return Formatter(mLosses);
	}

	// calculation
	public void processing() {

		// set data
		System.out.println("Новый рассчет");

		String s;
		if (duplex == 1) {
			s = "двойной";
		} else {
			s = "одинарный";
		}
		System.out.println("Сплав: " + kindOfAlloy);
		System.out.println("Вид литья: " + kindOfCasting);
		System.out.println("Переплав: " + s);
		System.out.println("М детали: " + mDet);
		System.out.println("М отливки: " + mCast);
		System.out.println("М отливки с лпс: " + mGFS);
		System.out.println("% брака: " + percentOfMarriage);
		System.out.println("% св. металла: " + percentOfNewMetall);
		System.out.println("К изб: " + kIzb);
		System.out.println("Б.В. Потери: " + kLosses);
		System.out.println("Отходы: " + wasteProduction);
		System.out.println();

		// processing

		mBatch = (mGFS * kIzb) / (1 - percentOfMarriage / 100);
		mLosses = mBatch * (kLosses + wasteProduction) / 100;
		normOfOutgo = mCast + mLosses;
		newMetall = mBatch * percentOfNewMetall / 100;
		reconvery = newMetall - normOfOutgo;

		if (reconvery < 0) {
			reconvery = 0;
		}

		System.out.println("Вес шихты: " + mBatch);
		System.out.println("Масса безв потерь: " + mLosses);
		System.out.println("Норма расхода " + normOfOutgo);
		System.out.println("свежего металла " + newMetall);
		System.out.println("Возврат " + reconvery);
		System.out.println();

	}

	// formating
	public static String Formatter(double d) {
		String formattedDouble = new DecimalFormat("#0.000").format(d);

		return formattedDouble;
	}

}
