package com.ebious.forecaster.model.forecaster;

import com.ebious.forecaster.model.domain.entity.ForecastDate;
import com.ebious.forecaster.model.domain.entity.Rate;
import com.ebious.forecaster.model.domain.enums.Currency;
import com.ebious.forecaster.model.domain.enums.Period;
import com.ebious.forecaster.model.utils.DateUtils;
import com.ebious.forecaster.model.utils.ForecasterUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ForecasterLinear implements Forecaster {

    /**
     * Number of recent required rates to predict!
     */
    private final static int RECENT_REQUIRED_RATES_TO_PREDICT = 30;
    private double intercept, slope;
    private double r2;
    private double svar0, svar1;

    @Override
    public Map<Currency, List<Rate>> forecast(Map<Currency, List<Rate>> rates, ForecastDate date) {
        Map<Currency, List<Rate>> res = new EnumMap<>(Currency.class);
        for (Currency currency : rates.keySet()) {
            List<Rate> reqRates = getRequiredRatesToPredict(rates.get(currency));
            linear(getX(reqRates), getY(reqRates));
            if (date.getPeriod().equals(Period.NONE)) {
                List<Rate> predictionRates = predictRates(date.getLocalDate().toEpochDay());
                res.put(currency, ForecasterUtils.getLastRateAsList(predictionRates));
            } else {
                res.put(currency, predictRates(DateUtils.periodToEpochDay(date.getPeriod())));
            }
        }
        return res;
    }

    private List<Rate> predictRates(Long forecastEpochDay) {
        List<Rate> rates = new ArrayList<>();
        long startDay = LocalDate.now().plusDays(1).toEpochDay();
        for (long i = startDay; i <= forecastEpochDay; i++) {
            rates.add(new Rate(LocalDate.ofEpochDay(i), predict(i)));
        }
        return rates;
    }

    private List<Rate> getRequiredRatesToPredict(List<Rate> rates) {
        return rates.stream()
                .limit(RECENT_REQUIRED_RATES_TO_PREDICT)
                .collect(Collectors.toList());
    }

    private double[] getY(List<Rate> rates) {
        return rates.stream()
                .mapToDouble(Rate::getCurs)
                .toArray();
    }

    private double[] getX(List<Rate> rates) {
        return rates.stream()
                .mapToDouble(d -> d.getDate().toEpochDay())
                .toArray();
    }

    /**
     * Performs a linear regression on the data points {@code (y[i], x[i])}.
     *
     * @param x the values of the predictor variable
     * @param y the corresponding values of the response variable
     * @throws IllegalArgumentException if the lengths of the two arrays are not equal
     */
    private void linear(double[] x, double[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("array lengths are not equal");
        }
        int n = x.length;

        // first pass
        double sumx = 0.0, sumy = 0.0, sumx2 = 0.0;
        for (int i = 0; i < n; i++) {
            sumx += x[i];
            sumx2 += x[i] * x[i];
            sumy += y[i];
        }
        double xbar = sumx / n;
        double ybar = sumy / n;

        // second pass: compute summary statistics
        double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
        for (int i = 0; i < n; i++) {
            xxbar += (x[i] - xbar) * (x[i] - xbar);
            yybar += (y[i] - ybar) * (y[i] - ybar);
            xybar += (x[i] - xbar) * (y[i] - ybar);
        }
        slope = xybar / xxbar;
        intercept = ybar - slope * xbar;

        // more statistical analysis
        double rss = 0.0;      // residual sum of squares
        double ssr = 0.0;      // regression sum of squares
        for (int i = 0; i < n; i++) {
            double fit = slope * x[i] + intercept;
            rss += (fit - y[i]) * (fit - y[i]);
            ssr += (fit - ybar) * (fit - ybar);
        }

        int degreesOfFreedom = n - 2;
        r2 = ssr / yybar;
        double svar = rss / degreesOfFreedom;
        svar1 = svar / xxbar;
        svar0 = svar / n + xbar * xbar * svar1;
    }

    /**
     * Returns the <em>y</em>-intercept &alpha; of the best of the best-fit line <em>y</em> = &alpha; + &beta; <em>x</em>.
     *
     * @return the <em>y</em>-intercept &alpha; of the best-fit line <em>y = &alpha; + &beta; x</em>
     */
    private double intercept() {
        return intercept;
    }

    /**
     * Returns the slope &beta; of the best of the best-fit line <em>y</em> = &alpha; + &beta; <em>x</em>.
     *
     * @return the slope &beta; of the best-fit line <em>y</em> = &alpha; + &beta; <em>x</em>
     */
    private double slope() {
        return slope;
    }

    /**
     * Returns the coefficient of determination <em>R</em><sup>2</sup>.
     *
     * @return the coefficient of determination <em>R</em><sup>2</sup>,
     * which is a real number between 0 and 1
     */
    private double R2() {
        return r2;
    }

    /**
     * Returns the standard error of the estimate for the intercept.
     *
     * @return the standard error of the estimate for the intercept
     */
    private double interceptStdErr() {
        return Math.sqrt(svar0);
    }

    /**
     * Returns the standard error of the estimate for the slope.
     *
     * @return the standard error of the estimate for the slope
     */
    private double slopeStdErr() {
        return Math.sqrt(svar1);
    }

    /**
     * Returns the expected response {@code y} given the value of the predictor
     * variable {@code x}.
     *
     * @param x the value of the predictor variable
     * @return the expected response {@code y} given the value of the predictor
     * variable {@code x}
     */
    private double predict(double x) {
        return slope * x + intercept;
    }

    /**
     * Returns a string representation of the simple linear regression model.
     *
     * @return a string representation of the simple linear regression model,
     * including the best-fit line and the coefficient of determination
     * <em>R</em><sup>2</sup>
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(String.format("%.2f n + %.2f", slope(), intercept()));
        s.append("  (R^2 = " + String.format("%.3f", R2()) + ")");
        return s.toString();
    }
}
