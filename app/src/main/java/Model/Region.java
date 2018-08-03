package Model;

import java.util.ArrayList;
import java.util.List;


public class Region {
    private List<String> regions;
    private List<String> shortNames;
    public Region() {
        regions=new ArrayList<>();
        regions.add("Canada");
        regions.add("US");
        regions.add("India");
        regions.add("Japan");
        regions.add("Russia");

        shortNames=new ArrayList<>();
        shortNames.add("ca");
        shortNames.add("us");
        shortNames.add("in");
        shortNames.add("jp");
        shortNames.add("ru");

    }

    public List<String> getRegions() {
        return regions;
    }

    public String getShortLabel(String selectedR) {
        int index=regions.indexOf(selectedR);
        return shortNames.get(index);
    }
}
