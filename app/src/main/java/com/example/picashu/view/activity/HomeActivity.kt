package com.example.picashu.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.picashu.R
import com.example.picashu.databinding.ActivityHomeBinding
import com.example.picashu.view.fragment.*
import com.example.picashu.viewModel.HomeActivityViewModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mViewModel: HomeActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel = ViewModelProvider(this).get(HomeActivityViewModel::class.java)

        val count = supportFragmentManager.backStackEntryCount
        Log.d("HomeActivity", " MainActivity backstack count =: $count")

        val pokemonListFragment = PokemonListFragment()
        supportFragmentManager.beginTransaction().add(R.id.main_fragment, pokemonListFragment)
            .commit()

        toolbar = binding.toolbar
        drawerLayout = binding.drawerLayout
        mAuth = FirebaseAuth.getInstance()

        setSupportActionBar(toolbar)
        verifyUserIsLoggedIn()
        UpdateUiWhenCreating()
        configureDrawerLayout()
        configureNavigationView()
    }


    override fun onResume() {
        super.onResume()
        var count = supportFragmentManager.backStackEntryCount
        Log.d("HomeActivity", " MainActivity onResume backstack count =: $count")
    }

    private fun configureDrawerLayout() {
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            0,
            0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun configureNavigationView() {
        val navigationView = findViewById<NavigationView>(R.id.home_activity_nav_view)
        navigationView.setNavigationItemSelectedListener(this)

    }

    override fun onBackPressed() {
        // 5 - Handle back click to close menu
        var count = supportFragmentManager.backStackEntryCount
        Log.d("HomeActivity", " MainActivity onResume backstack count =: $count")

        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun UpdateUiWhenCreating() {
        val currentUid = FirebaseAuth.getInstance().currentUser?.uid
        val autUid = FirebaseAuth.getInstance().uid
        Log.d("HomeActivity", "currentUserUid =: $currentUid et $autUid")
        val navigationView = findViewById<NavigationView>(R.id.home_activity_nav_view)
        val headerContainer: View = navigationView.getHeaderView(0)
        val mImageView = headerContainer.findViewById<ImageView>(R.id.drawer_imageview_profile)
        val mNameText = headerContainer.findViewById<TextView>(R.id.drawer_username)
        val mEmailText = headerContainer.findViewById<TextView>(R.id.drawer_email)

        if (currentUid != null) {
            mViewModel.getUser(currentUid).observe(this, Observer {
                if (it != null) {
                    mNameText.text = it.username
                    mEmailText.text = it.email

                    Log.d("HomeActivity", "currentUser =: $it")

                    Glide.with(this)
                        .load(Uri.parse(it.profileImageUrl))
                        .apply(RequestOptions.circleCropTransform())
                        .into(mImageView)
                }
            })
        }

    }

    private fun verifyUserIsLoggedIn() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid == null) {

            val intent = Intent(this, AppIntroScreenActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)

        } else {

            val query = FirebaseFirestore.getInstance().collection("users").document(uid)
            query.update("online", true)

            val fbquery = FirebaseDatabase.getInstance("https://picashu-20d74-default-rtdb.europe-west1.firebasedatabase.app").getReference("/status/$uid")

            fbquery.setValue("online").addOnSuccessListener {
                Log.d("HomeActivity2", "FirebaseDatabase status currently updated")
            }

            fbquery.onDisconnect().setValue("offline")

        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        val uid = FirebaseAuth.getInstance().currentUser?.uid

        val pokemonCollectionFragment = PokemonCollectionFragment()
        val pokemonListFragment = PokemonListFragment()
        val lastestMsgChatFragment = LastestMsgChatFragment()
        val concludedTradeFragment = ConcludedTradFragment()
        val mainProfilFragment = MainProfilFragment()

        supportFragmentManager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE)

        when (item.itemId) {

            R.id.mon_profile -> {
                supportFragmentManager.beginTransaction()
                    .add(pokemonListFragment,"pokemonListFragment")
                    .addToBackStack("pokemonListFragment")
                    .replace(
                    R.id.main_fragment,
                    mainProfilFragment
                ).commit()
            }

            R.id.log_out -> {
                FirebaseAuth.getInstance().signOut()
                val fbquery = FirebaseDatabase.getInstance("https://picashu-20d74-default-rtdb.europe-west1.firebasedatabase.app").getReference("/status/$uid")
                fbquery.setValue("offline")

                val intent = Intent(this, AppIntroScreenActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }

            R.id.collection -> {
                supportFragmentManager.beginTransaction()
                    .add(pokemonListFragment,"pokemonListFragment")
                    .addToBackStack("pokemonListFragment").replace(
                    R.id.main_fragment,
                    pokemonCollectionFragment
                ).commit()

            }

            R.id.pokecardex -> {
              //  supportFragmentManager.popBackStack()
                supportFragmentManager.beginTransaction()
                    .add(pokemonListFragment,"pokemonListFragment")
                    .addToBackStack("pokemonListFragment")
                    .replace(
                    R.id.main_fragment,
                    pokemonListFragment
                ).commit()
            }

            R.id.chat -> {
                supportFragmentManager.beginTransaction()
                    .add(pokemonListFragment,"pokemonListFragment")
                    .addToBackStack("pokemonListFragment")
                    .replace(
                    R.id.main_fragment,
                    lastestMsgChatFragment
                ).commit()
            }

            R.id.mes_echanges -> {

                supportFragmentManager.beginTransaction()
                    .add(pokemonListFragment,"pokemonListFragment")
                    .addToBackStack("pokemonListFragment")
                    .replace(
                    R.id.main_fragment,
                    concludedTradeFragment
                ).commit()
            }
        }
        return true

    }


}