package jdbc.dao.parameter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ParameterMap {
    Map<String, Object> parameters;

    public ParameterMap() {
        parameters = new LinkedHashMap<>();
    }

    private ParameterMap(Map<String, Object> parameters) {
        this.parameters = new LinkedHashMap<>(parameters);
    }

    public static ParameterMap of(Map<String, Object> parameters) {
        return new ParameterMap(parameters);
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public List<Object> getValues() {
        List<Object> values = new ArrayList<>();
        for (var entry : parameters.entrySet()) {
            values.add(entry.getValue());
        }
        return values;
    }

    public void addParameter(String parameter, Object value) {
        parameters.put(parameter, value);
    }

    public int size() {
        return parameters.size();
    }
}
