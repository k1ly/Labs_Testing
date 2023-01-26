package jdbc.dao.dbtable;

public enum UserTable {
    ID("id"),
    NAME("name"),
    BIRTH_DATE("birth_date");

    private final String columnName;

    UserTable(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
