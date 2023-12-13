package Classes;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class CANFrameTransmission {

    private static final int MAX_DATA_POINTS = 500;
    private static final double TIME_PERIOD_PER_BIT = 1; // Adjust as needed
    private int xSeriesData = 0;
    private XYChart.Series<Number, Number> series;

    public void initialize(Stage primaryStage) {
        primaryStage.setTitle("Real-Time CAN Frame Transmission");

        NumberAxis xAxis = new NumberAxis(0, MAX_DATA_POINTS * TIME_PERIOD_PER_BIT, MAX_DATA_POINTS * TIME_PERIOD_PER_BIT / 10);
        NumberAxis yAxis = new NumberAxis(0, 2, 1); // Adjust yAxis range as needed
        xAxis.setForceZeroInRange(false);

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setCreateSymbols(false);
        lineChart.setAnimated(false);
        lineChart.setTitle("CAN Frame Transmission");

        series = new XYChart.Series<>();
        series.setName("CAN Frame Data");
        lineChart.getData().add(series);

        Scene scene = new Scene(lineChart, 800, 600);
        primaryStage.setScene(scene);

        final AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                addDataToSeries();
            }
        };
        animationTimer.start();
    }

    private void addDataToSeries() {
        // Define binary CAN frame data
        String binaryData = "110101010111111110000000001111111111111111001111000001111"; // Replace this with your desired binary frame

        if (binaryData.matches("[01]+")) {
            updateSeries(binaryData);
        } else {
            System.out.println("Invalid binary frame. Please provide a valid binary string.");
        }
    }

    private void updateSeries(String binaryData) {
        for (char bit : binaryData.toCharArray()) {
            int value = (bit == '1') ? 1 : 0;
            double timePeriod = xSeriesData * TIME_PERIOD_PER_BIT;
            series.getData().add(new XYChart.Data<>(timePeriod, value));
            xSeriesData++;

            if (xSeriesData > MAX_DATA_POINTS) {
                series.getData().remove(0, series.getData().size() - MAX_DATA_POINTS);
            }
        }

        // Pause the animation for a short duration to make the waveform visible
        try {
            Thread.sleep(50); // Adjust as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
