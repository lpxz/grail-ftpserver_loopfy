package org.apache.ftpserver.ipfilter;

/**
 * Defines various types of IP Filters.
 * 
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 * 
 */
public enum IpFilterType {

    /**
	 * filter type that allows a set of predefined IP addresses, also known as a
	 * white list.
	 */
    ALLOW, /**
	 * filter type that blocks a set of predefined IP addresses, also known as a
	 * black list.
	 */
    DENY;

    /**
	 * Parses the given string into its equivalent enum.
	 * 
	 * @param value
	 *            the string value to parse.
	 * @return the equivalent enum
	 */
    public static IpFilterType parse(String value) {
        for (IpFilterType type : values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid IpFilterType: " + value);
    }
}
