package com.example.haritagedemo.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.viewpager2.widget.ViewPager2
import com.example.haritagedemo.API.Const
import com.example.haritagedemo.API.Response
import com.example.haritagedemo.API.ResponseListener
import com.example.haritagedemo.API.ServiceManager
import com.example.haritagedemo.Model.PackageDetailModel
import com.example.haritagedemo.QuizData
import com.example.haritagedemo.R
import com.example.haritagedemo.heritageQuizPagerAdapter
import kotlinx.android.synthetic.main.activity_heritage_quiz.*
import kotlinx.android.synthetic.main.dialog_custom_background.*

class HeritageQuizActivity : AppCompatActivity() {

    var currentPosition = 0
    lateinit var animationUp: Animation
    lateinit var animationDown: Animation
    lateinit var alertDialog: AlertDialog
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
            if (currentPosition == 15){
                prepareResult(mheritageQuizPagerAdapter!!)
            }else{
                viewPagerQuiz.setCurrentItem(viewPagerQuiz.currentItem+1,true)
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
                                 } else {}
                            }
                        }catch (e:Exception){
                            e.printStackTrace()
                        }
                    }
                }

                override fun onRequestFailed(t: Throwable) {
                    super.onRequestFailed(t)
                }
            }
        )
    }

    fun prepareResult(heritageQuizPagerAdapter: heritageQuizPagerAdapter) {

        var correctAns = heritageQuizPagerAdapter.getCorrectAnsCount()

        var incorrectAns = heritageQuizPagerAdapter.getIncorrectAnsCount()

        var skippedAns = heritageQuizPagerAdapter.getSkipAnsCount()

        var totalcount = correctAns + incorrectAns

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

        dialog.show()
        dialog.setContentView(R.layout.dialog_custom_background)
        dialog.window!!.setBackgroundDrawable(getDrawable(R.drawable.background_dialog))

        dialog.window!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)

        dialog.window!!.attributes.windowAnimations = R.style.animation

        try {
            dialog.ev_correctAns.text = correctAns.toString()
            dialog.ev_IncorrectAns.text = incorrectAns.toString()
            dialog.ev_skippedQues.text = skippedAns.toString()
            dialog.headerResult.text = getString(R.string.header_result,totalcount.toString(),"15")
        }catch (e:Exception){
            e.printStackTrace()
        }

        dialog.QuitBtn.setOnClickListener(View.OnClickListener {
            finish()
        })
        dialog.setCancelable(false)
    }

}
