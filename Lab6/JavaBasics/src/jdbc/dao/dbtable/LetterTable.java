package jdbc.dao.dbtable;

public enum LetterTable {
    ID("id"),
    SENDER_ID("sender_id"),
    RECEIVER_ID("receiver_id"),
    TEXT("text"),
    THEME("theme"),
    SEND_DATE("send_date");

    private final String columnName;

    LetterTable(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
