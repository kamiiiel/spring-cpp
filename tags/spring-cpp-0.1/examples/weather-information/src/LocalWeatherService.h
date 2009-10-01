/*
 * LocalWeatherService.h
 *
 *  Created on: Sep 24, 2009
 *      Author: woj
 */

#ifndef LOCALWEATHERSERVICE_H_
#define LOCALWEATHERSERVICE_H_

#include "IWeatherService.h"

class LocalWeatherService: public IWeatherService {
public:

    virtual void getWeather();

};

#endif /* LOCALWEATHERSERVICE_H_ */
