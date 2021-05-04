package com.breakdown.bilader.models;

/**
 * An interface to sort things by their alphabetic order or their price.
 * @author breakDown
 * @version  114.04.2021
 */

public interface Sortable {
    abstract void sortByAlphabet( boolean isAtoZ );

    abstract void sortByPrice( boolean isLowtoHigh );
}
