package com.lowestcostpath;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView txt_result, txt_path, txt_testcase;
    private EditText txt_input;
    private Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_result = (TextView) findViewById(R.id.txt_result);
        txt_path = (TextView) findViewById(R.id.txt_pass);
        txt_testcase = (TextView) findViewById(R.id.txt_testcase);

        txt_input = (EditText) findViewById(R.id.txt_input);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    txt_testcase.setText("");

                    String strMatrix = txt_input.getText().toString().trim();
                    String[] lines = strMatrix.split("\n");

                    int m = lines.length - 1;
                    int n = 0;
                    int[][] costMatrix = new int[lines.length][];
                    for (int i = 0; i < lines.length; i++) {
                        String[] values = lines[i].split(" ");
                        costMatrix[i] = new int[values.length];

                        for (int j = 0; j < values.length; j++) {
                            costMatrix[i][j] = Integer.parseInt(values[j]);
                        }
                        n = values.length - 1;
                    }

                    int result = minimumCostPathDP(costMatrix, m, n);       // call function
                    if (result > 50) {
                        txt_path.setText("No");
                    } else {
                        txt_path.setText("Yes");
                    }
                    txt_result.setText(String.format("%d", result));
                } catch (Exception e) {
                    txt_testcase.setText("Please input matrix correctly.");
                }
            }
        });
    }

    // function brief:
    // to get path of lowest cost. You can test this function with any matrix(m, n)
    // Parameter: costMatrix => array, m => rows, n => cols

    public int minimumCostPathDP(int[][] costMatrix, int m, int n) {
        int[][] minimumCostPath = new int[m+1][n+1];
        minimumCostPath[0][0] = costMatrix[0][0];

        try {
            for (int i = 1; i <= m; i++) {
                minimumCostPath[i][0] = minimumCostPath[i - 1][0] + costMatrix[i][0];
            }

            for (int j = 1; j <= n; j++) {
                minimumCostPath[0][j] = minimumCostPath[0][j - 1] + costMatrix[0][j];
            }

            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    minimumCostPath[i][j] = costMatrix[i][j]
                            + minimum(minimumCostPath[i - 1][j - 1],
                            minimumCostPath[i - 1][j],
                            minimumCostPath[i][j - 1]);
                    //                txt_testcase.setText(txt_testcase.getText().toString() + " " + String.format("%d", minimumCostPath[i][j]));
                }
            }
            txt_testcase.setText(String.format("%d", m + 1));
            int i = m;
            for (int j = n; j >= 1; j--) {
                int minIndex = 0;

                int p = i - 1;
                if (p < 0) {
                    p = m;
                }

                if (minimumCostPath[i][j - 1] > minimumCostPath[p][j - 1]) {
                    minIndex = p;

                    int k = i + 1;
                    if (k > m) {
                        k = 0;
                    }

                    if (minimumCostPath[k][j - 1] <= minimumCostPath[p][j - 1]) {
                        minIndex = k;
                    }
                } else {
                    minIndex = i;

                    int k = i + 1;
                    if (k > m) {
                        k = 0;
                    }
                    if (minimumCostPath[k][j - 1] <= minimumCostPath[i][j - 1]) {
                        minIndex = k;
                    }
                }
                i = minIndex;

                txt_testcase.setText(String.format("%d", minIndex + 1) + " " + txt_testcase.getText().toString());
            }
        } catch (Exception e) {
            txt_testcase.setText("Please input matrix correctly.");
            return 0;
        }
        return minimumCostPath[m][n];
    }

    // brief: to get minimum value
    public static int minimum(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }

}
