package com.assignment.myorders.ui.placeholder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.assignment.myorders.R

class PlaceholderFragment : Fragment() {

    companion object {
        private const val ARG_LABEL = "label"

        fun newInstance(label: String): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_LABEL, label)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_placeholder, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val label = arguments?.getString(ARG_LABEL) ?: "Screen"
        val titleView = view.findViewById<TextView>(R.id.tvTitle)
        val container = view.findViewById<LinearLayout>(R.id.dynamicContent)

        titleView.text = label

        when (label) {
            "Home" -> setupHomeView(container)
            "Payments" -> setupPaymentsView(container)
            "Account" -> setupAccountView(container)
        }
    }

    private fun setupHomeView(container: LinearLayout) {
        addSectionTitle(container, getString(R.string.home_greeting))
        
        val statsCard = createCard()
        val statsContent = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            padding(16)
        }
        statsContent.addView(createTextView(getString(R.string.home_stats_title), 18, true))
        statsContent.addView(createTextView(getString(R.string.home_total_orders), 16))
        statsContent.addView(createTextView(getString(R.string.home_total_spent), 16))
        statsCard.addView(statsContent)
        container.addView(statsCard)

        addSpace(container)

        val promoCard = createCard()
        promoCard.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorInfoBannerBg))
        val promoContent = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            padding(16)
        }
        promoContent.addView(createTextView(getString(R.string.home_promo_title), 18, true))
        promoContent.addView(createTextView(getString(R.string.home_promo_desc), 14))
        promoCard.addView(promoContent)
        container.addView(promoCard)
    }

    private fun setupPaymentsView(container: LinearLayout) {
        val walletCard = createCard()
        walletCard.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
        val walletContent = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            padding(24)
        }
        walletContent.addView(createTextView(getString(R.string.payments_wallet_title), 14))
        walletContent.addView(createTextView(getString(R.string.payments_balance), 32, true))
        walletCard.addView(walletContent)
        container.addView(walletCard)

        addSpace(container)
        addSectionTitle(container, getString(R.string.payments_saved_cards))
        container.addView(createItemCard("HDFC Bank **** 1234"))
        container.addView(createItemCard("Google Pay (UPI)"))

        addSpace(container)
        addSectionTitle(container, getString(R.string.payments_recent_title))
        container.addView(createItemCard("Trip to City Palace - ₹229"))
        container.addView(createItemCard("Wallet Top-up - ₹500"))
    }

    private fun setupAccountView(container: LinearLayout) {
        val profileCard = createCard()
        val profileContent = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            padding(16)
        }
        profileContent.addView(createTextView(getString(R.string.account_profile_name), 20, true))
        profileContent.addView(createTextView(getString(R.string.account_profile_phone), 16))
        profileCard.addView(profileContent)
        container.addView(profileCard)

        addSpace(container)
        addSectionTitle(container, getString(R.string.account_settings))
        container.addView(createItemCard("Notifications"))
        container.addView(createItemCard("Privacy Policy"))
        container.addView(createItemCard("Terms of Service"))
        
        addSpace(container)
        val logoutBtn = createItemCard(getString(R.string.account_logout))
        (logoutBtn.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(requireContext(), R.color.colorCancelledText))
        container.addView(logoutBtn)
    }

    private fun addSectionTitle(container: LinearLayout, title: String) {
        container.addView(createTextView(title, 18, true).apply {
            setPadding(0, 16, 0, 8)
        })
    }

    private fun createCard(): CardView {
        return CardView(requireContext()).apply {
            radius = 12f
            cardElevation = 4f
            useCompatPadding = true
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 8, 0, 8)
            }
        }
    }

    private fun createItemCard(text: String): CardView {
        val card = createCard()
        val tv = createTextView(text, 16).apply {
            padding(16)
        }
        card.addView(tv)
        return card
    }

    private fun createTextView(text: String, size: Int, isBold: Boolean = false): TextView {
        return TextView(requireContext()).apply {
            this.text = text
            textSize = size.toFloat()
            if (isBold) {
                setTypeface(null, android.graphics.Typeface.BOLD)
            }
            setTextColor(ContextCompat.getColor(context, R.color.colorTextPrimary))
        }
    }

    private fun addSpace(container: LinearLayout) {
        container.addView(View(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(1, 24)
        })
    }

    private fun View.padding(dp: Int) {
        val px = (dp * resources.displayMetrics.density).toInt()
        setPadding(px, px, px, px)
    }
}
