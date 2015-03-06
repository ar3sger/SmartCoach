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
				"Rücken/Beine",
				"Kreuzheben ist eine umstrittene Übung – zu unrecht. Oft wird behauptet, sie sei rückenschädigend, doch das ist falsch. Im Gegenteil: Wenn man auf die richtige Ausführung achtet, stärkt Kreuzheben den Rücken. Deshalb wird sie auch im Rehasport eingesetzt. Doch auch aus dem Kraftsport und dem Bodybuilding ist das Kreuzheben nicht wegzudenken. Kreuzheben zählt zu den Grundübungen und stärkt den Rücken und die Beinmuskulatur.",
				"", "routine/deadlift.png", 3, 15, "Ausführung"));

		// //////////////////////////////////////////////////////////
		// ////////// Biceps
		// //////////////////////////////////////////////////////////
		handler.addRoutineDetails(new Routine(
				"Bizeps-Curls",
				"Bizeps",
				"Das Armbeugen mit Kurzhanteln, gerne als Bizepscurls bezeichnet, ist wohl die bekannteste aller Übungen zum Muskelaufbau. Sie trainiert den langen (musculus biceps brachii caput longum) und den kurzen Kopf des Bizeps (musculus biceps brachii caput breve) sowie den Armbeuger (musculus brachialis). Durch die einfache und schnell zu erlernende Übungsausführung ist sie auch Einsteigern zu empfehlen.",
				"https://d1wst0behutosd.cloudfront.net/videos/1131149.mp4?Expires=1424955182&Signature=U~0Zokwi~JoDo0K2A8wzl48AphD42DlMqpOlvgpfIGtxYm7qrPRCxSn~XDGFX2acQK~dz0ry-sJspUgYyyunEAlChTWQT8bHmzfJyJU9qDv~AdYFgPthj-60gXXS-d7~cKS0TnRaJJzlmYqRcXWN89x6Wg8jnOdM2wOPRpNn6zsdJFhvsC2BA-CIPGPPbv7apK4VZXlF5CiNpQQEfBB6ix8-01NZSI1G8w-eXnij-lcDHrcU5ZRbCXUUCtu7kPkAiW0obo6qSj4-AmoWa~Q5ExIES9l8nRAcuDdSaf06GTPG5unEKrHYYXsyLdUT9ecwroFBfFZCJ2SQ9Smkxvtw9w__&Key-Pair-Id=APKAJJ6WELAPEP47UKWQ",
				"routine/bicepscurls.png", 3, 12, "Ausführung"));

		// //////////////////////////////////////////////////////////
		// ////////// Chest
		// //////////////////////////////////////////////////////////
		handler.addRoutineDetails(new Routine(
				"Bankdrücken",
				"Brust/Trizeps",
				"Das Bankdrücken gilt im Bodybuilding als wichtigste Übung für das Training der Brustmuskulatur. Gemeinsam mit dem Kreuzheben und den Kniebeugen bildet das Bankdrücken außerdem die drei Disziplinen des Kraftdreikampfs. Es gibt Varianten des Bankdrückens, die an dieser Stelle erwähnt werden sollen. Zunächst lässt sich das Bankdrücken auch mit Kurzhanteln ausführen. Außerdem gibt es die Möglichkeit, die Bank entweder positiv zu neigen (Schrägbankdrücken) oder negativ zu neigen (Negativ-Bankdrücken). Insbesondere das Schrägbankdrücken ist populär, weil viele Athleten glauben, mit dieser Übung einen Schwerpunkt auf das Training des oberen Teils der Brust legen zu können. Diese These ist jedoch nicht wissenschaftlich fundiert. Außerdem gibt es das enge Bankdrücken, das den Trizeps in besonderem Maße beansprucht.",
				"https://d1wst0behutosd.cloudfront.net/videos/1131215.mp4?Expires=1424955042&Signature=ByK-ZK8pJ2n6Ct1trqxug9xTEUC0mkflJtskBFovqKIaeymaB1ju~Fv-iuFr8ipllWQZpKQHTWz~oDJOmndgFAb9YZyGO-08LuAjZmvcFYZorfOEIZSDNxeMp-0DfLGeE37pwY4BrmNOm5bypY6jyustFsDK54GYkl11UH5H~Hlc6xV1ti~kSPpwCkwDHxT-Vviog3b-qem4r-pCCUjSe~49W~M8cN1NAuyL7WC9xnYSTozUxsjkPI0S5ElUC5mXGFS0kwIb4Gbt3ukk1-92o1Cyq3PjPD3ApiJTt883jD7i28rTXrd1FAG3rWtx2cp-sSCNr7Rw6QuFlRLi7pWjBA__&Key-Pair-Id=APKAJJ6WELAPEP47UKWQ",
				"routine/benchpress.png", 3, 12, "Ausführung"));

		handler.addRoutineDetails(new Routine(
				"Flyes (Bank)",
				"Brust",
				"Eine bei den Profis sehr geschätzte, im Fitnessstudio jedoch eher selten zu sehende Übung sind die Fliegenden. Diese Übung entspricht den Bewegungsabläufen am Butterfly-Gerät oder den Butterflys am Kabelzugturm. Nur mit einem Satz Kurzhanteln und zur Not auch ohne eine Flachbank durchführbar, lässt sich diese Übung auch ohne Probleme zu Hause trainieren. Fokus der Fliegenden ist natürlich der große Brustmuskel (musculus pectoralis major). Aufgrund der Rotationsbewegung werden zusätzlich die vordere Schulter (musculus deltoideus pars clavicularis), der vordere Sägemuskel (musculus serratus anterior) sowie der Knorrenmuskel (musculus anconaeus) trainiert.",
				"", "routine/fliesbench.png", 3, 12, "Ausführung"));

		handler.addRoutineDetails(new Routine(
				"Liegestütze",
				"Brust/Trizeps",
				"Die Liegestütze trainieren den Trizeps und die Brustmuskeln.",
				"https://d1wst0behutosd.cloudfront.net/videos/1131179.mp4?Expires=1424955331&Signature=DtLqpViyBd5NYAwVmM~GYaqi8V-wYM~NFDiwRrXUtxWamH2fZDwZnAlASnGHvTLzK6pR65w3VW2TZaHOYWc4TkIO7iFX75B~YWI0N2Q4LcnvuOTnvpdcUiShf5XXkCcGCKtq7j4WDqx0NlE2siIWB7dUBdaP3tcVy5tIFSvY3m3XJyIyy4wftzbewiK8safZ2qzlF4oofIfAQWFWxiTq5K5SufnCOgvrp6SffB1qZKkYlcLJat0IoE4tT3NB2u3qqC~ULZlZ96fEqfdVzA-6tNO9Jx2FZ-5305cRPSV3LiPq3ScnF~rajocbBMZW~wjHegQGqG31BFQCtcVcM0bisA__&Key-Pair-Id=APKAJJ6WELAPEP47UKWQ",
				"routine/pushups.png", 3, 12, "Ausführung"));

		// //////////////////////////////////////////////////////////
		// ////////// Calves
		// //////////////////////////////////////////////////////////
		handler.addRoutineDetails(new Routine(
				"Wadenheben",
				"Waden",
				"Das Wadenheben ohne Equipment ist eine Übung, bei der allein das eigene Körpergewicht bewältigt werden muss. Je nach Trainingsstand kann das durchaus eine Herausforderung darstellen. Fortgeschrittenen empfehlen wir dennoch das anspruchsvollere Wadenheben am Gerät, das Wadenheben mit Kurzhanteln oder das Wadenheben mit der Langhantel.",
				"https://d1wst0behutosd.cloudfront.net/videos/1131158.mp4?Expires=1424955147&Signature=GoRLMqTKWqIyjq3kCqvAr7l-EKt9NxdO2T~b~OHRzAVrw1t-nZXrthHg5TUkglSjNNciLG8tYFeKTQnsjJvMDtzh9~QsaaQb0bPCFKI6psl5Evu49Ky0f~yNVEcTC1XwsRctSB7F~NSx8H1o-OdWum3QW7zd7Qf4MDWtOjgN6qh6JzE6aXDP2oVuzoM5hOOCPYsfekLUhPY6PjVEGwKWo1puWDNTjdxAcBchav7BufHlBKb1BPyiIrMajNv2pzZgIGMRA4Si2hkVA0k8clNjtAQk1uMu5kTiu1lFZhNid-HNuZYuL50dE3WMFFmAhcBlzARaLvwNusfJuJx2jX3mWg__&Key-Pair-Id=APKAJJ6WELAPEP47UKWQ",
				"routine/calfraise.png", 3, 12, "Ausführung"));

		// //////////////////////////////////////////////////////////
		// ////////// Legs
		// //////////////////////////////////////////////////////////
		handler.addRoutineDetails(new Routine(
				"Beinpresse",
				"Beine",
				"Eine der bekanntesten und wirkungsvollsten Übungen an einem Kraftgerät ist die Beinpresse. Fast jedes Fitnessstudio, jedes Leistungszentrum und jeder Physiotherapeut hat solch eine Maschine, weshalb unumstritten sein dürfte, dass dieses Fitnessgerät eines der besten ist. Es gibt verschiedene Varianten dieses Geräts, die sich aber in ihrer Wirkungsweise kaum unterscheiden. Trainiert werden hier fast sämtliche Muskeln der Beine und zudem der Po. Im Detail sind dies die Beinstrecker (musculus quadrizeps femoris) und Beinbeuger (musculus biceps femoris) sowie der große Gesäßmuskel (musculus gluteus maximus). Mit beansprucht werden je nach Fußstellung die Adduktoren (musculus adductor) und die Wadenmuskulatur (musculus gastrocnemius).",
				"https://d1wst0behutosd.cloudfront.net/videos/1124090.mp4?Expires=1424950313&Signature=UZgBXdodwjK86gPtuWWdgylgML5~9FoQe-sffSbwI1cxMVpSq8JWW1CnkZfgw9hy4p-YsV-lNG5h9DfFAMMCF2-clxRu7jjk27SNw1~xvuB9IoC7rqXB9ySyqYtoj5ugd183fFI3io17ORU93n3q-E2D-D0MS2a4CRTOCbs3W24ofKKrSB~coOgcAEvTjw4ZBDJwGFEXbJQ27La2eKLUawRcQWWNdWqgPSd1tCcUN8C2RFBhi4Tea1I6FoUzUqji6fJYPh5kb7qGCWGyC2Fzl2WaMgu3JyBmO8wL2EJya~wJgmMUKMvY4cc9cBbX-qlNQ3eweLP92qTx~zZrfphAsg__&Key-Pair-Id=APKAJJ6WELAPEP47UKWQ",
				"routine/legpress.png", 3, 12, "Ausführung"));

		// //////////////////////////////////////////////////////////
		// ////////// Abs
		// //////////////////////////////////////////////////////////
		handler.addRoutineDetails(new Routine(
				"Seitliche Crushes",
				"Bauchmuskulatur",
				"Die Seitlichen Crunches zum Fuß sind eine Variante der Seitlichen Crunches. Sie trainieren die schrägen Bauchmuskeln und können ohne Equipment absolviert werden.",
				"https://d1wst0behutosd.cloudfront.net/videos/1131203.og2.mp4?Expires=1424955097&Signature=NeSNO-uVQCWr1i5lZEyOR5ogM3R6YcQxKmxBB6iI8T8W3e8~tlAGWtkn4y8brZ0t3mG0n9N6jMjLKO87JIhe~5Ss7w9AUPNzK-QgwFUW-dJhsP~Qo2pesi3fSL1A0wjDEFtOaFTpga-zXHo6pTbqrPmCBBLe51rCyG7oaQHDtExtC57uFlBKlocTESLGT59WPB1Hg7psyZ-YUvAFa4~esn1zUSsLuPKhN0AZxvphVqUPaZOg7E1wUC6-wCDzxEU2zcdSurReYEUUnAmWcSFT~8vmRUPSY~sHD8R9g5OeeqdUSOg6XeJOQm1ikhI3JZ1VnkwiKCsklR1Dk09w0pT0Qw__&Key-Pair-Id=APKAJJ6WELAPEP47UKWQ",
				"routine/sidecrushes.png", 3, 12, "Ausführung"));
	}

}