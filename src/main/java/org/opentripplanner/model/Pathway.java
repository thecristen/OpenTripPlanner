/* This file is based on code copied from project OneBusAway, see the LICENSE file for further information. */
package org.opentripplanner.model;

public final class Pathway extends IdentityBean<FeedScopedId> {

    private static final long serialVersionUID = -2404871423254094109L;

    private static final int MISSING_VALUE = -999;

    private static final float WALK_SPEED_FEET_SECONDS = 4.59f;

    private static final float WHEELCHAIR_SPEED_FEET_SECONDS = 3.5481f;

    private static final float STAIRS_UP_SPEED = 1.6404f;

    private static final float STAIRS_DOWN_SPEED = 2.0013f;

    private FeedScopedId id;

    private int pathwayMode;

    private Stop fromStop;

    private Stop toStop;

    private int traversalTime;

    private int wheelchairTraversalTime = MISSING_VALUE;

    private float length = MISSING_VALUE;

    private float wheelchairLength = MISSING_VALUE;

    private int stairCount = MISSING_VALUE;

    @Override
    public FeedScopedId getId() {
        return id;
    }

    @Override
    public void setId(FeedScopedId id) {
        this.id = id;
    }

    public void setPathwayMode(int pathwayMode) {
        this.pathwayMode = pathwayMode;
    }

    public int getPathwayMode() {
        return pathwayMode;
    }

    public void setFromStop(Stop fromStop) {
        this.fromStop = fromStop;
    }

    public Stop getFromStop() {
        return fromStop;
    }

    public void setToStop(Stop toStop) {
        this.toStop = toStop;
    }

    public Stop getToStop() {
        return toStop;
    }

    public void setTraversalTime(int traversalTime) {
        this.traversalTime = traversalTime;
    }

    public int calculateTraversalTime() {
        if (traversalTime != MISSING_VALUE) return traversalTime;
        else if (stairCount != MISSING_VALUE) return (int)(stairCount / (stairCount > 0 ? STAIRS_UP_SPEED : STAIRS_DOWN_SPEED));
        else if (length > 0) return (int)(length / WALK_SPEED_FEET_SECONDS);
        else return MISSING_VALUE;
    }

    public void setWheelchairTraversalTime(int wheelchairTraversalTime) {
        this.wheelchairTraversalTime = wheelchairTraversalTime;
    }

    public int calculateWheelchairTraversalTime() {
        if (wheelchairTraversalTime != MISSING_VALUE) return wheelchairTraversalTime;
        else if (wheelchairLength > 0) return (int)(wheelchairLength / WHEELCHAIR_SPEED_FEET_SECONDS);
        else return MISSING_VALUE;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getWheelchairLength() {
        return wheelchairLength;
    }

    public void setWheelchairLength(float wheelchairLength) {
        this.wheelchairLength = wheelchairLength;
    }

    public int getStairCount() {
        return stairCount;
    }

    public void setStairCount(int stairCount) {
        this.stairCount = stairCount;
    }

    public boolean isWheelchairTraversalTimeSet() {
        return wheelchairTraversalTime != MISSING_VALUE;
    }

    public boolean isTraversalTimeSet() {
        return traversalTime != MISSING_VALUE;
    }

    public void clearWheelchairTraversalTime() {
        this.wheelchairTraversalTime = MISSING_VALUE;
    }

    public boolean isAccessible() {
        if (stairCount == MISSING_VALUE) return wheelchairTraversalTime > 0;
        else return stairCount == 0;
    }


    @Override
    public String toString() {
        return "<Pathway " + this.id + ">";
    }
}
