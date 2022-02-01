package com.example.haritagedemo

import com.google.gson.annotations.SerializedName

data class QuizData(
    @SerializedName("q")
    var question: String,
    @SerializedName("correctIndex")
    var correctIndex: Int,
    var answer: String,
    @SerializedName("options")
    var optionArray: ArrayList<String>?,
    var option1: String,
    var option2: String,
    var option3: String,
    var option4: String,
    var isOption1Selected: Boolean,
    var isOption2Selected: Boolean,
    var isOption3Selected: Boolean,
    var isOption4Selected: Boolean,
    var correctAns: Boolean,
    var incorrectAns: Boolean
){
    constructor(
        question: String,
        answer: String,
        option1: String,
        option2: String,
        option3: String,
        option4: String
    ) :
            this(
                question,
                Int.MIN_VALUE,
                answer,
                null,
                option1,
                option2,
                option3,
                option4,
                false,
                false,
                false,
                false,
                false,
                false
            )
}
