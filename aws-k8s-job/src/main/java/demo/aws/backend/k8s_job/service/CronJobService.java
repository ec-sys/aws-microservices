package demo.aws.backend.k8s_job.service;

import demo.aws.backend.k8s_job.config.K8SConfig;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.BatchV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.auth.ApiKeyAuth;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Service
@Slf4j
public class CronJobService {

    public void createJobFromCronJob(String namespace, String cronJobName) {
        BatchV1Api apiInstance = new BatchV1Api(K8SConfig.getApiClient());

        V1CronJob cronJob = getCronJob(namespace, cronJobName);
        V1Job body = getV1Job(cronJob, cronJobName);

        try {
            V1Job createdJob = apiInstance.createNamespacedJob(namespace, body).execute();
        } catch (ApiException e) {
            System.err.println("Exception when calling BatchV1Api#createNamespacedJob");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }

    @NotNull
    private V1Job getV1Job(V1CronJob cronJob, String cronJobName) {
        V1JobSpec jobSpec = new V1JobSpec();
        V1PodTemplateSpec podTemplateSpec = cronJob.getSpec().getJobTemplate().getSpec().getTemplate();

        var specMetadata = podTemplateSpec.getMetadata();
        specMetadata.setName(getJobName(cronJobName));
        podTemplateSpec.setMetadata(specMetadata);

        jobSpec.setTemplate(podTemplateSpec);

        V1Job body = new V1Job(); // V1Job |
        body.setSpec(jobSpec);
        body.setMetadata(specMetadata);
        return body;
    }

    private String getJobName(String originJobName) {
        Random random = new Random();
        String pattern = "yyyyMMddHHmmss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        return String.format("%s-%s-%o", originJobName, date, random.nextInt(1000, 9999));
    }

    private V1CronJob getCronJob(String namespace, String cronJobName) {
        V1CronJob cronJob = new V1CronJob();
        try {
            BatchV1Api apiInstance = new BatchV1Api(K8SConfig.getApiClient());
            V1CronJobList jobList = apiInstance.listNamespacedCronJob(namespace).execute();
            for (V1CronJob item : jobList.getItems()) {
                if(cronJobName.equals(item.getMetadata().getName())) {
                    cronJob = item;
                }
            }
        } catch (ApiException exception) {
            log.error("Exception {}", ExceptionUtils.getStackTrace(exception));
        }
        return cronJob;
    }

    public void listAllCronJob(String namespace) {
        BatchV1Api apiInstance = getBatchV1Api();
        String pretty = "pretty_example"; // String | If 'true', then the output is pretty printed.
        Boolean allowWatchBookmarks = true; // Boolean | allowWatchBookmarks requests watch events with type \"BOOKMARK\". Servers that do not implement bookmarks may ignore this flag and bookmarks are sent at the server's discretion. Clients should not assume bookmarks are returned at any specific interval, nor may they assume the server will send any BOOKMARK event during a session. If this is not a watch, this field is ignored.
        String _continue = "_continue_example"; // String | The continue option should be set when retrieving more results from the server. Since this value is server defined, clients may only use the continue value from a previous query result with identical query parameters (except for the value of continue) and the server may reject a continue value it does not recognize. If the specified continue value is no longer valid whether due to expiration (generally five to fifteen minutes) or a configuration change on the server, the server will respond with a 410 ResourceExpired error together with a continue token. If the client needs a consistent list, it must restart their list without the continue field. Otherwise, the client may send another list request with the token received with the 410 error, the server will respond with a list starting from the next key, but from the latest snapshot, which is inconsistent from the previous list results - objects that are created, modified, or deleted after the first list request will be included in the response, as long as their keys are after the \"next key\".  This field is not supported when watch is true. Clients may start a watch from the last resourceVersion value returned by the server and not miss any modifications.
        String fieldSelector = "fieldSelector_example"; // String | A selector to restrict the list of returned objects by their fields. Defaults to everything.
        String labelSelector = "labelSelector_example"; // String | A selector to restrict the list of returned objects by their labels. Defaults to everything.
        Integer limit = 56; // Integer | limit is a maximum number of responses to return for a list call. If more items exist, the server will set the `continue` field on the list metadata to a value that can be used with the same initial query to retrieve the next set of results. Setting a limit may return fewer than the requested amount of items (up to zero items) in the event all requested objects are filtered out and clients should only use the presence of the continue field to determine whether more results are available. Servers may choose not to support the limit argument and will return all of the available results. If limit is specified and the continue field is empty, clients may assume that no more results are available. This field is not supported if watch is true.  The server guarantees that the objects returned when using continue will be identical to issuing a single list call without a limit - that is, no objects created, modified, or deleted after the first request is issued will be included in any subsequent continued requests. This is sometimes referred to as a consistent snapshot, and ensures that a client that is using limit to receive smaller chunks of a very large result can ensure they see all possible objects. If objects are updated during a chunked list the version of the object that was present at the time the first list result was calculated is returned.
        String resourceVersion = "resourceVersion_example"; // String | resourceVersion sets a constraint on what resource versions a request may be served from. See https://kubernetes.io/docs/reference/using-api/api-concepts/#resource-versions for details.  Defaults to unset
        String resourceVersionMatch = "resourceVersionMatch_example"; // String | resourceVersionMatch determines how resourceVersion is applied to list calls. It is highly recommended that resourceVersionMatch be set for list calls where resourceVersion is set See https://kubernetes.io/docs/reference/using-api/api-concepts/#resource-versions for details.  Defaults to unset
        Boolean sendInitialEvents = true; // Boolean | `sendInitialEvents=true` may be set together with `watch=true`. In that case, the watch stream will begin with synthetic events to produce the current state of objects in the collection. Once all such events have been sent, a synthetic \"Bookmark\" event  will be sent. The bookmark will report the ResourceVersion (RV) corresponding to the set of objects, and be marked with `\"k8s.io/initial-events-end\": \"true\"` annotation. Afterwards, the watch stream will proceed as usual, sending watch events corresponding to changes (subsequent to the RV) to objects watched.  When `sendInitialEvents` option is set, we require `resourceVersionMatch` option to also be set. The semantic of the watch request is as following: - `resourceVersionMatch` = NotOlderThan   is interpreted as \"data at least as new as the provided `resourceVersion`\"   and the bookmark event is send when the state is synced   to a `resourceVersion` at least as fresh as the one provided by the ListOptions.   If `resourceVersion` is unset, this is interpreted as \"consistent read\" and the   bookmark event is send when the state is synced at least to the moment   when request started being processed. - `resourceVersionMatch` set to any other value or unset   Invalid error is returned.  Defaults to true if `resourceVersion=\"\"` or `resourceVersion=\"0\"` (for backward compatibility reasons) and to false otherwise.
        Integer timeoutSeconds = 56; // Integer | Timeout for the list/watch call. This limits the duration of the call, regardless of any activity or inactivity.
        Boolean watch = true; // Boolean | Watch for changes to the described resources and return them as a stream of add, update, and remove notifications. Specify resourceVersion.
        try {
            V1CronJobList result = apiInstance.listNamespacedCronJob(namespace)
                    .pretty(pretty)
                    .allowWatchBookmarks(allowWatchBookmarks)
                    ._continue(_continue)
                    .fieldSelector(fieldSelector)
                    .labelSelector(labelSelector)
                    .limit(limit)
                    .resourceVersion(resourceVersion)
                    .resourceVersionMatch(resourceVersionMatch)
                    .sendInitialEvents(sendInitialEvents)
                    .timeoutSeconds(timeoutSeconds)
                    .watch(watch)
                    .execute();
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling BatchV1Api#listNamespacedCronJob");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }

    @NotNull
    private BatchV1Api getBatchV1Api() {
        BatchV1Api apiInstance = new BatchV1Api(K8SConfig.getApiClient());
        return apiInstance;
    }

    public void testConnect() throws Exception {
        ApiClient client = K8SConfig.getApiClient();

        CoreV1Api api = new CoreV1Api(client);
        BatchV1Api apiInstance = new BatchV1Api(client);

        // invokes the CoreV1Api client
        V1PodList list = api.listNamespacedPod("dev-envoy").execute();
        System.out.println("Listing all pods: ");
        for (V1Pod item : list.getItems()) {
            System.out.println(item.getMetadata().getName());
        }

        V1CronJobList jobList = apiInstance.listNamespacedCronJob("dev-envoy").execute();
        System.out.println(jobList.getItems().get(0).getSpec().getSchedule());
    }
}
