// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package com.microsoft.azure.sdk.iot.deps.serializer;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class JobPropertiesParser
{
    private static Gson gson = new Gson();

    private static final String JOB_ID_NAME = "jobId";
    @Expose(serialize = true, deserialize = true)
    @SerializedName(JOB_ID_NAME)
    private String JobId;

    private static final String START_TIME_UTC_NAME = "startTimeUtc";
    @Expose(serialize = true, deserialize = true)
    @SerializedName(START_TIME_UTC_NAME)
    private Date StartTimeUtc;

    private static final String END_TIME_UTC_NAME = "endTimeUtc";
    @Expose(serialize = true, deserialize = true)
    @SerializedName(END_TIME_UTC_NAME)
    private Date EndTimeUtc;

    private static final String TYPE_NAME = "type";
    @Expose(serialize = true, deserialize = true)
    @SerializedName(TYPE_NAME)
    private String Type;

    private static final String STATUS_NAME = "status";
    @Expose(serialize = true, deserialize = true)
    @SerializedName(STATUS_NAME)
    private String Status;

    private static final String PROGRESS_NAME = "progress";
    @Expose(serialize = true, deserialize = true)
    @SerializedName(PROGRESS_NAME)
    private int Progress;

    private static final String INPUT_BLOB_CONTAINER_URI_NAME = "inputBlobContainerUri";
    @Expose(serialize = true, deserialize = true)
    @SerializedName(INPUT_BLOB_CONTAINER_URI_NAME)
    private String InputBlobContainerUri;

    private static final String OUTPUT_BLOB_CONTAINER_URI_NAME = "outputBlobContainerUri";
    @Expose(serialize = true, deserialize = true)
    @SerializedName(OUTPUT_BLOB_CONTAINER_URI_NAME)
    private String OutputBlobContainerUri;

    private static final String EXCLUDE_KEYS_IN_EXPORT_NAME = "excludeKeysInExport";
    @Expose(serialize = true, deserialize = true)
    @SerializedName(EXCLUDE_KEYS_IN_EXPORT_NAME)
    private boolean ExcludeKeysInExport;

    private static final String FAILURE_REASON_NAME = "failureReason";
    @Expose(serialize = true, deserialize = true)
    @SerializedName(FAILURE_REASON_NAME)
    private String FailureReason;

    private static final String NULL_OR_EMPTY_JSON_ERROR_MESSAGE = "The provided json cannot be null or empty";
    private static final String INVALID_JSON_ERROR_MESSAGE = "The provided json could not be parsed";
    private static final String JOB_ID_NULL_ERROR_MESSAGE = "jobId cannot be null";
    private static final String MISSING_JOB_ID_FIELD = "The provided json is missing the JobId field";

    /**
     * Empty constructor: Used only to keep GSON happy.
     */
    @SuppressWarnings("unused")
    public JobPropertiesParser()
    {
        //jobId is mandatory
        this.setJobId("");
    }

    /**
     * Constructor for a JobPropertiesParser object that is built from the provided Json
     * @param json the json to build the JobPropertiesParser from
     * @throws IllegalArgumentException if the provided Json is null, empty, cannot be parsed,
     * or if the provided Json is missing any of the Type, InputBlobContainerUri or OutputBlobContainerUri fields
     */
    public JobPropertiesParser(String json) throws IllegalArgumentException
    {
        if (json == null || json.isEmpty())
        {
            //Codes_SRS_JOB_PROPERTIES_PARSER_34_007: [If the provided json is null or empty, an IllegalArgumentException shall be thrown.]
            throw new IllegalArgumentException(NULL_OR_EMPTY_JSON_ERROR_MESSAGE);
        }

        JobPropertiesParser parser;
        try
        {
            parser = gson.fromJson(json, JobPropertiesParser.class);
        }
        catch (Exception e)
        {
            //Codes_SRS_JOB_PROPERTIES_PARSER_34_008: [If the provided json cannot be parsed into a JobPropertiesParser object, an IllegalArgumentException shall be thrown.]
            throw new IllegalArgumentException(INVALID_JSON_ERROR_MESSAGE);
        }

        if (parser.getJobId() == null || parser.getJobId().isEmpty())
        {
            //Codes_SRS_JOB_PROPERTIES_PARSER_34_009: [If the provided json is missing the field for jobId, or if its value is null or empty, an IllegalArgumentException shall be thrown.]
            throw new IllegalArgumentException(MISSING_JOB_ID_FIELD);
        }

        this.InputBlobContainerUri = parser.InputBlobContainerUri;
        this.Type = parser.Type;
        this.Status = parser.Status;
        this.JobId = parser.JobId;
        this.ExcludeKeysInExport = parser.ExcludeKeysInExport;
        this.Progress = parser.Progress;
        this.OutputBlobContainerUri = parser.OutputBlobContainerUri;
        this.FailureReason = parser.FailureReason;
        this.EndTimeUtc = parser.EndTimeUtc;
        this.StartTimeUtc = parser.StartTimeUtc;
    }

    /**
     * Converts this into json and returns it
     * @return the json representation of this
     */
    public String toJson()
    {
        return gson.toJson(this);
    }

    /**
     * Getter for Type
     *
     * @return The value of Type
     */
    public String getType()
    {
        return Type;
    }

    /**
     * Setter for Type
     *
     * @throws IllegalArgumentException if type is null
     */
    public void setType(String type) throws IllegalArgumentException
    {
        Type = type;
    }

    /**
     * Getter for InputBlobContainerUri
     *
     * @return The value of InputBlobContainerUri
     */
    public String getInputBlobContainerUri()
    {
        return InputBlobContainerUri;
    }

    /**
     * Setter for InputBlobContainerUri
     *
     * @throws IllegalArgumentException if inputBlobContainerUri is null
     */
    public void setInputBlobContainerUri(String inputBlobContainerUri) throws IllegalArgumentException
    {
        InputBlobContainerUri = inputBlobContainerUri;
    }

    /**
     * Getter for OutputBlobContainerUri
     *
     * @return The value of OutputBlobContainerUri
     */
    public String getOutputBlobContainerUri()
    {
        return OutputBlobContainerUri;
    }

    /**
     * Setter for OutputBlobContainerUri
     *
     * @throws IllegalArgumentException if outputBlobContainerUri is null
     */
    public void setOutputBlobContainerUri(String outputBlobContainerUri) throws IllegalArgumentException
    {
        OutputBlobContainerUri = outputBlobContainerUri;
    }

    /**
     * Getter for JobId
     *
     * @return The value of JobId
     */
    public String getJobId()
    {
        return JobId;
    }

    /**
     * Setter for JobId
     */
    public void setJobId(String jobId)
    {
        //Codes_SRS_JOB_PROPERTIES_PARSER_34_005: [If the provided jobId is null, an IllegalArgumentException shall be thrown.]
        if (jobId == null)
        {
            throw new IllegalArgumentException(JOB_ID_NULL_ERROR_MESSAGE);
        }

        JobId = jobId;
    }

    /**
     * Getter for StartTimeUtc
     *
     * @return The value of StartTimeUtc
     */
    public Date getStartTimeUtc()
    {
        return StartTimeUtc;
    }

    /**
     * Setter for StartTimeUtc
     */
    public void setStartTimeUtc(Date startTimeUtc)
    {
        StartTimeUtc = startTimeUtc;
    }

    /**
     * Getter for EndTimeUtc
     *
     * @return The value of EndTimeUtc
     */
    public Date getEndTimeUtc()
    {
        return EndTimeUtc;
    }

    /**
     * Setter for EndTimeUtc
     */
    public void setEndTimeUtc(Date endTimeUtc)
    {
        EndTimeUtc = endTimeUtc;
    }

    /**
     * Getter for Status
     *
     * @return The value of Status
     */
    public String getStatus()
    {
        return Status;
    }

    /**
     * Setter for Status
     */
    public void setStatus(String status)
    {
        Status = status;
    }

    /**
     * Getter for Progress
     *
     * @return The value of Progress
     */
    public int getProgress()
    {
        return Progress;
    }

    /**
     * Setter for Progress
     */
    public void setProgress(int progress)
    {
        Progress = progress;
    }

    /**
     * Getter for ExcludeKeysInExport
     *
     * @return The value of ExcludeKeysInExport
     */
    public boolean isExcludeKeysInExport()
    {
        return ExcludeKeysInExport;
    }

    /**
     * Setter for ExcludeKeysInExport
     */
    public void setExcludeKeysInExport(boolean excludeKeysInExport)
    {
        ExcludeKeysInExport = excludeKeysInExport;
    }

    /**
     * Getter for FailureReason
     *
     * @return The value of FailureReason
     */
    public String getFailureReason()
    {
        return FailureReason;
    }

    /**
     * Setter for FailureReason
     */
    public void setFailureReason(String failureReason)
    {
        FailureReason = failureReason;
    }
}
