package pathfinding;

public enum City {
    LOERRACH(47.616, 7.668), WEIL(47.593, 7.623),
    BINZEN(47.631, 7.624), EFRINGEN_KIRCHEN(47.655, 7.563),
    STEINEN(47.644, 7.739), MAULBURG(47.643, 7.780),
    SCHOPFHEIM(47.650, 7.825), HAUSEN(47.680, 7.841),
    ZELL(47.707, 7.852), WITTLINGEN(47.656, 7.648),
    KANDERN(47.713, 7.662), BAD_BELLINGEN(47.731, 7.557),
    SCHLIENGEN(47.755, 7.579), AUGGEN(47.787, 7.593),
    MUELLHEIM(47.804, 7.629), MARZELL(47.771, 7.726),
    TEGERNAU(47.719, 7.795), SCHOENAU(47.785, 7.893),
    MUENSTERTAL(47.854, 7.784), STAUFEN(47.882, 7.729),
    HEITERSHEIM(47.874, 7.655);

    final double latitude;
    final double longitude;

    City(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}