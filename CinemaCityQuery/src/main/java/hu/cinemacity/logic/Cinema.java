package hu.cinemacity.logic;

/**
 *
 * @author Kircsi
 */
public enum Cinema {

    ALBA("Alba", 1010201),
    ALLEE("Allee", 1010202),
    ARÉNA("Aréna", 1010203),
    BALATON("Balaton", 1010204),
    DEBRECEN("Debrecen", 1010205),
    DUNA("Duna", 1010206),
    GYŐR("Győr", 1010207),
    KAPOSVÁR("Kaposvár", 1010208),
    MISKOLC("Miskolc", 1010209),
    NYÍREGYHÁZA("Nyíregyháza", 1010210),
    PÉCS("Pécs", 1010211),
    SAVARIA("Savaria", 1010212),
    SOPRON("Sopron", 1010213),
    SZEGED("Szeged", 1010214),
    SZOLNOK("Szolnok", 1010215),
    ZALA("Zala", 1010216),
    WESTEND("WestEnd", 1010217),
    MOM("MOM", 1010218),
    MAMMUT("Mammut", 1010219),
    CAMPONA("Campona", 1010220);

    private Cinema(String location, int id) {
        this.location = location;
        this.id = id;
    }

    @Override
    public String toString() {
        return location;
    }
    
    public String getLocation() {
        return location;
    }
    
    public int getID() {
        return id;
    }
    
    private final String location;
    private final int id;
    
}
