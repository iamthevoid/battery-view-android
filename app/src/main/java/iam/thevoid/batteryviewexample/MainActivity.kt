package iam.thevoid.batteryviewexample

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.CheckBox
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import iam.thevoid.ae.screenWidth
import iam.thevoid.batteryview.BatteryView

class MainActivity : AppCompatActivity() {

    companion object {
        const val INITIAL_CHARGE = 47
        const val INITIAL_WIDTH = 96
        const val INITIAL_HEIGHT = 96
        const val INITIAL_RED = 16
        const val INITIAL_BLUE = 16
        const val INITIAL_GREEN = 16
        const val BORDER_INITIAL_RED = 0
        const val BORDER_INITIAL_BLUE = 0
        const val BORDER_INITIAL_GREEN = 0
    }

    private var currentRed: Int = INITIAL_RED
    private var currentGreen: Int = INITIAL_GREEN
    private var currentBlue: Int = INITIAL_BLUE
    private var borderCurrentRed: Int = BORDER_INITIAL_RED
    private var borderCurrentGreen: Int = BORDER_INITIAL_GREEN
    private var borderCurrentBlue: Int = BORDER_INITIAL_BLUE

    private lateinit var batteryView: BatteryView
    private lateinit var widthSeek: SeekBar
    private lateinit var widthLabel: TextView
    private lateinit var heightSeek: SeekBar
    private lateinit var heightLabel: TextView
    private lateinit var redSeek: SeekBar
    private lateinit var redLabel: TextView
    private lateinit var greenSeek: SeekBar
    private lateinit var greenLabel: TextView
    private lateinit var blueSeek: SeekBar
    private lateinit var blueLabel: TextView
    private lateinit var borderRedSeek: SeekBar
    private lateinit var borderRedLabel: TextView
    private lateinit var borderGreenSeek: SeekBar
    private lateinit var borderGreenLabel: TextView
    private lateinit var borderBlueSeek: SeekBar
    private lateinit var borderBlueLabel: TextView
    private lateinit var chargeSeek: SeekBar
    private lateinit var chargeLabel: TextView
    private lateinit var chargedCb: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        batteryView = findViewById(R.id.battery_view)
        widthLabel = findViewById(R.id.width_label)
        widthSeek = findViewById(R.id.width_seek)
        heightLabel = findViewById(R.id.heigth_label)
        heightSeek = findViewById(R.id.height_seek)
        redSeek = findViewById(R.id.red_seek)
        redLabel = findViewById(R.id.red_label)
        greenSeek = findViewById(R.id.green_seek)
        greenLabel = findViewById(R.id.green_label)
        blueSeek = findViewById(R.id.blue_seek)
        blueLabel = findViewById(R.id.blue_label)
        borderRedSeek = findViewById(R.id.border_red_seek)
        borderRedLabel = findViewById(R.id.border_red_label)
        borderGreenSeek = findViewById(R.id.border_green_seek)
        borderGreenLabel = findViewById(R.id.border_green_label)
        borderBlueSeek = findViewById(R.id.border_blue_seek)
        borderBlueLabel = findViewById(R.id.border_blue_label)
        chargeSeek = findViewById(R.id.charge_seek)
        chargeLabel = findViewById(R.id.charge_label)
        chargedCb = findViewById(R.id.is_charging)
        initViews()
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        batteryView.apply {
            infillColor = calculateColor()
            batteryLevel = INITIAL_CHARGE
            updateLayoutParams {
                width = INITIAL_WIDTH
                height = INITIAL_HEIGHT
            }
        }

        redLabel.text = "Red: $currentRed"
        redSeek.apply {
            configureColor(currentRed)
            seekListener {
                currentRed = it
                infillColor = calculateColor()
                redLabel.text = "Red: $currentRed"
            }
        }

        greenLabel.text = "Green: $currentGreen"
        greenSeek.apply {
            configureColor(currentGreen)
            seekListener {
                currentGreen = it
                infillColor = calculateColor()
                greenLabel.text = "Green: $currentGreen"
            }
        }

        blueLabel.text = "Blue: $currentBlue"
        blueSeek.apply {
            configureColor(currentBlue)
            seekListener {
                currentBlue = it
                infillColor = calculateColor()
                blueLabel.text = "Blue: $currentBlue"
            }
        }

        borderRedLabel.text = "Red: $borderCurrentRed"
        borderRedSeek.apply {
            configureColor(borderCurrentRed)
            seekListener {
                borderCurrentRed = it
                borderColor = calculateBorderColor()
                borderRedLabel.text = "Red: $borderCurrentRed"
            }
        }

        borderGreenLabel.text = "Green: $borderCurrentGreen"
        borderGreenSeek.apply {
            configureColor(borderCurrentGreen)
            seekListener {
                borderCurrentGreen = it
                borderColor = calculateBorderColor()
                borderGreenLabel.text = "Green: $borderCurrentGreen"
            }
        }

        borderBlueLabel.text = "Blue: $borderCurrentBlue"
        borderBlueSeek.apply {
            configureColor(borderCurrentBlue)
            seekListener {
                borderCurrentBlue = it
                borderColor = calculateBorderColor()
                borderBlueLabel.text = "Blue: $borderCurrentBlue"
            }
        }

        widthLabel.text = "Width: $INITIAL_WIDTH"
        widthSeek.apply {
            configureSize(INITIAL_WIDTH)
            seekListener {
                updateLayoutParams { width = it }
                widthLabel.text = "Width: $it"
            }
        }

        heightLabel.text = "Height: $INITIAL_HEIGHT"
        heightSeek.apply {
            configureSize(INITIAL_HEIGHT)
            seekListener {
                updateLayoutParams { height = it }
                heightLabel.text = "Height: $it"
            }
        }

        chargeLabel.text = "Level: $INITIAL_CHARGE"
        chargeSeek.apply {
            max = 100
            seekListener {
                batteryView.batteryLevel = it
                chargeLabel.text = "Level: $it"
            }
        }

        chargedCb.setOnCheckedChangeListener { _, isChecked ->
            batteryView.isCharging = isChecked
        }
    }

    private fun SeekBar.seekListener(change: BatteryView.(progress: Int) -> Unit) {
        setOnSeekBarChangeListener(object : OnSeekBarChangeListenerAdapter() {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                super.onProgressChanged(seekBar, progress, fromUser)
                batteryView.change(progress)
            }
        })
    }

    private fun calculateColor() : Int {
        return (currentRed * 0x10000 + currentGreen * 0x100 + currentBlue + 0xFF000000).toInt()
    }

    private fun calculateBorderColor() : Int {
        return (borderCurrentRed * 0x10000 + borderCurrentGreen * 0x100 + borderCurrentBlue + 0xFF000000).toInt()
    }

    private fun SeekBar.configureColor(current: Int) =
        configure(255, current)

    private fun SeekBar.configureSize(current: Int) =
        configure(screenWidth, current)

    private fun SeekBar.configure(max: Int, current: Int) {
        this.max = max
        this.progress = current
    }
}