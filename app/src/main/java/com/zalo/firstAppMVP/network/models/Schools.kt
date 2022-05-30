package com.zalo.firstAppMVP.network.models

import com.google.gson.annotations.SerializedName


data class Schools(
    @SerializedName("schools") val schools: List<School>,
)

data class School(
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String,
)

data class ResponseNetwork(
    @SerializedName("message") val msm: String,
)