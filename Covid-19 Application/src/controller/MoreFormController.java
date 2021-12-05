package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class MoreFormController implements Initializable {
    public Hyperlink healthLink;
    public Hyperlink ambulanceLink;
    public Hyperlink policeLink;
    public Hyperlink googleLink;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void openGoogle(ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("http://www.google.com"));
    }

    public void openHealthMinistry(ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("http://www.health.gov.lk/moh_final/english/"));
    }

    public void openAmbulance(ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://internationalambulance.lk/"));
    }

    public void openPolice(ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://www.police.lk/"));

    }
}
