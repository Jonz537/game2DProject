package utils;

public class Vector3d {

    // TODO fix this public mess
    public double x;
    public double y;
    public double z;

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

    public double distance(Vector3d vector3d) {
        return Math.sqrt(Math.pow((x + vector3d.x), 2) + Math.pow((y + vector3d.y), 2) + Math.pow((z + vector3d.z), 2));
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