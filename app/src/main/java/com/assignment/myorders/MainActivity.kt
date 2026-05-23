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
            val fragment: Fragment = when (item.itemId) {
                R.id.nav_home -> homeFragment
                R.id.nav_orders -> ordersFragment
                R.id.nav_payments -> paymentsFragment
                R.id.nav_account -> accountFragment
                else -> return@setOnItemSelectedListener false
            }
            switchFragment(fragment)
            true
        }
    }

    private fun switchFragment(fragment: Fragment) {
        val tag = fragment::class.java.simpleName
        val existing = supportFragmentManager.findFragmentByTag(tag)

        supportFragmentManager.beginTransaction().apply {
            // Hide all current fragments
            supportFragmentManager.fragments.forEach { hide(it) }

            if (existing != null) {
                show(existing)
            } else {
                add(R.id.fragmentContainer, fragment, tag)
            }
            commit()
        }
    }
}