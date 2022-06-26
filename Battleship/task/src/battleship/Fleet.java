package battleship;

import battleship.vessels.*;

import java.util.ArrayList;
import java.util.List;

public class Fleet {
    public List<Ship> draftFleet = List.of(new AircraftCarrier(), new Battleship(), new Submarine(), new Cruiser(), new Destroyer());
    public List<Ship> fleet = new ArrayList<>();
}
