/*
 * CS2852
 * Spring 2017
 * Lab 9 - DNS Server
 * Ben Giese
 * 5/11/2017
*/

package gieseba;

/**
 * DomainName class that contains a valid DomainName
 */
public class DomainName {

    private String domain;

    /**
     * Constructor for DomainName with check if valid
     *
     * @param domain
     */
    public DomainName(String domain) {
        if (validDomainName(domain)) {
            this.domain = domain;
        } else {
            throw new IllegalArgumentException("Not a valid DomainName");
        }
    }

    /**
     * Hash Code String Override
     *
     * @return int
     */
    @Override
    public int hashCode() {
        return domain.hashCode();
    }

    /**
     * Returns the domainName String
     *
     * @return String
     */
    private String domainName() {
        return domain;
    }

    /**
     * Overridden toString that returns String domain
     *
     * @return String
     */
    @Override
    public String toString() {
        return domain;
    }

    /**
     * Returns true if the two domainNames have equal Strings
     *
     * @param domain
     * @return boolean
     */
    public boolean equals(Object domain) {
        return this.domain.equals(((DomainName) domain).domainName());
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
