/*
 * DisplayWeatherInformation.h
 *
 *  Created on: Sep 24, 2009
 *      Author: woj
 */

#ifndef DISPLAYWEATHERINFORMATION_H_
#define DISPLAYWEATHERINFORMATION_H_

#include "IWeatherService.h"

class DisplayWeather {
public:
    void displayWeather();

    IWeatherService* weatherService;
};

#endif /* DISPLAYWEATHERINFORMATION_H_ */
