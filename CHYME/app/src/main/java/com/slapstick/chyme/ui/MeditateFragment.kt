package com.slapstick.chyme.ui

import android.media.MediaPlayer
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

    private var mediaPlayer: MediaPlayer? = MediaPlayer()

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
            .translationYBy(-600F)
            .duration = 1500L
        binding.btnEnter.animate()
            .alpha(0F)
            .duration = 1000L
        binding.clScreenTwo.animate()
            .translationY(-500F)
            .alpha(1F)
            .duration = 1500L
    }

    private fun startMeditation() {
        if (binding.visualSwitch.isChecked) { selectImage() }

        binding.clScreenOne.animate()
            .alpha(0F)
            .translationYBy(-1000F)
            .duration = 800L
        binding.clScreenTwo.animate()
            .alpha(0F)
            .translationYBy(-1000F)
            .duration = 800L
        binding.clScreenThree.animate()
            .alpha(1F)
            .translationY(0F)
            .duration = 800L

        binding.timer.base = SystemClock.elapsedRealtime() + (selectDuration()) * 60000 + 0 * 1000
        binding.timer.start()
        selectSound()
    }

    private fun endMeditation() {
        binding.timer.stop()
        stopSoundFile()

        binding.clScreenOne.animate()
            .alpha(1F)
            .translationYBy(1000F)
            .duration = 800L
        binding.clScreenTwo.animate()
            .alpha(1F)
            .translationYBy(1000F)
            .duration = 800L
        binding.clScreenThree.animate()
            .alpha(0F)
            .translationY(2000F)
            .duration = 800L
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
        }
    }

    private fun selectSound() {
        when (binding.soundSpinner.selectedItem.toString()) {
            "Wind Chimes" -> playSoundFile(R.raw.wind_chimes)
            "Oceanfront" -> playSoundFile(R.raw.ocean)
            "Forest Canopy" -> playSoundFile(R.raw.forest)
            "Deep Space" -> playSoundFile(R.raw.deep_space)
            "Rainstorm" -> playSoundFile(R.raw.rain_storm)
        }
    }

    private fun playSoundFile(fileName: Int) {
        mediaPlayer = MediaPlayer.create(context, fileName)
        mediaPlayer?.start()
        mediaPlayer?.isLooping = true
    }

    private fun stopSoundFile() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    @Override
    override fun onStop() {
        super.onStop()
        stopSoundFile()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        stopSoundFile()
        _binding = null
    }

}