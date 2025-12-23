package dev.bokov.mpchartgradient

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Utils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Utils.init(this)

        val chart: LineChart = findViewById(R.id.chart)

        // Minimal chart setup
        chart.description.isEnabled = false
        chart.legend.isEnabled = false
        chart.axisRight.isEnabled = false
        chart.setDrawGridBackground(false)

        // -----------------------------
        // Linear function
        // y = k * x
        // -----------------------------
        val entries = ArrayList<Entry>(200)
        val k = 2.5f

        for (i in 0 until 200) {
            entries.add(Entry(i.toFloat(), i * k))
        }

        val dataSet = LineDataSet(entries, "Linear").apply {
            setDrawValues(false)
            setDrawCircles(false)
            lineWidth = 2f

            setDrawFilled(true)
            fillAlpha = 255
            fillDrawable = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.gradient_drawable_precipitation
            )

            fillFormatter = object : IFillFormatter {
                override fun getFillLinePosition(
                    dataSet: ILineDataSet?,
                    dataProvider: LineDataProvider?
                ): Float = chart.axisLeft.axisMinimum
            }
        }

        chart.axisLeft.axisMinimum = 0f
        chart.data = LineData(dataSet)
        chart.invalidate()
    }
}