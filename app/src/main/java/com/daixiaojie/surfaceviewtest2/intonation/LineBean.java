package com.daixiaojie.surfaceviewtest2.intonation;

import com.google.gson.Gson;

/**
 * Created by daixiaojie on 2017/2/9.
 */

public class LineBean {
    private static String linesData = "{\"cents_min\": 5400,\n" +
            "        \"cents_max\": 7100,\n" +
            "        \"data\": [\n" +
            "            {\n" +
            "                \"cents\": 5400,\n" +
            "                \"player\": 1,\n" +
            "                \"start_time\": 6.299,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.176,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 6300,\n" +
            "                \"player\": 1,\n" +
            "                \"start_time\": 6.475,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.377,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 6300,\n" +
            "                \"player\": 1,\n" +
            "                \"start_time\": 6.852,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.286,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 6300,\n" +
            "                \"player\": 1,\n" +
            "                \"start_time\": 7.138,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.157,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 6300,\n" +
            "                \"player\": 1,\n" +
            "                \"start_time\": 7.295,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.22,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 6300,\n" +
            "                \"player\": 1,\n" +
            "                \"start_time\": 7.515,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.155,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 6300,\n" +
            "                \"player\": 1,\n" +
            "                \"start_time\": 7.67,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.196,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 6100,\n" +
            "                \"player\": 1,\n" +
            "                \"start_time\": 7.866,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.328,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 5900,\n" +
            "                \"player\": 1,\n" +
            "                \"start_time\": 8.194,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.365,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 6100,\n" +
            "                \"player\": 1,\n" +
            "                \"start_time\": 8.559,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.291,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 6300,\n" +
            "                \"player\": 1,\n" +
            "                \"start_time\": 8.85,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.513,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 5900,\n" +
            "                \"player\": 1,\n" +
            "                \"start_time\": 9.363,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.258,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 5600,\n" +
            "                \"player\": 1,\n" +
            "                \"start_time\": 9.621,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.844,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 6300,\n" +
            "                \"player\": 1,\n" +
            "                \"start_time\": 11.981,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.291,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 6300,\n" +
            "                \"player\": 1,\n" +
            "                \"start_time\": 12.272,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.362,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 6300,\n" +
            "                \"player\": 1,\n" +
            "                \"start_time\": 12.634,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.389,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 6100,\n" +
            "                \"player\": 1,\n" +
            "                \"start_time\": 13.023,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.155,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 5900,\n" +
            "                \"player\": 1,\n" +
            "                \"start_time\": 13.178,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.201,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 6100,\n" +
            "                \"player\": 1,\n" +
            "                \"start_time\": 13.379,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.293,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 5900,\n" +
            "                \"player\": 1,\n" +
            "                \"start_time\": 13.672,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.381,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 6100,\n" +
            "                \"player\": 1,\n" +
            "                \"start_time\": 14.053,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.372,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 5900,\n" +
            "                \"player\": 1,\n" +
            "                \"start_time\": 14.425,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.238,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 5600,\n" +
            "                \"player\": 1,\n" +
            "                \"start_time\": 14.663,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.87,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 5900,\n" +
            "                \"player\": 2,\n" +
            "                \"start_time\": 17.08,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.448,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 6300,\n" +
            "                \"player\": 2,\n" +
            "                \"start_time\": 17.528,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.295,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 6600,\n" +
            "                \"player\": 2,\n" +
            "                \"start_time\": 17.823,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.279,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 6600,\n" +
            "                \"player\": 2,\n" +
            "                \"start_time\": 18.102,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.288,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 6300,\n" +
            "                \"player\": 2,\n" +
            "                \"start_time\": 18.39,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.295,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 6800,\n" +
            "                \"player\": 2,\n" +
            "                \"start_time\": 18.685,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.352,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 6600,\n" +
            "                \"player\": 2,\n" +
            "                \"start_time\": 19.037,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.341,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 6800,\n" +
            "                \"player\": 2,\n" +
            "                \"start_time\": 19.378,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.52,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 5900,\n" +
            "                \"player\": 2,\n" +
            "                \"start_time\": 19.991,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.242,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 6800,\n" +
            "                \"player\": 2,\n" +
            "                \"start_time\": 20.233,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.341,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 6600,\n" +
            "                \"player\": 2,\n" +
            "                \"start_time\": 20.574,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.198,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 6300,\n" +
            "                \"player\": 2,\n" +
            "                \"start_time\": 20.772,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.312,\n" +
            "                \"collected_status\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"cents\": 6100,\n" +
            "                \"player\": 2,\n" +
            "                \"start_time\": 21.084,\n" +
            "                \"type\": \"default\",\n" +
            "                \"duration\": 0.349,\n" +
            "                \"collected_status\": 0\n" +
            "            }\n" +
            "        ]\n" +
            "    }";

    public static Data getLineList() {
       /* Data data = new Data();
        data.setMax_cents(7100);
        data.setMin_cents(5100);
        List<IntonationLine> lines = new ArrayList<>();
        IntonationLine line = new IntonationLine();
        line.setCents(5400);
        line.setPlayer(1);
        line.setStart_time(6.299);
        line.setType("default");
        line.setDuration(0.176);
        line.setCollected_status(0);
        lines.add(line);
        data.setHeihei(lines);*/

        return new Gson().fromJson(linesData, Data.class);
    }

}
