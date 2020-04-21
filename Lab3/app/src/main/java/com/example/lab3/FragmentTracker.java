package com.example.lab3;

public interface FragmentTracker {

    void fragmentVisible(String s);
    void goNext();
    void goBack();
    void saveNameAndLastName(String name, String last);
    void saveCityAndZip(String city, String zip);
    void saveLanguage(String lang);
    void finished();
}
