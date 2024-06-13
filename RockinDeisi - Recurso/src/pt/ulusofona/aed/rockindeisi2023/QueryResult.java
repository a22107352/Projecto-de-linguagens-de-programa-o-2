package pt.ulusofona.aed.rockindeisi2023;

public class QueryResult {
    String result;
    long time;

    public QueryResult(String result, long time) {
        this.result = result;
        this.time = time;
    }

    public String getResult() {
        return result;
    }

    public long getTime() {
        return time;
    }
}
