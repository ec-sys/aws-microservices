package demo.aws.backend.batch.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class BatchResult {
    public BatchResult(Class runnerClass) {
        runnerName = runnerClass.getName();
        startTime = System.currentTimeMillis();
        startDate = new Date();
    }

    private String runnerName;
    private long startTime;
    private long endTime;
    private Date startDate;
    private Date endDate;
    private long runTimeMillis;
    private Object response;
    private String errorMessage;

    public void finish(){
        finish(null);
    }

    public void finish(Object response) {
        endTime = System.currentTimeMillis();
        endDate = new Date();
        runTimeMillis = endTime - startTime;
        this.response = response;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
