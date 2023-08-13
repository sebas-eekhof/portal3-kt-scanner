package com.jsmecommerce.portal3scanner.models.files

import org.json.JSONObject

data class FilesCollection(
    val files: List<File>,
    val folders: List<Folder>
) {
    companion object {
        fun fromJSON(obj: JSONObject): FilesCollection = FilesCollection(
            File.fromJSONArray(obj.getJSONArray("files")),
            Folder.fromJSONArray(obj.getJSONArray("folders"))
        )
    }
}
