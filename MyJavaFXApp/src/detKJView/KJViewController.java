package detKJView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import extfx.scene.control.DatePicker;
import extfx.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

//import javax.swing.event.ChangeListener;

public class KJViewController {
	
	public KJViewController(){
		init1();
	}
	
	
	@FXML
	private Label pStartDatum;
	
	@FXML
	protected void init1(){ 
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				GregorianCalendar now = new GregorianCalendar();
				DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
				pEndDatum.setText(df.format(now.getTime()));
				now.add(Calendar.DATE, -90);
				pStartDatum.setText(df.format(now.getTime()));
			}
		});
	}
	
	@FXML
	protected <pStartDatum> void handleSubmitButtonAction(ActionEvent event) {
		pStartDatum.setText("01.01.2000");
	}

	@FXML
	private Label pEndDatum;
	
	@FXML
	private Label pCBox;
	
	@FXML
	private ChoiceBox<String> ThChoice;
	
	@FXML
	private void initialize() {
		final String[] greetings = new String[] { "Hello", "Hola", "Привет", "你好",
		"こんにちは" };

		ThChoice.getSelectionModel().selectedIndexProperty()
				.addListener(new ChangeListener<Number>() {
					public void changed(ObservableValue ov, Number value,
							Number new_value) {
						pCBox.setText(greetings[new_value.intValue()]);
					}
				});
	}

	@FXML
	protected <pEndDatum> void handleSubmitButtonAction1(ActionEvent event) {
		DatePicker datePicker = new DatePicker();
//		datePicker.getCalendarView().setCalendar(new GregorianCalendar());
		datePicker.setLocale(Locale.GERMAN);
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
//		pEndDatum.setText(df.format(datePicker.getUserData().selectedDate));

	}

	

}
