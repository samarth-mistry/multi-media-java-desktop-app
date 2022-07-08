package pack1;

import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class fxLoader {
	private Pane view;
	public Pane getPage(String name) {
		try {
			URL fileURL=main_page.class.getResource(name+".fxml");
			if(fileURL==null) {
				throw new java.io.FileNotFoundException("FILE NOT FOUND");
			}
			System.out.println(fileURL);
			new FXMLLoader();
			view=FXMLLoader.load(fileURL);
		}catch(Exception e) {
			System.out.println("Exception in fxLoader");
			e.printStackTrace();
		}
		return view;
	}
}
