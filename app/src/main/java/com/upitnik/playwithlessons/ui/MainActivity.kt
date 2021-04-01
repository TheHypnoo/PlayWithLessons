package com.upitnik.playwithlessons.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.upitnik.playwithlessons.BuildConfig
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.databinding.ActivityMainBinding
import com.upitnik.playwithlessons.services.Firebase
import com.upitnik.playwithlessons.ui.question.AnswerData
import com.upitnik.playwithlessons.ui.question.QuestionProvider

class MainActivity : AppCompatActivity() {
    companion object {
        const val VERSION_CODE = BuildConfig.VERSION_CODE
        const val VERSION_NAME = BuildConfig.VERSION_NAME
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var Firebase: Firebase = Firebase()
    private val questions = QuestionProvider.getQuestions()
    private var count: Int = 0
    private var questionPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.includeAppbar.toolbar)
        val navController = findNavController(R.id.nav_host_fragment)
        /*
        if(!prueba) {
            val navGraph = navController.graph
            navGraph.startDestination = R.id.nav_register
            navController.navigate(R.id.nav_register)
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {*/
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_login,
                R.id.nav_register,
                R.id.nav_home,
                R.id.nav_gallery,
                R.id.nav_slideshow,
                R.id.nav_question,
                R.id.nav_logout
            ), binding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
        //}

        /*  binding.navView.setNavigationItemSelectedListener {
              when (it.itemId) {

                  R.id.nav_home-> {
                      navController.navigate(R.id.nav_home)
                      return@setNavigationItemSelectedListener true
                  }

                  R.id.nav_question -> {
                      navController.navigate(R.id.nav_question)
                      return@setNavigationItemSelectedListener true
                  }

                  R.id.nav_gallery -> {
                      navController.navigate(R.id.nav_gallery)
                      return@setNavigationItemSelectedListener true
                  }
                  R.id.nav_logout -> {
                      //Temporal
                      val builder = AlertDialog.Builder(this)
                      builder.setMessage("Estas seguro que deseas Cerrar sesión?")

                      builder.setPositiveButton(android.R.string.ok) { _, _ ->
                          Firebase.signOut()
                          startActivity(
                              Intent(
                                  this,
                                  LoadingActivity::class.java
                              )
                          )
                          finish()
                      }

                      builder.setNegativeButton(android.R.string.cancel) { dialog, _ ->
                          dialog.dismiss()
                          it.isChecked = false
                      }

                      builder.show()
                      return@setNavigationItemSelectedListener true
                  }
                  else -> false
              }

          }
  */

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //showNewQuestion(questions.first())
    }

    /*
        override fun onCreateOptionsMenu(menu: Menu): Boolean {
            // Inflate the menu; this adds items to the action bar if it is present.
            menuInflater.inflate(R.menu.main_activity, menu)
            return true
        }
    */
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun updateSteps() {
        //binding.tvSteps.text = " ${questionPosition+1} de ${questions.size}"
    }

    private fun setPoints() {
        /*
        count += 1
        binding.tvPoints.text = "$count puntos"*/
    }

    private fun nextQuestion() {
        questionPosition += 1
        if (questions.size <= questionPosition) {
            goToResult()
        } else {
            //showNewQuestion(questions[questionPosition])
        }
    }

    private fun goToResult() {
        println("Terminado")
    }
/*
    private fun showNewQuestion(questionData: QuestionData) {
        updateSteps()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
        fragmentTransaction.replace(R.id.container, QuestionFragment.newInstance(questionData))
        fragmentTransaction.commit()
    }*/

    private fun updateView(answer: AnswerData) {
        if (answer.isCorrect) {
            setPoints()
        }
        nextQuestion()
    }
}