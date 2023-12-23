package demo.aws.core.framework.grpc;

import demo.aws.core.common_util.model.AuthInfo;
import lombok.Data;

import java.io.*;

@Data
public class GRPCAuthInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private AuthInfo authInfo;
    private RequestInfo requestInfo;

    // Public methods
    // ------------------------------------------------------------------------
    public byte[] toBytes() {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        try {
            ObjectOutputStream os = new ObjectOutputStream(byteArray);
            os.writeObject(this);

        } catch (IOException ex) {
            return new byte[0];
        }
        return byteArray.toByteArray();
    }

    public static GRPCAuthInfo newLoginInfo(byte[] data) {
        try {
            ObjectInputStream io = new ObjectInputStream(new ByteArrayInputStream(data));
            return (GRPCAuthInfo) io.readObject();

        } catch (IOException | ClassNotFoundException ex) {
        }
        return null;
    }
}
