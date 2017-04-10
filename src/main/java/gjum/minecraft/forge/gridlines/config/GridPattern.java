package gjum.minecraft.forge.gridlines.config;

public enum GridPattern {
    SQUARE("Square"), HEX_STANDING("Hexagon, standing"), HEX_SITTING("Hexagon, sitting"),;

    public static String[] names = null;

    static {
        names = new String[values().length];
        GridPattern[] values = values();
        for (int i = 0; i < values.length; i++) {
            names[i] = values[i].name;
        }
    }

    public static GridPattern fromName(String name) {
        for (GridPattern gridPattern : values()) {
            if (gridPattern.name.equals(name)) return gridPattern;
        }
        return null;
    }

    public final String name;

    GridPattern(String name) {
        this.name = name;
    }
}
