package edu.wpi.cs3733.d19.teamL.Map.MapLocations;

import me.xdrop.fuzzywuzzy.FuzzySearch;

import java.util.ArrayList;
import java.util.HashMap;

public class AutoCompleteList extends ArrayList {
    public void takeTopTen(HashMap<String, Location> lookup, String test) {
        this.clear();
        for (Location l : lookup.values()) {
            int score = FuzzySearch.ratio(test, l.getLongName());
            l.setNameSimilarityScore(score);
            insert(l);

        }
    }

    public void insert(Location l) {
        if (this.size() < 10 && l.getNameSimilarityScore() > 5) {
            this.add(l);
            return;
        }
        else {
            for (int i = 0; i < this.size(); i ++) {
                if (l.getNameSimilarityScore() > ((Location) this.get(i)).getNameSimilarityScore() && l.getNameSimilarityScore() > 5) {
                    this.add(i, l);
                    this.remove(10);
                    return;
                }
            }
        }
    }
}
