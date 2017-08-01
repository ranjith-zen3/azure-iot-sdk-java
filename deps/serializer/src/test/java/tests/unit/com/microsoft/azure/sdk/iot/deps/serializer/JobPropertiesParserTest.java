package tests.unit.com.microsoft.azure.sdk.iot.deps.serializer;

import com.microsoft.azure.sdk.iot.deps.serializer.JobPropertiesParser;
import org.junit.Test;

import java.util.Date;

import static junit.framework.TestCase.assertEquals;

/**
 * Code Coverage
 * Methods: 100%
 * Lines: 100%
 */
public class JobPropertiesParserTest
{
    //Tests_SRS_JOB_PROPERTIES_PARSER_34_001: [The parser will return a json representation of the provided jobPropertiesParser]
    //Tests_SRS_JOB_PROPERTIES_PARSER_34_002: [The parser will create and return an instance of a JobPropertiesParser object based off the provided json]
    @Test
    public void testBasicFunctionality()
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
        parser.Status = "status";
        parser.Type = "type";

        // act
        JobPropertiesParser processedParser = JobPropertiesParser.fromJson(JobPropertiesParser.toJson(parser));

        // assert
        assertEquals(parser.EndTimeUtc.toString(), processedParser.EndTimeUtc.toString());
        assertEquals(parser.StartTimeUtc.toString(), processedParser.StartTimeUtc.toString());
        assertEquals(parser.ExcludeKeysInExport, processedParser.ExcludeKeysInExport);
        assertEquals(parser.FailureReason, processedParser.FailureReason);
        assertEquals(parser.JobId, processedParser.JobId);
        assertEquals(parser.InputBlobContainerUri, processedParser.InputBlobContainerUri);
        assertEquals(parser.OutputBlobContainerUri, processedParser.OutputBlobContainerUri);
        assertEquals(parser.Progress, processedParser.Progress);
        assertEquals(parser.Status, processedParser.Status);
        assertEquals(parser.Type, processedParser.Type);
    }
}
