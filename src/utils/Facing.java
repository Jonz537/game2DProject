package utils;

public enum Facing {
    RIGHT, LEFT;

    public int signDirection() {
        return (this.equals(RIGHT)) ? 1 : -1;
    }


}
