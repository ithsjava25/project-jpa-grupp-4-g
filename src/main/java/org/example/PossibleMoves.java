package org.example;

public class PossibleMoves {
    private final LocationLink route;
    private final Transport  transport;

    public PossibleMoves(LocationLink route, Transport transport) {
        this.route = route;
        this.transport = transport;
    }

    public Location getFrom(){
        return route.getFromLocation();
    }

    public Location getTo(){
        return route.getToLocation();
    }

    public int getDistance(){
        return route.getDistance();
    }

    public Transport getTransport(){
        return transport;
    }

}
