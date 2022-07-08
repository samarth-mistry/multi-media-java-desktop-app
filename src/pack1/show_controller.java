package pack1;

import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.TargetDataLine;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Callback;

public class show_controller implements Initializable {
	@FXML private Label s_sts;
	@FXML private Label s_u;
	@FXML private Label s_e;
	@FXML private Label s_p;
	@FXML private Label i_sts;
	@FXML private Label i_sts_succ;
	@FXML private Label img_name;
	@FXML private TextField s_ser;
	@FXML private TextField i_u;
	@FXML private TextField i_p;
	@FXML private TextField i_e;
	@FXML private TextField i_a;
	@FXML private TableView s_all_table;
	@FXML private ComboBox s_cbox;
	@FXML private Label r_sts;
	@FXML private ImageView imgPane;
	@FXML private AnchorPane mainAnchorpane;
	@FXML private AnchorPane soundAnchorpane;
	@FXML private Slider i_slid;
	private File iconimage;
	private File musicFile;
	private ObservableList<ObservableList> data;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	public void show(){		//Search Page
		if(s_ser.getText().isEmpty()) {
			s_sts.setText("Please enter name in above feild");
			s_ser.setText("");
		}
		else {
			try {		
				PreparedStatement pst = null;
				Connection conn=null;
				conn =  xampp_connector.getConnection();
		        pst = conn.prepareStatement("SELECT * from user_table WHERE username='"+s_ser.getText()+"'");
		        System.out.println(pst);
		        //s_ser.setText("");
		        ResultSet rs = pst.executeQuery();
		        int i = 0;
		        if (rs.next()) {               
		            s_u.setText(" "+rs.getString("username"));
		            s_e.setText(" "+rs.getString("email"));
		            s_p.setText(" "+rs.getString("password"));
		            i++;
		            System.out.println("found");
		        }
		        
		        conn.close();
		        if (i < 1) {s_sts.setText("No record found");System.out.println("Not found");}
		        if (i == 1) {s_sts.setText("One record found");}
		        if(i>1){s_sts.setText(i+" records found");}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}		
	}
	public void printer() {		//Insert into DB
		if(i_u.getText().isEmpty()){
			i_sts.setText("Please Enter USERNAME");
		}
		else if(i_e.getText().isEmpty()) {				
			i_sts.setText("Please Enter EMAIL");
		}
		else if(i_p.getText().isEmpty()) {				
			i_sts.setText("Please Enter PASSWORD");
		}else {
			try {
			 	Connection conn= null;
			 	conn =  xampp_connector.getConnection();
		         PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTo user_table(username,email,password) VALUES(?,?,?)");
		         preparedStatement.setString(1,i_u.getText() );
		         preparedStatement.setString(2, i_e.getText());
		         preparedStatement.setString(3, i_p.getText());

		         //System.out.println(preparedStatement);
		         preparedStatement.executeUpdate();
		         i_reset();
		         i_sts_succ.setText("Successfully added !");
		         conn.close();
		    } catch (SQLException e) {
		    	e.printStackTrace();
		    	i_sts.setText("Error ocurred !");
		    }
			System.out.println(i_u.getText());
			System.out.println(i_p.getText());
		}		
	}
	public void s_delete() {	//delete page
		
		Alert alert = new Alert(AlertType.CONFIRMATION, "Delete this item ?", ButtonType.YES, ButtonType.CANCEL);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES) { 
		    			
		if(s_ser.getText().isEmpty()) {
			s_sts.setText("Please enter name in above feild");
			s_ser.setText("");
		}else {			
			try {
				Connection conn=null;
				conn =  xampp_connector.getConnection();
				Statement stmt = conn.createStatement();
				int rs =stmt.executeUpdate("DELETE FROM user_table WHERE username='"+s_ser.getText()+"'");
				if(rs==1) {
					s_reset();
					s_sts.setText("One record deleted");
				}else if(rs>1) {
					s_reset();
					s_sts.setText(rs+" records deleted");
				}
				System.out.println("Record deleted successfully");
				conn.close();
				
			}catch (SQLException e) {
			      e.printStackTrace();
			}
		}}
	}
	public void s_all() {		//show all page	
        ResultSet rs;
        data = FXCollections.observableArrayList();
       
        try {
        	Connection conn=null;
			conn =  xampp_connector.getConnection();
			Statement st = conn.createStatement();
			String recQuery="SELECT username,email,password FROM user_table";
			rs = st.executeQuery(recQuery);
			for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;                
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                        return new SimpleStringProperty(param.getValue().get(j).toString());                        
                    }                    
                });

                s_all_table.getColumns().addAll(col); 
                System.out.println("Column ["+i+"] ");
            }
            while (rs.next()) {
            	ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added "+row );
                data.add(row);
            }
            //System.out.println(data);
            s_all_table.setItems(data);
        }catch(Exception e) {
        	e.printStackTrace();
        }
	}
	public void s_dd() {	//show_all drop down
		ResultSet rs;
        data = FXCollections.observableArrayList();
       
        try {
        	Connection conn=null;
			conn =  xampp_connector.getConnection();
			Statement st = conn.createStatement();
			String recQuery="SELECT username FROM user_table";
			rs = st.executeQuery(recQuery);
			while (rs.next()) {	  
				ObservableList<String> row = FXCollections.observableArrayList();    
				row.add(rs.getString("username"));
				data.add(row);
	        }
			s_cbox.setItems(data);
        }catch(Exception e){
        	e.printStackTrace();
        }
	}
	public void play_mus(){
		System.out.println("Music called");
		try {
			FileChooser filechooser = new FileChooser();
			musicFile = filechooser.showOpenDialog(soundAnchorpane.getScene().getWindow());
			System.out.println(musicFile.getName());
			if (musicFile != null) {
			      //String mfile = iconimage.getAbsolutePath();			      
			      System.out.println("INSIDE if");
			      Media sound = new Media(musicFile.toURI().toString());
			      MediaPlayer mediaPlayer = new MediaPlayer(sound);
			      mediaPlayer.play();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void image_view() {		//Default image load CANCER al-Gujarti
		try {
			File imgP= new File("src/images/cagair.png");			
			System.out.println("No Exception In image loading");
			img_name.setText("CANCER Al-Gujarati");
			Image image = new Image(imgP.toURI().toString());			
			imgPane.setImage(image);
			imgPane.setCache(true);

		}catch(Exception e) {
			System.out.println("Exception In image loading");
			e.printStackTrace();
		}
	}
	public void start_ss() {
		for (int i = 0; i < 5; i++) {
		    try {
		        Thread.sleep(5000);
		    } catch (Exception e) {
		        System.out.println("Error: " + e.toString());
		    }		   
		    File img =new File("src/images/Screenshot ("+i+").png");
		    System.out.println(img.getName());
		    Image image = new Image(img.toURI().toString());
		    imgPane.setImage(image);
		}
	}
	public void slider_changed() {		
		int i_sl_v = (int) i_slid.getValue();		
		Image image =null;
		File img=null;
		switch(i_sl_v){
		case 0:
			remove_img();
			break;
		case 1:
			img= new File("src/images/Screenshot (1).png");
			img_name.setText(img.getName());			
			image = new Image(img.toURI().toString());
			imgPane.setImage(image);
			break;
		case 2:
			img= new File("src/images/Screenshot (2).png");
			img_name.setText(img.getName());
			image = new Image(img.toURI().toString());
			imgPane.setImage(image);
			break;
		case 3:
			img= new File("src/images/Screenshot (3).png");
			img_name.setText(img.getName());
			image = new Image(img.toURI().toString());
			imgPane.setImage(image);
			break;
		case 4:
			
			img= new File("src/images/Screenshot (4).png");
			img_name.setText(img.getName());
			image = new Image(img.toURI().toString());
			imgPane.setImage(image);
			break;
		case 5:
			img= new File("src/images/Screenshot (5).png");
			img_name.setText(img.getName());
			image = new Image(img.toURI().toString());
			imgPane.setImage(image);
			break;
		}
	}
	public void iconimagebuttonAction(ActionEvent event) {
	  FileChooser filechooser = new FileChooser();
	  iconimage = filechooser.showOpenDialog(mainAnchorpane.getScene().getWindow());
	  System.out.println(iconimage.getName());
	    if (iconimage != null) {
	      String iconimagepath = iconimage.getAbsolutePath();
	      img_name.setText(iconimage.getName());
	      System.out.println(iconimagepath);
	      Image image = new Image(iconimage.toURI().toString());
	      imgPane.setImage(image);
	      imgPane.setCache(true);
	    }
	}
	public void remove_img() {
		img_name.setText(null);
		imgPane.setImage(null);
	}
	public void i_reset() {
		i_sts.setText("");
		i_sts_succ.setText("");
		i_u.setText("");		
		i_e.setText("");
		i_p.setText("");
		i_a.setText("");
		
	}
	public void s_reset() {
		s_ser.setText("");
		s_u.setText("");
		s_e.setText("");
		s_p.setText("");
		s_sts.setText("");
	}
}
