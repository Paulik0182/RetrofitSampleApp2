package com.android.retrofitsampleapp2.domain

import com.google.gson.annotations.SerializedName

data class GitUserEntity(
    var id: Int,
    var login: String,
    @field:SerializedName("node_id")
    var nodeId: String
)