package com.slapstick.chyme.ui

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.slapstick.chyme.R
import com.slapstick.chyme.databinding.FragmentMeditateBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeditateFragment : Fragment() {

    private var _binding: FragmentMeditateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMeditateBinding.inflate(inflater, container, false)

        binding.clScreenOne.animate().alpha(1F).duration = 1500L

        binding.btnEnter.setOnClickListener {
            entryAnimations()
        }

        binding.visualSwitch.setOnCheckedChangeListener { _, _ ->
            binding.kbvKenBurnsView.setImageResource(R.drawable.black)
        }

        binding.btnStartMeditation.setOnClickListener {
            startMeditation()
        }

        binding.timer.setOnChronometerTickListener {
            if (binding.timer.text == "00:01") {
                endMeditation()
                binding.timer.text = getString(R.string.default_timer)
            }
        }

        binding.btnEndMeditation.setOnClickListener {
            endMeditation()
        }

        return binding.root
    }

    private fun entryAnimations() {
        binding.btnEnter.isClickable = false
        binding.clScreenOne.animate()
            .translationYBy(-500F)
            .duration = 1500L
        binding.btnEnter.animate()
            .alpha(0F)
            .duration = 1000L
        binding.clScreenTwo.animate()
            .translationY(200F)
            .alpha(1F)
            .duration = 1500L
    }

    private fun startMeditation() {
        if (binding.visualSwitch.isChecked) { selectImage() }

        binding.clScreenOne.animate()
            .alpha(0F)
            .translationXBy(-1200F)
            .duration = 600L
        binding.clScreenTwo.animate()
            .alpha(0F)
            .translationXBy(-1200F)
            .duration = 600L
        binding.clScreenThree.animate()
            .translationX(0F)
            .duration = 600L

        binding.timer.base = SystemClock.elapsedRealtime() + (selectDuration() * 60000 + 0 * 1000)
        binding.timer.start()
    }

    private fun selectDuration(): Long {
        when (binding.durationSpinner.selectedItem.toString()) {
            "5 Minutes" -> return 5L
            "10 Minutes" -> return 10L
            "15 Minutes" -> return 15L
            "20 Minutes" -> return 20L
            "25 Minutes" -> return 25L
            "30 Minutes" -> return 30L
        }
        return 0
    }

    private fun selectImage() {
        when (binding.soundSpinner.selectedItem.toString()) {
            "Wind Chimes" -> binding.kbvKenBurnsView.setImageResource(R.drawable.chimes)
            "Oceanfront" -> binding.kbvKenBurnsView.setImageResource(R.drawable.beach)
            "Forest Canopy" -> binding.kbvKenBurnsView.setImageResource(R.drawable.forest)
            "Deep Space" -> binding.kbvKenBurnsView.setImageResource(R.drawable.nebula)
            "Rainstorm" -> binding.kbvKenBurnsView.setImageResource(R.drawable.rain)
            "White Noise" -> binding.kbvKenBurnsView.setImageResource(R.drawable.paper)
        }
    }

    private fun endMeditation() {
        binding.timer.stop()
        binding.clScreenOne.animate()
            .alpha(1F)
            .translationXBy(1200F)
            .duration = 600L
        binding.clScreenTwo.animate()
            .alpha(1F)
            .translationXBy(1200F)
            .duration = 600L
        binding.clScreenThree.animate()
            .translationXBy(1200F)
            .duration = 600L
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}