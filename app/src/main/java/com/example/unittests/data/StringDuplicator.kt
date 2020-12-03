package com.example.unittests.data

class StringDuplicator {

    fun duplicator(string: String): String {
        val sb = StringBuilder()
        val charSequence = string.split("/")
        for (i in charSequence.indices.first..charSequence.indices.last){
            sb.append(charSequence[i])
        }
        return sb.toString().replace(" ","")
    }
}