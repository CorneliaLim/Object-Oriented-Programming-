public enum RoomType {
    SMALL("Small", 4),
    LARGE("Large", 8);

    private final String displayName;
    private final int defaultCapacity;

    RoomType(String displayName, int defaultCapacity) {
        this.displayName = displayName;
        this.defaultCapacity = defaultCapacity;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getDefaultCapacity() {
        return defaultCapacity;
    }

    public static RoomType fromString(String text) {
        for (RoomType type : RoomType.values()) {
            if (type.displayName.equalsIgnoreCase(text)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid room type: " + text);
    }
} 