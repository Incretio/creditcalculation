package ru.incretio.creditcalculation;

import android.app.Activity;
import android.os.Bundle;
import androidx.core.internal.view.SupportMenu;
import android.util.Log;
import android.widget.LinearLayout;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import ru.incretio.creditcalculation.logics.CreditGraphicRecord;

public class GraphicActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic);
        if (GlobalData.getCredit() == null || GlobalData.getCredit().getCreditGraphic() == null) {
            Log.i(GlobalData.TAG, "Нет данных для отображения");
        } else {
            ((LinearLayout) findViewById(R.id.llGraphic)).addView(getGraphicView());
        }
    }

    private GraphicalView getGraphicView() {
        XYMultipleSeriesDataset seriesDataset = new XYMultipleSeriesDataset();
        XYMultipleSeriesRenderer xyMultipleSeriesRenderer = new XYMultipleSeriesRenderer();
        xyMultipleSeriesRenderer.setPanEnabled(false);
        xyMultipleSeriesRenderer.setZoomEnabled(false, false);
        xyMultipleSeriesRenderer.setXLabelsColor(-16777216);
        xyMultipleSeriesRenderer.setYLabelsColor(0, -16777216);
        xyMultipleSeriesRenderer.setXLabels(10);
        xyMultipleSeriesRenderer.setYLabels(10);
        xyMultipleSeriesRenderer.setLabelsTextSize(36.0f);
        xyMultipleSeriesRenderer.setYLabelsPadding(-80.0f);
        xyMultipleSeriesRenderer.setXLabelsPadding(-60.0f);
        xyMultipleSeriesRenderer.setYAxisMax(GlobalData.getCredit().getMonthlyPayment() + 1000.0d);
        xyMultipleSeriesRenderer.setLegendTextSize(40.0f);
        xyMultipleSeriesRenderer.setFitLegend(true);
        xyMultipleSeriesRenderer.setShowGrid(true);
        xyMultipleSeriesRenderer.setGridLineWidth(5.0f);
        xyMultipleSeriesRenderer.setMargins(new int[]{0, 0, 70, 0});
        XYSeries xySeriesMonthlyPayment = new XYSeries("Ежемесячный платёж");
        XYSeries xySeriesInterestPayment = new XYSeries("Оплата процентов");
        XYSeries xySeriesPrincipalPayment = new XYSeries("Оплата основного долга");
        seriesDataset.addSeries(xySeriesMonthlyPayment);
        seriesDataset.addSeries(xySeriesInterestPayment);
        seriesDataset.addSeries(xySeriesPrincipalPayment);
        XYSeriesRenderer rendererMonthlyPayment = new XYSeriesRenderer();
        rendererMonthlyPayment.setColor(-7829368);
        rendererMonthlyPayment.setLineWidth(5.0f);
        XYSeriesRenderer rendererInterestPayment = new XYSeriesRenderer();
        rendererInterestPayment.setColor(SupportMenu.CATEGORY_MASK);
        rendererInterestPayment.setLineWidth(5.0f);
        XYSeriesRenderer rendererPrincipalPayment = new XYSeriesRenderer();
        rendererPrincipalPayment.setColor(-16711936);
        rendererPrincipalPayment.setLineWidth(5.0f);
        xyMultipleSeriesRenderer.addSeriesRenderer(rendererMonthlyPayment);
        xyMultipleSeriesRenderer.addSeriesRenderer(rendererInterestPayment);
        xyMultipleSeriesRenderer.addSeriesRenderer(rendererPrincipalPayment);
        for (int curRecord = 0; curRecord < GlobalData.getCredit().getCreditGraphic().size(); curRecord++) {
            CreditGraphicRecord creditGraphicRecord = (CreditGraphicRecord) GlobalData.getCredit().getCreditGraphic().get(curRecord);
            Log.i(GlobalData.TAG, String.valueOf(creditGraphicRecord.getMonthNumber()));
            xySeriesMonthlyPayment.add((double) creditGraphicRecord.getMonthNumber(), creditGraphicRecord.getMonthlyPayment());
            xySeriesInterestPayment.add((double) creditGraphicRecord.getMonthNumber(), creditGraphicRecord.getInterestPayment());
            xySeriesPrincipalPayment.add((double) creditGraphicRecord.getMonthNumber(), creditGraphicRecord.getPrincipalPayment());
        }
        return ChartFactory.getLineChartView(this, seriesDataset, xyMultipleSeriesRenderer);
    }
}
