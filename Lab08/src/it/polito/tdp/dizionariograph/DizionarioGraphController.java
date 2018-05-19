package it.polito.tdp.dizionariograph;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.dizionariograph.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DizionarioGraphController {
	
	private Model m;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtlettere;

    @FXML
    private TextField txtWord;

    @FXML
    private Button btnGrafo;

    @FXML
    private Button btnVicini;

    @FXML
    private Button btnGradMax;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button btnReset;

    @FXML
    void doGrafo(ActionEvent event) {
    	
    	try {
    	  	    	
    		List <String> paroleTrovate = m.createGraph(Integer.parseInt(txtlettere.getText()));
	    	
	    		if (paroleTrovate != null)  //parola presente nel dizionario
	    			
					txtResult.setText("Trovate " + paroleTrovate.size() + " parole di lunghezza " + Integer.parseInt(txtlettere.getText()));
				else 
					
					txtResult.setText("Trovate 0 parole di lunghezza: " + Integer.parseInt(txtlettere.getText()));
	    	    	
	    } catch (NumberFormatException nfe) {
	    	
			txtResult.setText("Inserire il numero di lettere!");
			
		} catch (RuntimeException re) {
			
			txtResult.setText(re.getMessage());
			
		}
    }

    @FXML
    void doReset(ActionEvent event) {
    	
    	txtlettere.clear();
    	txtWord.clear();
    	txtResult.clear();

    }

    @FXML
    void findVicini(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	try {
    		
    		if( txtWord.getText() == null ) 
	    		
	    		txtResult.setText("inserire una parola");
	
	    	if( !txtWord.getText().toLowerCase().matches("[a-z]*") ) 
	    		
	    		txtResult.setText("inserire solo caratteri alfabetici");
	    			    	
	    	List<String> paroleVicine = m.displayNeighbours(txtWord.getText().toLowerCase());
	    	
	    	if(paroleVicine != null) { //se parola non presente in dizionario 
	    	   	 	
	    		for (String vicina : paroleVicine )
	    		
	    			txtResult.appendText(vicina+"\n");
	    	}
	    		
	    	else 
    		
	    		txtResult.setText("parola non trovata nel dizionario");
    	
    	} catch (RuntimeException re) {
    		
			txtResult.setText(re.getMessage());

    	}
    }

    @FXML
    void trovaGradMax(ActionEvent event) {
    	
    	try {
			txtResult.setText(m.findMaxDegree());

		} catch (RuntimeException re) {
			txtResult.setText(re.getMessage());
		}

    }

    @FXML
    void initialize() {
        assert txtlettere != null : "fx:id=\"txtlettere\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert txtWord != null : "fx:id=\"txtWord\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert btnGrafo != null : "fx:id=\"btnGrafo\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert btnVicini != null : "fx:id=\"btnVicini\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert btnGradMax != null : "fx:id=\"btnGradMax\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";

    }

	public void setModel(Model model) {

		m = model;
	}
}
