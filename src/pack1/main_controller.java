package pack1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class main_controller {
		@FXML private Button insert;
		@FXML private Button show;
		@FXML private Button submit;
		@FXML private BorderPane mainP;
		@FXML private Label hm_pg;
		public void home(ActionEvent e) {			
			mainP.setCenter(null);
		}
		public void closeWindow(ActionEvent event) {
	        System.exit(0);
	    }
		public void show_click(ActionEvent e) {
			fxLoader ob=new fxLoader();
			Pane view = ob.getPage("show");
			mainP.setCenter(view);
		}
		public void insert_click(ActionEvent e) {
			fxLoader ob=new fxLoader();
			Pane view = ob.getPage("insert");
			mainP.setCenter(view);
		}
		public void s_all_click(ActionEvent e) {
			fxLoader ob=new fxLoader();
			Pane view = ob.getPage("show all");
			mainP.setCenter(view);
		}
		public void image_viewer(ActionEvent e) {
			fxLoader ob=new fxLoader();
			Pane view = ob.getPage("img_view");
			mainP.setCenter(view);
		}
		public void recorder(ActionEvent e) {
			fxLoader ob=new fxLoader();
			Pane view = ob.getPage("recorder");
			mainP.setCenter(view);
		}
		public void charts_loader(ActionEvent e) {
			fxLoader ob=new fxLoader();
			Pane view = ob.getPage("piechart");
			mainP.setCenter(view);
		}
		public void line_charts_loader(ActionEvent e) {
			fxLoader ob=new fxLoader();
			Pane view = ob.getPage("linechart");
			mainP.setCenter(view);
		}
		public void bar_charts_loader(ActionEvent e) {
			fxLoader ob=new fxLoader();
			Pane view = ob.getPage("barchart");
			mainP.setCenter(view);
		}
		public void sac_charts_loader(ActionEvent e) {
			fxLoader ob=new fxLoader();
			Pane view = ob.getPage("stackedac");
			mainP.setCenter(view);
		}
}
