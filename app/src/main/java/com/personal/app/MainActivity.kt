package com.personal.app

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.graphics.Color
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.personal.app.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val chatViewModel: ChatViewModel by viewModels()
    private val weatherViewModel: MainViewModel by viewModels()
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    private var timeUpdateJob: android.os.Handler? = null
    private val timeUpdateRunnable = object : Runnable {
        override fun run() {
            updateTime()
            timeUpdateJob?.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupObservers()
        updateTime()
        loadInitialChatMessage()
    }

    private fun setupViews() {
        // Terminal-style chat view
        binding.chatMessagesTextView.setBackgroundColor(Color.parseColor("#1E1E1E"))
        binding.chatMessagesTextView.setTextColor(Color.parseColor("#00FF00"))

        // Weather/Time icon click
        binding.weatherTimeIcon.setOnClickListener {
            startActivity(Intent(this, WeatherTimeActivity::class.java))
        }
        
        // Weather/Time text click
        binding.weatherTimeCard.setOnClickListener {
            startActivity(Intent(this, WeatherTimeActivity::class.java))
        }

        // Send button
        binding.sendButton.setOnClickListener {
            val message = binding.messageEditText.text.toString().trim()
            if (message.isNotEmpty()) {
                sendMessage(message)
                binding.messageEditText.text?.clear()
            }
        }

        // Enter key sends message
        binding.messageEditText.setOnEditorActionListener { _, _, _ ->
            val message = binding.messageEditText.text.toString().trim()
            if (message.isNotEmpty()) {
                sendMessage(message)
                binding.messageEditText.text?.clear()
            }
            true
        }

        // Project buttons
        binding.project1Button.setOnClickListener {
            showProjectMessage("Project 1")
        }
        binding.project2Button.setOnClickListener {
            showProjectMessage("Project 2")
        }
        binding.project3Button.setOnClickListener {
            showProjectMessage("Project 3")
        }
        binding.project4Button.setOnClickListener {
            showProjectMessage("Project 4")
        }
    }

    private fun setupObservers() {
        chatViewModel.chatResponse.observe(this) { response ->
            response?.let {
                appendToChat("assistant", it)
            }
        }

        chatViewModel.errorMessage.observe(this) { error ->
            error?.let {
                appendToChat("system", "\u001B[91mERROR:\u001B[0m $it")
            }
        }

        chatViewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                appendToChat("system", "\u001B[93mProcessing request...\u001B[0m")
            }
        }

        weatherViewModel.weatherData.observe(this) { weather ->
            weather?.let {
                val temp = it.temperature.toInt()
                binding.weatherTimeText.text = "${timeFormat.format(Date())} | ${temp}°C"
            } ?: run {
                binding.weatherTimeText.text = timeFormat.format(Date())
            }
        }
    }

    private fun updateTime() {
        weatherViewModel.weatherData.value?.let {
            val temp = it.temperature.toInt()
            binding.weatherTimeText.text = "${timeFormat.format(Date())} | ${temp}°C"
        } ?: run {
            binding.weatherTimeText.text = timeFormat.format(Date())
        }
    }

    private fun loadInitialChatMessage() {
        appendToChat("system", "╔════════════════════════════════════════╗")
        appendToChat("system", "║   PERSONAL ASSISTANT TERMINAL v1.0    ║")
        appendToChat("system", "╚════════════════════════════════════════╝")
        appendToChat("system", "")
        appendToChat("assistant", "Hi Anand, how can I help you?")
        // Try to fetch weather for the display
        weatherViewModel.fetchWeather(28.41, 77.04) // Default location
    }

    private fun sendMessage(message: String) {
        appendToChat("You", message)
        chatViewModel.sendMessage(message)
    }

    private fun appendToChat(sender: String, message: String) {
        val terminal = binding.chatMessagesTextView
        val prompt = when (sender.lowercase()) {
            "you" -> "\u001B[36manand@personal-app\u001B[0m:\u001B[94m~\u001B[0m$ "
            "assistant" -> "\u001B[32massistant\u001B[0m> "
            "system" -> "\u001B[33m[SYSTEM]\u001B[0m "
            else -> "\u001B[37m[$sender]\u001B[0m "
        }
        
        val formattedMessage = when (sender.lowercase()) {
            "you" -> "$prompt\u001B[97m$message\u001B[0m"
            "assistant" -> "$prompt\u001B[92m$message\u001B[0m"
            "system" -> "\u001B[33m$message\u001B[0m"
            else -> "$prompt\u001B[37m$message\u001B[0m"
        }
        
        terminal.appendTerminal(formattedMessage)
        
        // Scroll to bottom
        binding.chatMessagesTextView.post {
            val scrollAmount = binding.chatMessagesTextView.layout?.getLineTop(
                binding.chatMessagesTextView.lineCount
            ) ?: 0 - binding.chatMessagesTextView.height
            binding.chatMessagesTextView.scrollTo(0, scrollAmount.coerceAtLeast(0))
        }
    }

    private fun showProjectMessage(projectName: String) {
        appendToChat("system", "┌─ Executing: $projectName")
        appendToChat("system", "├─ Status: Initializing...")
        appendToChat("system", "├─ Interface: Command-line UI (v1.0)")
        appendToChat("system", "└─ Note: Full interface implementation coming soon")
        appendToChat("system", "")
    }

    override fun onResume() {
        super.onResume()
        timeUpdateJob = android.os.Handler(android.os.Looper.getMainLooper())
        timeUpdateJob?.post(timeUpdateRunnable)
        // Try to refresh weather
        weatherViewModel.weatherData.value?.let {
            // Already have weather data
        } ?: run {
            weatherViewModel.fetchWeather(28.41, 77.04)
        }
    }

    override fun onPause() {
        super.onPause()
        timeUpdateJob?.removeCallbacks(timeUpdateRunnable)
        timeUpdateJob = null
    }
}

