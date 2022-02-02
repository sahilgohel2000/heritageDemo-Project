package com.example.haritagedemo

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.haritagedemo.Activity.HeritageQuizActivity
import kotlinx.android.synthetic.main.row_questions_layout.view.*

class heritageQuizPagerAdapter(
    val context: Context,
    var quizDataList: ArrayList<QuizData?>? ,
    var viewPager: ViewPager2
):RecyclerView.Adapter<heritageQuizPagerAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder=
        UserViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.row_questions_layout,parent,false)
    )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.tvQuestion.text = quizDataList!!.get(position)!!.question
        holder.btnOptionOne.text = quizDataList!!.get(position)!!.optionArray!!.get(0)
        holder.btnOptionTwo.text = quizDataList!!.get(position)!!.optionArray!!.get(1)
        holder.btnOptionThree.text = quizDataList!!.get(position)!!.optionArray!!.get(2)
        holder.btnOptionFour.text = quizDataList!!.get(position)!!.optionArray!!.get(3)

        val activity = context as HeritageQuizActivity
        val newIndex = position % 5

        when(newIndex){
            0->{
                holder.ivQuestion.setImageResource(R.drawable.ic_background_quiz_one)
            }
            1->{
                holder.ivQuestion.setImageResource(R.drawable.ic_background_quiz_two)
            }
            2->{
                holder.ivQuestion.setImageResource(R.drawable.ic_background_quiz_three)
            }
            3->{
                holder.ivQuestion.setImageResource(R.drawable.ic_background_quiz_four)
            }
            4->{
                holder.ivQuestion.setImageResource(R.drawable.ic_background_quiz_five)
            }
        }

        var correctBtn = checkCorrectBtn(
            holder.btnOptionOne as AppCompatButton,
            holder.btnOptionTwo as AppCompatButton,
            holder.btnOptionThree as AppCompatButton,
            holder.btnOptionFour as AppCompatButton,
            quizDataList!!.get(position)!!.answer
        )
        holder.btnOptionOne.setOnClickListener {
            checkAnswer(
                quizDataList!!.get(position)!!, holder.btnOptionOne, correctBtn, position
            )
            holder.btnOptionOne!!.isEnabled = false
            holder.btnOptionTwo!!.isEnabled = false
            holder.btnOptionThree!!.isEnabled = false
            holder.btnOptionFour!!.isEnabled = false
        }
        holder.btnOptionTwo.setOnClickListener {
            checkAnswer(
                quizDataList!!.get(position)!!, holder.btnOptionTwo, correctBtn, position
            )
            holder.btnOptionOne!!.isEnabled = false
            holder.btnOptionTwo!!.isEnabled = false
            holder.btnOptionThree!!.isEnabled = false
            holder.btnOptionFour!!.isEnabled = false
        }
        holder.btnOptionThree.setOnClickListener {
            checkAnswer(
                quizDataList!!.get(position)!!, holder.btnOptionThree, correctBtn, position
            )
            holder.btnOptionOne!!.isEnabled = false
            holder.btnOptionTwo!!.isEnabled = false
            holder.btnOptionThree!!.isEnabled = false
            holder.btnOptionFour!!.isEnabled = false
        }
        holder.btnOptionFour.setOnClickListener {
            checkAnswer(
                quizDataList!!.get(position)!!, holder.btnOptionFour, correctBtn, position
            )
            holder.btnOptionOne!!.isEnabled = false
            holder.btnOptionTwo!!.isEnabled = false
            holder.btnOptionThree!!.isEnabled = false
            holder.btnOptionFour!!.isEnabled = false
        }

    }

    private fun checkAnswer(
        get: QuizData,
        btnOptionOne: AppCompatButton,
        correctBtn: AppCompatButton,
        position: Int
    ) {

        if (btnOptionOne == correctBtn) {
            get.correctAns = true
            get.incorrectAns = false
            btnOptionOne.background =
                ContextCompat.getDrawable(context, R.drawable.rounded_corner_textview_correct)
            btnOptionOne.setTextColor(ContextCompat.getColor(context, R.color.white))
        } else {
            get.incorrectAns = true
            get.correctAns = false
            btnOptionOne.background =
                ContextCompat.getDrawable(context, R.drawable.rounded_corner_textview_incorrect)
            btnOptionOne.setTextColor(ContextCompat.getColor(context, R.color.white))
            correctBtn.background =
                ContextCompat.getDrawable(context, R.drawable.rounded_corner_textview_correct)
            correctBtn.setTextColor(ContextCompat.getColor(context, R.color.white))
        }
        val activity = context as HeritageQuizActivity
        if (position == (quizDataList!!.size - 1)) {

            Handler(Looper.getMainLooper()).postDelayed({
                activity.prepareResult(this)
            }, 1000)

        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                viewPager.setCurrentItem(viewPager.currentItem + 1, true)
            }, 1000)
        }

    }

    private fun checkCorrectBtn(
        btnOptionOne: AppCompatButton?,
        btnOptionTwo: AppCompatButton?,
        btnOptionThree: AppCompatButton?,
        btnOptionFour: AppCompatButton?,
        answer: String
    ): AppCompatButton {
        when(answer){
            btnOptionOne!!.text -> return btnOptionOne
            btnOptionTwo!!.text -> return btnOptionTwo
            btnOptionThree!!.text -> return btnOptionThree
            btnOptionFour!!.text -> return btnOptionFour
        }
        btnOptionOne!!.isEnabled = true
        btnOptionTwo!!.isEnabled = true
        btnOptionThree!!.isEnabled = true
        btnOptionFour!!.isEnabled = true

        return btnOptionOne
    }


    override fun getItemCount(): Int {
        return quizDataList!!.size
    }

    fun getCorrectAnsCount(): Int {
        var count = 0
        for (data in quizDataList!!) {

            if (data!!.correctAns) {
                count++;
            }

        }

        return count
    }

    fun getIncorrectAnsCount(): Int {
        var count = 0
        for (data in quizDataList!!) {

            if (data!!.incorrectAns) {
                count++;
            }
        }

        return count
    }

    fun getSkipAnsCount(): Int {
        var count = 0
        for (data in quizDataList!!) {
            if (!data!!.correctAns && !data.incorrectAns) {
                count++;
            }
        }

        return count
    }

    class UserViewHolder(view:View):RecyclerView.ViewHolder(view) {
        val tvQuestion = view.tv_quiestion
        val ivQuestion = view.iv_question
        val btnOptionOne = view.rb_option_one
        val btnOptionTwo = view.rb_option_two
        val btnOptionThree = view.rb_option_three
        val btnOptionFour = view.rb_option_four
    }
}
