public class PrototypeLocation {

    private int id;
    private String name;

    public PrototypeLocation(int idIn, String nameIn) {
        name = nameIn;
        id = idIn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String id) { this.name = name;}

}
