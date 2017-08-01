package com.microsoft.azure.sdk.iot.deps.serializer;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class JobPropertiesParser
{
    private static Gson gson = new Gson();

    @SerializedName("jobId")
    public String JobId;

    @SerializedName("startTimeUtc")
    public Date StartTimeUtc;

    @SerializedName("endTimeUtc")
    public Date EndTimeUtc;

    @SerializedName("type")
    public String Type;

    @SerializedName("status")
    public String Status;

    @SerializedName("progress")
    public int Progress;

    @SerializedName("inputBlobContainerUri")
    public String InputBlobContainerUri;

    @SerializedName("outputBlobContainerUri")
    public String OutputBlobContainerUri;

    @SerializedName("excludeKeysInExport")
    public boolean ExcludeKeysInExport;

    @SerializedName("failureReason")
    public String FailureReason;

    public static JobPropertiesParser fromJson(String json)
    {
        //Codes_SRS_JOB_PROPERTIES_PARSER_34_002: [The parser will create and return an instance of a JobPropertiesParser object based off the provided json]
        return gson.fromJson(json, JobPropertiesParser.class);
    }

    public static String toJson(JobPropertiesParser jobPropertiesParser)
    {
        //Codes_SRS_JOB_PROPERTIES_PARSER_34_001: [The parser will return a json representation of the provided jobPropertiesParser]
        return gson.toJson(jobPropertiesParser);
    }
}
