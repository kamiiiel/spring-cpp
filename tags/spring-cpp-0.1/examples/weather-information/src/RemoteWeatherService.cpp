/*
 * RemoteWeatherService.cpp
 *
 *  Created on: Sep 24, 2009
 *      Author: woj
 */

#include "RemoteWeatherService.h"

#include <iostream>
using namespace std;

void RemoteWeatherService::getWeather()
{
    internetClient->downloadData();
    cout << "Remote weather service provided data" << endl;
}



