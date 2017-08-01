package tests.unit.com.microsoft.azure.sdk.iot.service;

import com.microsoft.azure.sdk.iot.deps.serializer.JobPropertiesParser;
import com.microsoft.azure.sdk.iot.service.JobProperties;
import org.junit.Test;

import java.util.Date;

import static junit.framework.TestCase.assertEquals;

/**
 * Code Coverage
 * Methods:100%
 * Lines: 97%
 */
public class JobPropertiesTest
{
    //Tests_SRS_SERVICE_SDK_JAVA_JOB_PROPERTIES_15_001: [This method shall convert the provided parser into a JobProperty object and return it]
    @Test
    public void fromJobPropertiesParser()
    {
        // arrange
        JobPropertiesParser parser = new JobPropertiesParser();
        parser.EndTimeUtc = new Date(System.currentTimeMillis());
        parser.StartTimeUtc = new Date(System.currentTimeMillis());
        parser.FailureReason = "failureReason";
        parser.InputBlobContainerUri = "inputContainerUri";
        parser.OutputBlobContainerUri = "outputContainerUri";
        parser.Progress = 0;
        parser.ExcludeKeysInExport = false;
        parser.JobId = "jobId";
        parser.Status = JobProperties.JobStatus.COMPLETED.toString();
        parser.Type = JobProperties.JobType.IMPORT.toString();

        // act
        JobProperties jobProperties = JobProperties.fromJobPropertiesParser(parser);

        // assert
        assertEquals(parser.InputBlobContainerUri, jobProperties.getInputBlobContainerUri());
        assertEquals(parser.OutputBlobContainerUri, jobProperties.getOutputBlobContainerUri());
        assertEquals(parser.ExcludeKeysInExport, jobProperties.getExcludeKeysInExport());
        assertEquals(parser.Type, jobProperties.getType().toString());
        assertEquals(parser.Status, jobProperties.getStatus().toString());
        assertEquals(parser.Progress, jobProperties.getProgress());
        assertEquals(parser.JobId, jobProperties.getJobId());
        assertEquals(parser.FailureReason, jobProperties.getFailureReason());
        assertEquals(parser.EndTimeUtc, jobProperties.getEndTimeUtc());
        assertEquals(parser.StartTimeUtc, jobProperties.getStartTimeUtc());
    }

    //Tests_SRS_SERVICE_SDK_JAVA_JOB_PROPERTIES_34_002: [This method shall convert the provided jobProperties into a JobPropertiesParser object and return it]
    @Test
    public void toJobPropertiesParser()
    {
        // arrange
        JobProperties jobProperties = new JobProperties();
        jobProperties.setEndTimeUtc(new Date(System.currentTimeMillis()));
        jobProperties.setStartTimeUtc(new Date(System.currentTimeMillis()));
        jobProperties.setFailureReason("failureReason");
        jobProperties.setInputBlobContainerUri("inputContainerUri");
        jobProperties.setOutputBlobContainerUri("outputContainerUri");
        jobProperties.setProgress(0);
        jobProperties.setExcludeKeysInExport(false);
        jobProperties.setJobId("jobId");
        jobProperties.setStatus(JobProperties.JobStatus.COMPLETED);
        jobProperties.setType(JobProperties.JobType.IMPORT);

        // act
        JobPropertiesParser parser = JobProperties.toJobPropertiesParser(jobProperties);

        // assert
        assertEquals(parser.InputBlobContainerUri, jobProperties.getInputBlobContainerUri());
        assertEquals(parser.OutputBlobContainerUri, jobProperties.getOutputBlobContainerUri());
        assertEquals(parser.ExcludeKeysInExport, jobProperties.getExcludeKeysInExport());
        assertEquals(parser.Type, jobProperties.getType().toString());
        assertEquals(parser.Status, jobProperties.getStatus().toString());
        assertEquals(parser.Progress, jobProperties.getProgress());
        assertEquals(parser.JobId, jobProperties.getJobId());
        assertEquals(parser.FailureReason, jobProperties.getFailureReason());
        assertEquals(parser.EndTimeUtc, jobProperties.getEndTimeUtc());
        assertEquals(parser.StartTimeUtc, jobProperties.getStartTimeUtc());
    }
}
