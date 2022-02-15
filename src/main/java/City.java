public enum City {
    LOERRACH(47.616, 7.668, "Lörrach"), WEIL(47.593, 7.623, "Weil am Rhein"),
    BINZEN(47.631, 7.624, "Binzen"), EFRINGEN_KIRCHEN(47.655, 7.563, "Efringen-Kirchen"),
    STEINEN(47.644, 7.739, "Steinen"), MAULBURG(47.643, 7.780, "Maulburg"),
    SCHOPFHEIM(47.650, 7.825, "Schopfheim"), HAUSEN(47.680, 7.841, "Hausen"),
    ZELL(47.707, 7.852, "Zell"), WITTLINGEN(47.656, 7.648, "Wittlingen"),
    KANDERN(47.713, 7.662, "Kandern"), BAD_BELLINGEN(47.731, 7.557, "Bad Bellingen"),
    SCHLIENGEN(47.755, 7.579, "Schliengen"), AUGGEN(47.787, 7.593, "Auggen"),
    MUELLHEIM(47.804, 7.629, "Müllheim"), MARZELL(47.771, 7.726, "Marzell"),
    TEGERNAU(47.719, 7.795, "Tegernau"), SCHOENAU(47.785, 7.893, "Schönau"),
    MUENSTERTAL(47.854, 7.784, "Münstertal"), STAUFEN(47.882, 7.729, "Staufen"),
    HEITERSHEIM(47.874, 7.655, "Heitersheim");

    final double latitude;
    final double longitude;
    final String cityName;

    City(double latitude, double longitude, String cityName) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.cityName = cityName;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return cityName;
    }
}