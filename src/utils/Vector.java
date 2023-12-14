package utils;

import java.io.Serial;
import java.io.Serializable;

public class Vector implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private double x;
    private double y;
    private double z;

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector(Vector pos) {
        this.x = pos.x;
        this.y = pos.y;
        this.z = pos.z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }


    public double getZ() {
        return z;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void addX(double value) {
        this.x += value;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void addY(double value) {
        this.y += value;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getNorma() {
        return Math.sqrt((x * x) + (y * y) + (z * z));
    }

    @Override
    public String toString() {
        return "Vector3d{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}