package com.assignment.myorders.ui.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.assignment.myorders.R
import com.assignment.myorders.databinding.ItemOrderBinding
import com.assignment.myorders.model.Order
import com.assignment.myorders.model.OrderStatus

class OrdersAdapter(
    private val onInvoiceClick: (Order) -> Unit,
    private val onBookAgainClick: (Order) -> Unit,
    private val onMenuClick: (Order, Int) -> Unit
) : ListAdapter<Order, OrdersAdapter.OrderViewHolder>(OrderDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class OrderViewHolder(
        private val binding: ItemOrderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(order: Order) = with(binding) {
            tvVehicleType.text = order.vehicleType
            tvDateOrderId.text = "${order.date}  |  Order ID: ${order.orderId}"
            tvPickupAddress.text = order.pickupAddress
            tvDropAddress.text = order.dropAddress
            tvPrice.text = "₹ ${order.amount}"

            // Set vehicle icon
            val iconRes = if (order.vehicleType.contains("Two", ignoreCase = true)) {
                R.drawable.ic_scooter
            } else {
                R.drawable.ic_truck
            }
            ivVehicle.setImageResource(iconRes)

            applyStatusStyle(order.status)

            // Invoice click
            btnInvoice.setOnClickListener { onInvoiceClick(order) }

            // Book Again click
            btnBookAgain.setOnClickListener { onBookAgainClick(order) }

            // 3-dot menu click
            btnMore.setOnClickListener { view ->
                val popup = PopupMenu(view.context, view)
                popup.menuInflater.inflate(R.menu.order_popup_menu, popup.menu)
                popup.setOnMenuItemClickListener { menuItem ->
                    onMenuClick(order, menuItem.itemId)
                    true
                }
                popup.show()
            }
        }

        private fun applyStatusStyle(status: OrderStatus) = with(binding) {
            val ctx = root.context
            when (status) {
                OrderStatus.CANCELLED -> {
                    tvStatus.text = ctx.getString(R.string.status_cancelled)
                    tvStatus.setTextColor(ctx.getColor(R.color.colorCancelledText))
                    tvStatus.setBackgroundResource(R.drawable.bg_status_cancelled)
                }
                OrderStatus.COMPLETED -> {
                    tvStatus.text = ctx.getString(R.string.status_completed)
                    tvStatus.setTextColor(ctx.getColor(R.color.colorCompletedText))
                    tvStatus.setBackgroundResource(R.drawable.bg_status_completed)
                }
                OrderStatus.BOOKED_AGAIN -> {
                    tvStatus.text = ctx.getString(R.string.status_booked_again)
                    tvStatus.setTextColor(ctx.getColor(R.color.colorBookedAgainText))
                    tvStatus.setBackgroundResource(R.drawable.bg_status_completed)
                }
            }
        }
    }

    private class OrderDiffCallback : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Order, newItem: Order) =
            oldItem == newItem
    }
}