package enums;

public enum Language {
    ENGLISH("en", "US", "English"),
    FRENCH("fr", "FR", "French"),
    URDU("ur", "PK", "Urdu"),
    VIETNAMESE("vi", "VN", "Vietnamese");

    private final String code;
    private final String country;
    private final String name;

    Language(String code, String country, String name) {
        this.code = code;
        this.country = country;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return name;
    }

}
