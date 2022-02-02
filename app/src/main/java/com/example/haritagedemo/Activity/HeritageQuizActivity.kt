package com.example.haritagedemo.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.viewpager2.widget.ViewPager2
import com.example.haritagedemo.API.Const
import com.example.haritagedemo.API.Response
import com.example.haritagedemo.API.ResponseListener
import com.example.haritagedemo.API.ServiceManager
import com.example.haritagedemo.DBHelper
import com.example.haritagedemo.QuizData
import com.example.haritagedemo.R
import com.example.haritagedemo.heritageQuizPagerAdapter
import kotlinx.android.synthetic.main.activity_heritage_quiz.*

class HeritageQuizActivity : AppCompatActivity() {

    var currentPosition = 0
    lateinit var animationUp: Animation
    lateinit var animationDown: Animation
    lateinit var alertDialog: AlertDialog
    var quizList = ArrayList<QuizData>()
    var dbHelper: DBHelper? = null

    private var qarrayList :ArrayList<QuizData?> = ArrayList()
    private var mheritageQuizPagerAdapter: heritageQuizPagerAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heritage_quiz)
        initView()
    }

    private fun initView() {
        Log.d("HeritageQuizActivity","initView")
        //dbHelper = DBHelper(this)
        callApiGetHeritageQuiz()
        //viewPagerQuiz.isUserInputEnabled = false

        animationUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up)
        animationDown = AnimationUtils.loadAnimation(this, R.anim.bottom_down)

        viewPagerQuiz.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                val counterPosition: Int
                counterPosition = if (position == 0 || position <= 15) {
                    position + 1
                } else {
                    position
                }
                currentPosition = counterPosition
                tvHeader.text = counterPosition.toString() + " of 15"
                //enbleSkipButton()
                Log.d("HeritageQuizActivity","ViewPager Created" +quizList.size)
            }
        })

    }

    private fun callApiGetHeritageQuiz() {
        val paramMap = HashMap<String, Any?>()
        val serviceManager = ServiceManager(this)

        serviceManager.apiGetHeritageQuiz(
            paramMap,
            object :ResponseListener<retrofit2.Response<Response<ArrayList<QuizData?>>>>(){
                override fun onRequestSuccess(response: retrofit2.Response<Response<ArrayList<QuizData?>>>) {
                    val responseBody = response.body()
                    Log.d("HeritageQuizActivity",responseBody!!.result.toString())

                    runOnUiThread{
                        try {
                            if (responseBody != null) {
                                Log.d("HeritageQuizActivity","responsebody!=null")

                                if (responseBody.code == Const.SUCCESS) {
                                    Log.d("HeritageQuizActivity","responseBody is Success")

                                    //dbHelper?.insertQuiz(responseBody.result)
                                    //quizList = responseBody.result
                                            //dbHelper!!.getQuizList()
                                    Log.d("HeritageQuizActivity","Added to db and quizlist")
                                    mheritageQuizPagerAdapter = heritageQuizPagerAdapter(
                                        this@HeritageQuizActivity,
                                        responseBody.result,
                                        viewPagerQuiz
                                    )
                                    viewPagerQuiz.adapter = mheritageQuizPagerAdapter
                                    Log.d("HeritageQuizActivity","View pager set")
                                } else {
                                    Log.d("HeritageQuizActivity", "Else Part")
                                }
                            }
                        }catch (e:Exception){
                            e.printStackTrace()
                            Log.d("HeritageQuizActivity","exception")
                            Toast.makeText(this@HeritageQuizActivity,"Exception", Toast.LENGTH_LONG).show()
                        }
                    }

//                    heritageQuizPagerAdapter = heritageQuizPagerAdapter(
//                    this@HeritageQuizActivity,
//                        qarrayList
//                    )
//                    viewPagerQuiz.adapter = heritageQuizPagerAdapter

//                    if (responseBody != null){
//                        Log.d("HeritageQuizActivity","responesebody!=null")
//                        if (responseBody.code == Const.SUCCESS){
//                            heritageQuizPagerAdapter = heritageQuizPagerAdapter(
//                                this@HeritageQuizActivity,
//                                quizList,
//                                viewPagerQuiz
//                            )
//                            viewPagerQuiz.adapter = heritageQuizPagerAdapter
//                            Log.d("HeritageQuizActivity","Addeed")
//                        }
//                    }else{
//                        Log.d("HeritageQuizActivity","Not Added")
//                        Toast.makeText(this@HeritageQuizActivity,"its not working properly",Toast.LENGTH_LONG).show()
//                    }

                }

                override fun onRequestFailed(t: Throwable) {
                    super.onRequestFailed(t)
                }

            }
        )
    }

    private fun getData(quizList: ArrayList<QuizData?>?): java.util.ArrayList<QuizData> {
        var quizData = ArrayList<QuizData>()
        quizData.add(
            QuizData(
                    quizList!!.get(currentPosition)!!.question,
                    quizList!!.get(currentPosition)!!.option1,
                    quizList!!.get(currentPosition)!!.option2,
                    quizList!!.get(currentPosition)!!.answer,
                    quizList!!.get(currentPosition)!!.option3,
                    quizList!!.get(currentPosition)!!.option4
            )
        )
        return quizData
    }

//    private fun insertData(quizList: ArrayList<QuizData?>?){
//        for (quizData in quizList!!){
//
//        }
//    }

    fun prepareResult(heritageQuizPagerAdapter: heritageQuizPagerAdapter) {
        var correctAns = heritageQuizPagerAdapter.getCorrectAnsCount()
        var incorrectAns = heritageQuizPagerAdapter.getIncorrectAnsCount()
        var skippedAns = heritageQuizPagerAdapter.getSkipAnsCount()
        var totalcount = correctAns + incorrectAns
//        llResult.startAnimation(animationUp)
//        llResult.visibility = View.VISIBLE
//        tvHeaderResult.text = getString(R.string.lbl_you_answered_questions,totalcount.toString(),"15")
//        tvCorrectAns.text = correctAns.toString()
//        tvIncorrectAns.text = incorrectAns.toString()
//        tvSkippedAns.text = "$skippedAns ${getString(R.string.lbl_question)}"
    }

}
