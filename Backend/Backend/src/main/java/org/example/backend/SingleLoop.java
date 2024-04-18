package org.example.backend;

import java.util.List;

public class SingleLoop {

    private int loopID;
    private double loopGain;
    private List<Integer> loopPath;

    public SingleLoop(int id, double gian, List<Integer> loopPath) {
        this.loopPath = loopPath;
        this.loopGain = gian;
        this.loopID = id;
    }

    public List<Integer> getLoopPath() {
        return loopPath;
    }

    public void setLoopPath(List<Integer> loopPath) {
        this.loopPath = loopPath;
    }

    public double getLoopGain() {
        return loopGain;
    }

    public void setLoopGain(double loopGain) {
        this.loopGain = loopGain;
    }

    public int getLoopID() {
        return loopID;
    }

    public void setLoopID(int loopID) {
        this.loopID = loopID;
    }
}
