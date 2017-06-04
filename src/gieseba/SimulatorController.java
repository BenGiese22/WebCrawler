/*
 * CS2852
 * Spring 2017
 * Lab 9 - DNS Server
 * Ben Giese
 * 5/11/2017
*/

package gieseba;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Stack;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.swing.*;

/**
 * SimualatorController for the Simulator Application with corresponding simulator.fxml
 *
 * @author gieseba
 */
public class SimulatorController implements Initializable {

    private DNS dns = null;

    private Stack<String> undo = new Stack<>();
    private Stack<String> redo = new Stack<>();

    public Button startButton;
    public Button stopButton;
    public Button updateButton;
    public TextField domainTextField;
    public TextField ipTextField;
    public Button addButton;
    public Button deleteButton;
    public Button undoButton;
    public Button redoButton;
    public Button exitButton;

    /**
     * Initializes the controller class. Disables all buttons besides "start" and "exit"
     */
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        stopButton.setDisable(true);
        updateButton.setDisable(true);
        addButton.setDisable(true);
        deleteButton.setDisable(true);
        addButton.setDisable(true);
        deleteButton.setDisable(true);
        undoButton.setDisable(true);
        redoButton.setDisable(true);
        domainTextField.setDisable(true);
        ipTextField.setDisable(true);
    }

    /**
     * Enables all buttons for functional use. Creates a new DNS and starts the DNS.
     *
     * @param event
     */
    public void startButton(ActionEvent event) {
        stopButton.setDisable(false);
        updateButton.setDisable(false);
        addButton.setDisable(false);
        deleteButton.setDisable(false);
        addButton.setDisable(false);
        deleteButton.setDisable(false);
        undoButton.setDisable(false);
        redoButton.setDisable(false);

        startButton.setDisable(true);
        domainTextField.setDisable(false);
        ipTextField.setDisable(false);


        dns = new DNS("dnsentries.txt");
        dns.start();
    }

    /**
     * Stops the DNS service, and disables all buttons and enables "start" button
     *
     * @param event
     */
    public void stopButton(ActionEvent event) {
        try {
            dns.stop();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing out DNS file");
        }
        stopButton.setDisable(true);
        updateButton.setDisable(true);
        addButton.setDisable(true);
        deleteButton.setDisable(true);
        addButton.setDisable(true);
        deleteButton.setDisable(true);
        undoButton.setDisable(true);
        redoButton.setDisable(true);
        domainTextField.setDisable(true);
        ipTextField.setDisable(true);

        startButton.setDisable(false);
    }

    /**
     * Reads "updates.txt" file and adds these commands to HashMap
     *
     * @param event
     */
    public void updateButton(ActionEvent event) {
        Scanner in = null;
        try {
            in = new Scanner(new File("updates.txt"));
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error reading updates.txt");
        }
        while (in.hasNextLine()) {
            String line = in.nextLine().replaceAll("\\t", " ").replaceAll("  ", " ");
            if (line.length() != 0) {
                dns.update(line);
            }
        }
    }

    /**
     * Handles if enter is pressed in TextField. If valid DomainName is entered update ipTextField to the
     * DomainName's corresponding IPAddress, otherwise show "NOT FOUND"
     *
     * @param event
     */
    public void domainTextField(ActionEvent event) {
        if (!validDomainName(domainTextField.getText())) {
            JOptionPane.showMessageDialog(null, "Not a valid Domain Name, try again");
        } else {
            DomainName tempDomainName = new DomainName(domainTextField.getText());
            IPAddress tempIPAddress = dns.lookup(tempDomainName);
            if (tempIPAddress == null) {
                ipTextField.setText("NOT FOUND");
            } else {
                ipTextField.setText(tempIPAddress.toString());
            }
        }
    }

    /**
     * Adds the TextField DomainName and IPAddress to the HashMap
     *
     * @param event
     */
    public void addButton(ActionEvent event) {
        String command = "ADD " + ipTextField.getText() + " " + domainTextField.getText();
        undo.push(command);
        dns.update(command);
    }

    /**
     * Deletes the TextField DomainName and IPAddress from the HashMap
     *
     * @param event
     */
    public void deleteButton(ActionEvent event) {
        String command = "DEL " + ipTextField.getText() + " " + domainTextField.getText();
        dns.update(command);
    }

    /**
     * Undoes the update to the DNS
     *
     * @param event
     */
    public void undoButton(ActionEvent event) {
        String temp = "DEL" + undo.pop().substring(4);
        redo.push(temp);
        dns.update(temp);
    }

    /**
     * Redoes the update to the DNS
     *
     * @param event
     */
    public void redoButton(ActionEvent event) {
        String temp = "ADD" + redo.pop().substring(4);
        dns.update(temp);
    }

    /**
     * Exits the application
     *
     * @param event
     */
    public void exitButton(ActionEvent event) {
        Platform.exit();
    }

    /**
     * Checks if the input String is a valid domainName
     *
     * @param domain
     * @return boolean
     */
    public boolean validDomainName(String domain) {
        boolean valid = true;
        if (domain.length() > 253) {
            valid = false;
        } else {
            if (domain.charAt(0) == '-' || domain.charAt(0) == '.') {
                valid = false;
            } else {
                for (int i = 0; i < domain.length(); i++) {
                    char c = domain.charAt(i);
                    if (c == '.') {
                        if (i + 1 == domain.length() - 1 && domain.charAt(i + 1) == '.') {
                            valid = false;
                        }
                    } else if (!Character.isLetter(c) && !Character.isDigit(c)) {
                        if (c != '-') {
                            valid = false;
                        }
                    }
                }
            }
        }
        return valid;
    }

}
