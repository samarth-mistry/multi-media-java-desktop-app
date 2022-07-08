package pack1;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class chart_controller {
	@FXML private PieChart p1;
	@FXML private Label pc_title;
	@FXML private Label pc_detail;
	@FXML private Label nm_dt_error;
	@FXML private TextField title;
	@FXML private TextField data;
	@FXML private TextField name;
	@FXML private TextArea detail;
	//--------------------------------LINE CHART----------------------------
	@FXML private LineChart l1;
	@FXML private Label lc_title;
	@FXML private Label lc_detail;
	@FXML private Label l_nm_dt_error;
	@FXML private CategoryAxis xxis;
	@FXML private NumberAxis yxis;
	@FXML private TextField l_title;
	@FXML private TextField l_data;
	@FXML private TextField l_name;
	@FXML private TextField l_xaxis;
	@FXML private TextField l_yaxis;
	@FXML private TextArea l_detail;
	ArrayList<Integer> Line_data = new ArrayList<Integer>();
	@FXML private TextField series_name;
	//--------------------------------------------BAR CHART-----------------------------------------
	@FXML private BarChart b1;
	@FXML private TableView b_table;
	@FXML private Label bc_title;
	@FXML private Label bc_detail;
	@FXML private Label b_nm_dt_error;
	@FXML private CategoryAxis bxxis;
	@FXML private NumberAxis byxis;
	@FXML private TextField b_title;
	@FXML private TextField b_data;
	@FXML private TextField b_name;
	@FXML private TextField b_xaxis;
	@FXML private TextField b_yaxis;
	@FXML private TextArea b_detail;
	@FXML private TextField b_series_name;
	private ObservableList<ObservableList> b_table_data;
	//-----------------------------------------STACKER AREA CHART-----------------------------------
	@FXML private StackedAreaChart sac;	
	@FXML private Label sac_title;
	@FXML private Label sac_detail;
	@FXML private NumberAxis sacxxis;
	@FXML private NumberAxis sacyxis;
	//--------------------------------------------Pie chart-----------------------------------------
	private ObservableList<PieChart.Data> fetchdata;
	public void load_data() {		
		System.out.println("Load clicked");
		      
		if(title.getText().isEmpty()) {pc_title.setText("PieChart 01");}else {pc_title.setText(title.getText());}
		//if(detail.getText().isEmpty()) {pc_detail.setText("PieChart 01's Detail is showned here\n if you like to watch more then");}else {pc_detail.setText(detail.getText());}
		
		PieChart.Data data[] = new PieChart.Data[5];
		String status[] = {"Correct Answer", "Wrong Answer", "Compilation Error", "Runtime Error","Others" };
		int values[] = {20, 30, 10, 4, 2};
		   
        for (int i = 0; i < 5; i++) {
            data[i] = new PieChart.Data(status[i], values[i]);
            System.out.println(data[i]);
        }
        pc_title.setText("PieChart 01");
        pc_detail.setText("PieChart 01's Detail is showned here\n if you like to watch more then");
        p1.setData(FXCollections.observableArrayList(data));
	}
	public void add(){
		fetchdata = FXCollections.observableArrayList();
		if(name.getText().isEmpty() && data.getText().isEmpty()) {
			nm_dt_error.setText("Please enter name and data");
		}else {			
			try {
			 	Connection conn= null;
			 	conn =  xampp_connector.getConnection();
		        PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO piechart_data(name,data) VALUES(?,?)");
		        preparedStatement.setString(1,name.getText() );
		        preparedStatement.setString(2, data.getText());	        

		        System.out.println(preparedStatement);
		        preparedStatement.executeUpdate();		    		       
		        clear_piechart();
		        if(title.getText().isEmpty()) {pc_title.setText("PieChart 01");}else {pc_title.setText(title.getText());}
		        //----------------------GET BACK-------------------------		       
		        preparedStatement = conn.prepareStatement("SELECT name,data from piechart_data");
		        System.out.println(preparedStatement);		        
		        ResultSet rs = preparedStatement.executeQuery();
		        System.out.println("NAME         KILLS");
		        System.out.println("__________________________________");
		        while(rs.next()) {
		        	//ObservableList<String> row = FXCollections.observableArrayList();
	                //row.add(rs.getString("name"));
		        	System.out.println(rs.getString("name")+" | "+rs.getInt("data"));	
		            fetchdata.add(new PieChart.Data(rs.getString("name"), Double.parseDouble(rs.getString("data"))));
		        }
		        p1.setData(fetchdata);
		        conn.close();
		    } catch (SQLException e) {
		    	e.printStackTrace();	    	
		    }			
		}				
	}
	public void clear_piechart() {
		p1.getData().clear();
		pc_title.setText("");
		pc_detail.setText("");
		data.setText("");
		name.setText("");
		title.setText("");
		nm_dt_error.setText("");
	}
	public void clear_linechart() {
		l1.getData().clear();
		lc_title.setText("");
		lc_detail.setText("");
		l_data.setText("");
		l_name.setText("");
		l_title.setText("");
		l_xaxis.setText("");
		l_yaxis.setText("");
		l_nm_dt_error.setText("");
	}
	public void clear_barchart() {
		b1.getData().clear();
		bc_title.setText("");
		bc_detail.setText("");
		b_data.setText("");
		b_name.setText("");
		b_title.setText("");
		b_xaxis.setText("");
		b_yaxis.setText("");
		b_nm_dt_error.setText("");
	}
	//-------------------------------------------LINE CHART-----------------------------------------
	public void l_load_data() {
		System.out.println("ALL well");
		xxis.setLabel("Years");
		yxis.setLabel("DATA");
		XYChart.Series series = new XYChart.Series(); 
		series.setName("No of schools in an year"); 
		 
		series.getData().add(new XYChart.Data("1", 15)); 
		series.getData().add(new XYChart.Data("2", 30)); 
		series.getData().add(new XYChart.Data("3", 60)); 
		series.getData().add(new XYChart.Data("4", 120)); 			
        l1.getData().add(series);		
	}
	public void l_add() {
//		System.out.println(l_title.getText());
//		System.out.println(l_xaxis.getText());
//		System.out.println(l_yaxis.getText());
//		if(l_title.getText().isEmpty()) {lc_title.setText("Default Title");}else {lc_title.setText(l_title.getText());}
//		if(l_xaxis.getText().isEmpty()) {xxis.setLabel("Default Label");}else {xxis.setLabel(l_xaxis.getText());}
//		if(l_yaxis.getText().isEmpty()) {yxis.setLabel("Default Label");}else {yxis.setLabel(l_yaxis.getText());} 		
//		//if(l_name.getText().isEmpty() && l_data.getText().isEmpty()) {
//		int i=0;
//		if(i==1) {
//			l_nm_dt_error.setText("Please enter name and data");
//		}else {			
//			try {
//			 	Connection conn= null;
//			 	conn =  xampp_connector.getConnection();
//		        /*PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO piechart_data(name,data) VALUES(?,?)");
//		        preparedStatement.setString(1,l_name.getText() );
//		        preparedStatement.setString(2, l_data.getText());	        
//
//		        System.out.println(preparedStatement);
//		        preparedStatement.executeUpdate();*/		    		       
//		        clear_linechart();		      
//		        XYChart.Series series = new XYChart.Series(); 
//		        if(series_name.getText().isEmpty()) {series.setName("Data 1");}else {series.setName(series_name.getText());} 
//		        //----------------------GET BACK-------------------------		       
//				PreparedStatement preparedStatement = conn.prepareStatement("SELECT name,data from piechart_data");
//		        System.out.println(preparedStatement);		        
//		        ResultSet rs = preparedStatement.executeQuery();
//		        System.out.println("NAME         KILLS");
//		        System.out.println("__________________________________");
//		        while(rs.next()) {
//		        	series.getData().add(new XYChart.Data(rs.getString("name"), Double.parseDouble(rs.getString("data"))));
//		        	System.out.println(rs.getString("name")+" | "+rs.getInt("data"));	
//		        }
//		        l1.getData().addAll(series);
//		        conn.close();
//		    } catch (SQLException e) {
//		    	e.printStackTrace();	    	
//		    }
//			XYChart.Series series = new XYChart.Series();
//			series.getData().add(new XYChart.Data(, Double.parseDouble(rs.getString("data"))));
//		}
		/*
		 * int input[] = {Integer.parseInt(l_xaxis.getText()),
		 * Integer.parseInt(l_xaxis.getText())};
		 */
		/*
		 * int input[] = {1,2,2,3}; for (int i = 0; i < input.length; i++)
		 * Line_data.add(new Integer(input[i]));
		 * 
		 * System.out.print(data);
		 */
		Line_data.add(1);
		//xxis.setLabel("Years");
		//yxis.setLabel("DATA");
		@SuppressWarnings("rawtypes")
		XYChart.Series series = new XYChart.Series(); 
		series.setName("Seri1");
		
		for (int i = 0; i<Line_data.size() ; i++) {
			series.getData().add(new XYChart.Data(i, Line_data.get(i)));
		}
		series.getData().add(new XYChart.Data(10,13));
		l1.getData().add(series);	
	}
	//--------------------------------------------BAR CHART-----------------------------------------
	public void b_add() {
		System.out.println(b_title.getText());
		System.out.println(b_xaxis.getText());
		System.out.println(b_yaxis.getText());
		if(b_title.getText().isEmpty()) {bc_title.setText("Default Title");}else {bc_title.setText(b_title.getText());}
		if(b_xaxis.getText().isEmpty()) {bxxis.setLabel("Default Label");}else {bxxis.setLabel(b_xaxis.getText());}
		if(b_yaxis.getText().isEmpty()) {byxis.setLabel("Default Label");}else {byxis.setLabel(b_yaxis.getText());} 		
		//if(l_name.getText().isEmpty() && l_data.getText().isEmpty()) {
		int i=0;
		if(i==1) {
			b_nm_dt_error.setText("Please enter name and data");
		}else {			
			try {
			 	Connection conn= null;
			 	conn =  xampp_connector.getConnection();
		        /*PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO piechart_data(name,data) VALUES(?,?)");
		        preparedStatement.setString(1,l_name.getText() );
		        preparedStatement.setString(2, l_data.getText());	        

		        System.out.println(preparedStatement);
		        preparedStatement.executeUpdate();*/		    		       
		        clear_barchart();		      
		        XYChart.Series series = new XYChart.Series(); 
		        if(b_series_name.getText().isEmpty()) {series.setName("Data 1");}else {series.setName(b_series_name.getText());} 
		        //----------------------GET BACK-------------------------		       
				PreparedStatement preparedStatement = conn.prepareStatement("SELECT name,data from piechart_data");
		        System.out.println(preparedStatement);		        
		        ResultSet rs = preparedStatement.executeQuery();
		        System.out.println("NAME         KILLS");
		        System.out.println("__________________________________");
		        while(rs.next()) {
		        	series.getData().add(new XYChart.Data(rs.getString("name"), Double.parseDouble(rs.getString("data"))));
		        	System.out.println(rs.getString("name")+" | "+rs.getInt("data"));	
		        }
		        b1.getData().addAll(series);
		        conn.close();
		    } catch (SQLException e) {
		    	e.printStackTrace();	    	
		    }			
		}
	}
	public void b_load_data() {
		clear_barchart();
		System.out.println("ALL well");
		bxxis.setLabel("Years");
		byxis.setLabel("DATA");
		XYChart.Series series = new XYChart.Series(); 
		series.setName("No of schools in an year"); 
		 
		series.getData().add(new XYChart.Data("1", 15)); 
		series.getData().add(new XYChart.Data("2", 30)); 
		series.getData().add(new XYChart.Data("3", 60)); 
		series.getData().add(new XYChart.Data("4", 50)); 			
        b1.getData().add(series);		
	}
	public void b_table() {
		b_table_data = FXCollections.observableArrayList();
		b_table.setVisible(true);
		try {
        	Connection conn=null;
			conn =  xampp_connector.getConnection();
			Statement st = conn.createStatement();
			String recQuery="SELECT name,data from piechart_data";
			ResultSet rs = st.executeQuery(recQuery);
			for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;                
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                        return new SimpleStringProperty(param.getValue().get(j).toString());                        
                    }                    
                });

                b_table.getColumns().addAll(col); 
                System.out.println("Column ["+i+"] ");
            }
            while (rs.next()) {
            	ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added "+row );
                b_table_data.add(row);
            }
            //System.out.println(data);
            b_table.setItems(b_table_data);
        }catch(Exception e) {
        	e.printStackTrace();
        }
	}
	//-----------------------------------------STACKER AREA CHART-----------------------------------
	public void sac_load_data() {
		StackedAreaChart stack = new StackedAreaChart(sacxxis, sacyxis);  
		XYChart.Series series = new XYChart.Series(); 
		series.setName("No of schools in an year"); 
		 
		series.getData().add(new XYChart.Data(1, 15)); 
		series.getData().add(new XYChart.Data(2, 30)); 
		series.getData().add(new XYChart.Data(3, 60)); 
		series.getData().add(new XYChart.Data(4, 50)); 			
        sac.getData().add(series);		
	}
}

