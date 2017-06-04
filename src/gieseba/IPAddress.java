/*
 * CS2852
 * Spring 2017
 * Lab 9 - DNS Server
 * Ben Giese
 * 5/11/2017
*/

package gieseba;

/**
 * IPAddress class that contains a valid IPAddress
 */
public class IPAddress {

    private String address; //not sure if needed
    private String[] addressSplit;

    /**
     * IPAddress Constructor containing check if it is valid IP
     *
     * @param newAddress
     */
    public IPAddress(String newAddress) {
        if (validIPAddressCheck(newAddress)) {
            address = newAddress;
            addressSplit = address.split("\\.");
        } else {
            throw new IllegalArgumentException("Not a valid IPAddress");
        }
    }

    /**
     * Returns String of the IPAddress
     *
     * @return String
     */
    @Override
    public String toString() {
        return addressSplit[0] + "." + addressSplit[1] + "." + addressSplit[2] + "." + addressSplit[3];
    }

    /**
     * Returns true if the two IPAddresses have equal Strings
     *
     * @param address
     * @return boolean
     */
    public boolean equals(Object address) {
        return address.equals(address.toString());
    }

    /**
     * Checks if the inputIPAddress is a valid IP
     *
     * @param address
     * @return boolean
     */
    public boolean validIPAddressCheck(String address) {
        boolean valid = true;
        String[] addressSplit = address.split("\\.");
        if (addressSplit.length == 4) {
            for (String part : addressSplit) {
                int temp = Integer.parseInt(part);
                if (temp > 255 || temp < 0) {
                    valid = false;
                }
            }
        } else {
            valid = false;
        }
        return valid;
    }
}