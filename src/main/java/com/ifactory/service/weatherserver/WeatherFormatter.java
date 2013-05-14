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

import com.ifactory.client.greenwich.Result;

public class WeatherFormatter {
  public String json(Result result) {
    return new StringBuilder("{\"name\":").append("\"")
      .append(result.getName()).append("\",\"weatherId\":")
        .append(result.getWeathers().get(0).getId())
          .append(",\"desc\":\"").append(result.getWeathers().get(0).getDescription())
            .append("\",\"temp\":").append(result.getMain().getTemp())
              .append(",\"high\":").append(result.getMain().getTempMax())
                .append(",\"low\":").append(result.getMain().getTempMin())
                  .append("}").toString();
  }
}