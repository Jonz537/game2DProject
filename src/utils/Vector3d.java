package utils;

import java.io.Serializable;

public class Vector3d implements Serializable {

    public double x;
    public double y;
    private double z;

    public Vector3d(Vector3d pos) {
        this.x = pos.x;
        this.y = pos.y;
        this.z = pos.z;
    }

    public Vector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3d add(Vector3d addend) {
        return new Vector3d(x + addend.x, y + addend.y, z + addend.z);
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

    public void setY(double y) {
        this.y = y;
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