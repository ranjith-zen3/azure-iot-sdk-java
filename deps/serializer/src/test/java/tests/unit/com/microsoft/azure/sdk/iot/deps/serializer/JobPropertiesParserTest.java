// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

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
    //Tests_SRS_JOB_PROPERTIES_PARSER_34_001: [The constructor shall create and return an instance of a JobPropertiesParser object based off the provided json.]
    //Tests_SRS_JOB_PROPERTIES_PARSER_34_002: [This method shall return a json representation of this.]
    //Tests_SRS_JOB_PROPERTIES_PARSER_34_003: [JobPropertiesParser contains properties for InputBlobContainerUri, Type, Status, JobId, ExcludeKeysInExport, Progress, OutputBlobContainerUri, FailureReason, EndTimeUtc, and StartTimeUtc.]
    @Test
    public void testBasicFunctionality()
    {
        // arrange
        Date date = new Date(0);
        JobPropertiesParser parser = new JobPropertiesParser();
        parser.setEndTimeUtc(date);
        parser.setStartTimeUtc(date);
        parser.setFailureReason("failureReason");
        parser.setInputBlobContainerUri("inputContainerUri");
        parser.setOutputBlobContainerUri("outputContainerUri");
        parser.setProgress(0);
        parser.setExcludeKeysInExport(false);
        parser.setJobId("jobId");
        parser.setStatus("status");
        parser.setType("type");

        // act
        JobPropertiesParser processedParser = new JobPropertiesParser(parser.toJson());

        // assert
        assertEquals(parser.isExcludeKeysInExport(), processedParser.isExcludeKeysInExport());
        assertEquals(parser.getFailureReason(), processedParser.getFailureReason());
        assertEquals(parser.getJobId(), processedParser.getJobId());
        assertEquals(parser.getInputBlobContainerUri(), processedParser.getInputBlobContainerUri());
        assertEquals(parser.getOutputBlobContainerUri(), processedParser.getOutputBlobContainerUri());
        assertEquals(parser.getProgress(), processedParser.getProgress());
        assertEquals(parser.getStatus(), processedParser.getStatus());
        assertEquals(parser.getType(), processedParser.getType());
        assertEquals(parser.getStartTimeUtc(), processedParser.getStartTimeUtc());
        assertEquals(parser.getEndTimeUtc(), processedParser.getEndTimeUtc());
    }

    //Tests_SRS_JOB_PROPERTIES_PARSER_34_007: [If the provided json is null or empty, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void constructorThrowsForNullJson()
    {
        new JobPropertiesParser(null);
    }

    //Tests_SRS_JOB_PROPERTIES_PARSER_34_007: [If the provided json is null or empty, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void constructorThrowsForEmptyJson()
    {
        new JobPropertiesParser("");
    }

    //Tests_SRS_JOB_PROPERTIES_PARSER_34_008: [If the provided json cannot be parsed into a JobPropertiesParser object, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void constructorThrowsForInvalidJson()
    {
        new JobPropertiesParser("}");
    }

    //Tests_SRS_JOB_PROPERTIES_PARSER_34_005: [If the provided jobId is null, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void jobIdCannotBeSetToNull()
    {
        JobPropertiesParser parser = new JobPropertiesParser();
        parser.setJobId(null);
    }

    //Tests_SRS_JOB_PROPERTIES_PARSER_34_009: [If the provided json is missing the field for jobId, or if its value is null or empty, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void constructorJsonMissingJobIdInJsonThrowsIllegalArgumentException()
    {
        String json = "{\n" +
                "  \"startTimeUtc\": \"Dec 31, 1969 4:00:00 PM\",\n" +
                "  \"endTimeUtc\": \"Dec 31, 1969 4:00:00 PM\",\n" +
                "  \"type\": \"type\",\n" +
                "  \"status\": \"status\",\n" +
                "  \"progress\": 0,\n" +
                "  \"inputBlobContainerUri\": \"inputContainerUri\",\n" +
                "  \"outputBlobContainerUri\": \"outputContainerUri\",\n" +
                "  \"excludeKeysInExport\": false,\n" +
                "  \"failureReason\": \"failureReason\"\n" +
                "}";

        new JobPropertiesParser(json);
    }

    //Tests_SRS_JOB_PROPERTIES_PARSER_34_009: [If the provided json is missing the field for jobId, or if its value is null or empty, an IllegalArgumentException shall be thrown.]
    @Test (expected = IllegalArgumentException.class)
    public void constructorJsonWithJobIdMissingValueInJsonThrowsIllegalArgumentException()
    {
        String json = "{\n" +
                "  \"jobId\": \"\",\n" +
                "  \"startTimeUtc\": \"Dec 31, 1969 4:00:00 PM\",\n" +
                "  \"endTimeUtc\": \"Dec 31, 1969 4:00:00 PM\",\n" +
                "  \"type\": \"type\",\n" +
                "  \"status\": \"status\",\n" +
                "  \"progress\": 0,\n" +
                "  \"inputBlobContainerUri\": \"inputContainerUri\",\n" +
                "  \"outputBlobContainerUri\": \"outputContainerUri\",\n" +
                "  \"excludeKeysInExport\": false,\n" +
                "  \"failureReason\": \"failureReason\"\n" +
                "}";

        new JobPropertiesParser(json);
    }
}