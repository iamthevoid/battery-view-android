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
        const val INITIAL_RED2 = 0
        const val INITIAL_BLUE2 = 0
        const val INITIAL_GREEN2 = 0
    }

    private var currentRed: Int = INITIAL_RED
    private var currentGreen: Int = INITIAL_GREEN
    private var currentBlue: Int = INITIAL_BLUE
    private var currentRed2: Int = INITIAL_RED2
    private var currentGreen2: Int = INITIAL_GREEN2
    private var currentBlue2: Int = INITIAL_BLUE2

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
    private lateinit var redSeek2: SeekBar
    private lateinit var redLabel2: TextView
    private lateinit var greenSeek2: SeekBar
    private lateinit var greenLabel2: TextView
    private lateinit var blueSeek2: SeekBar
    private lateinit var blueLabel2: TextView
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
        redSeek2 = findViewById(R.id.red_seek2)
        redLabel2 = findViewById(R.id.red_label2)
        greenSeek2 = findViewById(R.id.green_seek2)
        greenLabel2 = findViewById(R.id.green_label2)
        blueSeek2 = findViewById(R.id.blue_seek2)
        blueLabel2 = findViewById(R.id.blue_label2)
        chargeSeek = findViewById(R.id.charge_seek)
        chargeLabel = findViewById(R.id.charge_label)
        chargedCb = findViewById(R.id.is_charging)
        initViews()
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        batteryView.apply {
            infillcolor = calculateColor()
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
                infillcolor = calculateColor()
                redLabel.text = "Red: $currentRed"
            }
        }

        greenLabel.text = "Green: $currentGreen"
        greenSeek.apply {
            configureColor(currentGreen)
            seekListener {
                currentGreen = it
                infillcolor = calculateColor()
                greenLabel.text = "Green: $currentGreen"
            }
        }

        blueLabel.text = "Blue: $currentBlue"
        blueSeek.apply {
            configureColor(currentBlue)
            seekListener {
                currentBlue = it
                infillcolor = calculateColor()
                blueLabel.text = "Blue: $currentBlue"
            }
        }

        redLabel2.text = "Red: $currentRed2"
        redSeek2.apply {
            configureColor(currentRed2)
            seekListener {
                currentRed2 = it
                bordercolor = calculateColor2()
                redLabel2.text = "Red: $currentRed2"
            }
        }

        greenLabel2.text = "Green: $currentGreen2"
        greenSeek2.apply {
            configureColor(currentGreen2)
            seekListener {
                currentGreen2 = it
                bordercolor = calculateColor2()
                greenLabel2.text = "Green: $currentGreen2"
            }
        }

        blueLabel2.text = "Blue: $currentBlue2"
        blueSeek2.apply {
            configureColor(currentBlue2)
            seekListener {
                currentBlue2 = it
                bordercolor = calculateColor2()
                blueLabel2.text = "Blue: $currentBlue2"
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

    private fun calculateColor2() : Int {
        return (currentRed2 * 0x10000 + currentGreen2 * 0x100 + currentBlue2 + 0xFF000000).toInt()
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