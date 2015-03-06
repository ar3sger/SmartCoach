package de.itintouch.smartcoach;

import java.util.List;

import de.itintouch.smartcoach.workoutDB.Workout_DB;
import de.itintouch.smartcoach.workoutDB.Workout_DB_Handler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Launch extends Activity {

	ListView lv;
	private RoutineHandler handler;
	private Workout_DB_Handler workoutHandler;
	private EditText filter;
	List<Routine> routine;
	List<Workout> workout;
	private Button btn_add;
	private ProgressBar progressBar;
	private int progressBarStatus = 0;
	private Handler progressBarHandler = new Handler();
	private View view;
	private TextView tv_load;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);

		handler = new RoutineHandler(getApplicationContext());
		workoutHandler = new Workout_DB_Handler(getApplicationContext());

		try {
			deleteAllWorkouts();

		} catch (Exception e) {
			Log.d("LOGWORKOUTs", "FEHLER");
		}

		try {
			deleteAllRoutines();
		} catch (Exception e) {
			Log.d("LOGROUTINES", "FEHLER");
		}
		//addSomeWorkouts(); 
		addSomeRoutines();
		startProgressDialog(view);
	}

	public void startProgressDialog(View V) {

		// prepare for a progress bar dialog
		progressBar = (ProgressBar) findViewById(R.id.progressBar2);
		progressBar.setProgress(0);
		progressBar.setMax(100);

		// reset progress bar status
		progressBarStatus = 0;

		new Thread(new Runnable() {
			public void run() {
				view = getWindow().getDecorView().findViewById(
						android.R.id.content);
				tv_load = (TextView) findViewById(R.id.tv_load);
				tv_load.setText("Laden: 0%");
				while (progressBarStatus < 100) {

					// process some tasks
					progressBarStatus += 5;

					// sleep 1 second to show the progress
					try {
						Thread.sleep(100);

					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					// Update the progress bar
					progressBarHandler.post(new Runnable() {
						public void run() {
							progressBar.setProgress(progressBarStatus);
							tv_load.setText("Laden: " + progressBarStatus + "%");
						}
					});
				}

				// when, file is downloaded 100%,
				if (progressBarStatus >= 100) {
					Intent intent = new Intent(getBaseContext(), Login.class);
					startActivity(intent);
					finish();
				}
			}
		}).start();

	}

	private void deleteAllRoutines() {
		List<Routine> routine = handler.readAllRoutines();
		for (Routine c : routine) {
			handler.deleteRoutine(c.getID());
		}
	}

	private void deleteAllWorkouts() {
		List<Workout_DB> workoutsList = workoutHandler.readAllWorkout();
		for (Workout_DB workouts : workoutsList) {
			workoutHandler.deleteWorkout(workouts.get_id());
		}
	}

	private void addSomeWorkouts() {
		workoutHandler.addWorkoutDetails(new Workout_DB(1, "Kreuzheben", 3, 12,
				1, 2, 3, 20.5));
	}

	private void addSomeRoutines() {
		// //////////////////////////////////////////////////////////
		// ////////// Back
		// //////////////////////////////////////////////////////////
		handler.addRoutineDetails(new Routine(
				"Kreuzheben",
				"R�cken/Beine",
				"Kreuzheben ist eine umstrittene �bung � zu unrecht. Oft wird behauptet, sie sei r�ckensch�digend, doch das ist falsch. Im Gegenteil: Wenn man auf die richtige Ausf�hrung achtet, st�rkt Kreuzheben den R�cken. Deshalb wird sie auch im Rehasport eingesetzt. Doch auch aus dem Kraftsport und dem Bodybuilding ist das Kreuzheben nicht wegzudenken. Kreuzheben z�hlt zu den Grund�bungen und st�rkt den R�cken und die Beinmuskulatur.",
				"", "routine/deadlift.png", 3, 15, "Ausf�hrung"));

		// //////////////////////////////////////////////////////////
		// ////////// Biceps
		// //////////////////////////////////////////////////////////
		handler.addRoutineDetails(new Routine(
				"Bizeps-Curls",
				"Bizeps",
				"Das Armbeugen mit Kurzhanteln, gerne als Bizepscurls bezeichnet, ist wohl die bekannteste aller �bungen zum Muskelaufbau. Sie trainiert den langen (musculus biceps brachii caput longum) und den kurzen Kopf des Bizeps (musculus biceps brachii caput breve) sowie den Armbeuger (musculus brachialis). Durch die einfache und schnell zu erlernende �bungsausf�hrung ist sie auch Einsteigern zu empfehlen.",
				"https://d1wst0behutosd.cloudfront.net/videos/1131149.mp4?Expires=1424955182&Signature=U~0Zokwi~JoDo0K2A8wzl48AphD42DlMqpOlvgpfIGtxYm7qrPRCxSn~XDGFX2acQK~dz0ry-sJspUgYyyunEAlChTWQT8bHmzfJyJU9qDv~AdYFgPthj-60gXXS-d7~cKS0TnRaJJzlmYqRcXWN89x6Wg8jnOdM2wOPRpNn6zsdJFhvsC2BA-CIPGPPbv7apK4VZXlF5CiNpQQEfBB6ix8-01NZSI1G8w-eXnij-lcDHrcU5ZRbCXUUCtu7kPkAiW0obo6qSj4-AmoWa~Q5ExIES9l8nRAcuDdSaf06GTPG5unEKrHYYXsyLdUT9ecwroFBfFZCJ2SQ9Smkxvtw9w__&Key-Pair-Id=APKAJJ6WELAPEP47UKWQ",
				"routine/bicepscurls.png", 3, 12, "Ausf�hrung"));

		// //////////////////////////////////////////////////////////
		// ////////// Chest
		// //////////////////////////////////////////////////////////
		handler.addRoutineDetails(new Routine(
				"Bankdr�cken",
				"Brust/Trizeps",
				"Das Bankdr�cken gilt im Bodybuilding als wichtigste �bung f�r das Training der Brustmuskulatur. Gemeinsam mit dem Kreuzheben und den Kniebeugen bildet das Bankdr�cken au�erdem die drei Disziplinen des Kraftdreikampfs. Es gibt Varianten des Bankdr�ckens, die an dieser Stelle erw�hnt werden sollen. Zun�chst l�sst sich das Bankdr�cken auch mit Kurzhanteln ausf�hren. Au�erdem gibt es die M�glichkeit, die Bank entweder positiv zu neigen (Schr�gbankdr�cken) oder negativ zu neigen (Negativ-Bankdr�cken). Insbesondere das Schr�gbankdr�cken ist popul�r, weil viele Athleten glauben, mit dieser �bung einen Schwerpunkt auf das Training des oberen Teils der Brust legen zu k�nnen. Diese These ist jedoch nicht wissenschaftlich fundiert. Au�erdem gibt es das enge Bankdr�cken, das den Trizeps in besonderem Ma�e beansprucht.",
				"https://d1wst0behutosd.cloudfront.net/videos/1131215.mp4?Expires=1424955042&Signature=ByK-ZK8pJ2n6Ct1trqxug9xTEUC0mkflJtskBFovqKIaeymaB1ju~Fv-iuFr8ipllWQZpKQHTWz~oDJOmndgFAb9YZyGO-08LuAjZmvcFYZorfOEIZSDNxeMp-0DfLGeE37pwY4BrmNOm5bypY6jyustFsDK54GYkl11UH5H~Hlc6xV1ti~kSPpwCkwDHxT-Vviog3b-qem4r-pCCUjSe~49W~M8cN1NAuyL7WC9xnYSTozUxsjkPI0S5ElUC5mXGFS0kwIb4Gbt3ukk1-92o1Cyq3PjPD3ApiJTt883jD7i28rTXrd1FAG3rWtx2cp-sSCNr7Rw6QuFlRLi7pWjBA__&Key-Pair-Id=APKAJJ6WELAPEP47UKWQ",
				"routine/benchpress.png", 3, 12, "Ausf�hrung"));

		handler.addRoutineDetails(new Routine(
				"Flyes (Bank)",
				"Brust",
				"Eine bei den Profis sehr gesch�tzte, im Fitnessstudio jedoch eher selten zu sehende �bung sind die Fliegenden. Diese �bung entspricht den Bewegungsabl�ufen am Butterfly-Ger�t oder den Butterflys am Kabelzugturm. Nur mit einem Satz Kurzhanteln und zur Not auch ohne eine Flachbank durchf�hrbar, l�sst sich diese �bung auch ohne Probleme zu Hause trainieren. Fokus der Fliegenden ist nat�rlich der gro�e Brustmuskel (musculus pectoralis major). Aufgrund der Rotationsbewegung werden zus�tzlich die vordere Schulter (musculus deltoideus pars clavicularis), der vordere S�gemuskel (musculus serratus anterior) sowie der Knorrenmuskel (musculus anconaeus) trainiert.",
				"", "routine/fliesbench.png", 3, 12, "Ausf�hrung"));

		handler.addRoutineDetails(new Routine(
				"Liegest�tze",
				"Brust/Trizeps",
				"Die Liegest�tze trainieren den Trizeps und die Brustmuskeln.",
				"https://d1wst0behutosd.cloudfront.net/videos/1131179.mp4?Expires=1424955331&Signature=DtLqpViyBd5NYAwVmM~GYaqi8V-wYM~NFDiwRrXUtxWamH2fZDwZnAlASnGHvTLzK6pR65w3VW2TZaHOYWc4TkIO7iFX75B~YWI0N2Q4LcnvuOTnvpdcUiShf5XXkCcGCKtq7j4WDqx0NlE2siIWB7dUBdaP3tcVy5tIFSvY3m3XJyIyy4wftzbewiK8safZ2qzlF4oofIfAQWFWxiTq5K5SufnCOgvrp6SffB1qZKkYlcLJat0IoE4tT3NB2u3qqC~ULZlZ96fEqfdVzA-6tNO9Jx2FZ-5305cRPSV3LiPq3ScnF~rajocbBMZW~wjHegQGqG31BFQCtcVcM0bisA__&Key-Pair-Id=APKAJJ6WELAPEP47UKWQ",
				"routine/pushups.png", 3, 12, "Ausf�hrung"));

		// //////////////////////////////////////////////////////////
		// ////////// Calves
		// //////////////////////////////////////////////////////////
		handler.addRoutineDetails(new Routine(
				"Wadenheben",
				"Waden",
				"Das Wadenheben ohne Equipment ist eine �bung, bei der allein das eigene K�rpergewicht bew�ltigt werden muss. Je nach Trainingsstand kann das durchaus eine Herausforderung darstellen. Fortgeschrittenen empfehlen wir dennoch das anspruchsvollere Wadenheben am Ger�t, das Wadenheben mit Kurzhanteln oder das Wadenheben mit der Langhantel.",
				"https://d1wst0behutosd.cloudfront.net/videos/1131158.mp4?Expires=1424955147&Signature=GoRLMqTKWqIyjq3kCqvAr7l-EKt9NxdO2T~b~OHRzAVrw1t-nZXrthHg5TUkglSjNNciLG8tYFeKTQnsjJvMDtzh9~QsaaQb0bPCFKI6psl5Evu49Ky0f~yNVEcTC1XwsRctSB7F~NSx8H1o-OdWum3QW7zd7Qf4MDWtOjgN6qh6JzE6aXDP2oVuzoM5hOOCPYsfekLUhPY6PjVEGwKWo1puWDNTjdxAcBchav7BufHlBKb1BPyiIrMajNv2pzZgIGMRA4Si2hkVA0k8clNjtAQk1uMu5kTiu1lFZhNid-HNuZYuL50dE3WMFFmAhcBlzARaLvwNusfJuJx2jX3mWg__&Key-Pair-Id=APKAJJ6WELAPEP47UKWQ",
				"routine/calfraise.png", 3, 12, "Ausf�hrung"));

		// //////////////////////////////////////////////////////////
		// ////////// Legs
		// //////////////////////////////////////////////////////////
		handler.addRoutineDetails(new Routine(
				"Beinpresse",
				"Beine",
				"Eine der bekanntesten und wirkungsvollsten �bungen an einem Kraftger�t ist die Beinpresse. Fast jedes Fitnessstudio, jedes Leistungszentrum und jeder Physiotherapeut hat solch eine Maschine, weshalb unumstritten sein d�rfte, dass dieses Fitnessger�t eines der besten ist. Es gibt verschiedene Varianten dieses Ger�ts, die sich aber in ihrer Wirkungsweise kaum unterscheiden. Trainiert werden hier fast s�mtliche Muskeln der Beine und zudem der Po. Im Detail sind dies die Beinstrecker (musculus quadrizeps femoris) und Beinbeuger (musculus biceps femoris) sowie der gro�e Ges��muskel (musculus gluteus maximus). Mit beansprucht werden je nach Fu�stellung die Adduktoren (musculus adductor) und die Wadenmuskulatur (musculus gastrocnemius).",
				"https://d1wst0behutosd.cloudfront.net/videos/1124090.mp4?Expires=1424950313&Signature=UZgBXdodwjK86gPtuWWdgylgML5~9FoQe-sffSbwI1cxMVpSq8JWW1CnkZfgw9hy4p-YsV-lNG5h9DfFAMMCF2-clxRu7jjk27SNw1~xvuB9IoC7rqXB9ySyqYtoj5ugd183fFI3io17ORU93n3q-E2D-D0MS2a4CRTOCbs3W24ofKKrSB~coOgcAEvTjw4ZBDJwGFEXbJQ27La2eKLUawRcQWWNdWqgPSd1tCcUN8C2RFBhi4Tea1I6FoUzUqji6fJYPh5kb7qGCWGyC2Fzl2WaMgu3JyBmO8wL2EJya~wJgmMUKMvY4cc9cBbX-qlNQ3eweLP92qTx~zZrfphAsg__&Key-Pair-Id=APKAJJ6WELAPEP47UKWQ",
				"routine/legpress.png", 3, 12, "Ausf�hrung"));

		// //////////////////////////////////////////////////////////
		// ////////// Abs
		// //////////////////////////////////////////////////////////
		handler.addRoutineDetails(new Routine(
				"Seitliche Crushes",
				"Bauchmuskulatur",
				"Die Seitlichen Crunches zum Fu� sind eine Variante der Seitlichen Crunches. Sie trainieren die schr�gen Bauchmuskeln und k�nnen ohne Equipment absolviert werden.",
				"https://d1wst0behutosd.cloudfront.net/videos/1131203.og2.mp4?Expires=1424955097&Signature=NeSNO-uVQCWr1i5lZEyOR5ogM3R6YcQxKmxBB6iI8T8W3e8~tlAGWtkn4y8brZ0t3mG0n9N6jMjLKO87JIhe~5Ss7w9AUPNzK-QgwFUW-dJhsP~Qo2pesi3fSL1A0wjDEFtOaFTpga-zXHo6pTbqrPmCBBLe51rCyG7oaQHDtExtC57uFlBKlocTESLGT59WPB1Hg7psyZ-YUvAFa4~esn1zUSsLuPKhN0AZxvphVqUPaZOg7E1wUC6-wCDzxEU2zcdSurReYEUUnAmWcSFT~8vmRUPSY~sHD8R9g5OeeqdUSOg6XeJOQm1ikhI3JZ1VnkwiKCsklR1Dk09w0pT0Qw__&Key-Pair-Id=APKAJJ6WELAPEP47UKWQ",
				"routine/sidecrushes.png", 3, 12, "Ausf�hrung"));
	}

}