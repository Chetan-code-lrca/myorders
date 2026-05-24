package com.assignment.myorders

import android.os.Bundle
import androidx.core.view.WindowInsetsControllerCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.assignment.myorders.databinding.ActivityMainBinding
import com.assignment.myorders.ui.orders.OrdersFragment
import com.assignment.myorders.ui.placeholder.PlaceholderFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Cache fragment instances to preserve scroll state on tab switch
    private val homeFragment by lazy { PlaceholderFragment.newInstance("Home") }
    private val ordersFragment by lazy { OrdersFragment() }
    private val paymentsFragment by lazy { PlaceholderFragment.newInstance("Payments") }
    private val accountFragment by lazy { PlaceholderFragment.newInstance("Account") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Make status bar color match header (yellow)
        window.statusBarColor = getColor(R.color.colorPrimary)
        // Use dark icons on yellow status bar
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true

        setupBottomNavigation()

        // Default to Orders tab on launch
        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.nav_orders
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            val (fragment, tag) = when (item.itemId) {
                R.id.nav_home -> homeFragment to "Home"
                R.id.nav_orders -> ordersFragment to "Orders"
                R.id.nav_payments -> paymentsFragment to "Payments"
                R.id.nav_account -> accountFragment to "Account"
                else -> return@setOnItemSelectedListener false
            }
            switchFragment(fragment, tag)
            true
        }
    }

    private fun switchFragment(fragment: Fragment, tag: String) {
        val transaction = supportFragmentManager.beginTransaction()

        // Hide all fragments currently in the manager
        supportFragmentManager.fragments.forEach { transaction.hide(it) }

        val existing = supportFragmentManager.findFragmentByTag(tag)
        if (existing != null) {
            transaction.show(existing)
        } else {
            transaction.add(R.id.fragmentContainer, fragment, tag)
        }
        transaction.commit()
    }
}