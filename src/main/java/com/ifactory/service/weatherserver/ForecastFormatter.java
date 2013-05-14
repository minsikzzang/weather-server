/**
 * Copyright 2013 iFactory Ltd.
 * 
 * Min Kim (minsikzzang@gmail.com)
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.ifactory.service.weatherserver;

import com.ifactory.client.greenwich.DailyForecast;
import com.ifactory.client.greenwich.ForecastResult;
import com.ifactory.client.greenwich.HourlyForecast;
import com.ifactory.client.greenwich.Weather;

public class ForecastFormatter {
  
  private boolean daily;
  
  public ForecastFormatter(boolean daily) {
    this.daily = daily;
  }
  
  public String dailyJson(ForecastResult result) {
    StringBuilder builder = new StringBuilder("{\"name\":\"")
      .append(result.getName()).append("\",\"forecasts\":[");
    for (DailyForecast f: result.getDaily()) {
      Weather w = f.getWeathers().get(0);
      builder.append("{\"dt\":").append(f.getTimestamp())
        .append(",\"high\":").append(f.getTemp().getMax())
          .append(",\"low\":").append(f.getTemp().getMin())
            .append(",\"weather\":{\"id\":").append(w.getId())
              .append("}},");          
    }
    builder.deleteCharAt(builder.length() - 1);
    return builder.append("]}").toString();
  }
  
  public String hourlyJson(ForecastResult result) {
    StringBuilder builder = new StringBuilder("{\"name\":\"")
      .append(result.getName()).append("\",\"forecasts\":[");
    for (HourlyForecast f: result.getHourly()) {
      Weather w = f.getWeathers().get(0);
      builder.append("{\"dt\":").append(f.getTimestamp())
        .append(",\"temp\":").append(f.getMain().getTemp())
          .append(",\"weather\":{\"id\":").append(w.getId())
            .append("}},");          
    }
    builder.deleteCharAt(builder.length() - 1);
    return builder.append("]}").toString();
  }
  
  public String json(ForecastResult result) {
    return this.daily ? dailyJson(result) : hourlyJson(result);
  }
} 