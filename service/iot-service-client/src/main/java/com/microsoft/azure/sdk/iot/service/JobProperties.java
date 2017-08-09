/*
 * Copyright (c) Microsoft. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package com.microsoft.azure.sdk.iot.service;

import com.microsoft.azure.sdk.iot.deps.serializer.JobPropertiesParser;

import java.util.Date;

public class JobProperties {
    public JobProperties()
    {
        this.setJobId("");
    }

    /**
     * @return the system generated job id. Ignored at creation.
     */
    public String getJobId() {
        return JobId;
    }

    /**
     * @param jobId the job id
     * @throws IllegalArgumentException if the provided jobId is null
     */
    public void setJobId(String jobId) throws IllegalArgumentException
    {
        //Codes_SRS_SERVICE_SDK_JAVA_JOB_PROPERTIES_34_004: [If the provided jobId is null, an IllegalArgumentException shall be thrown.]
        if (jobId == null)
        {
            throw new IllegalArgumentException("JobId cannot be null");
        }

        JobId = jobId;
    }

    /**
     * @return the system generated UTC job start time. Ignored at creation.
     */
    public Date getStartTimeUtc() {
        return StartTimeUtc;
    }

    /**
     * @param startTimeUtc the UTC job start time.
     */
    public void setStartTimeUtc(Date startTimeUtc) {
        StartTimeUtc = startTimeUtc;
    }

    /**
     * @return the UTC job end time. Ignored at creation.
     * Represents the time the job stopped processing.
     */
    public Date getEndTimeUtc() {
        return EndTimeUtc;
    }

    /**
     * @param endTimeUtc the UTC job end time.
     */
    public void setEndTimeUtc(Date endTimeUtc) {
        EndTimeUtc = endTimeUtc;
    }

    /**
     * @return the type of job to execute.
     */
    public JobType getType() {
        return Type;
    }

    /**
     * @param type the type of job to execute.
     */
    public void setType(JobType type) {
        Type = type;
    }

    /**
     * @return the system generated job status. Ignored at creation.
     */
    public JobStatus getStatus() {
        return Status;
    }

    /**
     * @param status the job status.
     */
    public void setStatus(JobStatus status) {
        Status = status;
    }

    /**
     * @return the system generated job progress. Ignored at creation.
     * Represents the completion percentage.
     */
    public int getProgress() {
        return Progress;
    }

    /**
     * @param progress the job progress.
     */
    public void setProgress(int progress) {
        Progress = progress;
    }

    /**
     * @return URI containing SAS token to a blob container that contains registry data to sync.
     */
    public String getInputBlobContainerUri() {
        return InputBlobContainerUri;
    }

    /**
     * @param inputBlobContainerUri the input blob container URI.
     */
    public void setInputBlobContainerUri(String inputBlobContainerUri) {
        InputBlobContainerUri = inputBlobContainerUri;
    }

    /**
     * @return URI containing SAS token to a blob container.
     * This is used to output the status of the job and the results.
     */
    public String getOutputBlobContainerUri() {
        return OutputBlobContainerUri;
    }

    /**
     * @param outputBlobContainerUri the output blob container URI.
     */
    public void setOutputBlobContainerUri(String outputBlobContainerUri) {
        OutputBlobContainerUri = outputBlobContainerUri;
    }

    /**
     * @return whether the keys are included in export or not.
     */
    public boolean getExcludeKeysInExport() {
        return ExcludeKeysInExport;
    }

    /**
     * @param excludeKeysInExport optional for export jobs; ignored for other jobs.  Default: false.
     * If false, authorization keys are included in export output.  Keys are exported as null otherwise.
     */
    public void setExcludeKeysInExport(boolean excludeKeysInExport) {
        ExcludeKeysInExport = excludeKeysInExport;
    }

    /**
     * @return System generated. Ignored at creation.
     * If status == failure, this represents a string containing the reason.
     */
    public String getFailureReason() {
        return FailureReason;
    }

    /**
     * @param failureReason the failure reason.
     */
    public void setFailureReason(String failureReason) {
        FailureReason = failureReason;
    }

    public enum JobType
    {
        UNKNOWN,
        EXPORT,
        IMPORT
    }

    public enum JobStatus
    {
        UNKNOWN,
        ENQUEUED,
        RUNNING,
        COMPLETED,
        FAILED,
        CANCELLED
    }

    // CODES_SRS_SERVICE_SDK_JAVA_JOB_PROPERTIES_15_001: [The JobProperties class has the following properties: JobId,
    // StartTimeUtc, EndTimeUtc, JobType, JobStatus, Progress, InputBlobContainerUri, OutputBlobContainerUri,
    // ExcludeKeysInExport, FailureReason.]
    private String JobId;
    private Date StartTimeUtc;
    private Date EndTimeUtc;
    private JobType Type;
    private JobStatus Status;
    private int Progress;
    private String InputBlobContainerUri;
    private String OutputBlobContainerUri;
    private boolean ExcludeKeysInExport;
    private String FailureReason;

    /**
     * Constructs a new JobProperties object using a JobPropertiesParser object
     * @param parser the parser object to convert from
     */
    public JobProperties(JobPropertiesParser parser)
    {
        //Codes_SRS_SERVICE_SDK_JAVA_JOB_PROPERTIES_34_003: [This method shall convert the provided parser into a JobProperty object and return it.]
        this.EndTimeUtc = parser.getEndTimeUtc();
        this.ExcludeKeysInExport = parser.isExcludeKeysInExport();
        this.FailureReason = parser.getFailureReason();
        this.InputBlobContainerUri = parser.getInputBlobContainerUri();
        this.OutputBlobContainerUri = parser.getOutputBlobContainerUri();
        this.JobId = parser.getJobId();
        this.Progress = parser.getProgress();
        this.StartTimeUtc = parser.getStartTimeUtc();

        if (parser.getStatus() != null)
        {
            this.Status = JobStatus.valueOf(parser.getStatus().toUpperCase());
        }

        if (parser.getType() != null)
        {
            this.Type = JobType.valueOf(parser.getType().toUpperCase());
        }
    }

    /**
     * Converts this into a JobPropertiesParser object that can be used for serialization and deserialization
     * @return the converted JobPropertiesParser object
     */
    public JobPropertiesParser toJobPropertiesParser()
    {
        //Codes_SRS_SERVICE_SDK_JAVA_JOB_PROPERTIES_34_002: [This method shall convert this into a JobPropertiesParser object and return it.]
        JobPropertiesParser jobPropertiesParser = new JobPropertiesParser();
        jobPropertiesParser.setEndTimeUtc(this.EndTimeUtc);
        jobPropertiesParser.setExcludeKeysInExport(this.ExcludeKeysInExport);
        jobPropertiesParser.setFailureReason(this.FailureReason);
        jobPropertiesParser.setInputBlobContainerUri(this.InputBlobContainerUri);
        jobPropertiesParser.setOutputBlobContainerUri(this.OutputBlobContainerUri);
        jobPropertiesParser.setJobId(this.JobId);
        jobPropertiesParser.setProgress(this.Progress);
        jobPropertiesParser.setStartTimeUtc(this.StartTimeUtc);

        if (this.Status != null)
        {
            jobPropertiesParser.setStatus(this.Status.toString());
        }

        if (this.Type != null)
        {
            jobPropertiesParser.setType(this.Type.toString());
        }

        return jobPropertiesParser;
    }
}