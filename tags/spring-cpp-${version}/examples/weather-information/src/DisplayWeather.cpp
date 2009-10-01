/*
 * DisplayWeatherInformation.cpp
 *
 *  Created on: Sep 24, 2009
 *      Author: woj
 */

#include "DisplayWeather.h"

#include <iostream>
using namespace std;

void DisplayWeather::displayWeather()
{
    weatherService->getWeather();

    cout << "Displaying weather" << endl;
}



