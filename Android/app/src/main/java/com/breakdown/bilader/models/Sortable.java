package com.breakdown.bilader.models;

public interface Sortable {
    abstract void sortByAlphabet( boolean isAtoZ );

    abstract void sortByPrice( boolean isLowtoHigh );
}
