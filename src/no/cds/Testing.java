package no.cds;

import java.util.ArrayList;
import java.util.Arrays;

public class Testing {
    public static void main(String[] args) {
        ArrayList<String> reqsList = new ArrayList<>(Arrays.asList("B A G".split(" ")));
        ArrayList<String> qualList = new ArrayList<>(Arrays.asList("B A F".split(" ")));
        reqsList.retainAll(qualList);
        System.out.println((double) reqsList.size() / (double) qualList.size() * 100.0);
    }
}
