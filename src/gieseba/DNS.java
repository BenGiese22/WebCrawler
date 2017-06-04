/*
 * CS2852
 * Spring 2017
 * Lab 9 - DNS Server
 * Ben Giese
 * 5/11/2017
*/

package gieseba;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * DNS handles all starting/stopping of the service.
 * Looks up DomainNames, updates the DNS HashMap, and writes the HashMap to .txt file
 */
public class DNS {

    Map<DomainName, IPAddress> domainIPMap = new HashMap<>();

    private Scanner in = null;
    private boolean started = false;
    private boolean stopped = false;
    private String dnsentriesFile = "dnsentries.txt";

    /**
     * Constructor for DNS, fileName input is the fileName of the file that will be output when stopped.
     *
     * @param filename
     */
    public DNS(String filename) {
        try {
            in = new Scanner(new File(dnsentriesFile));
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Could not create DNS",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Starts the DNS by reading in the names
     * Returns true if it was already started or if it was started successfully.
     *
     * @return boolean
     */
    public boolean start() {
        boolean check = false;
        if (started == true) {
            check = true;
        } else {
            while (in.hasNextLine()) {
                String line = in.nextLine();
                if (line.length() != 0) {
                    String[] split = line.replaceAll("\t", "").replaceAll("  ", " ").split(" ");
                    ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(split));
                    int i = 0;
                    while (i < arrayList.size()) {
                        if (arrayList.get(i).equals("")) {
                            arrayList.remove(i);
                        } else {
                            i++;
                        }
                    }
                    DomainName tempDomain = null;
                    IPAddress tempIPAddress = null;
                    try {
                        tempDomain = new DomainName(arrayList.get(1));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Invalid Domain Name " + arrayList.get(1));
                    }

                    try {
                        tempIPAddress = new IPAddress(arrayList.get(0));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Invalid IPAddress " + arrayList.get(0));
                    }

                    domainIPMap.put(tempDomain, tempIPAddress);
                }
            }
            started = true;
            check = true;
        }
        return check;
    }

    /**
     * Stops the DNS, writes out all HashMap entries
     *
     * @return boolean
     * @throws IOException
     */
    public boolean stop() throws IOException {
        boolean check = false;
        if (stopped) {
            check = true;
        } else {
            check = writeToFile();
        }
        stopped = true;
        return check;
    }

    /**
     * Finds the IPAddress that corresponds to its DomainName
     *
     * @param domain
     * @return IPAddress
     */
    public IPAddress lookup(DomainName domain) {
        IPAddress returnIP = null;
        if (!started) {
            returnIP = null;
        } else {
            if (domainIPMap.containsKey(domain)) {
                returnIP = domainIPMap.get(domain);
            }
        }
        return returnIP;
    }

    /**
     * Updates the HashMap using the input command
     * Parameter command contains ADD or DEL, IPAddress, DomainName
     *
     * @param command
     * @return IPAddress
     */
    public IPAddress update(String command) {
        IPAddress returnIP = null;
        if (!started) {
            returnIP = null;
        }
        String[] split = command.replaceAll("\\t", "").replaceAll("  ", " ").split(" ");
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(split));
        int i = 0;
        while (i < arrayList.size()) {
            if (arrayList.get(i).equals("")) {
                arrayList.remove(i);
            } else {
                i++;
            }
        }
        String addDel = arrayList.get(0);
        String ipAddress = arrayList.get(1);
        String domainName = arrayList.get(2);
        if (!addDel.equals("ADD") && !addDel.equals("DEL")) {
            throw new IllegalArgumentException("Not a valid update command");
        } else if (addDel.equals("ADD")) {
            DomainName newDomain = new DomainName(domainName);
            IPAddress newIP = new IPAddress(ipAddress);
            if (domainIPMap.containsKey(new DomainName(domainName))) {
                IPAddress tempIP = domainIPMap.remove(new DomainName(domainName));
                domainIPMap.put(newDomain, newIP);
                returnIP = tempIP;
            } else {
                domainIPMap.put(newDomain, newIP);
                returnIP = null;
            }
        } else if (addDel.equals("DEL")) {
            if (domainIPMap.containsKey(new DomainName(domainName))) {
                IPAddress tempIP = domainIPMap.get(new DomainName(domainName));
                if (!tempIP.equals(ipAddress)) {
                    throw new InputMismatchException("DomainName exists but the IPAddress does not match.");
                }
                returnIP = domainIPMap.remove(new DomainName(domainName));
            } else {
                returnIP = null;
            }
        }
        return returnIP;
    }

    /**
     * Writes all HashMap entries to the dnsentries.txt file.
     *
     * @return boolean
     * @throws IOException
     */
    public boolean writeToFile() throws IOException {
        FileWriter fileWriter = new FileWriter(new File("dnsentries.txt"));
        Set<Map.Entry<DomainName, IPAddress>> entrySet = domainIPMap.entrySet();
        for (Map.Entry<DomainName, IPAddress> part : entrySet) {
            String temporary = part.getValue().toString() + " " + part.getKey().toString() + "\n";
            fileWriter.write(temporary);

        }
        fileWriter.flush();
        fileWriter.close();
        return true;
    }

    /*
    HashMap is of better use in this situation due to having to add and
    delete entries frequently and the little need for a contains search.
    */
}
