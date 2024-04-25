package demo.aws.core.common_util.model.aws;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AwsSqsMessageFromSns {
    @JsonProperty("Type")
    private String Type;

    @JsonProperty("MessageId")
    private String MessageId;

    @JsonProperty("TopicArn")
    private String TopicArn;

    @JsonProperty("Message")
    private String Message;

    @JsonProperty("UnsubscribeURL")
    private String UnsubscribeURL;
}
