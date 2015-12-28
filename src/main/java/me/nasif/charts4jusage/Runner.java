package me.nasif.charts4jusage;

import com.googlecode.charts4j.*;
import static com.googlecode.charts4j.Color.*;
import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class Runner {

    public static void main(String[] args) {

        saveImage(getChartUrl(generateAreaChart()), "Area");
        saveImage(getChartUrl(generateHorizontalBarChart()), "Bar");
        saveImage(getChartUrl(generateLineChart()), "Line");

    }

    public static AbstractGraphChart generateAreaChart() {

        final double[] company1Sales = {90, 95, 85, 98};
        final double[] company2Sales = {80, 75, 85, 90};
        final double[] company3Sales = {70, 75, 65, 75};
        final double[] aciSales = {50, 40, 45, 55};

        for (int i = 0; i < 4; i++) {
            company2Sales[i] += company1Sales[i];
            company3Sales[i] += company2Sales[i];
            aciSales[i] += company3Sales[i];
        }

        Line company1Line = Plots.newLine(DataUtil.scaleWithinRange(0, 1500, company1Sales), YELLOW, "S & P 500");
        company1Line.setLineStyle(LineStyle.newLineStyle(3, 1, 0));
        company1Line.addShapeMarkers(Shape.CIRCLE, YELLOW, 10);
        company1Line.addShapeMarkers(Shape.CIRCLE, BLACK, 7);
        company1Line.setFillAreaColor(YELLOW);

        Line company2Line = Plots.newLine(DataUtil.scaleWithinRange(0, 1500, company2Sales), GREEN, "Inflation");
        company2Line.setLineStyle(LineStyle.newLineStyle(3, 1, 0));
        company2Line.addShapeMarkers(Shape.CIRCLE, LIME, 10);
        company2Line.addShapeMarkers(Shape.CIRCLE, BLACK, 7);
        company2Line.setFillAreaColor(GREEN);

        Line company3Line = Plots.newLine(DataUtil.scaleWithinRange(0, 1500, company3Sales), RED, "S & P 500");
        company3Line.setLineStyle(LineStyle.newLineStyle(3, 1, 0));
        company3Line.addShapeMarkers(Shape.CIRCLE, YELLOW, 10);
        company3Line.addShapeMarkers(Shape.CIRCLE, BLACK, 7);
        company3Line.setFillAreaColor(RED);

        Line aciLine = Plots.newLine(DataUtil.scaleWithinRange(0, 1500, aciSales), BLUE, "Inflation");
        aciLine.setLineStyle(LineStyle.newLineStyle(3, 1, 0));
        aciLine.addShapeMarkers(Shape.CIRCLE, LIME, 10);
        aciLine.addShapeMarkers(Shape.CIRCLE, BLACK, 7);
        aciLine.setFillAreaColor(BLUE);

        // Defining chart.
        LineChart chart = GCharts.newLineChart(aciLine, company3Line, company2Line, company1Line);
        chart.setSize(600, 450);
        chart.setTitle("S & P 500|1962 - 2008", WHITE, 14);

        // Defining axis info and styles
        AxisStyle axisStyle = AxisStyle.newAxisStyle(WHITE, 12, AxisTextAlignment.CENTER);
        AxisLabels yAxis = AxisLabelsFactory.newNumericRangeAxisLabels(0, company1Sales[company1Sales.length - 1]);
        yAxis.setAxisStyle(axisStyle);
        AxisLabels xAxis1 = AxisLabelsFactory.newAxisLabels(Arrays.asList("6", "3", "12", "9"), Arrays.asList(0, 33, 66, 100));
        xAxis1.setAxisStyle(axisStyle);

        AxisLabels xAxis2 = AxisLabelsFactory.newAxisLabels(Arrays.asList("2015", "2015", "2014", "2014"), Arrays.asList(0, 33, 66, 100));
        xAxis2.setAxisStyle(axisStyle);
        AxisLabels xAxis3 = AxisLabelsFactory.newAxisLabels("Year", 50.0);
        xAxis3.setAxisStyle(AxisStyle.newAxisStyle(WHITE, 14, AxisTextAlignment.CENTER));

        // Adding axis info to chart.
        chart.addYAxisLabels(yAxis);
        chart.addXAxisLabels(xAxis1);
        chart.addXAxisLabels(xAxis2);
        chart.addXAxisLabels(xAxis3);
        chart.setGrid(100, 6.78, 5, 0);

        // Defining background and chart fills.
        chart.setBackgroundFill(Fills.newSolidFill(BLACK));
        chart.setAreaFill(Fills.newSolidFill(Color.newColor("708090")));

        return chart;
    }

    public static AbstractGraphChart generateHorizontalBarChart() {

        final int MAX_SALES = 51;

        Data company1Data = DataUtil.scaleWithinRange(0, MAX_SALES, Arrays.asList(13, 36));
        Data company2Data = DataUtil.scaleWithinRange(0, MAX_SALES, Arrays.asList(21, 38));
        Data company3Data = DataUtil.scaleWithinRange(0, MAX_SALES, Arrays.asList(13, 36));
        Data aciData = DataUtil.scaleWithinRange(0, MAX_SALES, Arrays.asList(21, 38));

        BarChartPlot company1Bar = Plots.newBarChartPlot(company1Data, Color.BLUE, "c1");
        BarChartPlot company2Bar = Plots.newBarChartPlot(company2Data, Color.GREEN, "c2");
        BarChartPlot company3Bar = Plots.newBarChartPlot(company3Data, Color.YELLOW, "c3");
        BarChartPlot aciBar = Plots.newBarChartPlot(aciData, Color.RED, "aci");

        BarChart chart = GCharts.newBarChart(company1Bar, company2Bar, company3Bar, aciBar);

        // Defining axis info and styles
        AxisStyle axisStyle = AxisStyle.newAxisStyle(BLACK, 13, AxisTextAlignment.CENTER);

        AxisLabels quarters = AxisLabelsFactory.newAxisLabels("Quarters", 50.0);
        quarters.setAxisStyle(axisStyle);

        AxisLabels companies = AxisLabelsFactory.newAxisLabels("Last", "This");
        companies.setAxisStyle(axisStyle);

        AxisLabels sales = AxisLabelsFactory.newAxisLabels("Sales", 50.0);
        sales.setAxisStyle(axisStyle);

        AxisLabels salesCount = AxisLabelsFactory.newNumericRangeAxisLabels(0, MAX_SALES);
        salesCount.setAxisStyle(axisStyle);

//        // Adding axis info to chart.
//        chart.addXAxisLabels(salesCount);
//        chart.addXAxisLabels(sales);
//        chart.addYAxisLabels(companies);
//        chart.addYAxisLabels(quarters);
//        chart.addTopAxisLabels(salesCount);
        chart.addXAxisLabels(companies);
        chart.addXAxisLabels(quarters);
        chart.addYAxisLabels(salesCount);
        chart.addYAxisLabels(sales);
        //chart.addTopAxisLabels(salesCount);

        chart.setHorizontal(false);
        chart.setSize(400, 300);
        chart.setSpaceBetweenGroupsOfBars(30);

        chart.setTitle("Quartlerly Sales Comparison with Top Competitors", BLACK, 16);

        chart.setGrid((50.0 / MAX_SALES) * 20, 600, 3, 2);
        chart.setBackgroundFill(Fills.newSolidFill(WHITE));
        LinearGradientFill fill = Fills.newLinearGradientFill(0, Color.newColor("E37600"), 100);
        fill.addColorAndOffset(Color.newColor("DC4800"), 0);
        chart.setAreaFill(fill);

        return chart;
    }

    public static AbstractGraphChart generateLineChart() {

        final int NUM_POINTS = 25;

        final double[] company1Sales = {90, 95, 85, 98};
        final double[] company2Sales = {80, 75, 85, 90};
        final double[] company3Sales = {70, 75, 65, 75};
        final double[] aciSales = {50, 40, 45, 55};

        Line line1 = Plots.newLine(Data.newData(company1Sales), RED, "C1");
        line1.setLineStyle(LineStyle.newLineStyle(3, 1, 0));
        line1.addShapeMarkers(Shape.DIAMOND, Color.newColor("CA3D05"), 12);
        line1.addShapeMarkers(Shape.DIAMOND, Color.WHITE, 8);

        Line line2 = Plots.newLine(Data.newData(company2Sales), BLUE, "C2");
        line2.setLineStyle(LineStyle.newLineStyle(3, 1, 0));
        line2.addShapeMarkers(Shape.DIAMOND, Color.newColor("CA3D05"), 12);
        line2.addShapeMarkers(Shape.DIAMOND, Color.WHITE, 8);

        Line line3 = Plots.newLine(Data.newData(company3Sales), GREEN, "C3");
        line3.setLineStyle(LineStyle.newLineStyle(3, 1, 0));
        line3.addShapeMarkers(Shape.DIAMOND, Color.newColor("CA3D05"), 12);
        line3.addShapeMarkers(Shape.DIAMOND, Color.WHITE, 8);

        Line line4 = Plots.newLine(Data.newData(aciSales), ORANGE, "ACI");
        line4.setLineStyle(LineStyle.newLineStyle(3, 1, 0));
        line4.addShapeMarkers(Shape.DIAMOND, Color.newColor("CA3D05"), 12);
        line4.addShapeMarkers(Shape.DIAMOND, Color.WHITE, 8);

//        Line line2 = Plots.newLine(Data.newData(competition), SKYBLUE, "Competition.com");
//        line2.setLineStyle(LineStyle.newLineStyle(3, 1, 0));
//        line2.addShapeMarkers(Shape.DIAMOND, SKYBLUE, 12);
//        line2.addShapeMarkers(Shape.DIAMOND, Color.WHITE, 8);
        //LineChart chart = GCharts.newLineChart(line1, line2);
        LineChart chart = GCharts.newLineChart(line1, line2, line3, line4);

        chart.setSize(600, 450);
        chart.setTitle("Web Traffic|(in billions of hits)", WHITE, 14);
        chart.addHorizontalRangeMarker(40, 60, Color.newColor(RED, 30));
        chart.addVerticalRangeMarker(70, 90, Color.newColor(GREEN, 30));
        chart.setGrid(25, 25, 3, 2);

        // Defining axis info and styles
        AxisStyle axisStyle = AxisStyle.newAxisStyle(WHITE, 12, AxisTextAlignment.CENTER);
        AxisLabels xAxis = AxisLabelsFactory.newAxisLabels("9", "12", "3", "6");
        xAxis.setAxisStyle(axisStyle);
        AxisLabels xAxis2 = AxisLabelsFactory.newAxisLabels("2014", "2014", "2015", "2015");
        xAxis2.setAxisStyle(axisStyle);

        AxisLabels yAxis = AxisLabelsFactory.newAxisLabels("", "25", "50", "75", "100");
        yAxis.setAxisStyle(axisStyle);
        AxisLabels yAxis2 = AxisLabelsFactory.newAxisLabels("Hits", 50.0);
        yAxis2.setAxisStyle(AxisStyle.newAxisStyle(WHITE, 14, AxisTextAlignment.CENTER));
        yAxis2.setAxisStyle(axisStyle);

        chart.addXAxisLabels(xAxis);
        chart.addXAxisLabels(xAxis2);

        chart.addYAxisLabels(yAxis);
        chart.addYAxisLabels(yAxis2);

        // Defining background and chart fills.
        chart.setBackgroundFill(Fills.newSolidFill(Color.newColor("1F1D1D")));
        LinearGradientFill fill = Fills.newLinearGradientFill(0, Color.newColor("363433"), 100);
        fill.addColorAndOffset(Color.newColor("2E2B2A"), 0);
        chart.setAreaFill(fill);

        return chart;

    }

    public static String getChartUrl(AbstractGraphChart chart) {
        return chart.toURLString();
    }

    public static void saveImage(String urlString, String chartName) {

        Image image;
        try {
            URL url = new URL(urlString);
            image = ImageIO.read(url);
            File outputfile = new File(Paths.get("charts", chartName + ".png").toString());
            ImageIO.write((RenderedImage) image, "png", outputfile);

        } catch (IOException e) {
        }
    }

}
