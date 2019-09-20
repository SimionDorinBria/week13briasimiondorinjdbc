package accomodationfairrelation;

public class AccomodationFairRelation {
    public int id;
    public int id_accomodation;
    public int id_room_fair;

    public AccomodationFairRelation(int id, int id_accomodation, int id_room_fair) {
        this.id = id;
        this.id_accomodation = id_accomodation;
        this.id_room_fair = id_room_fair;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_accomodation() {
        return id_accomodation;
    }

    public void setId_accomodation(int id_accomodation) {
        this.id_accomodation = id_accomodation;
    }

    public int getId_room_fair() {
        return id_room_fair;
    }

    public void setId_room_fair(int id_room_fair) {
        this.id_room_fair = id_room_fair;
    }
}