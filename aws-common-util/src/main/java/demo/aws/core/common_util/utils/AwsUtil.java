package demo.aws.core.common_util.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import demo.aws.core.common_util.model.aws.AwsSqsMessageFromSns;
import org.apache.commons.lang3.StringUtils;

public class AwsUtil {
    public static  <T> T  getObjectFromSnsMessage(String jsonString, Class<?> classOfT) {
        Gson gson = new Gson();
        AwsSqsMessageFromSns fromSns = gson.fromJson(jsonString, AwsSqsMessageFromSns.class);
        if(StringUtils.isNotBlank(fromSns.getMessage())) {
            return (T) gson.fromJson(fromSns.getMessage(), classOfT);
        }
        return null;
    }
}
