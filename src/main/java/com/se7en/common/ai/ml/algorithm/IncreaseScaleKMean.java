package com.se7en.common.ai.ml.algorithm;

import com.se7en.common.util.PropertyUtil;
import org.encog.ml.MLCluster;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataPair;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.kmeans.KMeansClustering;

import java.util.Arrays;

/**
 * Created by admin on 2017/6/20.
 * 涨幅聚类
 */
public class IncreaseScaleKMean extends ProcessAlgorithmAdapter{

    private double[][] data;

    private int kmeanCount;

    public IncreaseScaleKMean(double[][] data){
        this.data = data;
        kmeanCount = Integer.parseInt(PropertyUtil.getProperty("kmean.count"));
    }

    public void processData(){
        final BasicMLDataSet set = new BasicMLDataSet();

        for (final double[] element : data) {
            set.add(new BasicMLData(element));
        }

        final KMeansClustering kmeans = new KMeansClustering(kmeanCount, set);

        kmeans.iteration(100);

        // Display the cluster
        int i = 1;
        for (final MLCluster cluster : kmeans.getClusters()) {
            final MLDataSet ds = cluster.createDataSet();
            System.out.println("*** Cluster " + (i++) + " ***, count=" + ds.getRecordCount());
            final MLDataPair pair = BasicMLDataPair.createPair(
                    ds.getInputSize(), ds.getIdealSize());
            for (int j = 0; j < ds.getRecordCount(); j++) {
                ds.getRecord(j, pair);
                System.out.println(Arrays.toString(pair.getInputArray()));

            }
        }
    }

}
