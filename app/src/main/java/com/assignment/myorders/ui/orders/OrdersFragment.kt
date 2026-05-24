package com.assignment.myorders.ui.orders

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.myorders.R
import com.assignment.myorders.databinding.FragmentOrdersBinding
import com.assignment.myorders.model.Order
import com.assignment.myorders.model.OrderStatus

class OrdersFragment : Fragment() {

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: OrdersAdapter

    // Simulated order data with local Udaipur areas
    private val allOrders = mutableListOf(
        Order("1", "Four Wheeler", "05 Feb, 4:46 PM", "#ORD12345",
            "Chetak Circle, Udaipur",
            "Fatehsagar Lake, Rani Rd, Udaipur, Rajasthan 313001, India",
            229.0, OrderStatus.CANCELLED),
        Order("2", "Four Wheeler", "05 Feb, 4:46 PM", "#ORD12346",
            "Surajpole, Udaipur",
            "City Palace, Old City, Udaipur, Rajasthan 313001, India",
            229.0, OrderStatus.CANCELLED),
        Order("3", "Four Wheeler", "05 Feb, 4:46 PM", "#ORD12347",
            "Hiran Magri Sector 4, Udaipur",
            "Badi Lake, Udaipur, Rajasthan 313011, India",
            1515.0, OrderStatus.CANCELLED),
        Order("4", "Four Wheeler", "05 Feb, 4:46 PM", "#ORD12348",
            "Pratap Nagar, Udaipur",
            "Udaipur Railway Station, City Station Rd, Udaipur, Rajasthan 313001, India",
            1634.0, OrderStatus.COMPLETED),
        Order("5", "Four Wheeler", "04 Feb, 2:30 PM", "#ORD12344",
            "Panchwati, Udaipur",
            "Maharana Pratap Airport, Dabok, Rajasthan 313022, India",
            980.0, OrderStatus.COMPLETED),
        Order("6", "Four Wheeler", "03 Feb, 11:00 AM", "#ORD12343",
            "Gulab Bagh, Udaipur",
            "Sajjangarh Monsoon Palace, Udaipur, Rajasthan 313001, India",
            560.0, OrderStatus.BOOKED_AGAIN),
    )

    private var currentTabFilter: OrderStatus? = null  // null = All Orders
    private var currentSearchQuery: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupTabs()
        setupSearch()
        setupClickListeners()
        selectTab(binding.tabAll, null)  // default: All Orders
    }

    private fun setupRecyclerView() {
        adapter = OrdersAdapter(
            onInvoiceClick = { order ->
                Toast.makeText(context,
                    getString(R.string.toast_invoice_downloading) + " ${order.orderId}",
                    Toast.LENGTH_SHORT).show()
            },
            onBookAgainClick = { order ->
                val index = allOrders.indexOfFirst { it.orderId == order.orderId }
                if (index != -1) {
                    allOrders[index] = allOrders[index].copy(status = OrderStatus.BOOKED_AGAIN)
                    applyFilters()
                    Toast.makeText(context,
                        getString(R.string.toast_booking_again) + " ${order.orderId}",
                        Toast.LENGTH_SHORT).show()
                }
            },
            onMenuClick = { order, itemId ->
                when (itemId) {
                    R.id.menu_view_details ->
                        Toast.makeText(context, "Details: ${order.orderId}", Toast.LENGTH_SHORT).show()
                    R.id.menu_share ->
                        Toast.makeText(context, "Share: ${order.orderId}", Toast.LENGTH_SHORT).show()
                    R.id.menu_report ->
                        Toast.makeText(context, "Report: ${order.orderId}", Toast.LENGTH_SHORT).show()
                }
            }
        )

        binding.rvOrders.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@OrdersFragment.adapter
            isNestedScrollingEnabled = false
        }
    }

    private fun setupTabs() {
        binding.tabAll.setOnClickListener { selectTab(it as TextView, null) }
        binding.tabCompleted.setOnClickListener { selectTab(it as TextView, OrderStatus.COMPLETED) }
        binding.tabCancelled.setOnClickListener { selectTab(it as TextView, OrderStatus.CANCELLED) }
        binding.tabBookedAgain.setOnClickListener { selectTab(it as TextView, OrderStatus.BOOKED_AGAIN) }
    }

    private fun selectTab(selectedTab: TextView, filter: OrderStatus?) {
        currentTabFilter = filter

        val tabs = listOf(binding.tabAll, binding.tabCompleted,
            binding.tabCancelled, binding.tabBookedAgain)

        tabs.forEach { tab ->
            if (tab == selectedTab) {
                // Active: yellow pill + bold black text
                tab.setBackgroundResource(R.drawable.bg_tab_active)
                tab.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorOnPrimary))
                tab.paint.isFakeBoldText = true
            } else {
                // Inactive: transparent + secondary text
                tab.setBackgroundResource(R.drawable.bg_tab_inactive)
                tab.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorTextSecondary))
                tab.paint.isFakeBoldText = false
            }
            tab.invalidate()
        }

        applyFilters()
    }

    private fun setupSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                currentSearchQuery = s?.toString()?.trim() ?: ""
                applyFilters()
            }
        })
    }

    private fun applyFilters() {
        var filtered: List<Order> = allOrders.toList()

        // Apply tab filter
        if (currentTabFilter != null) {
            filtered = filtered.filter { it.status == currentTabFilter }
        }

        // Apply search filter
        if (currentSearchQuery.isNotEmpty()) {
            val query = currentSearchQuery.lowercase()
            filtered = filtered.filter { order ->
                order.orderId.lowercase().contains(query) ||
                order.pickupAddress.lowercase().contains(query) ||
                order.dropAddress.lowercase().contains(query)
            }
        }

        adapter.submitList(filtered)

        // Show/hide empty state
        binding.emptyState.visibility = if (filtered.isEmpty()) View.VISIBLE else View.GONE
        binding.rvOrders.visibility = if (filtered.isEmpty()) View.GONE else View.VISIBLE
    }

    private fun setupClickListeners() {
        binding.btnDismissBanner.setOnClickListener {
            binding.infoBanner.visibility = View.GONE
        }

        binding.btnFilter.setOnClickListener {
            Toast.makeText(context, getString(R.string.toast_filter), Toast.LENGTH_SHORT).show()
        }

        binding.btnSort.setOnClickListener {
            Toast.makeText(context, getString(R.string.toast_sort), Toast.LENGTH_SHORT).show()
        }

        binding.helpFab.setOnClickListener {
            Toast.makeText(context, getString(R.string.toast_help), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}