package org.opentripplanner.routing.edgetype;

import org.opentripplanner.common.geometry.GeometryUtils;
import org.opentripplanner.routing.core.State;
import org.opentripplanner.routing.core.StateEditor;
import org.opentripplanner.routing.graph.Edge;
import org.opentripplanner.routing.graph.Vertex;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.LineString;
import org.opentripplanner.routing.core.TraverseMode;
import java.util.Locale;

/**
 * A walking pathway as described in GTFS
 */
public class PathwayEdge extends Edge {

    private int traversalTime = -1;

    private int wheelchairTraversalTime = -1;

    private double distance = -1;

    public PathwayEdge(Vertex fromv, Vertex tov) {
        super(fromv, tov);
    }

    public void setTraversalTime(int traversalTime) {
        this.traversalTime = traversalTime;
    }

    public void setWheelchairTraversalTime(int wheelchairTraversalTime) {
        this.wheelchairTraversalTime = wheelchairTraversalTime;
    }

    private static final long serialVersionUID = -3311099256178798981L;

    public String getDirection() {
        return null;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public TraverseMode getMode() {
       return TraverseMode.WALK;
    }

    public LineString getGeometry() {
        Coordinate[] coordinates = new Coordinate[] { getFromVertex().getCoordinate(),
                getToVertex().getCoordinate() };
        return GeometryUtils.getGeometryFactory().createLineString(coordinates);
    }

    public String getName() {
        return "pathway";
    }

    @Override
    public String getName(Locale locale) {
        //TODO: localize
        return this.getName();
    }

    public State traverse(State s0) {
        int time;
        if (s0.getOptions().wheelchairAccessible) {
            if (wheelchairTraversalTime < 0) {
                return null;
            }
            time = wheelchairTraversalTime;
        }
        else
        {
            // some pathways (e.g. elevators) only have accessible traversal time known
            if (traversalTime < 0) {
                return null;
            }
            time = traversalTime;
        }
        StateEditor s1 = s0.edit(this);
        s1.incrementTimeInSeconds(time);
        s1.incrementWeight(time);
        s1.setBackMode(getMode());
        return s1.makeState();
    }
}
