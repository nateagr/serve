package org.pytorch.serve.http.messages;

import com.google.gson.annotations.SerializedName;
import io.netty.handler.codec.http.QueryStringDecoder;
import org.pytorch.serve.util.ConfigManager;
import org.pytorch.serve.util.GRPCUtils;
import org.pytorch.serve.util.NettyUtils;

/** Register Model Request for Model server */
public class RegisterModelRequest {
    @SerializedName("model_name")
    private String modelName;

    @SerializedName("runtime")
    private String runtime;

    @SerializedName("handler")
    private String handler;

    @SerializedName("batch_size")
    private int batchSize;

    @SerializedName("max_batch_delay")
    private int maxBatchDelay;

    @SerializedName("initial_workers")
    private int initialWorkers;

    @SerializedName("synchronous")
    private boolean synchronous;

    @SerializedName("response_timeout")
    private int responseTimeout;

    @SerializedName("queue_timeout")
    private int queueTimeout;

    @SerializedName("url")
    private String modelUrl;

    @SerializedName("s3_sse_kms")
    private boolean s3SseKms;

    public RegisterModelRequest(QueryStringDecoder decoder) {
        modelName = NettyUtils.getParameter(decoder, "model_name", null);
        runtime = NettyUtils.getParameter(decoder, "runtime", null);
        handler = NettyUtils.getParameter(decoder, "handler", null);
        batchSize = NettyUtils.getIntParameter(decoder, "batch_size", 1);
        maxBatchDelay = NettyUtils.getIntParameter(decoder, "max_batch_delay", 100);
        initialWorkers =
                NettyUtils.getIntParameter(
                        decoder,
                        "initial_workers",
                        ConfigManager.getInstance().getConfiguredDefaultWorkersPerModel());
        synchronous = Boolean.parseBoolean(NettyUtils.getParameter(decoder, "synchronous", "true"));
        responseTimeout = NettyUtils.getIntParameter(decoder, "response_timeout", -1);
        queueTimeout = NettyUtils.getIntParameter(decoder, "queue_timeout", -1);
        modelUrl = NettyUtils.getParameter(decoder, "url", null);
        s3SseKms = Boolean.parseBoolean(NettyUtils.getParameter(decoder, "s3_sse_kms", "false"));
    }

    public RegisterModelRequest(org.pytorch.serve.grpc.management.RegisterModelRequest request) {
        modelName = GRPCUtils.getRegisterParam(request.getModelName(), null);
        runtime = GRPCUtils.getRegisterParam(request.getRuntime(), null);
        handler = GRPCUtils.getRegisterParam(request.getHandler(), null);
        batchSize = GRPCUtils.getRegisterParam(request.getBatchSize(), 1);
        maxBatchDelay = GRPCUtils.getRegisterParam(request.getMaxBatchDelay(), 100);
        initialWorkers =
                GRPCUtils.getRegisterParam(
                        request.getInitialWorkers(),
                        ConfigManager.getInstance().getConfiguredDefaultWorkersPerModel());
        synchronous = request.getSynchronous();
        responseTimeout = GRPCUtils.getRegisterParam(request.getResponseTimeout(), -1);
        queueTimeout = GRPCUtils.getRegisterParam(request.getQueueTimeout(), -1);
        modelUrl = GRPCUtils.getRegisterParam(request.getUrl(), null);
        s3SseKms = request.getS3SseKms();
    }

    public RegisterModelRequest() {
        batchSize = 1;
        maxBatchDelay = 100;
        synchronous = true;
        initialWorkers = ConfigManager.getInstance().getConfiguredDefaultWorkersPerModel();
        responseTimeout = -1;
        queueTimeout = -1;
        s3SseKms = false;
    }

    public String getModelName() {
        return modelName;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getHandler() {
        return handler;
    }

    public Integer getBatchSize() {
        return batchSize;
    }

    public Integer getMaxBatchDelay() {
        return maxBatchDelay;
    }

    public Integer getInitialWorkers() {
        return initialWorkers;
    }

    public Boolean getSynchronous() {
        return synchronous;
    }

    public Integer getResponseTimeout() {
        return responseTimeout;
    }

    public Integer getQueueTimeout() {
        return queueTimeout;
    }

    public String getModelUrl() {
        return modelUrl;
    }

    public Boolean getS3SseKms() {
        return s3SseKms;
    }
}
