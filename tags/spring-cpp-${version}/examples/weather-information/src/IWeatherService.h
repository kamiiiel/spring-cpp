/*
 * WeatherService.h
 *
 *  Created on: Sep 24, 2009
 *      Author: woj
 */

#ifndef WEATHERSERVICE_H_
#define WEATHERSERVICE_H_

class IWeatherService {
public:
    virtual ~IWeatherService();

    virtual void getWeather();
};

#endif /* WEATHERSERVICE_H_ */
