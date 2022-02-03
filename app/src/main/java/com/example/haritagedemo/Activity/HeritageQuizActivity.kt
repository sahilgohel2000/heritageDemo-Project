package com.example.haritagedemo.Activity

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
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
        callApiGetHeritageQuiz()
        animationUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up)
        animationDown = AnimationUtils.loadAnimation(this, R.anim.bottom_down)

        viewPagerQuiz.isUserInputEnabled = false

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
            }
        })

        tv_skip.setOnClickListener(View.OnClickListener {
            Log.d("HeritageQuizActivity","tv_skip")
            if (currentPosition == 15){
                Log.d("HeritageQuizActivity","curent position ==15")
                Toast.makeText(this,"Completed",Toast.LENGTH_LONG).show()
                Log.d("HeritageQuizActivity","prepareResult 1")
                prepareResult(mheritageQuizPagerAdapter!!)
            }else{
            viewPagerQuiz.setCurrentItem(viewPagerQuiz.currentItem+1,true)
            }
        })

        tv_Quit.setOnClickListener(View.OnClickListener {
            Log.d("HeritageQuizActivity","Quit")
            showAlertDialog()
        })
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)
        Log.d("HeritageQuizActivity","Show Alert Dialog")
        builder.setTitle("")
        builder.setMessage(getString(R.string.lbl_want_to_quit))
        builder.setPositiveButton(getString(R.string.yes)) { dialogInterface, which ->
            finish()
        }

        builder.setNegativeButton(getString(R.string.no)) { dialogInterface, which ->
            alertDialog.dismiss()
        }


        alertDialog = builder.create().apply {
            setOnShowListener {
                getButton(Dialog.BUTTON_NEGATIVE)?.setTextColor(
                    ContextCompat.getColor(
                        this@HeritageQuizActivity,
                        R.color.black
                    )
                )
                getButton(Dialog.BUTTON_POSITIVE)?.setTextColor(
                    ContextCompat.getColor(
                        this@HeritageQuizActivity,
                        R.color.colorBlue1
                    )
                )
            }
        }


        alertDialog.setCancelable(false)
        alertDialog.show()
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
                                if (responseBody.code == Const.SUCCESS) {
                                    mheritageQuizPagerAdapter = heritageQuizPagerAdapter(
                                        this@HeritageQuizActivity,
                                        responseBody.result,
                                        viewPagerQuiz
                                    )
                                    viewPagerQuiz.adapter = mheritageQuizPagerAdapter
                                 } else {
                                    Log.d("HeritageQuizActivity", "Else Part")
                                }
                            }
                        }catch (e:Exception){
                            e.printStackTrace()
                            Toast.makeText(this@HeritageQuizActivity,"Exception", Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onRequestFailed(t: Throwable) {
                    super.onRequestFailed(t)
                }
            }
        )
    }
//    private fun getData(quizList: ArrayList<QuizData?>?): java.util.ArrayList<QuizData> {
//        var quizData = ArrayList<QuizData>()
//        quizData.add(
//            QuizData(
//                    quizList!!.get(currentPosition)!!.question,
//                    quizList!!.get(currentPosition)!!.option1,
//                    quizList!!.get(currentPosition)!!.option2,
//                    quizList!!.get(currentPosition)!!.answer,
//                    quizList!!.get(currentPosition)!!.option3,
//                    quizList!!.get(currentPosition)!!.option4
//            )
//        )
//        return quizData
//    }

//    private fun insertData(quizList: ArrayList<QuizData?>?){
//        for (quizData in quizList!!){
//
//        }
//    }

    fun prepareResult(heritageQuizPagerAdapter: heritageQuizPagerAdapter) {
        Log.d("HeritageQuizActivity","prepareResult 2")

        var correctAns = heritageQuizPagerAdapter.getCorrectAnsCount()
        Log.d("HeritageQuizActivity","correct Answer"+correctAns)

        var incorrectAns = heritageQuizPagerAdapter.getIncorrectAnsCount()
        Log.d("HeritageQuizActivity","correct Answer"+incorrectAns)

        var skippedAns = heritageQuizPagerAdapter.getSkipAnsCount()
        Log.d("HeritageQuizActivity","correct Answer"+skippedAns)

        var totalcount = correctAns + incorrectAns
        Log.d("HeritageQuizActivity","correct Answer"+totalcount)

        //ResultDialog(correctAns,incorrectAns,skippedAns,totalcount)

        ResultDialogBox(correctAns,incorrectAns,skippedAns,totalcount)
    }

    fun ResultDialogBox(
        correctAns: Int,
        incorrectAns: Int,
        skippedAns: Int,
        totalcount: Int
    ) {
        val view = View.inflate(this,R.layout.dialog_custom_background,null)

        val builder = AlertDialog.Builder(this@HeritageQuizActivity)

        builder.setView(view)

        val dialog =builder.create()
        //Dialog(this@HeritageQuizActivity)
       dialog.show()
        dialog.setContentView(R.layout.dialog_custom_background)
        dialog.window!!.setBackgroundDrawable(getDrawable(R.drawable.background_dialog))

        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        //dialog.setCancelable(false)
        dialog.window!!.attributes.windowAnimations = R.style.animation
    }

    private fun ResultDialog(correctAns: Int, incorrectAns: Int, skippedAns: Int, totalcount: Int) {

        val builder = AlertDialog.Builder(this)
        Log.d("HeritageQuizActivity","Show Alert Dialog")

        builder.setTitle("Result")
        builder.setMessage("Correct Ans"+correctAns+"incorrect Ans"+incorrectAns+"skiped Ans"+skippedAns+"Result"+totalcount)
        builder.setPositiveButton(getString(R.string.close)) { dialogInterface, which ->
            finish()
        }

        alertDialog = builder.create().apply {
            setOnShowListener {
                getButton(Dialog.BUTTON_POSITIVE)?.setTextColor(
                    ContextCompat.getColor(
                        this@HeritageQuizActivity,
                        R.color.colorBrown
                    )
                )
            }
        }

        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}
